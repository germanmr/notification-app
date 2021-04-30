package com.javainuse.repository;

import com.javainuse.entity.Account;
import com.javainuse.services.RocketFuelStation;
import com.javainuse.services.Spaceship;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.PessimisticLockException;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

// test annotations
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ConcurrencyTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Before
    public void setUp() {

        Account saved = accountRepository.save(new Account("1234", 100L));

//        ThreadPoolExecutor executor =
//                (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

//        executor.submit(() -> {
//            Thread.sleep(1000);
//            return null;
//        });
//        executor.submit(() -> {
//            Thread.sleep(1000);
//            return null;
//        });
//        executor.submit(() -> {
//            Thread.sleep(1000);
//            return null;
//        });
    }

    @Test
    public void testMap() {

        Map<String, Integer> stringIntegerMap = new ConcurrentHashMap<>();
        stringIntegerMap.put("German", 39);
        stringIntegerMap.put("German", 40);

        stringIntegerMap.forEach((s, integer) -> System.out.println(s + " " + integer));
    }

    @Test
    public void test() throws InterruptedException {
        RocketFuelStation rfs = new RocketFuelStation(100);
        Spaceship ship = new Spaceship(rfs);
        Spaceship ship2 = new Spaceship(rfs);

        ship.start();
        ship2.start();

        ship.join();
        ship2.join();

        System.out.println("Ship 1 fueled up and now has: " + ship.getFuel() + "l of fuel");
        System.out.println("Ship 2 fueled up and now has: " + ship2.getFuel() + "l of fuel");

        System.out.println("Rocket Fuel Station has " + rfs.getFuelAmount() + "l of fuel left in the end.");
    }

    @Test
    public void testLockWithoutThreads() {
//        Account saved = accountRepository.save(new Account("1234", 100L));
//        Integer id = saved.getId();
//
//        Account account = transactionTemplate.execute(status -> {
//            try {
//                Account forUpdateById = accountRepository.getForUpdateById(id);
//                System.out.println(forUpdateById);
//
//                Account forUpdateById2 = accountRepository.getForUpdateById(id);
//
//                return forUpdateById;
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//                return null;
//            }
//        });
//
//        System.out.println(account);


//        Account lockedSaved = accountRepository.getForUpdateById(id);
//        Account anotherLockedSaved = accountRepository.getForUpdateById(id);
    }

    @Test
    public void testLock() throws Exception {

        Account saved = accountRepository.save(new Account("1234", 100L));

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        Future<Account> lockedAccount = executor.submit(() -> {
            try {
                System.out.println("Locking First?: " + Thread.currentThread().getName());
                Account result = transactionTemplate.execute(status -> {
                    // Open a Transaction and wait, so that the other thread tries the same!
                    System.out.println("Before locking");
                    Account accountLocked = accountRepository.getForUpdateById(1);
                    System.out.println("After First: " + accountLocked);
                    ReentrantLock lock = new ReentrantLock();
                    try {
                        boolean isLockAcquired = lock.tryLock(1, TimeUnit.SECONDS);
                        if (isLockAcquired) {
                            System.out.println("Locked!!");
                        }
//                    sleep(5000);
                        return accountLocked;
                    } catch (InterruptedException e) {
                        return null;
                    } finally {
                        lock.lock();
                    }
                });

                System.out.println("First: " + result);
                System.out.println("First");
                return result;
            } catch (Exception e) {
                return null;
            }
        });
        Future<Account> cannotLockAccount = executor.submit(() -> {
            try {
                sleep(5000);
                System.out.println("Locking second?: " + Thread.currentThread().getName());
                Account result = transactionTemplate.execute(status -> {
                    // Open a Transaction and wait, so that the other thread tries the same!
                    Account accountLocked = accountRepository.getForUpdateById(1);
                    System.out.println("second: " + accountLocked);
                    return accountLocked;
                });

                System.out.println("second: " + result);
                System.out.println("second");
                return result;
            } catch (PessimisticLockException e) {
                System.out.println("PessimisticLockException: " + e.getEntity());
                return null;
            } catch (Exception e) {
                return null;
            }
        });

        // Tell threads to finish off.
        executor.shutdown();
        // Wait for everything to finish.
        while (!executor.awaitTermination(180, TimeUnit.SECONDS)) {
            System.out.println("Awaiting completion of threads.");
        }

    }

    @Test
    public void testLockWithOutDatabase() throws Exception {

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            System.out.println("Locking First?: " + Thread.currentThread().getName());
            sleep(5);
        });
        executor.submit(() -> {
            System.out.println("Locking second?: " + Thread.currentThread().getName());
        });

        // Tell threads to finish off.
        executor.shutdown();
        // Wait for everything to finish.
        while (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("Awaiting completion of threads.");
        }

    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exception: " + e);
        }
    }

}