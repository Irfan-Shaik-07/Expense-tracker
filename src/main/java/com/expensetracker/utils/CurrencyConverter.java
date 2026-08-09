package com.expensetracker.utils;

import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {

    // Exchange rates relative to INR base
    private static final Map<String, Double> RATES_TO_INR = new HashMap<>();
    private static final Map<String, String> SYMBOLS = new HashMap<>();

    static {
        RATES_TO_INR.put("INR", 1.0);
        RATES_TO_INR.put("USD", 83.25);
        RATES_TO_INR.put("EUR", 90.50);
        RATES_TO_INR.put("GBP", 105.75);
        RATES_TO_INR.put("JPY", 0.56);
        RATES_TO_INR.put("CAD", 61.40);
        RATES_TO_INR.put("AUD", 54.80);
        RATES_TO_INR.put("SGD", 61.90);
        RATES_TO_INR.put("AED", 22.65);

        SYMBOLS.put("INR", "₹");
        SYMBOLS.put("USD", "$");
        SYMBOLS.put("EUR", "€");
        SYMBOLS.put("GBP", "£");
        SYMBOLS.put("JPY", "¥");
        SYMBOLS.put("CAD", "CA$");
        SYMBOLS.put("AUD", "A$");
        SYMBOLS.put("SGD", "S$");
        SYMBOLS.put("AED", "AED ");
    }

    public static double convertToINR(double amount, String fromCurrency) {
        if (fromCurrency == null || !RATES_TO_INR.containsKey(fromCurrency)) return amount;
        return amount * RATES_TO_INR.get(fromCurrency);
    }

    public static double convertFromINR(double amountInINR, String targetCurrency) {
        if (targetCurrency == null || !RATES_TO_INR.containsKey(targetCurrency)) return amountInINR;
        return amountInINR / RATES_TO_INR.get(targetCurrency);
    }

    public static String getSymbol(String currencyCode) {
        return SYMBOLS.getOrDefault(currencyCode, "₹");
    }

    public static String formatAmount(double amount, String currencyCode) {
        String symbol = getSymbol(currencyCode);
        return String.format("%s%,.2f", symbol, amount);
    }
}
