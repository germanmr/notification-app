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
    public void loadData() {
        this.smsDispatcherService.dispatch(1L);
    }
}
