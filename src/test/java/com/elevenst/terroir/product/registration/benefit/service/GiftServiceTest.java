package com.elevenst.terroir.product.registration.benefit.service;

import com.elevenst.terroir.product.registration.benefit.entity.PdPrdGift;
import com.elevenst.terroir.product.registration.benefit.vo.ProductGiftVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@Slf4j
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("local")
@SpringBootTest
public class GiftServiceTest {

    @Autowired
    ProductBenefitServiceImpl productBenefitService;


    @Test
    public void test01_insertProductGift() {
        PdPrdGift newGift = makePdPrdGift();
        productBenefitService.regProductGift(newGift);
        ProductGiftVO productGiftVO = productBenefitService.getProductGift(newGift.getPrdNo());

        assertEquals(newGift.getGiftNm(), productGiftVO.getGiftNm());
        assertEquals(newGift.getGiftInfo(), productGiftVO.getGiftInfo());
        assertEquals(newGift.getAplBgnDt(), productGiftVO.getAplBgnDt());
        assertEquals(newGift.getAplEndDt(), productGiftVO.getAplEndDt());
    }

    @Test
    public void test02_deleteProductGift() {
        long prdNo = 1245252447;
        ProductGiftVO productGiftVO = productBenefitService.getProductGift(prdNo);
        if(productGiftVO == null){
            log.info("사은품정보 없음");
            return;
        }
        PdPrdGift pdPrdGift = new PdPrdGift();
        BeanUtils.copyProperties(productGiftVO, pdPrdGift);
        productBenefitService.deleteProductGift(pdPrdGift);

        assertEquals(productBenefitService.getProductGift(productGiftVO.getPrdNo()), null);
    }

    
    private PdPrdGift makePdPrdGift() {
        FastDateFormat format = FastDateFormat.getInstance( "yyyyMMdd", Locale.getDefault());

        PdPrdGift pdPrdGift = new PdPrdGift();
        pdPrdGift.setPrdNo(1245252447);
        pdPrdGift.setGiftNm("졸리당1");
        pdPrdGift.setGiftInfo("집에가고싶당1");
        pdPrdGift.setCreateNo(10000276);
        pdPrdGift.setUpdateNo(10000276);
        pdPrdGift.setImgUrlPath("");
        pdPrdGift.setImgNm("");
        pdPrdGift.setAplBgnDt(format.format(new Date()));
        pdPrdGift.setAplEndDt(format.format(new Date().getTime()+10*24*60*60*1000));

        return pdPrdGift;
    }

}
