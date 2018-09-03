package com.elevenst.terroir.product.registration.price.validate;

import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.NumberUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.benefit.vo.CustomerBenefitVO;
import com.elevenst.terroir.product.registration.common.code.BsnDealClf;
import com.elevenst.terroir.product.registration.price.vo.PlusDscVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.product.code.SelStatCd;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PriceValidate {

    public boolean isCanChangePrice(ProductVO productVO) throws ProductException{

        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getBsnDealClf(), BsnDealClf.COMMISSION)){
            return true;
        }
        if(productVO.isPurchaseType()
            && GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelStatCd(), SelStatCd.PRODUCT_COMFIRM_WAIT)){
            return true;
        }

        return false;
    }

    public void checkDirectCoupon(ProductVO productVO) throws ProductException{
        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getBsnDealClf(), BsnDealClf.COMMISSION)){
            long selPrc = productVO.getPriceVO().getSelPrc();
            long dscAmt = productVO.getPriceVO().getDscAmt();
            double dscRt = productVO.getPriceVO().getDscRt();
            String issCnBgnDt = productVO.getPriceVO().getIssCnBgnDt();
            String issCnEndDt = productVO.getPriceVO().getIssCnEndDt();
            String cupnDscMthdCd = productVO.getPriceVO().getCupnDscMthdCd();
            if(dscAmt < 0 || dscRt < 0){
                throw new ProductException("즉시할인금액은 반드시 입력하셔야 합니다.");
            }

            if (ProductConstDef.CUPN_DSC_MTHD_CD_WON.equals(cupnDscMthdCd)) {
                dscAmt = productVO.getPriceVO().getDscAmt();
                if (dscAmt == 0) throw new ProductException("즉시할인금액은 반드시 입력하셔야 합니다.");
                if (dscAmt >= selPrc) throw new ProductException("즉시할인금액은 판매가 이상으로 설정할 수 없습니다.");
                if (dscAmt %10 != 0) throw new ProductException("즉시할인금액은 반드시 10원 단위로 입력하셔야 합니다.");

            }else if(ProductConstDef.CUPN_DSC_MTHD_CD_PERCNT.equals(cupnDscMthdCd)) {
                dscRt = productVO.getPriceVO().getDscRt();
                if (dscRt == 0.0) throw new ProductException("즉시할인율은 반드시 입력하셔야 합니다.");
                if (dscRt > 99.9 || dscRt <= 0.0) throw new ProductException("즉시할인률은 1~99까지 설정 가능합니다.");
            }else{
                throw new ProductException("유효하지 않은 즉시할인금액 단위 코드 입니다.");
            }

            if (StringUtil.isEmpty(cupnDscMthdCd)) throw new ProductException("즉시할인금액 단위는 반드시 선택하셔야 합니다.");


            // 할인기간 설정 ====================================
            if (StringUtil.isNotEmpty(issCnBgnDt)) {
                // 종료일 시작일 이전 설정 불가
                if (StringUtil.getLong(issCnBgnDt) > StringUtil.getLong(issCnEndDt))
                    throw new ProductException("즉시할인 적용 종료일은 시작일 이전으로  입력하실 수 없습니다.");
            }

        }else{
            throw new ProductException("직매입/특정매입은 기본즉시할인을 설정하실 수 없습니다.");
        }
    }

    public void checkPlusDsc(ProductVO productVO) throws ProductException{


        List<PlusDscVO> plusDscVOList = productVO.getPriceVO().getPlusDscVOList();

        if(plusDscVOList == null || plusDscVOList.size() == 0){
            return;
        }

        String plusDscCd = plusDscVOList.get(0).getPluDscCd(); // 수량 기준,금액기준
        String plusDscWyCd = plusDscVOList.get(0).getPluDscWyCd(); //정액,정률

        long selPrc = productVO.getPriceVO().getSelPrc();

        if(plusDscVOList.size() > 10){
            throw new ProductException("[복수구매 할인]할인 금액은 최대 10개까지 입력하셔야 합니다.");
        }

        List<Long> duplicateCheckerList = new ArrayList<Long>();
        for(PlusDscVO plusDscVO : plusDscVOList){
            long pluDscBasis = plusDscVO.getPluDscBasis();
            long pluDscAmt = plusDscVO.getPluDscAmt();
            double pluDscRt = plusDscVO.getPluDscRt();

            if(pluDscBasis <= 0) throw new ProductException("[복수구매 할인]할인 범위 기준은 반드시 입력하셔야 합니다.");
            if(ProductConstDef.PLU_DSC_MTHD_CD_PERCNT.equals(plusDscWyCd)) {
                if(pluDscRt <= 0) throw new ProductException("[복수구매 할인]할인 금액은 반드시 입력하셔야 합니다.");
            }
            if(ProductConstDef.PLU_DSC_MTHD_CD_WON.equals(plusDscWyCd)) {
                if(pluDscAmt <= 0) throw new ProductException("[복수구매 할인]할인 금액은 반드시 입력하셔야 합니다.");
            }

            if(duplicateCheckerList.contains(pluDscBasis)){
                throw new ProductException("[복수구매 할인]할인 범위 기준은 중복이 불가합니다.");
            }else{
                duplicateCheckerList.add(pluDscBasis);
            }

            if(ProductConstDef.PLU_DSC_BASIS_WON.equals(plusDscCd)) {
                if (pluDscBasis <= 9) throw new ProductException("[복수구매 할인]10원  이하로 설정할 수 없습니다.");
            }else if(ProductConstDef.PLU_DSC_BASIS_CNT.equals(plusDscCd)) {
                if (pluDscBasis <= 1) throw new ProductException("[복수구매 할인]2개이상 설정해야합니다.");
            }else{
                throw new ProductException("[복수구매 할인]유효하지 않은 포인트 지급 금액 단위 코드 입니다.");
            }

            //최소구매수량 체크
            long minLimitCnt = productVO.getBaseVO().getSelMinLimitQty();
            if(minLimitCnt > 0 && ProductConstDef.PLU_DSC_BASIS_CNT.equals(plusDscCd)
                && minLimitCnt > pluDscBasis)
            {
                new ProductException("복수구매할인 수량 기준은 최소구매허용수량 "+minLimitCnt+"개  보다 이상이 되어야 합니다.");
            }

            //최대구매허용수량 체크
            long selLimitQty = productVO.getBaseVO().getSelLimitQty();
            if(ProductConstDef.SEL_LIMIT_TYP_CD_TIMES.equals(productVO.getBaseVO().getSelLimitTypCd())
                && ProductConstDef.PLU_DSC_BASIS_CNT.equals(plusDscCd)
                && selLimitQty < pluDscBasis)
            {
                new ProductException("복수구매할인 수량 기준은 최대구매허용수량 "+selLimitQty+"개  보다 이하가 되어야 합니다.");
            }
            if(ProductConstDef.SEL_LIMIT_TYP_CD_PERSON.equals(productVO.getBaseVO().getSelLimitTypCd())
                && ProductConstDef.PLU_DSC_BASIS_CNT.equals(plusDscCd)
                && selLimitQty < pluDscBasis)
            {
                new ProductException("복수구매할인 수량 기준은 최대구매허용수량 "+selLimitQty+"개  보다 이하가 되어야 합니다.");
            }

            // 복수할인 금액이 20%초과 여부확인
            if(ProductConstDef.PLU_DSC_MTHD_CD_WON.equals(plusDscCd)){
                if(pluDscAmt > (Math.floor(selPrc * 0.2))) throw new ProductException("복수구매의 할인 금액은 판매가의 20%이상 설정하실 수 없습니다.");

            }else if(ProductConstDef.PLU_DSC_MTHD_CD_PERCNT.equals(plusDscCd)){
                if(pluDscRt > 20) throw new ProductException("복수구매의 할인  금액은 판매가의 20%이상 설정하실 수 없습니다.");
            }
        }
    }

    public void checkSelPrc(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        long selPrc = productVO.getPriceVO().getSelPrc();
        if(selPrc < 1){
            throw new ProductException("판매가는 반드시 입력하셔야 합니다.");
        }

        if (selPrc > ProductConstDef.PRD_SEL_PRC_LIMIT ){
            throw new ProductException("판매가는 "+NumberUtil.getCommaString(ProductConstDef.PRD_SEL_PRC_LIMIT)+"원 보다 크게 입력할 수 없습니다.");
        }

        if(productVO.isUpdate() && !"Y".equals(productVO.getMobile1WonYn())){
            long preSelPrc = preProductVO.getPriceVO().getSelPrc();

            double increaseRt = ((double)selPrc/(double)preSelPrc) * 100;

            if(increaseRt < 20){
                throw new ProductException("판매가는 최대 80% 인하까지 수정하실 수 있습니다.");
            }
//            if(increaseRt > 150){
//                throw new ProductException("판매가는 최대 50% 인상까지만 수정이 가능 합니다.");
//            }
        }
    }

    public void checkOcb(ProductVO preProductVO, ProductVO productVO) throws ProductException{

        if(productVO.getCustomerBenefitVO() != null && "Y".equals(productVO.getCustomerBenefitVO().getOcbYn())){
            CustomerBenefitVO bnftVO = productVO.getCustomerBenefitVO();
            long ocbPnt = 0;
            long selPrc = productVO.getPriceVO().getSelPrc();
            long soCupnAmt = productVO.getPriceVO().getSoCupnAmt();
            long limitPercnt = 30;
            long limitAmt = (long)(Math.floor((selPrc - soCupnAmt) * limitPercnt * 0.001) * 10);

            if (StringUtil.isEmpty(bnftVO.getOcbWyCd())){
                throw new ProductException("OK캐쉬백 지급 금액 단위는 반드시 선택하셔야 합니다.");
            }

            if (ProductConstDef.OCB_SPPL_WY_CD_WON.equals(bnftVO.getOcbWyCd())) {
                long ocb = bnftVO.getOcbPnt();
                if((ocb % 10) != 0) throw new ProductException("OK캐쉬백은 10원 단위로 입력하셔야 합니다.");
                if (ocb >= productVO.getPriceVO().getSelPrc()) throw new ProductException("OK캐쉬백 지급 금액은 판매가 이상으로 설정할 수 없습니다.");
                if (ocb < 0) throw new ProductException("OK캐쉬백 지급 금액은 0원 미만으로 입력할 수 없습니다.");
                ocbPnt = ocb;
            }else if(ProductConstDef.OCB_SPPL_WY_CD_PERCNT.equals(bnftVO.getOcbWyCd())) {
                long ocb = bnftVO.getOcbPntRt();
                if (ocb > 99 || ocb <= 0) throw new ProductException("OK캐쉬백 지급 률은 1~99까지 설정 가능합니다.");
                ocbPnt = (long)(Math.floor(selPrc * ocb * 0.001) * 10);
            }else{
                throw new ProductException("유효하지 않은 OK캐쉬백 지급 금액 단위 코드 입니다.");
            }

            if(limitAmt < ocbPnt) throw new ProductException("[OK캐쉬백 지급] '판매가-기본즉시할인' 금액의 30%를 초과할 수 없습니다.");
        }
    }

    public void checkMileage(ProductVO preProductVO, ProductVO productVO) throws ProductException{

        if(productVO.getCustomerBenefitVO() != null && "Y".equals(productVO.getCustomerBenefitVO().getMileageYn())) {
            CustomerBenefitVO bnftVO = productVO.getCustomerBenefitVO();
            if(ProductConstDef.MILEAGE_SPPL_WY_CD_WON.equals(bnftVO.getMileageWyCd())){
                long mileage = bnftVO.getMileagePnt();
                if((mileage % 100) != 0) throw new ProductException("마일리지는 100원 단위로 입력하셔야 합니다.");
                if (mileage < 0) throw new ProductException("마일리지는 0원 미만으로 입력할 수 없습니다.");
                if (mileage >= productVO.getPriceVO().getSelPrc()) throw new ProductException("마일리지 지급 금액은 판매가 이상으로 설정할 수 없습니다.");
            }else if(ProductConstDef.MILEAGE_SPPL_WY_CD_PERCNT.equals(bnftVO.getMileageWyCd())){
                long mileage = bnftVO.getMileagePntRt();
                if (mileage > 99 || mileage <= 0) throw new ProductException("마일리지 지급 률은 1~99까지 설정 가능합니다.");
            }else{
                throw new ProductException("유효하지 않은 마일리지 지급 금액 단위 코드 입니다.");
            }
        }
    }

    public void checkHopeShp(ProductVO preProductVO, ProductVO productVO) throws ProductException{

        if(productVO.getCustomerBenefitVO() != null && "Y".equals(productVO.getCustomerBenefitVO().getHopeShpYn())){
            CustomerBenefitVO bnftVO = productVO.getCustomerBenefitVO();

            long limitPercnt = 80;
            long limitAmt = 0;
            long selPrc = productVO.getPriceVO().getSelPrc();
            long soCupnAmt = productVO.getPriceVO().getSoCupnAmt();

            if(ProductConstDef.HOPE_SHP_SPPL_WY_CD_PERCNT.equals(bnftVO.getHopeShpWyCd())) {
                if(bnftVO.getHopeShpPntRt() > limitPercnt) throw new ProductException("희망후원설정 금액은 80%이하로 입력해주세요.");
                limitAmt = (long)(Math.floor((selPrc - soCupnAmt) * bnftVO.getHopeShpPntRt() * 0.001) * 10);
                if(limitAmt < 110) throw new ProductException("기준 희망후원설정 금액은 110원 미만이 될 수 없습니다.");

            } else if(ProductConstDef.HOPE_SHP_SPPL_WY_CD_WON.equals(bnftVO.getHopeShpWyCd())) {
                if(bnftVO.getHopeShpPnt() < 110) throw new ProductException("희망후원설정 금액은 110원 미만이 될 수 없습니다.");
                limitAmt = (long)(Math.floor((selPrc - soCupnAmt) * limitPercnt * 0.001) * 10);
                if(limitAmt < bnftVO.getHopeShpPnt()) throw new ProductException("희망후원 설정은 [판매가 - 기본즉시할인] 금액의 80%를 초과할 수 없습니다.");
                if(limitAmt < 110) throw new ProductException("기준 희망후원설정 금액은 110원 미만이 될 수 없습니다.");
            }else{
                throw new ProductException("유효하지 않은 희망후원 기부 금액 단위 코드 입니다.");
            }
        }
    }
}
