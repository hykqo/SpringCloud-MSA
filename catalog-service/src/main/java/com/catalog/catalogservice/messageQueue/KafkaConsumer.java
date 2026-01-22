package com.catalog.catalogservice.messageQueue;

import com.catalog.catalogservice.entity.CatalogEntity;
import com.catalog.catalogservice.repository.CatalogRespository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final CatalogRespository repository;

    //여기서 지정한 토픽을 캐치해서 기능을 수행한다.
    @KafkaListener(topics = "example-catalog-topic")
    public void updateQty(String kafkaMessage) {
        log.info("Kafka Message: ->" + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<>() {});
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        //재고수량 업데이트
        CatalogEntity entity = repository.findByProductId((String)map.get("productId"));
        if (entity != null) {
            entity.setStock(entity.getStock() - (Integer)map.get("qty"));
            repository.save(entity);
        }
    }
}
