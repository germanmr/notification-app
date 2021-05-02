package com.acme.notificacionapp.schedule;

import com.acme.notificacionapp.config.Profiles;
import com.acme.notificacionapp.services.DispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Profile("!" + Profiles.TEST)
@Service
public class ScheduleDispatcherService {

    @Autowired
    private @Qualifier("mailDispatcherServiceImpl")
    DispatcherService mailDispatcherServiceImpl;
    @Autowired
    private @Qualifier("pushNotificationsDispatcherServiceImpl")
    DispatcherService pushNotificationsDispatcherServiceImpl;
    @Autowired
    private @Qualifier("smsDispatcherServiceImpl")
    DispatcherService smsDispatcherService;

    @Scheduled(fixedRate = 1000)
//    @EventListener(ApplicationReadyEvent.class)
    public void publishMailBatchMessages() {
        // Try to fire 10 threads, just to avoid Thread pool
        for (int i = 0; i < 10; i++) {
            this.smsDispatcherService.dispatch(10L);
        }
    }

//    @Scheduled(fixedRate = 300)
//    public void publishPushNotificationsBatchMessages() {
//        this.dispatcherService.dispatch(PUSH_NOTIFICATIONS_TOPIC);
//    }
//
//    @Scheduled(fixedRate = 6000)
//    public void publishSMSBatchMessages() {
//        this.dispatcherService.dispatch(SMS_TOPIC);
//    }


}
