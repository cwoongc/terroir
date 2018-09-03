package com.elevenst.terroir.product.registration.mobile.validate;

import com.elevenst.common.util.NumberUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.mobile.code.MpContractTermCd;
import com.elevenst.terroir.product.registration.mobile.code.ProductBulkExcelDef;
import com.elevenst.terroir.product.registration.mobile.entity.PdPrdMobileFee;
import com.elevenst.terroir.product.registration.mobile.exception.MobileServiceException;
import com.elevenst.terroir.product.registration.mobile.mapper.MobileMapper;
import com.elevenst.terroir.product.registration.mobile.message.ValidationResult;
import com.elevenst.terroir.product.registration.mobile.service.MobileServiceImpl;
import com.elevenst.terroir.product.registration.mobile.vo.MobileFeeSearchParamVO;
import com.elevenst.terroir.product.registration.mobile.vo.PdPrdMobileFeeVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MobileValidateion {

    @Autowired
    MobileServiceImpl mobileService;

    @Autowired
    MobileMapper mobileMapper;

    /**
     * 상품 휴대폰 요금제 정보 검증.
     *
     * @param mpContractTypCd       휴대폰 약정 유형 코드.
     * @param productMobileFeeBOList    상품 휴대폰 요금제 정보 목록.
     * @param dispCtgrNo    카테고리번호.
     * @return  검증결과.
     */
    public ValidationResult checkMobileFeeInfo(String mpContractTypCd, List<PdPrdMobileFeeVO> productMobileFeeBOList, long dispCtgrNo) {
        try {
            return this.validateAPIAndBulkRegProductMobileFeeInfo(mpContractTypCd, productMobileFeeBOList, dispCtgrNo);
        } catch (ProductException e) {
            return new ValidationResult(false, e.getMessage());
        }
    }

    public ValidationResult validateAPIAndBulkRegProductMobileFeeInfo(String mpContractTypCd, List<PdPrdMobileFeeVO> productMobileFeeBOList, long dispCtgrNo) {
        if (StringUtils.isEmpty(mpContractTypCd)) {
            return new ValidationResult(false, "약정방식은 반드시 입력하셔야 합니다.", ProductBulkExcelDef.EXCEL_FIELD_MpContractTypCd);
        }
        if (!(ProductConstDef.MP_CONTRACT_TYP_CD_NORMAL.equals(mpContractTypCd) || ProductConstDef.MP_CONTRACT_TYP_CD_FEE.equals(mpContractTypCd))) {
            return new ValidationResult(false, "약정방식 코드가 잘못되었습니다.", ProductBulkExcelDef.EXCEL_FIELD_MpContractTypCd);
        }


        if(ProductConstDef.MP_CONTRACT_TYP_CD_NORMAL.equals(mpContractTypCd)) {
            if (productMobileFeeBOList != null && !productMobileFeeBOList.isEmpty()) {
                return new ValidationResult(false, "요금제 정보는 약정방식이 '요금제약정'인 경우에만 입력하세요.", ProductBulkExcelDef.EXCEL_FIELD_MpFeeInfo);
            }
        }

        if (ProductConstDef.MP_CONTRACT_TYP_CD_FEE.equals(mpContractTypCd)) {
            if (productMobileFeeBOList == null || productMobileFeeBOList.isEmpty()) {
                return new ValidationResult(false, "요금제 정보가 잘못되었습니다.", ProductBulkExcelDef.EXCEL_FIELD_MpFeeInfo);
            }
            if (productMobileFeeBOList.size() > 20) {
                return new ValidationResult(false, "요금제는 최대 20개 까지 등록 가능합니다.", ProductBulkExcelDef.EXCEL_FIELD_MpFeeInfo);
            }

            for (PdPrdMobileFeeVO prdMobileFeeVO : productMobileFeeBOList) {
                if(prdMobileFeeVO.getMpFeeNo() <= 0) {
                    return new ValidationResult(false, "요금제 코드를 입력하십시오.", ProductBulkExcelDef.EXCEL_FIELD_MpFeeInfo);
                }
                if (!this.isExistMobileFeeInCategory(prdMobileFeeVO.getMpFeeNo(), dispCtgrNo)) {
                    return new ValidationResult(false, "카테고리에 사용 가능한 요금제를 확인해 주십시오.", ProductBulkExcelDef.EXCEL_FIELD_MpFeeInfo);
                }
                if (prdMobileFeeVO.getMaktPrc() <= 0) {
                    return new ValidationResult(false, "고객부담단말가격은 반드시 입력하셔야 합니다.", ProductBulkExcelDef.EXCEL_FIELD_MpFeeInfo);
                }
                if (prdMobileFeeVO.getMaktPrc() % 10 != 0) {
                    return new ValidationResult(false, "고객부담단말가격은 반드시 10원 단위로 입력하셔야 합니다.", ProductBulkExcelDef.EXCEL_FIELD_MpFeeInfo);
                }
                if (prdMobileFeeVO.getMaktPrc() > ProductConstDef.MOBILE_PHONE_PRC_LIMIT) {
                    return new ValidationResult(false,
                            "고객부담단말가격은 " + NumberUtil.getCommaString(ProductConstDef.MOBILE_PHONE_PRC_LIMIT) + "원 보다 크게 입력할 수 없습니다.",
                            ProductBulkExcelDef.EXCEL_FIELD_MpFeeInfo);
                }

                boolean existContractTermCd = false;

                if(!StringUtil.isEmpty(prdMobileFeeVO.getContractTermCd())) {
                    for (MpContractTermCd elem : MpContractTermCd.values()) {
                        if(prdMobileFeeVO.getContractTermCd().equals(elem.getDtlsCd())) {
                            existContractTermCd = true;
                            break;
                        }
                    }
                }

                if (!existContractTermCd) {
                    return new ValidationResult(false, "약정기간코드가 잘못되었습니다.",ProductBulkExcelDef.EXCEL_FIELD_MpFeeInfo);
                }
            }

            if (isExistDuplicatedMobileFee(productMobileFeeBOList)) {
                return new ValidationResult(false, "중복된 요금제 정보가 존재합니다.", ProductBulkExcelDef.EXCEL_FIELD_MpFeeInfo);
            }

        }
        return new ValidationResult(true);
    }

    private static boolean isExistDuplicatedMobileFee(List<PdPrdMobileFeeVO> productMobileFeeBOList) {
        Set mpFeeSet = new HashSet();
        for (PdPrdMobileFeeVO prdMobileFeeVO : productMobileFeeBOList) {
            String bizKey = prdMobileFeeVO.composeBizKey();

            if (mpFeeSet.contains(bizKey)) {
                return true;
            } else {
                mpFeeSet.add(bizKey);
            }
        }

        return false;
    }

    private boolean isExistMobileFeeInCategory(long mpFeeNo, long dispCtgrNo) {
        boolean result;

        try {
            MobileFeeSearchParamVO param = new MobileFeeSearchParamVO();
            param.setMpFeeNo(mpFeeNo);
            param.setSearchClsf("02");
            param.setSearchCategory(String.valueOf(dispCtgrNo));
            param.setUseYn("Y");
            param.setStart(0);
            param.setEnd(1);

            List<PdPrdMobileFee> mobileFeeVOList = mobileService.searchMobileFee(param);

            result = !(mobileFeeVOList == null || mobileFeeVOList.isEmpty());
        } catch (ProductException e) {
            result = false;
        }

        return result;
    }

    public boolean isMobileCtgr(ProductVO productVO) throws MobileServiceException{
        if(productVO.getProductAddtInfoVOList() != null
            && productVO.getProductAddtInfoVOList().size() > 0
            && "Y".equals(productVO.getCategoryVO().getMobileYn())){
            return true;
        }else{
            return false;
        }
    }
}
