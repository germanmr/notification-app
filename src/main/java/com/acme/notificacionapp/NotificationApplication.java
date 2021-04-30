package com.acme.notificacionapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableScheduling
@EnableAsync
//@EntityScan("com.javainuse.entity")
@EntityScan("com.acme")
//@EnableJpaRepositories("com.javainuse.repository")
@EnableJpaRepositories("com.acme")
@EnableTransactionManagement
public class NotificationApplication {

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();
        return executor;
    }

    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(NotificationApplication.class, args);


//        String expression1 = "0 30/30 16 * * ?";
//        String expression2 = "0 30/30 16 ? * *";
//
//        boolean validExpression = CronSequenceGenerator.isValidExpression(expression1);
//        boolean validExpression2 = CronSequenceGenerator.isValidExpression(expression2);
//
//        boolean validExpression3 = CronSequenceGenerator.isValidExpression("0 0 20 28-31 * ?");
//        boolean validExpression4 = CronSequenceGenerator.isValidExpression("0 0 6 1 * ?");
//
//        System.out.println("Algo");
    }

    /**
     * <minute> <hour> <day-of-month> <month> <day-of-week> <command>
     */
//    @Scheduled(cron = "*/10 * * * * *")
//    @Scheduled(cron = "0 39 16 * * ?")
//    @Scheduled(cron = "0 0 12 * * ?")
//    @Scheduled(cron = "0 0 20 28-31 * ?"")
//            @Scheduled(cron = "0 1/1 * * * ?")
//    public void scheduleTaskUsingCronExpression() {
////        String expression1 = "0 30/30 16 * * ?";
////        String expression2 = "0 30/30 16 ? * *";
////
////        boolean validExpression = CronSequenceGenerator.isValidExpression(expression1);
////        boolean validExpression2 = CronSequenceGenerator.isValidExpression(expression2);
//
//        long now = System.currentTimeMillis() / 1000;
//        System.out.println(
//                "schedule tasks using cron jobs - " + now);
//    }

//    @Scheduled(cron = "0 30/30 16 * * ?\")
//    public void scheduleTaskUsingCronExpression2() {
////        String expression1 = "0 30/30 16 * * ?";
////        String expression2 = "0 30/30 16 ? * *";
////
////        boolean validExpression = CronSequenceGenerator.isValidExpression(expression1);
////        boolean validExpression2 = CronSequenceGenerator.isValidExpression(expression2);
//
//        long now = System.currentTimeMillis() / 1000;
//        System.out.println(
//                "schedule tasks using cron jobs - " + now);
//    }


//    @Autowired
//    Environment env;
//
//    @Bean
//    public MyBean myBean() {
//        return new MyBean();
//    }
//
//    @Bean(destroyMethod = "shutdown")
//    public Executor taskExecutor() {
//        return Executors.newScheduledThreadPool(100);
//    }

//    @Override
//    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        taskRegistrar.setScheduler(taskExecutor());
//        taskRegistrar.addTriggerTask(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        myBean().getSchedule();
//                    }
//                },
//                new Trigger() {
//                    @Override
//                    public Date nextExecutionTime(TriggerContext triggerContext) {
//                        Calendar nextExecutionTime = new GregorianCalendar();
//                        Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
//                        nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
//                        nextExecutionTime.add(Calendar.MILLISECOND, env.getProperty("myRate", Integer.class)); //you can get the value from wherever you want
//                        return nextExecutionTime.getTime();
//                    }
//                }
//        );
//    }

}