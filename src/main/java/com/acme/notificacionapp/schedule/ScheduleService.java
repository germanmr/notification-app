package com.acme.notificacionapp.schedule;

import com.acme.notificacionapp.services.DispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private final String MAIL_TOPIC = "MAIL_TOPIC";
    private final String SMS_TOPIC = "SMS_TOPIC";
    private final String PUSH_NOTIFICATIONS_TOPIC = "PUSH_NOTIFICATIONS_TOPIC";

    private final DispatcherService dispatcherService;

    @Autowired
    public ScheduleService(DispatcherService dispatcherService) {
        this.dispatcherService = dispatcherService;
    }

    @Scheduled(fixedRate = 3000)
    public void publishMailBatchMessages() {
        this.dispatcherService.dispatch(MAIL_TOPIC);
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


//    @Autowired
//    ThreadPoolTaskScheduler threadPoolTaskScheduler;
//
//    @PostConstruct
//    void runs() {
//        try {
//            System.out.println("Stooooooop");
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
////        threadPoolTaskScheduler.scheduleAtFixedRate(
////                new RunnableTask("First Task"),
////                new Date(System.currentTimeMillis() + 3000));
//        threadPoolTaskScheduler.scheduleAtFixedRate(
//                new RunnableTask("Second"),
//                8000);
//        threadPoolTaskScheduler.scheduleAtFixedRate(
//                new RunnableTask("Third"),
//                8000);
//
//        //threadPoolTaskScheduler.scheduleWithFixedDelay(new RunnableTask("Every five seconds!"),5000);
//        threadPoolTaskScheduler.scheduleWithFixedDelay(new RunnableTask("Every five seconds after a date"), new Date(),5000);
//    }
//
//    class RunnableTask implements Runnable {
//
//        private String message;
//
//        public RunnableTask(String message) {
//            this.message = message;
//        }
//
//        @Override
//        public void run() {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(new Date() + " Runnable Task with " + message
//                    + " on thread " + Thread.currentThread().getName());
//        }
//    }

}
