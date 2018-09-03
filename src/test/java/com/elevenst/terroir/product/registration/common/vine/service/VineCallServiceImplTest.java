package com.elevenst.terroir.product.registration.common.vine.service;

import com.elevenst.terroir.product.registration.common.vine.dto.ProductPriceV1;
import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
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
public class VineCallServiceImplTest {

    @Autowired
    VineCallServiceImpl vineCallService;

    @Test
    public void testGetMemberInfo(){
        long memNo = 10000276;
        MemberVO memberVO = vineCallService.getMemberDefaultInfo(memNo);
        Assert.assertEquals(memNo, memberVO.getMemNO());
    }

    @Test
    public void testGetSellerCash(){
        long memNo = 10000276;
        long cash = vineCallService.getSellerCash(memNo);
        Assert.assertTrue(cash >= 0);
    }

    @Test
    public void testInsertSellerDirectCoupon(){
        ProductVO productVO = new ProductVO();
        ProductPriceV1 productPriceV1 = new ProductPriceV1();

        productVO.setPrdNo(1245254084);
        productVO.setSelMnbdNo(10000276);
        productVO.setPrdNm("[나이키] 다이나모 프리 (TD) [343938] 테스트");
        productVO.setUpdate(true);


        productPriceV1.setDscAmt(100L);
//        productPriceV1.setDscRt(0.0);
        productPriceV1.setCupnDscMthdCd("01");
//        productPriceV1.setCupnUseLmtQty(10);

        vineCallService.insertSellerDirectCoupon(productVO, productPriceV1);
    }
}
