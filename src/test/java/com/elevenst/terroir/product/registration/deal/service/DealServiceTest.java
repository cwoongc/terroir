package com.elevenst.terroir.product.registration.deal.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class DealServiceTest {

    @Autowired
    DealServiceImpl dealService;

    @Test
    public void getShockingDealPrd() {
        long prdNo = 1245253478;
        dealService.getShockingDealPrd(prdNo);
    }

    @Test
    public void getShockDealStarted() {
        long prdNo = 1244668877;
        dealService.getShockDealStarted(prdNo);
    }
}
