package com.orderservice.dto;

import com.orderservice.entity.OrderEntity;
import com.orderservice.vo.RequestOrder;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDto{
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private String orderId;
    private String userId;

    public static OrderDto of(OrderEntity orderEntity){
        OrderDto orderDto = new OrderDto();
        orderDto.setProductId(orderEntity.getProductId());
        orderDto.setQty(orderEntity.getQty());
        orderDto.setUnitPrice(orderEntity.getUnitPrice());
        orderDto.setTotalPrice(orderEntity.getTotalPrice());
        orderDto.setOrderId(orderEntity.getOrderId());
        orderDto.setUserId(orderEntity.getUserId());
        return orderDto;
    }

    public static OrderDto of(RequestOrder order, String userId){
        OrderDto orderDto = new OrderDto();
        orderDto.setProductId(order.getProductId());
        orderDto.setQty(order.getQty());
        orderDto.setUnitPrice(order.getUnitPrice());
        orderDto.setUserId(userId);
        orderDto.setTotalPrice(order.getQty() * order.getUnitPrice());
        return orderDto;
    }
}
