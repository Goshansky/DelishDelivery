package org.example.orders.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String deliveryAddress;
    private String restaurantAddress; // Новое поле для адреса ресторана

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED; // Поле статуса с значением по умолчанию


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    // Getters and Setters
}
