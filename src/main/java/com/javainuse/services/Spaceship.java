package com.javainuse.services;

public class Spaceship extends Thread {

    private int fuel;
    private RocketFuelStation rfs;

    public Spaceship(RocketFuelStation rfs) {
        this.rfs = rfs;
    }

    public void refill(int amount) {
        fuel += amount;
    }

    // Getters and Setters

    public int getFuel() {
        return fuel;
    }

    public void run() {
        rfs.refillShip(this, 50);
    }


}