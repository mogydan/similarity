package com.mogydan.similarity.service;

import com.mogydan.similarity.exception.ResourceNotFoundException;
import com.mogydan.similarity.model.*;
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
    public List<OrderDetails> getOrderDetailsByOrderId(long orderDetailsId) {
        return orderDetailsRepository.findByPurchaseOrder(
                new PurchaseOrder()
                        .setId(orderDetailsId)
        );
    }

    @Override
    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    @Override
    public OrderDetails getOrderDetailById(long orderDetailId) {
        return orderDetailsRepository.findById(orderDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderDetail with id = {0} was not found", orderDetailId));
    }

    @Override
    public void updateOrderDetails(OrderDetails updates, long orderDetailId) {
        OrderDetails orderDetails = getOrderDetailById(orderDetailId)
                .setPurchaseOrder(updates.getPurchaseOrder())
                .setAmount(updates.getAmount())
                .setProduct(updates.getProduct());
        orderDetailsRepository.save(orderDetails);
    }

    @Override
    public void deleteOrderDetails(long orderId) {
        orderDetailsRepository.delete(getOrderDetailById(orderId));
    }

    @Override
    public List<ProductAmount> getTopSoldProducts() {
        return orderDetailsRepository.getTopSoldProducts();
    }

    @Override
    public List<Long> getPurchasedProductsIds() {
        return orderDetailsRepository.getPurchasedProductsIds();
    }

    @Override
    public long getProductAmount(long productId, long orderId) {
        return orderDetailsRepository.getProductAmount(productId, orderId).orElse(0L);
    }

    @Override
    public List<Long> getAllOrdersIds() {
        return orderDetailsRepository.getAllOrdersIds();
    }

    @Override
    public long[] prepareVector(long productId, List<Long> ids) {
        return ids.stream()
                .mapToLong(id -> getProductAmount(productId, id))
                .toArray();
    }

    @Override
    public List<Purchase> getAllCustomersStatistic() {
        return orderDetailsRepository.getAllStatistics();
    }

    @Override
    public List<ProductStatistic> getAllProductsStatistic() {
        return orderDetailsRepository.getAllProductsStatistic();
    }

    @Override
    public void clear() {
        orderDetailsRepository.deleteAll();
    }
}
