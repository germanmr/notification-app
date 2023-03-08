package com.acme.notificationapp.projections;

import com.acme.notificationapp.TestData;
import com.acme.notificationapp.events.Event;
import com.acme.notificationapp.events.MessageRequestCreatedEvent;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class MessageRequestProjectionTest {

    @Autowired
    private MessageRequestProjector underTest;

    private final UUID messageRequestId = UUID.randomUUID();

    @Test
    void project() {
        // given
        List<Event> events = Arrays.asList(new MessageRequestCreatedEvent(messageRequestId, TestData.CLIENT, TestData.PUBLICATION));

        // when
        underTest.project(messageRequestId.toString(), events);

        // then
        Assert.assertTrue(underTest.getMessageRequestsByMessageState().size() == 1);
    }
}