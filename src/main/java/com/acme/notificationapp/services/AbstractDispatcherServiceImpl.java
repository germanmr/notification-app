package com.acme.notificationapp.services;

import com.acme.notificationapp.config.TransactionalHelper;
import com.acme.notificationapp.domain.Medias;
import com.acme.notificationapp.domain.MessageRequest;
import com.acme.notificationapp.repository.MessageRequestReadRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;

public abstract class AbstractDispatcherServiceImpl implements DispatcherService {

    private static final Logger logger = LoggerFactory.getLogger(AbstractDispatcherServiceImpl.class);
    private static final Long MAX_BATCH_SIZE = 30L;

    private final MessageRequestReadRepository messageRequestReadRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private String topic;
    private Medias MEDIA_TYPE;
    protected final TransactionalHelper transactionalHelper;

    @Autowired
    public AbstractDispatcherServiceImpl(MessageRequestReadRepository messageRequestReadRepository,
                                         KafkaTemplate<String, String> kafkaTemplate,
                                         ObjectMapper objectMapper,
                                         TransactionalHelper transactionalHelper) {
        this.messageRequestReadRepository = messageRequestReadRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.transactionalHelper = transactionalHelper;
    }

    //    @Async
    @Override
    @Transactional
    // This transation rollsback postgresSql and kafka!
    // Tratamos de tener lotes un poco mas grandes y esperar menos errores
    // Para lotes mas chicos esperamos muchos errores
    public void dispatch(Long batchSize) {
        Long actualBatchSize = batchSize > MAX_BATCH_SIZE ? MAX_BATCH_SIZE : batchSize;
        logger.info("Begin batch publish - batch size: {}", actualBatchSize);
        messageRequestReadRepository.getBatchForUpdateById(actualBatchSize)
                .forEach(messageRequest -> {
                    try {
                        logger.info("Begin Publish message: " + messageRequest.getId());
                        messageRequest.setBeginProcessing();
                        messageRequestReadRepository.save(messageRequest);
                        logger.info(String.format("#### -> Dispatching message -> %s", messageRequest));
                        logger.info("Before Kafka exception");
                        String message = objectMapper.writeValueAsString(messageRequest);
                        this.kafkaTemplate.send(topic, message);
                        logger.info("End Publish message: ", messageRequest.getId());
                    } catch (Exception e) {
                        // If we got an exception the ones that have been sent are still committed and marked as PROCESSED
                        // The error one should be back to its state
                        messageRequest.setBeginProcessing();
                        // Si tenemos un error aqui el mensaje ya fue
                        // enviado pero volvemos la transaccion para atras?
                        messageRequestReadRepository.save(messageRequest);
                        logger.error(String.format("#### -> There was an error sending the message -> %s", e.getMessage()));
                    }
                });
        logger.info("End of batch publish - batch size: " + actualBatchSize);
    }

    //    @Async
    @org.springframework.transaction.annotation.Transactional( transactionManager ="kafkaTransactionManager" ,propagation = Propagation.REQUIRED)
    @Override
    public void dispatchOne()  {
        transactionalHelper.executeInKafkaTransaction(() -> {
            logger.info("Begin single dispatch publish ");
            MessageRequest nextMessage = messageRequestReadRepository.getNextMessageForUpdate();
            if (nextMessage == null) {
                logger.info("No new next message to publish");
                return;
            }
            logger.info("Next message retrieved: {}", nextMessage);
//            nextMessage.setBeginProccesing();
//            messageRequestRepository.save(nextMessage);
            logger.info(String.format("#### -> Dispatching message -> %s", nextMessage));
            String message = null;
            try {
                message = objectMapper.writeValueAsString(nextMessage);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            kafkaTemplate.send(topic, message);
            logger.info("End of dispacth one");
        });
    }

    @Override
    @org.springframework.transaction.annotation.Transactional( propagation = Propagation.REQUIRED)
    public void dispatchOneKafka() {
        logger.info("Begin dispatchOneKafka");
        String message = "{\"id\":666,\"uuid\":\"c98806fd-3cdf-4697-be0e-50374615e932\",\"client\":{\"name\":\"JUAN\",\"favoriteMedia\":\"MAIL\",\"favoriteMediaIdentifier\":\"juan@hotmail.com\"},\"publication\":{\"messages\":\"DESCUENTOS\\nPROMOCIONES\\nSE CIERRA MAS TEMPRANO EL VIERNES\"},\"error\":null,\"messageState\":\"PROCESSING\"}";
        kafkaTemplate.send(topic, message);
        message = "{\"id\":999,\"uuid\":\"c98806fd-3cdf-4697-be0e-50374615e932\",\"client\":{\"name\":\"JUAN\",\"favoriteMedia\":\"MAIL\",\"favoriteMediaIdentifier\":\"juan@hotmail.com\"},\"publication\":{\"messages\":\"DESCUENTOS\\nPROMOCIONES\\nSE CIERRA MAS TEMPRANO EL VIERNES\"},\"error\":null,\"messageState\":\"PROCESSING\"}";
        kafkaTemplate.send(topic, message);
        logger.info("End of dispacth one");
        throw new RuntimeException("This is a test exception");
    }

    @Override
    public void dispatchOneWithTransaction() throws Exception {
        transactionalHelper.executeInTransaction(()->{
            logger.info("Begin dispatchOneWithTransaction");
            String message = "{\"id\":666,\"uuid\":\"c98806fd-3cdf-4697-be0e-50374615e932\",\"client\":{\"name\":\"JUAN\",\"favoriteMedia\":\"MAIL\",\"favoriteMediaIdentifier\":\"juan@hotmail.com\"},\"publication\":{\"messages\":\"DESCUENTOS\\nPROMOCIONES\\nSE CIERRA MAS TEMPRANO EL VIERNES\"},\"error\":null,\"messageState\":\"PROCESSING\"}";
            kafkaTemplate.send(topic, message);
//            throw new RuntimeException("This is a test exception");
        });
    }

    protected void setMedia(Medias media) {
        this.MEDIA_TYPE = media;
    }

    protected void setTopic(String topic) {
        this.topic = topic;
    }
}
