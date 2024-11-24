package org.example.orders.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private RestaurantDTO restaurant;
}
