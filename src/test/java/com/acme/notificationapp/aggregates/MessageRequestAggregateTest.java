package com.acme.notificationapp.aggregates;

import com.acme.notificationapp.commands.CreateMessageRequestCommand;
import com.acme.notificationapp.domain.Client;
import com.acme.notificationapp.domain.Medias;
import com.acme.notificationapp.domain.Publication;
import com.acme.notificationapp.events.Event;
import com.acme.notificationapp.events.MessageRequestCreatedEvent;
import com.acme.notificationapp.repository.ClientReadRepository;
import com.acme.notificationapp.repository.PublicationReadRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessageRequestAggregateTest {

    @Autowired
    private MessageRequestAggregate underTest;
    @Autowired
    private ClientReadRepository clientReadRepository;

    @Autowired
    private PublicationReadRepository publicationReadRepository;

    @Test
    void handleCreateMessageRequestCommand() {

        // given
        Client client = clientReadRepository.save(new Client("German 1", Medias.SMS, "SMS"));
        Publication publication = publicationReadRepository.save(new Publication("Hello, your account is about to expire"));


        // when
        List<Event> actual = underTest
                .handleCreateMessageRequestCommand(
                        new CreateMessageRequestCommand(client.getId(), publication.getId()));

        // then
        Assert.assertEquals(actual, Arrays.asList(new MessageRequestCreatedEvent(client, publication)));
    }
}