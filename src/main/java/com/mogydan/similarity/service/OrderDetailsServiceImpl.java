package com.mogydan.similarity.service;

import com.mogydan.similarity.exception.ResourceNotFoundException;
import com.mogydan.similarity.model.OrderDetails;
import com.mogydan.similarity.model.PurchaseOrder;
import com.mogydan.similarity.repository.OrderDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    @Override
    public OrderDetails createOrderDetails(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    @Override
    public void addListOfOrderDetails(List<OrderDetails> orderDetails) {
        orderDetailsRepository.save(orderDetails);
    }

    @Override
    public List<OrderDetails> getOrderDetailsByOrderId(String orderDetailsId) {
        return orderDetailsRepository.findByPurchaseOrder(
                new PurchaseOrder()
                        .setId(Long.parseLong(orderDetailsId))
        );
    }

    @Override
    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    @Override
    public OrderDetails getOrderDetailById(String orderDetailId) {
        return orderDetailsRepository.findById(Long.parseLong(orderDetailId))
                .orElseThrow(() -> new ResourceNotFoundException("OrderDetail with id = {0} was not found", orderDetailId));
    }

    @Override
    public void updateOrderDetails(OrderDetails updates, String orderDetailId) {
        OrderDetails orderDetails = getOrderDetailById(orderDetailId)
                .setPurchaseOrder(updates.getPurchaseOrder())
                .setAmount(updates.getAmount())
                .setProduct(updates.getProduct());
        orderDetailsRepository.save(orderDetails);
    }

    @Override
    public void deleteOrderDetails(String orderId) {
        orderDetailsRepository.delete(getOrderDetailById(orderId));
    }

    @Override
    public void clear() {
        orderDetailsRepository.deleteAll();
    }
}
