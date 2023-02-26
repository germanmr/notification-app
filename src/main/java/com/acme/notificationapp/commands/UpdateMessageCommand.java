package com.acme.notificationapp.commands;

import com.acme.notificationapp.domain.MessageStates;
import lombok.Data;

@Data
public class UpdateMessageCommand {
    private Long id;

    private MessageStates messageState;

}