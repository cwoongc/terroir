package com.elevenst.terroir.product.registration.registration.controller;

import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.common.util.TnsUtil;
import com.elevenst.exception.TerroirException;
import com.elevenst.terroir.product.registration.benefit.service.ProductBenefitServiceImpl;
import com.elevenst.terroir.product.registration.catalog.vo.CtlgVO;
import com.elevenst.terroir.product.registration.category.service.CategoryServiceImpl;
import com.elevenst.terroir.product.registration.cert.service.CertService;
import com.elevenst.terroir.product.registration.common.process.SwitchableServiceFacotiry;
import com.elevenst.terroir.product.registration.common.process.prdtyp.ProductTypeService;
import com.elevenst.terroir.product.registration.customer.service.CustomerServiceImpl;
import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.deal.service.DealServiceImpl;
import com.elevenst.terroir.product.registration.delivery.service.DeliveryServiceImpl;
import com.elevenst.terroir.product.registration.delivery.vo.ClaimAddressInfoVO;
import com.elevenst.terroir.product.registration.desc.code.PrdDescTypCd;
import com.elevenst.terroir.product.registration.desc.service.ProductDescServiceImpl;
import com.elevenst.terroir.product.registration.desc.vo.ProductDescVO;
import com.elevenst.terroir.product.registration.etc.vo.PrdEtcVO;
import com.elevenst.terroir.product.registration.fee.service.FeeServiceImpl;
import com.elevenst.terroir.product.registration.fee.vo.FeeVO;
import com.elevenst.terroir.product.registration.image.service.ProductImageServiceImpl;
import com.elevenst.terroir.product.registration.option.service.OptionServiceImpl;
import com.elevenst.terroir.product.registration.option.vo.OptionVO;
import com.elevenst.terroir.product.registration.price.service.PriceServiceImpl;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.service.AdditionalProductServiceImpl;
import com.elevenst.terroir.product.registration.product.service.ProductServiceImpl;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.registration.service.RegistrationServiceImpl;
import com.elevenst.terroir.product.registration.seller.service.SellerServiceImpl;
import com.elevenst.terroir.product.registration.seller.vo.SellerVO;
import com.elevenst.terroir.product.registration.wms.service.WmsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skt.tmall.auth.Auth;
import skt.tmall.auth.AuthCode;
import skt.tmall.auth.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value="/registration", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RegistrationController {

    @Autowired
    private RegistrationServiceImpl registrationService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private SellerServiceImpl sellerService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private PriceServiceImpl priceService;

    @Autowired
    private DeliveryServiceImpl deliveryService;

    @Autowired
    private AdditionalProductServiceImpl additionalProductService;

    @Autowired
    private ProductDescServiceImpl descService;

    @Autowired
    private CertService certService;

    @Autowired
    private ProductBenefitServiceImpl benefitService;

    @Autowired
    private WmsServiceImpl wmsService;

    @Autowired
    private FeeServiceImpl feeService;

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private ProductImageServiceImpl productImageService;

    @Autowired
    private DealServiceImpl dealService;

    @Autowired
    private OptionServiceImpl optionService;

    /**
     * 상품등록 API
     * @param productVO 상품
     * @return Status code 200
     */
    @PostMapping("/product")
    public ResponseEntity<Long> registerProduct(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductVO productVO) {
        this.convertRegistrationInfo(request, response, productVO);
        this.convertTestBaseInfo(productVO);
        ProductVO preProductVO = this.convertRegInfo(productVO);
        registrationService.registrationProduct(preProductVO, productVO);
        return new ResponseEntity<>(productVO.getPrdNo(), HttpStatus.OK);
    }

    private ProductVO convertRegInfo(ProductVO productVO) throws TerroirException{
        ProductVO preProductVO = null;
        if(productVO.getPrdNo() > 0) {
            productVO.setUpdate(true);
            preProductVO = productService.getProduct(productVO.getPrdNo());
            categoryService.getCategoryInfo(preProductVO);
        }else{
            productVO.setUpdate(false);
        }
        if(productVO.getReRegPrdNo() > 1){
            productVO.setPrdCopyYn("Y");
        }else{
            productVO.setPrdCopyYn("N");
        }
        categoryService.getCategoryInfo(productVO);

        productService.convertRegInfo(preProductVO, productVO);
        sellerService.convertRegInfo(preProductVO, productVO);
        categoryService.convertRegInfo(preProductVO, productVO);
        priceService.convertRegInfo(preProductVO, productVO);
        optionService.convertRegInfo(preProductVO, productVO);
        deliveryService.convertRegInfo(preProductVO, productVO);
        additionalProductService.convertRegInfo(preProductVO, productVO);
        descService.convertRegInfo(preProductVO, productVO);
        certService.convertRegInfo(preProductVO, productVO);
        benefitService.convertRegInfo(preProductVO, productVO);
        dealService.convertRegInfo(preProductVO, productVO);
        wmsService.convertRegInfo(preProductVO, productVO);
        feeService.convertRegInfo(preProductVO, productVO);
        productImageService.convertRegInfo(preProductVO, productVO);

        ProductTypeService productTypeService = SwitchableServiceFacotiry.createPrdocutTypeService(GroupCodeUtil.fromString(PrdTypCd.class, productVO.getBaseVO().getPrdTypCd()));
        if(productTypeService != null) productTypeService.setProductTypeInfo(preProductVO, productVO);

        return preProductVO;
    }

    private void convertRegistrationInfo(HttpServletRequest request, HttpServletResponse response, ProductVO productVO) throws TerroirException{
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        productVO.setAuth(auth);

        this.convertRegMemberInfo(productVO);

        productVO.getBaseVO().setSellerPcId(TnsUtil.getPCID(request));
        productVO.getBaseVO().setCreateIp(request.getRemoteAddr());
        productVO.getBaseVO().setUpdateIp(request.getRemoteAddr());
        productVO.setChannel(ProductConstDef.SELLEROFFICE_TYPE);

    }

    private ProductVO convertRegMemberInfo(ProductVO productVO) throws TerroirException{

        Auth auth = productVO.getAuth();
        if(productVO.getMemberVO() == null){
            productVO.setMemberVO(new MemberVO());
        }
        productVO.getMemberVO().setMemNO(auth.getMemberNumber());
        productVO.getMemberVO().setMemID(auth.getMemberId());
        productVO.getMemberVO().setMemTypCD(auth.getMemberType());
        productVO.getMemberVO().setBsnDealClf(auth.getBsnDealClf());
        productVO.getMemberVO().setCertCardYn(auth.getCertCardYN());
        productVO.getMemberVO().setFrSeller(customerService.isFrSeller(auth.getMemberNumber()));
        productVO.setCreateNo(auth.getMemberNumber());
        productVO.setUpdateNo(auth.getMemberNumber());

        customerService.getMemberInfo(productVO);

        if(ProductConstDef.PRD_MEM_TYPE_GLOBAL.equals(auth.getMemberType())){
            productVO.getMemberVO().setGlobalSellerYn("Y");
            productVO.getBaseVO().setAbrdBrandYn("Y");
        }else{
            productVO.getBaseVO().setAbrdBrandYn("N");
        }

        if(!ProductConstDef.PRD_MEM_TYPE_INTEGRATION.equals(productVO.getMemberVO().getMemTypCD())){
            productVO.setSelMnbdNo(auth.getMemberNumber());
        }

        return productVO;
    }

    private void convertTestBaseInfo(ProductVO productVO) throws ProductException{
        String tmp = "";
        tmp = productVO.getBaseVO().getSelBgnDy().replaceAll("[^0-9]", "");
        productVO.getBaseVO().setSelBgnDy(tmp);
        tmp = productVO.getBaseVO().getSelEndDy().replaceAll("[^0-9]", "");
        productVO.getBaseVO().setSelEndDy(tmp);

        if(StringUtil.isEmpty(productVO.getBaseVO().getRtngdDlvCd())){
            productVO.getBaseVO().setRtngdDlvCd(ProductConstDef.RTNGD_DLV_CD_RT);
        }

        if(productVO.getCtlgVO() == null) productVO.setCtlgVO(new CtlgVO());
        if(productVO.getPrdModelVO() != null){
            long ctlgNo = productVO.getPrdModelVO().getModelNo();
            productVO.getCtlgVO().setCtlgNo(ctlgNo);
            //productVO.getPrdModelVO().setModelNo(ctlgNo);
            productVO.getPrdModelVO().setCtlgNo(ctlgNo);
        }

        if(!StringUtil.isContains(ProductConstDef.DEFAULT_VALUE_Y_N, productVO.getBaseVO().getVisitDlvYn())){
            productVO.getBaseVO().setVisitDlvYn("N");
        }

        if(productVO.getBaseVO().getSelLimitTerm() == 0){
            productVO.getBaseVO().setSelLimitTerm(productVO.getBaseVO().getTownSelLmtDy());
        }

    }

}


