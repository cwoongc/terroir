package com.elevenst.terroir.product.registration.etc.validate;

import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.etc.exception.EtcValidateException;
import com.elevenst.terroir.product.registration.etc.vo.PrdEtcVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.springframework.stereotype.Component;

@Component
public class EtcValidate {

    public void setEtcProductInfo(ProductVO productVO) throws EtcValidateException {

        if(productVO.getPrdEtcVO() == null) return;

        PrdEtcVO prdEtcVO = productVO.getPrdEtcVO();

        if(ProductConstDef.DP_DISP_CTGR_BOOK_CLF_CD_BOOK.equals(StringUtil.nvl(prdEtcVO.getBookClfCd())) ||
                ProductConstDef.DP_DISP_CTGR_BOOK_CLF_CD_FOREIGN_BOOK.equals(StringUtil.nvl(prdEtcVO.getBookClfCd()))){
            if(prdEtcVO.getIsbn13Cd().length() > 13) {
                throw new EtcValidateException("ISBN-13은 13자까지 입력 가능합니다.");
            }else if(prdEtcVO.getIsbn10Cd().length() > 10) {
                throw new EtcValidateException("ISBN-10은 10자까지 입력 가능합니다.");
            }

        }
    }
}
