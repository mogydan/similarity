package com.mogydan.similarity.service;

import com.mogydan.similarity.model.ProductAmount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TopProductsServiceImpl implements TopProductsService {

    private final OrderDetailsService orderDetailsService;

    @Override
    public List<ProductAmount> getTopProducts() {
        return orderDetailsService.getTopSoldProducts();
    }
}
