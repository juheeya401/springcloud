package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.jpa.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;


    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getUnitPrice() * orderDto.getQty());

        OrderEntity orderEntity = modelMapper.map(orderDto, OrderEntity.class);
        OrderEntity resultEntity = orderRepository.save(orderEntity);

        return modelMapper.map(resultEntity, OrderDto.class);
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) throws ChangeSetPersister.NotFoundException {
        OrderEntity order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public List<OrderDto> getOrderByUserId(String userId) {
        Iterable<OrderEntity> ordersByUser = orderRepository.findAllByUserId(userId);
        List<OrderDto> result = new ArrayList<>();
        for (OrderEntity orderEntity : ordersByUser) {
            result.add(modelMapper.map(orderEntity, OrderDto.class));
        }
        return result;
    }
}
