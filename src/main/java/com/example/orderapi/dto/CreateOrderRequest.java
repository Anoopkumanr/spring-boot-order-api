package com.example.orderapi.dto;

import com.example.orderapi.entity.OrderStatus;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CreateOrderRequest(
        @NotBlank String customerName,
        @NotBlank String productName,
        @NotNull @Min(1) Integer quantity,
        @NotNull @DecimalMin("0.01") BigDecimal price,
        OrderStatus status
) {}
