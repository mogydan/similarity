package com.mogydan.similarity.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class ProductAmount {

    @NotNull
    private long productId;

    @NotNull
    private long amount;
}
