package com.mogydan.similarity.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@Accessors(chain = true)
@EqualsAndHashCode(exclude = "id")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id", updatable = false, nullable = false)
    private Long id;

    private String name;

    private String email;
}
