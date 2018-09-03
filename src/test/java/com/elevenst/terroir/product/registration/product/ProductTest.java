package com.elevenst.terroir.product.registration.product;

import com.elevenst.terroir.product.registration.option.code.OptionConstDef;
import com.elevenst.terroir.product.registration.option.vo.ProductOptLimitVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.entity.PdSvcPassPrd;
import com.elevenst.terroir.product.registration.product.service.ProductServiceImpl;
import com.elevenst.terroir.product.registration.product.validate.ProductValidate;
import com.elevenst.terroir.product.registration.product.vo.BaseVO;
import com.elevenst.terroir.product.registration.product.vo.ProductNameMultiLangVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class ProductTest {

    @Autowired
    ProductServiceImpl productServiceImpl;

    @Autowired
    ProductValidate productValidate;

    @Rule
    public ExpectedException expectedExcetption = ExpectedException.none();

    String prefixExceptionStr = "상품에러 : ";

    @Test
    public void checkLifeSvcTyp() {
        ProductVO productVO = new ProductVO();
        productVO.getBaseVO().setPrdTypCd(ProductConstDef.PRD_TYP_CD_LIFE_TOWN_VISIT);
        boolean isResult = productValidate.checkLifeSvcTyp(productVO);
        Assert.assertSame(isResult, true);
    }

    @Test
    public void checkLifeSvcTyp2() {
        boolean isResult = productValidate.checkLifeSvcTyp(ProductConstDef.PRD_TYP_CD_LIFE_BUSINESS_TRIP_SERVICE);
        Assert.assertSame(isResult, true);
    }

    @Test
    public void checkLifeSvcTyp3() {
        boolean isResult = productValidate.checkLifeSvcTyp(ProductConstDef.PRD_TYP_CD_LIFE_PICKUP_N_DEVLIVERY);
        Assert.assertSame(isResult, true);
    }

    @Test
    public void checkLifeSvcTyp4() {
        boolean isResult = productValidate.checkLifeSvcTyp(ProductConstDef.PRD_TYP_CD_NORMAL);
        Assert.assertSame(isResult, false);
    }

    @Test
    public void checkPrdSelQty() {

        //String optClfCd, String updYn, String prdSelQty, boolean isMixedOpt, String bsnDealClf, String isTownExcelUser, String dlvClf)
        String optClfCd = OptionConstDef.OPT_CLF_CD_MIXED;
        String updYn = "N";
        String prdSelQty = "0";
        boolean isMixedOpt = false;
        String bsnDealClf = "03";
        String isTownExcelUser = "N";
        String dlvClf = "03";
        expectedExcetption.expectMessage(prefixExceptionStr+"재고수량은 반드시 입력하셔야 합니다.");
        try {
            //expectedExcetption.expectMessage(prefixExceptionStr+"재고수량은 반드시 입력하셔야 합니다.");
            productValidate.checkPrdSelQty(optClfCd, updYn, prdSelQty, isMixedOpt, bsnDealClf, isTownExcelUser, dlvClf);
        } catch(Exception e) {
            e.getMessage();
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void checkPrdSelQty2() {
        String optClfCd = OptionConstDef.OPT_CLF_CD_MIXED;
        String updYn = "Y";
        String prdSelQty = "1000000001";
        boolean isMixedOpt = true;
        String bsnDealClf = "03";
        String isTownExcelUser = "N";
        String dlvClf = "03";
        try {
            expectedExcetption.expectMessage(prefixExceptionStr+"재고 수량은 1,000,000,000개 이하로 입력하셔야 합니다.");
            productValidate.checkPrdSelQty(optClfCd, updYn, prdSelQty, isMixedOpt, bsnDealClf, isTownExcelUser, dlvClf);
        } catch(Exception e) {
        }
    }

//    @Test
//    public void updateProductStockSelStatCd() {
//        long prdNo = 1244995215;
//        productServiceImpl.updateProductStockSelStatCd(prdNo);
//    }

//    @Test
//    public void updateProductStockSelStatCdBySoldOut() {
//        long prdNo = 1244990384;
//        productServiceImpl.updateProductStockSelStatCd(prdNo);
//    }

    @Test
    public void mergePdPrdTourInfo() {
        ProductVO productVO = new ProductVO();
        productVO.setPrdNo(1244990384);
        productVO.setDispCtgrNo(1018708);
        productVO.getBaseVO().setPrdTypCd(ProductConstDef.PRD_TYP_CD_NORMAL);
        productVO.setCreateNo(99);
        //productVO.setTourSidoSelectInfo("aa");
        //productVO.setSigunguSelectInfoID("bb");
        productServiceImpl.mergePdPrdTourInfo(productVO, false);
    }

    @Test
    public void insertProductServicePass() {
        PdSvcPassPrd pdSvcPassPrd = new PdSvcPassPrd();
        pdSvcPassPrd.setPrdNo(99);
        pdSvcPassPrd.setEftvBgnDy("20171001");
        pdSvcPassPrd.setEftvEndDy("20171231");
        productServiceImpl.insertProductServicePass(pdSvcPassPrd);
    }

    @Test
    public void getProductTypeInfoList() {
        HashMap hashMap = new HashMap();
        hashMap.put("memNo", 10000276);
        hashMap.put("dispCtgrNo", 1018708);
        productServiceImpl.getProductTypeInfoList(hashMap);
    }

    @Test
    public void getProductLimitInfo() {
        ProductOptLimitVO productOptLimitVO = new ProductOptLimitVO();
        productOptLimitVO.setOptLmtObjNo(1018708);
        productServiceImpl.getProductLimitInfo(productOptLimitVO);
    }

    @Test
    public void getProductNameMultiLang() {
        ProductNameMultiLangVO productNameMultiLangVO = new ProductNameMultiLangVO();
        productNameMultiLangVO.setPrdNo(1244990384);
        productServiceImpl.getProductNameMultiLang(productNameMultiLangVO);
    }

    @Test
    public void isSohoPrd() {
        long selMnbdNo = 10000276;
        long dispCtgrNo = 1018708;
        productServiceImpl.isSohoPrd(selMnbdNo, dispCtgrNo);
    }

    @Test
    public void getProductOptTypCd() {
        long prdNo = 8;
        String retStr = productServiceImpl.getProductOptTypCd(prdNo);
        Assert.assertTrue(retStr == null);
    }

    @Test
    public void getProductWghtHist() {
        long prdNo = 1244990384;
        productServiceImpl.getProductWghtHist(prdNo);
    }

    @Test
    public void getProductSelMnbdNo() {
        long prdNo = 1244990384;
        productServiceImpl.getProductSelMnbdNo(prdNo);
    }

    @Test
    public void processProductDispAreaChk() {
        ProductVO productVO = new ProductVO();
        productVO.setDispAreaDmlCd("I");
        productVO.setPrdNo(5656565656L);
        BaseVO baseVO = new BaseVO();
        baseVO.setSvcAreaCd("02");
        baseVO.setCertDownYn("N");
        baseVO.setCertType("03");
        baseVO.setAllBranchUseYn("N");
        baseVO.setTownSelLmtDy(100);
        baseVO.setShopNo(9898989898L);
        productVO.setBaseVO(baseVO);
        productVO.setSelMnbdNo(10000276);

        productServiceImpl.checkProductDispArea(productVO);

        productVO = new ProductVO();
        productVO.setDispAreaDmlCd("U");
        productVO.setPrdNo(5656565656L);
        baseVO = new BaseVO();
        baseVO.setSvcAreaCd("04");
        baseVO.setCertDownYn("N");
        baseVO.setCertType("05");
        baseVO.setAllBranchUseYn("N");
        baseVO.setTownSelLmtDy(1000);
        baseVO.setShopNo(9898989898L);
        productVO.setBaseVO(baseVO);
        productVO.setSelMnbdNo(10000276);
        productServiceImpl.checkProductDispArea(productVO);

        productVO.setDispAreaDmlCd("D");
        productServiceImpl.checkProductDispArea(productVO);
    }

    @Test
    public void getProductItemList() {
        long prdNo = 1244990384;
        productServiceImpl.getProductItemList(prdNo);
    }

    @Test
    public void getAddProductInformationListByPrdNo() {
        long prdNo = 1042850192;
        productServiceImpl.getAddProductInformationListByPrdNo(prdNo);
    }

    @Test
    public void getEventProductYn() {
        long prdNo = 1244973237;
        String retStr = productServiceImpl.getEventProductYn(prdNo);

        System.out.println("getEventProductYn= "+retStr);
    }

    @Test
    public void getRntlInfo() {
        long prdNo = 1244885445;
        productServiceImpl.getRntlInfo(prdNo);
    }

    @Test
    public void getPdPartnerPrdRt() {
        ProductVO productVO = new ProductVO();
        productVO.setPrdNo(35327683);
        productVO.setSkMemberPartnerCd("1458");
        productServiceImpl.getPdPartnerPrdRt(productVO);
    }

    @Test
    public void getNationList() {
        HashMap hashMap = new HashMap();
        hashMap.put("ntNo", 401);
        hashMap.put("ntShortNm", "GH");
        productServiceImpl.getNationList(hashMap);
    }

    @Test
    public void getProductTagList() {
        long prdNo = 1245256686;
        productServiceImpl.getProductTagList(prdNo);
    }

    @Test
    public void getApproveDt() {
        long prdNo = 1245256686;
        productServiceImpl.getApproveDt(prdNo);
    }

    @Test
    public void getProductMobileFee() {
        long prdNo = 501269435;
        productServiceImpl.getProductMobileFee(prdNo);
    }
}
