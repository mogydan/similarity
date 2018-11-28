package com.mogydan.similarity.service;

import com.mogydan.similarity.model.ProductAmount;

import java.util.List;

public interface TopProductsService {

    List<ProductAmount> getTopProducts();
}
