package com.acme.notificacionapp.services;

import com.acme.notificacionapp.repository.MessageRequestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;

public abstract class AbstractDispatcherServiceImpl implements DispatcherService {

    private static final Logger logger = LoggerFactory.getLogger(AbstractDispatcherServiceImpl.class);
    private static final Long MAX_BATCH_SIZE = 30L;

    private final MessageRequestRepository messageRequestRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private String topic;

    @Autowired
    public AbstractDispatcherServiceImpl(MessageRequestRepository messageRequestRepository,
                                         KafkaTemplate<String, String> kafkaTemplate,
                                         ObjectMapper objectMapper) {
        this.messageRequestRepository = messageRequestRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Async
    @Override
    public void dispatch(Long batchSize) {
        // Calculate batch size
        Long actualBatchsize = batchSize > MAX_BATCH_SIZE ? MAX_BATCH_SIZE : batchSize;
        logger.info("Begginng batch publish - batch size: {actualBatchsize}", actualBatchsize);
        messageRequestRepository.getBatchForUpdateById(actualBatchsize)
                .forEach(messageRequest -> {
                    try {
                        logger.info("Begin Publish message: " + messageRequest.getId());
                        logger.info(String.format("#### -> Dispatching message -> %s", messageRequest));
                        String message = objectMapper.writeValueAsString(messageRequest);
                        this.kafkaTemplate.send(topic, message);
                        messageRequest.setBeginProccesing();
                        messageRequestRepository.save(messageRequest);
                        logger.info("Begin Publish message: ", messageRequest.getId());
                    } catch (Exception e) {
                        logger.error(String.format("#### -> There was an error ending a message -> %s", e.getMessage()));
                    }
                });
        logger.info("End of batch publish - batch size: " + actualBatchsize);
    }

    protected void setTopic(String topic) {
        this.topic = topic;
    }
}
