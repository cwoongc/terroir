package com.elevenst.terroir.product.registration.product.mapper;

import com.elevenst.terroir.product.registration.product.entity.PdPrdGrpMapp;
import com.elevenst.terroir.product.registration.product.vo.ProductGroupVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ProductGroupMapper {
    long getProductGroupNo(long prdNo);

    ProductGroupVO getGroupInfo(long prdGrpNo);

    void updatePdPrdGrpMapp(PdPrdGrpMapp pdPrdGrpMapp);

    ProductGroupVO productGroupBasePrdInfo(ProductGroupVO prdBo);

    String getProductCartUse(long prdNo);
}
