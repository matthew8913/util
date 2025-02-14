package ru.matthewyurkevich;

public class StatisticsCollector {
    private int integerCount = 0;
    private int floatCount = 0;
    private int stringCount = 0;

    private Integer minInteger = null;
    private Integer maxInteger = null;
    private long sumInteger = 0;

    private Float minFloat = null;
    private Float maxFloat = null;
    private double sumFloat = 0.0;

    private String shortestString = null;
    private String longestString = null;

    public void collectInteger(String line) {
        integerCount++;
        int value = Integer.parseInt(line);

        if (minInteger == null || value < minInteger) minInteger = value;
        if (maxInteger == null || value > maxInteger) maxInteger = value;
        sumInteger += value;
    }

    public void collectFloat(String line) {
        floatCount++;
        float value = Float.parseFloat(line);

        if (minFloat == null || value < minFloat) minFloat = value;
        if (maxFloat == null || value > maxFloat) maxFloat = value;
        sumFloat += value;
    }

    public void collectString(String line) {
        stringCount++;
        if (shortestString == null || line.length() < shortestString.length()) {
            shortestString = line;
        }
        if (longestString == null || line.length() > longestString.length()) {
            longestString = line;
        }
    }

    public void printShortStatistics() {
        System.out.println("Integers: " + integerCount);
        System.out.println("Floats: " + floatCount);
        System.out.println("Strings: " + stringCount);
    }

    public void printFullStatistics() {
        printShortStatistics();

        if (integerCount > 0) {
            System.out.println("Integers Details:");
            System.out.println("  Min: " + minInteger);
            System.out.println("  Max: " + maxInteger);
            System.out.println("  Sum: " + sumInteger);
            System.out.println("  Average: " + (sumInteger / (double) integerCount));
        }

        if (floatCount > 0) {
            System.out.println("Floats Details:");
            System.out.println("  Min: " + minFloat);
            System.out.println("  Max: " + maxFloat);
            System.out.println("  Sum: " + sumFloat);
            System.out.println("  Average: " + (sumFloat / floatCount));
        }

        if (stringCount > 0) {
            System.out.println("Strings Details:");
            System.out.println("  Shortest: " + shortestString);
            System.out.println("  Longest: " + longestString);
        }
    }
}