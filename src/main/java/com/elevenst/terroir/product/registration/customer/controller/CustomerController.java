package com.elevenst.terroir.product.registration.customer.controller;

import com.elevenst.common.code.CacheKeyConstDef;
import com.elevenst.common.properties.PropMgr;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.customer.service.CustomerServiceImpl;
import com.elevenst.terroir.product.registration.delivery.service.DeliveryServiceImpl;
import com.elevenst.terroir.product.registration.delivery.vo.MbMemVO;
import com.elevenst.terroir.product.registration.delivery.vo.PdSellerIslandDlvCstVO;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.message.ProductExceptionMessage;
import com.elevenst.terroir.product.registration.seller.service.SellerServiceImpl;
import com.elevenst.terroir.product.registration.seller.validate.SellerValidate;
import com.elevenst.terroir.product.registration.seller.vo.SellerVO;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skt.tmall.auth.Auth;
import skt.tmall.auth.AuthCode;
import skt.tmall.auth.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping(value="/customer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CustomerController {

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    SellerServiceImpl sellerService;

    @Autowired
    SellerValidate sellerValidate;

    @Autowired
    DeliveryServiceImpl deliveryService;

    @Autowired
    PropMgr propMgr;

    @GetMapping("/isRoadShopSeller")
    public boolean getCtgrAttributeInfo(HttpServletRequest request, HttpServletResponse response) throws ProductException  {
        long memNo = 0;
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if(auth != null) {
            memNo = auth.getMemberNumber();
        }

        return customerService.isRoadShoSeller(memNo);
    }

    @GetMapping("/seller-common-info")
    public ResponseEntity<JSONObject> getSellerCommonInfo(HttpServletRequest request, HttpServletResponse response) throws ProductException  {
        long memNo = 0;
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if(auth != null) {
            memNo = auth.getMemberNumber();
        }
        if (memNo == 0) throw new ProductException(ProductExceptionMessage.AUTH_ERROR);

        SellerVO sellerVO = new SellerVO();
        boolean isRoadShoSeller = customerService.isRoadShoSeller(memNo);
        sellerVO.setRoadShopSeller(isRoadShoSeller);
        boolean isMobileRegSeller = sellerValidate.isSupportMobileEditSeller(auth);
        sellerVO.setMobileRegSeller(isMobileRegSeller);

        boolean isFreeImgMember = false;
        String freeImgMemStr = propMgr.get1hourTimeProperty(CacheKeyConstDef.PRD_DTL_IMG_SKIP_SELLER);
        if(StringUtil.isNotEmpty(freeImgMemStr)) {
            isFreeImgMember = freeImgMemStr.contains(String.valueOf(memNo));
        }
        sellerVO.setFreeImgMember(isFreeImgMember);

        // 제주 도서산간
        PdSellerIslandDlvCstVO pdSellerIslandDlvCstVO = deliveryService.getSellerIslandDlvCst(memNo);

        String jejuDlvCst = "";
        String islandDlvCst = "";
        String islandUseYn = "N";
        // 제주/도서산간 정보 세팅
        if(pdSellerIslandDlvCstVO != null) {
            jejuDlvCst = pdSellerIslandDlvCstVO.getJejuDlvCst() > 0 ? String.valueOf(pdSellerIslandDlvCstVO.getJejuDlvCst()) : "";
            islandDlvCst = pdSellerIslandDlvCstVO.getIslandDlvCst() > 0 ? String.valueOf(pdSellerIslandDlvCstVO.getIslandDlvCst()) : "";
            if(pdSellerIslandDlvCstVO.getJejuDlvCst() > 0 || pdSellerIslandDlvCstVO.getIslandDlvCst() > 0) {
                islandUseYn = "Y";
            }
        }
        sellerVO.setJejuDlvCst(jejuDlvCst);
        sellerVO.setIslandDlvCst(islandDlvCst);
        sellerVO.setIslandUseYn(islandUseYn);

        // 반품/교환 배송비 (+ 초기 배송비 무료시 부과방법)
        String rtnDlvFee = "";
        String chngDlvFee = "";
        String initDlvNoFeeMth = "";
        MbMemVO mbMemVO = sellerService.getSellerRtnChngDlvInfo(memNo);
        if(mbMemVO != null) {
            rtnDlvFee = mbMemVO.getRtnDlvFee() > 0 ? String.valueOf(mbMemVO.getRtnDlvFee()) : "";
            chngDlvFee = mbMemVO.getChngDlvFee() > 0 ? String.valueOf(mbMemVO.getChngDlvFee()) : "";
            initDlvNoFeeMth = mbMemVO.getInitDlvNoFeeMth();
        }
        sellerVO.setRtngdDlvCst(rtnDlvFee);
        sellerVO.setExchDlvCst(chngDlvFee);
        sellerVO.setRtngdDlvCd(initDlvNoFeeMth);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sellerVO", sellerVO);

        return new ResponseEntity(jsonObject, HttpStatus.OK);
    }
}
