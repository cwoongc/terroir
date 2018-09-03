package com.elevenst.terroir.product.registration.common.process.prdtyp;

import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.entity.PdPrdAgency;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.mapper.ProductMapper;
import com.elevenst.terroir.product.registration.product.vo.AgencyVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.beans.BeanUtils.copyProperties;

public class TicketTypeServiceImpl implements ProductTypeService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException {

    }

    @Override
    public void setProductTypeInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException {
        this.checkTicketInfo(productVO);
    }


    @Override
    public void insertProductTypeProcess(ProductVO productVO) throws ProductException {
        this.insertProductAgency(productVO);
    }

    @Override
    public void updateProductTypeProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException {

        long agencyCnt = productMapper.checkProductAgency(productVO.getPrdNo());
        if(agencyCnt > 0){
            productMapper.disableProductAgency(productVO.getPrdNo());
        }
        this.insertProductAgency(productVO);
    }

    private void insertProductAgency(ProductVO productVO) throws ProductException{
        productMapper.insertPdPrdAgency(this.setPdPrdAgency(productVO));
    }

    private PdPrdAgency setPdPrdAgency(ProductVO productVO) throws ProductException {
        PdPrdAgency pdPrdAgency = new PdPrdAgency();
        copyProperties(productVO.getBaseVO().getAgencyVO(), pdPrdAgency);
        pdPrdAgency.setPrdNo(productVO.getPrdNo());
        pdPrdAgency.setCreateNo(productVO.getCreateNo());
        return pdPrdAgency;
    }

    private void checkTicketInfo(ProductVO productVO) throws ProductException{
        if (StringUtil.isEmpty(productVO.getPrdEtcVO().getPrdDetailOutLink()))	throw new ProductException("제휴사 상품 URL은 반드시 입력하셔야 합니다.");
        if (StringUtil.isEmpty(productVO.getPrdEtcVO().getPrdSvcBgnDy()))	throw new ProductException("예매가능 시작시간은 반드시 입력하셔야 합니다.");
        if (StringUtil.isEmpty(productVO.getPrdEtcVO().getPrdSvcEndDy()))	throw new ProductException("예매가능 종료시간은 반드시 입력하셔야 합니다.");

        if (productVO.getPrdEtcVO().getPrdSvcBgnDy().length() != 14 || productVO.getPrdEtcVO().getPrdSvcEndDy().length() != 14)
            throw new ProductException("예매가능일자의 날짜 포맷을 잘못 입력 하셨습니다.");

        if (StringUtil.getLong(productVO.getPrdEtcVO().getPrdSvcEndDy()) < StringUtil.getLong(productVO.getPrdEtcVO().getPrdSvcBgnDy()))
            throw new ProductException("예매가능 종료시간은 예매가능 시작시간 이전으로  입력하실 수 없습니다.");

        if("".equals(productVO.getPrdEtcVO().getSeatTyp())){
            throw new ProductException("좌석형태는 반드시 입력하셔야 합니다.");
        }

        if(productVO.getPrdEtcVO().getSeatTyp().getBytes().length > 2){
            throw new ProductException("좌석형태는 2Byte 까지 가능합니다.");
        }

        if(StringUtil.isContains(ProductConstDef.DEFAULT_VALUE_Y_N, productVO.getPrdEtcVO().getSeatTyp())){
            throw new ProductException("좌석형태는 'N' , 'Y'만 입력 가능합니다.");
        }

        if(StringUtil.isEmpty(productVO.getPrdEtcVO().getTheaterNm())){
            throw new ProductException("공연장명은 반드시 입력하셔야 합니다.");
        }

        if(StringUtil.isEmpty(productVO.getPrdEtcVO().getTheaterAreaInfo())){
            throw new ProductException("공연장 지역정보는 반드시 입력하셔야 합니다.");
        }

        if(StringUtil.isEmpty(productVO.getBaseVO().getAgencyVO().getAgencyBsnsNo())){
            throw new ProductException("기획사 사업자번호는 반드시 입력하셔야 합니다.");
        }

        if(StringUtil.isEmpty(productVO.getBaseVO().getAgencyVO().getAgencyNm())){
            throw new ProductException("기획사명은 반드시 입력하셔야 합니다.");
        }

        if(StringUtil.isEmpty(productVO.getBaseVO().getAgencyVO().getAgencyBsnsSt())){
            throw new ProductException("기획사업태는 반드시 입력하셔야 합니다.");
        }

        if(StringUtil.isEmpty(productVO.getBaseVO().getAgencyVO().getAgencyItm())){
            throw new ProductException("기획사종목은 반드시 입력하셔야 합니다.");
        }

        if(StringUtil.isEmpty(productVO.getBaseVO().getAgencyVO().getRptvNm())){
            throw new ProductException("대표자명은 반드시 입력하셔야 합니다.");
        }

        if(StringUtil.isEmpty(productVO.getBaseVO().getAgencyVO().getRptvTlphnNo())){
            throw new ProductException("대표자 전화번호는 반드시 입력하셔야 합니다.");
        }

        if(StringUtil.isEmpty(productVO.getBaseVO().getAgencyVO().getRptvEmailAddr())){
            throw new ProductException("대표자 이메일주소는 반드시 입력하셔야 합니다.");
        }

        if(!StringUtil.isEmpty(productVO.getBaseVO().getAgencyVO().getRptvEmailAddr())){
            String regex = "^([^'^\"^\\[^\\]^<^>^&^? ]+)@([a-zA-Z0-9]+)(.[a-zA-Z0-9]+){1,2}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(productVO.getBaseVO().getAgencyVO().getRptvEmailAddr());
            if(!matcher.matches()){
                throw new ProductException("대표자 이메일주소 포맷을 잘못 입력 하셨습니다.");
            }
        }

        if(!StringUtil.isEmpty(productVO.getBaseVO().getAgencyVO().getRptvTlphnNo())){
            String regex = "^[0-9()-]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(productVO.getBaseVO().getAgencyVO().getRptvTlphnNo());
            if(!matcher.matches()){
                throw new ProductException("대표자 전화번호는 숫자, (, ), - 만 가능합니다.");
            }
        }

        if(StringUtil.isEmpty(productVO.getBaseVO().getAgencyVO().getEnpPlcAddr())){
            throw new ProductException("사업장주소는 반드시 입력하셔야 합니다.");
        }

        if(StringUtil.isEmpty(productVO.getBaseVO().getMnfcDy())){
            throw new ProductException("공연시작일자는 반드시 입력하셔야 합니다.");
        }

        if(StringUtil.isEmpty(productVO.getBaseVO().getEftvDy())){
            throw new ProductException("공연종료일자는 반드시 입력하셔야 합니다.");
        }

        if(productVO.getBaseVO().getMnfcDy().replaceAll("/", "").length() != 8 ){
            throw new ProductException("공연시작일자의 날짜 포맷을 잘못 입력 하셨습니다.");
        }

        if(productVO.getBaseVO().getEftvDy().replaceAll("/", "").length() != 8 ){
            throw new ProductException("공연종료일자의 날짜 포맷을 잘못 입력 하셨습니다..");
        }

        if (StringUtil.getLong(productVO.getBaseVO().getEftvDy().replaceAll("/", ""))
            < StringUtil.getLong(productVO.getBaseVO().getMnfcDy().replaceAll("/", "")))
            throw new ProductException("공연종료일자는 공연시작일자 이전으로  입력하실 수 없습니다.");


        if(productVO.getBaseVO().getAgencyVO().getAgencyBsnsNo().getBytes().length > 14){
            throw new ProductException("기획사 사업자번호는 14자 까지 입력 가능합니다.");
        }

        if(productVO.getBaseVO().getAgencyVO().getAgencyNm().getBytes().length > 40){
            throw new ProductException("기획사명은 40자 까지 입력 가능합니다.");
        }

        if(productVO.getBaseVO().getAgencyVO().getAgencyBsnsSt().getBytes().length > 40){
            throw new ProductException("기획사업태는 40자 까지 입력 가능합니다.");
        }

        if(productVO.getBaseVO().getAgencyVO().getAgencyItm().getBytes().length > 40){
            throw new ProductException("기획사종목은 40자 까지 입력 가능합니다.");
        }


        if(productVO.getBaseVO().getAgencyVO().getRptvNm().getBytes().length > 40){
            throw new ProductException("대표자명은 40자 까지 입력 가능합니다.");
        }

        if(productVO.getBaseVO().getAgencyVO().getRptvTlphnNo().getBytes().length > 15){
            throw new ProductException("대표자 전화번호는 15자 까지 입력 가능합니다.");
        }

        if(productVO.getBaseVO().getAgencyVO().getRptvEmailAddr().getBytes().length > 200){
            throw new ProductException("대표자 이메일주소는 200자 까지 입력 가능합니다.");
        }

        if(productVO.getBaseVO().getAgencyVO().getEnpPlcAddr().getBytes().length > 120){
            throw new ProductException("사업장주소는 120자 까지 입력 가능합니다.");
        }
    }
}
