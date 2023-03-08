package com.acme.notificationapp.command;

import com.acme.notificationapp.domain.MessageStates;
import lombok.Data;

@Data
public class MessageRequestByMessageStateQuery {
    private final MessageStates messageState;
}
