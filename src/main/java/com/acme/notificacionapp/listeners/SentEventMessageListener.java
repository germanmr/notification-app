package com.acme.notificacionapp.listeners;

import org.springframework.stereotype.Service;

@Service
public class SentEventMessageListener {

//    private static final Logger logger = LoggerFactory.getLogger(SentEventMessageListener.class);
//
//    @Autowired
//    private final ObjectMapper jacksonObjectMapper;
//
//    public SentEventMessageListener(ObjectMapper jacksonObjectMapper) {
//        this.jacksonObjectMapper = jacksonObjectMapper;
//    }
//
//    @KafkaListener(topics = {"SMS_MESSAGE_ACKNOWLEDGEMENT"}, groupId = "group_id")
//    public void consume(String message) {
//        if (Strings.isNullOrEmpty(message)) {
//            logger.error("Message was null!");
//            return;
//        }
//        try{
//            jacksonObjectMapper.readValue(message,MessageRequest.class);
//        }catch (Exception)
//
//
//
//    }
}
