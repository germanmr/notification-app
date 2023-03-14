package com.acme.notificationapp.services.utils;

import com.acme.notificationapp.domain.Client;
import com.acme.notificationapp.domain.MessageRequest;
import com.acme.notificationapp.domain.MessageRequestEvent;
import com.acme.notificationapp.domain.Publication;
import com.acme.notificationapp.events.Event;
import com.acme.notificationapp.events.MessageRequestClientUpdatedEvent;
import com.acme.notificationapp.events.MessageRequestCreatedEvent;
import com.acme.notificationapp.events.MessageRequestPublicationUpdatedEvent;
import com.acme.notificationapp.repository.ClientReadRepository;
import com.acme.notificationapp.repository.PublicationReadRepository;
import com.acme.notificationapp.services.elasticsearch.MessageRequestEventRepositoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageRequestUtility {

    private final ClientReadRepository clientReadRepository;
    private final PublicationReadRepository publicationReadRepository;
    private final ObjectMapper mapper;

    @Autowired
    public MessageRequestUtility(ClientReadRepository clientReadRepository,
                                 PublicationReadRepository publicationReadRepository,
                                 ObjectMapper mapper) {
        this.clientReadRepository = clientReadRepository;
        this.publicationReadRepository = publicationReadRepository;
        this.mapper = mapper;
    }

    @Transactional
    public MessageRequest recreateMessageRequestState(MessageRequestEventRepositoryService store,
                                                      String messageRequestId) throws JsonProcessingException {
        MessageRequest messageRequest = null;

        List<MessageRequestEvent> events = store.getEvents(messageRequestId);
        for (MessageRequestEvent event : events) {
            Event castedEvent = (Event) mapper.readValue(event.getEvent(), event.eventTypeResolver());
            if (castedEvent instanceof MessageRequestCreatedEvent) {
                MessageRequestCreatedEvent e = (MessageRequestCreatedEvent) castedEvent;
                messageRequest = new MessageRequest(e.getMessageRequestId(), e.getClient(), e.getPublication());
            }
            if (castedEvent instanceof MessageRequestClientUpdatedEvent) {
                MessageRequestClientUpdatedEvent e = (MessageRequestClientUpdatedEvent) castedEvent;
                Client client = clientReadRepository.getOne(e.getClientId());
                messageRequest.setClient(client);
            }
            if (castedEvent instanceof MessageRequestPublicationUpdatedEvent) {
                MessageRequestPublicationUpdatedEvent e = (MessageRequestPublicationUpdatedEvent) castedEvent;
                Publication publication = publicationReadRepository.getOne(e.getPublicationId());
                messageRequest.setPublication(publication);
            }
        }
        return messageRequest;
    }
}
