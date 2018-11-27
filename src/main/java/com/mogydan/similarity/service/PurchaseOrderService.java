package com.mogydan.similarity.service;

import com.mogydan.similarity.model.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderService {

    PurchaseOrder createOrder(PurchaseOrder order);

    void addOrders(List<PurchaseOrder> order);

    PurchaseOrder getOrder(String orderId);

    List<PurchaseOrder> getAllOrders();

    void updateOrder(PurchaseOrder order, String orderId);

    void deleteOrder(String orderId);

    void clear();
}
