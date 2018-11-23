package com.mogydan.similarity.service;

import com.mogydan.similarity.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(String productId);

    Product addProduct(Product product);

    void addProducts(List<Product> products);

    void updateProduct(Product product, String productId);

    void deleteProductById(String productId);

    void clear();
}
