package org.example.orders.controllers;

import org.example.orders.dto.OrderRequest;
import org.example.orders.dto.OrderStatusRequest;
import org.example.orders.entities.Order;
import org.example.orders.entities.OrderItem;
import org.example.orders.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest orderRequest) {
        Order order = new Order();
        order.setUserId(orderRequest.getUserId());
        order.setDeliveryAddress(orderRequest.getDeliveryAddress());

        List<OrderItem> orderItems = orderRequest.getItems().stream()
                .map(item -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setMenuId(item.getId());
                    orderItem.setQuantity(item.getQuantity());
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        return orderService.createOrder(order);
    }

    @PutMapping("/{orderId}/status")
    public Order updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatusRequest statusRequest) {
        return orderService.updateOrderStatus(orderId, statusRequest.getStatus());
    }
}
