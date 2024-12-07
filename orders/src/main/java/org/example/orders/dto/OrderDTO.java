package org.example.orders.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.orders.entities.OrderStatus;

import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private Long userId;
    private String deliveryAddress;
    private String restaurantAddress;
    private OrderStatus status;
    private List<OrderItemDTO> items;
    private double totalPrice;
}
