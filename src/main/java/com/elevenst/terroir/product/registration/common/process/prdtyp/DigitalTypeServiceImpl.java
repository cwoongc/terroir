package com.elevenst.terroir.product.registration.common.process.prdtyp;

import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;

public class DigitalTypeServiceImpl implements ProductTypeService{
    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException {

    }

    @Override
    public void setProductTypeInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException {
        this.checkDigitalType(productVO);
    }

    @Override
    public void insertProductTypeProcess(ProductVO productVO) throws ProductException {

    }

    @Override
    public void updateProductTypeProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException {

    }

    private void checkDigitalType(ProductVO productVO) throws ProductException {
        if (StringUtil.isEmpty(productVO.getPrdEtcVO().getPrdDetailOutLink())) throw new ProductException("제휴사 상품 URL은 반드시 입력하셔야 합니다.");
    }
}
