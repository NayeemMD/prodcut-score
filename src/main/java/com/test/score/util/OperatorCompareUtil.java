package com.test.score.util;

import com.test.score.model.Operator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OperatorCompareUtil {
    public static boolean compare(String lValue, Object rValue, Operator operator) {
        switch (operator) {

            case EQ -> {
                return lValue == rValue;
            }
            case NE -> {
                return lValue != rValue;
            }
            case GT -> {
                return Double.parseDouble( lValue) > (double) rValue;
            }
            case LT -> {
                return Double.parseDouble(lValue) < (double) rValue;
            }
            case GE -> {
                return Double.parseDouble(lValue) >= (double) rValue;
            }
            case LE -> {
                return Double.parseDouble(lValue) <= (double) rValue;
            }
            default -> {
                return false;
            }
        }
    }

}
