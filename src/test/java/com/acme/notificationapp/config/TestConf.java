package com.acme.notificationapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Profile(Profiles.TEST)
@Configuration
public class TestConf {

}
