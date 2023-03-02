package com.acme.notificationapp.projections;

import com.acme.notificationapp.TestData;
import com.acme.notificationapp.domain.MessageRequest;
import com.acme.notificationapp.domain.MessageStates;
import com.acme.notificationapp.events.Event;
import com.acme.notificationapp.events.MessageRequestCreatedEvent;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class MessageRequestProjectionTest {

    @Autowired
    private MessageRequestProjection underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void project() {
        // given
        UUID uuid = UUID.randomUUID();
        String messageRequestId = "1";
        List<Event> events = Arrays.asList(new MessageRequestCreatedEvent(TestData.CLIENT, TestData.PUBLICATION));

        // when
        underTest.project(messageRequestId, events);

        // then
        Map<MessageStates, Set<MessageRequest>> expected = new HashMap<>();
        Assert.assertTrue(underTest.getMessageRequestsByMessageState().size() == 1);
    }
}