package com.elevenst.terroir.product.registration.etc.service;

import com.elevenst.common.util.DateUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import com.elevenst.terroir.product.registration.etc.entity.PdPrdEtc;
import com.elevenst.terroir.product.registration.etc.entity.PdPrdOthers;
import com.elevenst.terroir.product.registration.etc.mapper.EtcMapperTest;
import com.elevenst.terroir.product.registration.etc.vo.PrdEtcVO;
import com.elevenst.terroir.product.registration.etc.vo.PrdOthersVO;
import com.elevenst.terroir.product.registration.option.service.OptionServiceImpl;
import com.elevenst.terroir.product.registration.option.vo.OptionVO;
import com.elevenst.terroir.product.registration.option.vo.ProductStockVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.validate.ProductValidate;
import com.elevenst.terroir.product.registration.product.vo.BaseVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.validate.SellerValidate;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("local")
@SpringBootTest
public class EtcServiceTest {
    @Autowired
    EtcServiceImpl etcService;
    @Autowired
    EtcMapperTest etcMapperTest;
    @Autowired
    OptionServiceImpl optionService;



    private static final long PRD_NO01 = 888888883L;
    private static final long PRD_NO02 = 888888884L;
    private static final long PRD_NO03 = 888888885L;
    private static final long PRD_NO04 = 888888886L;
    private static final long PRD_NO05 = 888888887L;
    private static final long PRD_NO06 = 888888888L;
    private static final long PRD_NO07 = 888888889L;
    private static final long PRD_NO08 = 888888890L;
    private static final long PRD_NO09 = 888888891L;


    private static final String PRD_DETAIL_LINK = "httpd://www.11st.co.kr";
    private static final String BEEF_TRACE_NO1 = "99";
    private static final String BEEF_TRACE_NO2 = "100";
    private static boolean IS_UPDATED_ETC = false;
    private static boolean IS_UPDATED_BEEFTRACE = false;
    private static boolean IS_DONE_GET_RSVLYN = false;
    private static boolean IS_UPDATED_BUYDSBLDY_INFO = false;
    private static boolean IS_UPDATED_YES24_ETC = false;
    private static boolean IS_UPDATED_PTNR_INFO = false;
    private static boolean IS_UPDATED_MEDICAL_INFO = false;
    private static boolean IS_UPDATED_SET_LIFE = false;
    private static boolean IS_UPDATED_PTNR_PRD_CLF = false;

    @Test
    public void insertProductEtc(){
        int result = 0;

        ProductVO prdVO = new ProductVO();
        PrdEtcVO testVO = new PrdEtcVO();
        prdVO.setPrdNo(PRD_NO01);
        testVO.setCreateNo(10000276);
        testVO.setUpdateNo(10000276);
        testVO.setPrdDetailOutLink("http://api.11st.co.kr");
        testVO.setSeatTyp("Y");
        testVO.setTheaterAreaInfo("test");
        testVO.setTheaterNm("테스트");
        testVO.setPrdSvcBgnDy("20171026000000");
        testVO.setPrdSvcEndDy("20171027000000");
        testVO.setAuthorInfo("authorInfo");
        testVO.setBookPage("126");
        testVO.setBookSize("big");
        testVO.setFreeGiftTgNo1("test");
        testVO.setFreeGiftTgNo2("test");
        testVO.setIsbn10Cd("01");
        testVO.setIsbn13Cd("02");
        testVO.setMainTitle("title");
        testVO.setMudvdLabel("blacklabel");
        testVO.setCompInfo("11st");
        testVO.setIssuCoLogoUrl("test.co.kr");
        testVO.setVodBrowserTyp("exploser");
        testVO.setVodUrl("api.11st.co.kr");
        testVO.setStandardSalesQty(3);
        testVO.setSalesQty(1);
        testVO.setTransInfo("test");
        testVO.setPicInfo("test");
        testVO.setPreVwCd("05");
        testVO.setBookClfCd("03");
        prdVO.setPrdEtcVO(testVO);
        prdVO.setIsOpenApi("Y");

        result = etcService.insertProductEtc(prdVO);
        Assert.assertEquals(1,result);
    }

