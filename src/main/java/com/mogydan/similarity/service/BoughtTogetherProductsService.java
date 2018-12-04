package com.mogydan.similarity.service;

import com.mogydan.similarity.model.Product;

import java.util.List;
import java.util.Map;

public interface BoughtTogetherProductsService {

    List<Product> getBoughtTogetherProducts(long productId);

    boolean areProductsBoughtTogether(Map<Long, Long> currentProductStatistic, Map<Long, Long> productStatistic2);
}
