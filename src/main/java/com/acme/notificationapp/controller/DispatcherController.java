package com.acme.notificationapp.controller;

import com.acme.notificationapp.services.DispatcherService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @GetMapping("/testing")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value =
            {@ApiResponse(
                    responseCode = "200",
                    description = "Process triggered correctly"

            ), @ApiResponse(
                    responseCode = "204",
                    description = "Process triggered correctly"

            ), @ApiResponse(
                    responseCode = "400",
                    description = "Process triggered correctly"

            ), @ApiResponse(
                    responseCode = "500",
                    description = "Process triggered correctly"

            )
            }
    )
    public ResponseEntity testing(@RequestParam(name = "trigger", required = true) @NotNull
                                  Integer trigger) {
        if (trigger == 200) {
            return ResponseEntity.ok().build();
        }
        if (trigger == 204) {
            return ResponseEntity.noContent().build();
        }
        if (trigger == 400) {
            return ResponseEntity.badRequest().build();
        }
        if (trigger == 500) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();

    }

    public void loadSingle(@RequestParam(name = "lastName", required = true) @NotNull
                           @NotBlank
                           @Size(max = 10) String lastName) throws Exception {
        System.out.println(lastName);
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
