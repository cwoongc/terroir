package com.elevenst.terroir.product.registration.etc.validate;

import com.elevenst.terroir.product.registration.etc.exception.EtcValidateException;
import com.elevenst.terroir.product.registration.etc.vo.PrdEtcVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
public class EtcValidateTest {
    @Autowired
    EtcValidate etcValidate;

    @Rule
    public ExpectedException expectedExcetption = ExpectedException.none();

    @Test
    public void setEtcProductInfo() {
        ProductVO productVO = new ProductVO();
        PrdEtcVO etcVO = new PrdEtcVO();

        etcVO.setBookClfCd("02");
        etcVO.setIsbn13Cd("일이삼사오육칠팔구십일이삼사");
        etcVO.setIsbn10Cd("일이삼사오육칠팔구십일이삼사");

        productVO.setPrdEtcVO(etcVO);

        try{
            etcValidate.setEtcProductInfo(productVO);
        }catch(EtcValidateException ex) {
            log.error(ex.getMessage());
        }
    }
}
