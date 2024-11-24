package org.example.menu.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDTO {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
}
