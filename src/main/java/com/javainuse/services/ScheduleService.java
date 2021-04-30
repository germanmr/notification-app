package com.javainuse.services;

import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

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
