package com.mogydan.similarity.service;

import com.mogydan.similarity.model.Product;
import com.mogydan.similarity.utils.SimilarityVector;

import java.util.List;

public interface BoughtTogetherProductsService {

    List<Product> getBoughtTogetherProducts(long productId);

    boolean areProductsBoughtTogether(SimilarityVector vector1, long productId2, List<Long> orderIds);
}
