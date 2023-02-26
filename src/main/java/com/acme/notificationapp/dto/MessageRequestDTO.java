package com.acme.notificationapp.dto;

import java.util.Objects;
import java.util.UUID;

public class MessageRequestDTO {

    private Long id;
    private UUID uuid;
    private ClientDTO client;
    private PublicationDTO publication;
    private String error;
    private MessageStatesDTO messageState;

    public MessageRequestDTO() {
    }

    public MessageRequestDTO(Long id, UUID uuid, ClientDTO client, PublicationDTO publication, String error, MessageStatesDTO messageState) {
        this.id = id;
        this.uuid = uuid;
        this.client = client;
        this.publication = publication;
        this.error = error;
        this.messageState = messageState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public PublicationDTO getPublication() {
        return publication;
    }

    public void setPublication(PublicationDTO publication) {
        this.publication = publication;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public MessageStatesDTO getMessageState() {
        return messageState;
    }

    public void setMessageState(MessageStatesDTO messageState) {
        this.messageState = messageState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageRequestDTO that = (MessageRequestDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(uuid, that.uuid) && Objects.equals(client, that.client) && Objects.equals(publication, that.publication) && Objects.equals(error, that.error) && messageState == that.messageState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, client, publication, error, messageState);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MessageRequestDTO{");
        sb.append("id=").append(id);
        sb.append(", uuid=").append(uuid);
        sb.append(", client=").append(client);
        sb.append(", publication=").append(publication);
        sb.append(", error='").append(error).append('\'');
        sb.append(", messageState=").append(messageState);
        sb.append('}');
        return sb.toString();
    }
}
