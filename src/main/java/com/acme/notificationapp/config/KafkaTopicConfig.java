package com.acme.notificationapp.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Profile(Profiles.LOCAL)
@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("com.acme.notificationapp.mail.topic").partitions(3).build();
    }
}
