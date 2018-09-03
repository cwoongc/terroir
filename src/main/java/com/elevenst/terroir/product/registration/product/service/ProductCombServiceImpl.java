package com.elevenst.terroir.product.registration.product.service;

import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.mapper.ProductCombMapper;
import com.elevenst.terroir.product.registration.product.vo.ProductCombMstrVO;
import com.elevenst.terroir.product.registration.product.vo.ProductCombPrdVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductCombServiceImpl {

    @Autowired
    ProductCombMapper productCombMapper;

    public int postProcessAddedStock(ProductVO productVO) throws ProductException {
        ProductCombMstrVO param = new ProductCombMstrVO();
        param.setMstrObjNo(productVO.getPrdNo());
        param.setMstrObjType("P");
        param.setCombTypCd("02");

        List<ProductCombPrdVO> smartMatchingList = productCombMapper.getExistsMatchedPrd(param);
        int rstCnt = 0;
        if(!StringUtil.isEmpty(smartMatchingList) && productCombMapper.getCntMatchableStatPrd(param) > 0){
            ProductCombPrdVO combPrd = smartMatchingList.get(0);

            if("N".equals(combPrd.getMstrYn())){
                rstCnt = productCombMapper.deleteCombPrd(combPrd);
            }else if("Y".equals(combPrd.getUseYn())){
                ProductCombMstrVO mstr = new ProductCombMstrVO();
                mstr.setUseYn("N");
                mstr.setMstrObjNo(param.getMstrObjNo());
                mstr.setMstrObjType(param.getMstrObjType());
                mstr.setCombTypCd(param.getCombTypCd());
                mstr.setUpdateNo(productVO.getUpdateNo());

                rstCnt = productCombMapper.updateUseYnCombMstr(mstr);
            }else{
            }
        }

        return rstCnt;
    }
}
