package com.acme.notificationapp;

import com.acme.notificationapp.domain.Client;
import com.acme.notificationapp.domain.Medias;
import com.acme.notificationapp.domain.Publication;
import com.acme.notificationapp.events.MessageRequestClientUpdatedEvent;
import com.acme.notificationapp.events.MessageRequestCreatedEvent;
import lombok.Data;

import java.util.UUID;

@Data
public class TestData {
    public static Client CLIENT = new Client(1L, "Client 1", Medias.MAIL, "MAIL");
    public static Client CLIENT_2 = new Client(2L, "Client 2", Medias.SMS, "SMS");
    public static Publication PUBLICATION = new Publication(1L, "This is my message to you");

    public static MessageRequestCreatedEvent exampleMessageRequestCreatedEvent(UUID messageRequestId) {
        return new MessageRequestCreatedEvent(messageRequestId, CLIENT, PUBLICATION);
    }

    public static MessageRequestClientUpdatedEvent exampleMessageRequestClientUpdatedEvent(UUID messageRequestId) {
        return new MessageRequestClientUpdatedEvent(messageRequestId, 2L);
    }
}
