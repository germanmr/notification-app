package com.acme.notificacionapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;


@Profile(Profiles.TEST)
@Configuration
//@MockBean({MessageRequestRepository.class})
public class TestConf {

}