package com.ecommerce.demo.config;

import com.ecommerce.demo.dto.UserDto;
import com.ecommerce.demo.entity.CartItem;
import com.ecommerce.demo.entity.Users;
import jakarta.annotation.Nullable;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.group.id}")
    private String groupId;

    @Value("${spring.kafka.group.id2}")
    private String saveGroupId;

    @Value("${spring.kafka.reply.topic}")
    private String replyTopic;

    @Value("${spring.kafka.request.topic2}")
    private String saveRequestTopic;



    @Bean
    public ReplyingKafkaTemplate<String, Object, Users> replyingKafkaTemplate(ProducerFactory<String, Object> pf,
                                                                              ConcurrentKafkaListenerContainerFactory<String, Users> factory) {
        ConcurrentMessageListenerContainer<String, Users> replyContainer = factory.createContainer(replyTopic);
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId(groupId);

        return new ReplyingKafkaTemplate<String, Object, Users>(pf, replyContainer);
    }
    @Bean
    public ReplyingKafkaTemplate<String, Object, Users> savereplyingKafkaTemplate(ProducerFactory<String, Object> pf,
                                                                                 ConcurrentKafkaListenerContainerFactory<String, Users> factory) {
        ConcurrentMessageListenerContainer<String, Users> replyContainer = factory.createContainer(saveRequestTopic);
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId(saveGroupId);

        return new ReplyingKafkaTemplate<String, Object, Users>(pf, replyContainer);
    }
    @Bean
    public KafkaTemplate<String, Users> replyTemplate(ProducerFactory<String, Users> pf,
                                                       ConcurrentKafkaListenerContainerFactory<String, Users> factory) {
        KafkaTemplate<String, Users> kafkaTemplate = new KafkaTemplate<>(pf);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setReplyTemplate(kafkaTemplate);
        return kafkaTemplate;
    }

//    @Bean
//    public KafkaTemplate<String, Users> replyTemplate(ProducerFactory<String, Users> pf,
//                                                        ConcurrentKafkaListenerContainerFactory<String, UserDto> factory) {
//        KafkaTemplate<String, Users> kafkaTemplate = new KafkaTemplate<>(pf);
//        factory.getContainerProperties().setMissingTopicsFatal(false);
//        factory.setReplyTemplate(kafkaTemplate);
//
//        return kafkaTemplate;
//    }
@Bean
public ConcurrentMessageListenerContainer<String, Object> repliesContainer(
        ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory) {
    ConcurrentMessageListenerContainer<String, Object> repliesContainer = containerFactory.createContainer("user_save");
    repliesContainer.getContainerProperties().setGroupId("CONSUMER_GROUPS");
    repliesContainer.setAutoStartup(false);
    return repliesContainer;
}

    @Bean
    public ProducerFactory<String, Users> producerFactory(){
        Map<String,Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate kafkaTemplate()
    {
        return new KafkaTemplate<>(producerFactory());
    }
    @Bean
    public ProducerFactory<String, Object> producerFactoryObject(){
        Map<String,Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }
}
