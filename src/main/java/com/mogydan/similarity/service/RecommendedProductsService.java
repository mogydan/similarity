package com.mogydan.similarity.service;

import com.mogydan.similarity.model.Product;

import java.util.Set;

public interface RecommendedProductsService {

    Set<Product> getRecommendedProducts(long customerId);
}
