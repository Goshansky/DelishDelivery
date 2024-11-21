package org.example.orders.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.orders.entities.OrderStatus;

@Getter
@Setter
public class OrderStatusRequest {
    private OrderStatus status;
}
