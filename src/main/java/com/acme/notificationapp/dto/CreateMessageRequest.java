package com.acme.notificationapp.dto;

import lombok.Data;

@Data
public final class CreateMessageRequest {
    private final Long clientId;
    private final Long publicationId;
}