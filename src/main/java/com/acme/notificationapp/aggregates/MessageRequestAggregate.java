package com.acme.notificationapp.aggregates;

import com.acme.notificationapp.commands.CreateMessageRequestCommand;
import com.acme.notificationapp.commands.UpdateMessageCommand;
import com.acme.notificationapp.domain.MessageRequest;
import com.acme.notificationapp.repository.MessageRequestWriteRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageRequestAggregate {

    private MessageRequestWriteRepository writeRepository;

    @Autowired
    public MessageRequestAggregate(MessageRequestWriteRepository repository) {
        this.writeRepository = repository;
    }

    public MessageRequest handleCreateMessageRequestCommand(@NotNull CreateMessageRequestCommand command) {
        MessageRequest messageRequest = new MessageRequest(command.getClient(), command.getPublication());
        return writeRepository.save(messageRequest);
    }

    public MessageRequest handleUpdateMessageRequestCommand(@NotNull UpdateMessageCommand command) {
        MessageRequest messageRequest = writeRepository.getOne(command.getId());
        messageRequest.setMessageState(command.getMessageState());
        return writeRepository.save(messageRequest);
    }
}