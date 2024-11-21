package org.example.orders.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryRequest {
    private Long orderId;
    private String status;
}
