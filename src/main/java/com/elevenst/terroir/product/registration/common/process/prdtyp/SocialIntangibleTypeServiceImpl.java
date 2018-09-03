package com.elevenst.terroir.product.registration.common.process.prdtyp;

import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;

public class SocialIntangibleTypeServiceImpl implements ProductTypeService{

    @Autowired
    MultiTypeServiceImpl multiTypeService;

    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException {

    }

    @Override
    public void setProductTypeInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException {
        multiTypeService.checkMultiSocialTypeInfo(productVO);
    }

    @Override
    public void insertProductTypeProcess(ProductVO productVO) throws ProductException {

    }

    @Override
    public void updateProductTypeProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException {

    }
}
