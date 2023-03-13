package com.acme.notificationapp.services.elasticsearch;

import com.acme.notificationapp.domain.MessageRequestEvent;
import com.acme.notificationapp.events.MessageRequestCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static com.acme.notificationapp.TestData.*;

@SpringBootTest
public class MessageRequestEventRepositoryServiceTest {

    @Autowired
    private MessageRequestEventRepositoryService repository;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void createMessageRequestEventIndex() throws JsonProcessingException {
        // given
        UUID messageRequestId = UUID.randomUUID();

        MessageRequestCreatedEvent createdEvent = exampleMessageRequestCreatedEvent(messageRequestId);

        String eventType = createdEvent.getClass().toString();

        String createdEventJson = mapper.writeValueAsString(createdEvent);

        MessageRequestEvent messageRequestEvent = new MessageRequestEvent(createdEvent.getMessageRequestId().toString(),
                createdEventJson,
                eventType);

        MessageRequestEvent actual = repository.index(messageRequestEvent);

        MessageRequestEvent expected = new MessageRequestEvent(messageRequestId.toString(),
                createdEventJson,
                eventType);

        Assert.assertEquals(expected, actual);

        MessageRequestCreatedEvent desEvent = mapper.readValue(actual.getEvent(), MessageRequestCreatedEvent.class);
        Assert.assertEquals(createdEvent, desEvent);
    }

    @Test
    void findEventsByMessageRequest() {
    }
}