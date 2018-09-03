package com.elevenst.terroir.product.registration.product;


import com.elevenst.terroir.product.registration.product.service.ProductGroupServiceImpl;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class ProductGroupTest {
    @Autowired
    ProductGroupServiceImpl productGroupService;

    @Test
    public void dropGroupNotValidPrdTest(){
        ProductVO productVO = new ProductVO();
        productVO.setPrdNo(1245253740);
        productVO.setCreateNo(0);
        productVO.setUpdateNo(0);
        productGroupService.dropGroupNotValidPrd(productVO);
    }
}
