package com.elevenst.terroir.product.registration.product;

import com.elevenst.terroir.product.registration.catalog.vo.CtlgVO;
import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import com.elevenst.terroir.product.registration.category.vo.DpGnrlDispVO;
import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.etc.vo.PrdOthersVO;
import com.elevenst.terroir.product.registration.option.vo.OptionVO;
import com.elevenst.terroir.product.registration.price.vo.PriceVO;
import com.elevenst.terroir.product.registration.product.service.AdditionalProductServiceImpl;
import com.elevenst.terroir.product.registration.product.vo.BaseVO;
import com.elevenst.terroir.product.registration.product.vo.ProductAddCompositionVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.vo.SellerVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class AdditionalProductTest {

    @Autowired
    AdditionalProductServiceImpl additionalProductService;

    public void registrationServiceVO(ProductVO productVO) {

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
        baseVO.setDlvCst(2500);
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
        baseVO.setSelPrdClfCd("0:100");
        baseVO.setSndPlnTrm(4);
        baseVO.setSelLimitTypCd("00");
        baseVO.setPrcCmpExpYn("Y");
        baseVO.setDlvWyCd("01");
        baseVO.setSuplDtyfrPrdClfCd("01");
        baseVO.setPrdWght(500);
        baseVO.setDlvCstInstBasiCd("01");
        baseVO.setStdPrdYn("Y");
        baseVO.setCertTypCd("132");


        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setDispCtgrStatCd("03");
        categoryVO.setAdltCrtfYn("Y");
        categoryVO.setMaxLimitQty(0);
        categoryVO.setDispCtgrPrdTypCd("01");
        categoryVO.setRootCtgrNo(1001390);
        categoryVO.setBrandCd("6161");
        categoryVO.setOfferDispLmtYn("N");

//        ProductStockVO productStockVO = new ProductStockVO();
//        productStockVO.setPtnrPrdSellerYN("Y");
//        productStockVO.setSelQty(500);


        PrdOthersVO prdOthersVO = new PrdOthersVO();
        prdOthersVO.setSelPrdClfFpCd("-1:-1");
        prdOthersVO.setSvcCanRgnClfCd("01");
        prdOthersVO.setPrdRiousQty(0);
        prdOthersVO.setDlvProcCanDd(1);
        prdOthersVO.setRsvSchdlClfCd("01");
        prdOthersVO.setRsvSchdlYn("N");

        DpGnrlDispVO dpGnrlDispVO = new DpGnrlDispVO();
        dpGnrlDispVO.setBrandCd("6161");
        dpGnrlDispVO.setStckQty(500);

        PriceVO priceVO = new PriceVO();
        priceVO.setSelPrc(50000);
        priceVO.setMpContractTypCd("01");

        OptionVO optionVO = new OptionVO();
        optionVO.setOptClfCd("01");

        CtlgVO ctlgVO = new CtlgVO();
        ctlgVO.setCtlgNo(226922);


        productVO.setBaseVO(baseVO);
        productVO.setSellerVO(sellerVO);
        productVO.setMemberVO(memberVO);
        productVO.setCategoryVO(categoryVO);
        productVO.setPrdOthersVO(prdOthersVO);
        productVO.setDpGnrlDispVO(dpGnrlDispVO);
        productVO.setPriceVO(priceVO);
        productVO.setOptionVO(optionVO);
        productVO.setCtlgVO(ctlgVO);
    }

    public void setAdditionalProductVO(ProductVO productVO) {

        registrationServiceVO(productVO);

        //추가구성상품에서 baseVO 추가
        BaseVO baseVO = productVO.getBaseVO();
        baseVO.setSelMnbdClfCd("02"); //PD011 판매자
        baseVO.setSelStatCd("103");


        //추가구성상품에서 옵션VO 추가
        OptionVO optionVO = productVO.getOptionVO();
        HashMap<String,String> tmpColMap = new HashMap<String, String>();
        tmpColMap.put("143", "Y");
        optionVO.setTmpColMap(tmpColMap);

        //추가구성상품 추가
        List<ProductAddCompositionVO> productAddCompositionVOList = new ArrayList<ProductAddCompositionVO>();
        ProductAddCompositionVO group1AddPrd1 = new ProductAddCompositionVO();
        group1AddPrd1.setAddPrdGrpNm("addit1");
        group1AddPrd1.setCompPrdNm("memory 1GB");
        group1AddPrd1.setAddCompPrc(1000);
        group1AddPrd1.setCompPrdQty(4);
        group1AddPrd1.setCompPrdVatCd("01");
        group1AddPrd1.setUseYn("Y");
        group1AddPrd1.setAddPrdWght(500);
        productAddCompositionVOList.add(group1AddPrd1);

        ProductAddCompositionVO group1AddPrd2 = new ProductAddCompositionVO();
        group1AddPrd2.setAddPrdGrpNm("addit1");
        group1AddPrd2.setCompPrdNm("memory 2GB");
        group1AddPrd2.setAddCompPrc(2000);
        group1AddPrd2.setCompPrdQty(6);
        group1AddPrd2.setCompPrdVatCd("01");
        group1AddPrd2.setUseYn("Y");
        group1AddPrd2.setAddPrdWght(500);
        productAddCompositionVOList.add(group1AddPrd2);


        ProductAddCompositionVO group2AddPrd1 = new ProductAddCompositionVO();
        group2AddPrd1.setAddPrdGrpNm("addit2");
        group2AddPrd1.setCompPrdNm("USB CABLE");
        group2AddPrd1.setAddCompPrc(10000);
        group2AddPrd1.setCompPrdQty(8);
        group2AddPrd1.setCompPrdVatCd("01");
        group2AddPrd1.setUseYn("Y");
        group2AddPrd1.setAddPrdWght(500);
        productAddCompositionVOList.add(group2AddPrd1);

        productVO.setProductAddCompositionVOList(productAddCompositionVOList);

    }

    @Test
    public void insertAddtTest() {

        ProductVO productVO =  new ProductVO();
        setAdditionalProductVO(productVO);

        productVO.setOfferDispLmtYn("N"); //Y 로 세팅되면 추가구성상품 생성안함.
        productVO.setPrdNo(1245005484);

        additionalProductService.insertAdditionalProductProcess(productVO);

    }

}
