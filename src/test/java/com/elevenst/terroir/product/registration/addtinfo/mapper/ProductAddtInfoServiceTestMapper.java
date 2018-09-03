package com.elevenst.terroir.product.registration.addtinfo.mapper;

import com.elevenst.terroir.product.registration.addtinfo.entity.PdPrdAddtInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductAddtInfoServiceTestMapper {

    int insertProductAddtInfo(PdPrdAddtInfo pdPrdAddtInfo);

    int updateProductAddtInfo(PdPrdAddtInfo pdPrdAddtInfo);

    List<PdPrdAddtInfo> getProductAddtInfoList(long prdNo);

    int deleteProductAddtInfo(long prdNo);

    int deleteProductAddtInfoWithClfCd(PdPrdAddtInfo pdPrdAddtInfo);

}
