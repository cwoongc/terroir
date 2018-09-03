package com.elevenst.terroir.product.registration.benefit.validate;

import com.elevenst.terroir.product.registration.benefit.message.BenefitExceptionMessage;
import com.elevenst.terroir.product.registration.benefit.vo.CustomerBenefitVO;
import com.elevenst.terroir.product.registration.benefit.vo.ProductGiftVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.List;

@Component
public class BenefitValidate {

    public void checkProductGiftLen(ProductGiftVO giftVO) throws ProductException {
        if(giftVO.getGiftNm()==null || "".equals(giftVO.getGiftNm())) {
            throw new ProductException(BenefitExceptionMessage.GIFT_10);
        }
        try {
            if(giftVO.getGiftNm().getBytes("EUC-KR").length > 30){
                throw new ProductException(BenefitExceptionMessage.GIFT_01);
            }
        } catch (UnsupportedEncodingException e) {
            throw new ProductException(BenefitExceptionMessage.GIFT_01);
        }
        try {
            if(giftVO.getGiftInfo()!=null && giftVO.getGiftInfo().getBytes("EUC-KR").length > 200){
                throw new ProductException(BenefitExceptionMessage.GIFT_02);
            }
        } catch (UnsupportedEncodingException e) {
            throw new ProductException(BenefitExceptionMessage.GIFT_02);
        }
    }

    public void checkProductGift(ProductGiftVO giftVO, ProductGiftVO preGiftBO) throws ProductException {

        String giftImgNm = giftVO.getImgNm();
        String giftImgUrlPath = giftVO.getImgUrlPath();
        if(("".equals(giftImgNm) && "".equals(giftImgUrlPath))
                && (("N".equals(giftVO.getImgUploadYn()) && giftVO.getImgFile() == null)
                && (preGiftBO == null || "".equals(giftImgNm)))
                && "".equals(giftVO.getGiftInfo())){
            throw new ProductException(BenefitExceptionMessage.GIFT_03);
        }
    }

    public void checkProductGiftImage(File giftImgFile) throws ProductException {
        if(giftImgFile == null){
            return;
        }
        if (giftImgFile.length() > 1048576) {
            throw new ProductException(BenefitExceptionMessage.GIFT_04);
        }
        try {
            BufferedImage bi = ImageIO.read(giftImgFile);
            if (bi.getWidth() != 80 || bi.getHeight() != 80) {
                throw new ProductException(BenefitExceptionMessage.GIFT_05);
            }
        } catch(IOException e){
            throw new ProductException(BenefitExceptionMessage.GIFT_06);
        }
    }

    public void checkProductGiftAPI(ProductVO productVO) throws ProductException {
        if (productVO == null) {
            throw new ProductException(BenefitExceptionMessage.GIFT_08);
        }

        String useGiftYn = productVO.getUseGiftYn();
        if((!"Y".equals(useGiftYn) && !"N".equals(useGiftYn))) {
            throw new ProductException(BenefitExceptionMessage.GIFT_07);

        } else if (productVO.getSelMnbdNo() != productVO.getCreateNo()) {
            throw new ProductException(BenefitExceptionMessage.GIFT_09);
        }
    }

    public void checkProductGiftDt(ProductGiftVO giftVO) throws ProductException {
        if(giftVO.getAplBgnDt().length() != 8) {
            throw new NumberFormatException();
        }
        if(giftVO.getAplEndDt().length() != 8) {
            throw new NumberFormatException();
        }

        try {
            long aplBgnDt = Long.parseLong(giftVO.getAplBgnDt());
            long aplEndDt = Long.parseLong(giftVO.getAplEndDt());
            if(aplBgnDt > aplEndDt) {
                throw new ProductException(BenefitExceptionMessage.GIFT_11);
            }
        } catch (NumberFormatException e) {
            throw new ProductException(BenefitExceptionMessage.GIFT_12);
        }
    }

