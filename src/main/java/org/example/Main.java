package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n" + factorial(5));

        System.out.println("\n" + square(2, 3, 4));

        try {
            System.out.println("\n" + arithmetic(2, "f", 0));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        compare(5, 2);
    }

    static int factorial(int f) {
        int factorial = 1;
        for (int i = 1; i <= f; i++) factorial *= i;
        return factorial;
    }

    static double square(int sideA, int sideB, int sideC) {
        double p = (double) (sideA + sideB + sideC) / 2;
        return Math.sqrt(p * (p - sideA) * (p - sideB) * (p - sideC));
    }

    static double arithmetic(int a, String s, int b) throws ArithmeticException {
        double d;
        switch (s) {
            case "+":
                d = a + b;
                break;
            case "-":
                d = a - b;
                break;
            case "*":
                d = a * b;
                break;
            case "/":
                d = (double) a / b;
                break;
            default:
                throw new IllegalArgumentException("\nНе правильно указанно арифметическое действие: " + s);
        }
        return d;
    }

    static void compare(int a, int b) {
        if (a > b) {
            System.out.println("\n" + a + " больше " + b);
        } else System.out.println("\n" + b + " больше " + a);
    }
}