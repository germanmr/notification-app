package com.acme.notificacionapp.services;

import com.acme.notificacionapp.domain.MessageRequest;
import com.acme.notificacionapp.repository.MessageRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DispatcherServiceImpl implements DispatcherService {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServiceImpl.class);
    private static final Long MAX_BATCH_SIZE = 500L;

    private final MessageRequestRepository messageRequestRepository;
    private final KafkaTemplate<String, MessageRequest> kafkaTemplate;

    @Autowired
    public DispatcherServiceImpl(MessageRequestRepository messageRequestRepository, KafkaTemplate<String, MessageRequest> kafkaTemplate) {
        this.messageRequestRepository = messageRequestRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    private Long calculateBatchSize() {
        long count = messageRequestRepository.count();
        if (count > 0 & count * 0.01 < MAX_BATCH_SIZE) {
            return Math.round(count * 0.01);
        } else {
            return MAX_BATCH_SIZE;
        }
    }

    @Async
    @Override
    public void dispatch(String topic) {
        // Calculate batch size
        Long batch_size = calculateBatchSize();
        messageRequestRepository.getBatchForUpdateById(batch_size)
                .forEach(messageRequest -> {
                    try {
                        logger.info(String.format("#### -> Dispatching message -> %s", messageRequest));
                        this.kafkaTemplate.send(topic, messageRequest);
                        messageRequest.setBeginProccesing();
                        messageRequestRepository.save(messageRequest);
                    } catch (Exception e) {
                        logger.info(String.format("#### -> There was an error ending a message -> %s", e.getMessage()));
                    }
                });
    }

}
