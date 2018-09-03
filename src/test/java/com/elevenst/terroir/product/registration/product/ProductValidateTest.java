package com.elevenst.terroir.product.registration.product;

import com.elevenst.terroir.product.registration.category.service.CategoryServiceImpl;
import com.elevenst.terroir.product.registration.category.vo.DpGnrlDispVO;
import com.elevenst.terroir.product.registration.option.vo.OptionVO;
import com.elevenst.terroir.product.registration.price.vo.PriceVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.service.ProductServiceImpl;
import com.elevenst.terroir.product.registration.product.validate.ProductValidate;
import com.elevenst.terroir.product.registration.product.vo.BaseVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class ProductValidateTest {

    @Autowired
    ProductValidate productValidate;

    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    ProductServiceImpl productService;

    @Rule
    public ExpectedException expectedExcetption = ExpectedException.none();

    @Test
    public void checkProductTypeTest() {

        ProductVO testVO1 = new ProductVO();
    }

    @Test
    public void checkMsgPuchPrc() {
        ProductVO productVO = new ProductVO();
        productVO.setOptionVO(new OptionVO());
        productVO.getOptionVO().setMrgnPolicyCd(null);
        try {
            String retVal = productValidate.checkMsgPuchPrc(productVO);
            Assert.assertSame(retVal, "마진정책은 반드시 선택하셔야 합니다.");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkMsgPuchPrc2() {
        ProductVO productVO = new ProductVO();
        productVO.setOptionVO(new OptionVO());
        productVO.setPriceVO(new PriceVO());
        productVO.getOptionVO().setMrgnPolicyCd(ProductConstDef.MRGN_POLICY_CD_PERCNT);
        try {
            String retVal = productValidate.checkMsgPuchPrc(productVO);
            Assert.assertSame(retVal, "마진율은 반드시 입력하셔야 합니다.");
            System.out.println(retVal);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkMsgPuchPrc3() {
        ProductVO productVO = new ProductVO();
        productVO.setOptionVO(new OptionVO());
        productVO.setPriceVO(new PriceVO());
        productVO.getOptionVO().setMrgnPolicyCd(ProductConstDef.MRGN_POLICY_CD_PERCNT);
        productVO.getOptionVO().setMrgnRt(new Double(1.222));
        try {
            String retVal = productValidate.checkMsgPuchPrc(productVO);
            Assert.assertSame(retVal, "마진율은 소수점 2자리까지 입력 가능합니다.");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkMsgPuchPrc4() {
        ProductVO productVO = new ProductVO();
        productVO.setOptionVO(new OptionVO());
        productVO.setPriceVO(new PriceVO());
        productVO.getOptionVO().setMrgnPolicyCd(ProductConstDef.MRGN_POLICY_CD_PERCNT);
        productVO.getPriceVO().setSelPrc(10000);
        productVO.getOptionVO().setMrgnRt(new Double(1.22));
        try {
            String retVal = productValidate.checkMsgPuchPrc(productVO);
            Assert.assertSame(retVal, "매입가 계산이 잘못되었습니다.");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkMsgPuchPrc4Success() {
        ProductVO productVO = new ProductVO();
        productVO.setOptionVO(new OptionVO());
        productVO.setPriceVO(new PriceVO());
        productVO.getOptionVO().setMrgnPolicyCd(ProductConstDef.MRGN_POLICY_CD_PERCNT);
        productVO.getPriceVO().setSelPrc(10000);
        productVO.getOptionVO().setPuchPrc(9880);
        productVO.getOptionVO().setMrgnAmt(120);
        productVO.getOptionVO().setMrgnRt(new Double(99));
        try {
            String retVal = productValidate.checkMsgPuchPrc(productVO);
            Assert.assertSame(retVal, "");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkMsgPuchPrc5() {
        ProductVO productVO = new ProductVO();
        productVO.setOptionVO(new OptionVO());
        productVO.setPriceVO(new PriceVO());
        productVO.getOptionVO().setMrgnPolicyCd(ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE);
        productVO.getPriceVO().setSelPrc(10000);
        productVO.getOptionVO().setPuchPrc(100);
        try {
            String retVal = productValidate.checkMsgPuchPrc(productVO);
            Assert.assertSame(retVal, "마진율 계산이 잘못되었습니다.");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkMsgPuchPrc6Success() {
        ProductVO productVO = new ProductVO();
        productVO.setOptionVO(new OptionVO());
        productVO.setPriceVO(new PriceVO());
        productVO.getOptionVO().setMrgnPolicyCd(ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE);
        productVO.getPriceVO().setSelPrc(10000);
        productVO.getOptionVO().setPuchPrc(100);
        productVO.getOptionVO().setMrgnRt(new Double(99));
        productVO.getOptionVO().setMrgnAmt(9900);
        try {
            String retVal = productValidate.checkMsgPuchPrc(productVO);
            Assert.assertSame(retVal, "");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void preProductChkTemplate() {
        ProductVO productVO = new ProductVO();
        BaseVO baseVO = new BaseVO();
        baseVO.setTemplateYn("Y");
        productVO.setBaseVO(baseVO);
        DpGnrlDispVO dpGnrlDispVO = new DpGnrlDispVO();
        productVO.setDpGnrlDispVO(dpGnrlDispVO);
        productValidate.preProductChkTemplate(productVO);
    }

    @Test
    public void isUsePrdGrpTypCd() {
        String stdPrdYn = "Y";
        long memNo = 38677742;
        boolean isResult = productValidate.isUsePrdGrpTypCd(stdPrdYn, memNo);
        System.out.println("isUsePrdGrpTypCd= "+isResult);
    }
}
