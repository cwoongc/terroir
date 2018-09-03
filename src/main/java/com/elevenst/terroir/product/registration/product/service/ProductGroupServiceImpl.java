package com.elevenst.terroir.product.registration.product.service;

import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.common.service.CommonServiceImpl;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.entity.PdPrdGrpMapp;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.mapper.ProductGroupMapper;
import com.elevenst.terroir.product.registration.product.vo.ProductGroupPrdVO;
import com.elevenst.terroir.product.registration.product.vo.ProductGroupVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductGroupServiceImpl {

    @Autowired
    ProductGroupMapper productGroupMapper;

    @Autowired
    CommonServiceImpl commonService;

    public boolean dropGroupNotValidPrd(ProductVO productVO) throws ProductException {
        boolean drop = false;

        long prdNo = productVO.getPrdNo();

        // 그룹 구성여부
        long prdGrpNo = productGroupMapper.getProductGroupNo(prdNo);

        if (prdGrpNo > 0) { // 구성상품
            // 기 등록된 그룹 정보
            ProductGroupVO productGroupVO = productGroupMapper.getGroupInfo(prdGrpNo);
            drop = !isValidGroupProduct(productGroupVO, prdNo);
        }

        if (drop) {
            PdPrdGrpMapp pdPrdGrpMapp = new PdPrdGrpMapp();

            pdPrdGrpMapp.setPrdGrpNo(prdGrpNo);
            pdPrdGrpMapp.setPrdNo(prdNo);
            pdPrdGrpMapp.setDispPrrt(9999);
            pdPrdGrpMapp.setUseYn("N");
            pdPrdGrpMapp.setCreateNo(productVO.getCreateNo());
            pdPrdGrpMapp.setUpdateNo(productVO.getCreateNo());
            productGroupMapper.updatePdPrdGrpMapp(pdPrdGrpMapp);
        }

        return drop;
    }

    private boolean isValidGroupProduct(ProductGroupVO groupBo, long prdNo) throws ProductException {

        boolean valid = false;

        // select selected group info based on prd-info
        ProductGroupVO PrdBo = new ProductGroupVO();
        PrdBo.setRepPrdNo(prdNo);
        PrdBo.setBasiCtgrLevel(2);
        PrdBo = productGroupMapper.productGroupBasePrdInfo(PrdBo);
        PrdBo.setBasiCtgrLevel(getGroupCtgrLevel(PrdBo.getDispCtgr1No()));

        // 전시 카테고리 비교
        if (groupBo.getDispCtgr1No() != PrdBo.getDispCtgr1No()) {
            valid = false;
        } else if (groupBo.getBasiCtgrLevel() == 2 && groupBo.getDispCtgr2No() != PrdBo.getDispCtgr2No()) {
            valid = false;
        }
        // 배송가능지역 동일
        else if (!groupBo.getBasiDlvCanRgnCd().equals(PrdBo.getBasiDlvCanRgnCd())) {
            valid = false;
        }
        // 배송방법 동일
        else if (!groupBo.getBasiDlvMthdCd().equals(PrdBo.getBasiDlvMthdCd())) {
            valid = false;
        }
        // 미성년자 구매가능 여부 동일
        else if (!groupBo.getMinorBuyCanYn().equals(PrdBo.getMinorBuyCanYn())) {
            valid = false;
        }
        // / 주문제작은 안됨
        else if (PrdBo.getPrdStatCd().equals(ProductConstDef.PRD_STAT_CD_ORDER)) {
            valid = false;
        }
        // 마트는 마트끼리.
        else if ((ProductConstDef.PRD_TYP_CD_MART.equals(groupBo.getBasiPrdClfCd()) && !ProductConstDef.PRD_TYP_CD_MART.equals(PrdBo
                .getBasiPrdClfCd()))
                || (!ProductConstDef.PRD_TYP_CD_MART.equals(groupBo.getBasiPrdClfCd()) && ProductConstDef.PRD_TYP_CD_MART.equals(PrdBo
                .getBasiPrdClfCd()))) {
            valid = false;
        } else {

            ProductGroupPrdVO paramVO = new ProductGroupPrdVO();
            paramVO.setPrdTypCd(PrdBo.getBasiPrdClfCd());
            paramVO.setBcktExYn("Y".equals(productGroupMapper.getProductCartUse(prdNo)) ? "N" : "Y");
            paramVO.setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_SALE); // 판매상태 사유로는 제외처리를 하지 않는다.
            setProductValidate(paramVO);
            if (!StringUtil.equals(paramVO.getUseYn(), "Y")) {
                valid = false;
            }
            else
                valid = true;
        }

        return valid;
    }

    private void setProductValidate(ProductGroupPrdVO product) {

        // 2) 서비스 상품 유형 : 아래 서비스 상품 유형일 경우, 상품그룹 등록에 유효하지 않음
        // 1 마트지점배송 상품 (11) _PRD_TYP_CD. MART
        // 2 출장서비스형 (36) _PRD_TYP_CD.LIFE_BUSINESS_TRIP_SERVICE
        // 3 매장방문형 (35) _PRD_TYP_CD.LIFE_TOWN_VISIT
        // 4 출장수거배달서비스형 (37) _PRD_TYP_CD.PRD_TYP_CD_LIFE_PICKUP_N_DEVLIVERY

        if (StringUtil.equals(product.getPrdTypCd(), ProductConstDef.PRD_TYP_CD_MART)
                || StringUtil.equals(product.getPrdTypCd(), ProductConstDef.PRD_TYP_CD_LIFE_BUSINESS_TRIP_SERVICE)
                || StringUtil.equals(product.getPrdTypCd(), ProductConstDef.PRD_TYP_CD_LIFE_TOWN_VISIT)
                || StringUtil.equals(product.getPrdTypCd(), ProductConstDef.PRD_TYP_CD_LIFE_PICKUP_N_DEVLIVERY)) {
            product.setUseYn("N");
            product.setWarning(true);
            product.setWarningMsg(true);
            return;
        }

        // 3) 장바구니 담기 불가상품 여부 : 장바구니 담기 불가 N
        if (StringUtil.equals(product.getBcktExYn(), "Y")) {
            product.setUseYn("N");
            product.setWarning(true);
            product.setWarningMsg(true);
            return;
        }

        // 3) 장바구니 담기 불가상품 여부 : 장바구니 담기 불가 N
        if (StringUtil.equals(product.getPrdStatCd(), ProductConstDef.PRD_STAT_CD_ORDER)) {
            product.setUseYn("N");
            product.setWarning(true);
            product.setWarningMsg(true);
            return;
        }

        // 4) 단일상품 판매상태 : 판매중
        if (!StringUtil.equals(product.getSelStatCd(), ProductConstDef.PRD_SEL_STAT_CD_SALE)) {
            product.setUseYn("Y");
            product.setWarning(true);
            product.setWarningMsg(false);
            return;
        }

        product.setUseYn("Y");
        product.setWarning(false);
        product.setWarningMsg(false);
    }

    private int getGroupCtgrLevel(long dispCtgr1No) {

        int level = 2;

        if (commonService.getCodeDetail(ProductConstDef.COMMON_CODE_PRD_GRP_LEVEL1_CTGR_NO, String.valueOf(dispCtgr1No)) != null) {
            level = 1;
        }
        return level;
    }
}
