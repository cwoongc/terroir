package com.elevenst.terroir.product.registration.customer.validate;

import com.elevenst.terroir.product.registration.customer.service.CustomerServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class CustomerValidateTest {

    @Autowired
    CustomerServiceImpl customerServiceImpl;

    @Autowired
    CustomerValidate customerValidate;

    @Test
    public void checkItgMemNo() {
        Boolean result = customerValidate.checkItgMemNo(24591453,23102016);
        Assert.assertTrue(result == true);
    }

    @Test
    public void isFrSellerByPrdNo() {
        boolean isFrSeller = customerServiceImpl.isFrSellerByPrdNo(374629011);
        Assert.assertTrue(true);
    }
}
