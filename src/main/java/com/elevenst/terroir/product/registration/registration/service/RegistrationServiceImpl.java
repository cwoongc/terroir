package com.elevenst.terroir.product.registration.registration.service;

import com.elevenst.common.util.DateUtil;
import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.exception.TerroirException;
import com.elevenst.terroir.product.registration.addtinfo.service.ProductAddtInfoServiceImpl;
import com.elevenst.terroir.product.registration.benefit.service.ProductBenefitServiceImpl;
import com.elevenst.terroir.product.registration.catalog.service.CatalogServiceImpl;
import com.elevenst.terroir.product.registration.category.service.CategoryServiceImpl;
import com.elevenst.terroir.product.registration.cert.service.CertService;
import com.elevenst.terroir.product.registration.common.process.SwitchableServiceFacotiry;
import com.elevenst.terroir.product.registration.common.process.channel.ChannelService;
import com.elevenst.terroir.product.registration.common.process.prdtyp.ProductTypeService;
import com.elevenst.terroir.product.registration.coordi_prd.service.CoordiProductServiceImpl;
import com.elevenst.terroir.product.registration.ctgrattr.service.CtgrAttrServiceImpl;
import com.elevenst.terroir.product.registration.customer.service.CustomerServiceImpl;
import com.elevenst.terroir.product.registration.delivery.service.DeliveryServiceImpl;
import com.elevenst.terroir.product.registration.delivery.service.SpecificBaseDeliveryServiceImpl;
import com.elevenst.terroir.product.registration.desc.service.ProductDescServiceImpl;
import com.elevenst.terroir.product.registration.etc.service.EtcServiceImpl;
import com.elevenst.terroir.product.registration.fee.service.FeeServiceImpl;
import com.elevenst.terroir.product.registration.image.service.ProductImageServiceImpl;
import com.elevenst.terroir.product.registration.lifeplus.service.LifePlusServiceImpl;
import com.elevenst.terroir.product.registration.mobile.service.MobileServiceImpl;
import com.elevenst.terroir.product.registration.option.service.OptionServiceImpl;
import com.elevenst.terroir.product.registration.pln_prd.service.PlanProductServiceImpl;
import com.elevenst.terroir.product.registration.price.service.PriceServiceImpl;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.service.AdditionalProductServiceImpl;
import com.elevenst.terroir.product.registration.product.service.ProductGroupServiceImpl;
import com.elevenst.terroir.product.registration.product.service.ProductServiceImpl;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.service.SellerServiceImpl;
import com.elevenst.terroir.product.registration.template.service.TemplateServiceImpl;
import com.elevenst.terroir.product.registration.wms.service.WmsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class RegistrationServiceImpl{

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    SellerServiceImpl sellerService;

    @Autowired
    PriceServiceImpl priceService;

    @Autowired
    OptionServiceImpl optionService;

    @Autowired
    EtcServiceImpl etcService;

    @Autowired
    ProductDescServiceImpl productDescService;

    @Autowired
    WmsServiceImpl wmsService;

    @Autowired
    FeeServiceImpl feeService;

    @Autowired
    MobileServiceImpl mobileService;

    @Autowired
    CertService certService;

    @Autowired
    DeliveryServiceImpl deliveryService;

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    ProductBenefitServiceImpl benefitService;

    @Autowired
    PlanProductServiceImpl planProductService;

    @Autowired
    SpecificBaseDeliveryServiceImpl specificBaseDeliveryService;

    @Autowired
    AdditionalProductServiceImpl additionalProductService;

    @Autowired
    CatalogServiceImpl catalogService;

    @Autowired
    LifePlusServiceImpl lifePlusService;

    @Autowired
    TemplateServiceImpl templateService;

    @Autowired
    CoordiProductServiceImpl coordiProductService;

    @Autowired
    ProductAddtInfoServiceImpl productAddtInfoService;

    @Autowired
    ProductImageServiceImpl productImageService;

    @Autowired
    ProductGroupServiceImpl productGroupService;

    @Autowired
    CtgrAttrServiceImpl ctgrAttrService;

    ProductTypeService productTypeService = null;

    public void registrationProduct(ProductVO preProductVO, ProductVO productVO) throws TerroirException{
        this.setProductDefautlInfo(preProductVO, productVO);
        this.setRegistrationInfo(preProductVO, productVO);
        this.insertUpdateProductProcess(preProductVO, productVO);

    }

    private void setProductDefautlInfo(ProductVO preProductVO, ProductVO productVO) throws TerroirException{

        String histAplBgnDt = DateUtil.getFormatString("yyyyMMddHHmmss");
        productVO.setHistAplBgnDt(histAplBgnDt);
        productVO.setUpdateDt(histAplBgnDt);
        customerService.getMemberInfo(productVO);
        this.setNotSupportStdPrdInfo(preProductVO, productVO);
    }

    private void setNotSupportStdPrdInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException {
        if(productVO.isUpdate()){
            priceService.getPdPrdPluDscInfoList(productVO);
            benefitService.getCustomerPrdInfo(productVO);
        }
    }

    /**
     * 상품 등록/수정 도메인 영역의 대표 함수 호출
     * @param productVO
     * @throws TerroirException
     */
    private void setRegistrationInfo(ProductVO preProductVO, ProductVO productVO) throws TerroirException{

        ChannelService channelService = SwitchableServiceFacotiry.createChannelService(productVO.getChannel());
        channelService.setChannelInfo(productVO);

        productService.setProductInfoProcess(preProductVO, productVO);
        sellerService.setSellerInfoProcess(preProductVO, productVO);
        categoryService.setCategoryInfoProcess(preProductVO, productVO);

        certService.setCertInfoProcess(preProductVO, productVO);
        deliveryService.setDeliveryInfoProcess(preProductVO, productVO);
        etcService.setEtcInfoProcess(preProductVO, productVO);

        optionService.setOptionInfoProcess(preProductVO, productVO);

        productTypeService = SwitchableServiceFacotiry.createPrdocutTypeService(GroupCodeUtil.fromString(PrdTypCd.class, productVO.getBaseVO().getPrdTypCd()));
        if(productTypeService != null) productTypeService.setProductTypeInfo(preProductVO, productVO);

        additionalProductService.setAdditionInfoProcess(preProductVO, productVO);

        templateService.setTemplateValidateInfoProcess(preProductVO, productVO);

        mobileService.setMobileInfoProcess(preProductVO, productVO);

        priceService.setPriceInfoProcess(preProductVO, productVO);

        ctgrAttrService.setCtgrAttrInfoProcess(preProductVO, productVO);

        coordiProductService.setCoodiProductInfoProcess(preProductVO, productVO);

        catalogService.setCatalogInfoProcess(preProductVO, productVO);

    }

    /**
     * 상품 등록/수정의 각 도메인 영역의 실제 DML 대표 함수 호출
     * @param productVO
     * @param productVO
     * @throws TerroirException
     */
    @Transactional
    public void insertUpdateProductProcess(ProductVO preProductVO, ProductVO productVO) throws TerroirException{

        if(productVO.isUpdate()){
            wmsService.updateWmsInfoProcess(productVO);
            benefitService.upateBenefitInfoProcess(preProductVO, productVO);
            optionService.updateOptionInfoProcess(preProductVO, productVO);

            planProductService.updatePlanProductInfo(productVO);
            productDescService.updateProductDescInfo(preProductVO, productVO);
            priceService.updatePriceInfoProcess(preProductVO, productVO);
            specificBaseDeliveryService.updateSpecificInfoProcess(preProductVO, productVO);
            deliveryService.updateDeliveryInfoProcess(preProductVO, productVO);
            productService.updateProductProcess(preProductVO, productVO);
            coordiProductService.updateCoordiInfoProcess(preProductVO, productVO);
            additionalProductService.updateAdditionalProductProcess(preProductVO, productVO);
            ctgrAttrService.mergeCtgrAttrInfoProcess(preProductVO, productVO);
            categoryService.updateCategoryProcess(preProductVO, productVO);
            certService.updateCertInfoProcess(productVO);
            mobileService.updateMobileProcess(productVO);
            feeService.updateSeFeeItmInfoProcess(preProductVO, productVO);
            productAddtInfoService.updateAddtInfoProcess(preProductVO, productVO);
            lifePlusService.mergeLifePlusInfoProcess(productVO);

            if(productTypeService != null) productTypeService.updateProductTypeProcess(preProductVO, productVO);
        }else{
            productVO.setPrdNo(productService.getNewPrdNo());

            optionService.insertOptionInfoProcess(productVO);
            productDescService.insertProductDescInfo(productVO);
            priceService.insertPriceInfoProcess(productVO);
            planProductService.insertPlanInfoProcess(productVO);
            benefitService.insertBenefitInfoProcess(productVO);
            specificBaseDeliveryService.insertSpecificInfoProcess(productVO);
            deliveryService.insertDeliveryInfoProcess(productVO);
            additionalProductService.insertAdditionalProductProcess(productVO);
            productService.insertProductProcess(productVO);
            coordiProductService.insertCoordiInfoProcess(productVO);
            ctgrAttrService.mergeCtgrAttrInfoProcess(preProductVO, productVO);
            categoryService.insertCategoryProcess(productVO);
            certService.insertCertInfoProcess(productVO);
            mobileService.insertMobileProcess(productVO);
            feeService.insertSeFeeItmInfoProcess(productVO);
            productAddtInfoService.insertAddtInfoProcess(productVO);
            wmsService.insertWmsInfoProcess(productVO);
            lifePlusService.mergeLifePlusInfoProcess(productVO);
            templateService.insertTemplateInfoProcess(productVO);

            if(productTypeService != null) productTypeService.insertProductTypeProcess(productVO);
        }

        catalogService.mergeCtlgInfoProcess(preProductVO, productVO);
        etcService.mergeEtcInfoProcess(preProductVO, productVO);
        benefitService.mergeGiftInfoProcess(preProductVO, productVO);
        productImageService.mergeImageInfoProcess(preProductVO, productVO);
        productGroupService.dropGroupNotValidPrd(productVO);
        priceService.insertSellerDirectCoupon(preProductVO, productVO);
    }

}