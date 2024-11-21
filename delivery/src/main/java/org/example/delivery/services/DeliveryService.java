package org.example.delivery.services;

import org.example.delivery.ResourceNotFoundException;
import org.example.delivery.entities.Delivery;
import org.example.delivery.entities.DeliveryRequest;
import org.example.delivery.entities.OrderStatus;
import org.example.delivery.entities.OrderStatusRequest;
import org.example.delivery.repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private RestTemplate restTemplate;


    public void createDelivery(DeliveryRequest deliveryRequest) {
        Delivery delivery = new Delivery();
        delivery.setOrderId(deliveryRequest.getOrderId());
        delivery.setStatus(deliveryRequest.getStatus());
        deliveryRepository.save(delivery);
    }


    public Delivery updateDeliveryStatus(Long deliveryId, String status) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));
        delivery.setStatus(status);

        // Вызов микросервиса заказов для обновления статуса заказа
        if (status.equals("DELIVERE")) {
            OrderStatusRequest statusRequest = new OrderStatusRequest();
            statusRequest.setStatus(OrderStatus.PROCESSING);
            restTemplate.put("http://localhost:8083/api/orders/" + delivery.getOrderId() + "/status", statusRequest);
        }
        if (status.equals("DELIVERED")) {
            OrderStatusRequest statusRequest = new OrderStatusRequest();
            statusRequest.setStatus(OrderStatus.COMPLETED);
            restTemplate.put("http://localhost:8083/api/orders/" + delivery.getOrderId() + "/status", statusRequest);
        }

        return deliveryRepository.save(delivery);
    }
}
