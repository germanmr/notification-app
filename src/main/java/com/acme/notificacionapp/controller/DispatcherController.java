package com.acme.notificacionapp.controller;

import com.acme.notificacionapp.services.DispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dispatcher")
public class DispatcherController {

    @Autowired
    private @Qualifier("mailDispatcherServiceImpl")
    DispatcherService mailDispatcherServiceImpl;
    @Autowired
    private @Qualifier("pushNotificationsDispatcherServiceImpl")
    DispatcherService pushNotificationsDispatcherServiceImpl;
    @Autowired
    private @Qualifier("smsDispatcherServiceImpl")
    DispatcherService smsDispatcherService;

    @GetMapping("/batch")
    public void loadBatch() {
        this.mailDispatcherServiceImpl.dispatch(1L);
    }

    @GetMapping("/single")
    public void loadSingle() throws Exception {
        this.mailDispatcherServiceImpl.dispatchOne();
    }

    @GetMapping("/transactionhelper")
    public void transactionhelper() throws Exception {
        this.mailDispatcherServiceImpl.dispatchOne();
    }

    @GetMapping("/singlekafka")
    public void singlekafka() {
        this.mailDispatcherServiceImpl.dispatchOneKafka();
    }

    @GetMapping("/executeintransaction")
    public void executeintransaction() throws Exception {
        this.mailDispatcherServiceImpl.dispatchOneWithTransaction();
    }
}
