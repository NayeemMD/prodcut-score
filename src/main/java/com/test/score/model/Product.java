package com.test.score.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <b>Product</b> pojo holds all the product related parameters
 *  Limited attributes added useful for the small poc
 * <i>Double</i> datatype is not applicable for all the types listed in practical but to keep it simple, added everything as double.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String id;
    private String name;
    private String color;
    private Double cost;
    private Double weight;
    private Double quantity;
}
