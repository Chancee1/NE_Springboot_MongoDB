package com.practice.BackendApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.practice.BackendApp.model.Product;
import com.practice.BackendApp.model.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "purchased")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Purchased {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code")
    private Long productCode;

    private int quantity;

    private double total;

    @Column(name = "date")
    private LocalDateTime date = LocalDateTime.now();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


}