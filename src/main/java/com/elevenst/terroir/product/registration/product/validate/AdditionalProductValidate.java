package com.elevenst.terroir.product.registration.product.validate;

import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.common.util.StripHTMLTag;
import com.elevenst.terroir.product.registration.common.code.BsnDealClf;
import com.elevenst.terroir.product.registration.customer.service.CustomerServiceImpl;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.code.SelMthdCd;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.mapper.ProductMapper;
import com.elevenst.terroir.product.registration.product.vo.ProductAddCompositionVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class AdditionalProductValidate {

    @Autowired
    CustomerServiceImpl customerService;


    @Autowired
    ProductMapper productMapper;

    public void checkRentalProductAdditional(ProductVO productVO) {
        checkRentalProductAdditional(productVO.getBaseVO().getPrdTypCd());
    }
    public void checkRentalProductAdditional(String prdTypCd) {
        if(GroupCodeUtil.equalsDtlsCd(prdTypCd, PrdTypCd.RENTAL)) {
            throw new ProductException("렌탈상품은 추가구성상품 설정이 불가능합니다.");
        }
    }

    public void checkAddProductVOList(ProductVO productVO) throws ProductException{
        List<ProductAddCompositionVO> addPrdVOList = productVO.getProductAddCompositionVOList();

        if(addPrdVOList == null || addPrdVOList.size() == 0){
            return;
        }
        if(!GroupCodeUtil.isContainsDtlsCd(productVO.getBaseVO().getSelMthdCd(), SelMthdCd.FIXED, SelMthdCd.COBUY, SelMthdCd.USED)){
            return;
        }
        if("Y".equals(productVO.getMobile1WonYn())){
            return;
        }

        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getBsnDealClf(), BsnDealClf.DIRECT_PURCHASE)){
            throw new ProductException("직매입상품은 추가구성상품을 등록/수정할 수 없습니다.");
        }

        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getPrdTypCd(), PrdTypCd.RENTAL)){
            throw new ProductException("렌탈상품은 추가구성상품 설정이 불가능합니다.");
        }

        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getBsnDealClf(), BsnDealClf.DIRECT_PURCHASE)){
            throw new ProductException("직매입상품은 추가구성상품을 등록/수정할 수 없습니다.");
        }
        String addPrdGrpNm = "";
        String compPrdNm = "";

        boolean isGlbItgIdsSeller =  (customerService.getGlbItgIdsBySeller(productVO.getSelMnbdNo()) > 0 ? true : false);

        HashMap<String, Integer> addPrdGrpMap = new HashMap<String, Integer>();
        HashMap<String, Integer> addCompKeyMap = new HashMap<String, Integer>();
        for(ProductAddCompositionVO addPrdVO : addPrdVOList){

            if(!"02".equals(addPrdVO.getPrdCompTypCd())){
                continue;
            }
            if(ProductConstDef.DLV_CLF_GLOBAL_DELIVERY.equals(productVO.getBaseVO().getDlvClf())){
                if(addPrdVO.getAddPrdWght() <= 0 || addPrdVO.getAddCstmAplPrc() <= 0){
                    throw new ProductException("11번가해외배송인 경우 추가구성상품의 신고가와 무게는 0이상이어야 합니다.");
                }
            }

            if(!("Y".equals(addPrdVO.getUseYn()) || "N".equals(addPrdVO.getUseYn()))){
                throw new ProductException("유효하지 않은 추가구성 상품 상태 코드 입니다.");
            }

            addPrdGrpNm = StripHTMLTag.removeInvalidTag(StringUtil.nvl(addPrdVO.getAddPrdGrpNm()).trim());
            if(!addPrdGrpMap.containsKey(addPrdGrpNm)){
                addPrdGrpMap.put(addPrdGrpNm, 0);
            }
            if(addPrdGrpMap.size() > 10){
                throw new ProductException("추가상품명은 10개를 초과 입력할수 없습니다.");
            }
            if(StringUtil.isEmpty(addPrdGrpNm) || "".equals(addPrdGrpNm))
                throw new ProductException("추가상품명은 반드시 입력하셔야 합니다.");
            if(StringUtil.existsCharacters(addPrdGrpNm, "['|\"|%|&|<|>|#|†|\\\\|∏|‡]"))
                throw new ProductException("추가상품명에 특수 문자[',\",%,&,<,>,#,†,\\,∏,‡]는 입력할 수 없습니다.");
            if(addPrdGrpNm.getBytes().length > 28)
                throw new ProductException("추가상품명은 최대 한글14자(28바이트)까지  입력 가능합니다. 상품명 [" + addPrdGrpNm + "]");

            // 상품명
            compPrdNm = StripHTMLTag.removeInvalidTag(StringUtil.nvl(addPrdVO.getCompPrdNm()).trim());

            if(addCompKeyMap.containsKey(compPrdNm)){
                throw new ProductException("중복된 추가상품값 이 존재합니다. 상품값 [" + compPrdNm + "]을 확인해 주십시오.");
            }else{
                addCompKeyMap.put(compPrdNm, 0);
            }
            if(StringUtil.isEmpty(compPrdNm))
                throw new ProductException("추가상품값은 반드시 입력하셔야 합니다.");
            if(StringUtil.existsCharacters(compPrdNm, "['|\"|%|&|<|>|#|†|\\\\|∏|‡|,]"))
                throw new ProductException("추가상품값에 특수 문자[',\",%,&,<,>,#,†,\\,∏,‡,콤마(,)]는 입력할 수 없습니다.");
            if(compPrdNm.getBytes().length > 40)
                throw new ProductException("추가상품값은 최대 한글20자(40 바이트)까지 입력 가능합니다. 상품값 [" + compPrdNm + "]");


            // 가격
            if(addPrdVO.getAddCompPrc() <= 0)
                throw new ProductException("추가구성 상품 가격은 반드시 0원 이상 입력하셔야 합니다.");
            if(addPrdVO.getAddCompPrc() % 10 != 0)
                throw new ProductException("추가구성 상품 가격은 10원 단위로 입력하셔야 합니다.");

            // 재고수량
            if ("Y".equals(addPrdVO.getUseYn()) && addPrdVO.getCompPrdQty() <= 0)
                throw new ProductException("추가구성 상품 재고수량은 반드시 입력하셔야 합니다.");
            if (addPrdVO.getCompPrdQty() < 0)
                throw new ProductException("추가구성 상품 재고수량은 0 이상의 값을 입력하셔야 합니다.");


            // 무게&상품신고가(해외 통합출고지 사용가능 셀러인 경우만 해당함)
            if(isGlbItgIdsSeller){
                if (ProductConstDef.DLV_CLF_GLOBAL_DELIVERY.equals(productVO.getBaseVO().getDlvClf())
                    && (addPrdVO.getAddPrdWght() <= 0))
                    throw new ProductException("추가구성 상품 무게는  0 보다 큰 값을 입력하셔야 합니다.");

                if (ProductConstDef.DLV_CLF_GLOBAL_DELIVERY.equals(productVO.getBaseVO().getDlvClf())){
                    if(addPrdVO.getAddCstmAplPrc() <= 0)
                        throw new ProductException("추가구성상품의 상품신고가는 0 보다 큰 값을 입력하셔야 합니다.");

                    String[] arrCstmAplPrc =  StringUtil.split(String.valueOf(addPrdVO.getAddCstmAplPrc()),".");
                    if(arrCstmAplPrc.length >1 && arrCstmAplPrc[1].length()>2)
                        throw new ProductException("추가구성상품 신고가는 소수둘째자리 까지만 입력 가능 합니다.");
                }
            }else{
                if("Y".equals(productVO.getBaseVO().getGblDlvYn())){
                    if (addPrdVO.getAddPrdWght() <= 0)
                        throw new ProductException("추가구성 상품 무게는 0 보다 큰 값을 입력하셔야 합니다.");
                }
            }

            //부가세 구분 코드
            if(!ProductConstDef.isValidCompPrdVatCd(addPrdVO.getCompPrdVatCd()))
                throw new ProductException("유효하지 않은 부가세 코드입니다.");

            // 추가구성 상품인지 아닌지 조회
            if(addPrdVO.getCompPrdNo() > 0){
                if(addPrdVO.getPrdCompNo() <= 0){
                    throw new ProductException("추가구성상품에 해당하는 키값이 존재하지 않습니다. 확인하시기 바랍니다.");
                }
                if (productMapper.getAddPrdNoInputTypeCount(addPrdVO) == 0){
                    throw new ProductException("잘못된 추가구성상품 키 입니다. 확인하시기 바랍니다.");
                }
            }
        }
    }

}
