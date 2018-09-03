package com.elevenst.terroir.product.registration.product.mapper;

import com.elevenst.terroir.product.registration.product.entity.PdPrdTour;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ProductVerticalMapper {

    void insertTour(PdPrdTour pdPrdTour);
    void insertTourHist(long prdNo);
    void updateTourProductForSO(PdPrdTour pdPrdTour);
    PdPrdTour getProductTourDetail(long prdNo);
}
