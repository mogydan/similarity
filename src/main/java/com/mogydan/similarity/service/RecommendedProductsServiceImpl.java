package com.mogydan.similarity.service;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mogydan.similarity.model.Product;
import com.mogydan.similarity.utils.SimilarityVector;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecommendedProductsServiceImpl implements RecommendedProductsService {

    private final OrderDetailsService orderDetailsService;
    private final PurchaseOrderService purchaseOrderService;
    private final ProductService productService;


    @Override
    public Set<Product> getRecommendedProducts(long customerId) {
        List<Long> customerOrdersIds = purchaseOrderService.getCustomerOrdersIds(customerId);
        if (customerOrdersIds.isEmpty()) {return ImmutableSet.of();}

        Map<Long, Long> purchasedStatistic = orderDetailsService.getCustomerPurchaseStatistic(customerOrdersIds);

        Set<Long> productsIds = purchaseOrderService.getBuyersIds().stream()
                .filter(id -> !id.equals(customerId))
                .flatMap(id -> getRecommendedProductIds(purchasedStatistic, id).stream())
                .collect(Collectors.toSet());

        return productsIds.stream()
                .map(productService::getProductById)
                .collect(Collectors.toSet());

    }

    private Set<Long> getRecommendedProductIds(Map<Long, Long> currentStatistic, long customerId) {
        List<Long> customerOrdersIds = purchaseOrderService.getCustomerOrdersIds(customerId);
        if (customerOrdersIds.isEmpty()) {
            return ImmutableSet.of();
        }

        Map<Long, Long> statistic2 = orderDetailsService.getCustomerPurchaseStatistic(customerOrdersIds);
        Set<Long> commonIds = Sets.union(currentStatistic.keySet(), statistic2.keySet());

        Set<Long> differentIds = Sets.difference(statistic2.keySet(), currentStatistic.keySet());

        if (Collections.disjoint(currentStatistic.keySet(), statistic2.keySet()) || differentIds.isEmpty()) {
            return ImmutableSet.of();
        }

        SimilarityVector vector1 = new SimilarityVector(currentStatistic, commonIds);
        SimilarityVector vector2 = new SimilarityVector(statistic2, commonIds);

        if (vector1.getCosine(vector2) < 0.65) {
            return ImmutableSet.of();
        }

        return differentIds;
    }
}
