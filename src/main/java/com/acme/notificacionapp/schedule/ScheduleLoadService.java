package com.acme.notificacionapp.schedule;

import com.acme.notificacionapp.config.Profiles;
import com.acme.notificacionapp.services.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Profile("!" + Profiles.TEST)
@Service
public class ScheduleLoadService {

    @Autowired
    private final DataLoader dataLoader;

    public ScheduleLoadService(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

//    @Scheduled(fixedRate = 3000)
    public void publishMailBatchMessages() {
        this.dataLoader.loadData();
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
