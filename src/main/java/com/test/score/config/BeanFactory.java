package com.test.score.config;

import com.test.score.service.CustomerHandler;
import com.test.score.service.SalesDealerHandler;
import com.test.score.service.impl.CustomerRulesImplementation;
import com.test.score.service.impl.RegisteredSalesDealerHandlerImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <b>BeanFactory</b> handles the bean life cycles required for this application
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanFactory {
    private static final SalesDealerHandler salesDealerHandler = new RegisteredSalesDealerHandlerImpl();

    public static SalesDealerHandler salesDealerHandler() {
        return salesDealerHandler;
    }

    public static CustomerHandler customerHandler() {
        return new CustomerRulesImplementation();
    }
}
