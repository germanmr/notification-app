package com.acme.notificationapp.listeners;

import com.acme.notificationapp.dto.MessageRequestDTO;
import com.acme.notificationapp.services.MessageRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AcknowledgementListener {

    private static final Logger logger = LoggerFactory.getLogger(AcknowledgementListener.class);

    private final ObjectMapper objectMapper;
    private final MessageRequestService messageRequestService;

    @Autowired
    public AcknowledgementListener(MessageRequestService messageRequestService,
                                   ObjectMapper objectMapper) {
        this.messageRequestService = messageRequestService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "com.acme.notificationapp.ack.topic", groupId = "group_id")
    public void consume(String message) throws Exception {
        logger.info("Received message - Acknowledgement:" + message);
        if (Strings.isNullOrEmpty(message)) {
            throw new Exception("The body was empty!");
        }
        MessageRequestDTO messageRequestDTO = objectMapper.readValue(message, MessageRequestDTO.class);
        logger.info("Received message - successfully deserialize entity: {}", messageRequestDTO);
        messageRequestService.updateStatus(messageRequestDTO);
        logger.info("Received message - Acknowledgement with request: {} finished successfully", messageRequestDTO);
    }
}
