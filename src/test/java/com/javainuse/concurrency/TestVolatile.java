package com.javainuse.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class TestVolatile extends Thread {

    // Without volatile on keepRunning the thread hangs forever,
    // because the "t.keepRunning = false;" is not updated on the TestVolatile Thread, so if keeps on going
    // Once you mark keepRunning as volatile - it stops after t.keepRunning = false;
    //volatile
    volatile boolean keepRunning = true;

    public void run() {
        long count = 0;
        while (keepRunning) {
            count++;
        }

        System.out.println("Thread terminated." + count);
    }

    public static void main(String[] args) throws InterruptedException {
        TestVolatile t = new TestVolatile();
        t.start();
//        Thread.sleep(1000);
        Lock lock = new ReentrantLock();
        boolean locked = lock.tryLock();
        if (locked) {
            System.out.println("Locked...");
        } else {
            System.out.println("NOT locked!!");
        }

        long j = 0;
        while (j < 10000000000L) {
            j++;
        }
        lock.unlock();

        System.out.println("after sleeping in main");
        t.keepRunning = false;
        t.join();
        System.out.println("keepRunning set to " + t.keepRunning);
    }
}