package com.mogydan.similarity.service;

import com.mogydan.similarity.exception.ResourceNotFoundException;
import com.mogydan.similarity.model.PurchaseOrder;
import com.mogydan.similarity.repository.PurchaseOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository orderRepository;

    @Override
    public PurchaseOrder createOrder(PurchaseOrder order) {
        return orderRepository.save(order);
    }

    @Override
    public void addOrders(List<PurchaseOrder> orders) {
        orderRepository.save(orders);
    }

    @Override
    public PurchaseOrder getOrder(String orderId) {
        return orderRepository.findById(Long.parseLong(orderId))
                .orElseThrow(() -> new ResourceNotFoundException("Order with id = {0} was not found", orderId));
    }

    @Override
    public List<PurchaseOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void updateOrder(PurchaseOrder updates, String orderId) {
        PurchaseOrder order = getOrder(orderId)
                .setCustomer(updates.getCustomer());
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(String orderId) {
        orderRepository.delete(getOrder(orderId));
    }

    @Override
    public void clear() {
        orderRepository.deleteAll();
    }
}
