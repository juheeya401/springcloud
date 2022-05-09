package com.example.orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {

    private String orderId;

    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    private LocalDateTime createdAt;

}
