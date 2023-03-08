package com.acme.notificationapp.controller;

import com.acme.notificationapp.aggregates.MessageRequestAggregate;
import com.acme.notificationapp.command.CreateMessageRequestCommand;
import com.acme.notificationapp.dto.CreateMessageRequest;
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
                new CreateMessageRequestCommand(request.getMessageRequestId(),
                        request.getClientId(),
                        request.getPublicationId()));
    }

}
