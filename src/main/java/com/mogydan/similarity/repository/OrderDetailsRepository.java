package com.mogydan.similarity.repository;

import com.mogydan.similarity.model.OrderDetails;
import com.mogydan.similarity.model.ProductAmount;
import com.mogydan.similarity.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findByPurchaseOrder(PurchaseOrder order);

    Optional<OrderDetails> findById(Long orderDetailId);

    @Query(
            value = "select new com.mogydan.similarity.model.ProductAmount(product.id,  sum(amount))  " +
                    "from OrderDetails group by product.id order by sum(amount) desc"
    )
    List<ProductAmount> getTopSoldProducts();

    Optional<OrderDetails> findByPurchaseOrder_IdAndProduct_Id(Long orderId, Long productId);

    @Query(
            value = "select distinct product.id from OrderDetails"
    )
    List<Long> getPurchasedProductsIds();
}
