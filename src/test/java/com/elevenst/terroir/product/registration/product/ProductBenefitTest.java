package com.elevenst.terroir.product.registration.product;

import com.elevenst.terroir.product.registration.benefit.service.ProductBenefitServiceImpl;
import com.elevenst.terroir.product.registration.benefit.vo.ProductGiftVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class ProductBenefitTest {

    @Autowired
    ProductBenefitServiceImpl productBenefitService;

    @Test
    public void getCustomerBenefit() {
        long prdNo = 1244990384;
        productBenefitService.getCustomerBenefit(prdNo);
    }

    @Test
    public void getCustomerPrdPluDscList() {
        long prdNo = 564475785;
        productBenefitService.getCustomerPrdPluDscList(prdNo);
    }

    @Test
    public void convertRegInfo() {
        ProductVO preProductVO = new ProductVO();
        ProductVO productVO = new ProductVO();
        ProductGiftVO productGiftVO = new ProductGiftVO();
        productVO.setUseGiftYn("Y");
        productVO.setPrdNo(1245264798);

        productGiftVO.setAplBgnDt("20171221");
        productGiftVO.setAplEndDt("20180201");
        productGiftVO.setCreateNo(10000276);
        productGiftVO.setUpdateNo(10000276);
        productGiftVO.setImgUploadYn("Y");
        productGiftVO.setImgUrlPath("/data1/tmp/20171221/10000276");
        productGiftVO.setImgNm("20171221190000_test.jpg");
        productGiftVO.setGiftNm("giftnm");
        productGiftVO.setGiftInfo("giftinfo");
        productVO.setProductGiftVO(productGiftVO);
        productBenefitService.convertRegInfo(preProductVO, productVO);
    }
}
