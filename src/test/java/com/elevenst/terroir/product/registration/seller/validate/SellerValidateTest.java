package com.elevenst.terroir.product.registration.seller.validate;

import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.seller.vo.SellerAuthVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class SellerValidateTest {

    @Autowired
    SellerValidate sellerValidate;

    @Test
    public void checkSelMnbdNckNmSeqMethod() {

        SellerAuthVO vo = new SellerAuthVO();
        vo.setSelMnbdNo(10000276);
        vo.setSelMnbdNckNmSeq(161450);

        //Assert.assertEquals(true, sellerValidate.checkSelMnbdNckNmSeq(vo));

    }

    @Test
    public void isOnlyUsableSetTypCd(){
        MemberVO memberVO = new MemberVO();
        memberVO.setBsnDealClf("02");
        memberVO.setWmsUseClfCd("03");

        boolean result = sellerValidate.isOnlyUsableSetTypCd(memberVO);

        Assert.assertTrue(result == true);

    }

    @Test
    public void checkMobileSeller_memNo(){
        long memNo = 10000276;
        boolean result = false;

        try {
            result = sellerValidate.checkMobileSeller(memNo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(result == true);
    }

    @Test
    public void checkMobileSeller_memId(){
        String memId = "crewmate";
        boolean result = false;

        try {
            result = sellerValidate.checkMobileSeller(memId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(result == true);
    }

    @Test
    public void isDirectBuyingMark() {
        Boolean result = false;
        result = sellerValidate.isDirectBuyingMark(22354957);

        Assert.assertTrue(result);
    }

    @Test
    public void isCertStockCdSeller_true () {
        Boolean result = false;
        result = sellerValidate.isCertStockCdSeller(10007228);

        Assert.assertTrue(result);
    }

    @Test
    public void isCertStockCdSeller_false () {
        Boolean result = false;
        result = sellerValidate.isCertStockCdSeller(10002222);

        Assert.assertFalse(result);
    }
}
