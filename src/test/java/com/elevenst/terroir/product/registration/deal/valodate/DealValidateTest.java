package com.elevenst.terroir.product.registration.deal.valodate;

import com.elevenst.terroir.product.registration.deal.validate.DealValidate;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("local")
@SpringBootTest
public class DealValidateTest {
    @Autowired
    DealValidate dealValidate;

    @Rule
    public ExpectedException expectedExcetption = ExpectedException.none();


    @Test
    public void checkConfirmDealByChanged_TRUE() {
        ProductVO productVO = new ProductVO();
        productVO.setPrdNo(1245253714);
        productVO.setDispCtgrNo(1008556);

        Boolean result = false;

        try {
            result = dealValidate.checkConfirmDealByChanged(productVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkConfirmDealByChanged_FALSE() {
        ProductVO productVO = new ProductVO();
        productVO.setPrdNo(1245253714);

        Boolean result = false;
        String msg = "[com.elevenst.exception.Terroir][com.elevenst.terroir.product.registration.product.exception.Product]쇼킹딜 참여 신청 상품은 카테고리를 변경할 수 없습니다.";

        expectedExcetption.expectMessage(msg);

        try {
            result = dealValidate.checkConfirmDealByChanged(productVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
