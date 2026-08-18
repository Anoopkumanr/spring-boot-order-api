package com.example.orderapi.controller;

import com.example.orderapi.dto.CreateOrderRequest;
import com.example.orderapi.dto.PatchOrderRequest;
import com.example.orderapi.dto.UpdateOrderRequest;
import com.example.orderapi.entity.Order;
import com.example.orderapi.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Order> create(@Valid @RequestBody CreateOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping
    public List<Order> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Order update(@PathVariable Long id,
                        @Valid @RequestBody UpdateOrderRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}")
    public Order patch(@PathVariable Long id,
                       @Valid @RequestBody PatchOrderRequest request) {
        return service.patch(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
