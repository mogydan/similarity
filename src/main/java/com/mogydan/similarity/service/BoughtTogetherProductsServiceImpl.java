package com.mogydan.similarity.service;

import com.google.common.collect.ImmutableList;
import com.mogydan.similarity.model.Product;
import com.mogydan.similarity.utils.SimilarityVector;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BoughtTogetherProductsServiceImpl implements BoughtTogetherProductsService {

    private final OrderDetailsService orderDetailsService;
    private final ProductService productService;

    @Override
    public List<Product> getBoughtTogetherProducts(long productId) {
        List<Long> orderIds = orderDetailsService.getAllOrdersIds();

        List<Long> purchasedProductsIds = orderDetailsService.getPurchasedProductsIds();

        if (!purchasedProductsIds.contains(productId)) {
            return ImmutableList.of();
        }


        SimilarityVector vector1 = new SimilarityVector(orderDetailsService.prepare(productId, orderIds));
        if (vector1.isZero()) {return ImmutableList.of();}

        return purchasedProductsIds.stream()
                .filter(id -> productId != id)
                .filter(id -> areProductsBoughtTogether(vector1, id, orderIds))
                .map(productService::getProductById)
                .collect(Collectors.toList());
    }

    @Override
    public boolean areProductsBoughtTogether(SimilarityVector vector1, long productId2, List<Long> orderIds) {
        SimilarityVector vector2 = new SimilarityVector(orderDetailsService.prepare(productId2, orderIds));
        if (vector2.isZero()) {return false;}
        return vector1.getCosine(vector2) > 0.65;
    }


}
