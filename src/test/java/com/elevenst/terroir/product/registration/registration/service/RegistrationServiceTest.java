package com.elevenst.terroir.product.registration.registration.service;

import com.elevenst.terroir.product.registration.catalog.vo.CtlgVO;
import com.elevenst.terroir.product.registration.category.vo.DpGnrlDispVO;
import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.desc.vo.ProductDescVO;
import com.elevenst.terroir.product.registration.etc.vo.PrdEtcVO;
import com.elevenst.terroir.product.registration.etc.vo.PrdOthersVO;
import com.elevenst.terroir.product.registration.option.vo.OptionVO;
import com.elevenst.terroir.product.registration.price.vo.PriceVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.BaseVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;

import com.elevenst.terroir.product.registration.seller.vo.SellerVO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
public class RegistrationServiceTest {

    @Autowired
    RegistrationServiceImpl registrationService;

    public ProductVO setProductVO(){

        ProductVO productVO = new ProductVO();
        productVO.setSeriesPrdYn(false);
        productVO.setChannel("SO");
        productVO.setCreateNo(10000276);
        productVO.setUpdateNo(10000276);
        productVO.setSelMnbdNo(10000276);
        productVO.setTown(false);
        productVO.setPrdNm("[나이키] 다이나모 프리 (TD) [343938] 테스트 상품");
        productVO.setPrdCopyYn("N");
        productVO.setReNewYn("N");
        productVO.setOptChangeYn("Y");
        productVO.setDispCtgrNo(1007649);

        SellerVO sellerVO = new SellerVO();
        sellerVO.setCyberMoney(1223210);
        sellerVO.setSellableBundlePrdSeller(false);
        sellerVO.setSelMnbdNckNmSeq(1601738);

        MemberVO memberVO = new MemberVO();
        memberVO.setMemTypCD("02");
        memberVO.setMemNO(10000276);

        BaseVO baseVO = new BaseVO();
        baseVO.setPrdStatCd("01");
        baseVO.setDlvCstPayTypCd("01");
        baseVO.setSelMinLimitTypCd("00");
        baseVO.setPrdTypCd("01");
        baseVO.setBsnDealClf("01");
        baseVO.setOrgnTypCd("01");
//        baseVO.setDlvCst(2500);
        baseVO.setMinorSelCnYn("Y");
        baseVO.setBndlDlvCnYn("Y");
        baseVO.setIslandDlvCst(3000);
        baseVO.setStdPrdNm("다이나모 프리 (TD) [343938]");
        baseVO.setGblDlvYn("N");
        baseVO.setGblHsCode("6403");
        baseVO.setRtngdDlvCd("01");
        baseVO.setTodayDlvCnYn("N");
        baseVO.setShopNo(10015346);
        baseVO.setAllBranchUseYn("N");
        baseVO.setSelMinLimitQty(0);
        baseVO.setDispCtgr1NoDe(1001390);
        baseVO.setDispCtgr2NoDe(1002167);
        baseVO.setDispCtgr3NoDe(1007649);
        baseVO.setRcptIsuCnYn("Y");
        baseVO.setSelMthdCd("01");
//        baseVO.setPrdStckQty(500);
        baseVO.setSelEndDy("29991231");
        baseVO.setNtNo(304);
        baseVO.setJejuDlvCst(3000);
        baseVO.setDlvClf("02");
        baseVO.setSetTypCd("01");
        baseVO.setSelBgnDy("20171112");
        baseVO.setRtngdDlvCst(1000);
        baseVO.setBcktExYn("N");
        baseVO.setInfoTypeCtgrNo(891012);
        baseVO.setModelNo(226922);
        baseVO.setTownSellerCertType("100");
        baseVO.setModelNm("다이나모 프리 (TD) [343938]");
        baseVO.setMktPrdNm("테스트 상품");
        baseVO.setSelLimitQty(0);
        baseVO.setCreateIp("127.0.0.1");
        baseVO.setUpdateIp("127.0.0.1");
        baseVO.setAbrdBrandYn("N");
        baseVO.setReviewDispYn("Y");
        baseVO.setExchDlvCst(2000);
        baseVO.setBrandNm("나이키");
        baseVO.setSellerPcId("14773051038359617659959");
        baseVO.setAppmtDyDlvCnYn("N");
        baseVO.setCreateCd("0203");
        baseVO.setEngDispYn("Y");
        baseVO.setDlvCnAreaCd("01");
        baseVO.setSelPrdClfCd("100");
        baseVO.setSelTermDy("0");
        baseVO.setSndPlnTrm(4);
        baseVO.setSelLimitTypCd("00");
        baseVO.setPrcCmpExpYn("Y");
        baseVO.setDlvWyCd("01");
        baseVO.setSuplDtyfrPrdClfCd("01");
        baseVO.setPrdWght(500);
        baseVO.setDlvCstInstBasiCd("01");
        baseVO.setStdPrdYn("Y");
        baseVO.setCertTypCd("132");
//        baseVO.setEmployeeNo(0); <-- 담당MD 번호


//        CategoryVO categoryVO = new CategoryVO();
//        categoryVO.setDispCtgrStatCd("03");
//        categoryVO.setAdltCrtfYn("Y");
//        categoryVO.setMaxLimitQty(0);
//        categoryVO.setDispCtgrPrdTypCd("01");
//        categoryVO.setRootCtgrNo(1001390);
//        categoryVO.setBrandCd("6161");
//        categoryVO.setOfferDispLmtYn("N");
//        categoryVO.setAdultCtgr("N");

//        ProductStockVO productStockVO = new ProductStockVO();
//        productStockVO.setPtnrPrdSellerYN("Y");
//        productStockVO.setSelQty(500);

        PrdEtcVO prdEtcVO = new PrdEtcVO();
        prdEtcVO.setBookClfCd("01");
        prdEtcVO.setIssuCoLogoUrl("testLogoURL");
        productVO.setPrdEtcVO(prdEtcVO);
        setBookDVDEtcPrdVO(productVO);

        PrdOthersVO prdOthersVO = new PrdOthersVO();
        prdOthersVO.setSelPrdClfFpCd("-1:-1");
        prdOthersVO.setSvcCanRgnClfCd("01");
        prdOthersVO.setPrdRiousQty(0);
        prdOthersVO.setDlvProcCanDd(1);
        prdOthersVO.setRsvSchdlClfCd("01");
        prdOthersVO.setRsvSchdlYn("N");
        prdOthersVO.setBeefTraceStat("test");

        if("상세설명 참조".equals(prdOthersVO.getBeefTraceStat())) {
            prdOthersVO.setBeefTraceNo("99");
        }
//        prdOthersVO.setBuyDsblDyCd("11");
//        prdOthersVO.setPtnrPrdClfCd("22");
        prdOthersVO.setCreateNo(productVO.getCreateNo());
        prdOthersVO.setUpdateNo(productVO.getCreateNo());

        DpGnrlDispVO dpGnrlDispVO = new DpGnrlDispVO();
        dpGnrlDispVO.setBrandCd("6161");
        dpGnrlDispVO.setStckQty(500);

        PriceVO priceVO = new PriceVO();
        priceVO.setSelPrc(50000);
        priceVO.setMpContractTypCd("01");

        OptionVO optionVO = new OptionVO();
        optionVO.setOptClfCd("01");
        HashMap<String,String> tmpColMap = new HashMap<String, String>();
        tmpColMap.put("143", "Y");
        optionVO.setTmpColMap(tmpColMap);
        optionVO.setPrdSelQty(500);
        optionVO.setCtlgNo(226922);

        CtlgVO ctlgVO = new CtlgVO();
        ctlgVO.setCtlgNo(226922);

        List<ProductDescVO> productDescVOList = new ArrayList<ProductDescVO>();
        ProductDescVO productDescVO = new ProductDescVO();
        productDescVO.setPrdDescTypCd("02");
        productDescVO.setPrdDescContClob("테스트 상품");
        productDescVO.setClobTypYn("Y");
        productDescVOList.add(productDescVO);
        productDescVO = new ProductDescVO();
        productDescVO.setPrdDescTypCd("06");
        productDescVO.setClobTypYn("N");
        productDescVO.setPrdDescContVc("테스트 상품");
        productDescVOList.add(productDescVO);
        productDescVO = new ProductDescVO();
        productDescVO.setPrdDescTypCd("07");
        productDescVO.setClobTypYn("N");
        productDescVO.setPrdDescContVc("테스트 상품");
        productDescVOList.add(productDescVO);


        productVO.setBaseVO(baseVO);
        productVO.setSellerVO(sellerVO);
        productVO.setMemberVO(memberVO);
//        productVO.setCategoryVO(categoryVO);
        productVO.setPrdOthersVO(prdOthersVO);
        productVO.setDpGnrlDispVO(dpGnrlDispVO);
        productVO.setPriceVO(priceVO);
        productVO.setOptionVO(optionVO);
        productVO.setCtlgVO(ctlgVO);
        productVO.setProductDescVOList(productDescVOList);

        return productVO;
    }

