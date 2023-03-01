package com.acme.notificationapp.events;

import com.acme.notificationapp.domain.Client;
import com.acme.notificationapp.domain.Publication;
import lombok.Data;

@Data
public final class MessageRequestCreatedEvent extends Event {
    private final Client client;
    private final Publication publication;
}
