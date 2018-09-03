package com.elevenst.terroir.product.registration.product.mapper;


import com.elevenst.terroir.product.registration.product.entity.PdRntlPrd;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RentalMapper {

    public void insertRntlPrd(PdRntlPrd pdRntlPrd);

    public void insertRntlPrdHist(long prdNo);

    public void updateRntlPrd(PdRntlPrd pdRntlPrd);

    void updateRntlCstUnt(PdRntlPrd pdRntlPrd);
}
