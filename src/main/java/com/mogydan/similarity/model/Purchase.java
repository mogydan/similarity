package com.mogydan.similarity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class Purchase {
    private long customerId;
    private long productId;
    private long productAmount;
}
