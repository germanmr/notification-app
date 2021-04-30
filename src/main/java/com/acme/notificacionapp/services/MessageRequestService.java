package com.acme.notificacionapp.services;

import com.acme.notificacionapp.domain.Client;
import com.acme.notificacionapp.domain.MessageRequest;
import com.acme.notificacionapp.domain.Publication;
import com.acme.notificacionapp.repository.MessageRequestRepository;
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

    @Autowired
    private final MessageRequestRepository messageRequestRepository;

    public MessageRequestService(MessageRequestRepository messageRequestRepository) {
        this.messageRequestRepository = messageRequestRepository;
    }

    @Transactional
    public void saveData(List<Client> clients, Publication publication) throws Exception {
        Preconditions.checkNotNull(clients, "Clients should not be null");
        Preconditions.checkNotNull(publication, "Publication shoul not be null");

        logger.info("Saving request begin");

        clients.stream().forEach(client -> {
            MessageRequest messageRequest = new MessageRequest(client, publication);
            logger.info("Saving request messageRequest: " + messageRequest);
            messageRequestRepository.save(messageRequest);
        });
        logger.info("Saving request end");
    }
}
