package com.acme.notificationapp.controller;

import com.acme.notificationapp.aggregates.MessageRequestAggregate;
import com.acme.notificationapp.commands.CreateMessageRequestCommand;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageRequestController {

    private final MessageRequestAggregate messageRequestAggregate;

    @Autowired
    public MessageRequestController(MessageRequestAggregate messageRequestAggregate) {
        this.messageRequestAggregate = messageRequestAggregate;
    }


    @PutMapping("")
    public void put(@RequestBody CreateMessageRequest request) {
        messageRequestAggregate.handleCreateMessageRequestCommand(
                new CreateMessageRequestCommand(request.getClientId(), request.getPublicationId()));
    }

    @Data
    public final class CreateMessageRequest {
        private final Long clientId;
        private final Long publicationId;
    }
}
