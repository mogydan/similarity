package com.mogydan.similarity.controller;

import com.mogydan.similarity.exception.ResourceNotFoundException;
import com.mogydan.similarity.model.Customer;
import com.mogydan.similarity.model.PurchaseOrder;
import com.mogydan.similarity.service.PurchaseOrderService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Api(value = "Orders", description = "Purchase Orders controller", tags = {"Orders"})
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @PostMapping
    @ApiOperation(value = "Add new order", response = PurchaseOrder.class, tags = {"Orders"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "New order was added", response = PurchaseOrder.class),
                    @ApiResponse(code = 400, message = "Unexpected error", response = ResourceNotFoundException.class)
            }
    )
    public PurchaseOrder addOrder(
            @ApiParam(value = "Customer's id", required = true) @RequestParam("customerId") long customerId
    ) {
        return purchaseOrderService.createOrder(
                new PurchaseOrder()
                        .setCustomer(
                                new Customer()
                                        .setId(customerId)
                        )
        );
    }

    @PostMapping("/addAll")
    @ApiOperation(value = "Add the list of new orders", tags = {"Orders"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "OK"),
                    @ApiResponse(code = 500, message = "Unexpected error", response = ResourceNotFoundException.class)
            }
    )
    public void addOrders(
            @ApiParam(value = "Add the list of orders to the store", required = true)
            @Valid @RequestBody List<PurchaseOrder> orders
    ) {
        purchaseOrderService.addOrders(orders);
    }

    @GetMapping("/{orderId}")
    @ApiOperation(value = "Info for a specific order", response = PurchaseOrder.class, tags = {"Orders"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Ok", response = PurchaseOrder.class),
                    @ApiResponse(code = 400, message = "NOT FOUND", response = ResourceNotFoundException.class)
            }
    )
    public PurchaseOrder getOrder(
            @ApiParam(value = "The id of the order to retrieve", required = true) @PathVariable long orderId
    ) {
        return purchaseOrderService.getOrder(orderId);
    }

    @GetMapping
    @ApiOperation(
            value = "The list of all orders",
            response = PurchaseOrder.class,
            responseContainer = "List",
            tags = {"Orders"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200,
                            message = "The list of all orders",
                            response = PurchaseOrder.class,
                            responseContainer = "List"
                    ),
                    @ApiResponse(code = 500, message = "Unexpected error", response = ResourceNotFoundException.class)
            })
    public List<PurchaseOrder> getAllOrders() {
        return purchaseOrderService.getAllOrders();
    }

    @PutMapping("/{orderId}")
    @ApiOperation(value = "Update order", tags = {"Orders"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 500, message = "Unexpected error", response = ResourceNotFoundException.class)
            }
    )
    public void updateOrder(
            @ApiParam(value = "The id of the Order to update", required = true) @PathVariable long orderId,
            @ApiParam(value = "Updates", required = true) @RequestBody PurchaseOrder updates
    ) {
        purchaseOrderService.updateOrder(updates, orderId);
    }

    @DeleteMapping("/{orderId}")
    @ApiOperation(value = "Delete order by Id", tags = {"Orders"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(code = 404, message = "NOT FOUND", response = ResourceNotFoundException.class)
            }
    )
    public void deleteOrder(
            @ApiParam(value = "The id of the order to delete", required = true) @PathVariable long orderId
    ) {
        purchaseOrderService.deleteOrder(orderId);//TODO need to delete related OrderDetails too
    }

}
