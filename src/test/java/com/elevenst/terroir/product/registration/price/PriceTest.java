package com.elevenst.terroir.product.registration.price;

import com.elevenst.terroir.product.registration.price.service.PriceServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class PriceTest {

    @Autowired
    PriceServiceImpl priceServiceImpl;

    @Test
    public void getProductDiscountDataByPrdNo() {
        priceServiceImpl.getProductDiscountDataByPrdNo(1244662904, 999099910);
    }
}
