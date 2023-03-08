package com.acme.notificationapp.events;

import com.acme.notificationapp.domain.Client;
import com.acme.notificationapp.domain.Publication;
import lombok.Data;

import java.util.UUID;

@Data
public final class MessageRequestCreatedEvent extends Event {
    private final UUID messageRequestId;
    private final Client client;
    private final Publication publication;
}
