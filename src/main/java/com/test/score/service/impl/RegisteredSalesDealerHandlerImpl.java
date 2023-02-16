package com.test.score.service.impl;

import com.test.score.exception.FutureSupportException;
import com.test.score.model.Product;
import com.test.score.service.SalesDealerHandler;

import java.util.*;
import java.util.Map.Entry;

/**
 * <b>RegisteredSalesDealerHandlerImpl</b> implements <i>SalesDealerHandler</i> interface
 * This implementation assumes dealer is already registered with system and obtained dealer unique identification id.
 * In this implementation - this bean going to be singleton in nature. means, all the dealers will interact this bean alone.
 * This assumption is considered to make the easy of implementation. Avoids storing the products in database.
 */

public class RegisteredSalesDealerHandlerImpl implements SalesDealerHandler {
    private Map<String, List<Product>> dealerProducts;

    public RegisteredSalesDealerHandlerImpl() {
        this.dealerProducts = new HashMap<>();
    }

    /**
     * Not implemented at the movement.
     * @param dealerId - dealer unique identification id
     * @param productId - product unique identification id for which schema belongs to.
     * @param Schema - product schema - later converted it as Json
     */

    @Override
    public void registerProductSchema(String dealerId, String productId, String schema) {
        throw new FutureSupportException("Not implemented as of now, stay tuned!!");
    }

    /**
     * This method used to register products for given dealer id.
     * To make it simple - it always replace the existing products.
     * How existing products to be handle is actual business case, for this exercise it is out of context.
     * @param salesPersonId dealer unique identification id
     * @param products list of products to register
     */

    @Override
    public void registerProducts(String salesPersonId, List<Product> products) {
            dealerProducts.put(salesPersonId, products);
    }

    /**
     * Return all the products of a dealer.
     * If not found, simple returns empty list.
     * @param dealerId dealer unique identification
     * @return list of products belongs to given dealerId
     */

    @Override
    public List<Product> getDealerProducts(String dealerId) {
        return dealerProducts.getOrDefault(dealerId, new ArrayList<>(0));
    }

    /**
     * Returns all the dealers and their corresponding registered prodcuts
     * @return dealerIds and their products
     */

    @Override
    public Map<String, List<Product>> getAllDealersAndTheirProducts() {
        return dealerProducts;
    }

    /**
     * Returns the price of a product belongs to a dealer
     * If product not found, returns Zero as a default value.
     * @param dealerId dealer unique identification id
     * @param productId product unique identification id
     * @return price of a product belongs to a dealer.
     */
    @Override
    public Double getPrice(String dealerId, String productId) {
        for (Entry<String, List<Product>> entry : dealerProducts.entrySet()) {
            String id = entry.getKey();
            List<Product> products = entry.getValue();
            if (Objects.equals(id, dealerId)) {
                Optional<Product> matched = products.stream().filter(product -> Objects.equals(product.getId(), productId)).findFirst();
                if (matched.isPresent()) {
                   return matched.get().getCost();
                }
            }
        }
        return 0D;
    }
}
