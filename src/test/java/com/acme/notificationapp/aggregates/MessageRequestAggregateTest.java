package com.acme.notificationapp.aggregates;

import com.acme.notificationapp.command.CreateMessageRequestCommand;
import com.acme.notificationapp.command.MessageRequestByMessageStateQuery;
import com.acme.notificationapp.command.UpdateMessageRequestCommand;
import com.acme.notificationapp.domain.*;
import com.acme.notificationapp.events.Event;
import com.acme.notificationapp.events.MessageRequestCreatedEvent;
import com.acme.notificationapp.projections.MessageRequestProjector;
import com.acme.notificationapp.projectors.MessageRequestProjection;
import com.acme.notificationapp.repository.BaseDatabaseTest;
import com.acme.notificationapp.repository.MessageRequestReadRepositoryInMemory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@SpringBootTest
class MessageRequestAggregateTest extends BaseDatabaseTest {

    @Autowired
    private MessageRequestAggregate messageRequestAggregate;

    MessageRequestReadRepositoryInMemory readRepository;
    private MessageRequestProjector messageRequestProjector;
    private MessageRequestProjection messageRequestProjection;

    private Client client;
    private Publication publication;
    private MessageRequest messageRequest;
    private UUID messageRequestId = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        clientReadRepository.deleteAll();
        publicationReadRepository.deleteAll();
        readRepository = new MessageRequestReadRepositoryInMemory();
        messageRequestProjector = new MessageRequestProjector(clientReadRepository, publicationReadRepository, readRepository);
        messageRequestProjection = new MessageRequestProjection(readRepository);

        //messageRequestProjector = new MessageRequestProjector(clientReadRepository, publicationReadRepository);
        client = clientReadRepository.save(new Client(1L, "German 1", Medias.SMS, "SMS"));
        publication = publicationReadRepository.save(new Publication(1L, "Hello, your account is about to expire"));
        messageRequest = messageRequestWriteRepository.save(new MessageRequest(messageRequestId, client, publication));
    }

    @Test
    void handleCreateMessageRequestCommand() throws JsonProcessingException {
        // given
        Client client = clientReadRepository.save(new Client("German 1", Medias.SMS, "SMS"));
        Publication publication = publicationReadRepository.save(new Publication("Hello, your account is about to expire"));

        // when
        List<Event> actual = messageRequestAggregate
                .handleCreateMessageRequestCommand(
                        new CreateMessageRequestCommand(messageRequestId, client.getId(), publication.getId()));

        messageRequestProjector.project(messageRequestId.toString(), actual);

        // then
        MessageRequestCreatedEvent event = (MessageRequestCreatedEvent) actual.get(0);
        assertThat(event.getClient()).usingRecursiveComparison().ignoringFields("messageRequests").isEqualTo(client);
        Assert.assertEquals(publication, event.getPublication());
    }

    @Test
    public void handle() throws JsonProcessingException {
        Client client2 = clientReadRepository.save(new Client(2L, "German 2", Medias.MAIL, "MAIL"));
        Publication publication2 = publicationReadRepository.save(new Publication(2L, "Hello, your account is now open"));
        List<Event> events = null;
        // Create the message request
        CreateMessageRequestCommand createUserCommand = new CreateMessageRequestCommand(messageRequestId, 1L, 1L);
        // Add created event
        events = messageRequestAggregate.handleCreateMessageRequestCommand(createUserCommand);
        // Store in messages by States
        messageRequestProjector.project(messageRequestId.toString(), events);

        // Update the client
        Client client = clientReadRepository.getOne(2L);
        UpdateMessageRequestCommand updateMessageRequestCommand = new UpdateMessageRequestCommand(messageRequestId, 2L, 1L);
        // Add Updated Event!
        events = messageRequestAggregate.handleUpdateMessageRequestCommand(updateMessageRequestCommand);
        // Update in messages by States in PROJECTOR
        messageRequestProjector.project(messageRequestId.toString(), events);

        Publication publication = publicationReadRepository.getOne(2L);
        UpdateMessageRequestCommand updateMessageRequestPublicationCommand = new UpdateMessageRequestCommand(messageRequestId, 2L, 2L);
        // Add Updated Event!
        events = messageRequestAggregate.handleUpdateMessageRequestCommand(updateMessageRequestPublicationCommand);
        // Update in messages by States in PROJECTOR
        messageRequestProjector.project(messageRequestId.toString(), events);

        MessageRequestByMessageStateQuery messageRequestByMessageStateQuery = new MessageRequestByMessageStateQuery(MessageStates.PENDING);
        Map<MessageStates, Set<MessageRequest>> groupedMessageRequests = new HashMap<>();
        Set<MessageRequest> pendingMessagesRequests = new HashSet<>(Arrays.asList(new MessageRequest(messageRequestId, client, publication)));
        // Retrieve PROJECTIONS
        assertEquals(pendingMessagesRequests, messageRequestProjection.handle(messageRequestByMessageStateQuery));

    }
}