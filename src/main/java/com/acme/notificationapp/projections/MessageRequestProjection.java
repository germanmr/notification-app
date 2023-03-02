package com.acme.notificationapp.projections;

import com.acme.notificationapp.commands.MessageRequestByMessageStateQuery;
import com.acme.notificationapp.domain.MessageRequest;
import com.acme.notificationapp.domain.MessageStates;
import com.acme.notificationapp.events.Event;
import com.acme.notificationapp.events.MessageRequestCreatedEvent;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageRequestProjection {

    private final Map<MessageStates, Set<MessageRequest>> messageRequestsByMessageState = new HashMap<>();

    public void project(String userId, List<Event> events) {
        for (Event event : events) {
            if (event instanceof MessageRequestCreatedEvent)
                apply(userId, (MessageRequestCreatedEvent) event);
        }
    }

    private void apply(String userId, MessageRequestCreatedEvent event) {
        MessageRequest messageRequest = new MessageRequest(event.getClient(), event.getPublication());
        Set<MessageRequest> messageRequests = messageRequestsByMessageState.getOrDefault(messageRequest.getMessageState(), new HashSet<>());
        messageRequests.add(messageRequest);
        messageRequestsByMessageState.put(messageRequest.getMessageState(), messageRequests);
    }

    public Map<MessageStates, Set<MessageRequest>> handle(MessageRequestByMessageStateQuery query) {
        return messageRequestsByMessageState
                .entrySet()
                .stream()
                .filter(e -> e.getKey() == query.getMessageState())
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue()
                ));
        // List<MessageRequest> messages = readRepository.findAll();
        // Set<MessageStates> messageStates = messages.stream().map(m -> m.getMessageState()).collect(toSet());
        //        return messageStates.stream()
        //                .collect(Collectors.toMap(
        //                        s -> s,
        //                        s -> messages.stream().filter(m -> m.getMessageState() == s)
        //                                .collect(Collectors.toSet())));
    }

    public Map<MessageStates, Set<MessageRequest>> getMessageRequestsByMessageState() {
        return messageRequestsByMessageState;
    }

    //    public void project(MessageRequest messageRequest) {
//        Set<MessageRequest> messageRequests = messageRequestsByMessageState.get(messageRequest.getMessageState());
//        messageRequests.add(messageRequest);
//        messageRequestsByMessageState.put(messageRequest.getMessageState(), messageRequests);
//        readRepository.save(messageRequest);
//    }
}


















