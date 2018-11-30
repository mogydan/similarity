package com.mogydan.similarity.service;

import com.mogydan.similarity.model.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderService {

    PurchaseOrder createOrder(PurchaseOrder order);

    void addOrders(List<PurchaseOrder> order);

    PurchaseOrder getOrder(long orderId);

    List<PurchaseOrder> getAllOrders();

    void updateOrder(PurchaseOrder order, long orderId);

    void deleteOrder(long orderId);

    List<Long> getBuyersIds();

    List<Long> getCustomerOrdersIds(long customerId);

    void clear();
}
