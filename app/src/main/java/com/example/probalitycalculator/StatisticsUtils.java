package com.example.probalitycalculator;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class StatisticsUtils {

    public static double calculateMean(List<Double> data) {
        double sum = 0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.size();
    }

    public static double calculateMedian(List<Double> data) {
        Collections.sort(data);
        int size = data.size();
        if (size % 2 == 0) {
            return (data.get(size / 2 - 1) + data.get(size / 2)) / 2.0;
        } else {
            return data.get(size / 2);
        }
    }

    public static double calculateVariance(List<Double> data) {
        double mean = calculateMean(data);
        double sumOfSquaredDifferences = 0;
        for (double value : data) {
            sumOfSquaredDifferences += Math.pow(value - mean, 2);
        }
        return sumOfSquaredDifferences / data.size();
    }

    public static double calculateStandardDeviation(List<Double> data) {
        return Math.sqrt(calculateVariance(data));
    }
}
