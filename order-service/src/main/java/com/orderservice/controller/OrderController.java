package com.orderservice.controller;

import com.orderservice.dto.OrderDto;
import com.orderservice.entity.OrderEntity;
import com.orderservice.service.OrderService;
import com.orderservice.vo.RequestOrder;
import com.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/order-service")
public class OrderController {

    private final OrderService orderService;
    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Order Service on LOCAL PORT %s (SERVER PORT %s)",
                env.getProperty("local.server.port"),
                env.getProperty("server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderDto> createOrder(@PathVariable String userId, @RequestBody RequestOrder requestOrder) {
        log.info("Before add orders data");
        OrderDto orderDto = OrderDto.of(requestOrder, userId);

        OrderDto order = orderService.createOrder(orderDto);
        log.info("After added orders data");
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable String userId) {
        log.info("Before retrieve orders data");
        Iterable<OrderEntity> ordersByUserId = orderService.getOrdersByUserId(userId);
        List<ResponseOrder> from = ResponseOrder.from(ordersByUserId);
        return ResponseEntity.ok().body(from);
    }


}
