package com.trabalho.sistemacompradecursos.utils;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FormatUtils {

    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("R$ #,##0.00");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public static String formatMoney(Double value) {
        if (value == null) return "R$ 0,00";
        return MONEY_FORMAT.format(value);
    }

    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMAT) : "";
    }

    public static String formatTime(LocalTime time) {
        return time != null ? time.format(TIME_FORMAT) : "";
    }

    public static Double parseMoney(String value) {
        if (value == null || value.isBlank()) return 0.0;
        String cleaned = value.replace("R$", "").replace(".", "").replace(",", ".").trim();
        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid money format: " + value);
        }
    }

    public static BigDecimal toBigDecimal(Double value) {
        return value != null ? BigDecimal.valueOf(value) : BigDecimal.ZERO;
    }

    public static Double fromBigDecimal(BigDecimal value) {
        return value != null ? value.doubleValue() : 0.0;
    }
    public static String safeStringNull(Object value){
        return value != null ? String.valueOf(value) : null;
    }

    public static Long safeLongNull(String value){
        return value != null ? Long.parseLong(value) : null;
    }
}
