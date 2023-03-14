package com.acme.notificationapp.controller;

import com.acme.notificationapp.aggregates.MessageRequestAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageRequestController {

    private final MessageRequestAggregate messageRequestAggregate;

    @Autowired
    public MessageRequestController(MessageRequestAggregate messageRequestAggregate) {
        this.messageRequestAggregate = messageRequestAggregate;
    }
}
