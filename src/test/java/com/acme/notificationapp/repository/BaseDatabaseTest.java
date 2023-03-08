package com.acme.notificationapp.repository;

import com.acme.notificationapp.domain.Client;
import com.acme.notificationapp.domain.Medias;
import com.acme.notificationapp.domain.Publication;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ActiveProfiles("test")
public abstract class BaseDatabaseTest {

//    private static final PostgreSQLContainer POSTGRES_SQL_CONTAINER;
//
//    static {
//        POSTGRES_SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.1-alpine"));
//        POSTGRES_SQL_CONTAINER.start();
//    }
//
//    @DynamicPropertySource
//    static void overrideTestProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", POSTGRES_SQL_CONTAINER::getJdbcUrl);
//        registry.add("spring.datasource.username", POSTGRES_SQL_CONTAINER::getUsername);
//        registry.add("spring.datasource.password", POSTGRES_SQL_CONTAINER::getPassword);
//    }

    @Autowired
    @PersistenceContext
    protected EntityManager entityManager;
    @Autowired
    protected ClientReadRepository clientReadRepository;
    @Autowired
    protected PublicationReadRepository publicationReadRepository;
    @Autowired
    protected ClientWriteRepository clientWriteRepository;
    @Autowired
    protected PublicationWriteRepository publicationWriteRepository;

    @Autowired
    protected MessageRequestWriteRepository messageRequestWriteRepository;
    @Autowired
    protected MessageRequestReadRepository messageRequestReadRepository;

    @BeforeEach
    void setUp() {
        messageRequestWriteRepository.deleteAll();
        clientWriteRepository.deleteAll();
        publicationWriteRepository.deleteAll();
    }
}
