package com.acme.notificationapp.events;

import lombok.Data;

import java.util.UUID;

@Data
public final class MessageRequestClientUpdatedEvent extends Event {
    private final UUID messageRequestId;
    private final Long clientId;
}
