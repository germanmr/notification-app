package com.acme.notificationapp.projections;

import com.acme.notificationapp.commands.MessageRequestByMessageStateQuery;
import com.acme.notificationapp.domain.MessageRequest;
import com.acme.notificationapp.domain.MessageStates;
import com.acme.notificationapp.repository.MessageRequestReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageRequestProjection {

    private MessageRequestReadRepository readRepository;

    @Autowired
    public MessageRequestProjection(MessageRequestReadRepository readRepository) {
        this.readRepository = readRepository;
    }

    private Map<MessageStates, Set<MessageRequest>> messageRequestsByMessageState = new HashMap<>();

    public Map<MessageStates, Set<MessageRequest>> handle(MessageRequestByMessageStateQuery query) {
        return messageRequestsByMessageState
                .entrySet()
                .stream()
                .filter(e -> e.getKey() == query.getMessageState())
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue()
                ));
        //List<MessageRequest> messages = readRepository.findAll();
        //Set<MessageStates> messageStates = messages.stream().map(m -> m.getMessageState()).collect(toSet());
        //        return messageStates.stream()
        //                .collect(Collectors.toMap(
        //                        s -> s,
        //                        s -> messages.stream().filter(m -> m.getMessageState() == s)
        //                                .collect(Collectors.toSet())));
    }

    public void project(MessageRequest messageRequest) {
        Set<MessageRequest> messageRequests = messageRequestsByMessageState.get(messageRequest.getMessageState());
        messageRequests.add(messageRequest);
        messageRequestsByMessageState.put(messageRequest.getMessageState(), messageRequests);
        readRepository.save(messageRequest);
    }
}


















