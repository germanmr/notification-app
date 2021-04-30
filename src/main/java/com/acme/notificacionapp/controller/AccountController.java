package com.acme.notificacionapp.controller;

import com.acme.notificacionapp.entity.Account;
import com.acme.notificacionapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionTemplate transactionTemplate;

    @PutMapping("/")
    public Account create() {
        Account account = accountRepository.save(new Account("21q3", 123L));
        return account;
    }

    @GetMapping("/")
    public Account getAccount() {
        System.out.println("TransactionSynchronizationManager.isActualTransactionActive(): " + TransactionSynchronizationManager.isActualTransactionActive());
        Account account = accountRepository.save(new Account("21q3", 123L));
        System.out.println("TransactionSynchronizationManager.isActualTransactionActive(): " + TransactionSynchronizationManager.isActualTransactionActive());
        Integer id = account.getId();
        System.out.println("TransactionSynchronizationManager.isActualTransactionActive(): " + TransactionSynchronizationManager.isActualTransactionActive());
        Account saved = transactionTemplate.execute(status -> {
            try {
                System.out.println("TransactionSynchronizationManager.isActualTransactionActive(): " + TransactionSynchronizationManager.isActualTransactionActive());
                Account savedAccount = accountRepository.getForUpdateById(id);
                System.out.println(savedAccount);

                Account thisShouldFail = accountRepository.getForUpdateById(id);
                System.out.println("TransactionSynchronizationManager.isActualTransactionActive(): " + TransactionSynchronizationManager.isActualTransactionActive());
                return savedAccount;
            } catch (Exception e) {
                System.out.println("TransactionSynchronizationManager.isActualTransactionActive(): " + TransactionSynchronizationManager.isActualTransactionActive());
                System.out.println(e.getMessage());
                return null;
            }
        });
        System.out.println("TransactionSynchronizationManager.isActualTransactionActive(): " + TransactionSynchronizationManager.isActualTransactionActive());
        return saved;
    }

}
