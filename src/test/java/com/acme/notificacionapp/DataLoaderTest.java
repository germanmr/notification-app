package com.acme.notificacionapp;

import com.acme.notificacionapp.domain.MessageRequest;
import com.acme.notificacionapp.repository.MessageRequestRepository;
import com.acme.notificacionapp.services.DataLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// test annotations
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
public class DataLoaderTest {

    @Autowired
    private DataLoader dataLoader;

    @Autowired
    private MessageRequestRepository messageRequestRepository;

    @Before
    public void setUp() {
    }

    @Test
    public void testGettingData() {

        dataLoader.loadData();

        List<MessageRequest> all = messageRequestRepository.findAll();
        all.stream().forEach(MessageRequest::toString);

    }
}