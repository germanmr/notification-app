package com.acme.notificationapp.services.elasticsearch;

import com.acme.notificationapp.domain.MessageRequestEvent;
import com.acme.notificationapp.events.MessageRequestClientUpdatedEvent;
import com.acme.notificationapp.events.MessageRequestCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.acme.notificationapp.TestData.exampleMessageRequestClientUpdatedEvent;
import static com.acme.notificationapp.TestData.exampleMessageRequestCreatedEvent;

@SpringBootTest
public class MessageRequestEventRepositoryServiceTest {

    @Autowired
    private MessageRequestEventRepositoryService repository;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void beforeEach() {
        repository.deleteIndex();
    }

    @Test
    void createMessageRequestEventIndex() throws JsonProcessingException {
        // given
        UUID messageRequestId = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();
        Date created = new Date();
        MessageRequestCreatedEvent createdEvent = exampleMessageRequestCreatedEvent(messageRequestId);
        String eventType = createdEvent.getClass().toString();
        String createdEventJson = mapper.writeValueAsString(createdEvent);
        MessageRequestEvent messageRequestEvent = new MessageRequestEvent(eventId.toString(),
                createdEventJson,
                createdEvent.getMessageRequestId().toString(),
                eventType,
                created);
        MessageRequestEvent actual = repository.addEvent(messageRequestEvent);

        MessageRequestEvent expected = new MessageRequestEvent(eventId.toString(),
                createdEventJson,
                createdEvent.getMessageRequestId().toString(),
                eventType,
                created);

        Assert.assertEquals(expected, actual);
        MessageRequestCreatedEvent desEvent = mapper.readValue(actual.getEvent(), MessageRequestCreatedEvent.class);
        Assert.assertEquals(createdEvent, desEvent);
    }

    @Test
    void findEventsByMessageRequest() throws JsonProcessingException {
        // given

        UUID messageRequestId = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();
        MessageRequestCreatedEvent createdEvent = exampleMessageRequestCreatedEvent(messageRequestId);
        MessageRequestEvent messageRequestEvent = new MessageRequestEvent(eventId.toString(),
                mapper.writeValueAsString(createdEvent),
                createdEvent.getMessageRequestId().toString(),
                createdEvent.getClass().toString(),
                new Date());
        repository.addEvent(messageRequestEvent);

        eventId = UUID.randomUUID();
        MessageRequestClientUpdatedEvent messageRequestClientUpdatedEvent = exampleMessageRequestClientUpdatedEvent(messageRequestId);
        messageRequestEvent = new MessageRequestEvent(eventId.toString(),
                messageRequestClientUpdatedEvent.getMessageRequestId().toString(),
                mapper.writeValueAsString(messageRequestClientUpdatedEvent),
                createdEvent.getClass().toString(),
                new Date());
        repository.addEvent(messageRequestEvent);

        // when
        List<MessageRequestEvent> actual = repository.getEvents(messageRequestId.toString());

        // then
        Assert.assertFalse(actual.isEmpty());
        Assert.assertEquals(actual.get(0).eventTypeResolver(), MessageRequestCreatedEvent.class);
        Assert.assertEquals(actual.get(1).eventTypeResolver(), MessageRequestClientUpdatedEvent.class);
    }
}