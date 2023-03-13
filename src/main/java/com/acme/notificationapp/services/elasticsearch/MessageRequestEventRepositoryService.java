package com.acme.notificationapp.services.elasticsearch;

import com.acme.notificationapp.domain.MessageRequestEvent;
import com.acme.notificationapp.repository.es.MessageRequestEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MessageRequestEventRepositoryService {

    private final MessageRequestEventRepository repository;

    @Autowired
    public MessageRequestEventRepositoryService(MessageRequestEventRepository repository) {
        this.repository = repository;
    }

    // TODO add tests
    public void createMessageRequestIndexBulk(final List<MessageRequestEvent> messageRequests) {
        repository.saveAll(messageRequests);
    }

    public MessageRequestEvent index(final MessageRequestEvent messageRequestEvent) {
        return repository.save(messageRequestEvent);
    }

    public List<MessageRequestEvent> findEventsByMessageRequest() {
        return StreamSupport.stream(
                        Spliterators.spliteratorUnknownSize(repository.findAll().iterator(), 0), false)
                .collect(Collectors.toList());
    }
}

