package com.elevenst.terroir.product.registration.common.process.prdtyp;

import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.shop.exception.ShopServiceException;
import com.elevenst.terroir.product.registration.shop.message.ShopServiceExceptionMessage;
import com.elevenst.terroir.product.registration.shop.service.ShopServiceImpl;
import com.elevenst.terroir.product.registration.shop.vo.ShopBranchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MultiTypeServiceImpl {

    @Autowired
    ShopServiceImpl shopService;

    public List<ShopBranchVO> getShopBranchList(ProductVO productVO) throws ShopServiceException {
        List<ShopBranchVO> shopBranchVOList = null;
        try {
            shopBranchVOList = shopService.getShopBranchList(productVO.getBaseVO().getShopNo());
        } catch (Exception ex) {
            ShopServiceException threx = new ShopServiceException(ShopServiceExceptionMessage.SHOP_BRANCH_LIST_SELECT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
        return shopBranchVOList;
    }


    public void checkMultiTourTypeInfo(ProductVO productVO) throws ProductException {
        // 제휴사 필수
        // 여행(여행 카테고리에 PIN번호 서비스 상품(타운)이 선택 가능함으로 카테고리정보로 유형비교)
        if(GroupCodeUtil.equalsDtlsCd(productVO.getCategoryVO().getPrdTypCd(), PrdTypCd.BP_TRAVEL)){
            if(!productVO.isTown() && StringUtil.isEmpty(productVO.getPrdEtcVO().getPrdDetailOutLink())){
                throw new ProductException("제휴사 상품 URL은 반드시 입력하셔야 합니다.");
            }
            if (StringUtil.isEmpty(productVO.getPrdEtcVO().getPrdSvcBgnDy()))	throw new ProductException("최초 출발일은 반드시 입력하셔야 합니다.");
            if (StringUtil.isEmpty(productVO.getPrdEtcVO().getPrdSvcEndDy()))	throw new ProductException("마지막 출발일은 반드시 입력하셔야 합니다.");

            if (productVO.getPrdEtcVO().getPrdSvcBgnDy().length() != 8 || productVO.getPrdEtcVO().getPrdSvcEndDy().length() != 8)
                throw new ProductException("출발일의 날짜 포맷을 잘못 입력 하셨습니다.");

            if (StringUtil.getLong(productVO.getPrdEtcVO().getPrdSvcEndDy()) < StringUtil.getLong(productVO.getPrdEtcVO().getPrdSvcBgnDy()))
                throw new ProductException("마지막 출발일은 최초 출발일 이전으로  입력하실 수 없습니다.");
        }

    }

    public void checkMultiSocialTypeInfo(ProductVO productVO) throws ProductException{
        if (StringUtil.isEmpty(productVO.getPrdEtcVO().getPrdDetailOutLink()))	throw new ProductException("제휴사 상품 URL은 반드시 입력하셔야 합니다.");
    }
}
