package com.mogydan.similarity.controller;

import com.mogydan.similarity.model.Customer;
import com.mogydan.similarity.model.OrderDetails;
import com.mogydan.similarity.model.Product;
import com.mogydan.similarity.model.PurchaseOrder;
import com.mogydan.similarity.service.CustomerService;
import com.mogydan.similarity.service.OrderDetailsService;
import com.mogydan.similarity.service.ProductService;
import com.mogydan.similarity.service.PurchaseOrderService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@AllArgsConstructor
@Api(
        value = "Test Data Provider",
        description = "Provide Test Data",
        tags = {"Test Data Provider"}
)
public class DataPreparationController {

    private final PurchaseOrderService purchaseOrderService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderDetailsService orderDetailsService;

    @PostMapping("/data")
    @ApiOperation(value = "Prepare new test Data", tags = {"Test Data Provider"})
    @ApiResponses(value = @ApiResponse(code = 201, message = "OK"))
    public void prepareData(
            @ApiParam(value = "Product amount", required = true) @Valid @RequestParam("Products amount") int productAmount,
            @ApiParam(value = "Customers amount", required = true) @Valid @RequestParam("Customers amount") int customersAmount,
            @ApiParam(value = "Orders amount", required = true) @Valid @RequestParam("Orders amount") int orderAmount
    ) {
        List<Customer> customers = IntStream.range(0, customersAmount)
                .mapToObj(i -> new Customer()
                        .setEmail("customer" + i + "@m.com")
                        .setName("customer" + i)
                )
                .collect(Collectors.toList());


        List<Product> products = IntStream.range(0, productAmount)
                .mapToObj(i -> new Product()
                        .setColor("color" + i)
                        .setName("product" + i)
                        .setPrice(new Random().nextInt(100) + i)
                )
                .collect(Collectors.toList());

        customerService.addCustomers(customers);
        productService.addProducts(products);


        List<PurchaseOrder> orders = IntStream.range(0, orderAmount)
                .mapToObj(i -> new PurchaseOrder()
                        .setCustomer(customers.get(
                                new Random()
                                        .nextInt(customers.size() - 1)
                                )
                        )
                )
                .collect(Collectors.toList());

        purchaseOrderService.addOrders(orders);


        List<OrderDetails> orderDetails = orders.stream()
                .map(order -> {
                            Set<OrderDetails> items = new HashSet<>();
                            for (int j = 0; j < new Random().nextInt(products.size() - 1); j++) {
                                OrderDetails item = new OrderDetails()
                                        .setProduct(products.get(j))
                                        .setPurchaseOrder(order)
                                        .setAmount(new Random().nextInt(20) + 1);
                                items.add(item);
                            }
                            return items;
                        }
                )
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        orderDetailsService.addListOfOrderDetails(orderDetails);

    }

    @DeleteMapping("/data/clear")
    public void clear() {
        orderDetailsService.clear();
        purchaseOrderService.clear();
        productService.clear();
        customerService.clear();
    }
}
