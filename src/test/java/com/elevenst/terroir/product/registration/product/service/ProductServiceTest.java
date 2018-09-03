package com.elevenst.terroir.product.registration.product.service;

import com.elevenst.common.util.cache.ObjectCacheImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    ProductServiceImpl productService;


    @Test
    public void testObjectCache(){
        boolean aa = productService.checkSupportMobileEdit(111);
        Assert.assertTrue(aa);
    }
}
