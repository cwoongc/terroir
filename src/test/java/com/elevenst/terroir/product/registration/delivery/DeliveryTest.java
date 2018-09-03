package com.elevenst.terroir.product.registration.delivery;

import com.elevenst.terroir.product.registration.delivery.code.PrdAddrClfCd;
import com.elevenst.terroir.product.registration.delivery.controller.DeliveryController;
import com.elevenst.terroir.product.registration.delivery.service.DeliveryServiceImpl;
import com.elevenst.terroir.product.registration.delivery.validate.DeliveryValidate;
import com.elevenst.terroir.product.registration.delivery.vo.ClaimAddressInfoVO;
import com.elevenst.terroir.product.registration.delivery.vo.PdSellerIslandDlvCstVO;
import com.elevenst.terroir.product.registration.delivery.vo.ProductDeliveryVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
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
public class DeliveryTest {

    @Autowired
    DeliveryServiceImpl deliveryServiceImpl;

    @Autowired
    DeliveryValidate deliveryValidate;

    @Autowired
    DeliveryController deliveryController;

    @Test
    public void checkClaimAddressVO() {

        long selMnbdNo = 14538450;
        List<ClaimAddressInfoVO> claimAddressInfoVOList = new ArrayList<ClaimAddressInfoVO>();
        ClaimAddressInfoVO param1 = new ClaimAddressInfoVO();
        param1.setMbAddrLocation("02");
        param1.setAddrCd("01");
        param1.setMemNo(selMnbdNo);
        param1.setAddrSeq(16);
        param1.setPrdAddrClfCd(PrdAddrClfCd.EXCHANGE_RETURN.getDtlsCd());

        claimAddressInfoVOList.add(param1);

        ProductVO productVO = new ProductVO();
        productVO.setSelMnbdNo(selMnbdNo);
        productVO.setClaimAddressInfoVOList(claimAddressInfoVOList);

        deliveryValidate.checkClaimAddressVO(productVO);

    }

    @Test
    public void updateProductReglUseYn() {
        ProductDeliveryVO productDeliveryVO = new ProductDeliveryVO();
        productDeliveryVO.setPrdNo(1);
        productDeliveryVO.setUpdateNo(1);
        productDeliveryVO.setUseYn("Y");
        deliveryServiceImpl.updateProductReglUseYn(productDeliveryVO);
    }

    @Test
    public void insertProductReglDeliveryInfoHist() {
        ProductDeliveryVO productDeliveryVO = new ProductDeliveryVO();
        productDeliveryVO.setPrdNo(1);
        deliveryServiceImpl.insertProductReglDeliveryInfoHist(productDeliveryVO);
    }

    @Test
    public void getSellerIslandDlvCst() {
        long selMnbdNo = 10000276;
        PdSellerIslandDlvCstVO pdSellerIslandDlvCstVO = deliveryServiceImpl.getSellerIslandDlvCst(selMnbdNo);
        Assert.assertTrue(pdSellerIslandDlvCstVO != null);
    }

    @Test
    public void getGlobalItgInfoForPrdDeliveryCost() {
        long prdNo = 1244820849;
        deliveryServiceImpl.getGlobalItgInfoForPrdDeliveryCost(prdNo);
    }

    @Test
    public void getGlobalItgDeliveryCost() {
        // long itgNo, long finalDscPrc, long weight, String HSCode
        long itgNo = 24591453;
        long finalDscPrc = 100;
        long weight = 14;
        String hsCode = "";
        deliveryServiceImpl.getGlobalItgDeliveryCost(itgNo, finalDscPrc, weight, hsCode);
    }

    @Test
    public void getDlvPossibleAreaCd() {
        deliveryController.getDlvPossibleAreaCd();
    }

    @Test
    public void getDlvWyCd() {
        deliveryController.getDlvWyCd();
    }

    @Test
    public void getOrdQtyBasDlvcstList() {
        long prdNo = 1085160586;
        deliveryServiceImpl.getOrdQtyBasDlvcstList(prdNo);
    }

    @Test
    public void getSellerBasiDlvCstTxt() {
        long memNo = 10000276;
        String result = deliveryServiceImpl.getSellerBasiDlvCstTxt(memNo);

        Assert.assertEquals("2,500원@20,000원 미만 구매/1,000원@20,000원 이상 ~ 40,000원 미만 구매/무료@40,000원 이상 구매",result);
    }

    @Test
    public void getNowDlvCstTxt() {
        String dlvCstInstBasiCd = "12";
        String result = deliveryServiceImpl.getNowDlvCstTxt(dlvCstInstBasiCd);

        Assert.assertEquals("2500@20000",result);

    }

    @Test
    public void getIntegratedInOutAddrInfo() {
        //long userNo, String prdAddrClfCd, String memTyp, boolean isAbrdSubIntegId, boolean isOut
        long userNo = 10000276;
        String prdAddrClfCd = null;
        String memTyp = ProductConstDef.PRD_MEM_TYPE_SUPPLIER;
        boolean isAbrdSubIntegId = true;
        boolean isOut = true;
        deliveryServiceImpl.getIntegratedInOutAddrInfo(userNo, prdAddrClfCd, memTyp, isAbrdSubIntegId, isOut);
    }

    @Test
    public void getSellerBasicInOutAddrInfo() {
        long userNo = 10000276;
        String prdAddrClfCd = null;
        String memTyp = ProductConstDef.PRD_MEM_TYPE_SUPPLIER;
        boolean isAbrdSubIntegId = true;
        deliveryServiceImpl.getSellerBasicInOutAddrInfo(userNo, prdAddrClfCd, memTyp, isAbrdSubIntegId);
    }

    @Test
    public void isUseGlobalDeliver() {
        long memNo = 10000276;
        deliveryServiceImpl.isUseGlobalDeliver(memNo);
    }
}
