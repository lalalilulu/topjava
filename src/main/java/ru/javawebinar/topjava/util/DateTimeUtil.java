package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T period, T from, T to) {
        return period.compareTo(from) >= 0 && period.compareTo(to) < 0;
    }

    public static <T extends Comparable<T>> boolean isBetweenClose(T period, T from, T to) {
        return period.compareTo(from) >= 0 && period.compareTo(to) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static Optional<LocalDate> createOptionalLd(String value) {
        return value == null || value.isEmpty() ? Optional.empty() : Optional.of(LocalDate.parse(value));
    }

    public static Optional<LocalTime> createOptionalLt(String value) {
        return value == null || value.isEmpty() ? Optional.empty() : Optional.of(LocalTime.parse(value));
    }
}

