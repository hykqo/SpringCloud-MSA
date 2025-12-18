package com.example.userservice.vo;

import lombok.Builder;

import java.util.Date;

@Builder
public record ResponseOrder(
        String productId,
        Integer qty,
        Integer unitPrice,
        Integer totalPrice,
        Date createdAt,

        String orderId
) {

}
