package com.mogydan.similarity.service;

import com.mogydan.similarity.model.OrderDetails;
import com.mogydan.similarity.model.ProductAmount;
import com.mogydan.similarity.model.ProductStatistic;
import com.mogydan.similarity.model.Purchase;

import java.util.List;

public interface OrderDetailsService {
    OrderDetails createOrderDetails(OrderDetails orderDetails);

    void addListOfOrderDetails(List<OrderDetails> orderDetails);

    List<OrderDetails> getOrderDetailsByOrderId(long orderId);

    List<OrderDetails> getAllOrderDetails();

    OrderDetails getOrderDetailById(long orderDetailId);

    void updateOrderDetails(OrderDetails updates, long orderDetailId);

    void deleteOrderDetails(long orderId);

    List<ProductAmount> getTopSoldProducts();

    List<Long> getPurchasedProductsIds();

    long getProductAmount(long productId, long orderId);

    List<Long> getAllOrdersIds();

    long[] prepareVector(long productId, List<Long> ids);

    List<Purchase> getAllCustomersStatistic();

    List<ProductStatistic> getAllProductsStatistic();

    void clear();
}
