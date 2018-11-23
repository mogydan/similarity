package com.mogydan.similarity.controller;

import com.mogydan.similarity.model.Product;
import com.mogydan.similarity.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public Product getById(@PathVariable("id") Long id) {
        return productService.getProductById(Long.toString(id));
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Product createProduct(@RequestParam("name") String name, @RequestParam("color") String color, @RequestParam("price") Double price) {
        return productService.addProduct(
                new Product()
                        .setColor(color)
                        .setName(name)
                        .setPrice(price)
        );
    }
}