    @Test
    public void updateProductEtc(){
        int result = 0;
        PdPrdEtc testVO = new PdPrdEtc();
        testVO.setPrdNo(PRD_NO01);
        testVO.setCreateNo(10000276);
        testVO.setUpdateNo(10000276);
        testVO.setPrdDetailOutLink(PRD_DETAIL_LINK);
        testVO.setSeatTyp("Y");
        testVO.setTheaterAreaInfo("test");
        testVO.setTheaterNm("테스트");
        testVO.setPrdSvcBgnDy("20171026000000");
        testVO.setPrdSvcEndDy("20171027000000");
        testVO.setAuthorInfo("authorInfo");
        testVO.setBookPage("126");
        testVO.setBookSize("big");
        testVO.setFreeGiftTgNo1("test");
        testVO.setFreeGiftTgNo2("test");
        testVO.setIsbn10Cd("01");
        testVO.setIsbn13Cd("02");
        testVO.setMainTitle("title");
        testVO.setMudvdLabel("blacklabel");
        testVO.setCompInfo("11st");
        testVO.setIssuCoLogoUrl("test.co.kr");
        testVO.setVodBrowserTyp("exploser");
        testVO.setVodUrl("api.11st.co.kr");
        testVO.setStandardSalesQty(3);
        testVO.setSalesQty(1);
        testVO.setTransInfo("test");
        testVO.setPicInfo("test");
        testVO.setPreVwCd("05");

        PrdEtcVO orgProductEtc;

        orgProductEtc = etcMapperTest.getProductEtc(testVO.getPrdNo());
        //ETC 정보가 있으면 등록 없으면 수정
        if (orgProductEtc == null) {
            result = etcMapperTest.insertProductEtc(testVO);
        } else {
            result = etcMapperTest.updateProductEtc(testVO);
            IS_UPDATED_ETC = true;
        }


        PrdEtcVO updatedProductEtc;
        updatedProductEtc = etcMapperTest.getProductEtc(testVO.getPrdNo());


        Assert.assertEquals(1,result);
        Assert.assertEquals(updatedProductEtc.getPrdDetailOutLink(),PRD_DETAIL_LINK);
    }

    @Test
    public void insertBeefTraceInfo_insert(){
        int result = 0;

        PdPrdOthers pdPrdOthers =  new PdPrdOthers();
        PrdOthersVO newData = new PrdOthersVO();
        newData.setPrdNo(PRD_NO02);
        newData.setUpdateNo(1004);
        newData.setCreateNo(1004);
        newData.setUpdateDt(DateUtil.getCurrentDate());
        newData.setCreateDt(DateUtil.getCurrentDate());
        newData.setBeefTraceNo(BEEF_TRACE_NO1);

        PrdOthersVO curData = null;

        if (newData.getPrdNo() > 0) {
            curData = etcMapperTest.getBeefTraceInfo(newData.getPrdNo());
        }

        BeanUtils.copyProperties(newData,pdPrdOthers);
        if (curData != null) {
            etcMapperTest.updateBeefTraceInfo(pdPrdOthers);
        } else if (newData != null && StringUtil.isNotEmpty(newData.getBeefTraceNo())) {
            result += etcMapperTest.insertBeefTraceInfo(pdPrdOthers);
        }

        // 검증
        PrdOthersVO testData = null;
        testData = etcMapperTest.getBeefTraceInfo(newData.getPrdNo());

        Assert.assertEquals(1,result);
        Assert.assertEquals(testData.getBeefTraceNo(),BEEF_TRACE_NO1);
    }

    @Test
    public void insertBeefTraceInfo_update(){

        int result = 0;

        PdPrdOthers pdPrdOthers =  new PdPrdOthers();
        PrdOthersVO newData = new PrdOthersVO();
        newData.setPrdNo(PRD_NO02);
        newData.setUpdateNo(1004);
        newData.setCreateNo(1004);
        newData.setUpdateDt(DateUtil.getCurrentDate());
        newData.setCreateDt(DateUtil.getCurrentDate());
        newData.setBeefTraceNo(BEEF_TRACE_NO2);

        PrdOthersVO curData = null;

        if (newData.getPrdNo() > 0) {
            curData = etcMapperTest.getBeefTraceInfo(newData.getPrdNo());
        }

        BeanUtils.copyProperties(newData,pdPrdOthers);

        if (curData != null) {
            result +=  etcMapperTest.updateBeefTraceInfo(pdPrdOthers);
            IS_UPDATED_BEEFTRACE = true;
        } else if (newData != null && StringUtil.isNotEmpty(newData.getBeefTraceNo())) {
            etcMapperTest.insertBeefTraceInfo(pdPrdOthers);
        }

        // 검증
        PrdOthersVO testData = null;
        testData = etcMapperTest.getBeefTraceInfo(newData.getPrdNo());

        Assert.assertEquals(1,result);
        Assert.assertEquals(testData.getBeefTraceNo(),BEEF_TRACE_NO2);
    }

