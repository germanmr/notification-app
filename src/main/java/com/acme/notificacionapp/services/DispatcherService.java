package com.acme.notificacionapp.services;

import org.springframework.transaction.annotation.Propagation;

public interface DispatcherService {

    void dispatch(Long batchSize);

    void dispatchOne() throws Exception;

    void dispatchOneKafka();

    void dispatchOneWithTransaction() throws Exception;
}