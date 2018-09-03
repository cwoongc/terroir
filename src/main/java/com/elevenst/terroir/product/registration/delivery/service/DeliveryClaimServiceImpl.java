package com.elevenst.terroir.product.registration.delivery.service;

import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.terroir.product.registration.delivery.code.DlvClfCdTypes;
import com.elevenst.terroir.product.registration.delivery.code.PrdAddrClfCd;
import com.elevenst.terroir.product.registration.delivery.vo.ClaimAddressInfoVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DeliveryClaimServiceImpl {

    public List<ClaimAddressInfoVO> setClaimAddressVOList(ProductVO productVO) {

        List<ClaimAddressInfoVO> resultVOList = new ArrayList<ClaimAddressInfoVO>();

        //출고지
        ClaimAddressInfoVO exWarehouseVO = new ClaimAddressInfoVO();
        if(productVO.getBaseVO().getOutAddrSeq() > 0) {
            exWarehouseVO.setAddrSeq(productVO.getBaseVO().getOutAddrSeq());
            exWarehouseVO.setPrdAddrClfCd(PrdAddrClfCd.EX_WAREHOUSE.getDtlsCd());
            exWarehouseVO.setMemNo(productVO.getBaseVO().getOutMemNo());
            exWarehouseVO.setCreateNo(productVO.getUpdateNo());
            exWarehouseVO.setUpdateNo(productVO.getUpdateNo());
            exWarehouseVO.setMbAddrLocation(productVO.getBaseVO().getOutMbAddrLocation());
            exWarehouseVO.setFrCtrCd(productVO.getBaseVO().getFrCtrCd());

            resultVOList.add(exWarehouseVO);
        }

        //교환/반품지
        ClaimAddressInfoVO exchangeReturnVO = new ClaimAddressInfoVO();
        if(productVO.getBaseVO().getInAddrSeq() > 0) {
            exchangeReturnVO.setAddrSeq(productVO.getBaseVO().getInAddrSeq());
            exchangeReturnVO.setPrdAddrClfCd(PrdAddrClfCd.EXCHANGE_RETURN.getDtlsCd());
            exchangeReturnVO.setMemNo(productVO.getBaseVO().getInMemNo());
            exchangeReturnVO.setCreateNo(productVO.getUpdateNo());
            exchangeReturnVO.setUpdateNo(productVO.getUpdateNo());
            exchangeReturnVO.setMbAddrLocation(productVO.getBaseVO().getInMbAddrLocation());

            resultVOList.add(exchangeReturnVO);
        }

        //방문수령지
        ClaimAddressInfoVO visitReceiveVO = new ClaimAddressInfoVO();
        if(StringUtils.equals(productVO.getBaseVO().getVisitDlvYn(), "Y") &&
            productVO.getBaseVO().getVisitAddrSeq() > 0) {
            visitReceiveVO.setAddrSeq(productVO.getBaseVO().getVisitAddrSeq());
            visitReceiveVO.setPrdAddrClfCd(PrdAddrClfCd.VISIT_RECEIVE.getDtlsCd());
            visitReceiveVO.setMemNo(productVO.getSelMnbdNo());
            visitReceiveVO.setCreateNo(productVO.getUpdateNo());
            visitReceiveVO.setUpdateNo(productVO.getUpdateNo());
            visitReceiveVO.setMbAddrLocation(productVO.getBaseVO().getVisitMbAddrLocation());

            resultVOList.add(visitReceiveVO);
        }

        //11번가 해외배송일 경우
        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getDlvClf(), DlvClfCdTypes.ELEVENST_ABROAD_DELIVERY)) {
            // 판매자 해외출고지
            ClaimAddressInfoVO globalExWarehouseVO = new ClaimAddressInfoVO();
            if(productVO.getBaseVO().getGlobalOutAddrSeq() > 0) {
                globalExWarehouseVO.setAddrSeq(productVO.getBaseVO().getGlobalOutAddrSeq());
                globalExWarehouseVO.setPrdAddrClfCd(PrdAddrClfCd.SELLER_GLOBAL_EX_WAREHOUSE.getDtlsCd());
                globalExWarehouseVO.setMemNo(productVO.getSelMnbdNo());
                globalExWarehouseVO.setCreateNo(productVO.getUpdateNo());
                globalExWarehouseVO.setUpdateNo(productVO.getUpdateNo());
                globalExWarehouseVO.setMbAddrLocation(productVO.getBaseVO().getGlobalOutMbAddrLocation());

                resultVOList.add(globalExWarehouseVO);
            }

            //판매자 해외 반품교환지
            ClaimAddressInfoVO globalExchangeReturnVO = new ClaimAddressInfoVO();
            if(productVO.getBaseVO().getGlobalInAddrSeq() > 0) {
                globalExchangeReturnVO.setAddrSeq(productVO.getBaseVO().getGlobalInAddrSeq());
                globalExchangeReturnVO.setPrdAddrClfCd(PrdAddrClfCd.SELLER_EXCHANGE_RETURN.getDtlsCd());
                globalExchangeReturnVO.setMemNo(productVO.getSelMnbdNo());
                globalExchangeReturnVO.setCreateNo(productVO.getUpdateNo());
                globalExchangeReturnVO.setUpdateNo(productVO.getUpdateNo());
                globalExchangeReturnVO.setMbAddrLocation(productVO.getBaseVO().getGlobalInMbAddrLocation());

                resultVOList.add(globalExchangeReturnVO);
            }

        }

        return resultVOList;

    }

}
