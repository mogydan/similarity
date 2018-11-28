package com.mogydan.similarity.controller;

import com.mogydan.similarity.model.Customer;
import com.mogydan.similarity.model.OrderDetails;
import com.mogydan.similarity.model.Product;
import com.mogydan.similarity.model.PurchaseOrder;
import com.mogydan.similarity.repository.CustomerRepository;
import com.mogydan.similarity.repository.OrderDetailsRepository;
import com.mogydan.similarity.repository.ProductRepository;
import com.mogydan.similarity.repository.PurchaseOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@AllArgsConstructor
public class DataPreparationController {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    @PostMapping("/data")
    public void prepareData() {
        List<Customer> customers = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            customers.add(new Customer().setEmail("customer" + i + "@m.com").setName("customer" + i));
            products.add(new Product().setColor("asd" + i).setName("product" + i).setPrice(123 + i));
        }
        customerRepository.save(customers);
        productRepository.save(products);
        List<PurchaseOrder> orders = IntStream.range(0, products.size())
                .mapToObj(i -> new PurchaseOrder().setCustomer(customers.get(i)))
                .collect(Collectors.toList());

        purchaseOrderRepository.save(orders);

        List<OrderDetails> orderDetails = IntStream.range(0, customers.size())
                .mapToObj(i -> {
                    Set<OrderDetails> items = new HashSet<>();
                    for (int j = 0; j < new Random().nextInt(products.size() - 1); j++) {
                        OrderDetails item = new OrderDetails()
                                .setProduct(products.get(new Random().nextInt(products.size() - 1)))
                                .setPurchaseOrder(orders.get(new Random().nextInt(orders.size() - 1)))
                                .setAmount((long) j);
                        items.add(item);
                    }
                    return items;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        orderDetailsRepository.save(orderDetails);

    }

    @GetMapping("/orders/all")
    public @ResponseBody
    List<PurchaseOrder> getAllOrders() {
        return purchaseOrderRepository.findAll();
    }
}
