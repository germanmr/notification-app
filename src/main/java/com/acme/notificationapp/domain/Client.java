package com.acme.notificationapp.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Embeddable
public class Client {

    @Column(name = "client_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "favorite_media")
    private Medias favoriteMedia;

    @Column(name = "favorite_media_identifier")
    private String favoriteMediaIdentifier;

    public Client() {
    }

    public Client(String name, Medias favoriteMedia, String favoriteMediaIdentifier) {
        this.name = name;
        this.favoriteMedia = favoriteMedia;
        this.favoriteMediaIdentifier = favoriteMediaIdentifier;
    }

    public Client(String name, String favoriteMedia, String favoriteMediaIdentifier) {
        this.name = name;
        this.favoriteMedia = Medias.valueOf(favoriteMedia);
        this.favoriteMediaIdentifier = favoriteMediaIdentifier;
    }

    public String getName() {
        return name;
    }

    public Medias getFavoriteMedia() {
        return favoriteMedia;
    }

    public void setFavoriteMedia(Medias favoriteMedia) {
        this.favoriteMedia = favoriteMedia;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFavoriteMediaIdentifier() {
        return favoriteMediaIdentifier;
    }

    public void setFavoriteMediaIdentifier(String favoriteMediaIdentifier) {
        this.favoriteMediaIdentifier = favoriteMediaIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name) && favoriteMedia == client.favoriteMedia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, favoriteMedia);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append(", name=").append(name);
        sb.append(", favoriteMedia=").append(favoriteMedia);
        sb.append('}');
        return sb.toString();
    }
}
