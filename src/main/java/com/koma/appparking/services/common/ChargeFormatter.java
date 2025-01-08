package com.koma.appparking.services.common;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Component
public class ChargeFormatter {

    //napisać metode która przekształci czyli bigdecimala na stringa a string będzie w postacni polskich zł czyli np 15.99PLN
    public String formatToPLN(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }

        var decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(amount) + " PLN";
    }
}
