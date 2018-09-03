package com.elevenst.terroir.product.registration.common.process.prdtyp;

import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;

public interface ProductTypeService {

    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException;

    public void setProductTypeInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException;

    public void insertProductTypeProcess(ProductVO productVO) throws ProductException;

    public void updateProductTypeProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException;
}
