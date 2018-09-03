package com.elevenst.terroir.product.registration.help.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("local")
@SpringBootTest
public class HelpServiceTest {

    @Autowired
    HelpServiceImpl helpServiceImpl;

    @Test
    public void getSellerFaq() {
        String code = "0170A";
        helpServiceImpl.getSellerFaq(code);
    }
}
