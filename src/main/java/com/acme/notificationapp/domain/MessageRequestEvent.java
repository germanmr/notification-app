package com.acme.notificationapp.domain;

import com.acme.notificationapp.events.MessageRequestClientUpdatedEvent;
import com.acme.notificationapp.events.MessageRequestCreatedEvent;
import com.acme.notificationapp.events.MessageRequestPublicationUpdatedEvent;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(indexName = "message_request_event")
@EqualsAndHashCode
public class MessageRequestEvent {
    @Id
    @Field(type = FieldType.Text, name = "id")
    private String id;

    @Field(type = FieldType.Text, name = "events")
    private String event;

    @Field(type = FieldType.Text, name = "message_request_id")
    private String messageRequestId;

    @Field(type = FieldType.Text, name = "event_type")
    private String eventType;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSSSSZ")
    private Date created;

    public Class eventTypeResolver() {
        if (eventType == "MessageRequestCreatedEvent") {
            return MessageRequestCreatedEvent.class;
        }
        if (eventType == "MessageRequestClientUpdatedEvent") {
            return MessageRequestClientUpdatedEvent.class;
        }
        if (eventType == "MessageRequestPublicationUpdatedEvent") {
            return MessageRequestPublicationUpdatedEvent.class;
        }
        throw new ClassCastException("No valid class for type: " + eventType);
    }
}
