package com.acme.notificationapp.aggregates;

import com.acme.notificationapp.commands.CreateMessageRequestCommand;
import com.acme.notificationapp.domain.Client;
import com.acme.notificationapp.domain.Publication;
import com.acme.notificationapp.events.Event;
import com.acme.notificationapp.events.MessageRequestCreatedEvent;
import com.acme.notificationapp.repository.ClientReadRepository;
import com.acme.notificationapp.repository.PublicationReadRepository;
import com.acme.notificationapp.stores.EventStore;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class MessageRequestAggregate {

//    private MessageRequestWriteRepository writeRepository;

    private final ClientReadRepository clientReadRepository;
    private final PublicationReadRepository publicationReadRepository;
    private final EventStore writeRepository;

    @Autowired
    public MessageRequestAggregate(ClientReadRepository clientReadRepository,
                                   PublicationReadRepository publicationReadRepository,
                                   EventStore repository) {
        this.clientReadRepository = clientReadRepository;
        this.publicationReadRepository = publicationReadRepository;
        this.writeRepository = repository;
    }

//    public MessageRequest handleCreateMessageRequestCommand(@NotNull CreateMessageRequestCommand command) {
//        MessageRequest messageRequest = new MessageRequest(command.getClient(), command.getPublication());
//        return writeRepository.save(messageRequest);
//    }

    public List<Event> handleCreateMessageRequestCommand(@NotNull CreateMessageRequestCommand command) {
        Client client = clientReadRepository
                .findById(command.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("No client found -> id: " + command.getClientId()));

        Publication publication = publicationReadRepository
                .findById(command.getPublicationId())
                .orElseThrow(() -> new IllegalArgumentException("No pub found -> id: " + command.getPublicationId()));
        MessageRequestCreatedEvent event =
                new MessageRequestCreatedEvent(client, publication);
        writeRepository.addEvent(UUID.randomUUID().toString(), event);
        return Arrays.asList(event);
    }

//    public MessageRequest handleUpdateMessageRequestCommand(@NotNull UpdateMessageCommand command) {
//        MessageRequest messageRequest = writeRepository.getOne(command.getId());
//        messageRequest.setMessageState(command.getMessageState());
//        return writeRepository.save(messageRequest);
//    }
}