package com.test.score.service

import com.test.score.config.BeanFactory
import com.test.score.model.Condition
import com.test.score.model.Operator
import com.test.score.model.Product
import com.test.score.model.Rule
import com.test.score.util.ProductStub
import spock.lang.Specification
import spock.lang.Subject

import static com.test.score.util.ProductStub.getProduct

class CustomerHandlerSpec extends Specification {
    @Subject
    CustomerHandler customerHandler

    private SalesDealerHandler salesDealer = BeanFactory.salesDealerHandler()
    def dealerIds = ["11", "12", "13"]

    def setup() {

        setupRegisteredProducts()
    }

    def "given - deal products and buying rule, when fetching product match score, then should return products with score"() {
        given:
            def product = getProduct("12", "DRESS", "GREENISH", 20D, 10D, 12D)
            salesDealer.registerProducts("D01", [product])
            def c1 = new Condition("color", "GREENISH", Operator.EQ)
            def c3 = new Condition("cost", 20D, Operator.EQ)
            def c5 = new Condition("quantity", 10D, Operator.EQ)
            Rule r1 = new Rule([c1,c3,c5], 1, false)
            customerHandler = BeanFactory.customerHandler()
            customerHandler.registerBuyingRules([r1])
        when:
            def productScores = customerHandler.getProductMatchScore()
            def totalPrice = customerHandler.getTotalPrice(1)
            def averagePrice = customerHandler.getAveragePrice(1)
        then:
            productScores.result.size() == 1
            totalPrice == 20
            averagePrice == 20
    }

    def "given - buying rule, when fetching product match score, then should return products with scores"() {
        given:
            customerHandler = BeanFactory.customerHandler()
            def c1 = new Condition("color", "BLUE", Operator.EQ)
            def c3 = new Condition("cost", 5D, Operator.LE)
            def c5 = new Condition("quantity", 5D, Operator.GE)
            Rule r1 = new Rule([c1,c3,c5], 1, false)
            customerHandler.registerBuyingRules([r1])
        when:
            def productScores = customerHandler.getProductMatchScore()
            def totalPrice = customerHandler.getTotalPrice(1)
            def averagePrice = customerHandler.getAveragePrice(1)

        then:
            productScores.getResult().size() == 3
            totalPrice >= 30
            averagePrice > 10

    }

    def "given - buying rules, when fetching product match score, then should return products with scores"() {
        given:
            customerHandler = BeanFactory.customerHandler()
            def c1 = new Condition("color", "BLUE", Operator.EQ)
            def c3 = new Condition("cost", 5D, Operator.LE)
            def c5 = new Condition("quantity", 5D, Operator.GE)
            Rule r1 = new Rule([c1,c3,c5], 1, false)
            customerHandler.registerBuyingRules([r1])
        when:
            def productScores = customerHandler.getProductMatchScore()
            def totalPrice = customerHandler.getTotalPrice(1)
            def averagePrice = customerHandler.getAveragePrice(1)

        then:
            productScores.getResult().size() == 3
            totalPrice >= 30
            averagePrice > 10

    }

    def "given - buying rules and fetching with threshold, when fetching product match score, then should return products with scores"() {
        given:
            customerHandler = BeanFactory.customerHandler()
            def rules = prepareRules()
            customerHandler.registerBuyingRules(rules)
        when:
            def productScores = customerHandler.getProductMatchScore(10)
            def totalPrice = customerHandler.getTotalPrice(0)
            def averagePrice = customerHandler.getAveragePrice(0)

        then:
            productScores.getResult().size() == 3
            totalPrice >= 100
            averagePrice > 15

    }

    def "given - Negative buying rule, when fetching product match score, then should return products with scores"() {
        given:
            customerHandler = BeanFactory.customerHandler()
            def c1 = new Condition("color", "BLUE", Operator.EQ)
            def c3 = new Condition("cost", 5D, Operator.LE)
            def c5 = new Condition("quantity", 5D, Operator.GE)
            Rule r1 = new Rule([c1,c3,c5], 1, true)
            customerHandler.registerBuyingRules([r1])
        when:
            def productScores = customerHandler.getProductMatchScore()
            def totalPrice = customerHandler.getTotalPrice(0)
            def averagePrice = customerHandler.getAveragePrice(0)

        then:
            productScores.getResult().size() == 3
            totalPrice == 0
            averagePrice == 0
    }


    void setupRegisteredProducts() {
        dealerIds.forEach { id ->
            {
                salesDealer.registerProducts(id, getProducts(3))
            }
        }
    }

    List<Product> getProducts(int count) {
        ArrayList<Product> products = new ArrayList<>()
        count.times { i ->
            products.add(getProduct(UUID.randomUUID().toString()))
        }
        products
    }

    List<Rule> prepareRules() {
        def c1 = new Condition("color", "BLUE", Operator.EQ)
        def c2 = new Condition("color", "RED", Operator.EQ)
        def c3 = new Condition("cost", 5D, Operator.LE)
        def c4 = new Condition("cost", 5D, Operator.GE)
        def c5 = new Condition("quantity", 5D, Operator.GE)
        def c6 = new Condition("quantity", 10D, Operator.LE)

        Rule r1 = new Rule([c1,c3,c5], 1, false)
        Rule r2 = new Rule([c2,c4,c6], 2, false)
        Rule r3 = new Rule([c2,c3,c5], 2, true)
        Rule r4 = new Rule([c2,c4,c5], 1, false)
        Rule r5 = new Rule([c1,c4,c5], 3, false)

        [r1,r2,r3,r4,r5]
    }
}