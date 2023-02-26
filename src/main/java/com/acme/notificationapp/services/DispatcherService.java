package com.acme.notificationapp.services;

public interface DispatcherService {

    void dispatch(Long batchSize);

    void dispatchOne() throws Exception;

    void dispatchOneKafka();

    void dispatchOneWithTransaction() throws Exception;
}