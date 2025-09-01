package co.com.auth.usecase.utils;

import java.math.BigDecimal;

public final class Constants {
    private Constants() {}

    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final BigDecimal MIN_SALARY = BigDecimal.ZERO;
    public static final BigDecimal MAX_SALARY = new BigDecimal("15000000");
}