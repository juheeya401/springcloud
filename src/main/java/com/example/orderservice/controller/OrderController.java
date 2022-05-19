package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order-service")
public class OrderController {

    private final ModelMapper modelMapper;
    private final Environment env;
    private final OrderService orderService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working! in User Service on PORT %s"
                , env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId, @RequestBody RequestOrder orderDetails) {
        OrderDto requestOrder = modelMapper.map(orderDetails, OrderDto.class);
        requestOrder.setUserId(userId);
        OrderDto resultOrder = orderService.createOrder(requestOrder);
        ResponseOrder responseOrder = modelMapper.map(resultOrder, ResponseOrder.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    // http://127.0.0.1/order-service/{user_id}/orders/
    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrders(@PathVariable("userId") String userId) {
        List<OrderDto> orders = orderService.getOrderByUserId(userId);
        List<ResponseOrder> resultDatas = orders.stream().map(e -> modelMapper.map(e, ResponseOrder.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultDatas);
    }
}
