package com.elevenst.terroir.product.registration.option.validate;

import com.elevenst.terroir.product.registration.desc.entity.PdPrdDesc;
import com.elevenst.terroir.product.registration.option.code.OptionConstDef;
import com.elevenst.terroir.product.registration.option.entity.PdEventRqstCalc;
import com.elevenst.terroir.product.registration.option.entity.PdPrdOptCalc;
import com.elevenst.terroir.product.registration.option.entity.PdStock;
import com.elevenst.terroir.product.registration.option.service.OptionServiceImpl;
import com.elevenst.terroir.product.registration.option.vo.OptionVO;
import com.elevenst.terroir.product.registration.option.vo.PdOptItemVO;
import com.elevenst.terroir.product.registration.option.vo.ProductOptCalcVO;
import com.elevenst.terroir.product.registration.option.vo.ProductOptLimitVO;
import com.elevenst.terroir.product.registration.option.vo.ProductStockVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.service.ProductServiceImpl;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes={OptionValidate.class, OptionServiceImpl.class})
//@ContextConfiguration
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class OptionValidateTest {
    @Autowired
    private OptionValidate optionValidate;

    @Autowired
    private OptionServiceImpl optionServiceImpl;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Rule
    public ExpectedException expectedExcetption = ExpectedException.none();

    String prefixExceptionStr = "상품에러 : ";

    @Test
    public void checkSmartOption() {
        PdPrdDesc pdPrdDesc = new PdPrdDesc();
        pdPrdDesc.setPrdNo(1245242468);
        optionValidate.checkSmartOption(pdPrdDesc);
    }

    @Test
    public void checkSmartOption2() {
        PdPrdDesc pdPrdDesc = new PdPrdDesc();
        pdPrdDesc.setPrdNo(1244964947);
        optionValidate.checkSmartOption(pdPrdDesc);
    }

    @Test
    public void checkSmartOptionForStockImageCount() {
        optionServiceImpl.getPrdStockImageCount(1245242468);
    }

    @Test
    public void checkRestrictFirstOption() {
        ProductOptLimitVO pdPrdOptLimit = new ProductOptLimitVO();
        pdPrdOptLimit.setCombOptNmCnt(2);
        expectedExcetption.expectMessage(prefixExceptionStr+"조합형 옵션명은 "+pdPrdOptLimit.getCombOptNmCnt()+"개까지 사용 가능합니다. 현재 상태로 옵션 수정이 불가능 하니, 조합형 옵션명을 "+pdPrdOptLimit.getCombOptNmCnt()+"개 이하로 등록해주세요.");

        PdOptItemVO pdOptItem = new PdOptItemVO();
        pdOptItem.setOptClfCd(OptionConstDef.OPT_CLF_CD_MIXED);

        List<ProductStockVO> pdStockList = new ArrayList<ProductStockVO>();
        ProductStockVO pdStock1 = new ProductStockVO();
        pdStock1.setStockNo(11);
        pdStockList.add(pdStock1);
        ProductStockVO pdStock2 = new ProductStockVO();
        pdStock2.setStockNo(11);
        pdStockList.add(pdStock2);

        optionValidate.checkRestrictFirstOption(pdPrdOptLimit, pdOptItem, pdStockList, 3);
    }

    @Test
    public void checkRestrictLastOption() {
       //StringBuilder overOptItemNm, StringBuilder chkBuf, long totalRegOptSize, PdPrdOptLimit resProductOptLimitBO, boolean checkIsCustOpt, boolean checkIsIdepOpt, int custOptCnt, ProductBO productBO, String _type, Map<String, PdOptItem> optItemMap
        StringBuilder overOptItemNm = new StringBuilder();
        overOptItemNm.append("색상");
        overOptItemNm.append("사이즈");

        int totalRegOptSize = 1;

        expectedExcetption.expectMessage(prefixExceptionStr+"옵션값은 "+totalRegOptSize+"개까지 사용 가능합니다. 현재 상태로 옵션 수정이 불가능 하니, 색상사이즈 의 옵션값을 "+totalRegOptSize+"개 이하로 등록해주세요.");
        optionValidate.checkRestrictLastOption(overOptItemNm, null, totalRegOptSize, null, false, false, 0, null,"", null);
    }

    @Test
    public void checkRestrictLastOptionForCalc() {
        //StringBuilder overOptItemNm, StringBuilder chkBuf, long totalRegOptSize, PdPrdOptLimit resProductOptLimitBO, boolean checkIsCustOpt, boolean checkIsIdepOpt, int custOptCnt, ProductBO productBO, String _type, Map<String, PdOptItem> optItemMap
        ProductVO productVO = new ProductVO();
        productVO.setOptionVO(new OptionVO());
        productVO.getOptionVO().setOptTypCd(OptionConstDef.OPT_TYP_CD_CALC);
        int totalRegOptSize = 1;
        expectedExcetption.expectMessage(prefixExceptionStr+"계산형 옵션은 작성형 옵션과 함께 사용 불가능 합니다. 작성형 옵션을 사용하려면 계산형 옵션 사용에 체크 해제 해주세요.");
        optionValidate.checkRestrictLastOption(new StringBuilder(), new StringBuilder(), totalRegOptSize, null, true, false, 0, productVO,"", null);
    }

    @Test
    public void checkOptionInfo() {

        //ProductBO productBO, PdStock stockBO, PdStock prevStockBO, boolean isOnlyOneBarCode, StringBuilder asis, StringBuilder tobe
        ProductVO productBO = new ProductVO();
        productBO.getBaseVO().setDlvClf(ProductConstDef.DLV_CLF_CONSIGN_DELIVERY);

        ProductStockVO stockBO = new ProductStockVO();
        stockBO.setStckQty(1);
        ProductStockVO prevStockBO = new ProductStockVO();
        prevStockBO.setStckQty(2);

        expectedExcetption.expectMessage(prefixExceptionStr+"셀러위탁배송 상품의 재고수량은 수정이 불가능합니다.");
        optionValidate.checkOptionInfo(productBO, stockBO, prevStockBO, false, null, null);
    }

    @Test
    public void checkOptionInfoForBundle() {
        //ProductBO productBO, PdStock stockBO, PdStock prevStockBO, boolean isOnlyOneBarCode, StringBuilder asis, StringBuilder tobe
        ProductVO productBO = new ProductVO();
        productBO.getBaseVO().setSetTypCd(ProductConstDef.SetTypCd.BUNDLE.value());

        ProductStockVO stockBO = new ProductStockVO();
        stockBO.setStckQty(1);

        expectedExcetption.expectMessage(prefixExceptionStr+"묶음 상품의 재고수량은 입력이 불가능합니다.");
        optionValidate.checkOptionInfo(productBO, stockBO, null, false, null, null);
    }

    @Test
    public void checkIsNotOption() {
        Map<String, PdOptItemVO> optItemMap = new HashMap<String, PdOptItemVO>();
        optItemMap.put("aaa", new PdOptItemVO());
        expectedExcetption.expectMessage(prefixExceptionStr+"비옵션 상품에 옵션 아이템 정보가 존재 합니다. 재 등록 바랍니다.");
        optionValidate.checkIsNotOption(optItemMap, false);
    }

    @Test
    public void checkConsignNBsnDirectOption() {
        ProductVO productBO = new ProductVO();
        productBO.getBaseVO().setDlvClf(ProductConstDef.DLV_CLF_CONSIGN_DELIVERY);

        expectedExcetption.expectMessage(prefixExceptionStr+"셀러위탁배송 상품은 작성형 옵션을 적용 하실 수 없습니다.");
        optionValidate.checkConsignNBsnDirectOption(productBO, null, 0, 1);
    }

    @Test
    public void checkConsignNBsnDirectOption2() {
        ProductVO productBO = new ProductVO();
        productBO.getBaseVO().setDlvClf(ProductConstDef.DLV_CLF_CONSIGN_DELIVERY);
        productBO.setOptionSaveYn("Y");

        expectedExcetption.expectMessage(prefixExceptionStr+"옵션을 등록하면 상품의 재고번호가 변경되므로 옵션 등록이 불가합니다.");
        optionValidate.checkConsignNBsnDirectOption(productBO, new ArrayList(), 1, 0);
    }

    @Test
    public void checkCalcOption() {
        //(String optCalcItem1Nm, String optCalcItem2Nm, long optCalcItem1MinValue, long optCalcItem1MaxValue, long optCalcItem2MinValue, long optCalcItem2MaxValue, double optUnitPrc, long optSelUnit, String optUnitCd)
        String specialStr = "aa#bb";
        expectedExcetption.expectMessage(prefixExceptionStr+"계산형 옵션의 모든 항목을 입력해야 저장이 가능합니다.");
        optionValidate.checkCalcOption(specialStr, null, 0, 0, 0, 0, 0, 0, null);
    }

    @Test
    public void checkCalcOption2() {
        //(String optCalcItem1Nm, String optCalcItem2Nm, long optCalcItem1MinValue, long optCalcItem1MaxValue, long optCalcItem2MinValue, long optCalcItem2MaxValue, double optUnitPrc, long optSelUnit, String optUnitCd)
        String optCalcItem1Nm = "aa#bb";
        String optCalcItem2Nm = "aaa";
        long optCalcItem1MinValue = 100000000;
        long optCalcItem1MaxValue = 100000000;
        long optCalcItem2MinValue = 100000000;
        long optCalcItem2MaxValue = 100000000;
        long optUnitPrc = 10;
        long optSelUnit = 100;
        String optUnitCd = "01";
        expectedExcetption.expectMessage(prefixExceptionStr+"판매최소값은 1부터 1백만까지 입력 가능합니다.");
        optionValidate.checkCalcOption(optCalcItem1Nm, optCalcItem2Nm, optCalcItem1MinValue, optCalcItem1MaxValue, optCalcItem2MinValue, optCalcItem2MaxValue, optUnitPrc, optSelUnit, optUnitCd);
    }

    @Test
    public void checkCalcOption3() {
        //(String optCalcItem1Nm, String optCalcItem2Nm, long optCalcItem1MinValue, long optCalcItem1MaxValue, long optCalcItem2MinValue, long optCalcItem2MaxValue, double optUnitPrc, long optSelUnit, String optUnitCd)
        String optCalcItem1Nm = "aa#bb";
        String optCalcItem2Nm = "aaa";
        long optCalcItem1MinValue = 100000;
        long optCalcItem1MaxValue = 100000;
        long optCalcItem2MinValue = 100000;
        long optCalcItem2MaxValue = 100000;
        long optUnitPrc = 10;
        long optSelUnit = 100;
        String optUnitCd = "01";
        expectedExcetption.expectMessage(prefixExceptionStr+"계산형 옵션값에 특수 문자[',\",%,&,<,>,#,†,\\,∏,‡]는 입력할 수 없습니다.");
        optionValidate.checkCalcOption(optCalcItem1Nm, optCalcItem2Nm, optCalcItem1MinValue, optCalcItem1MaxValue, optCalcItem2MinValue, optCalcItem2MaxValue, optUnitPrc, optSelUnit, optUnitCd);
    }

    @Test
    public void checkTownInfo() {
        String prdTypCd = ProductConstDef.PRD_TYP_CD_TOWN_PROMOTE;
        expectedExcetption.expectMessage(prefixExceptionStr+"PIN번호 (0원 상품)은 옵션 등록이 불가합니다.");
        optionValidate.checkTownInfo(prdTypCd, null, null);
    }

    @Test
    public void checkTownInfo2() {
        String certTypCd = ProductConstDef.CERT_TYPE_102;
        expectedExcetption.expectMessage(prefixExceptionStr+"외부인증상품는 옵션 등록이 불가합니다.");
        optionValidate.checkTownInfo(null, certTypCd, null);
    }

    @Test
    public void checkTownInfo3() {
        String optTypCd = OptionConstDef.OPT_TYP_CD_DAY;
        expectedExcetption.expectMessage(prefixExceptionStr+"날짜형 옵션은 PIN번호 상품에만 등록 가능합니다.");
        optionValidate.checkTownInfo(null, null, optTypCd);
    }

    @Test
    public void checkSetOptionInfo() {
        //String createCd, boolean isSendMixedOpt, boolean isSendIndepOpt, String optSelectYn, String bsnDealClf, String dlvClf, boolean globalItgSeller, String certDownYn, String optTypCd, String globalSellerYn, long dispCtgr1No, String selMthdCd, String engDispYn)
        String createCd = "0203";
        String optTypCd = OptionConstDef.OPT_TYP_CD_DAY;
        long dispCtgr1No = 8286;
        expectedExcetption.expectMessage(prefixExceptionStr+"해외쇼핑 카테고리에는 날짜형 옵션을 등록 할 수 없습니다.");
        optionValidate.checkSetOptionInfo(createCd, false, "N", null, null, false, null, optTypCd, "N", dispCtgr1No, null, "N");
    }

    @Test
    public void checkSetOptionInfo2() {
        //String createCd, boolean isSendMixedOpt, boolean isSendIndepOpt, String optSelectYn, String bsnDealClf, String dlvClf, boolean globalItgSeller, String certDownYn, String optTypCd, String globalSellerYn, long dispCtgr1No, String selMthdCd, String engDispYn)
        String createCd = "0203";
        String optTypCd = OptionConstDef.OPT_TYP_CD_DAY;
        long dispCtgr1No = 8286;
        String certDownYn = "Y";
        expectedExcetption.expectMessage(prefixExceptionStr+"외부인증 상품의 경우에는 날짜형 옵션기능이 적용되지 않습니다.");
        optionValidate.checkSetOptionInfo(createCd, false, "N", null, null, false, certDownYn, optTypCd, "N", dispCtgr1No, null, "N");
    }

    @Test
    public void checkSetOptionInfo3() {
        //String createCd, boolean isSendMixedOpt, boolean isSendIndepOpt, String optSelectYn, String bsnDealClf, String dlvClf, boolean globalItgSeller, String certDownYn, String optTypCd, String globalSellerYn, long dispCtgr1No, String selMthdCd, String engDispYn)
        String createCd = "0203";
        String optTypCd = OptionConstDef.OPT_TYP_CD_DAY;
        long dispCtgr1No = 8286;
        String certDownYn = "Y";
        boolean isSendMixedOpt = true;
        expectedExcetption.expectMessage(prefixExceptionStr+"선택형 옵션 전달에 문제가 있습니다.");
        optionValidate.checkSetOptionInfo(createCd, isSendMixedOpt, "N", null, null, false, certDownYn, optTypCd, "N", dispCtgr1No, null, "N");
    }

    @Test
    public void checkMaxOptCnt() {
        long prdNo = 1042849673;
        optionValidate.checkMaxOptCnt(prdNo);
    }

    @Test
    public void getSelectOptionCnt() {
        optionServiceImpl.getSelectOptionCnt(1245242468);
    }

    @Test
    public void getOptClfCdCnt() {
        List prdNoList = new ArrayList();
        prdNoList.add(1245250565);
        prdNoList.add(1245250564);

        optionServiceImpl.getOptClfCdCnt(prdNoList);
    }

    @Test
    public void getProductStckCnt() {
        long prdNo = 1245242468;
        optionServiceImpl.getProductStckCnt(prdNo);
    }

    @Test
    public void getProductStckCnt2() {
        long prdNo = 1244973370;
        optionServiceImpl.getProductStckCnt(prdNo);
    }

    @Test
    public void getProductOptCd() {
        long prdNo = 1245242468;
        optionServiceImpl.getProductOptCd(prdNo);
    }

    @Test
    public void getProductOptCd2() {
        long prdNo = 1244973370;
        optionServiceImpl.getProductOptCd(prdNo);
    }

    @Test
    public void getOptionClfTypeCnt() {
        long prdNo = 1245242468;
        String optClfCd = "01";
        optionServiceImpl.getOptionClfTypeCnt(prdNo, optClfCd);
    }

    @Test
    public void getProductSelStatChangeStatReturn() {
        long prdNo = 1245242468;
        String selBgnDy = "20170101";
        String townExcelUser = "N";
        optionServiceImpl.getProductSelStatChangeStatReturn(prdNo, selBgnDy, townExcelUser);
    }

    @Test
    public void getProductSelStatChangeStatReturnVO() {
        long prdNo = 1244973370;
        String selBgnDy = "20170101";
        String townExcelUser = "N";

        ProductVO productVO = productServiceImpl.getProduct(prdNo);
        optionServiceImpl.getProductSelStatChangeStatReturn(productVO, selBgnDy, townExcelUser);
    }

    @Test
    public void getProductSelStatChangeStatReturnVO2() {
        long prdNo = 1244973370;
        String selBgnDy = "20190101";
        String townExcelUser = "Y";

        ProductVO productVO = productServiceImpl.getProduct(prdNo);
        String retVal = optionServiceImpl.getProductSelStatChangeStatReturn(productVO, selBgnDy, townExcelUser);
        Assert.assertEquals("102", retVal);
    }

    @Test
    public void getPdStockPtnrInfoByPrdNo() {
        ProductStockVO productStockVO = new ProductStockVO();
        productStockVO.setPrdNo(1244995215);

        optionServiceImpl.getPdStockPtnrInfoByPrdNo(productStockVO);
    }

    @Test
    public void insertProductOptCalcAll() {
        List<PdPrdOptCalc> pdPrdOptCalcList = new ArrayList<PdPrdOptCalc>();
        PdPrdOptCalc pdPrdOptCalc = new PdPrdOptCalc();
        pdPrdOptCalc.setPrdNo(1122);
        pdPrdOptCalc.setOptItemNo(1);
        pdPrdOptCalc.setOptItemNm("aa");
        pdPrdOptCalc.setOptItemMinValue(10);
        pdPrdOptCalc.setOptItemMaxValue(100);
        pdPrdOptCalc.setOptUnitPrc(100);
        pdPrdOptCalc.setOptUnitCd("01");
        pdPrdOptCalc.setOptSelUnit(1000);
        pdPrdOptCalc.setCreateNo(9);
        pdPrdOptCalc.setUpdateNo(9);
        pdPrdOptCalcList.add(pdPrdOptCalc);

        pdPrdOptCalc = new PdPrdOptCalc();
        pdPrdOptCalc.setPrdNo(1122);
        pdPrdOptCalc.setOptItemNo(2);
        pdPrdOptCalc.setOptItemNm("bb");
        pdPrdOptCalc.setOptItemMinValue(20);
        pdPrdOptCalc.setOptItemMaxValue(200);
        pdPrdOptCalc.setOptUnitPrc(200);
        pdPrdOptCalc.setOptUnitCd("02");
        pdPrdOptCalc.setOptSelUnit(2000);
        pdPrdOptCalc.setCreateNo(9);
        pdPrdOptCalc.setUpdateNo(9);
        pdPrdOptCalcList.add(pdPrdOptCalc);

        optionServiceImpl.insertProductOptCalcAll(pdPrdOptCalcList);
        deleteProductOptCalcAll(false);
    }

    public void deleteProductOptCalcAll(boolean isEvent) {

        if(isEvent) {
            List<PdEventRqstCalc> deletePdPrdOptCalcList = new ArrayList<PdEventRqstCalc>();
            PdEventRqstCalc deletePdEventRqstCalc1 = new PdEventRqstCalc();
            deletePdEventRqstCalc1.setEventNo(99);
            deletePdEventRqstCalc1.setPrdNo(1122);
            deletePdEventRqstCalc1.setOptItemNo(1);
            deletePdEventRqstCalc1.setOptItemNm("aa");
            deletePdEventRqstCalc1.setOptUnitCd("01");
            PdEventRqstCalc deletePdEventRqstCalc2= new PdEventRqstCalc();
            deletePdEventRqstCalc2.setEventNo(99);
            deletePdEventRqstCalc2.setPrdNo(1122);
            deletePdEventRqstCalc2.setOptItemNo(2);
            deletePdEventRqstCalc2.setOptItemNm("bb");
            deletePdEventRqstCalc2.setOptUnitCd("01");
            deletePdPrdOptCalcList.add(deletePdEventRqstCalc1);
            deletePdPrdOptCalcList.add(deletePdEventRqstCalc2);
            optionServiceImpl.deleteProductOptCalcForDeal(deletePdPrdOptCalcList);
        } else {
            List<PdPrdOptCalc> deletePdPrdOptCalcList = new ArrayList<PdPrdOptCalc>();
            PdPrdOptCalc deletePdPrdOptCalc1 = new PdPrdOptCalc();
            deletePdPrdOptCalc1.setPrdNo(1122);
            deletePdPrdOptCalc1.setOptItemNo(1);
            deletePdPrdOptCalc1.setOptItemNm("aa");
            deletePdPrdOptCalc1.setOptUnitCd("01");
            PdPrdOptCalc deletePdPrdOptCalc2= new PdPrdOptCalc();
            deletePdPrdOptCalc2.setPrdNo(1122);
            deletePdPrdOptCalc2.setOptItemNo(2);
            deletePdPrdOptCalc2.setOptItemNm("bb");
            deletePdPrdOptCalc2.setOptUnitCd("01");
            deletePdPrdOptCalcList.add(deletePdPrdOptCalc1);
            deletePdPrdOptCalcList.add(deletePdPrdOptCalc2);
            optionServiceImpl.deleteProductOptCalc(deletePdPrdOptCalcList);
        }
    }

    @Test
    public void applyProductOptionCalcForNormalReg() {
        long userNo = 999;
        ProductOptCalcVO productOptCalcVO = new ProductOptCalcVO();
        productOptCalcVO.setUseOptCalc("Y");
        productOptCalcVO.setEventNo(-1);
        productOptCalcVO.setPrdNo(1122);
        productOptCalcVO.setOptUnitPrc(1000);
        productOptCalcVO.setOptSelUnit(10);
        productOptCalcVO.setOptUnitCd("01");
        productOptCalcVO.setOptItem1Nm("aa");
        productOptCalcVO.setOptItem1MinValue(20);
        productOptCalcVO.setOptItem1MaxValue(200);
        productOptCalcVO.setOptItem2Nm("bb");
        productOptCalcVO.setOptItem2MinValue(30);
        productOptCalcVO.setOptItem2MaxValue(300);
        productOptCalcVO.setOptCalcTranType("reg");

        optionServiceImpl.applyProductOptionCalc(userNo, productOptCalcVO);

        try {
            Thread.sleep(1100);
        } catch(Exception e) {
        }
        applyProductOptionCalcForNormalUpd(userNo);

        deleteProductOptCalcAll(false);
    }

    public void applyProductOptionCalcForNormalUpd(long userNo) {
        ProductOptCalcVO productOptCalcVO = new ProductOptCalcVO();
        productOptCalcVO.setUseOptCalc("Y");
        productOptCalcVO.setEventNo(-1);
        productOptCalcVO.setPrdNo(1122);
        productOptCalcVO.setOptUnitPrc(2000);
        productOptCalcVO.setOptSelUnit(20);
        productOptCalcVO.setOptUnitCd("01");
        productOptCalcVO.setOptItem1Nm("aa1");
        productOptCalcVO.setOptItem1MinValue(40);
        productOptCalcVO.setOptItem1MaxValue(400);
        productOptCalcVO.setOptItem2Nm("bb1");
        productOptCalcVO.setOptItem2MinValue(60);
        productOptCalcVO.setOptItem2MaxValue(600);
        productOptCalcVO.setOptCalcTranType("upd");

        optionServiceImpl.applyProductOptionCalc(userNo, productOptCalcVO);
    }

    @Test
    public void applyProductOptionCalcForEventReg() {
        long userNo = 999;
        ProductOptCalcVO productOptCalcVO = new ProductOptCalcVO();
        productOptCalcVO.setUseOptCalc("Y");
        productOptCalcVO.setEventNo(99);
        productOptCalcVO.setPrdNo(1122);
        productOptCalcVO.setOptUnitPrc(1000);
        productOptCalcVO.setOptSelUnit(10);
        productOptCalcVO.setOptUnitCd("01");
        productOptCalcVO.setOptItem1Nm("aa");
        productOptCalcVO.setOptItem1MinValue(20);
        productOptCalcVO.setOptItem1MaxValue(200);
        productOptCalcVO.setOptItem2Nm("bb");
        productOptCalcVO.setOptItem2MinValue(30);
        productOptCalcVO.setOptItem2MaxValue(300);
        productOptCalcVO.setOptCalcTranType("reg");

        optionServiceImpl.applyProductOptionCalc(userNo, productOptCalcVO);

        applyProductOptionCalcForEventUpd(userNo);

        deleteProductOptCalcAll(true);
    }

    public void applyProductOptionCalcForEventUpd(long userNo) {
        ProductOptCalcVO productOptCalcVO = new ProductOptCalcVO();
        productOptCalcVO.setUseOptCalc("Y");
        productOptCalcVO.setPrdNo(1122);
        productOptCalcVO.setEventNo(99);
        productOptCalcVO.setOptUnitPrc(90);
        productOptCalcVO.setOptUnitPrc(2000);
        productOptCalcVO.setOptSelUnit(20);
        productOptCalcVO.setOptUnitCd("011");
        productOptCalcVO.setOptItem1Nm("aa1");
        productOptCalcVO.setOptItem1MinValue(40);
        productOptCalcVO.setOptItem1MaxValue(400);
        productOptCalcVO.setOptItem2Nm("bb1");
        productOptCalcVO.setOptItem2MinValue(60);
        productOptCalcVO.setOptItem2MaxValue(600);
        productOptCalcVO.setOptCalcTranType("upd");
        optionServiceImpl.applyProductOptionCalc(userNo, productOptCalcVO);
    }

    @Test
    public void updateProductOptionPurchaseInfo() {
        PdStock pdStock = new PdStock();
        pdStock.setPrdNo(1122);
        pdStock.setPuchPrc(1);
        pdStock.setMrgnRt(2);
        pdStock.setMrgnAmt(3);
        pdStock.setUpdateNo(1000);

        optionServiceImpl.updateProductOptionPurchaseInfo(pdStock);

        pdStock.setPrdNo(1122);
        pdStock.setPuchPrc(0);
        pdStock.setMrgnRt(0);
        pdStock.setMrgnAmt(0);
        pdStock.setUpdateNo(0);

        optionServiceImpl.updateProductOptionPurchaseInfo(pdStock);
    }
}