    public boolean checkAddCustomerBenefitAvail(CustomerBenefitVO customerBenefitVO) {

        List<String> candidateList = Arrays.asList(customerBenefitVO.getPointYn(), customerBenefitVO.getChipYn(),
                                           customerBenefitVO.getIntFreeYn(), customerBenefitVO.getOcbYn(),
                                           customerBenefitVO.getMileageYn(), customerBenefitVO.getHopeShpYn());

        if(candidateList.contains("Y")) {
            return true;
        }

        return false;

    }

    public void checkChipInfo(ProductVO productVO) throws ProductException{
        if("Y".equals(productVO.getCustomerBenefitVO().getChipYn())){
            throw new ProductException("칩(CHIP) 지급을 설정하실 수 없습니다.등록 및 수정불가 합니다.");
        }
    }

    public void checkPointInfo(ProductVO productVO) throws ProductException{
        if("Y".equals(productVO.getCustomerBenefitVO().getPointYn())){
            throw new ProductException("포인트 지급을 설정하실 수 없습니다.등록 및 수정불가 합니다.");
        }
    }

    public void checkOCBInfo(ProductVO productVO) throws ProductException{
        if("Y".equals(productVO.getCustomerBenefitVO().getOcbYn())){
//        productVO.getCustomerBenefitVO().setOcbPntRt(ocbRsrvRt);            //OK캐쉬백 지급율
//        productVO.getCustomerBenefitVO().setOcbPnt(ocbPnt);			        //OK캐쉬백
//        productVO.getCustomerBenefitVO().setOcbWyCd(ocbWyCd);		        //OK캐쉬백 지급방식
//        productVO.getCustomerBenefitVO().setOcbSpplLmtQty(ocbSpplLmtQty);   //OK캐쉬백 지급제한수량
//        productVO.getCustomerBenefitVO().setOcbSpplQty(0);                  //OK캐쉬백 지급수량
        }else{
            productVO.getCustomerBenefitVO().setOcbPntRt(0);
            productVO.getCustomerBenefitVO().setOcbPnt(0);
            productVO.getCustomerBenefitVO().setOcbWyCd("");
            productVO.getCustomerBenefitVO().setOcbSpplLmtQty(0);
            productVO.getCustomerBenefitVO().setOcbSpplQty(0);
        }
    }

    public void checkMileageInfo(ProductVO productVO) throws ProductException{
        if("Y".equals(productVO.getCustomerBenefitVO().getMileageYn())){
//        productVO.getCustomerBenefitVO().setMileagePntRt(mileageRsrvRt);		    //마일리지 지급율
//        productVO.getCustomerBenefitVO().setMileagePnt(mileage);					//마일리지
//        productVO.getCustomerBenefitVO().setMileageWyCd(mileageWyCd);				//마일리지 지급방식
//        productVO.getCustomerBenefitVO().setMileageSpplLmtQty(mileageSpplLmtQty);	//마일리지 지급제한수량
//        productVO.getCustomerBenefitVO().setMileageSpplQty(0);					//마일리지 지급수량
        }else{
            productVO.getCustomerBenefitVO().setMileagePntRt(0);
            productVO.getCustomerBenefitVO().setMileagePnt(0);
            productVO.getCustomerBenefitVO().setMileageWyCd("");
            productVO.getCustomerBenefitVO().setMileageSpplLmtQty(0);
            productVO.getCustomerBenefitVO().setMileageSpplQty(0);
        }
    }

    public void checkCardFreeMonInfo(ProductVO productVO) throws ProductException{
        if("Y".equals(productVO.getCustomerBenefitVO().getIntFreeYn())){
//            productVO.getCustomerBenefitVO().setIntfreeMonClfCd(intfreeMonClfCd);		// 무이자할부코드
//            productVO.getCustomerBenefitVO().setIntfreeAplLmtQty(intfreeAplLmtQty);     // 무이자할부 제한 횟수
//            productVO.getCustomerBenefitVO().setIntfreeAplQty(0);						// 무아지할부 제공 수량
        }
    }
}
