package com.acme.notificacionapp.services;

import com.acme.notificacionapp.domain.Client;
import com.acme.notificacionapp.domain.Publication;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

@Service
public class DataLoaderImpl implements DataLoader {

    private static final Logger logger = LoggerFactory.getLogger(DataLoaderImpl.class);

    private final MessageRequestService messageRequestService;

    @Autowired
    public DataLoaderImpl(MessageRequestService messageRequestService) {
        this.messageRequestService = messageRequestService;
    }

    // @EventListener(ApplicationReadyEvent.class) When application is ready this fires
    // @EventListener(ApplicationReadyEvent.class)
    @Override
    public void loadData() {

        try {
            Publication publication = getMessages();
            if (Strings.isNullOrEmpty(publication.getMessages())) {
                logger.error("There is nothing to publish");
                return;
            }
            logger.info("Publication obtained successfully");

            List<Client> clients = getClients();
            if (clients == null || clients.isEmpty()) {
                logger.error("There are no clients");
                return;
            }
            logger.info("Clients obtained successfully");

            logger.info("Building Message Requests");
            messageRequestService.saveData(clients, publication);
            logger.info("Generating request proccess finished successfully");
        } catch (Exception e) {
            logger.error("There was an error when building message requests: " + e.getMessage());
            return;
        }
    }

    private Publication getMessages() throws IOException {

        String fileName = "messages.csv";
        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(classLoader.getResource(fileName).getFile());

        //File is found
        System.out.println("File Found : " + file.exists());

        //Read File Content
        String content = new String(Files.readAllBytes(file.toPath()));
        System.out.println(content);
        Publication publication = new Publication(content);

        return publication;
    }

    private List<Client> getClients() throws IOException {

        String fileName = "clients.csv";
        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(classLoader.getResource(fileName).getFile());

        //File is found
        System.out.println("File Found : " + file.exists());

        //Read File Content
        String content = new String(Files.readAllBytes(file.toPath()));
        System.out.println(content);
        String[] lines = content.split("\\r?\\n");

        List<Client> clients = new LinkedList<>();

        for (String line : lines) {
            String[] fields = line.split(";");
            clients.add(new Client(fields[0], fields[1], fields[2]));
        }

        return clients;
    }
}
