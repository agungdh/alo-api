package com.bpkad.api.alo.helper;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("AdhHelper")
public class AdhHelper {
    public static BigDecimal moneyNullToZero(BigDecimal obj) {
        if (obj == null) {
            return BigDecimal.ZERO;
        }

        return obj;
    }
}