    @Test
    public void getPrdRsvSchdlYn() {

        int result=0;

        PrdOthersVO testData = new PrdOthersVO();
        PdPrdOthers pdPrdOthers =  new PdPrdOthers();

        testData.setPrdNo(PRD_NO03);
        testData.setUpdateNo(1004);
        testData.setCreateNo(1004);
        testData.setUpdateDt(DateUtil.getCurrentDate());
        testData.setCreateDt(DateUtil.getCurrentDate());
        testData.setBeefTraceNo(BEEF_TRACE_NO1);
        testData.setRsvSchdlClfCd("11");
        testData.setRsvSchdlYn("Y");

        BeanUtils.copyProperties(testData,pdPrdOthers);

        result = etcMapperTest.insertPrdRsvSchdlYnTest(pdPrdOthers);

        Assert.assertEquals(1,result);


        HashMap curData = etcMapperTest.getPrdRsvSchdlYn(pdPrdOthers);

        Assert.assertEquals("Y",curData.get("RSVSCHDLYN"));
        Assert.assertEquals("11",curData.get("RSVSCHDLCLFCD"));

        IS_DONE_GET_RSVLYN =  true;
    }

    @Test
    public void mergeProductOthersBuyDsblDyInfo_insert() {

        int result = 0;

        PrdOthersVO prdOthersVO = new PrdOthersVO();
        prdOthersVO.setPrdNo(PRD_NO04);
        prdOthersVO.setBuyDsblDyCd("01");
        prdOthersVO.setCreateNo(1004);
        prdOthersVO.setUpdateNo(1004);

        PrdOthersVO curData = new PrdOthersVO();
        PdPrdOthers pdPrdOthers = new PdPrdOthers();

        if (prdOthersVO.getPrdNo() > 0) {
            curData = etcMapperTest.getProdMedicalDeviceInfo(prdOthersVO.getPrdNo());
        }

        BeanUtils.copyProperties(prdOthersVO,pdPrdOthers);

        if (curData != null) {
            etcMapperTest.updateProductOthersBuyDsblDyInfo(pdPrdOthers);
        } else if (prdOthersVO != null && StringUtil.isNotEmpty(prdOthersVO.getBuyDsblDyCd())) {
            result += etcMapperTest.insertProductOthersBuyDsblDyInfo(pdPrdOthers);
        }

        Assert.assertEquals(1,result);
    }


    @Test
    public void mergeProductOthersBuyDsblDyInfo_update() {

        int result = 0;
        PrdOthersVO prdOthersVO = new PrdOthersVO();
        prdOthersVO.setPrdNo(PRD_NO04);
        prdOthersVO.setBuyDsblDyCd("02");
        prdOthersVO.setCreateNo(1004);
        prdOthersVO.setUpdateNo(1004);

        PdPrdOthers pdPrdOthers = new PdPrdOthers();

        PrdOthersVO curData = new PrdOthersVO();

        if (prdOthersVO.getPrdNo() > 0) {
            curData = etcMapperTest.getProdMedicalDeviceInfo(prdOthersVO.getPrdNo());
        }

        BeanUtils.copyProperties(prdOthersVO,pdPrdOthers);

        if (curData != null) {
            result += etcMapperTest.updateProductOthersBuyDsblDyInfo(pdPrdOthers);
            IS_UPDATED_BUYDSBLDY_INFO = true;
        } else if (prdOthersVO != null && StringUtil.isNotEmpty(prdOthersVO.getBuyDsblDyCd())) {
            etcMapperTest.insertProductOthersBuyDsblDyInfo(pdPrdOthers);
        }

        Assert.assertEquals(1,result);

        PrdOthersVO testData = new PrdOthersVO();
        testData = etcMapperTest.getProdMedicalDeviceInfo(prdOthersVO.getPrdNo());

        Assert.assertEquals("02",testData.getBuyDsblDyCd());
    }

