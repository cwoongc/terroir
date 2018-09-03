package com.elevenst.terroir.product.registration.fee.service;

import com.elevenst.common.util.DateUtil;
import com.elevenst.terroir.product.registration.fee.entity.SeFeeItm;
import com.elevenst.terroir.product.registration.fee.mapper.FeeMapperTest;
import com.elevenst.terroir.product.registration.fee.vo.BasketNointerestVO;
import com.elevenst.terroir.product.registration.fee.vo.SeFeeItemVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.validate.SellerValidate;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
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
public class FeeServiceTest {
    @Autowired
    FeeServiceImpl feeService;

    @Autowired
    SellerValidate sellerValidate;

    @Autowired
    FeeMapperTest feeMapperTest;
    private static long FEEITMNO = 777777771L;
    private static long PRDNO01 = 777777772L;
    public static boolean FINISHUPDATE = false;

    @Before
    public void before() {

        int result = 0;

        SeFeeItm value = new SeFeeItm();
        value.setSelFeeAplObjNo(String.valueOf(PRDNO01));
        value.setUpdateNo(10000276);
        value.setFeeKdCd("03101");
        value.setFeeTypCd("03");
        value.setFeeItmNo(FEEITMNO);
        value.setFee(40);
        value.setAplEndDy("20171114000000");

        result =  feeMapperTest.insertSeeFeeItm(value);

        Assert.assertEquals(1,result);
    }

    @Test
    public void setSeFeeEndDt() {
        SeFeeItm value = new SeFeeItm();

        int result = 0;

        value.setUpdateNo(10000276);
        value.setUpdateDt(DateUtil.formatDate("20171115000000"));
        value.setSelFeeAplObjNo(String.valueOf(PRDNO01));

        if(!sellerValidate.checkMobileSeller(10000276)) {
            result = feeMapperTest.setSeFeeEndDt(value);
        }

        Assert.assertEquals(1,result);

        FINISHUPDATE = true;
    }

    @Test
    public void getSaleFeeRateAmount() {
        long dispCtgr2No = 1002167;
        long dispCtgr3No = 1007649;
        String selMthdCd = ProductConstDef.PRD_SEL_MTHD_CD_FIXED;
        long selMnbdNo = 10000276;
        long prdNo = 1245256711;
        long selPrc = 50000;
        feeService.getSaleFeeRateAmount(dispCtgr2No, dispCtgr3No, selMthdCd, selMnbdNo, prdNo, selPrc);
    }

    @Test
    public void getCategoryDefaultFee() {
        ProductVO productVO = new ProductVO();
        productVO.setDispCtgrNo(1007649);
        productVO.getBaseVO().setSelMthdCd(ProductConstDef.PRD_SEL_MTHD_CD_FIXED);
        feeService.getCategoryDefaultFee(productVO);
    }

    @Test
    public void getCrdtCrdenFeeList() {
        BasketNointerestVO basketNointerestVO = new BasketNointerestVO();
        basketNointerestVO.setSearchCard("16");
        basketNointerestVO.setSearchType("Y");
        basketNointerestVO.setSpcCnrtCLF("Y");
        feeService.getCrdtCrdenFeeList(basketNointerestVO);
    }

    @After
    public void after() {
        int result = 0;
        if(FINISHUPDATE) {
            result = feeMapperTest.deleteSetFeeTest(FEEITMNO);
        }
        Assert.assertEquals(1,result);
    }
}
