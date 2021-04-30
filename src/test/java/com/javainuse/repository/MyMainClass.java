package com.javainuse.repository;

import static java.lang.Math.*;

public class MyMainClass {

    public static void main(String[] args) {

//        Long material = new Long("456464644");

//        System.out.println(material);

        // Positive points
        System.out.println(calculateDistance(1, 1, 8, 4));

        // Opposite point
        System.out.println(calculateDistance(1, 8, -1, -8));

        // The same point!
        System.out.println(calculateDistance(1, 8, 1, 8));
    }

    private static double calculateDistance(double a1, double b1, double a2, double b2) {
        double result = calculateSubTerm(a1, a2) + calculateSubTerm(b1, b2);
        return sqrt(result);
    }

    private static double calculateSubTerm(double c1, double c2) {
        return pow(abs(c2 - c1), 2);
    }

}
