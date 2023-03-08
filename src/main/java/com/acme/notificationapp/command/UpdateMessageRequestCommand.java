package com.acme.notificationapp.command;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateMessageRequestCommand implements Command {
    private final UUID messageRequestId;
    private final Long clientId;
    private final Long publicationId;
}
