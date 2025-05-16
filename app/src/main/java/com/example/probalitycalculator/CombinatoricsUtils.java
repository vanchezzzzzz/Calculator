package com.example.probalitycalculator;

import java.math.BigInteger;

public class CombinatoricsUtils {

    public static BigInteger factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Факториал отрицательного числа не определён");
        }
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    // Размещения (Aₖⁿ = n! / (n - k)!)
    public static BigInteger calculateArrangements(int n, int k) {
        if (n < 0 || k < 0) {
            throw new IllegalArgumentException("n и k должны быть ≥ 0");
        }
        if (k > n) {
            return BigInteger.ZERO;
        }
        return factorial(n).divide(factorial(n - k));
    }

    // Сочетания (Cₖⁿ = n! / (k! * (n - k)!))
    public static BigInteger calculateCombinations(int n, int k) {
        if (n < 0 || k < 0) {
            throw new IllegalArgumentException("n и k должны быть ≥ 0");
        }
        if (k > n) {
            return BigInteger.ZERO;
        }
        return factorial(n).divide(factorial(k).multiply(factorial(n - k)));
    }

    // Перестановки (Pₙ = n!)
    public static BigInteger calculatePermutations(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n должно быть ≥ 0");
        }
        return factorial(n);
    }
}