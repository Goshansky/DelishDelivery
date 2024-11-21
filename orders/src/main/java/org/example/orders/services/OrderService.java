package org.example.orders.services;

import org.example.orders.ResourceNotFoundException;
import org.example.orders.entities.*;
import org.example.orders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setOrder(order);
        }
        order = orderRepository.save(order);

        DeliveryRequest deliveryRequest = new DeliveryRequest();
        deliveryRequest.setOrderId(order.getId());
        deliveryRequest.setStatus("CREATED");
        restTemplate.postForEntity("http://localhost:8084/api/deliveries", deliveryRequest, Void.class);

        return order;
    }

    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}


