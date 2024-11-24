package org.example.orders.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
    private Long id;
    private String name;
    private double price;
    private int quantity;
}
