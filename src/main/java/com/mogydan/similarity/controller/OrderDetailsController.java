package com.mogydan.similarity.controller;

import com.mogydan.similarity.exception.ResourceNotFoundException;
import com.mogydan.similarity.model.OrderDetails;
import com.mogydan.similarity.model.Product;
import com.mogydan.similarity.model.PurchaseOrder;
import com.mogydan.similarity.service.OrderDetailsService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orderDetails")
@Api(value = "Order Details", description = "Order Details controller", tags = {"Order Details"})
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;

    @PostMapping
    @ApiOperation(value = "Add new order", response = OrderDetails.class, tags = {"Order Details"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "New order details was added", response = OrderDetails.class),
                    @ApiResponse(code = 400, message = "Unexpected error", response = ResourceNotFoundException.class)
            }
    )
    public OrderDetails createOrderDetails(
            @ApiParam(value = "Amount", required = true) @Valid @RequestParam("amount") long amount,
            @ApiParam(value = "Product Id", required = true) @Valid @RequestParam("productId") long productId,
            @ApiParam(value = "Purchase Order Id", required = true) @Valid @RequestParam("orderId") long orderId
    ) {
        return orderDetailsService.createOrderDetails(
                new OrderDetails()
                        .setAmount(amount)
                        .setProduct(new Product()
                                .setId(productId)
                        )
                        .setPurchaseOrder(
                                new PurchaseOrder()
                                        .setId(orderId)
                        )
        );
    }

    @PostMapping("/addAll")
    @ApiOperation(value = "Add the list of new Order Details", tags = {"Order Details"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "OK"),
                    @ApiResponse(code = 500, message = "Unexpected error", response = ResourceNotFoundException.class)
            }
    )
    public void addListOfOrderDetails(
            @ApiParam(value = "Add the list of Order Details", required = true)
            @Valid @RequestBody List<OrderDetails> orders
    ) {
        orderDetailsService.addListOfOrderDetails(orders);

    }

    @GetMapping("/byOrderId/{orderId}")
    @ApiOperation(
            value = "The list of all Order Details",
            response = OrderDetails.class,
            responseContainer = "List",
            tags = {"Order Details"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200,
                            message = "The list of all Order Details",
                            response = OrderDetails.class,
                            responseContainer = "List"
                    ),
                    @ApiResponse(code = 404, message = "NOT FOUND", response = ResourceNotFoundException.class),
                    @ApiResponse(code = 500, message = "Unexpected error", response = ResourceNotFoundException.class)
            })
    public List<OrderDetails> getOrderDetailsByOrderId(
            @ApiParam(value = "Order Id to get its Order Details", required = true)
            @PathVariable long orderId
    ) {
        return orderDetailsService.getOrderDetailsByOrderId(orderId);
    }

    @GetMapping
    @ApiOperation(
            value = "The list of all Order Details",
            response = OrderDetails.class,
            responseContainer = "List",
            tags = {"Order Details"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200,
                            message = "The list of all Order Details",
                            response = OrderDetails.class,
                            responseContainer = "List"
                    ),
                    @ApiResponse(code = 500, message = "Unexpected error", response = ResourceNotFoundException.class)
            })
    public List<OrderDetails> getAllOrderDetails() {
       return orderDetailsService.getAllOrderDetails();
    }

    @GetMapping("/{orderDetailId}")
    @ApiOperation(value = "Info for a specific Order Detail", response = PurchaseOrder.class, tags = {"Order Details"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK", response = OrderDetails.class),
                    @ApiResponse(code = 400, message = "NOT FOUND", response = ResourceNotFoundException.class)
            }
    )
    public OrderDetails getOrderDetailById(
            @ApiParam(value = "The id of the Order Detail to retrieve", required = true)
            @PathVariable long orderDetailId
    ) {
        return orderDetailsService.getOrderDetailById(orderDetailId);
    }

    @PutMapping("/{orderDetailId}")
    @ApiOperation(value = "Update order", tags = {"Orders Details"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 500, message = "Unexpected error", response = ResourceNotFoundException.class)
            }
    )
    public void updateOrderDetails(
            @ApiParam(value = "The id of the Order Detail to update", required = true) @PathVariable long orderDetailId,
            @ApiParam(value = "Updates", required = true) @RequestBody OrderDetails updates
    ) {
        orderDetailsService.updateOrderDetails(updates, orderDetailId);
    }

    @DeleteMapping("/{orderDetailId}")
    @ApiOperation(value = "Delete order by Id", tags = {"Order Details"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(code = 404, message = "NOT FOUND", response = ResourceNotFoundException.class)
            }
    )
    public void deleteOrderDetails(
            @ApiParam(value = "The id of the Order Detail to delete", required = true)
            @PathVariable long orderDetailId
    ) {
        orderDetailsService.deleteOrderDetails(orderDetailId);
    }
}
