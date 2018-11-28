package com.mogydan.similarity.controller;

import com.mogydan.similarity.exception.ResourceNotFoundException;
import com.mogydan.similarity.model.ProductAmount;
import com.mogydan.similarity.service.TopProductsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Api(value = "Top Products", description = "Get the rating of the most sold products", tags = "Top Products")
public class TopProductsController {

    private final TopProductsService topProductsService;

    @GetMapping(value = "/topProducts")
    @ApiOperation(
            value = "Get the list of the best-selling products",
            response = ProductAmount.class,
            responseContainer = "List",
            tags = {"Top Products"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK", response = ProductAmount.class, responseContainer = "List"),
                    @ApiResponse(code = 400, message = "Unexpected error", response = ResourceNotFoundException.class)
            }
    )
    public List<ProductAmount> getTopProducts() {
        return topProductsService.getTopProducts();
    }
}
