package com.acme.notificationapp.repository;

import com.acme.notificationapp.domain.Client;
import com.acme.notificationapp.domain.Medias;
import com.acme.notificationapp.domain.MessageRequest;
import com.acme.notificationapp.domain.Publication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class MessageRequestReadRepositoryTest extends BaseDatabaseTest {

    @Transactional
    @Test
    void getBatchForUpdateById() {
        //        given
        Client client = clientReadRepository.save(new Client(1L, "German 1", Medias.SMS, "SMS"));
        Publication publication = publicationReadRepository.save(new Publication(1L, "Hello, your account is about to expire"));
        UUID messageRequestId = UUID.randomUUID();
        MessageRequest messageRequest = messageRequestWriteRepository.save(new MessageRequest(messageRequestId, client, publication));

        // when
        // 10L
        List<MessageRequest> batchForUpdateById = messageRequestReadRepository.getBatchForUpdateById(1L);

        // then

    }
}