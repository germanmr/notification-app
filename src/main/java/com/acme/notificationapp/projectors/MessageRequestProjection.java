package com.acme.notificationapp.projectors;

import com.acme.notificationapp.command.MessageRequestByMessageStateQuery;
import com.acme.notificationapp.domain.MessageRequest;
import com.acme.notificationapp.repository.MessageRequestReadRepositoryInMemory;

import java.util.Set;

public class MessageRequestProjection {

    MessageRequestReadRepositoryInMemory readRepository;

    public MessageRequestProjection(MessageRequestReadRepositoryInMemory readRepository) {
        this.readRepository = readRepository;
    }

    public Set<MessageRequest> handle(MessageRequestByMessageStateQuery query) {
        return readRepository.getMessageRequestsByMessageState(query.getMessageState());
    }
}
