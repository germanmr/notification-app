package com.acme.notificationapp.events;

import com.acme.notificationapp.domain.Client;
import com.acme.notificationapp.domain.Publication;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MessageRequestCreatedEvent extends Event {
    private UUID messageRequestId;
    private Client client;
    private Publication publication;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MessageRequestCreatedEvent that = (MessageRequestCreatedEvent) o;
        return Objects.equals(messageRequestId, that.messageRequestId) && Objects.equals(client, that.client) && Objects.equals(publication, that.publication);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), messageRequestId, client, publication);
    }

    @Override
    public String toString() {
        return "MessageRequestCreatedEvent{" +
                "messageRequestId=" + messageRequestId +
                ", client=" + client +
                ", publication=" + publication +
                ", id=" + id +
                ", created=" + created +
                '}';
    }
}
