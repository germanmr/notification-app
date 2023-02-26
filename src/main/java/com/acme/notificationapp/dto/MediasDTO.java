package com.acme.notificationapp.dto;

public enum MediasDTO {

    SMS("SMS"),
    MAIL("MAIL"),
    PUSH_NOTIFICATION("PUSH_NOTIFICATION");

    private final String description;

    MediasDTO(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
