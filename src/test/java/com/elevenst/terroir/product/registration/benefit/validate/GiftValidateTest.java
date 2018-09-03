package com.elevenst.terroir.product.registration.benefit.validate;

import com.elevenst.terroir.product.registration.benefit.entity.PdPrdGift;
import com.elevenst.terroir.product.registration.benefit.message.BenefitExceptionMessage;
import com.elevenst.terroir.product.registration.benefit.service.ProductBenefitServiceImpl;
import com.elevenst.terroir.product.registration.benefit.vo.ProductGiftVO;
import com.elevenst.terroir.product.registration.product.service.ProductServiceImpl;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class GiftValidateTest {

    private PdPrdGift pdPrdGift;
    private ProductGiftVO productGiftVO;

    @Autowired
    BenefitValidate benefitValidate;

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    ProductBenefitServiceImpl productBenefitService;

    @Rule
    public ExpectedException expectedExcetption = ExpectedException.none();

    @Before
    public void before(){
        long prdNo = 1245252447;
        productGiftVO = productBenefitService.getProductGift(prdNo);
        if(productGiftVO == null){
            log.info("사은품정보 없음!");
        }
    }

    /**
     * Validation
     */
    @Test
    public void checkGiftNm() {
        ProductGiftVO productGiftVO = new ProductGiftVO();
        BeanUtils.copyProperties(pdPrdGift, productGiftVO);
        benefitValidate.checkProductGiftLen(productGiftVO);
    }

    @Test
    public void checkGiftDt() {
        ProductGiftVO productGiftVO = new ProductGiftVO();
        BeanUtils.copyProperties(pdPrdGift, productGiftVO);
        benefitValidate.checkProductGiftDt(productGiftVO);
    }

    /*@Test
    public void test01_checkProductGift() {

        ProductGiftVO preProductGiftVO = productBenefitService.getProductGift(pdPrdGift.getPrdNo());
        productBenefitValidate.checkProductGift(productVO.getProductGiftVO(), pdPrdGift);
    }*/

    /*@Test
    public void test01_사은품이미지검사() {
        if(!"".equals(pdPrdGift.getImgNm()) && pdPrdGift..getImgFile() != null) {
            productBenefitValidate.checkProductGiftImage(productGiftVO.getImgFile());
        }
    }*/


    // exception test
    @Test
    public void checkWrongGiftAPI() {
        expectedExcetption.expectMessage(BenefitExceptionMessage.GIFT_07);

        ProductVO productVO = new ProductVO();
        productVO.setUseGiftYn("D");

        benefitValidate.checkProductGiftAPI(productVO);
    }

}
