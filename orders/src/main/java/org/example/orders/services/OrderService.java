package org.example.orders.services;

import org.example.orders.ResourceNotFoundException;
import org.example.orders.dto.DeliveryRequest;
import org.example.orders.dto.MenuItemDTO;
import org.example.orders.dto.OrderDTO;
import org.example.orders.dto.OrderItemDTO;
import org.example.orders.entities.Order;
import org.example.orders.entities.OrderItem;
import org.example.orders.entities.OrderStatus;
import org.example.orders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO createOrder(Order order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setOrder(order);
        }
        order = orderRepository.save(order);

        // Отправка запроса на микросервис доставки для создания новой записи
        DeliveryRequest deliveryRequest = new DeliveryRequest();
        deliveryRequest.setOrderId(order.getId());
        deliveryRequest.setStatus("NEW");
        restTemplate.postForEntity("http://localhost:8084/api/deliveries", deliveryRequest, Void.class);

        return convertToDTO(order);
    }

    public OrderDTO updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(status);
        order = orderRepository.save(order);
        return convertToDTO(order);
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setRestaurantAddress(order.getRestaurantAddress()); // Новое поле для адреса ресторана
        dto.setStatus(order.getStatus());

        List<OrderItemDTO> orderItems = order.getOrderItems().stream()
                .map(orderItem -> {
                    MenuItemDTO menuItemDTO = restTemplate.getForObject(
                            "http://localhost:8082/api/menu-items/" + orderItem.getMenuId(),
                            MenuItemDTO.class
                    );
                    OrderItemDTO orderItemDTO = new OrderItemDTO();
                    orderItemDTO.setId(orderItem.getId());
                    orderItemDTO.setName(menuItemDTO.getName());
                    orderItemDTO.setPrice(menuItemDTO.getPrice());
                    orderItemDTO.setQuantity(orderItem.getQuantity());
                    return orderItemDTO;
                })
                .collect(Collectors.toList());

        dto.setItems(orderItems);
        dto.setTotalPrice(orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum());

        return dto;
    }
}



