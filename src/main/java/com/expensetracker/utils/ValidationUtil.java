package com.expensetracker.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidationUtil {

    public static boolean isPositive(double amount) {
        return amount > 0;
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        if (!isNotEmpty(email)) return false;
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isValidDate(String dateStr) {
        if (!isNotEmpty(dateStr)) return false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            Date d = sdf.parse(dateStr);
            return d != null;
        } catch (ParseException e) {
            return false;
        }
    }
}