    @Test
    public void updateProductEtcYes24_insert() {
        PdPrdEtc pdPrdEtc = new PdPrdEtc();

        pdPrdEtc.setPrdNo(PRD_NO05);
        pdPrdEtc.setPrdSvcEndDy("20171108000000");
        pdPrdEtc.setPrdSvcBgnDy("20171108000000");
        pdPrdEtc.setPreVwCd("12");
        pdPrdEtc.setPicInfo("test");
        pdPrdEtc.setTransInfo("transinfo");
        pdPrdEtc.setSalesQty(1);
        pdPrdEtc.setStandardSalesQty(2);
        pdPrdEtc.setVodUrl("http://test.com");
        pdPrdEtc.setVodBrowserTyp("12");
        pdPrdEtc.setCompInfo("cominfo");
        pdPrdEtc.setMudvdLabel("label");
        pdPrdEtc.setIssuCoLogoUrl("http://test.com");
        pdPrdEtc.setPicInfo("priceInfo");
        pdPrdEtc.setIsbn13Cd("13cd");
        pdPrdEtc.setIsbn10Cd("10cd");
        pdPrdEtc.setFreeGiftTgNo2("no2");
        pdPrdEtc.setFreeGiftTgNo1("no1");
        pdPrdEtc.setBookSize("booksize");
        pdPrdEtc.setBookPage("bookpage");
        pdPrdEtc.setPrdDetailOutLink("detailoutlink");
        pdPrdEtc.setUpdateNo(1004);
        pdPrdEtc.setCreateNo(1004);
        pdPrdEtc.setMainTitle("maintitle");
        pdPrdEtc.setPntCupnNo("cupnno");
        pdPrdEtc.setSeatTyp("T");
        pdPrdEtc.setTheaterAreaInfo("theaterAreaInfo");
        pdPrdEtc.setAuthorInfo("authorInfo");
        pdPrdEtc.setTheaterNm("teaterNm");

        int rtnInt = 0;
        PrdEtcVO orgProductEtcVO = new PrdEtcVO();
        orgProductEtcVO = etcMapperTest.getProductEtc(pdPrdEtc.getPrdNo());
        //ETC 정보가 있으면 등록 없으면 수정
        if(orgProductEtcVO == null) {
            rtnInt += etcMapperTest.insertProductEtc(pdPrdEtc);
        } else {
            etcMapperTest.updateProductEtcYes24(pdPrdEtc);
        }
        Assert.assertEquals(1,rtnInt);
    }

    @Test
    public void updateProductEtcYes24_update() {
        PdPrdEtc pdPrdEtc = new PdPrdEtc();

        pdPrdEtc.setPrdNo(PRD_NO05);
        pdPrdEtc.setPreVwCd("12");
        pdPrdEtc.setPicInfo("test");
        pdPrdEtc.setTransInfo("transinfo");
        pdPrdEtc.setSalesQty(1);
        pdPrdEtc.setStandardSalesQty(2);
        pdPrdEtc.setVodUrl("http://test.com");
        pdPrdEtc.setVodBrowserTyp("12");
        pdPrdEtc.setCompInfo("cominfo");
        pdPrdEtc.setMudvdLabel("label");
        pdPrdEtc.setIssuCoLogoUrl("http://test.com");
        pdPrdEtc.setPicInfo("priceInfo");
        pdPrdEtc.setIsbn13Cd("13cd");
        pdPrdEtc.setIsbn10Cd("10cd");
        pdPrdEtc.setFreeGiftTgNo2("no2");
        pdPrdEtc.setFreeGiftTgNo1("no1");
        pdPrdEtc.setBookSize("booksize");
        pdPrdEtc.setBookPage("bookpage");
        pdPrdEtc.setPrdDetailOutLink("detailoutlink");
        pdPrdEtc.setUpdateNo(1004);
        pdPrdEtc.setCreateNo(1004);
        pdPrdEtc.setMainTitle("maintitle");
        pdPrdEtc.setPntCupnNo("cupnno");
        pdPrdEtc.setSeatTyp("T");
        pdPrdEtc.setTheaterAreaInfo("theaterAreaInfo");
        pdPrdEtc.setAuthorInfo("authorInfo");
        pdPrdEtc.setTheaterNm("teaterNm");

        int rtnInt = 0;
        PrdEtcVO orgProductEtcVO = new PrdEtcVO();
        orgProductEtcVO = etcMapperTest.getProductEtc(pdPrdEtc.getPrdNo());
        //ETC 정보가 있으면 등록 없으면 수정
        if(orgProductEtcVO == null) {
            etcMapperTest.insertProductEtc(pdPrdEtc);
        } else {
            rtnInt += etcMapperTest.updateProductEtcYes24(pdPrdEtc);
            IS_UPDATED_YES24_ETC = true;
        }
        Assert.assertEquals(1,rtnInt);

    }

