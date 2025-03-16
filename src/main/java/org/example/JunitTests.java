package org.example;

public class JunitTests {
    public static void main(String[] args) {
        System.out.println("\n" + factorial(5));

        System.out.println("\n" + square(2, 3, 4));

        try {
            System.out.println("\n" + arithmetic(2, "/", 0));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        compare(5, 2);
    }

    public static String[] colors = {"\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[37m", "\u001B[35m", "\u001B[31m", "\u001B[32m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[37m", "\u001B[35m"};


    public static int factorial(int f) {
        if (f >= 0) {
            int factorial = 1;
            for (int i = 1; i <= f; i++) factorial *= i;
            return factorial;
        } else throw new IllegalArgumentException("\nНе правильно указанно число для вычисления факториала: " + f);
    }

    public static double square(int sideA, int sideB, int sideC) {
        double p = (double) (sideA + sideB + sideC) / 2;
        return Math.sqrt(p * (p - sideA) * (p - sideB) * (p - sideC));
    }

    public static double arithmetic(int a, String s, int b) {
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

    public static boolean compare(int a, int b) {
        if (a > b) {
            System.out.print("\n" + a + " больше " + b);
        } else if (a == b) {
            System.out.print("\n" + a + " равно " + b);
        } else
            System.out.print("\n" + b + " больше " + a);
        return a > b;
    }
}