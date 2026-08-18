package com.example.orderapi.dto;

import com.example.orderapi.entity.OrderStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;

public record PatchOrderRequest(
        String customerName,
        String productName,
        @Min(1) Integer quantity,
        @DecimalMin("0.01") BigDecimal price,
        OrderStatus status
) {}
