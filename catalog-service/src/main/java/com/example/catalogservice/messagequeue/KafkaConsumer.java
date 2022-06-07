package com.example.catalogservice.messagequeue;

import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.jpa.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * kafka 에서 데이터 수신 & DB 업데이트
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class KafkaConsumer {

    private final CatalogRepository catalogRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 지정한 토픽을 수신하는 메서드
     */
    @Transactional
    @KafkaListener(topics = "example-catalog-topic")
    public void updateQuantity(String kafkaMessage) {
        log.info("kafka Message -> " + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        try {
            // String 타입의 데이터를 -> Map<Object, Object> 객체로 변환
            map = objectMapper.readValue(kafkaMessage, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Optional<CatalogEntity> productOptional = catalogRepository.findAllByProductId((String) map.get("productId"));
        if (productOptional.isPresent()) {
            CatalogEntity product = productOptional.get();
            int quantity = (Integer) map.get("quantity");
            product.minusQuantity(quantity);
        }
    }
}
