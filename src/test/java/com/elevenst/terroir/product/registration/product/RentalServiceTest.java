package com.elevenst.terroir.product.registration.product;


import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.terroir.product.registration.common.process.SwitchableServiceFacotiry;
import com.elevenst.terroir.product.registration.common.process.prdtyp.ProductTypeService;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.product.vo.RentalVO;
import org.junit.Before;
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
public class RentalServiceTest {


    ProductTypeService rentalService;
    @Rule
    public ExpectedException expectedExcetption = ExpectedException.none();

    @Before
    public void setProductTypeService(){
        rentalService = SwitchableServiceFacotiry.createPrdocutTypeService(GroupCodeUtil.fromString(PrdTypCd.class, "26"));
    }

    @Test
    public void checkIsAuthTypeSpecialRentalSeller() {

//        System.out.println("crewmate isRentalSeller : "+rentalService.isAuthTypeSpecialRentalSeller(10000276));
    }

    @Test
    public void CheckUpdateRntlPrd() {
        ProductVO productVO = new ProductVO();
        productVO.setPrdNo(999999999);
        productVO.setCreateNo(12345);
        productVO.setUpdateNo(12345);

        RentalVO rentalVO = new RentalVO();

        rentalVO.setCstTermCd("01");
        rentalVO.setFreeInstYn("Y");
        rentalVO.setCoCardBnft("제휴카드 혜택");
        rentalVO.setInstCstDesc("tjfclql tjfaud");
        rentalVO.setFrgftDesc("사은품 설명");
        rentalVO.setEtcInfo("기타 설명 설명");
        rentalVO.setUseSpecialRentalYn("Y");
        productVO.setRentalVO(rentalVO);

//        rentalService.updateRntlPrd(productVO);
    }

    @Test
    public void CheckInsertRntlPrd() {
        ProductVO productVO = new ProductVO();
        productVO.setPrdNo(999999999);
        productVO.setCreateNo(12345);
        productVO.setUpdateNo(12345);

        RentalVO rentalVO = new RentalVO();

        rentalVO.setCstTermCd("01");
        rentalVO.setFreeInstYn("Y");
        //rentalVO.setCoCardBnft("제휴카드 혜택");
        rentalVO.setInstCstDesc("");
        rentalVO.setFrgftDesc("사은품 설명");
        rentalVO.setEtcInfo("기타 설명 설명");
        rentalVO.setUseSpecialRentalYn("Y");
        productVO.setRentalVO(rentalVO);

//        rentalService.insertRntlPrd(productVO);
    }

    @Test
    public void CheckUpdateRntlCstUnt() {
        ProductVO productVO = new ProductVO();
        productVO.setPrdNo(999999999);
        productVO.setCreateNo(12345);
        productVO.setUpdateNo(12345);

        RentalVO rentalVO = new RentalVO();

        rentalVO.setCstTermCd("03");
        rentalVO.setFreeInstYn("Y");
        //rentalVO.setCoCardBnft("제휴카드 혜택");
        rentalVO.setInstCstDesc("");
        rentalVO.setFrgftDesc("사은품 설명");
        rentalVO.setEtcInfo("기타 설명 설명");
        rentalVO.setUseSpecialRentalYn("Y");
        productVO.setRentalVO(rentalVO);

//        rentalService.updateRntlCstUnt(productVO);
    }



}
