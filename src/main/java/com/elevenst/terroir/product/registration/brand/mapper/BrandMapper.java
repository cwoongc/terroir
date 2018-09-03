package com.elevenst.terroir.product.registration.brand.mapper;

import com.elevenst.terroir.product.registration.brand.vo.BrandVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface BrandMapper {
    public List<BrandVO> getBrandList(HashMap param);
}
