package com.acme.notificacionapp.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Embeddable
public class Client {

    @Column(name = "client_id")
    private Integer id;
    @Column(name = "client_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "favorite_media")
    private Medias favoriteMedia;

    public Client() {
    }

    public Client(String name, Medias favoriteMedia) {
        this.name = name;
        this.favoriteMedia = favoriteMedia;
    }

    public Client(String name, String favoriteMedia) {
        this.name = name;
        this.favoriteMedia = Medias.valueOf(favoriteMedia);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(name, client.name) && favoriteMedia == client.favoriteMedia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, favoriteMedia);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", favoriteMedia=").append(favoriteMedia);
        sb.append('}');
        return sb.toString();
    }
}
