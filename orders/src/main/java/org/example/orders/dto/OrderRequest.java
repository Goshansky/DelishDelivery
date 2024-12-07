package org.example.orders.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private Long userId;
    private String deliveryAddress;
    private String restaurantAddress;
    private List<OrderItemRequest> items;

}
