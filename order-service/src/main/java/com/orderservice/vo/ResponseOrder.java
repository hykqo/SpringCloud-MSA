package com.orderservice.vo;

import com.orderservice.entity.OrderEntity;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
public record ResponseOrder(
         String productId,
         Integer qty,
         Integer unitPrice,
         Integer totalPrice,
         Date createdAt,
         String orderId
) {

    public static ResponseOrder of(OrderEntity o){
        return ResponseOrder.builder()
                .productId(o.getProductId())
                .qty(o.getQty())
                .unitPrice(o.getUnitPrice())
                .totalPrice(o.getTotalPrice())
                .createdAt(o.getCreatedAt())
                .orderId(o.getOrderId())
                .build();
    }

    public static List<ResponseOrder> from(Iterable<OrderEntity> orders){
        List<ResponseOrder> from = new ArrayList<>();
        orders.forEach(o -> from.add(ResponseOrder.of(o)));
        return from;
    }
}
