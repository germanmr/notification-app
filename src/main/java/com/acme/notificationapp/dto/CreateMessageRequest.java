package com.acme.notificationapp.dto;

import lombok.Data;

import java.util.UUID;

@Data
public final class CreateMessageRequest {
    private final UUID messageRequestId;
    private final Long clientId;
    private final Long publicationId;
}