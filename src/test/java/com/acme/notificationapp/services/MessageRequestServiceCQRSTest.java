package com.acme.notificationapp.services;

import com.acme.notificationapp.domain.Client;
import com.acme.notificationapp.domain.Publication;
import com.acme.notificationapp.repository.BaseDatabaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
class MessageRequestServiceCQRSTest extends BaseDatabaseTest {

    @Autowired
    private MessageRequestServiceCQRS messageRequestServiceCQRS;

    private Client client;
    private Publication publication;

    @Transactional
    @Test
    void create() {
//        // When
//        client = clientReadRepository.save(new Client(1L, "Name", Medias.SMS, "SMS"));
//        publication = publicationReadRepository.save(new Publication("This is a message"));
//
//        MessageRequest actual = messageRequestServiceCQRS.create(client, publication);
//
//        // Then
//        MessageRequest expected = new MessageRequest(1L, actual.getUuid(), actual.getClient(), actual.getPublication());
//        assertThat(expected).usingRecursiveComparison().ignoringFields("client").isEqualTo(actual);
    }
}