package com.acme.notificationapp.domain;

import com.acme.notificationapp.dto.MessageRequestDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "message_request")
@NoArgsConstructor
public class MessageRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "uuid")
    private UUID uuid;

    // Child
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publication_id")
    private Publication publication;
    @Column(name = "error")
    private String error;
    @Enumerated(EnumType.STRING)
    @Column(name = "message_state")
    private MessageStates messageState;

    public MessageRequest(Client client, Publication publication) {
        this.uuid = UUID.randomUUID();
        this.client = client;
        this.publication = publication;
        this.messageState = MessageStates.PENDING;
    }

    public MessageRequest(UUID uuid, Client client, Publication publication) {
        this.uuid = uuid;
        this.client = client;
        this.publication = publication;
        this.messageState = MessageStates.PENDING;
    }

    public MessageRequest(Long id, UUID uuid, Client client, Publication publication) {
        this.id = id;
        this.uuid = uuid;
        this.client = client;
        this.publication = publication;
        this.messageState = MessageStates.PENDING;
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
        return Objects.equals(id, that.id) && Objects.equals(uuid, that.uuid) && Objects.equals(error, that.error) && messageState == that.messageState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, error, messageState);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MessageRequest{");
        sb.append("id=").append(id);
        sb.append(", uuid=").append(uuid);
        sb.append(", error='").append(error).append('\'');
        sb.append(", messageState=").append(messageState);
        sb.append('}');
        return sb.toString();
    }
}
