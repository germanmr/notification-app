package com.acme.notificationapp.services;

import com.acme.notificationapp.config.Profiles;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

// test annotations
@ActiveProfiles(Profiles.TEST)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
public class DispatcherServiceImplTest {

    @Autowired
    private @Qualifier("mailDispatcherServiceImpl")
    DispatcherService mailDispatcherService;

    @Before
    public void setUp() {
    }

    @Test
    public void testGettingData() {
        mailDispatcherService.dispatch(10L);
    }
}