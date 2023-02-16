package com.test.score.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * <b>Rule</b> pojo holds rules defined the company
 */
@Data
@AllArgsConstructor
public class Rule {
    private List<Condition> conditions;
    private int score;
    private boolean isNegative;
}
