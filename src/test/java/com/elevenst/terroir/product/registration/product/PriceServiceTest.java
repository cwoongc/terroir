package com.elevenst.terroir.product.registration.product;

import com.elevenst.terroir.product.registration.price.entity.PdPrdPrc;
import com.elevenst.terroir.product.registration.price.service.PriceServiceImpl;
import com.elevenst.terroir.product.registration.price.vo.PriceVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class PriceServiceTest {
    @Autowired
    PriceServiceImpl priceServiceImpl;
    @Rule
    public ExpectedException expectedExcetption = ExpectedException.none();

    @Test
    public void getProductPrice(){
        long prdNo = 135;
        PriceVO result = priceServiceImpl.getProductPrice(prdNo);

        Assert.assertTrue(result.getPrdPrcNo() ==196);
        Assert.assertTrue(result.getPrdNo()==135);
        Assert.assertTrue(result.getEvntNo()==0);
        Assert.assertTrue(result.getAplEndDy().equals("20071116235959"));
        Assert.assertTrue(result.getAplBgnDy().equals("20071114000000"));
        Assert.assertTrue(result.getSelPrc()==999000);
        Assert.assertTrue(result.getBuylCnPrc() ==999000);
        Assert.assertTrue(result.getDscAmt() ==0);
        Assert.assertTrue(result.getDscRt() ==0);
        Assert.assertTrue(result.getMrgnRt() == 0);
        Assert.assertTrue(result.getCreateDt().equals("20071114"));
        Assert.assertTrue(result.getCreateNo()==10000005);
        Assert.assertTrue(result.getUpdateDt().equals("20071114"));
        Assert.assertTrue(result.getUpdateNo()==10000005);
        Assert.assertTrue(result.getCupnUseLmtQty()==0);
        Assert.assertTrue(result.getCupnDscMthdCd().equals(""));
        Assert.assertTrue(result.getMaktPrc()==0);
        Assert.assertTrue(result.getMrgnPolicyCd().equals(""));
        Assert.assertTrue(result.getPuchPrc()==0);
        Assert.assertTrue(result.getMrgnAmt()==0);
        Assert.assertTrue(result.getFrSelPrc()==0);
        Assert.assertTrue(result.getCurrencyCd().equals(""));
        Assert.assertTrue(result.getCstmAplPrc() ==0);
        Assert.assertTrue(result.getPaidSelPrc()==0);
        Assert.assertTrue(result.getPrmtDscAmt()==0);
        Assert.assertTrue(result.getMpContractTypCd().equals(""));

    }

    @Test
    public void getEnuriProductInfo() {
        long prdNo = 113858;
        priceServiceImpl.getEnuriProductInfo(prdNo);
    }
}