    @Test
    public void mergeProductOthersPtnrInfo_insert() {

        int result = 0;

        PrdOthersVO prdOthersVO = new PrdOthersVO();
        PdPrdOthers pdPrdOthers = new PdPrdOthers();

        prdOthersVO.setUpdateNo(1004);
        prdOthersVO.setPrdNo(PRD_NO06);
        prdOthersVO.setCreateNo(1004);

        PrdOthersVO curData = null;

        if(prdOthersVO.getPrdNo() > 0) {
            curData = etcMapperTest.getProdMedicalDeviceInfo(prdOthersVO.getPrdNo());
        }

        if(prdOthersVO != null) prdOthersVO.setPtnrPrdClfCd(ProductConstDef.PTNR_PRD_CLF_CD_SPEC_DLV);

        BeanUtils.copyProperties(prdOthersVO,pdPrdOthers);

        if(curData != null){
            etcMapperTest.updateProductOthersPtnrInfo(pdPrdOthers);
        }else if(prdOthersVO != null && StringUtil.isNotEmpty(prdOthersVO.getPtnrPrdClfCd())){
            result += etcMapperTest.insertProductOthersPtnrInfo(pdPrdOthers);
        }

        Assert.assertEquals(1,result);
    }

    @Test
    public void mergeProductOthersPtnrInfo_update() {

        int result = 0;

        PrdOthersVO prdOthersVO = new PrdOthersVO();
        PdPrdOthers pdPrdOthers = new PdPrdOthers();

        prdOthersVO.setUpdateNo(1005);
        prdOthersVO.setPrdNo(PRD_NO06);
        prdOthersVO.setCreateNo(1005);

        PrdOthersVO curData = null;

        if(prdOthersVO.getPrdNo() > 0) {
            curData = etcMapperTest.getProdMedicalDeviceInfo(prdOthersVO.getPrdNo());
        }

        if(prdOthersVO != null) prdOthersVO.setPtnrPrdClfCd(ProductConstDef.PTNR_PRD_CLF_CD_SPEC_DLV);

        BeanUtils.copyProperties(prdOthersVO , pdPrdOthers);

        if(curData != null){
            result += etcMapperTest.updateProductOthersPtnrInfo(pdPrdOthers);
            IS_UPDATED_PTNR_INFO = true;
        }else if(prdOthersVO != null && StringUtil.isNotEmpty(prdOthersVO.getPtnrPrdClfCd())){
            etcMapperTest.insertProductOthersPtnrInfo(pdPrdOthers);
        }

        Assert.assertEquals(1,result);
    }

    @Test
    public void insertProdMedicalDeviceInfo_insert() {

        int result = 0;
        PrdOthersVO prdOthersVO = new PrdOthersVO();
        PdPrdOthers pdPrdOthers = new PdPrdOthers();

        prdOthersVO.setPrdNo(PRD_NO07);
        prdOthersVO.setQCVerifiedStat(true);
        prdOthersVO.setHealthItmAuthNum("test1");
        prdOthersVO.setHealthSaleCoNum("test2");
        prdOthersVO.setPreAdvChkNum("test3");
        prdOthersVO.setTemplatePrdNo(1003);

        PrdOthersVO newData = prdOthersVO;
        PrdOthersVO curData = null;

        // 상품  의료기기 품목허가번호 정보 조회
        if(newData.getPrdNo() > 0) {
            curData = etcMapperTest.getProdMedicalDeviceInfo(newData.getPrdNo());
        }

        // 현재 등록되어 있는 의료기기 정보가 있으면 수정 아니면 등록
        /*
        healthItmAuthNum = getMedNum1
        healthSaleCoNum =  getMedNum2
        preAdvChkNum = getMedNum3
         */

        BeanUtils.copyProperties(newData,pdPrdOthers);

        if(curData != null){
            if(!prdOthersVO.isQCVerifiedStat()){
                String preMedNum1 = StringUtil.nvl(curData.getHealthItmAuthNum());
                String medNum1 = prdOthersVO == null ? "" : StringUtil.nvl(prdOthersVO.getHealthItmAuthNum());

                prdOthersVO.setQCVerifiedStat(!preMedNum1.equals(medNum1));//Update 안하는데 왜 세팅하는가..?
            }
            etcMapperTest.updateProdMedicalDeviceInfo(pdPrdOthers);
        }else if((newData != null && (StringUtil.isNotEmpty(newData.getHealthItmAuthNum()) || StringUtil.isNotEmpty(newData.getHealthSaleCoNum()) || StringUtil.isNotEmpty(newData.getPreAdvChkNum()) || StringUtil.isNotEmpty(newData.getSelPrdClfFpCd())))
                || prdOthersVO.getTemplatePrdNo() > 0){
            if(!prdOthersVO.isQCVerifiedStat()) prdOthersVO.setQCVerifiedStat(true); //Insert도 안하는데 왜 세팅하는가..?
            result += etcMapperTest.insertProdMedicalDeviceInfo(pdPrdOthers);
        }

        Assert.assertEquals(1,result);
    }

