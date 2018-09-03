package com.elevenst.terroir.product.registration.fee.validate;

import com.elevenst.terroir.product.registration.fee.exception.FeeServiceException;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.service.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeeValidate {

    @Autowired
    SellerServiceImpl sellerService;


    public boolean isInsertSeFeeItmProduct(ProductVO productVO) throws ProductException {
        if("Y".equals(productVO.getCategoryVO().getMobileYn())
            && "Y".equals(productVO.getMobile1WonYn())
            && 1 == productVO.getPriceVO().getSelPrc()
            && "Y".equals(productVO.getMobile1WonApprvAsk())
            && !sellerService.isMobileSeller(productVO)){

            return true;
        }else{
            return false;
        }

    }

    public void checkItemCtgrStat(ProductVO productVO) throws FeeServiceException{
        if(productVO.getFeeVO() == null) return;

        if(productVO.getFeeVO().getListingItemVOList() != null
            && productVO.getFeeVO().getListingItemVOList().size() > 0)
        {
            if(ProductConstDef.DISP_CTGR_STAT_CD_BEFORE_DISP.equals(productVO.getCategoryVO().getDispCtgrStatCd())){
                throw new FeeServiceException("전시대기 카테고리의 경우 리스팅광고를 설정할 수 없습니다.");
            }
        }
    }
}
