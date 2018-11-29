package com.mogydan.similarity.controller;

import com.mogydan.similarity.exception.ResourceNotFoundException;
import com.mogydan.similarity.model.Product;
import com.mogydan.similarity.service.ProductService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Api(value = "Products", description = "Products controller", tags = {"Products"})
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Info for a specific product", response = Product.class, tags = {"Products"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK", response = Product.class),
                    @ApiResponse(code = 404, message = "NOT FOUND", response = ResourceNotFoundException.class)
            }
    )
    public Product getById(
            @ApiParam(value = "The id of the product to retrieve", required = true)
            @PathVariable("id") long id
    ) {
        return productService.getProductById(id);
    }

    @GetMapping
    @ApiOperation(
            value = "The list of all products",
            response = Product.class,
            responseContainer = "List",
            tags = {"Products"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "List of all products",
                            response = Product.class,
                            responseContainer = "List"
                    ),
                    @ApiResponse(code = 500, message = "Unexpected error", response = ResourceNotFoundException.class)
            }
    )
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    @ApiOperation(value = "Add new product", tags = {"Products"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "New product was added"),
                    @ApiResponse(code = 404, message = "NOT FOUND", response = ResourceNotFoundException.class),
                    @ApiResponse(code = 400, message = "Unexpected error", response = ResourceNotFoundException.class)
            }
    )
    public Product createProduct(
            @ApiParam(value = "Name of new product", required = true) @Valid @RequestParam("name") String name,
            @ApiParam(value = "Color of new product", required = true) @Valid @RequestParam("color") String color,
            @ApiParam(value = "Price of new product", required = true) @Valid @RequestParam("price") Double price
    ) {
        return productService.createProduct(
                new Product()
                        .setColor(color)
                        .setName(name)
                        .setPrice(price)
        );
    }

    @PostMapping("/addAll")
    @ApiOperation(value = "Add the list of new products", tags = {"Products"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "New Products were added"),
                    @ApiResponse(code = 400, message = "Unexpected error", response = ResourceNotFoundException.class)
            }
    )
    public void addProducts(
            @ApiParam(value = "Add the list of new products to the store", required = true)
            @Valid @RequestBody List<Product> products
    ) {
        productService.addProducts(products);
    }

    @PutMapping("/{productId}")
    @ApiOperation(value = "Update product", tags = {"Products"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 500, message = "Unexpected error", response = ResourceNotFoundException.class)
            }
    )
    public void updateProduct(
            @ApiParam(value = "The id of the product to update", required = true) @PathVariable long productId,
            @ApiParam(value = "Body of updated product", required = true) @Valid @RequestBody Product updates
    ) {

        productService.updateProduct(productId, updates);
    }

    @DeleteMapping("/{productId}")
    @ApiOperation(value = "Delete product by Id", tags = {"Products"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 400, message = "unexpected error", response = ResourceNotFoundException.class)
            }
    )
    public void deleteProduct(
            @ApiParam(value = "The id of the product to delete", required = true) @PathVariable long productId
    ) {
        productService.deleteProductById(productId);
    }



}
