package com.acme.notificationapp.commands;

import com.acme.notificationapp.domain.MessageStates;
import lombok.Data;

@Data
public class MessageRequestByMessageStateQuery {
    private MessageStates messageState;
}
