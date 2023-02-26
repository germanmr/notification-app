package com.acme.notificationapp.domain;

public enum Medias {

    SMS("SMS"),
    MAIL("MAIL"),
    PUSH_NOTIFICATION("PUSH_NOTIFICATION");

    private final String description;

    Medias(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