    @Test
    public void insertProdMedicalDeviceInfo_update() {
        int result = 0;

        PrdOthersVO prdOthersVO = new PrdOthersVO();
        PdPrdOthers pdPrdOthers = new PdPrdOthers();

        prdOthersVO.setPrdNo(PRD_NO07);
        prdOthersVO.setQCVerifiedStat(false);
        prdOthersVO.setHealthItmAuthNum("test1");
        prdOthersVO.setHealthSaleCoNum("test2");
        prdOthersVO.setPreAdvChkNum("test3");
        prdOthersVO.setSelPrdClfFpCd("TCD");

        PrdOthersVO newData = prdOthersVO;
        PrdOthersVO curData = null;

        // 상품  의료기기 품목허가번호 정보 조회
        if(newData.getPrdNo() > 0) {
            curData = etcMapperTest.getProdMedicalDeviceInfo(newData.getPrdNo());
        }
        // 현재 등록되어 있는 의료기기 정보가 있으면 수정 아니면 등록
        /*
        healthItmAuthNum = getMedNum1
        healthSaleCoNum =  getMedNum2
        preAdvChkNum = getMedNum3
         */

        BeanUtils.copyProperties(newData,pdPrdOthers);
        if(curData != null){
            if(!prdOthersVO.isQCVerifiedStat()){
                String preMedNum1 = StringUtil.nvl(curData.getHealthItmAuthNum());
                String medNum1 = prdOthersVO == null ? "" : StringUtil.nvl(prdOthersVO.getHealthItmAuthNum());

                prdOthersVO.setQCVerifiedStat(!preMedNum1.equals(medNum1));//Update 안하는데 왜 세팅하는가..?
            }
            result += etcMapperTest.updateProdMedicalDeviceInfo(pdPrdOthers);
            IS_UPDATED_MEDICAL_INFO = true;
        }else if((newData != null && (StringUtil.isNotEmpty(newData.getHealthItmAuthNum()) || StringUtil.isNotEmpty(newData.getHealthSaleCoNum()) || StringUtil.isNotEmpty(newData.getPreAdvChkNum()) || StringUtil.isNotEmpty(newData.getSelPrdClfFpCd())))
                || prdOthersVO.getTemplatePrdNo() > 0){
            if(!prdOthersVO.isQCVerifiedStat()) prdOthersVO.setQCVerifiedStat(true); //Insert도 안하는데 왜 세팅하는가..?
            etcMapperTest.insertProdMedicalDeviceInfo(pdPrdOthers);
        }

        Assert.assertEquals(1,result);
    }

