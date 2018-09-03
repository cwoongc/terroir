package com.elevenst.terroir.product.registration.customer.service;

import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

@Slf4j
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("local")
@SpringBootTest
public class CustomerServiceImplTest {

    @Autowired
    CustomerServiceImpl customerService;

    @Test
    public void testGetMemberInfo() throws SQLException {
        ProductVO productVO = new ProductVO();
        MemberVO memberVO = new MemberVO();
        memberVO.setMemNO(10000276);
        productVO.setMemberVO(memberVO);
        customerService.getMemberInfo(productVO);

        Assert.assertEquals(memberVO.getMemNO(), productVO.getMemberVO().getMemNO());
    }

//    @Test
//    public void testGetMemberInfo() throws SQLException {
//        MemberVO memberVO = new MemberVO();
//        memberVO.setMemNO(10000276);
//        MemberVO resultVO = customerService.getMemberInfo(memberVO);
//
//        Assert.assertEquals(memberVO.getMemNO(), resultVO.getMemNO());
//    }

    @Test
    public void getRepSellerNo() {
        long memNo = 10000276;
        long retMemNo = customerService.getRepSellerNo(memNo);
        Assert.assertNotNull(retMemNo);
    }

    @Test
    public void getProductRegInfobySuplMemNo() {
        long memNo = 10969574;
        long repSellerNo = 1000;
        customerService.getProductRegInfobySuplMemNo(memNo, repSellerNo);

    }

    @Test
    public void isFrSeller() {
        long memNo = 10000276;
        customerService.isFrSeller(memNo);
    }

    @Test
    public void getExAmt() {
        long memNo = 10000276;
        customerService.getExAmt(memNo);
    }

    @Test
    public void getMemberInfo(){
        long memNo = 10000276;
        MemberVO memberVO = new MemberVO();
        memberVO.setMemNO(memNo);
        ProductVO productVO = new ProductVO();
        productVO.setMemberVO(memberVO);

        customerService.getMemberInfo(productVO);
        log.info(productVO.getMemberVO().getMemTypCD());
    }
}
