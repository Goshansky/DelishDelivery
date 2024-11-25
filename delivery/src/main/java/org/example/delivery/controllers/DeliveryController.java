package org.example.delivery.controllers;

import org.example.delivery.entities.Delivery;
import org.example.delivery.entities.DeliveryRequest;
import org.example.delivery.entities.DeliveryStatusRequest;
import org.example.delivery.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping
    public List<Delivery> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @PostMapping
    public ResponseEntity<Void> createDelivery(@RequestBody DeliveryRequest deliveryRequest) {
        deliveryService.createDelivery(deliveryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{deliveryId}/status")
    public Delivery updateDeliveryStatus(@PathVariable Long deliveryId, @RequestBody DeliveryStatusRequest statusRequest) {
        return deliveryService.updateDeliveryStatus(deliveryId, statusRequest);
    }
}