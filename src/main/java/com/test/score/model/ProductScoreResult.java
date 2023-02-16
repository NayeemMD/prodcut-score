package com.test.score.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <b>Result</b> holds the ultimate result
 */
@Data
@AllArgsConstructor
public class ProductScoreResult {
   private Map<String, List<ProductScore>> result;
}

