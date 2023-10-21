package com.example.mxbeans;

import java.util.List;

public class MediumLoadGeneratorThread extends LoadGeneratorThread {
    @Override
    public String getThreadType() {
        return "MED_LOAD";
    }

    @Override
    protected void generateLoad(List<String> strings) {
        // Perform a heavy computational task - calculating prime numbers
        do1();
    }

    void do1() {
        do2();
    }

    void do2() {
        do3();
    }

    void do3() {
        do4();
    }

    void do4() {
        for (int i = 2; i <= 10000; i++) {
            if (isPrime(i)) {
                i = i;
            }
        }
    }
    // Check if a number is prime
    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
