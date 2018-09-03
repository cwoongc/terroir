package com.elevenst.terroir.product.registration.common.process.prdtyp;

import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;

public class BranchMartTypeServiceImpl implements ProductTypeService{
    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException {

    }

    @Override
    public void setProductTypeInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException {
        this.setBranchMartProductInfo(productVO);
    }

    @Override
    public void insertProductTypeProcess(ProductVO productVO) throws ProductException {

    }

    @Override
    public void updateProductTypeProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException {

    }

    private void setBranchMartProductInfo(ProductVO productVO) throws ProductException{
        productVO.getBaseVO().setLowPrcCompExYn("N");
    }
}
