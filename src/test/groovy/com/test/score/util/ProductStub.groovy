package com.test.score.util

import com.test.score.model.Product

class ProductStub {
    static def names = ["p1", "p2", "p3"]
    static def colors = ["BLUE", "RED", "WHITE"]
    static def costs = [10D, 20D, 30D]
    static def weights = [2D, 10D, 20D]
    static def quantities = [10D, 20D, 30D]

    static Product getProduct(String id) {
        getProduct(id, shuffle(names).get(0) , shuffle(colors).get(0), shuffle(costs).get(0), shuffle(weights).get(0), shuffle(quantities).get(0))
    }

    static Product getProduct(String id, String name, String color, Double cost, Double weight, Double quantity) {
        Product product = new Product()
        product.id = id
        product.name = name
        product.color = color
        product.cost = cost
        product.weight = weight
        product.quantity = quantity
        product
    }

    static List shuffle(List list) {
        list.shuffle()
         list
    }
}
