package com.acme.notificationapp.commands;

import com.acme.notificationapp.domain.Client;
import com.acme.notificationapp.domain.Publication;

import java.util.Objects;

public class CreateMessageRequestCommand {
    private Long messageRequestId;
    private Client client;
    private Publication publication;

    public CreateMessageRequestCommand(Long messageRequestId, Client client, Publication publication) {
        this.messageRequestId = messageRequestId;
        this.client = client;
        this.publication = publication;
    }

    public Long getMessageRequestId() {
        return messageRequestId;
    }

    public Client getClient() {
        return client;
    }

    public Publication getPublication() {
        return publication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateMessageRequestCommand that = (CreateMessageRequestCommand) o;
        return Objects.equals(messageRequestId, that.messageRequestId) && Objects.equals(client, that.client) && Objects.equals(publication, that.publication);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageRequestId, client, publication);
    }

    @Override
    public String toString() {
        return "CreateMessageRequestCommand{" +
                "messageRequestId=" + messageRequestId +
                ", client=" + client +
                ", publication=" + publication +
                '}';
    }
}