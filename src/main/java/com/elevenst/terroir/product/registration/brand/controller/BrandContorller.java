package com.elevenst.terroir.product.registration.brand.controller;


import com.elevenst.terroir.product.registration.brand.service.BrandServiceImpl;
import com.elevenst.terroir.product.registration.brand.vo.BrandVO;
import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value="/brand", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BrandContorller {

    @Autowired
    BrandServiceImpl brandService;

    @GetMapping("/search")
    @ResponseBody
    public List<BrandVO> getBrandList(@RequestParam(name="brandName", defaultValue = "나이키") String brandName,
                                      @RequestParam(name="start", defaultValue = "0") long start,
                                      @RequestParam(name="limit", defaultValue = "20") long limit,
                                      @RequestParam(name="incheck", defaultValue = "N") String incheck
                                      ) throws Exception {

        HashMap param = new HashMap();

        param.put("incheck", incheck);
        param.put("brandName", brandName);
        param.put("start", start);
        param.put("limit", limit+start);

        List<BrandVO> brandVOList = brandService.getBrandList(param);

        return brandVOList;


    }
}
