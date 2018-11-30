package com.mogydan.similarity.repository;

import com.mogydan.similarity.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    Optional<PurchaseOrder> findById(Long orderId);

    @Query("select distinct customer.id from PurchaseOrder ")
    List<Long> getBuyersIds();

    @Query("select id from PurchaseOrder where customer.id = ?1")
    List<Long> getCustomerOrdersIds(long customerId);
}
