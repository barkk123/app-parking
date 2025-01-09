package com.koma.appparking.services.common;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@UtilityClass
public class FeeFormatter {

    public static String format(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }

        var decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(amount) + " PLN";
    }
}
