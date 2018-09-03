package com.elevenst.terroir.product.registration.brand.service;

import com.elevenst.terroir.product.registration.brand.mapper.BrandMapper;
import com.elevenst.terroir.product.registration.brand.vo.BrandVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class BrandServiceImpl {
    @Autowired
    BrandMapper brandMapper;

    public List<BrandVO> getBrandList(HashMap param){
        return brandMapper.getBrandList(param);
    }
}
