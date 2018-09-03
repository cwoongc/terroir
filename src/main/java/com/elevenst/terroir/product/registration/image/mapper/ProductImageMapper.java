package com.elevenst.terroir.product.registration.image.mapper;

import com.elevenst.terroir.product.registration.image.entity.PdPrdImage;
import com.elevenst.terroir.product.registration.image.entity.PdPrdImageChgHist;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 이미지 관련
 * PD_PRD_IMAGE
 * PD_PRD_IMAGE_HIST
 */
@Mapper
@Component
public interface ProductImageMapper {

    PdPrdImage getProductImage(long prdNo);

    void insertProductImage(PdPrdImage pdPrdImage);

    void insertProductImageChgHist(PdPrdImageChgHist pdPrdImageChgHist);

    void updateProductImage(PdPrdImage pdPrdImage);

    void insertProductDeleteImg(PdPrdImage pdPrdImage);

}
