package com.acme.notificationapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class Client {

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "favorite_media")
    private Medias favoriteMedia;

    @Column(name = "favorite_media_identifier")
    private String favoriteMediaIdentifier;

    // Parent
    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<MessageRequest> messageRequests = new ArrayList<>();

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

    public Client(Long id, String name, Medias favoriteMedia, String favoriteMediaIdentifier) {
        this.id = id;
        this.name = name;
        this.favoriteMedia = favoriteMedia;
        this.favoriteMediaIdentifier = favoriteMediaIdentifier;
    }

    public void addMessageRequest(MessageRequest messageRequest) {
        messageRequests.add(messageRequest);
        messageRequest.setClient(this);
    }

    public void removeMessageRequest(MessageRequest messageRequest) {
        messageRequests.remove(messageRequest);
        messageRequest.setClient(null);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
