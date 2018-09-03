package com.elevenst.terroir.product.registration.delivery.mapper;

import com.elevenst.terroir.product.registration.delivery.entity.PdOrdQtyBasiDlvCst;
import com.elevenst.terroir.product.registration.delivery.entity.PdPrdTgowPlcExchRtngd;
import com.elevenst.terroir.product.registration.delivery.entity.PdReglDlvPrd;
import com.elevenst.terroir.product.registration.delivery.entity.PdReglDlvPrdHist;
import com.elevenst.terroir.product.registration.delivery.entity.TrGlobalPrdAvgDeli;
import com.elevenst.terroir.product.registration.delivery.vo.*;
import com.elevenst.terroir.product.registration.product.vo.BaseVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface DeliveryMapper {

    long isExistUserAddressSeq(ClaimAddressInfoVO claimAddressInfoVO);
    List<ProductSellerBasiDeliveryCostVO> getSellerBasiDlvCstList(long memNo);

    WmGlobalHsCodeVO getWmGblHsCode(WmGlobalHsCodeVO wmGlobalHsCodeVO);

    TrGlobalProductAverageDeliveryVO getProductAvgDeliInfo(long prdNo);
    long insertTrGlobalPrdAvgDeli(TrGlobalPrdAvgDeli trGlobalPrdAvgDeli);
    long insertTrGlobalPrdAvgDeliHist(TrGlobalPrdAvgDeli trGlobalPrdAvgDeli);

    long updateTrGlobalPrdAvgDeli(TrGlobalPrdAvgDeli trGlobalPrdAvgDeli);
    void insertPdPrdTgowPlcExchRtngd(PdPrdTgowPlcExchRtngd pdPrdTgowPlcExchRtngd);

    void updateProductReglUseYn(PdReglDlvPrd pdReglDlvPrd);
    void insertProductReglDeliveryInfoHist(PdReglDlvPrdHist pdReglDlvPrdHist);
    PrdReglDlvVO selectPdReglDlvPrdUseY(long prdNo);

    void deletePrdOrdQtyBasiDlvCst(long prdNo);

    void updateOrdQtyBasiDlvCstPrdNoHist(long prdNo);

    long getPrdOrdQtyBasiDlvCstPrimaryKey();
    void insertPrdOrdQtyBasiDlvCst(PdOrdQtyBasiDlvCst param);
    void insertPrdOrdQtyBasiDlvCstHist(long dlvCstInstNo);

    void deletePdPrdTgowPlcExchRtngdUsingPrdAddrClfCd(PdPrdTgowPlcExchRtngd pdPrdTgowPlcExchRtngd);
    //상기 deletePdPrdTgowPlcExchRtngdUsingPrdAddrClfCd 와 통합됨
//    void deletePdPrdTgowPlcExchRtngdUsingClaimAddressVO(ClaimAddressInfoVO claimAddressInfoVO);

    PdSellerIslandDlvCstVO getSellerIslandDlvCst(long selMnbdNo);
    HashMap getGlobalItgInfoForPrdDeliveryCost(long prdNo);

    ClaimAddressInfoVO getBaseAddrInfo(ClaimAddressInfoVO claimAddressInfoVO);
    ClaimAddressInfoVO getItgBaseOutAddrInfo(ClaimAddressInfoVO claimAddressInfoVO);
    ClaimAddressInfoVO getItgBaseInAddrInfo(ClaimAddressInfoVO claimAddressInfoVO);
    ClaimAddressInfoVO getSellerBasicInOutAddrInfo(ClaimAddressInfoVO claimAddressInfoVO);
    MemberGlobalDeliverVO getGlobalDeliverSeller(long memNo);

    String getAddrBasiDlvCstText(ClaimAddressInfoVO claimAddressInfoVO);
    List<PrdOrderCountBaseDeliveryVO> getProductOrdDlvCstList(long prdNo);

    String getSellerBasiDlvCstTxt(long memNo);

    String getNowDlvCstTxt(String dlvCstInstBasiCd);

    void updateSellerBasiDlvCstHist(long memNo);

    void deleteSellerBasiDlvCst(long memNo);

    void insertSellerBasiDlvCst(ProductSellerBasiDeliveryCostVO dlvCstStdDtlsVO);

    void insertSellerBasiDlvCstHist(ProductSellerBasiDeliveryCostVO dlvCstStdDtlsVO);

    long geSellerBasiDlvCstSeq();

    BaseVO getGlobalHsCode(BaseVO baseVO);

    String getAddrBasiDlvCstTxt(ProductSellerBasiDeliveryCostVO addrBasiDlvCstVO);

    String getAddrBasiDlvCstTxt2(ProductSellerBasiDeliveryCostVO addrBasiDlvCstVO);

    ClaimAddressInfoVO isFreePickupSupplyTarget(ClaimAddressInfoVO claimAddressInfoVO);

    String getAddrLocation(@Param("prdNo") long prdNo, @Param("prdAddrClfCd") String prdAddrClfCd);

    AddressVO getDomesticAddress(@Param("prdNo") long prdNo, @Param("prdAddrClfCd") String prdAddrClfCd);
    AddressVO getOverseaAddress(@Param("prdNo") long prdNo, @Param("prdAddrClfCd") String prdAddrClfCd);
    AddressVO getGlobalOutAddress(@Param("prdNo") long prdNo, @Param("prdAddrClfCd") String prdAddrClfCd);
}
