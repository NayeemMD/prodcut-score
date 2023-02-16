package com.test.score.service.impl;

import com.test.score.config.BeanFactory;
import com.test.score.model.Product;
import com.test.score.model.ProductScore;
import com.test.score.model.ProductScoreResult;
import com.test.score.model.Rule;
import com.test.score.service.CustomerHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static com.test.score.util.OperatorCompareUtil.*;
import static com.test.score.util.PropertyValueUtil.getProductValue;

/**
 * <b>CustomerRulesImplementation</b> implements <i>CustomerHandler</i>
 * In exercise - this bean is type of prototype, each time a customer initiates the rules new bean is created
 */
public class CustomerRulesImplementation implements CustomerHandler {
    private List<Rule> rules;

    public CustomerRulesImplementation() {
        this.rules = new ArrayList<>();
    }

    /**
     * To register all the custom rules defined the customer.
     * Implementation assumes - rules are add at the beginning. Should have been set type, assume that no duplications are sent.
     *
     * @param rules set of rules defined a customer
     */
    @Override
    public void registerBuyingRules(List<Rule> rules) {
        this.rules.addAll(rules);
    }

    /**
     * Determines the product score by firing all the rules defined the customer.
     * Edge cases such as rules not found not considered as part of this exercise.
     * Algorithm:
     * 1. Get all the products belongs to all the dealers
     * 2. for each dealer,
     * 2.1. for each product
     * a. evaluate the rule for each condition
     * a.1. obtain the condition attribute name
     * a.2 obtain the product value for given condition attribute name
     * a.3 compare condition value to product value derived in a.2
     * a.4 if matched increment matched value
     * b. repeat a.1 to a.4 for all the conditions
     * c. calculate rule score by (rule.score) * ((matched value)/total conditions * 100)
     * d. if rule is negative - multiple by -1 (assumption, this is what negative means)
     * e. Repeat this a to e for all the rules
     * 2.2 keep productId and result score in result map
     * 3. repeat 2.1 to 2.2 steps for all the products
     * 4. repeat 2 to 3 for all dealers
     * 5. Ultimately prepare result object with map (Result is can hold metadata, for now, out of context)
     *
     * @return dealer id to their products score
     */

    @Override
    public ProductScoreResult getProductMatchScore() {
        Map<String, List<Product>> allDealerProducts = BeanFactory.salesDealerHandler().getAllDealersAndTheirProducts();
        Map<String, List<ProductScore>> result = new HashMap<>();

        allDealerProducts.forEach((dealerId, products) -> {
            List<ProductScore> productScores = new ArrayList<>();
            products.forEach(product -> calculateProductScore(productScores, product));
            if (!productScores.isEmpty()) {
                result.put(dealerId, productScores);
            }
        });

        return new ProductScoreResult(result);
    }

    /**
     * Returns the filtered product score above the given threshold.
     *
     * @param threshold threshold value works as filter the products which are below the standard value
     * @return dealer id to their products score
     */

    @Override
    public ProductScoreResult getProductMatchScore(int threshold) {
        Map<String, List<ProductScore>> filterResult = new HashMap<>();
        ProductScoreResult productMatchScore = getProductMatchScore();
        productMatchScore.getResult().forEach((id, productsScore) -> {
            List<ProductScore> filteredScores = productsScore.stream().filter(productScore -> productScore.getScore().intValue() >= threshold).toList();
            filterResult.put(id, filteredScores);
        });
        return new ProductScoreResult(filterResult);
    }

    /**
     * Returns the total price product's score above threshold above the given threshold.
     *
     * @param threshold threshold value works as filter the products which are below the standard value
     * @return total price default 0 value
     */


    @Override
    public Double getTotalPrice(int threshold) {
        Map<String, List<ProductScore>> result = getProductMatchScore(threshold).getResult();
        AtomicReference<Double> totalPrice = new AtomicReference<>(0D);
        result.forEach((id, productsScore) -> productsScore.forEach(productScore -> {
            Double price = BeanFactory.salesDealerHandler().getPrice(id, productScore.getProductId());
            totalPrice.updateAndGet(v -> v + price);
        }));

        return totalPrice.get();
    }

    /**
     * Returns the average price product's score above threshold above the given threshold.
     * total price divides total products
     *
     * @param threshold threshold value works as filter the products which are below the standard value
     * @return average price, returns 0 in case of total products are zero.
     */

    @Override
    public Double getAveragePrice(int threshold) {
        Map<String, List<ProductScore>> result = getProductMatchScore(threshold).getResult();
        AtomicInteger totalProducts = new AtomicInteger(0);
        result.forEach((id, productsScore) -> totalProducts.getAndUpdate(v -> v + productsScore.size()));

        if (totalProducts.get() == 0) return 0D;
        return getTotalPrice(threshold) / totalProducts.get();
    }

    private void calculateProductScore(List<ProductScore> productScores, Product product) {
        AtomicReference<Double> totalScore = new AtomicReference<>(0D);
        rules.forEach(rule -> totalScore.getAndUpdate(v -> v + applyRule(product, rule)));
        if (totalScore.get() > 0) {
            productScores.add(new ProductScore(product.getId(), totalScore.get()));
        }
    }

    private double applyRule(Product product, Rule rule) {
        AtomicInteger matchedRules = new AtomicInteger();
        rule.getConditions().forEach(condition -> {
            String productValue = getProductValue(product, condition.getAttribute());
            boolean matched = compare(productValue, condition.getValue(), condition.getOperator());
            if (matched) matchedRules.getAndIncrement();
        });
        double ruleScore = (rule.getScore()) * (((double) matchedRules.get() / rule.getConditions().size()) * 100);
        if (rule.isNegative()) ruleScore *= -1;
        return ruleScore;
    }
}
