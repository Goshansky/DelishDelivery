package org.example.delivery.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryStatusRequest {
    private String status;
    private Long deliverer_id;
}
