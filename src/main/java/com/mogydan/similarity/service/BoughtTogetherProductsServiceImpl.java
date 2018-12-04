package com.mogydan.similarity.service;

import com.google.common.collect.Sets;
import com.mogydan.similarity.model.Product;
import com.mogydan.similarity.model.ProductStatistic;
import com.mogydan.similarity.utils.SimilarityVector;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BoughtTogetherProductsServiceImpl implements BoughtTogetherProductsService {

    private final OrderDetailsService orderDetailsService;
    private final ProductService productService;

    public List<Product> getBoughtTogetherProducts(long productId) {

        List<Long> boughtProductsIds = orderDetailsService.getPurchasedProductsIds();

        List<ProductStatistic> allProductsStatistic = orderDetailsService.getAllProductsStatistic();

        Map<Long, Long> currentProductStatistic = getProductStatistic(productId, allProductsStatistic);

        List<Long> similarProductsIds = boughtProductsIds.stream()
                .filter(id -> id != productId && areProductsBoughtTogether(currentProductStatistic, getProductStatistic(id, allProductsStatistic)))
                .collect(Collectors.toList());

        return productService.getListOfProductsByIds(similarProductsIds);

    }

    @Override
    public boolean areProductsBoughtTogether(Map<Long, Long> currentProductStatistic, Map<Long, Long> productStatistic2) {
        Set<Long> commonOrdersIds = Sets.intersection(currentProductStatistic.keySet(), productStatistic2.keySet());
        if (commonOrdersIds.isEmpty()) {return false;}

        Set<Long> commonIds = Sets.union(currentProductStatistic.keySet(), productStatistic2.keySet());

        SimilarityVector vector1 = new SimilarityVector(currentProductStatistic, commonIds);
        SimilarityVector vector2 = new SimilarityVector(productStatistic2, commonIds);

        return vector1.getCosine(vector2) > 0.65;

    }

    private Map<Long, Long> getProductStatistic(long productId, List<ProductStatistic> allProductsStatistic) {
        return allProductsStatistic.stream()
                .filter(statistic -> statistic.getProductId() == productId)
                .collect(Collectors.toMap(ProductStatistic::getOrderId, ProductStatistic::getAmount));
    }

}
