package com.mogydan.similarity.service;

import com.mogydan.similarity.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<Product> getAllProducts();

    List<Product> getListOfProductsByIds(List<Long> ids);

    Product getProductById(long productId);

    Product createProduct(Product product);

    void addProducts(List<Product> products);

    void updateProduct(long productId, Product product);

    void deleteProductById(long productId);

    void clear();
}
