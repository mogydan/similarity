package com.mogydan.similarity.controller;

import com.mogydan.similarity.exception.ResourceNotFoundException;
import com.mogydan.similarity.model.Product;
import com.mogydan.similarity.service.RecommendedProductsService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@Api(
        value = "getRecommendedProducts",
        description = "Get the products, which customers like you bought",
        tags = {"Recommended Products"}
)
@AllArgsConstructor
public class RecommendedProductsController {

    private final RecommendedProductsService recommendedProductsService;

    @GetMapping("/getRecommendedProducts/{customerId}")
    @ApiOperation(
            value = "The list of products which similar customers bought",
            response = Product.class,
            responseContainer = "List",
            tags = {"Recommended Products"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "List of all products", response = Product.class, responseContainer = "List"),
                    @ApiResponse(code = 500, message = "Product was not found", response = ResourceNotFoundException.class)
            }
    )
    public Set<Product> getRecommendedProducts(@ApiParam(value = "Customer id to find the recommended products", required = true)
                                               @PathVariable Long customerId) {
        return recommendedProductsService.getRecommendedProducts(customerId);
    }

}
