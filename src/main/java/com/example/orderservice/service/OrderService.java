package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);

    OrderDto getOrderByOrderId(String orderId) throws ChangeSetPersister.NotFoundException;

    List<OrderDto> getOrderByUserId(String userId);
}
