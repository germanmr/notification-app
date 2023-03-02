package com.acme.notificationapp;

import com.acme.notificationapp.domain.Client;
import com.acme.notificationapp.domain.Medias;
import com.acme.notificationapp.domain.Publication;
import lombok.Data;

@Data
public class TestData {
    public static Client CLIENT = new Client(1L, "Client 1", Medias.MAIL, "MAIL");
    public static Publication PUBLICATION = new Publication(1L, "This is my message to you");
}
