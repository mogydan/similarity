package com.mogydan.similarity.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Accessors(chain = true)
@EqualsAndHashCode(exclude = "id")
public class Product {

    @Id
    @GeneratedValue(generator = "product_increment")
    @GenericGenerator(name = "product_increment", strategy = "increment")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "PRICE")
    private double price;
}


