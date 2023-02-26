package com.acme.notificationapp.services.pushnotifications;

import com.acme.notificationapp.config.TransactionalHelper;
import com.acme.notificationapp.domain.Medias;
import com.acme.notificationapp.repository.MessageRequestReadRepository;
import com.acme.notificationapp.services.AbstractDispatcherServiceImpl;
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
    public PushNotificationsDispatcherServiceImpl(MessageRequestReadRepository messageRequestReadRepository,
                                                  KafkaTemplate<String, String> kafkaTemplate,
                                                  ObjectMapper objectMapper,
                                                  @Value("com.acme.notificationapp.push-notification.topic") String topic,
                                                  TransactionalHelper transactionalHelper) {
        super(messageRequestReadRepository, kafkaTemplate, objectMapper, transactionalHelper);
        setTopic(topic);
        setMedia(Medias.PUSH_NOTIFICATION);
    }
}
