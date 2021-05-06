package com.acme.notificacionapp.services;

import com.acme.notificacionapp.domain.Medias;
import com.acme.notificacionapp.domain.MessageRequest;
import com.acme.notificacionapp.repository.MessageRequestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import javax.transaction.Transactional;

public abstract class AbstractDispatcherServiceImpl implements DispatcherService {

    private static final Logger logger = LoggerFactory.getLogger(AbstractDispatcherServiceImpl.class);
    private static final Long MAX_BATCH_SIZE = 30L;

    private final MessageRequestRepository messageRequestRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private String topic;
    private Medias MEDIA_TYPE;

    @Autowired
    public AbstractDispatcherServiceImpl(MessageRequestRepository messageRequestRepository,
                                         KafkaTemplate<String, String> kafkaTemplate,
                                         ObjectMapper objectMapper) {
        this.messageRequestRepository = messageRequestRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    //    @Async
    @Override
    @Transactional
    // This transation rollsback postgresSql and kafka!
    // Tratamos de tener lotes un poco mas grandes y esperar menos errores
    // Para lotes mas chicos esperamos muchos errores
    public void dispatch(Long batchSize) {
        Long actualBatchsize = batchSize > MAX_BATCH_SIZE ? MAX_BATCH_SIZE : batchSize;
        logger.info("Begin batch publish - batch size: {}", actualBatchsize);
        messageRequestRepository.getBatchForUpdateById(actualBatchsize)
                .forEach(messageRequest -> {
                    try {
                        logger.info("Begin Publish message: " + messageRequest.getId());
                        messageRequest.setBeginProccesing();
                        messageRequestRepository.save(messageRequest);
                        logger.info(String.format("#### -> Dispatching message -> %s", messageRequest));
                        logger.info("Before Kafka exception");
                        String message = objectMapper.writeValueAsString(messageRequest);
                        this.kafkaTemplate.send(topic, message);
                        logger.info("End Publish message: ", messageRequest.getId());
                    } catch (Exception e) {
                        // If we got an exception the ones that have been send are still commited and marked as PROCESSED
                        // The error one should be back to its state
                        messageRequest.setBeginProccesing();
                        // Si tenemos un error aqui el mensaje ya fue
                        // enviado pero volvemos la transaccion para atras?
                        messageRequestRepository.save(messageRequest);
                        logger.error(String.format("#### -> There was an error sending the message -> %s", e.getMessage()));
                    }
                });
        logger.info("End of batch publish - batch size: " + actualBatchsize);
    }

    //    @Async
    @Transactional
    @Override
    public void dispatchOne() throws Exception {
        logger.info("Begin single dispatch publish ");
        MessageRequest nextMessage = messageRequestRepository.getNextMessageForUpdate();
        if (nextMessage == null) {
            logger.info("No new next message to publish");
            return;
        }
        logger.info("Next message retrieved: {}", nextMessage);
        nextMessage.setBeginProccesing();
        messageRequestRepository.save(nextMessage);
        logger.info(String.format("#### -> Dispatching message -> %s", nextMessage));
        String message = objectMapper.writeValueAsString(nextMessage);
        kafkaTemplate.send(topic, message);
        logger.info("End Publish single message: ", nextMessage.getId());
    }

    @Override
    public void dispatchOneWithTransaction() throws Exception {

    }

    protected void setMedia(Medias media) {
        this.MEDIA_TYPE = media;
    }

    protected void setTopic(String topic) {
        this.topic = topic;
    }
}
