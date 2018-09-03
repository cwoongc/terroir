package com.elevenst.terroir.product.registration.mobile.service;

import com.elevenst.common.util.DateUtil;
import com.elevenst.terroir.product.registration.mobile.entity.PdPrdMobileFee;
import com.elevenst.terroir.product.registration.mobile.mapper.MobileMapperTest;
import com.elevenst.terroir.product.registration.mobile.vo.PdPrdMobileFeeVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class MobileServiceTest {

    @Autowired
    MobileServiceImpl productMobileService;

    @Autowired
    MobileMapperTest mobileMapperTest;

    private static final long PRD_NO1 = 8888888880L;
    private static final long SELL_NO1 = 1004;
    private static final long SELL_NO2 = 1005;

    private static boolean ISUPDATED = false;



    @Test
    public void insertProductMobileFeeInfo() {
        ProductVO testVO = new ProductVO();
        PdPrdMobileFeeVO param1 = new PdPrdMobileFeeVO();
        PdPrdMobileFeeVO param2 = new PdPrdMobileFeeVO();
        List<PdPrdMobileFeeVO> productMobileFeeVOList = new ArrayList<>();

        DateUtil.getCurrentDate();
        String aplBgnDt = DateUtil.formatDate(DateUtil.getCurrentDate(),"yyyyMMddHHmmss");

        int result = 0;

        param1.setPrdNo(PRD_NO1);
        param1.setMpFeeNo(SELL_NO1);
        param1.setContractTermCd("CD");
        param1.setMaktPrc(SELL_NO1);
        param1.setRepFeeYn("Y");
        param1.setPrrtRnk(1);
        param1.setCreateNo(SELL_NO1);
        param1.setHistAplBgnDt(aplBgnDt);

        param2.setPrdNo(PRD_NO1);
        param2.setMpFeeNo(SELL_NO1);
        param2.setContractTermCd("CDCD");
        param2.setMaktPrc(SELL_NO1);
        param2.setRepFeeYn("Y");
        param2.setPrrtRnk(2);
        param2.setCreateNo(SELL_NO1);
        param2.setHistAplBgnDt(aplBgnDt);

        productMobileFeeVOList.add(param1);
        productMobileFeeVOList.add(param2);

        testVO.setCreateNo(SELL_NO1);
        testVO.setPrdNo(PRD_NO1);
        testVO.setHistAplEndDt(aplBgnDt);
        testVO.setProductMobileFeeVOList(productMobileFeeVOList);


        for (PdPrdMobileFeeVO productMobileFeeVO : productMobileFeeVOList) {
            productMobileFeeVO.setPrdNo(testVO.getPrdNo());
            productMobileFeeVO.setCreateNo(testVO.getCreateNo());

//            result += mobileMapperTest.insertProductMobileFee(productMobileFeeVO);
        }

        PdPrdMobileFee histParm = new PdPrdMobileFee();
        histParm.setPrdNo(testVO.getPrdNo());
        histParm.setHistAplBgnDt(testVO.getHistAplEndDt());

        mobileMapperTest.insertProductMobileFeeHistory(histParm);

        Assert.assertEquals(2,result);
    }

    @Test
    public void updateProductMobileFeeInfo() {
        ProductVO testVO = new ProductVO();
        PdPrdMobileFeeVO param1 = new PdPrdMobileFeeVO();
        PdPrdMobileFeeVO param2 = new PdPrdMobileFeeVO();
        List<PdPrdMobileFeeVO> productMobileFeeVOList = new ArrayList<>();

        int result = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MINUTE, 1);

        String aplBgnDt = sdf.format(cal.getTime());

        param1.setPrdNo(PRD_NO1);
        param1.setMpFeeNo(SELL_NO2);
        param1.setContractTermCd("AA");
        param1.setMaktPrc(SELL_NO2);
        param1.setRepFeeYn("N");
        param1.setPrrtRnk(1);
        param1.setCreateNo(SELL_NO2);
        param1.setHistAplBgnDt(aplBgnDt);

        productMobileFeeVOList.add(param1);

        param2.setPrdNo(PRD_NO1);
        param2.setMpFeeNo(SELL_NO2);
        param2.setContractTermCd("BB");
        param2.setMaktPrc(SELL_NO2);
        param2.setRepFeeYn("N");
        param2.setPrrtRnk(1);
        param2.setCreateNo(SELL_NO2);
        param2.setHistAplBgnDt(aplBgnDt);

        productMobileFeeVOList.add(param2);

        testVO.setPrdNo(PRD_NO1);
        testVO.setHistAplEndDt("20171230000000");
        testVO.setProductMobileFeeVOList(productMobileFeeVOList);
        testVO.setCreateNo(SELL_NO2);

//        mobileMapperTest.deleteProductMobileFee(param2);
//
//        for (PdPrdMobileFeeVO productMobileFeeVO : productMobileFeeVOList) {
//            productMobileFeeVO.setPrdNo(testVO.getPrdNo());
//            productMobileFeeVO.setCreateNo(testVO.getCreateNo());
//
//            result += mobileMapperTest.insertProductMobileFee(productMobileFeeVO);
//        }
        PdPrdMobileFee histParm = new PdPrdMobileFee();
        histParm.setPrdNo(testVO.getPrdNo());
        histParm.setHistAplBgnDt(testVO.getHistAplEndDt());

        mobileMapperTest.insertProductMobileFeeHistory(histParm);

        Assert.assertEquals(2,result);

        PdPrdMobileFee pdPrdMobileFee;
        pdPrdMobileFee = mobileMapperTest.getPdPrdMobileFeeTest(PRD_NO1);
        Assert.assertEquals(SELL_NO2,pdPrdMobileFee.getUpdateNo());
        Assert.assertEquals(SELL_NO2,pdPrdMobileFee.getMpFeeNo());
        Assert.assertEquals("N",pdPrdMobileFee.getRepFeeYn());

        ISUPDATED = true;

    }

    @Test
    public void getGroupList() {
        long dispCtgrNo = 1;
        List<HashMap> retList = productMobileService.getGroupList(dispCtgrNo);
        Assert.assertTrue(retList.size() == 0);
    }

    @Test
    public void getMobileFeeNameList() {
        long dispCtgrNo = 1;
        String grpNm = "test";
        List<HashMap> retList = productMobileService.getMobileFeeNameList(dispCtgrNo, grpNm);
        Assert.assertTrue(retList.size() == 0);
    }

    @After
    public void after() {

        int result = 0;

        if(ISUPDATED) {
            PdPrdMobileFee pdPrdMobileFee = new PdPrdMobileFee();
            pdPrdMobileFee.setPrdNo(PRD_NO1);

            result = mobileMapperTest.deleteProductMobileFee(pdPrdMobileFee);
            mobileMapperTest.deleteProductMobileFeeHistTest(pdPrdMobileFee);

            Assert.assertEquals(2,result);
        }
    }
}
