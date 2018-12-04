package com.mogydan.similarity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class ProductStatistic {

    private long orderId;

    private long productId;

    private long amount;
}
