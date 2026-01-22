package com.catalog.catalogservice.messageQueue;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");                  //카프가 서버 주소값
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumerGroupId");                          //토픽에 쌓여있는 메시지를 가져가는 컨슈머들을 그룹핑 할때 사용. 현재는 하나밖에 없으니까 의미는 없음.
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);     //json 해석용 클래스
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);   //역직렬화할때 사용한다.

        return new DefaultKafkaConsumerFactory<>(properties);
    }

    /**
     * 리스너용 빈
     *
     * 토픽에 변경사항이 생겻을 경우 해당하는 값을 캐치하기 위해 빈 등록.
     * */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();
        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());

        return kafkaListenerContainerFactory;
    }
}
