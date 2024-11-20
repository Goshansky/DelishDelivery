package org.example.orders.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {
    private Long id;
    private int quantity;

    // Getters and Setters
}
