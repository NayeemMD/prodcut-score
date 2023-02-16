package com.test.score.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <b>ProductScore</b> pojo to hold the final result attributes
 */
@Data
@AllArgsConstructor
public class ProductScore {
    private String productId;
    private Double score;
}
