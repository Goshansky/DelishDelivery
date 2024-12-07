package org.example.delivery.services;

import org.example.delivery.ResourceNotFoundException;
import org.example.delivery.entities.*;
import org.example.delivery.repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Delivery updateDeliveryStatus(Long deliveryId, DeliveryStatusRequest statusRequest) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));
        delivery.setStatus(statusRequest.getStatus());
        delivery.setDelivererId(statusRequest.getDeliverer_id()); // Установка идентификатора доставщика

        // Вызов микросервиса заказов для обновления статуса заказа
        if (statusRequest.getStatus().equals("DELIVERY")) {
            OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
            orderStatusRequest.setStatus(OrderStatus.PROCESSING);
            restTemplate.put("http://localhost:8083/api/orders/" + delivery.getOrderId() + "/status", orderStatusRequest);
        }
        if (statusRequest.getStatus().equals("DELIVERED")) {
            OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
            orderStatusRequest.setStatus(OrderStatus.COMPLETED);
            restTemplate.put("http://localhost:8083/api/orders/" + delivery.getOrderId() + "/status", orderStatusRequest);
        }

        return deliveryRepository.save(delivery);
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public void createDelivery(DeliveryRequest deliveryRequest) {
        Delivery delivery = new Delivery();
        delivery.setOrderId(deliveryRequest.getOrderId());
        delivery.setStatus(deliveryRequest.getStatus());
        deliveryRepository.save(delivery);
    }
}
