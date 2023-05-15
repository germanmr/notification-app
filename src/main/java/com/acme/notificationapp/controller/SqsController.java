package com.acme.notificationapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sqs")
public class SqsController {
//    public void publishExpense(CreateExpenseDto createExpenseDto) {
//        try {
//            GetQueueUrlResult queueUrl = amazonSQSClient.getQueueUrl(messageQueueTopic);
//            log.info("Reading SQS Queue done: URL {}", queueUrl.getQueueUrl());
//            amazonSQSClient.sendMessage(queueUrl.getQueueUrl(), createExpenseDto.getType() + ":" + createExpenseDto.getAmount());
//        } catch (QueueDoesNotExistException | InvalidMessageContentsException e) {
//            log.error("Queue does not exist {}", e.getMessage());
//        }
//    }
}
