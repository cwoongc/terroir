package com.elevenst.terroir.product.registration.common.process.prdtyp;

import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;

public class BpTravelTypeServiceImpl implements ProductTypeService{

    @Autowired
    MultiTypeServiceImpl multiTypeService;

    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException {
        multiTypeService.checkMultiTourTypeInfo(productVO);
    }

    @Override
    public void setProductTypeInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException {

    }

    @Override
    public void insertProductTypeProcess(ProductVO productVO) throws ProductException {

    }

    @Override
    public void updateProductTypeProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException {

    }
}
