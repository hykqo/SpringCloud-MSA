package com.orderservice.service;

import com.orderservice.dto.OrderDto;
import com.orderservice.entity.OrderEntity;
import com.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDetail) {
        orderDetail.setTotalPrice(orderDetail.getQty() * orderDetail.getUnitPrice());
        OrderEntity orderEntity = OrderEntity.CREATE_ORDER(orderDetail);
        orderRepository.save(orderEntity);
        orderDetail.setOrderId(orderEntity.getOrderId());
        return orderDetail;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity byOrderId = orderRepository.findByOrderId(orderId).orElseThrow(() -> new RuntimeException("조회된 주문내역이 없습니다."));
        return OrderDto.of(byOrderId);
    }

    @Override
    public Iterable<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
