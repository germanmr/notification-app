package com.acme.notificationapp.services.utils;

import com.acme.notificationapp.domain.Client;
import com.acme.notificationapp.domain.MessageRequest;
import com.acme.notificationapp.domain.Publication;
import com.acme.notificationapp.events.Event;
import com.acme.notificationapp.events.MessageRequestClientUpdatedEvent;
import com.acme.notificationapp.events.MessageRequestCreatedEvent;
import com.acme.notificationapp.events.MessageRequestPublicationUpdatedEvent;
import com.acme.notificationapp.repository.ClientReadRepository;
import com.acme.notificationapp.repository.PublicationReadRepository;
import com.acme.notificationapp.stores.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageRequestUtility {

    private final ClientReadRepository clientReadRepository;
    private final PublicationReadRepository publicationReadRepository;

    @Autowired
    public MessageRequestUtility(ClientReadRepository clientReadRepository, PublicationReadRepository publicationReadRepository) {
        this.clientReadRepository = clientReadRepository;
        this.publicationReadRepository = publicationReadRepository;
    }

    @Transactional
    public MessageRequest recreateMessageRequestState(EventStore store,
                                                      String messageRequestId) {
        MessageRequest messageRequest = null;

        List<Event> events = store.getEvents(messageRequestId);
        for (Event event : events) {
            if (event instanceof MessageRequestCreatedEvent) {
                MessageRequestCreatedEvent e = (MessageRequestCreatedEvent) event;
                messageRequest = new MessageRequest(e.getMessageRequestId(), e.getClient(), e.getPublication());
            }
            if (event instanceof MessageRequestClientUpdatedEvent) {
                MessageRequestClientUpdatedEvent e = (MessageRequestClientUpdatedEvent) event;
                Client client = clientReadRepository.getOne(e.getClientId());
                messageRequest.setClient(client);
            }
            if (event instanceof MessageRequestPublicationUpdatedEvent) {
                MessageRequestPublicationUpdatedEvent e = (MessageRequestPublicationUpdatedEvent) event;
                Publication publication = publicationReadRepository.getOne(e.getPublicationId());
                messageRequest.setPublication(publication);
            }
        }

        return messageRequest;
    }

}
