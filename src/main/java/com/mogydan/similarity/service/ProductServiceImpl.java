package com.mogydan.similarity.service;

import com.google.common.collect.Lists;
import com.mogydan.similarity.exception.ResourceNotFoundException;
import com.mogydan.similarity.model.Product;
import com.mogydan.similarity.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void addProducts(List<Product> products) {
        productRepository.save(products);
    }

    @Override
    public Product getProductById(String productId) {
        return productRepository.findById(Long.parseLong(productId))
                         .orElseThrow(() -> new ResourceNotFoundException("Product with id = {0} was not found", productId));
    }

    @Override
    public List<Product> getAllProducts() {
        return Lists.newArrayList(productRepository.findAll());
    }

    @Override
    public void updateProduct(Product updates, String productId) {
        Product product = getProductById(productId);
        product.setName(updates.getName());
        product.setColor(updates.getColor());
        product.setPrice(updates.getPrice());
        productRepository.save(product);
    }

    @Override
    public void deleteProductById(String productId) {
        productRepository.delete(getProductById(productId));
    }

    @Override
    public void clear() {
        productRepository.deleteAll();
    }
}
