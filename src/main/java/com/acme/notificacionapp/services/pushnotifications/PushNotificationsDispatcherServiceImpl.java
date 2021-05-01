package com.acme.notificacionapp.services.pushnotifications;

import com.acme.notificacionapp.repository.MessageRequestRepository;
import com.acme.notificacionapp.services.AbstractDispatcherServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationsDispatcherServiceImpl extends AbstractDispatcherServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(PushNotificationsDispatcherServiceImpl.class);

    @Autowired
    public PushNotificationsDispatcherServiceImpl(MessageRequestRepository messageRequestRepository,
                                                  KafkaTemplate<String, String> kafkaTemplate,
                                                  ObjectMapper objectMapper,
                                                  @Value("com.acme.notificationapp.push-notification.topic") String topic) {
        super(messageRequestRepository, kafkaTemplate, objectMapper);
        setTopic(topic);
    }
}
