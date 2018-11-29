package com.mogydan.similarity.controller;

import com.mogydan.similarity.exception.ResourceNotFoundException;
import com.mogydan.similarity.model.Product;
import com.mogydan.similarity.service.BoughtTogetherProductsService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/boughtTogetherProducts")
@AllArgsConstructor
@Api(
        value = "boughtTogetherProducts",
        description = "Get products, that are often bought together",
        tags = {"Bought Together Products"}
)
public class BoughtTogetherProductsController {

    private final BoughtTogetherProductsService boughtTogetherProductsService;

    @GetMapping("/{productId}")
    @ApiOperation(
            value = "The list of products which customers often bought with current product",
            response = Product.class,
            responseContainer = "List",
            tags = {"Bought Together Products"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK", response = Product.class, responseContainer = "List"),
                    @ApiResponse(code = 400, message = "Unexpected error", response = ResourceNotFoundException.class)
            })
    public List<Product> getBoughtTogetherProducts(
            @ApiParam(
                    value = "Product id to find the products which customers often bought with current",
                    required = true
            )
            @PathVariable long productId) {

        return boughtTogetherProductsService.getBoughtTogetherProducts(productId);
    }
}
