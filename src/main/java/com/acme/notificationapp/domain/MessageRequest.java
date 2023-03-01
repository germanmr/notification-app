package com.acme.notificationapp.domain;

import com.acme.notificationapp.dto.MessageRequestDTO;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "message_request")
public class MessageRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "uuid")
    private UUID uuid;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "publication_id")
    private Publication publication;
    @Column(name = "error")
    private String error;
    @Enumerated(EnumType.STRING)
    @Column(name = "message_state")
    private MessageStates messageState;

    public MessageRequest() {
    }

    public MessageRequest(Client client, Publication publication) {
        this.uuid = UUID.randomUUID();
        this.client = client;
        this.publication = publication;
        this.messageState = MessageStates.PENDING;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public String getError() {
        return error;
    }

    public MessageStates getMessageState() {
        return messageState;
    }

    public void setMessageState(MessageStates messageState) {
        this.messageState = messageState;
    }

    public void setSuccess() {
        this.messageState = MessageStates.SUCCESS;
        this.error = null;
    }

    public void setError(String errorMessage) {
        this.messageState = MessageStates.ERROR;
        this.error = errorMessage;
    }

    public void setBeginProcessing() {
        this.messageState = MessageStates.PROCESSING;
        this.error = null;
    }

    public void setAcknowledgement(MessageRequestDTO messageRequestDTO) {
        if (MessageStates.SUCCESS.equals(MessageStates.valueOf(messageRequestDTO.getMessageState().toString()))) {
            this.setSuccess();
        } else {
            this.setError(messageRequestDTO.getError());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageRequest that = (MessageRequest) o;
        return Objects.equals(id, that.id) && Objects.equals(uuid, that.uuid) && Objects.equals(client, that.client) && Objects.equals(publication, that.publication) && Objects.equals(error, that.error) && messageState == that.messageState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, client, publication, error, messageState);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MessageRequest{");
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
