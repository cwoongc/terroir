package com.elevenst.terroir.product.registration.mobile.validate;

import com.elevenst.terroir.product.registration.mobile.entity.PdPrdMobileFee;
import com.elevenst.terroir.product.registration.mobile.message.ValidationResult;
import com.elevenst.terroir.product.registration.mobile.vo.PdPrdMobileFeeVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class MobileValidateTest {
        @Autowired
    MobileValidateion mobileValidateion;

    @Test
    public void checkMobileFeeInfo_null() {

        String mpContractTypCd = "";
        List<PdPrdMobileFeeVO> productMobileFeeBOList = new ArrayList<>();
        long dispCtgrNo = 0;

        ValidationResult validationResult = mobileValidateion.checkMobileFeeInfo(mpContractTypCd,productMobileFeeBOList,dispCtgrNo);

        String errType = validationResult.getErrorType();
        String message = validationResult.getMessage();
        boolean result = validationResult.isSuccess();

        Assert.assertEquals("약정방식은 반드시 입력하셔야 합니다.",message);
    }

    @Test
    public void checkMobileFeeInfo_test01() {

        String mpContractTypCd = "01";
        List<PdPrdMobileFeeVO> productMobileFeeBOList = new ArrayList<>();
        long dispCtgrNo = 0;

        ValidationResult validationResult = mobileValidateion.checkMobileFeeInfo(mpContractTypCd,productMobileFeeBOList,dispCtgrNo);

        String errType = validationResult.getErrorType();
        String message = validationResult.getMessage();
        boolean result = validationResult.isSuccess();

        Assert.assertEquals(true,result);
    }

    @Test
    public void checkMobileFeeInfo_test02() {

        String mpContractTypCd = "02";
        List<PdPrdMobileFeeVO> productMobileFeeBOList = new ArrayList<>();
        PdPrdMobileFeeVO testFee = new PdPrdMobileFeeVO();
        PdPrdMobileFee testVO = new PdPrdMobileFee();
        testFee.setContractTermCd("01");
        testFee.setMpFeeNo(5065);
        testFee.setMaktPrc(1000);

        copyProperties(testFee, testVO);
//        testVO.setPdPrdMobileFee(testFee);
        productMobileFeeBOList.add(testFee);

        long dispCtgrNo = 112088;

        ValidationResult validationResult = mobileValidateion.checkMobileFeeInfo(mpContractTypCd,productMobileFeeBOList,dispCtgrNo);

        String errType = validationResult.getErrorType();
        String message = validationResult.getMessage();
        boolean result = validationResult.isSuccess();

        Assert.assertEquals(true,result);
    }
}