    @Test
    public void setProductOthersForLife() {

        int result = 0;

        PrdOthersVO prdOthersVO = new PrdOthersVO();
        ProductVO productVO = new ProductVO();
        PdPrdOthers pdPrdOthers = new PdPrdOthers();

        long productNo = PRD_NO08;

        prdOthersVO.setSelPrdRiousQty(1);
        prdOthersVO.setAddQueryCont("Cont");
        prdOthersVO.setSvcCanRgnClfCd("Cd");
        prdOthersVO.setDlvProcCanDd(11);
        prdOthersVO.setRsvSchdlYn("Y");
        prdOthersVO.setRsvSchdlClfCd("99");
        prdOthersVO.setPrdNo(productNo);

        productVO.setPtnrPrdClfCd("99");
        productVO.setSelMnbdNo(10000276);

        prdOthersVO.setCreateNo(productVO.getSelMnbdNo());
        productVO.setPrdOthersVO(prdOthersVO);
        productVO.setPtnrPrdClfCd("01");


//        SellerValidate sellerValidate = new SellerValidate();
//
//        if(!"".equals(productVO.getPtnrPrdClfCd()) && sellerValidate.isCertStockCdSeller(productVO.getSelMnbdNo())) {
//            prdOthersVO.setPtnrPrdClfCd(productVO.getPtnrPrdClfCd());
//        }
//        BeanUtils.copyProperties(prdOthersVO,pdPrdOthers);
//        result += etcMapperTest.insertProductOthersForLife(pdPrdOthers);

        etcService.setProductOthersForLife(productVO,0);

//        Assert.assertEquals(1,result);
//        IS_UPDATED_SET_LIFE = true;
    }
    @Test
    public void mergeProductOthersForPtnrPrdClf() {

        int result = 0;

        ProductStockVO productStockVO = new ProductStockVO();
        PrdOthersVO prdOthersVO = new PrdOthersVO();
        PdPrdOthers pdPrdOthers = new PdPrdOthers();

        long productNo = PRD_NO09;

        ProductStockVO setProductStockVO = new ProductStockVO();
        setProductStockVO.setPrdNo(PRD_NO09);
        productStockVO.setUpdateNo(10000276);
        productStockVO.setPrdSellerStockCd("TESTSTOCKCD11");

//        // 지정일상품코드가 들어왔는지 여부 확인
//        String prdSellerStockCd = productStockVO.getPrdSellerStockCd();
//        setProductStockVO.setPrdNo(productStockVO.getPrdNo());
//
//        List<ProductStockVO> productStockList = optionService.getPdStockPtnrInfoByPrdNo(setProductStockVO);
////        HashMap<String, Object> ptnrPrdClfMap = new HashMap<String, Object>();
////        ptnrPrdClfMap.put("prdNo", productNo);
//        prdOthersVO.setPrdNo(productNo);
////        ptnrPrdClfMap.put("ptnrPrdClfCd", "");
//        prdOthersVO.setPtnrPrdClfCd("");
//        if(!"".equals(prdSellerStockCd) || (productStockList != null && productStockList.size() > 0)) {
////            ptnrPrdClfMap.put("ptnrPrdClfCd", ProductConstDef.PTNR_PRD_CLF_CD_SPEC_DLV);
//            prdOthersVO.setPtnrPrdClfCd(ProductConstDef.PTNR_PRD_CLF_CD_SPEC_DLV);
//        }
////        ptnrPrdClfMap.put("memNo", productStockVO.getUpdateNo());
//        prdOthersVO.setCreateNo(productStockVO.getUpdateNo());
//
//        BeanUtils.copyProperties(prdOthersVO , pdPrdOthers);
//
//        result = etcMapperTest.insertProductOthersForLife(pdPrdOthers);

//        etcService.mergeProductOthersForPtnrPrdClf(productStockVO,productNo);

//        Assert.assertEquals(1,result);
    }

    @Test
    public void updateProductOthersForPtnrPrdClf() {

        int result = 0;

        ProductVO productVO = new ProductVO();

        ProductStockVO productStockVO = new ProductStockVO();
        PdPrdOthers pdPrdOthers = new PdPrdOthers();
        PrdOthersVO prdOthersVO  = new PrdOthersVO();
        long productNo = PRD_NO09;

        ProductStockVO setProductStockVO = new ProductStockVO();
        // 지정일상품코드가 들어왔는지 여부 확인
        String prdSellerStockCd = productStockVO.getPrdSellerStockCd();
        setProductStockVO.setPrdNo(productStockVO.getPrdNo());
        List<ProductStockVO> productStockList = new ArrayList<>();

        String ptnrPrdClfCd = "";

        setProductStockVO = new ProductStockVO();
        setProductStockVO.setOptItemNo1(0);
        setProductStockVO.setPrdNo(productStockVO.getPrdNo());
        productStockVO.setPrdSellerStockCd("55");
        productStockVO.setPrdNo(productNo);


        productVO.setProductStockVO(setProductStockVO);
        productVO.setUpdateNo(1000278);
        productVO.setPrdOthersVO(prdOthersVO);

        etcService.updateProductOthersForPtnrPrdClf(productVO,productNo);

//        int retVal = 0;//optionService.getSingleOptionCnt(setProductStockVO);
//        // 옵션이 없을경우 상품의 지정일상품코드 정보가 있을 경우
//        if((!"".equals(prdSellerStockCd) && retVal > 0) || (productStockList != null && productStockList.size() > 0)) {
//            ptnrPrdClfCd = ProductConstDef.PTNR_PRD_CLF_CD_SPEC_DLV;
//        }
//        productStockVO.setPrdNo(productNo);
//        productStockVO.setUpdateNo(0);
//        if(productStockVO.getPrdOthersVO() == null) {
//            productStockVO.setPrdOthersVO(prdOthersVO);
//        }
//        productStockVO.getPrdOthersVO().setPtnrPrdClfCd(ptnrPrdClfCd);
//        productStockVO.getPrdOthersVO().setPrdNo(productNo);
//        //************check insert prdNo
//
//        BeanUtils.copyProperties(productStockVO.getPrdOthersVO(), pdPrdOthers);
//        result = etcMapperTest.updateProductOthersPtnrInfo(pdPrdOthers);
//
//        Assert.assertEquals(1,result);
//        IS_UPDATED_PTNR_PRD_CLF = true;
    }