    private void setBookDVDEtcPrdVO(ProductVO productVO) throws ProductException {
        PrdEtcVO prdEtcVO = new PrdEtcVO();

        try {
            if(ProductConstDef.DP_DISP_CTGR_BOOK_CLF_CD_BOOK.equals(productVO.getPrdEtcVO().getBookClfCd()) ||
                ProductConstDef.DP_DISP_CTGR_BOOK_CLF_CD_FOREIGN_BOOK.equals(productVO.getPrdEtcVO().getBookClfCd())) {

                prdEtcVO.setAuthorInfo("authorInfo");
                prdEtcVO.setTransInfo("transInfo");
                prdEtcVO.setPicInfo("picInfo");
                prdEtcVO.setIsbn13Cd("abcdefghijklm");
                prdEtcVO.setIsbn10Cd("abcdefghij");
                prdEtcVO.setBookSize("250");
                prdEtcVO.setBookPage("200p");
                prdEtcVO.setCompInfo("compInfo");
                prdEtcVO.setPreVwCd("VwCd");
                prdEtcVO.setMainTitle("메인제목");
                prdEtcVO.setFreeGiftTgNo1("G1");
                prdEtcVO.setFreeGiftTgNo2("G2");

                if(prdEtcVO.getIsbn13Cd().length() > 13){
                    throw new ProductException("ISBN-13은 13자까지 입력 가능합니다.");
                }else if(prdEtcVO.getIsbn10Cd().length() > 10){
                    throw new ProductException("ISBN-10은 10자까지 입력 가능합니다.");
                }
            } else if(ProductConstDef.DP_DISP_CTGR_BOOK_CLF_CD_MUSIC.equals(productVO.getPrdEtcVO().getBookClfCd())) {
                prdEtcVO.setMudvdLabel("dvdLabel");
                prdEtcVO.setFreeGiftTgNo1("G1");
                prdEtcVO.setFreeGiftTgNo2("G2");
            } else if(ProductConstDef.DP_DISP_CTGR_BOOK_CLF_CD_DVD.equals(productVO.getPrdEtcVO().getBookClfCd())) {
                prdEtcVO.setFreeGiftTgNo1("G1");
                prdEtcVO.setFreeGiftTgNo2("G2");
            }

            if("Y".equals(productVO.getIsOpenApi())) {
                prdEtcVO.setIssuCoLogoUrl("testLogoUrl");
            }

            productVO.setPrdEtcVO(prdEtcVO);
        }catch(ProductException ex) {
            throw ex;
        }catch(Exception ex) {
            throw new ProductException("상품 등록 "+ex, ex);
        }
    }

    @Test
//    @Transactional(propagation= Propagation.REQUIRED, readOnly=false)
    public void testRegistrationProduct() throws Exception{

        registrationService.registrationProduct(null, setProductVO());
        return;
    }

    @Test
    public void checkMobileUpdateInfoProcess() {
//        Boolean result = false;
        ProductVO productVO = new ProductVO();
//        List<ProductAddtInfoVO> testList = new ArrayList<>();
//        ProductAddtInfoVO productAddtInfoVO = new ProductAddtInfoVO();
//        productAddtInfoVO.setPrdNo(99999999);
//        testList.add(productAddtInfoVO);
//        productVO.setProductAddtInfoVOList(testList);

//        result = registrationService.checkMobileUpdateInfoProcess(productVO);
//        Assert.assertEquals(false,result);
//        registrationService.checkMobileUpdateInfoProcess(productVO);
    }


    public void getJsonString() {
        Gson gson = new Gson();
        String voJson = gson.toJson(setProductVO());
        System.out.println(voJson);
    }



}
