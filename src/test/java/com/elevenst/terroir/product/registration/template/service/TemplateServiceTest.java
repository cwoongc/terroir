package com.elevenst.terroir.product.registration.template.service;

import com.elevenst.terroir.product.registration.category.vo.DpGnrlDispVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.vo.BaseVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.template.validate.TemplateValidate;
import com.elevenst.terroir.product.registration.template.vo.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class TemplateServiceTest {

    public static final long PRD_NO01 = 888888881L;
    public static final long TEMPLT_NM01 = 999999991L;
    public boolean PRD_INFO_TEMP_INSERT = false;
    public boolean PRD_INFO_TEMP_ADD_INSERT = false;
    public long TEMP_NO = 0;
    public long TEMP_NO1 = 0;

    @Autowired
    TemplateValidate templateValidate;

    @Autowired
    TemplateServiceImpl templateService;


    @Test
    public void setProductTemplateTest() {

        TemplateVO templateVO = new TemplateVO();
        AddTemplateVO addPrdTempleteVO = new AddTemplateVO();

        ProductInformationTemplateVO productInformationTemplateVO = new ProductInformationTemplateVO();
        InventoryVO inventoryVO = new InventoryVO();

        SellInformationVO sellInformationVO = new SellInformationVO();
        PrdInfoTmplVO prdInfoTmplVO = new PrdInfoTmplVO();

        //상품템플릿

        sellInformationVO.setDlvClf("03");//11번가 해외배송여부 확인
        sellInformationVO.setAbrdInCd("01"); //11번가 무료픽업인 경우
        sellInformationVO.setPrdWght(2);

        sellInformationVO.setDlvCnAreaCd("01");
        sellInformationVO.setDlvWyCd("02");
        sellInformationVO.setDlvCstPayTypCd("03");
        sellInformationVO.setFrDlvBasiAmt(100);
        sellInformationVO.setRtngdDlvCst(111);
        sellInformationVO.setExchDlvCst(112);
        sellInformationVO.setBndlDlvCnYn("N");
        sellInformationVO.setTodayDlvCnYn("Y");
        sellInformationVO.setAppmtDyDlvCnYn("N");
        sellInformationVO.setSndPlnTrm(113);
        sellInformationVO.setCreateNo(1004);
        sellInformationVO.setUpdateNo(1004);
        sellInformationVO.setDlvCstInstBasiCd("04");
        sellInformationVO.setUnDlvCnYn("Y");
        sellInformationVO.setVisitDlvYn("N");
        sellInformationVO.setJejuDlvCst(114);
        sellInformationVO.setIslandDlvCst(115);
        sellInformationVO.setRtngdDlvCd("05");
        sellInformationVO.setRcptIsuCnYn("N");

        //판매정보 템플릿

        prdInfoTmplVO.setMemNo(1004);
        prdInfoTmplVO.setPrdInfoTmpltNm("템플릿이름");
        prdInfoTmplVO.setPrdInfoTmpltClfCd("CD");
        prdInfoTmplVO.setCreateNo("1005");
        prdInfoTmplVO.setUpdateNo("1005");

        //수량별 차등 기준값

        PdPrdLstOrdBasiDlvCstVO pdPrdLstOrdBasiDlvCstVO1 = new PdPrdLstOrdBasiDlvCstVO();

        pdPrdLstOrdBasiDlvCstVO1.setCreateNo(1004);
        pdPrdLstOrdBasiDlvCstVO1.setDlvCst(1000);
        pdPrdLstOrdBasiDlvCstVO1.setDlvCstInstNo(1010);
        pdPrdLstOrdBasiDlvCstVO1.setDlvCstInstObjClfCd("01");
        pdPrdLstOrdBasiDlvCstVO1.setOrdBgnQty(1);
        pdPrdLstOrdBasiDlvCstVO1.setOrdEndQty(2);
        pdPrdLstOrdBasiDlvCstVO1.setUpdateNo(1004);

        PdPrdLstOrdBasiDlvCstVO pdPrdLstOrdBasiDlvCstVO2 = new PdPrdLstOrdBasiDlvCstVO();

        pdPrdLstOrdBasiDlvCstVO2.setCreateNo(1004);
        pdPrdLstOrdBasiDlvCstVO2.setDlvCst(2000);
        pdPrdLstOrdBasiDlvCstVO2.setDlvCstInstNo(2020);
        pdPrdLstOrdBasiDlvCstVO2.setDlvCstInstObjClfCd("02");
        pdPrdLstOrdBasiDlvCstVO2.setOrdBgnQty(3);
        pdPrdLstOrdBasiDlvCstVO2.setOrdEndQty(4);
        pdPrdLstOrdBasiDlvCstVO2.setUpdateNo(1004);

        List inventoryOrdBasiDeliCostVOList = new ArrayList();
        inventoryOrdBasiDeliCostVOList.add(pdPrdLstOrdBasiDlvCstVO1);
        inventoryOrdBasiDeliCostVOList.add(pdPrdLstOrdBasiDlvCstVO2);

        inventoryVO.setInventoryOrdBasiDeliCostVOList(inventoryOrdBasiDeliCostVOList);

        // 안내정보 등록
        List inventoryProductDescVOList = new ArrayList();

        PdPrdLstDescVO pdPrdLstDescVO1 = new PdPrdLstDescVO();
        pdPrdLstDescVO1.setClobTypYn("Y");
        pdPrdLstDescVO1.setCreateNo(1004);
        pdPrdLstDescVO1.setPrdDescContClob("testtest");
        pdPrdLstDescVO1.setPrdDescContVc("vcvcvcvc");
        pdPrdLstDescVO1.setPrdDescObjClfCd("01");
        pdPrdLstDescVO1.setPrdDescTypCd("02");

        PdPrdLstDescVO pdPrdLstDescVO2 = new PdPrdLstDescVO();
        pdPrdLstDescVO2.setClobTypYn("Y");
        pdPrdLstDescVO2.setCreateNo(1004);
        pdPrdLstDescVO2.setPrdDescContClob("testtesttesttest");
        pdPrdLstDescVO2.setPrdDescContVc("vcvcvcvcvcvcvcvc");
        pdPrdLstDescVO2.setPrdDescObjClfCd("03");
        pdPrdLstDescVO2.setPrdDescTypCd("04");

        inventoryProductDescVOList.add(pdPrdLstDescVO1);
        inventoryProductDescVOList.add(pdPrdLstDescVO2);

        inventoryVO.setInventoryProductDescVOList(inventoryProductDescVOList);

        //출고지 반품지 등록
        List inventoryProductAddressVOList = new ArrayList();
        PdPrdLstTgowPlcExchRtngdVO param1 = new PdPrdLstTgowPlcExchRtngdVO();
        PdPrdLstTgowPlcExchRtngdVO param2 = new PdPrdLstTgowPlcExchRtngdVO();

        param1.setPrdAddrObjClfCd("01");
        param1.setPrdAddrObjNo(12345);
        param1.setMemNo(1004);
        param1.setCreateNo(1005);
        param1.setAddrSeq(104);
        param1.setPrdAddrClfCd("02");
        param1.setMbAddrLocation("03");

        param2.setPrdAddrObjClfCd("02");
        param2.setPrdAddrObjNo(12345);
        param2.setMemNo(1004);
        param2.setCreateNo(1005);
        param2.setAddrSeq(104);
        param2.setPrdAddrClfCd("03");
        param2.setMbAddrLocation("05");

        inventoryProductAddressVOList.add(param1);
        inventoryProductAddressVOList.add(param2);

        inventoryVO.setInventoryProductAddressVOList(inventoryProductAddressVOList);


        productInformationTemplateVO.setSellInformationVO(sellInformationVO);
        productInformationTemplateVO.setPrdInfoTmplVO(prdInfoTmplVO);

        templateVO.setProductInformationTemplateVO(productInformationTemplateVO);
        templateVO.setInventoryVO(inventoryVO);

        //추가상품 데이터
        addPrdTempleteVO.setProductInformationTemplateVO(productInformationTemplateVO);
        List listAddProd = new ArrayList();
        InventoryAddProductCompositionVO inventoryAddProductCompositionVO = new InventoryAddProductCompositionVO();
        inventoryAddProductCompositionVO.setMainPrdNo(99999999);
        inventoryAddProductCompositionVO.setPrdCompNo(111111);

        PdPrdLstAddPrdCompVO pdPrdLstAddPrdCompVO = new PdPrdLstAddPrdCompVO();

        pdPrdLstAddPrdCompVO.setAddCompPrc(2000);
        pdPrdLstAddPrdCompVO.setCompPrdNm("콤프이름");
        pdPrdLstAddPrdCompVO.setCompPrdNo(99999999);
        pdPrdLstAddPrdCompVO.setCompPrdQty(200);
        pdPrdLstAddPrdCompVO.setCompPrdStckNo(220);
        pdPrdLstAddPrdCompVO.setCreateNo(1004);
        pdPrdLstAddPrdCompVO.setUpdateNo(1004);
        pdPrdLstAddPrdCompVO.setMainPrdYn("Y");
        pdPrdLstAddPrdCompVO.setPrdCompTypCd("UU");
        inventoryAddProductCompositionVO.setPdPrdLstAddPrdCompVO(pdPrdLstAddPrdCompVO);

        InventoryAddProductCompositionVO inventoryAddProductCompositionVO1 = new InventoryAddProductCompositionVO();
        inventoryAddProductCompositionVO1.setMainPrdNo(99999999);
        inventoryAddProductCompositionVO1.setPrdCompNo(111111);

        PdPrdLstAddPrdCompVO pdPrdLstAddPrdCompVO1 = new PdPrdLstAddPrdCompVO();

        pdPrdLstAddPrdCompVO1.setAddCompPrc(2000);
        pdPrdLstAddPrdCompVO1.setCompPrdNm("콤프이름");
        pdPrdLstAddPrdCompVO1.setCompPrdNo(99999999);
        pdPrdLstAddPrdCompVO1.setCompPrdQty(200);
        pdPrdLstAddPrdCompVO1.setCompPrdStckNo(220);
        pdPrdLstAddPrdCompVO1.setCreateNo(1004);
        pdPrdLstAddPrdCompVO1.setUpdateNo(1004);
        pdPrdLstAddPrdCompVO1.setMainPrdYn("Y");
        pdPrdLstAddPrdCompVO1.setPrdCompTypCd("II");
        inventoryAddProductCompositionVO1.setPdPrdLstAddPrdCompVO(pdPrdLstAddPrdCompVO1);


        listAddProd.add(inventoryAddProductCompositionVO);
        listAddProd.add(inventoryAddProductCompositionVO1);

        addPrdTempleteVO.setListAddProd(listAddProd);


        templateService.setProductTemplate(templateVO ,addPrdTempleteVO);
    }


    @Test
    public void setSelStatCdTmplt() {

        ProductVO productVO = new ProductVO();
        ProductVO preProductVO = new ProductVO();

        BaseVO preBaseVO = new BaseVO();
        DpGnrlDispVO dpGnrlDispVO = new DpGnrlDispVO();
        BaseVO baseVO = new BaseVO();

        preBaseVO.setTemplateYn("Y");
        preProductVO.setBaseVO(preBaseVO);

        productVO.setDpGnrlDispVO(dpGnrlDispVO);
        productVO.setBaseVO(baseVO);

        templateService.setSelStatCdTmplt(preProductVO,productVO);

        Assert.assertEquals(productVO.getBaseVO().getSelStatCd() , ProductConstDef.PRD_SEL_STAT_CD_BAN_SALE);
        Assert.assertEquals(productVO.getDpGnrlDispVO().getSelStatCd() , ProductConstDef.PRD_SEL_STAT_CD_BAN_SALE);

    }
}
