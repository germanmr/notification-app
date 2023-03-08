package com.acme.notificationapp.repository;

import com.acme.notificationapp.domain.Client;
import com.acme.notificationapp.domain.MessageRequest;
import com.acme.notificationapp.domain.Publication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.jdo.annotations.Transactional;

import static com.acme.notificationapp.TestData.CLIENT;
import static com.acme.notificationapp.TestData.PUBLICATION;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ClientWriteRepositoryTest extends BaseDatabaseTest {

    @Test
    @Transactional
    void manuallyCreateMessageRequest() {
        // given
        Client client = clientWriteRepository.save(CLIENT);
        Publication publication = publicationWriteRepository.save(PUBLICATION);
        MessageRequest messageRequest = new MessageRequest(client, publication);
        client.addMessageRequest(messageRequest); // NO SQL!! Only add the reference

        // When
        MessageRequest actual = messageRequestWriteRepository.save(messageRequest);

        // Then
        assertThat(client.getMessageRequests().get(0)).usingRecursiveComparison().ignoringFields("client").isEqualTo(actual);
    }
}