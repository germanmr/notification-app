package com.acme.notificationapp.domain;

import lombok.Data;

import javax.persistence.*;

//@Embeddable
@Entity
@Data
public class Publication implements HasAdaptedMessage {

    @Id
    @Column(name = "publication_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String messages;

    public Publication() {
    }

    public Publication(String messages) {
        this.messages = messages;
    }
//
//    public Publication() {
//    }
//
//    public Publication(String messages) {
//        this.messages = messages;
//    }
//
//    public String getMessages() {
//        return messages;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setMessages(String messages) {
//        this.messages = messages;
//    }

    @Override
    public String getAdaptedMessageByMedia(Medias favoriteMedia) {
        return Medias.SMS.equals(favoriteMedia) ? this.messages.substring(1, 254) : this.messages;
    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Publication that = (Publication) o;
//        return Objects.equals(id, that.id) && Objects.equals(messages, that.messages);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, messages);
//    }
//
//    @Override
//    public String toString() {
//        return "Publication{" +
//                "id=" + id +
//                ", messages='" + messages + '\'' +
//                '}';
//    }
}
