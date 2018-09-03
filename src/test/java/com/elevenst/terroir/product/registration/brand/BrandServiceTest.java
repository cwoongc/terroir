package com.elevenst.terroir.product.registration.brand;


import com.elevenst.terroir.product.registration.brand.service.BrandServiceImpl;
import com.elevenst.terroir.product.registration.brand.vo.BrandVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class BrandServiceTest {

    @Autowired
    BrandServiceImpl brandService;
    @Test
    public void getBrandNameList(){
        HashMap param = new HashMap();
        param.put("brandName","나이키");
        param.put("start","1");
        param.put("limit","10");
        param.put("incheck","N");

        List<BrandVO> brandVOList = brandService.getBrandList(param);
        for(BrandVO brandVO : brandVOList){
            System.out.println("getBrandNm :"+brandVO.getBrandNm());
            System.out.println("getBrandCd :"+brandVO.getBrandCd());
            System.out.println("getBrdCd :"+brandVO.getBrdCd());
            System.out.println("getTotalCount :"+brandVO.getTotalCount());
        }
    }
}
