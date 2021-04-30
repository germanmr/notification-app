package com.acme.notificacionapp.config;

import com.acme.notificacionapp.NotificationApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@ComponentScan(
        basePackages = "com.acme.notificacionapp",
        basePackageClasses = {NotificationApplication.class})
public class ThreadPoolTaskSchedulerConfig {

    private final int POOL_SIZE = 30;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
        threadPoolTaskScheduler.setThreadNamePrefix("notification-app-task-pool");

        return threadPoolTaskScheduler;
    }
}