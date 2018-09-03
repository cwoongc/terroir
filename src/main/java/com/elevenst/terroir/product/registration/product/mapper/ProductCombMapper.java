package com.elevenst.terroir.product.registration.product.mapper;

import com.elevenst.terroir.product.registration.product.vo.ProductCombMstrVO;
import com.elevenst.terroir.product.registration.product.vo.ProductCombPrdVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProductCombMapper {

    List<ProductCombPrdVO> getExistsMatchedPrd(ProductCombMstrVO productCombMstrVO);
    int getCntMatchableStatPrd(ProductCombMstrVO productCombMstrVO);
    int deleteCombPrd(ProductCombPrdVO productCombPrdVO);
    int updateUseYnCombMstr(ProductCombMstrVO productCombMstrVO);
}
