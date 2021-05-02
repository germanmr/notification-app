package com.acme.notificacionapp.listeners;

import com.acme.notificacionapp.dto.MessageRequestDTO;
import com.acme.notificacionapp.services.MessageRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
//@KafkaListener(topics = "${com.acme.notificationappmailsender.mail.topic}", groupId = "group_id")
public class AcknowledgementListener {

    private static final Logger logger = LoggerFactory.getLogger(AcknowledgementListener.class);

    private final ObjectMapper objectMapper;
    private String topic;
    private final MessageRequestService messageRequestService;

    @Autowired
    public AcknowledgementListener(MessageRequestService messageRequestService,
                                   ObjectMapper objectMapper) {
        this.messageRequestService = messageRequestService;
        this.objectMapper = objectMapper;
    }

    //    @KafkaListener(topics = "Kafka_Example", groupId = "group_id")
    @KafkaListener(topics = "com.acme.notificationapp.ack.topic", groupId = "group_id")
    public void consume(String message) throws Exception {
//        try {
        logger.info("Received message - Acknowledgement:" + message);
        if (Strings.isNullOrEmpty(message)) {
            throw new Exception("The body was empty!");
        }
        MessageRequestDTO messageRequestDTO = objectMapper.readValue(message, MessageRequestDTO.class);
        logger.info("Received message - succesfully deserialize entity: {messageRequestDTO}", messageRequestDTO);
//        messageRequestService.updateStatus(messageRequestDTO);
        logger.info("Received message - Acknowledgement with request: {} finished succesfully", messageRequestDTO);
//        } catch (Exception e) {
//            logger.error("ERROR - Received message - Acknowledgement: " + e.getMessage());
//        }
    }
}
