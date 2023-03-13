package com.acme.notificationapp.domain;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

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

    @Field(type = FieldType.Text, name = "event_type")
    private String eventType;
}
