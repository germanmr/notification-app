package com.acme.notificacionapp.services;

public interface DispatcherService {

    void dispatch(Long batchSize);

    void dispatchOne() throws Exception;

    void dispatchOneWithTransaction() throws Exception;
}