package com.acme.notificacionapp.services.mail;

import com.acme.notificacionapp.config.TransactionalHelper;
import com.acme.notificacionapp.domain.Medias;
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
public class MailDispatcherServiceImpl extends AbstractDispatcherServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(MailDispatcherServiceImpl.class);

    @Autowired
    public MailDispatcherServiceImpl(MessageRequestRepository messageRequestRepository,
                                     KafkaTemplate<String, String> kafkaTemplate,
                                     ObjectMapper objectMapper,
                                     @Value("${com.acme.notificationapp.mail.topic}") String topic,
                                     TransactionalHelper transactionalHelper) {
        super(messageRequestRepository, kafkaTemplate, objectMapper, transactionalHelper);
        setTopic(topic);
        setMedia(Medias.MAIL);
    }
}
