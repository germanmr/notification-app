package com.acme.notificationapp.repository;

import com.acme.notificationapp.domain.MessageRequest;
import com.acme.notificationapp.domain.MessageStates;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class MessageRequestReadRepositoryInMemory {
    private Map<MessageStates, Set<MessageRequest>> messageRequestsByMessageState = new HashMap<>();

    public void addMessageRequests(MessageStates messageState, Set<MessageRequest> messageRequestsByMessageState) {
        this.messageRequestsByMessageState.put(messageState, messageRequestsByMessageState);
    }

    public Set<MessageRequest> getMessageRequestsByMessageState(MessageStates messageState) {
        return messageRequestsByMessageState.get(messageState);
    }

    public Map<MessageStates, Set<MessageRequest>> getMessageRequests() {
        return messageRequestsByMessageState;
    }
}
