package com.test.score.service;

import com.test.score.model.Product;

import java.util.List;
import java.util.Map;

/**
 * <b>SalesDealerHandler</b> represent the Sales Dealer activities in online store
 * In reality, these methods would be exposed as API endpoints
 * Current implementation does not considered exception and edge cases
 */

public interface SalesDealerHandler {
    /**
     * This method is used for registering the product schema.
     * Allows various products to various data types by registering the schema.
     * Later when Company/Customer defining the rules, can use this schema to validate the data types and attributes.
     * @param dealerId - dealer unique identification id
     * @param productId - product unique identification id for which schema belongs to.
     * @param schema - product schema - later converted it as Json
     */
    void registerProductSchema(String dealerId, String productId, String schema);

    /**
     * This method allows dealer to register his products in the online store to sale.
     * @param dealerId dealer unique identification id
     * @param products list of products to register
     */
    void registerProducts(String dealerId, List<Product> products);

    /**
     * This method used to return the all the list products registered by a dealer in the online store
     * @param dealerId dealer unique identification
     * @return list of products registered belongs to given- ready to sale at online store
     */
    List<Product> getDealerProducts(String dealerId);

    /**
     * This method returns all the dealers and corresponding registered products
     * @return dealerId and their list of the products
     */

    Map<String, List<Product>> getAllDealersAndTheirProducts();

    /**
     * This method returns price of a given product belongs a dealer
     * @param dealerId dealer unique identification id
     * @param productId product unique identification id
     * @return price of product id
     */
    Double getPrice(String dealerId, String productId);
}
