package com.javainuse.services;

public class RocketFuelStation {
    // The amount of rocket fuel, in liters
    private static int fuelAmount;

    public RocketFuelStation(int i) {
        fuelAmount = i;
    }

    public static int getFuelAmount() {
        return fuelAmount;
    }

    public void refillShip(Spaceship ship, int amount) {
        if (amount <= fuelAmount) {
            ship.refill(amount);
            this.fuelAmount -= amount;
        } else {
            System.out.println("Not enough fuel in the tank!");
        }
    }


}