package com.mogydan.similarity.service;

import com.mogydan.similarity.model.OrderDetails;

import java.util.List;

public interface OrderDetailsService {
    OrderDetails createOrderDetails(OrderDetails orderDetails);

    void addListOfOrderDetails(List<OrderDetails> orderDetails);

    List<OrderDetails> getOrderDetailsByOrderId(String orderId);

    List<OrderDetails> getAllOrderDetails();

    OrderDetails getOrderDetailById(String orderDetailId);

    void updateOrderDetails(OrderDetails updates, String orderDetailId);

    void deleteOrderDetails(String orderId);

    void clear();
}
