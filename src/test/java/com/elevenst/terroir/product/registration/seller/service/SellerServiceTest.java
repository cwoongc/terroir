package com.elevenst.terroir.product.registration.seller.service;

import com.elevenst.terroir.product.registration.seller.vo.MbRoadShopVO;
import com.elevenst.terroir.product.registration.seller.vo.MbSellerOptSetDetailVO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class SellerServiceTest {

    @Autowired
    private SellerServiceImpl sellerServiceImpl;

    @Rule
    public ExpectedException expectedExcetption = ExpectedException.none();

    @Test
    public void isCupnExcldSeller() {
        long memNo = 10000439;
        sellerServiceImpl.isCupnExcldSeller(memNo);
    }

    @Test
    public void getProductSellerInfo() {
        sellerServiceImpl.getProductSellerInfo(1244662904);
    }

    @Test
    public void getSellerTypCd() {
        long memNo = 10000276;
        sellerServiceImpl.getSellerTypCd(memNo);
    }

    @Test
    public void getConsignmentDstSellerInfo() {
        long memNo = 10000276;
        sellerServiceImpl.getConsignmentDstSellerInfo(memNo);
    }

    @Test
    public void hasCategoryModificationAuth() {
        long memNo = 10000276;
        sellerServiceImpl.hasCategoryModificationAuth(memNo);
    }

    @Test
    public void getMbSellerOptSetDetail() {
        MbSellerOptSetDetailVO mbSellerOptSetDetailVO = new MbSellerOptSetDetailVO();
        mbSellerOptSetDetailVO.setMemNo(10000276);
        mbSellerOptSetDetailVO.setOptSvcCd("PICP");
        mbSellerOptSetDetailVO.setOptCd("agree_yn");
        sellerServiceImpl.getMbSellerOptSetDetail(mbSellerOptSetDetailVO);
    }

    @Test
    public void getMbRoadShopApplyInfo() {
        long memNo = 38677742;
        MbRoadShopVO mbRoadShopVO = sellerServiceImpl.getMbRoadShopApplyInfo(memNo);
        if(mbRoadShopVO != null) System.out.println("getMbRoadShopApplyInfo= "+mbRoadShopVO.getEnbrRqstStatCd());
    }
}
