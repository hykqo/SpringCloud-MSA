package com.example.userservice.client;

import com.example.userservice.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//order-service의 애플리케이션명을 지정
@FeignClient("order-service")
public interface OrderServiceClient {

    @GetMapping("/order-service/{userId}/orders")
    List<ResponseOrder> getOrdersByUserId(@PathVariable String userId);

}
