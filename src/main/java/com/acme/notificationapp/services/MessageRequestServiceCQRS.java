package com.acme.notificationapp.services;

import com.acme.notificationapp.domain.*;
import com.acme.notificationapp.repository.MessageRequestReadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageRequestServiceCQRS {

    private static final Logger logger = LoggerFactory.getLogger(MessageRequestServiceCQRS.class);

    @Autowired
    private final MessageRequestReadRepository repository;

    public MessageRequestServiceCQRS(MessageRequestReadRepository repository) {
        this.repository = repository;
    }

    public void create(Client client, Publication publication) {
        repository.save(new MessageRequest(client, publication));
    }

    public void update(Long messageRequestId, Client client, Publication publication) {
        MessageRequest messageRequest = repository.getOne(messageRequestId);
        messageRequest.setClient(client);
        messageRequest.setPublication(publication);
        repository.save(messageRequest);
    }
}