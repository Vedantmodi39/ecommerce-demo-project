package com.ecommerce.demo.config;

import com.ecommerce.demo.dto.UserDto;
import com.ecommerce.demo.entity.CartItem;
import com.ecommerce.demo.entity.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import java.util.List;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.request.topic3}")
    private String replyTopic;

    @Value("${spring.kafka.group.id3}")
    private String groupId;

    @Bean
    public ReplyingKafkaTemplate<String,Object, List<CartItem>> replyingKafkaTemplate(ProducerFactory<String,Object> pf, ConcurrentKafkaListenerContainerFactory<String,List<CartItem>> factory){

        ConcurrentMessageListenerContainer<String,List<CartItem>> replyContainer = factory.createContainer(replyTopic);
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId(groupId);

        return new ReplyingKafkaTemplate<String, Object, List<CartItem>>(pf,replyContainer);
    }
    @Bean
    public KafkaTemplate<String, List<CartItem>> replyTemplate(ProducerFactory<String, List<CartItem>> pf,
                                                       ConcurrentKafkaListenerContainerFactory<String, List<CartItem>> factory) {
        KafkaTemplate<String, List<CartItem>> kafkaTemplate = new KafkaTemplate<>(pf);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setReplyTemplate(kafkaTemplate);
        return kafkaTemplate;
    }
}
