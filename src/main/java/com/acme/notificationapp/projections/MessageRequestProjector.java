package com.acme.notificationapp.projections;

import com.acme.notificationapp.domain.Client;
import com.acme.notificationapp.domain.MessageRequest;
import com.acme.notificationapp.domain.MessageStates;
import com.acme.notificationapp.domain.Publication;
import com.acme.notificationapp.events.Event;
import com.acme.notificationapp.events.MessageRequestClientUpdatedEvent;
import com.acme.notificationapp.events.MessageRequestCreatedEvent;
import com.acme.notificationapp.events.MessageRequestPublicationUpdatedEvent;
import com.acme.notificationapp.repository.ClientReadRepository;
import com.acme.notificationapp.repository.MessageRequestReadRepositoryInMemory;
import com.acme.notificationapp.repository.PublicationReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageRequestProjector {

    MessageRequestReadRepositoryInMemory readRepository;

    private ClientReadRepository clientReadRepository;
    private PublicationReadRepository publicationReadRepository;

    @Autowired
    public MessageRequestProjector(ClientReadRepository clientReadRepository,
                                   PublicationReadRepository publicationReadRepository,
                                   MessageRequestReadRepositoryInMemory readRepository) {
        this.clientReadRepository = clientReadRepository;
        this.publicationReadRepository = publicationReadRepository;
        this.readRepository = readRepository;
    }

    public void project(String messageRequestId, List<Event> events) {
        for (Event event : events) {
            if (event instanceof MessageRequestCreatedEvent)
                apply(messageRequestId, (MessageRequestCreatedEvent) event);
            if (event instanceof MessageRequestClientUpdatedEvent)
                apply(messageRequestId, (MessageRequestClientUpdatedEvent) event);
            if (event instanceof MessageRequestPublicationUpdatedEvent)
                apply(messageRequestId, (MessageRequestPublicationUpdatedEvent) event);
        }
    }

    private void apply(String messageRequestId, MessageRequestCreatedEvent event) {
        MessageRequest messageRequest = new MessageRequest(UUID.fromString(messageRequestId),
                event.getClient(),
                event.getPublication());
        Set<MessageRequest> messageRequests = Optional.
                ofNullable(readRepository.getMessageRequestsByMessageState(messageRequest.getMessageState())).
                orElse(new HashSet<>());
        messageRequests.add(messageRequest);
        readRepository.addMessageRequests(messageRequest.getMessageState(), messageRequests);
    }

    private void apply(String messageRequestId, MessageRequestClientUpdatedEvent event) {
        for (Map.Entry<MessageStates, Set<MessageRequest>> entry : readRepository.getMessageRequests().entrySet()) {
            MessageRequest messageRequest = entry.getValue().stream().filter(ms -> ms.getUuid().toString().equals(messageRequestId)).findFirst().get();
            Client client = clientReadRepository.findById(event.getClientId())
                    .orElseThrow(() -> new IllegalArgumentException("No client found -> id: " + event.getClientId()));

            Set<MessageRequest> updatedMessageRequests = entry.getValue();
            updatedMessageRequests.remove(messageRequest);
            messageRequest.setClient(client);
            updatedMessageRequests.add(messageRequest);
            readRepository.addMessageRequests(messageRequest.getMessageState(), updatedMessageRequests);
        }
    }

    private void apply(String messageRequestId, MessageRequestPublicationUpdatedEvent event) {
        for (Map.Entry<MessageStates, Set<MessageRequest>> entry : readRepository.getMessageRequests().entrySet()) {
            Set<MessageRequest> updatedMessageRequests = entry.getValue();
            MessageRequest messageRequest = updatedMessageRequests.
                    stream().filter(ms -> ms.getUuid().toString().equals(messageRequestId)).findFirst().get();

            updatedMessageRequests.remove(messageRequest);
            Publication publication = publicationReadRepository.findById(event.getPublicationId())
                    .orElseThrow(() -> new IllegalArgumentException("No pub found -> id: " + event.getPublicationId()));
            messageRequest.setPublication(publication);

            updatedMessageRequests.add(messageRequest);
            readRepository.addMessageRequests(messageRequest.getMessageState(), updatedMessageRequests);
        }
    }

    public Map<MessageStates, Set<MessageRequest>> getMessageRequestsByMessageState() {
        return readRepository.getMessageRequests();
    }
}


















