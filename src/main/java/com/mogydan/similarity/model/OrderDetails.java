package com.mogydan.similarity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@Accessors(chain = true)
@EqualsAndHashCode(exclude = "id")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PURCHASE_ORDER_ID", nullable = false, foreignKey = @ForeignKey(name = "ORD_FK"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false, foreignKey = @ForeignKey(name = "PROD_FK"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product product;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private long amount;

    public long getProductAmount(Long productId) {
        return productId == product.getId() ? amount : 0;
    }

    public long getPurchaseOrderId() {
        return purchaseOrder.getId();
    }

    public long getProductId() {
        return product.getId();
    }
}
