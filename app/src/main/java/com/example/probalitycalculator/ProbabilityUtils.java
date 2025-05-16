package com.example.probalitycalculator;

public class ProbabilityUtils {

    public static double calculateAndProbability(double probabilityA, double probabilityB) {
        return probabilityA * probabilityB;
    }

    public static double calculateOrProbability(double probabilityA, double probabilityB) {
        return probabilityA + probabilityB - (probabilityA * probabilityB);
    }

    public static double calculateConditionalProbability(double probabilityAandB, double probabilityB) {
        return probabilityAandB / probabilityB;
    }
}
