package com.acme.notificationapp.dto;

import java.util.Objects;

public class PublicationDTO {

    private String messages;

    public PublicationDTO() {
    }

    public PublicationDTO(String messages) {
        this.messages = messages;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublicationDTO that = (PublicationDTO) o;
        return Objects.equals(messages, that.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messages);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PublicationDTO{");
        sb.append("messages='").append(messages).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
