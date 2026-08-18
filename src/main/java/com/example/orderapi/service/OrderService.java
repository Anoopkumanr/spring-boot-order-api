package com.example.orderapi.service;

import com.example.orderapi.dto.CreateOrderRequest;
import com.example.orderapi.dto.PatchOrderRequest;
import com.example.orderapi.dto.UpdateOrderRequest;
import com.example.orderapi.entity.Order;
import com.example.orderapi.entity.OrderStatus;
import com.example.orderapi.exception.OrderNotFoundException;
import com.example.orderapi.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order create(CreateOrderRequest request) {
        Order order = new Order();
        order.setCustomerName(request.customerName());
        order.setProductName(request.productName());
        order.setQuantity(request.quantity());
        order.setPrice(request.price());
        order.setStatus(request.status() == null ? OrderStatus.CREATED : request.status());
        return repository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Order getById(Long id) {
        return find(id);
    }

    public Order update(Long id, UpdateOrderRequest request) {
        Order order = find(id);
        order.setCustomerName(request.customerName());
        order.setProductName(request.productName());
        order.setQuantity(request.quantity());
        order.setPrice(request.price());
        order.setStatus(request.status());
        return repository.save(order);
    }

    public Order patch(Long id, PatchOrderRequest request) {
        Order order = find(id);

        if (request.customerName() != null) order.setCustomerName(request.customerName());
        if (request.productName() != null) order.setProductName(request.productName());
        if (request.quantity() != null) order.setQuantity(request.quantity());
        if (request.price() != null) order.setPrice(request.price());
        if (request.status() != null) order.setStatus(request.status());

        return repository.save(order);
    }

    public void delete(Long id) {
        Order order = find(id);
        repository.delete(order);
    }

    private Order find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }
}
