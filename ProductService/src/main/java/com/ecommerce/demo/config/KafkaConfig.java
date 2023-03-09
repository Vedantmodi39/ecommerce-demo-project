package com.ecommerce.demo.config;

import com.ecommerce.demo.dto.UserDto;
import com.ecommerce.demo.entity.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.group.id}")
    private String groupId;

    @Value("${spring.kafka.reply.topic}")
    private String replyTopic;

    @Bean
    public ReplyingKafkaTemplate<String, Object, Users> replyingKafkaTemplate(ProducerFactory<String, Object> pf,
                                                                              ConcurrentKafkaListenerContainerFactory<String, Users> factory) {
        ConcurrentMessageListenerContainer<String, Users> replyContainer = factory.createContainer(replyTopic);
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId(groupId);

        return new ReplyingKafkaTemplate<String, Object, Users>(pf, replyContainer);
    }

    @Bean
    public KafkaTemplate<String, Users> replyTemplate(ProducerFactory<String, Users> pf,
                                                        ConcurrentKafkaListenerContainerFactory<String, UserDto> factory) {
        KafkaTemplate<String, Users> kafkaTemplate = new KafkaTemplate<>(pf);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setReplyTemplate(kafkaTemplate);

        return kafkaTemplate;
    }
}
