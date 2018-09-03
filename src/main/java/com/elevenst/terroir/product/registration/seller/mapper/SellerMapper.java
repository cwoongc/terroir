package com.elevenst.terroir.product.registration.seller.mapper;

import com.elevenst.terroir.product.registration.delivery.vo.MbMemVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.entity.PdSellerServiceLmtLog;
import com.elevenst.terroir.product.registration.seller.vo.MbCnsgnDstSellerVO;
import com.elevenst.terroir.product.registration.seller.vo.MbRoadShopVO;
import com.elevenst.terroir.product.registration.seller.vo.MbSellerAddSetVO;
import com.elevenst.terroir.product.registration.seller.vo.MbSellerOptSetDetailVO;
import com.elevenst.terroir.product.registration.seller.vo.SellerAuthVO;
import com.elevenst.terroir.product.registration.seller.vo.SellerServiceLmtVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SellerMapper {

    SellerAuthVO getSellerAuthInfo(SellerAuthVO sellerAuthVO);
    List<SellerAuthVO> getSellerAuthList(SellerAuthVO sellerAuthVO);

    int getBaseSelMnbdNck(SellerAuthVO sellerAuthVO);

    Long isCupnExcldSeller(long sellerNo);

    String getSellerId(long memNo);
    String getSellerTypCd(long memNo);
    MbCnsgnDstSellerVO getConsignmentDstSellerInfo(long memNo);
    MbSellerAddSetVO getMbSellerAddSetInfo(long memNo);
    MbSellerOptSetDetailVO getMbSellerOptSetDetail(MbSellerOptSetDetailVO mbSellerOptSetDetailVO);

    ProductVO getProductSellerInfo(long prdNo);

    void mergePdSellerServiceLmtLog(PdSellerServiceLmtLog pdSellerServiceLmtLog);

    SellerServiceLmtVO getSellerServiceLmtInfo(SellerServiceLmtVO sellerServiceLmtVO);
    MbRoadShopVO getMbRoadShopApplyInfo(long memNo);
    MbMemVO getSellerRtnChngDlvInfo(long memNo);
}
