package com.test.score.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <b>Condition</b> pojo holds the Rule's parameters and operator
 */
@Data
@AllArgsConstructor
public class Condition {
    private String attribute;
    private Object value;
    private Operator operator;
}
