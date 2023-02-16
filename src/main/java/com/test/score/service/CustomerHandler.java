package com.test.score.service;

import com.test.score.model.ProductScoreResult;
import com.test.score.model.Rule;

import java.util.List;

/**
 * <b>CustomerHandler</b> interface represent the activities can be performed by a customer / company
 * In reality, these methods will be exposed as API endpoints
 */
public interface CustomerHandler {
    /**
     * This method used for registering custom rules defined a customer/company
     * @param rules set of rules defined a customer
     */
    void registerBuyingRules(List<Rule> rules);

    /**
     * This method used for returning product match score criteria by firing all the rules defined
     * @return Result - map of dealer id and their corresponding matching product id and their score
     */
    ProductScoreResult getProductMatchScore();

    /**
     * This method used for returning product match score criteria by firing all the rules defined
     * At the end filtering the product's score above the threshold
     * @param threshold threshold value works as filter the products which are below the standard value
     * @return Result - map of dealer id and their corresponding matching product id and their score
     */
    ProductScoreResult getProductMatchScore(int threshold);

    /**
     * This method used for returning total price of products, which are matched criteria by firing all the rules defined
     * @param threshold threshold value works as filter the products which are below the standard value
     * @return Result - map of dealer id and their corresponding matching product id and their score
     */
    Double getTotalPrice(int threshold);

    /**
     *  This method used for returning average price of products, which are matched criteria by firing all the rules defined
     * @param threshold threshold value works as filter the products which are below the standard value
     * @return Result - map of dealer id and their corresponding matching product id and their score
     */
    Double getAveragePrice(int threshold);
}
