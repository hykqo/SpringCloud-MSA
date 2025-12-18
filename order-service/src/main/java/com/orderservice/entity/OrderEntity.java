package com.orderservice.entity;

import com.orderservice.dto.OrderDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @Column(nullable = false, length = 120, unique = true)
    @Column(nullable = false, length = 120)
    private String productId;
    @Column(nullable = false)
    private Integer qty;
    @Column(nullable = false)
    private Integer unitPrice;
    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private String userId;
    @Column(nullable = false, unique = true)
    private String orderId;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;


    public static OrderEntity CREATE_ORDER(OrderDto orderDetail) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.orderId = UUID.randomUUID().toString();
        orderEntity.productId = orderDetail.getProductId();
        orderEntity.qty = orderDetail.getQty();
        orderEntity.unitPrice = orderDetail.getUnitPrice();
        orderEntity.totalPrice = orderDetail.getTotalPrice();
        orderEntity.userId = orderDetail.getUserId();
        return orderEntity;
    }
}
