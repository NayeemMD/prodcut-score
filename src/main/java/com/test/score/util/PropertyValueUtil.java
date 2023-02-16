package com.test.score.util;

import com.test.score.model.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertyValueUtil {

    public static String getProductValue(Product product, String name) {
        try {
            return BeanUtils.getProperty(product, name);
        } catch (Exception e) {
            return null;
        }
    }

}
