package com.mogydan.similarity.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class ProductAmount {

    @NotNull
    private String productId;

    @NotNull
    private long amount;
}