    @After
    public void after() {

        if(IS_UPDATED_ETC) {
            int result = 0;
            result = etcMapperTest.deleteProductEtcTest(PRD_NO01);
            Assert.assertEquals(1,result);
            IS_UPDATED_ETC = false;
        }
        if(IS_UPDATED_BEEFTRACE) {
            int result = 0;
            result = etcMapperTest.deleteBeefTraceInfoTest(PRD_NO02);
            Assert.assertEquals(1,result);
            IS_UPDATED_BEEFTRACE = false;
        }
        if(IS_DONE_GET_RSVLYN) {
            int result = 0;
            result = etcMapperTest.deleteBeefTraceInfoTest(PRD_NO03);
            Assert.assertEquals(1,result);
            IS_DONE_GET_RSVLYN = false;
        }
        if(IS_UPDATED_BUYDSBLDY_INFO) {
            int result = 0;
            result = etcMapperTest.deleteBeefTraceInfoTest(PRD_NO04);
            Assert.assertEquals(1,result);
            IS_UPDATED_BUYDSBLDY_INFO =false;
        }
        if(IS_UPDATED_YES24_ETC) {
            int result = 0;
            result = etcMapperTest.deleteProductEtcTest(PRD_NO05);
            Assert.assertEquals(1,result);
            IS_UPDATED_YES24_ETC = false;
        }
        if(IS_UPDATED_PTNR_INFO) {
            int result = 0;
            result = etcMapperTest.deleteBeefTraceInfoTest(PRD_NO06);
            Assert.assertEquals(1,result);
            IS_UPDATED_PTNR_INFO = false;
        }
        if(IS_UPDATED_MEDICAL_INFO) {
            int result = 0;
            result = etcMapperTest.deleteBeefTraceInfoTest(PRD_NO07);
            Assert.assertEquals(1,result);
            IS_UPDATED_MEDICAL_INFO = false;
        }
        if(IS_UPDATED_SET_LIFE) {
            int result = 0;
            result = etcMapperTest.deleteBeefTraceInfoTest(PRD_NO08);
            Assert.assertEquals(1,result);
            IS_UPDATED_SET_LIFE = false;
        }
        if(IS_UPDATED_PTNR_PRD_CLF) {
            int result = 0;
            result = etcMapperTest.deleteBeefTraceInfoTest(PRD_NO09);
            Assert.assertEquals(1,result);
            IS_UPDATED_PTNR_PRD_CLF = false;
        }
    }

    @Test
    public void checkEtcDataInsertProcess() {
        ProductVO productVO = new ProductVO();
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setDispCtgrPrdTypCd("01");
        productVO.setCategoryVO(categoryVO);
        productVO.setPrdNo(99999999L);
        BaseVO baseVO = new BaseVO();
        baseVO.setPrdTypCd("222");
        productVO.setBaseVO(baseVO);
        PrdEtcVO prdEtcVO = new PrdEtcVO();
//        prdEtcVO.setBookClfCd("01");
        productVO.setPrdEtcVO(prdEtcVO);
        productVO.setHmallVod(false);
        PrdOthersVO prdOthersVO = new PrdOthersVO();
        productVO.setPrdOthersVO(prdOthersVO);
        OptionVO optionVO = new OptionVO();
        productVO.setOptionVO(optionVO);
        etcService.checkEtcDataInsertProcess(productVO);
    }

    @Test
    public void setEtcProductVO() {
        ProductVO productVO = new ProductVO();
        PrdOthersVO prdOthersVO = new PrdOthersVO();
        prdOthersVO.setBeefTraceStat("02");
        prdOthersVO.setBeefTraceNo("11");
        prdOthersVO.setPtnrPrdClfCd("01");
        prdOthersVO.setBuyDsblDyCd("03");
        prdOthersVO.setQCVerifiedStat(true);
        prdOthersVO.setBeefTraceDtl("001");
        productVO.setPrdNo(99999999);
        productVO.setPrdOthersVO(prdOthersVO);

        etcService.setEtcProductVO(productVO);
    }
}
