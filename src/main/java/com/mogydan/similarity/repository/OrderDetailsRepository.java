package com.mogydan.similarity.repository;

import com.mogydan.similarity.model.OrderDetails;
import com.mogydan.similarity.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findByPurchaseOrder(PurchaseOrder order);
    Optional<OrderDetails> findById(Long orderDetailId);
}
