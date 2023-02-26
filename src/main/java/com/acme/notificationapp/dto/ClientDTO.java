package com.acme.notificationapp.dto;

import com.acme.notificationapp.domain.Medias;

import java.util.Objects;

public class ClientDTO {

    private String name;
    private Medias favoriteMedia;
    private String favoriteMediaIdentifier;

    public ClientDTO() {
    }

    public ClientDTO(String name, Medias favoriteMedia, String favoriteMediaIdentifier) {
        this.name = name;
        this.favoriteMedia = favoriteMedia;
        this.favoriteMediaIdentifier = favoriteMediaIdentifier;
    }

    public ClientDTO(String name, String favoriteMedia) {
        this.name = name;
        this.favoriteMedia = Medias.valueOf(favoriteMedia);
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
        ClientDTO client = (ClientDTO) o;
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
