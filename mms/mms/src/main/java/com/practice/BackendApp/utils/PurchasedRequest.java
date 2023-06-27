package com.practice.BackendApp.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchasedRequest {
    private Long productCode;
    private int quantity;

}
