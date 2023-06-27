package com.practice.BackendApp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotNull(message = "Product Name is required")
    private String name;

    @NotNull(message = "Product type is required")
    private String productType;

    @NotNull(message = "Price is required")
    private double price;

    @NotNull(message = "Product in date is required")
    @PastOrPresent(message = "Product in date must be a past or present date")
    private Date inDate;

    @Column(columnDefinition = "INT DEFAULT '0'")
    private int quantity;
}