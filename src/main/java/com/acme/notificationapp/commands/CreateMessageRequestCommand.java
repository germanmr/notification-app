package com.acme.notificationapp.commands;

import com.acme.notificationapp.domain.Client;
import com.acme.notificationapp.domain.Publication;
import lombok.Data;

import java.util.Objects;

@Data
public final class CreateMessageRequestCommand {
    private final Long clientId;
    private final Long publicationId;
}