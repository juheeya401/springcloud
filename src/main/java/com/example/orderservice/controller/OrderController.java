package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.messagequeue.OrderProducer;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/order-service")
public class OrderController {

    private final ModelMapper modelMapper;
    private final Environment env;
    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;
    private final OrderProducer orderProducer;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working! in User Service on PORT %s"
                , env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId, @RequestBody RequestOrder orderDetails) {
        log.info("Before retrieve order data");

        OrderDto requestOrder = modelMapper.map(orderDetails, OrderDto.class);
        requestOrder.setUserId(userId);

        // jpa 작업 - kafka topic 으로 전달하므로 제거
        OrderDto resultOrder = orderService.createOrder(requestOrder);

        /*// send this order to the kafka
        requestOrder.setOrderId(UUID.randomUUID().toString());
        requestOrder.setTotalPrice(requestOrder.getUnitPrice() * requestOrder.getQty());
        kafkaProducer.send("example-catalog-topic", requestOrder);
        orderProducer.send("orders", requestOrder);*/

        ResponseOrder responseOrder = modelMapper.map(requestOrder, ResponseOrder.class);
        log.info("After retrieve order data");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    // http://127.0.0.1/order-service/{user_id}/orders/
    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrders(@PathVariable("userId") String userId) throws Exception {
        log.info("Before retrieve order data");
        List<OrderDto> orders = orderService.getOrderByUserId(userId);
        List<ResponseOrder> resultDatas = orders.stream().map(e -> modelMapper.map(e, ResponseOrder.class))
                .collect(Collectors.toList());
        try {
            Thread.sleep(1000);
            throw new Exception("장애발생");
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
        }
        log.info("After retrieve order data");
        return ResponseEntity.ok(resultDatas);
    }
}
