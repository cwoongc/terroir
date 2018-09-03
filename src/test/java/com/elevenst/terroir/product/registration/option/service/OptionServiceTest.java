package com.elevenst.terroir.product.registration.option.service;

import com.elevenst.terroir.product.registration.catalog.vo.CtlgVO;
import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.option.code.OptionConstDef;
import com.elevenst.terroir.product.registration.option.entity.PdOptItem;
import com.elevenst.terroir.product.registration.option.vo.OptionVO;
import com.elevenst.terroir.product.registration.option.vo.ProductStockVO;
import com.elevenst.terroir.product.registration.option.vo.StandardOptionGroupMappingVO;
import com.elevenst.terroir.product.registration.price.vo.PriceVO;
import com.elevenst.terroir.product.registration.product.code.CreateCdTypes;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.vo.BaseVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.vo.SellerVO;
import com.google.gson.Gson;
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

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OptionServiceTest {

    @Autowired
    OptionServiceImpl optionServiceImpl;

    long selMnbdNo = 10000276; // 11349581

    @Test
    public void convertVoToVo() {
        ProductVO productVO = new ProductVO();
        productVO.setPrdNo(1);
        productVO.setUpdateNo(2);

        PdOptItem pdOptItem = new PdOptItem();
        BeanUtils.copyProperties(productVO, pdOptItem);

        System.out.println("11= " + pdOptItem.getPrdNo() + " : " + pdOptItem.getOptItemNm() + " : " + pdOptItem.getUpdateNo());
    }

    @Test
    public void getProduct4OptionPop() {
        ProductVO productVO = new ProductVO();
        productVO.setBaseVO(new BaseVO());
        productVO.getBaseVO().setPrdInfoType("EVENT");
        productVO.setPriceVO(new PriceVO());
        productVO.getPriceVO().setEventNo(11);
        productVO.getBaseVO().setBsnDealClf(ProductConstDef.BSN_DEAL_CLF_COMMISSION);
        productVO.setPrdNo(1244662904);
        //productVO.setPrdInfoType("ROLLBACK");
        optionServiceImpl.getProduct4OptionPop(productVO);
    }

    public ProductVO setProductVO(ProductVO productVO, long selMnbdNo, String bsnDealClf, String tranType) {
        if(productVO == null) {
            productVO = new ProductVO();
        }
        //ProductVO productVO = new ProductVO();
        //setOptionData()
        //productVO.setPrdNo(1);
        productVO.setDispCtgrNo(1008870);
        productVO.setSelMnbdNo(selMnbdNo);
        productVO.setUpdateNo(selMnbdNo);

        BaseVO baseVO = new BaseVO();
        baseVO.setPrdTypCd(ProductConstDef.PRD_TYP_CD_NORMAL);
        baseVO.setPrdStatCd(ProductConstDef.PRD_SEL_STAT_CD_SALE);
        baseVO.setBsnDealClf(bsnDealClf);
        baseVO.setDispCtgr1NoDe(1001373);
        baseVO.setDispCtgr2NoDe(1002289);
        baseVO.setEngDispYn("N");
        baseVO.setSelMthdCd("01");
        baseVO.setDlvClf("01");
        baseVO.setSelStatCd("01");
        baseVO.setCreateCd(CreateCdTypes.MO_SIMPLEREG.getDtlsCd());


        OptionVO optionVO = null;
        if(productVO.getOptionVO() == null) {
            optionVO = new OptionVO();
            //옵션명
            String[] colTitle = new String[1];
            colTitle[0] = "색상†ㅁㅁㅁ";
            optionVO.setColTitle(colTitle);

            //재고수량
            String[] colCount = new String[1];
            colCount[0] = "11†12†13†14";
            //optionVO.setColCount(colCount);

            //옵션가격
            String[] colOptPrice = new String[1];
            colOptPrice[0] = "100†200†300†0";
            //optionVO.setColOptPrice(colOptPrice);

            //옵션상태
            String[] prdStckStatCd = new String[1];
            prdStckStatCd[0] = "01†01†02†01";
            //optionVO.setPrdStckStatCds(prdStckStatCd);

            //옵션무게
            String[] colOptWght = new String[1];
            colOptWght[0] = "10†20†30†40";
            //optionVO.setColOptWght(colOptWght);

            //지정일배송코드
            String[] colSellerStockCd = new String[1];
            colSellerStockCd[0] = "A†B†C†D";
            //optionVO.setColSellerStockCd(colSellerStockCd);

            //조합된 옵션값 (2x2 일 경우 1단 :빨간색,노란색 이라면 총4개 출력 빨간색†빨간색†노란색†노란색
            String[] colValue1 = new String[1];
            colValue1[0] = "빨간색†빨간색†노란색†노란색";
            //optionVO.setColValue1(colValue1);

            //조합된 옵션값 (2x2 일 경우 2단 :a,b 이라면 총4개 출력 a†b†a†b
            String[] colValue2 = new String[1];
            colValue2[0] = "a†b†a†b";
            //optionVO.setColValue2(colValue2);

            //작성협옵션 사용여부
            optionVO.setOptInputYn("Y");
            //작성형옵션 명
            String[] colOptName = new String[1];
            colOptName[0] = "작성형1†작성형2";
            //optionVO.setColOptName(colOptName);

            //작성형옵션 상태값
            String[] colOptStatCd = new String[1];
            colOptStatCd[0] = "01†01";
            //optionVO.setColOptStatCd(colOptStatCd);

            //barcode
            String[] colBarCode = new String[1];
            colBarCode[0] = "1†2†3†4";
            //optionVO.setColBarCode(colBarCode);

            if("U".equals(tranType)) {
                productVO.setPrdNo(1);
                colCount[0] = "111†121†131†141";
                colOptPrice[0] = "100†2000†300†0";
                prdStckStatCd[0] = "01†02†02†01";
                colOptWght[0] = "100†20†30†400";
                colSellerStockCd[0] = "A†B1†C1†D";
                colValue1[0] = "빨간색†빨간색†노란색1†노란색1";
                colValue2[0] = "a†b1†a1†b";
                colOptName[0] = "작성형11†작성형22";
                colOptStatCd[0] = "01†02";
                colBarCode[0] = "1†2a†3†4b";
            }
            optionVO.setColCount(colCount);
            optionVO.setColOptPrice(colOptPrice);
            optionVO.setPrdStckStatCds(prdStckStatCd);
            optionVO.setColOptWght(colOptWght);
            optionVO.setColSellerStockCd(colSellerStockCd);
            optionVO.setColValue1(colValue1);
            optionVO.setColValue2(colValue2);
            optionVO.setColOptName(colOptName);
            optionVO.setColOptStatCd(colOptStatCd);
            optionVO.setColBarCode(colBarCode);
        } else {
            optionVO = productVO.getOptionVO();
        }
        optionVO.setOptClfCd(OptionConstDef.OPT_CLF_CD_MIXED);
        optionVO.setOptSelectYn("Y");






        //계산형, 날짜형 옵션 (단일상품에는 포함안됨)


        PriceVO priceVO = new PriceVO();
        priceVO.setSelPrc(30000);


        MemberVO memberVO = new MemberVO();
        memberVO.setGlobalSellerYn("N");

        CategoryVO categoryVO = new CategoryVO();


        SellerVO sellerVO = new SellerVO();


        CtlgVO ctlgVO = new CtlgVO();


        productVO.setBaseVO(baseVO);
        productVO.setPriceVO(priceVO);
        productVO.setOptionVO(optionVO);
        productVO.setMemberVO(memberVO);
        productVO.setOptionVO(optionVO);
        productVO.setCategoryVO(categoryVO);
        productVO.setSellerVO(sellerVO);
        productVO.setCtlgVO(ctlgVO);


        return productVO;
    }

    @Test
    public void insertProductOption() {
        //조합형 + 작성형 옵션 등록 테스트

        //옵션 전체 등록
        //1. insertPdOptItem
        //1-1. set ProductVO
        ProductVO productVO = setProductVO(null, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_COMMISSION, "I");
        productVO = optionServiceImpl.setProductInformationOptionVO(productVO);

        //테스트를 위한 prdNo설정
        productVO.setPrdNo(1);
        productVO.getOptionVO().setPrdSelQty(100);

        //2. insertPdStock
        //System.out.println(productVO);
        optionServiceImpl.insertProductOption(productVO);
    }

    @Test
    public void updateProductOption() {
        ProductVO productVO = setProductVO(null, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_COMMISSION, "U");
        productVO = optionServiceImpl.setProductInformationOptionVO(productVO);

        optionServiceImpl.applyProductOption(productVO);
    }

    @Test
    public void copyProductOption() {
        ProductVO productVO = setProductVO(null, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_COMMISSION, "I");
        productVO.setPrdNo(1);
        productVO = optionServiceImpl.setProductInformationOptionVO(productVO);

        productVO.setPrdNo(3);  // 신규 생성된 상품번호 (테스트를 위해 임시로 생성되었다고 가정함)
        productVO.setPrdCopyYn("Y");
        productVO.setOptChangeYn("N");
        productVO.getBaseVO().setReRegPrdNo(1); // 복사대상 상품번호
        productVO.getBaseVO().setCertTypCd("");

        optionServiceImpl.insertProductOption(productVO);
    }

    public ProductVO setProductPurchaseVO(ProductVO productVO, long selMnbdNo, String bsnDealClf, String tranType) {
        if(productVO == null) {
            productVO = new ProductVO();
        }
        //ProductVO productVO = new ProductVO();
        //setOptionData()
        //productVO.setPrdNo(1);
        productVO.setDispCtgrNo(1008870);
        productVO.setSelMnbdNo(selMnbdNo);
        productVO.setUpdateNo(selMnbdNo);

        BaseVO baseVO = new BaseVO();
        baseVO.setPrdTypCd(ProductConstDef.PRD_TYP_CD_NORMAL);
        baseVO.setPrdStatCd(ProductConstDef.PRD_SEL_STAT_CD_SALE);
        baseVO.setBsnDealClf(bsnDealClf);
        baseVO.setDispCtgr1NoDe(1001373);
        baseVO.setDispCtgr2NoDe(1002289);
        baseVO.setEngDispYn("N");
        baseVO.setSelMthdCd("01");
        baseVO.setDlvClf("01");
        baseVO.setSelStatCd("01");
        baseVO.setPrdTypCd("01");
        baseVO.setCreateCd(CreateCdTypes.MO_SIMPLEREG.getDtlsCd());


        OptionVO optionVO = null;
        if(productVO.getOptionVO() == null) {
            optionVO = new OptionVO();
            //옵션명
            String[] colTitle = new String[1];
            colTitle[0] = "색상†ㅁㅁㅁ";

            //재고수량
            String[] colCount = new String[1];
            colCount[0] = "11†12†13†14"; // tobe재고수량 (그리드의 재고수량)

            String[] oriColCount = new String[1];
            oriColCount[0] = "11†12†13†14"; // asis재고수량


            //옵션가격
            String[] colOptPrice = new String[1];
            colOptPrice[0] = "0†0†0†0";


            //옵션상태
            String[] prdStckStatCd = new String[1];
            prdStckStatCd[0] = "01†01†02†01";


            //옵션무게
            String[] colOptWght = new String[1];
            colOptWght[0] = "10†20†30†40";


            //지정일배송코드
            String[] colSellerStockCd = new String[1];
            colSellerStockCd[0] = "A†B†C†D";


            //조합된 옵션값 (2x2 일 경우 1단 :빨간색,노란색 이라면 총4개 출력 빨간색†빨간색†노란색†노란색
            String[] colValue1 = new String[1];
            colValue1[0] = "빨간색†빨간색†노란색†노란색";


            //조합된 옵션값 (2x2 일 경우 2단 :a,b 이라면 총4개 출력 a†b†a†b
            String[] colValue2 = new String[1];
            colValue2[0] = "a†b†a†b";


            //작성협옵션 사용여부
            optionVO.setOptInputYn("Y");
            //작성형옵션 명
            String[] colOptName = new String[1];
            colOptName[0] = "작성형1†작성형2";


            //작성형옵션 상태값
            String[] colOptStatCd = new String[1];
            colOptStatCd[0] = "01†01";



            //계산형, 날짜형 옵션 (단일상품에는 포함안됨)


            //매입옵션 관련 테스트 필요함
            //매입가
            String[] colPuchPrc = new String[1];
            colPuchPrc[0] = "14000†14000†14000†14000";


            //마진가
            String[] colMrgnAmt = new String[1];
            colMrgnAmt[0] = "6000†6000†6000†6000";


            //마진율
            String[] colMrgnRt = new String[1];
            colMrgnRt[0] = "30.0†30.0†30.0†30.0";


            //가격승인상태
            String[] colAprvStatCd = new String[1];
            colAprvStatCd[0] = "01†01†01†01";


            //barcode
            String[] colBarCode = new String[1];
            colBarCode[0] = "1†2†3†4";


            if("U".equals(tranType)) {
                productVO.setPrdNo(2);
                colOptWght[0] = "100†200†300†400";
            }

            optionVO.setColTitle(colTitle);
            optionVO.setColCount(colCount);
            optionVO.setColOptStatCd(colOptStatCd);
            optionVO.setColOptName(colOptName);
            optionVO.setColValue2(colValue2);
            optionVO.setColValue1(colValue1);
            optionVO.setColSellerStockCd(colSellerStockCd);
            optionVO.setColOptWght(colOptWght);
            optionVO.setPrdStckStatCds(prdStckStatCd);
            optionVO.setColOptPrice(colOptPrice);
            optionVO.setOriColCount(oriColCount);
            optionVO.setColBarCode(colBarCode);
            optionVO.setColAprvStatCd(colAprvStatCd);
            optionVO.setColMrgnRt(colMrgnRt);
            optionVO.setColMrgnAmt(colMrgnAmt);
            optionVO.setColPuchPrc(colPuchPrc);
        } else {
            optionVO = productVO.getOptionVO();
        }
        optionVO.setOptClfCd(OptionConstDef.OPT_CLF_CD_MIXED);
        optionVO.setOptSelectYn("Y");
        optionVO.setMrgnPolicyCd(ProductConstDef.MRGN_POLICY_CD_WON);



        PriceVO priceVO = new PriceVO();
        priceVO.setSelPrc(20000);


        MemberVO memberVO = new MemberVO();
        memberVO.setGlobalSellerYn("N");

        CategoryVO categoryVO = new CategoryVO();


        SellerVO sellerVO = new SellerVO();


        CtlgVO ctlgVO = new CtlgVO();


        productVO.setBaseVO(baseVO);
        productVO.setPriceVO(priceVO);
        productVO.setOptionVO(optionVO);
        productVO.setMemberVO(memberVO);
        productVO.setOptionVO(optionVO);
        productVO.setCategoryVO(categoryVO);
        productVO.setSellerVO(sellerVO);
        productVO.setCtlgVO(ctlgVO);


        return productVO;
    }

    @Test
    public void insertProductPurchaseOption() {
        //조합형 + 작성형 옵션 등록 테스트
        long selMnbdNo = 10000330;
        //옵션 전체 등록
        //1. insertPdOptItem
        //1-1. set ProductVO
        ProductVO productVO = setProductPurchaseVO(null, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE, "I");
        productVO = optionServiceImpl.setProductInformationOptionVO(productVO);

        //테스트를 위한 prdNo설정
        productVO.setPrdNo(2);
        productVO.getOptionVO().setPrdSelQty(100);

        //2. insertPdStock
        //System.out.println(productVO);
        optionServiceImpl.insertProductOption(productVO);
    }

    @Test
    public void updateProductPurchaseOption() {
        ProductVO productVO = setProductPurchaseVO(null, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE, "U");
        productVO = optionServiceImpl.setProductInformationOptionVO(productVO);

        optionServiceImpl.applyProductOption(productVO);
    }

    @Test
    public void copyProductPurchaseOption() {
        long selMnbdNo = 10000330;
        ProductVO productVO = setProductPurchaseVO(null, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE, "I");
        productVO.setPrdNo(2);
        productVO = optionServiceImpl.setProductInformationOptionVO(productVO);

        productVO.setPrdNo(4);  // 신규 생성된 상품번호 (테스트를 위해 임시로 생성되었다고 가정함)
        productVO.setPrdCopyYn("Y");
        productVO.setOptChangeYn("N");
        productVO.getBaseVO().setReRegPrdNo(2); // 복사대상 상품번호
        productVO.getBaseVO().setCertTypCd("");

        optionServiceImpl.insertProductOption(productVO);
    }










    public String setJsonData(String sellerType, String transactionType) {
        String retStr = "";

        if(ProductConstDef.BSN_DEAL_CLF_COMMISSION.equals(sellerType)) {
            if("N".equals(transactionType)) {
                retStr = "{\t\n" +
                    "\t\"optionVO\": {\n" +
                    "\t\t\"optClfCd\":\"01\",\n" +
                    "\t\t\"optSelectYn\":\"Y\"\n" +
                    "\t\t\"prdExposeClfCd\":\"01\"\n" +
                    "\t}\n" +
                    "}";
            }
            //일반셀러 등록
            else if("I".equals(transactionType)) {
                retStr = "{\t\n" +
                    "\t\"optionVO\": {\n" +
                    "\t\t\"optClfCd\":\"01\",\n" +
                    "\t\t\"optSelectYn\":\"Y\",\n" +
                    "\t\t\"optionSelectList\": [\n" +
                    "\t\t\t{\t\"name\":\"색상\", \"optionValueList\": [\t\"빨간색\", \"노란색\" ]\t},\n" +
                    "\t\t\t{\t\"name\":\"사이즈\", \"optionValueList\": [\t\"L\", \"XL\" ]\t}\n" +
                    "\t\t],\n" +
                    "\t\t\"optInputYn\":\"Y\",\n" +
                    "\t\t\"optionWriteVO\": {\n" +
                    "\t\t\t\"colOptName\": [ \"번호입력1\", \"번호입력2\" ], \n" +
                    "\t\t\t\"colOptStatCd\": [ \"01\", \"02\" ]\n" +
                    "\t\t},\n" +
                    "\t\t\"optionMixVO\": {\t\n" +
                    "\t\t\t\t\"colValue1\": [ \"빨간\", \"빨간\", \"노란\", \"노란\" ],\n" +
                    "\t\t\t\t\"colValue2\": [ \"L\", \"XL\", \"L\", \"XL\" ], \n" +
                    "\t\t\t\t\"colCount\": [ \"10\", \"10\", \"10\", \"10\" ], \n" +
                    "\t\t\t\t\"colOptPrice\": [ \"0\", \"1000\", \"0\", \"1000\" ], \n" +
                    "\t\t\t\t\"prdStckStatCd\": [ \"01\", \"01\", \"01\", \"01\" ], \n" +
                    "\t\t\t\t\"colCstmAplPrc\": [ \"\", \"\", \"\", \"\" ],\n" +
                    "\t\t\t\t\"colOptWght\": [ \"100\", \"200\", \"100\", \"200\" ], \n" +
                    "\t\t\t\t\"colSellerStockCd\": [ \"A812\", \"A813\", \"A814\", \"A815\" ], \n" +
                    "\t\t\t\t\"barCode\": [ \"A\", \"B\", \"C\", \"D\" ], \n" +
                    "\t\t\t\t\"colSetTypCd\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\t\"colCompInfoJson\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\t\"colCtlgNo\": [ \"1043\", \"1043\", \"1043\", \"1043\" ], \n" +
                    "\t\t\t\t\"colAddDesc\": [ \"11\", \"12\", \"13\", \"14\" ], \n" +
                    "\t\t\t\t\"colSpplBnftCd\": [ \"01\", \"01\", \"01\", \"01\" ], \n" +
                    "\t\t\t\t\"groupNumber\": [ \"01\", \"01\", \"02\", \"02\" ], \n" +
                    "\t\t\t\t\"summaryImage\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\t\"summaryImageStr\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\t\"detailImage\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\t\"detailImageStr\": [ \"\", \"\", \"\", \"\" ]\t\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}";
            } else {
                // 일반셀러 수정
                retStr = "{\t\n" +
                    "\t\"optionVO\": {\n" +
                    "\t\t\"optClfCd\":\"01\",\n" +
                    "\t\t\"optSelectYn\":\"Y\",\n" +
                    "\t\t\"optionSelectList\": [\n" +
                    "\t\t\t{\t\"name\":\"색상\", \"optionValueList\": [\t\"빨간색\", \"노란색\" ]\t},\n" +
                    "\t\t\t{\t\"name\":\"사이즈\", \"optionValueList\": [\t\"L\", \"XL\" ]\t}\n" +
                    "\t\t],\n" +
                    "\t\t\"optInputYn\":\"Y\",\n" +
                    "\t\t\"optionWriteVO\": {\n" +
                    "\t\t\t\"colOptName\": [ \"번호입력11\", \"번호입력22\" ], \n" +
                    "\t\t\t\"colOptStatCd\": [ \"01\", \"02\" ]\n" +
                    "\t\t},\n" +
                    "\t\t\"optionMixVO\": {\t\n" +
                    "\t\t\t\t\"colValue1\": [ \"빨간\", \"빨간1A\", \"노란\", \"노란2\" ],\n" +
                    "\t\t\t\t\"colValue2\": [ \"L\", \"XL\", \"L\", \"XL\" ], \n" +
                    "\t\t\t\t\"colCount\": [ \"10\", \"20\", \"10\", \"10\" ], \n" +
                    "\t\t\t\t\"colOptPrice\": [ \"0\", \"200\", \"0\", \"100\" ], \n" +
                    "\t\t\t\t\"prdStckStatCd\": [ \"01\", \"02\", \"01\", \"01\" ], \n" +
                    "\t\t\t\t\"colCstmAplPrc\": [ \"\", \"\", \"\", \"\" ],\n" +
                    "\t\t\t\t\"colOptWght\": [ \"100\", \"310\", \"100\", \"200\" ], \n" +
                    "\t\t\t\t\"colSellerStockCd\": [ \"A819\", \"A813\", \"A814\", \"A815\" ], \n" +
                    "\t\t\t\t\"barCode\": [ \"A\", \"B\", \"CC\", \"D\" ], \n" +
                    "\t\t\t\t\"colSetTypCd\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\t\"colCompInfoJson\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\t\"colCtlgNo\": [ \"1058\", \"1058\", \"1058\", \"1058\" ], \n" +
                    "\t\t\t\t\"colAddDesc\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\t\"colSpplBnftCd\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\t\"groupNumber\": [ \"01\", \"01\", \"02\", \"01\" ], \n" +
                    "\t\t\t\t\"summaryImage\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\t\"summaryImageStr\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\t\"detailImage\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\t\"detailImageStr\": [ \"\", \"\", \"\", \"\" ]\t\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}";
            }
        } else {
            if("I".equals(transactionType)) {
                retStr = "{\n" +
                    "\t\"optionVO\": {\n" +
                    "\t\t\"optClfCd\":\"01\",\n" +
                    "\t\t\"optSelectYn\":\"Y\",\n" +
                    "\t\t\"mrgnPolicyCd\":\"01\",\n" +
                    "\t\t\"optionSelectList\": [\n" +
                    "\t\t\t{\t\"name\":\"색상\", \"optionValueList\": [\t\"빨간색\", \"노란색\" ]\t},\n" +
                    "\t\t\t{\t\"name\":\"사이즈\", \"optionValueList\": [\t\"L\", \"XL\" ]\t}\n" +
                    "\t\t],\n" +
                    "\t\t\"optInputYn\":\"Y\",\n" +
                    "\t\t\"optionWriteVO\": {\n" +
                    "\t\t\t\"colOptName\": [ \"번호입력1\", \"번호입력2\" ],  \n" +
                    "\t\t\t\"colOptStatCd\": [ \"01\", \"02\" ]\n" +
                    "\t\t},\n" +
                    "\t\t\"optionMixVO\": {\t\n" +
                    "\t\t\t\"colValue1\": [ \"빨간\", \"빨간\", \"노란\", \"노란\" ], \n" +
                    "\t\t\t\"colValue2\": [ \"L\", \"XL\", \"L\", \"XL\" ], \n" +
                    "\t\t\t\"colCount\": [ \"10\", \"10\", \"10\", \"10\" ], \n" +
                    "\t\t\t\"oriColCount\": [ \"10\", \"10\", \"10\", \"10\" ], \n" +
                    "\t\t\t\"colOptPrice\": [ \"0\", \"0\", \"0\", \"0\" ], \n" +
                    "\t\t\t\"prdStckStatCd\": [ \"01\", \"01\", \"01\", \"01\" ], \n" +
                    "\t\t\t\"colCstmAplPrc\": [ \"\", \"\", \"\", \"\" ],\n" +
                    "\t\t\t\"colOptWght\": [ \"100\", \"200\", \"100\", \"200\" ], \n" +
                    "\t\t\t\"colSellerStockCd\": [ \"A812\", \"A813\", \"A814\", \"A815\" ], \n" +
                    "\t\t\t\"barCode\": [ \"A\", \"B\", \"C\", \"D\" ], \n" +
                    "\t\t\t\"colPuchPrc\": [ \"14000\", \"14000\", \"14000\", \"14000\" ], \n" +
                    "\t\t\t\"colMrgnAmt\": [ \"6000\", \"6000\", \"6000\", \"6000\" ], \n" +
                    "\t\t\t\"colMrgnRt\": [ \"30.0\", \"30.0\", \"30.0\", \"30.0\" ], \n" +
                    "\t\t\t\"colAprvStatCd\": [ \"01\", \"01\", \"01\", \"01\" ], \n" +
                    "\t\t\t\"colSetTypCd\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\"colCompInfoJson\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\"colCtlgNo\": [ \"1043\", \"1043\", \"1043\", \"1043\" ], \n" +
                    "\t\t\t\"colAddDesc\": [ \"A1\", \"A2\", \"A3\", \"A4\" ], \n" +
                    "\t\t\t\"colSpplBnftCd\": [ \"01\", \"01\", \"01\", \"01\" ], \n" +
                    "\t\t\t\"groupNumber\": [ \"01\", \"01\", \"02\", \"02\" ], \n" +
                    "\t\t\t\"summaryImage\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\"summaryImageStr\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\"detailImage\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\"detailImageStr\": [ \"\", \"\", \"\", \"\" ]\t\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}";
            } else {
                retStr = "{\n" +
                    "\t\"optionVO\": {\n" +
                    "\t\t\"optClfCd\":\"01\",\n" +
                    "\t\t\"optSelectYn\":\"Y\",\n" +
                    "\t\t\"mrgnPolicyCd\":\"01\",\n" +
                    "\t\t\"optionSelectList\": [\n" +
                    "\t\t\t{\t\"name\":\"색상\", \"optionValueList\": [\t\"빨간색\", \"노란색\" ]\t},\n" +
                    "\t\t\t{\t\"name\":\"사이즈\", \"optionValueList\": [\t\"L\", \"XL\" ]\t}\n" +
                    "\t\t],\n" +
                    "\t\t\"optInputYn\":\"Y\",\n" +
                    "\t\t\"optionWriteVO\": {\n" +
                    "\t\t\t\"colOptName\": [ \"번호입력aa\", \"번호입력bb\" ],  \n" +
                    "\t\t\t\"colOptStatCd\": [ \"01\", \"02\" ]\n" +
                    "\t\t},\n" +
                    "\t\t\"optionMixVO\": {\t\n" +
                    "\t\t\t\"colValue1\": [ \"빨간\", \"빨간\", \"노란\", \"노란\" ], \n" +
                    "\t\t\t\"colValue2\": [ \"L\", \"XL\", \"L\", \"XL\" ], \n" +
                    "\t\t\t\"colCount\": [ \"10\", \"10\", \"10\", \"10\" ], \n" +
                    "\t\t\t\"oriColCount\": [ \"10\", \"10\", \"10\", \"10\" ], \n" +
                    "\t\t\t\"colOptPrice\": [ \"0\", \"0\", \"0\", \"0\" ], \n" +
                    "\t\t\t\"prdStckStatCd\": [ \"01\", \"01\", \"01\", \"01\" ], \n" +
                    "\t\t\t\"colCstmAplPrc\": [ \"\", \"\", \"\", \"\" ],\n" +
                    "\t\t\t\"colOptWght\": [ \"1000\", \"2000\", \"1000\", \"2000\" ], \n" +
                    "\t\t\t\"colSellerStockCd\": [ \"A812\", \"A813\", \"A814\", \"A815\" ], \n" +
                    "\t\t\t\"barCode\": [ \"A\", \"B\", \"C\", \"D\" ], \n" +
                    "\t\t\t\"colPuchPrc\": [ \"14000\", \"14000\", \"14000\", \"14000\" ], \n" +
                    "\t\t\t\"colMrgnAmt\": [ \"6000\", \"6000\", \"6000\", \"6000\" ], \n" +
                    "\t\t\t\"colMrgnRt\": [ \"30.0\", \"30.0\", \"30.0\", \"30.0\" ], \n" +
                    "\t\t\t\"colAprvStatCd\": [ \"01\", \"01\", \"01\", \"01\" ], \n" +
                    "\t\t\t\"colSetTypCd\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\"colCompInfoJson\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\"colCtlgNo\": [ \"1058\", \"1058\", \"1058\", \"1058\" ], \n" +
                    "\t\t\t\"colAddDesc\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\"colSpplBnftCd\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\"groupNumber\": [ \"01\", \"01\", \"02\", \"02\" ], \n" +
                    "\t\t\t\"summaryImage\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\"summaryImageStr\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\"detailImage\": [ \"\", \"\", \"\", \"\" ], \n" +
                    "\t\t\t\"detailImageStr\": [ \"\", \"\", \"\", \"\" ]\t\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}";
            }
        }
        return retStr;
    }


    @Test
    public void insertProductOptionNew() {
        //조합형 + 작성형 옵션 등록 테스트
        //옵션 전체 등록
        ProductVO productVO = new ProductVO();
        Gson gson = new Gson();
        productVO = gson.fromJson(setJsonData(ProductConstDef.BSN_DEAL_CLF_COMMISSION, "I"), ProductVO.class);
        productVO = setProductVO(productVO, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_COMMISSION, "I");
        productVO = optionServiceImpl.setProductInformationOptionVONew(productVO);

        //테스트를 위한 prdNo설정
        productVO.setPrdNo(1);
        productVO.getOptionVO().setPrdSelQty(100);

        //2. insertPdStock
        //System.out.println(productVO);
        optionServiceImpl.insertProductOption(productVO);
    }

    @Test
    public void insertProductNoOptionNew() {
        //조합형 + 작성형 옵션 등록 테스트
        //옵션 전체 등록
        ProductVO productVO = new ProductVO();
        //Gson gson = new Gson();
        //productVO = gson.fromJson(setJsonData(ProductConstDef.BSN_DEAL_CLF_COMMISSION, "N"), ProductVO.class);
        productVO = setProductVO(productVO, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_COMMISSION, "N");
        OptionVO optionVO = new OptionVO();
        optionVO.setOptClfCd("");
        optionVO.setOptSelectYn("");
        productVO.setOptionVO(optionVO);
        productVO = optionServiceImpl.setProductInformationOptionVONew(productVO);

        //테스트를 위한 prdNo설정
        productVO.setPrdNo(8);
        productVO.getOptionVO().setPrdSelQty(100);

        //2. insertPdStock
        //System.out.println(productVO);
        optionServiceImpl.insertProductOption(productVO);
    }

    @Test
    public void updateProductNoOptionNew() {
        //조합형 + 작성형 옵션 등록 테스트
        //옵션 전체 등록
        ProductVO productVO = new ProductVO();
        //Gson gson = new Gson();
        //productVO = gson.fromJson(setJsonData(ProductConstDef.BSN_DEAL_CLF_COMMISSION, "N"), ProductVO.class);
        productVO = setProductVO(productVO, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_COMMISSION, "N");
        OptionVO optionVO = new OptionVO();
        optionVO.setOptClfCd("");
        optionVO.setOptSelectYn("");
        productVO.setOptionVO(optionVO);
        productVO = optionServiceImpl.setProductInformationOptionVONew(productVO);

        //테스트를 위한 prdNo설정
        productVO.setPrdNo(8);
        productVO.getOptionVO().setPrdSelQty(300);

        //2. insertPdStock
        //System.out.println(productVO);
        optionServiceImpl.insertProductOption(productVO);
    }

    @Test
    public void updateProductOptionNew() {
        //조합형 + 작성형 옵션 등록 테스트
        //옵션 전체 등록
        ProductVO productVO = new ProductVO();
        Gson gson = new Gson();
        productVO = gson.fromJson(setJsonData(ProductConstDef.BSN_DEAL_CLF_COMMISSION, "U"), ProductVO.class);
        productVO = setProductVO(productVO, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_COMMISSION, "U");
        productVO = optionServiceImpl.setProductInformationOptionVONew(productVO);

        //테스트를 위한 prdNo설정
        productVO.setPrdNo(1);
        productVO.getOptionVO().setPrdSelQty(100);

        //2. insertPdStock
        //System.out.println(productVO);
        optionServiceImpl.insertProductOption(productVO);
    }

    @Test
    public void insertProductMartOptionNew() {
        //조합형 + 작성형 옵션 등록 테스트
        //옵션 전체 등록
        ProductVO productVO = new ProductVO();
        Gson gson = new Gson();
        productVO = gson.fromJson(setJsonData(ProductConstDef.BSN_DEAL_CLF_COMMISSION, "I"), ProductVO.class);
        productVO = setProductVO(productVO, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_COMMISSION, "I");
        productVO.setMartPrdYn("Y");
        productVO = optionServiceImpl.setProductInformationOptionVONew(productVO);

        //테스트를 위한 prdNo설정
        productVO.setPrdNo(1);
        productVO.getOptionVO().setPrdSelQty(100);

        //2. insertPdStock
        //System.out.println(productVO);
        optionServiceImpl.insertProductOption(productVO);
    }

    @Test
    public void updateProductMartOptionNew() {
        //조합형 + 작성형 옵션 등록 테스트
        //옵션 전체 등록
        ProductVO productVO = new ProductVO();
        Gson gson = new Gson();
        productVO = gson.fromJson(setJsonData(ProductConstDef.BSN_DEAL_CLF_COMMISSION, "U"), ProductVO.class);
        productVO = setProductVO(productVO, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_COMMISSION, "U");
        productVO.setMartPrdYn("Y");
        productVO = optionServiceImpl.setProductInformationOptionVONew(productVO);

        //테스트를 위한 prdNo설정
        productVO.setPrdNo(1);
        productVO.getOptionVO().setPrdSelQty(100);

        //2. insertPdStock
        //System.out.println(productVO);
        optionServiceImpl.insertProductOption(productVO);
    }

    @Test
    public void insertPurchaseProductOptionNew() {
        //조합형 + 작성형 옵션 등록 테스트
        //옵션 전체 등록
        ProductVO productVO = new ProductVO();
        Gson gson = new Gson();
        productVO = gson.fromJson(setJsonData(ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE, "I"), ProductVO.class);
        productVO = setProductPurchaseVO(productVO, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE, "I");
        productVO = optionServiceImpl.setProductInformationOptionVONew(productVO);

        //테스트를 위한 prdNo설정
        productVO.setPrdNo(2);
        productVO.getOptionVO().setPrdSelQty(100);

        //2. insertPdStock
        //System.out.println(productVO);
        optionServiceImpl.insertProductOption(productVO);
    }

    @Test
    public void updatePurchaseProductOptionNew() {
        //조합형 + 작성형 옵션 등록 테스트
        //옵션 전체 등록
        ProductVO productVO = new ProductVO();
        Gson gson = new Gson();
        productVO = gson.fromJson(setJsonData(ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE, "U"), ProductVO.class);
        productVO = setProductPurchaseVO(productVO, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE, "U");
        productVO = optionServiceImpl.setProductInformationOptionVONew(productVO);

        //테스트를 위한 prdNo설정
        productVO.setPrdNo(2);
        productVO.getOptionVO().setPrdSelQty(100);

        //2. insertPdStock
        //System.out.println(productVO);
        optionServiceImpl.insertProductOption(productVO);
    }

    @Test
    public void insertPurchaseProductMartOptionNew() {
        //조합형 + 작성형 옵션 등록 테스트
        //옵션 전체 등록
        ProductVO productVO = new ProductVO();
        Gson gson = new Gson();
        productVO = gson.fromJson(setJsonData(ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE, "I"), ProductVO.class);
        productVO = setProductPurchaseVO(productVO, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE, "I");
        productVO.setMartPrdYn("Y");
        productVO = optionServiceImpl.setProductInformationOptionVONew(productVO);

        //테스트를 위한 prdNo설정
        productVO.setPrdNo(2);
        productVO.getOptionVO().setPrdSelQty(100);

        //2. insertPdStock
        //System.out.println(productVO);
        optionServiceImpl.insertProductOption(productVO);
    }

    @Test
    public void updatePurchaseProductMartOptionNew() {
        //조합형 + 작성형 옵션 등록 테스트
        //옵션 전체 등록
        ProductVO productVO = new ProductVO();
        Gson gson = new Gson();
        productVO = gson.fromJson(setJsonData(ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE, "U"), ProductVO.class);
        productVO = setProductPurchaseVO(productVO, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE, "U");
        productVO.setMartPrdYn("Y");
        productVO = optionServiceImpl.setProductInformationOptionVONew(productVO);

        //테스트를 위한 prdNo설정
        productVO.setPrdNo(2);
        productVO.getOptionVO().setPrdSelQty(100);

        //2. insertPdStock
        //System.out.println(productVO);
        optionServiceImpl.insertProductOption(productVO);
    }

    @Test
    public void insertFrProductOptionNew() {
        //조합형 + 작성형 옵션 등록 테스트
        //옵션 전체 등록
        ProductVO productVO = new ProductVO();
        Gson gson = new Gson();
        productVO = gson.fromJson(setJsonData(ProductConstDef.BSN_DEAL_CLF_COMMISSION, "I"), ProductVO.class);
        productVO = setProductVO(productVO, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_COMMISSION, "I");
        productVO = optionServiceImpl.setProductInformationOptionVONew(productVO);

        //테스트를 위한 prdNo설정
        productVO.setPrdNo(1);
        productVO.getOptionVO().setPrdSelQty(100);

        productVO.getBaseVO().setFrPrdNo(2);

        //2. insertPdStock
        //System.out.println(productVO);
        optionServiceImpl.insertProductOption(productVO);
    }

    @Test
    public void copyProductOptionNew() {
        ProductVO productVO = new ProductVO();
        Gson gson = new Gson();
        productVO = gson.fromJson(setJsonData(ProductConstDef.BSN_DEAL_CLF_COMMISSION, "I"), ProductVO.class);
        productVO = setProductVO(productVO, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_COMMISSION, "I");
        productVO.setPrdNo(1);
        productVO = optionServiceImpl.setProductInformationOptionVONew(productVO);

        productVO.setPrdNo(3);  // 신규 생성된 상품번호 (테스트를 위해 임시로 생성되었다고 가정함)
        productVO.setPrdCopyYn("Y");
        productVO.setOptChangeYn("N");
        productVO.getBaseVO().setReRegPrdNo(1); // 복사대상 상품번호
        productVO.getBaseVO().setCertTypCd("");

        optionServiceImpl.insertProductOption(productVO);
    }

    @Test
    public void copyProductNoOptionNew() {
        ProductVO productVO = new ProductVO();
        Gson gson = new Gson();
        productVO = gson.fromJson(setJsonData(ProductConstDef.BSN_DEAL_CLF_COMMISSION, "N"), ProductVO.class);
        productVO.setOptionVO(new OptionVO());
        productVO.getOptionVO().setPrdSelQty(100);
        productVO = setProductVO(productVO, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_COMMISSION, "I");
        productVO.setPrdNo(1);
        productVO = optionServiceImpl.setProductInformationOptionVONew(productVO);

        productVO.setPrdNo(3);  // 신규 생성된 상품번호 (테스트를 위해 임시로 생성되었다고 가정함)
        productVO.setPrdCopyYn("Y");
        productVO.setOptChangeYn("N");
        productVO.getBaseVO().setReRegPrdNo(1); // 복사대상 상품번호
        productVO.getBaseVO().setCertTypCd("");

        optionServiceImpl.insertProductOption(productVO);
    }

    @Test
    public void copyProductChangeOptionNew() {
        ProductVO productVO = new ProductVO();
        Gson gson = new Gson();
        productVO = gson.fromJson(setJsonData(ProductConstDef.BSN_DEAL_CLF_COMMISSION, "I"), ProductVO.class);
        productVO = setProductVO(productVO, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_COMMISSION, "I");
        productVO.setPrdNo(1);
        productVO = gson.fromJson(setJsonData(ProductConstDef.BSN_DEAL_CLF_COMMISSION, "U"), ProductVO.class);
        productVO = setProductVO(productVO, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_COMMISSION, "U");
        productVO = optionServiceImpl.setProductInformationOptionVONew(productVO);

        productVO.setPrdNo(3);  // 신규 생성된 상품번호 (테스트를 위해 임시로 생성되었다고 가정함)
        productVO.setPrdCopyYn("Y");
        productVO.setOptChangeYn("Y");
        productVO.getBaseVO().setReRegPrdNo(1); // 복사대상 상품번호
        productVO.getBaseVO().setCertTypCd("");

        optionServiceImpl.insertProductOption(productVO);
    }

    @Test
    public void copyProductPurchaseOptionNew() {
        long selMnbdNo = 10000330;
        ProductVO productVO = new ProductVO();
        Gson gson = new Gson();
        productVO = gson.fromJson(setJsonData(ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE, "I"), ProductVO.class);
        productVO = setProductPurchaseVO(productVO, selMnbdNo, ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE, "I");
        productVO.setPrdNo(2);
        productVO = optionServiceImpl.setProductInformationOptionVONew(productVO);

        productVO.setPrdNo(4);  // 신규 생성된 상품번호 (테스트를 위해 임시로 생성되었다고 가정함)
        productVO.setPrdCopyYn("Y");
        productVO.setOptChangeYn("N");
        productVO.getBaseVO().setReRegPrdNo(2); // 복사대상 상품번호
        productVO.getBaseVO().setCertTypCd("");

        optionServiceImpl.insertProductOption(productVO);
    }

    @Test
    public void getStockList() {
        ProductStockVO productStockVO = new ProductStockVO();
        productStockVO.setPrdNo(1244820849);
        productStockVO.setSetTypCd(ProductConstDef.SetTypCd.BUNDLE.value());
        productStockVO.setOptPop(true);
        optionServiceImpl.getStockList(productStockVO);
    }

    @Test
    public void getStockList2() {
        ProductStockVO productStockVO = new ProductStockVO();
        productStockVO.setPrdNo(1244820849);
        optionServiceImpl.getStockList(productStockVO);
    }

    @Test
    public void getOptInfoListAtStd() {
        StandardOptionGroupMappingVO standardOptionGroupMappingVO = new StandardOptionGroupMappingVO();
        standardOptionGroupMappingVO.setDispCtgrNo(1006632);
        optionServiceImpl.getOptInfoListAtStd(standardOptionGroupMappingVO);
    }

    @Test
    public void getOptInfoListAtStdWithPrd() {
        StandardOptionGroupMappingVO standardOptionGroupMappingVO = new StandardOptionGroupMappingVO();
        standardOptionGroupMappingVO.setDispCtgrNo(1006632);
        standardOptionGroupMappingVO.setPrdNo(1042849673);
        optionServiceImpl.getOptInfoListAtStdWithPrd(standardOptionGroupMappingVO);
    }

    @Test
    public void getOptionWghtHist() {
        HashMap hashMap = new HashMap();
        hashMap.put("prdNo", "1244964953");
        hashMap.put("stockNo", "4551499858");
        optionServiceImpl.getOptionWghtHist(hashMap);
    }

    @Test
    public void getProductFrOptionInfo() {
        long prdNo = 315651002;
        optionServiceImpl.getProductFrOptionInfo(prdNo);
    }

    @Test
    public void getProductOptionInfo() {
        ProductVO productVO = new ProductVO();
        productVO.setPrdNo(1039382884);

        productVO.setBaseVO(new BaseVO());
        productVO.getBaseVO().setPrdInfoType("");

        PriceVO priceVO = new PriceVO();
        priceVO.setEventNo(-1);
        productVO.setPriceVO(priceVO);

        productVO.setCtlgVO(new CtlgVO());
        productVO.setOptionVO(new OptionVO());

        optionServiceImpl.getProductOptionInfo(productVO);
    }

    @Test
    public void getProductOptMartComboList() {
        HashMap hashMap = new HashMap();
        hashMap.put("ctlgNo", 1);
        optionServiceImpl.getProductOptMartComboList(hashMap);
    }

    @Test
    public void getProductStockLst() {
        ProductStockVO productStockVO = new ProductStockVO();
        productStockVO.setPrdNo(1039382884);
        optionServiceImpl.getProductStockLst(productStockVO);
    }
}