package com.acme.notificationapp.services;

import com.acme.notificationapp.domain.Client;
import com.acme.notificationapp.domain.MessageRequest;
import com.acme.notificationapp.domain.Publication;
import com.acme.notificationapp.dto.MessageRequestDTO;
import com.acme.notificationapp.repository.ClientWriteRepository;
import com.acme.notificationapp.repository.MessageRequestReadRepository;
import com.acme.notificationapp.repository.PublicationWriteRepository;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MessageRequestService {

    private static final Logger logger = LoggerFactory.getLogger(MessageRequestService.class);

    private final MessageRequestReadRepository messageRequestReadRepository;
    private final ClientWriteRepository clientWriteRepository;
    private final PublicationWriteRepository publicationWriteRepository;

    @Autowired
    public MessageRequestService(MessageRequestReadRepository messageRequestReadRepository,
                                 ClientWriteRepository clientWriteRepository,
                                 PublicationWriteRepository publicationWriteRepository) {
        this.messageRequestReadRepository = messageRequestReadRepository;
        this.clientWriteRepository = clientWriteRepository;
        this.publicationWriteRepository = publicationWriteRepository;
    }

    @Transactional
    public void saveData(List<Client> clients, Publication publication) throws Exception {
        Preconditions.checkNotNull(clients, "Clients should not be null");
        Preconditions.checkNotNull(publication, "Publication should not be null");

        logger.info("Saving request begin");

        clients.stream().forEach(client -> {
            clientWriteRepository.save(client);
            publicationWriteRepository.save(publication);
            MessageRequest messageRequest = new MessageRequest(client, publication);
            logger.info("Saving request messageRequest: " + messageRequest);
            messageRequestReadRepository.save(messageRequest);
        });
        logger.info("Saving request end");
    }

    @Transactional
    public void elasticLoad(List<Client> clients, Publication publication) throws Exception {
        Preconditions.checkNotNull(clients, "Clients should not be null");
        Preconditions.checkNotNull(publication, "Publication should not be null");

        logger.info("Saving request begin");

        clients.stream().forEach(client -> {
            MessageRequest messageRequest = new MessageRequest(client, publication);
            logger.info("Saving request messageRequest: " + messageRequest);
            messageRequestReadRepository.save(messageRequest);
        });
        logger.info("Saving request end");
    }

    @Transactional
    public void updateStatus(MessageRequestDTO messageRequestDTO) throws Exception {
        Preconditions.checkNotNull(messageRequestDTO, "The message request must not be null");

        logger.info("Setting acknowledgement for messageRequestDTO: " + messageRequestDTO);

        // Block the record for update on
        MessageRequest messageRequest = messageRequestReadRepository
                .findById(messageRequestDTO.getId())
                .orElseThrow(() -> new Exception("The message request " + messageRequestDTO + " is nonexistent"));

        messageRequest.setAcknowledgement(messageRequestDTO);
        messageRequestReadRepository.save(messageRequest);

        logger.info("Saving request end");
    }
}
