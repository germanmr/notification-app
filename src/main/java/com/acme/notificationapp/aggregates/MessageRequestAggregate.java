package com.acme.notificationapp.aggregates;

import com.acme.notificationapp.command.CreateMessageRequestCommand;
import com.acme.notificationapp.command.UpdateMessageRequestCommand;
import com.acme.notificationapp.domain.Client;
import com.acme.notificationapp.domain.MessageRequest;
import com.acme.notificationapp.domain.Publication;
import com.acme.notificationapp.events.Event;
import com.acme.notificationapp.events.MessageRequestClientUpdatedEvent;
import com.acme.notificationapp.events.MessageRequestCreatedEvent;
import com.acme.notificationapp.events.MessageRequestPublicationUpdatedEvent;
import com.acme.notificationapp.repository.ClientReadRepository;
import com.acme.notificationapp.repository.PublicationReadRepository;
import com.acme.notificationapp.services.utils.MessageRequestUtility;
import com.acme.notificationapp.stores.EventStore;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MessageRequestAggregate {

    private final ClientReadRepository clientReadRepository;
    private final PublicationReadRepository publicationReadRepository;
    private final EventStore writeRepository;
    private final MessageRequestUtility messageRequestUtility;

    @Autowired
    public MessageRequestAggregate(ClientReadRepository clientReadRepository,
                                   PublicationReadRepository publicationReadRepository,
                                   EventStore repository,
                                   MessageRequestUtility messageRequestUtility) {
        this.clientReadRepository = clientReadRepository;
        this.publicationReadRepository = publicationReadRepository;
        this.writeRepository = repository;
        this.messageRequestUtility = messageRequestUtility;
    }

    public List<Event> handleCreateMessageRequestCommand(@NotNull CreateMessageRequestCommand command) {
        Client client = clientReadRepository
                .findById(command.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("No client found -> id: " + command.getClientId()));

        Publication publication = publicationReadRepository
                .findById(command.getPublicationId())
                .orElseThrow(() -> new IllegalArgumentException("No pub found -> id: " + command.getPublicationId()));
        MessageRequestCreatedEvent event = new MessageRequestCreatedEvent(command.getMessageRequestId(), client, publication);
        // Message Request created
        writeRepository.addEvent(command.getMessageRequestId().toString(), event);
        return Arrays.asList(event);
    }

    public List<Event> handleUpdateMessageRequestCommand(UpdateMessageRequestCommand command) {
        MessageRequest messageRequest = messageRequestUtility.recreateMessageRequestState(writeRepository, command.getMessageRequestId().toString());
        List<Event> events = new ArrayList<>();

        if (messageRequest.getClient().getId() != command.getClientId()) {
            Event messageRequestClientUpdatedEvent = new MessageRequestClientUpdatedEvent(command.getMessageRequestId(), command.getClientId());
            events.add(messageRequestClientUpdatedEvent);
            writeRepository.addEvent(command.getMessageRequestId().toString(), messageRequestClientUpdatedEvent);
        }
        if (messageRequest.getPublication().getId() != command.getPublicationId()) {
            Event messageRequestPublicationUpdatedEvent = new MessageRequestPublicationUpdatedEvent(command.getMessageRequestId(), command.getPublicationId());
            events.add(messageRequestPublicationUpdatedEvent);
            writeRepository.addEvent(command.getMessageRequestId().toString(), messageRequestPublicationUpdatedEvent);
        }
        return events;
    }
}