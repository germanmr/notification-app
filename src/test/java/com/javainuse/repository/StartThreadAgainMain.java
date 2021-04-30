package com.javainuse.repository;

// Our Thread!
class CustomThread extends Thread {
    private static int counter = 0;
    public void run() {
        while (counter < 5) {
            try {
                counter++;
                Thread.sleep(3000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            System.out.println("Thread: " + Thread.currentThread().getId() + " says: " + counter);
        }

    }

}

public class StartThreadAgainMain {

    public static void main(String[] args) throws InterruptedException {
        CustomThread ct1 = new CustomThread();
        CustomThread ct2 = new CustomThread();
//        ct1.run();
//        ct2.run();
        ct1.start();
        ct2.start();

        System.out.println("Thread: " + Thread.currentThread().getId() + " says: END");

    }

}
 