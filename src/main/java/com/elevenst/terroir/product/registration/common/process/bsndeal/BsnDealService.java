package com.elevenst.terroir.product.registration.common.process.bsndeal;

import com.elevenst.exception.TerroirException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;

public interface BsnDealService {

    public void setBsnDealClfInfo(ProductVO productVO) throws TerroirException;
}
