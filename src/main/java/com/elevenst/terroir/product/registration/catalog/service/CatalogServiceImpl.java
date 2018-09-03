package com.elevenst.terroir.product.registration.catalog.service;

import com.elevenst.common.util.DBSwitchUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.catalog.entity.PdPrdModel;
import com.elevenst.terroir.product.registration.catalog.exception.CatalogException;
import com.elevenst.terroir.product.registration.catalog.mapper.CatalogMapper;
import com.elevenst.terroir.product.registration.catalog.vo.CatalogRecmVO;
import com.elevenst.terroir.product.registration.catalog.vo.ItgModelInfoVO;
import com.elevenst.terroir.product.registration.catalog.vo.PrdModelVO;
import com.elevenst.terroir.product.registration.option.entity.PdStock;
import com.elevenst.terroir.product.registration.option.mapper.OptionMapper;
import com.elevenst.terroir.product.registration.option.vo.ProductStockVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.entity.PdPrd;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Slf4j
@Service
public class CatalogServiceImpl {

    @Autowired
    CatalogMapper catalogMapper;

    @Autowired
    OptionMapper optionMapper;

    @Autowired
    DBSwitchUtil switchUtil;

    // 상품 저장 시 상품모델 사용여부 체크
    public boolean getCtlgModelNoCheck(HashMap map) throws CatalogException {
        try{
            int cnt = catalogMapper.getCtlgModelNoCheck(map);
            return cnt>0?true:false;
        }catch (Exception e) {
            throw new CatalogException("상품모델 사용여부 체크 오류", e);
        }
    }

    public void deleteProductModel(ItgModelInfoVO paramBO) throws CatalogException {
        try {
            catalogMapper.deleteProductModel(paramBO);
        } catch (Exception ex) {
            throw new CatalogException("카탈로그 연결된 상품 삭제 처리 오류", ex);
        }
    }

    public void mergeProductCatalogMatch(ProductStockVO productStockVO) throws CatalogException {
        try {
            catalogMapper.mergeProductCatalogMatch(productStockVO);
        } catch (Exception ex) {
            throw new CatalogException("카탈로그 매칭 처리 오류", ex);
        }
    }

    public void mergeProductModelInfo(CatalogRecmVO catalogRecmVO) throws CatalogException {
        try {
            catalogMapper.mergeProductModelInfo(catalogRecmVO);
        } catch (Exception ex) {
            throw new CatalogException("상품 모델정보 수정 처리 오류", ex);
        }
    }

    // 상품모델 검색, 카탈로그
    public List getProductCtlgModelInfo(HashMap paramMap) throws CatalogException {
        List resultList = null;
        try {
            resultList = catalogMapper.getProductCtlgModelInfo(paramMap);
        }
        catch(Exception e) {
            throw new CatalogException("에누리 모델 정보 조회 오류", e);
        }
        return resultList;
    }

    @Deprecated
    public void insertCtlgInfoProcess(ProductVO productVO) throws CatalogException{
        //TODO CYB 카탈로그 매칭 관련 다시 확인 해볼것
        PdPrd pdPrd = new PdPrd();
        pdPrd.setPrdNo(productVO.getPrdNo());
        pdPrd.setSelMnbdNo(productVO.getSelMnbdNo());
        pdPrd.setSellerPrdCd(productVO.getBaseVO().getSellerPrdCd());
        pdPrd.setCreateNo(productVO.getCreateNo());
        pdPrd.setUpdateNo(productVO.getUpdateNo());

        catalogMapper.insertProductCatalogMatchInfo(pdPrd);
    }

    public void setCatalogInfoProcess(ProductVO preProductVO, ProductVO productVO) throws CatalogException{

        String special[] = ProductConstDef.CTLG_MODEL_NOT_ALLOW_SPECIAL_CHAR;
        // 특수문자 Check
        for(int n=0; n<special.length; n++){
            if(productVO.getPrdModelVO().getModelCdVal().indexOf(special[n]) > 0) {
                throw new CatalogException("모델코드는 특수문자 입력이 불가능합니다.");
            }
        }

        if(productVO.isUpdate()){
//            PrdModelVO prePrdModelNo = catalogMapper.getProductModel(productVO.getPrdModelVO());
        }else{

        }
    }

    public void mergeCtlgInfoProcess(ProductVO preProductVO, ProductVO productVO) throws CatalogException{

        if(StringUtil.isEmpty(productVO.getPrdModelVO().getMnbdClfCd())){
            if(productVO.getPrdModelVO().getModelNo() == -1){ // 직접입력일 경우 유형 세팅
                productVO.getPrdModelVO().setMnbdClfCd(ProductConstDef.MNBD_CLF_CD_TEXT_INPUT);
            } else if(ProductConstDef.SELLEROFFICE_TYPE.equals(productVO.getChannel())){
                productVO.getPrdModelVO().setMnbdClfCd(ProductConstDef.MNBD_CLF_CD_SELLER);
            }
        }

        PdPrdModel pdPrdModel = new PdPrdModel();
        copyProperties(productVO.getPrdModelVO(), pdPrdModel);
        pdPrdModel.setPrdNo(productVO.getPrdNo());
        pdPrdModel.setCreateNo(productVO.getUpdateNo());

        catalogMapper.mergePdPrdModel(pdPrdModel);
    }

    /**
     * 에누리 상품 등록
     * @param productVO
     * @throws CatalogException
     */
    public void setEnuriModel(ProductVO productVO) throws CatalogException {

        String modelNm = StringUtil.nvl(productVO.getPrdModelVO().getModelNm());
        String modelCd = StringUtil.nvl(productVO.getPrdModelVO().getModelCd());
        String modelNo = "-1";

        if(0 != productVO.getPrdModelVO().getCtlgModelNo()) {
            modelNo = String.valueOf(productVO.getPrdModelVO().getCtlgModelNo());
        }

        if((StringUtil.isEmpty(modelNo) || "0".equals(modelNo) || "-1".equals(modelNo)) && StringUtil.isEmpty(modelNm) && StringUtil.isEmpty(modelCd)) modelNo = null;

        boolean isDirectWrite = "-1".equals(modelNo);
        String mnbdClfCd = isDirectWrite ? "10" : ProductConstDef.MNBD_CLF_CD_OPT;

        if(isDirectWrite){
            if(!"".equals(modelNm)){
                String special[] = ProductConstDef.CTLG_MODEL_NOT_ALLOW_SPECIAL_CHAR;
                // 특수문자 Check
                for(int n=0; n<special.length; n++){
                    if(modelNm.indexOf(special[n]) > 0) {
                        throw new CatalogException("모델명은 특수문자 입력이 불가능합니다.");
                    }
                }
            }

            if(!"".equals(modelCd)){
                String special[] = ProductConstDef.CTLG_MODEL_NOT_ALLOW_SPECIAL_CHAR;
                // 특수문자 Check
                for(int n=0; n<special.length; n++){
                    if(modelCd.indexOf(special[n]) > 0) {
                        throw new CatalogException("모델코드는 특수문자 입력이 불가능합니다.");
                    }
                }
            }

            if(StringUtil.isNotEmpty(modelNm) && (ProductConstDef.PRD_TYP_CD_MART.equals(productVO.getBaseVO().getPrdTypCd()) || ProductConstDef.PRD_TYP_CD_NEW_MART.equals(productVO.getBaseVO().getPrdTypCd()))){
                throw new CatalogException("마트상품은 모델명 직접입력이 불가능합니다");
            }
        }else{
            if (StringUtil.isNotEmpty(modelNo)) {
                // 마트, 신선 카테고리인지 체크
                if( ProductConstDef.PRD_TYP_CD_MART.equals(productVO.getBaseVO().getPrdTypCd()) || ProductConstDef.PRD_TYP_CD_NEW_MART.equals(productVO.getBaseVO().getPrdTypCd()) ) {
                    mnbdClfCd = ProductConstDef.MNBD_CLF_CD_OPT;
                } else {
                    mnbdClfCd = ProductConstDef.MNBD_CLF_CD_SELLER;
                }

                PrdModelVO prdModelVO = new PrdModelVO();
                // 유효한 카탈로그 인지 체크
                boolean isEffective = false;

                prdModelVO.setCtlgNo(StringUtil.getLong(modelNo));
                prdModelVO.setMode(productVO.getPrdModelVO().getMode());

                prdModelVO = catalogMapper.getCatalogDetailInfo(prdModelVO);

                if( prdModelVO != null && ProductConstDef.CTLG_STAT_CD_APRV.equals(prdModelVO.getCtlgStatCd()) && "Y".equals(prdModelVO.getUseYn()) ) {
                    if( ProductConstDef.MNBD_CLF_CD_OPT.equals(mnbdClfCd) && ProductConstDef.CTLG_TYP_CD_OPT.equals(prdModelVO.getCtlgTypCd()) ) {
                        isEffective = true;
                    } else if( ProductConstDef.MNBD_CLF_CD_SELLER.equals(mnbdClfCd) && ProductConstDef.CTLG_TYP_CD_PRD.equals(prdModelVO.getCtlgTypCd()) ) {
                        isEffective = true;
                    }
                }

                if( !isEffective ) {
                    modelNo = null;
                }
            }
        }

        PrdModelVO prdModelVO = new PrdModelVO();
        prdModelVO.setPrdNo(productVO.getPrdNo());
        prdModelVO.setModelNo(Long.parseLong(modelNo));
        prdModelVO.setModelCd(modelCd);
        prdModelVO.setModelNm(modelNm);
        prdModelVO.setMnbdClfCd(mnbdClfCd);
        prdModelVO.setMemNo(productVO.getUpdateNo());

        boolean isDeleteModel = false;

        if( "Y".equals(productVO.getUpdateYn())) {
            // 기존 등록된 모델 정보가 있는지 체크 후 있다면 현재 등록하려는 모델 번호와 동일 한지 체크하고 다르면 전체 삭제
            PrdModelVO getPrdModel = catalogMapper.getProductModel(prdModelVO);

            if(getPrdModel != null) {
                // 같은 정보가 있다면 아래 등록 처리 안하기 위함.
                if( StringUtil.nvl(modelNo).equals(StringUtil.nvl(getPrdModel.getModelNo())) ) {
                    if(!isDirectWrite) modelNo = null;
                } else {
                    isDeleteModel = true;

                    ItgModelInfoVO itgModelVO = new ItgModelInfoVO();
                    itgModelVO.setPrdNo(productVO.getPrdNo());
                    itgModelVO.setMatchClfCd(ProductConstDef.MATCH_CLF_CD_PRD);
                    itgModelVO.setMatchCtlgNo(getPrdModel.getModelNo());
                    itgModelVO.setUpdateNo(productVO.getUpdateNo());

                    catalogMapper.deleteProductModel(itgModelVO);
                }
            }
        }

        if( isDeleteModel ) {
            catalogMapper.deletePdPrdModel(prdModelVO);
        }

        if (StringUtil.isNotEmpty(modelNo)) {
            PdPrdModel pdPrdModel = new PdPrdModel();
            pdPrdModel.setMnbdClfCd(prdModelVO.getMnbdClfCd());
            // 마트 옵션 매칭이고 옵션이 없는 상품일 경우 승인 프로세스 후 매칭 되도록 타입 분기
            if( ProductConstDef.MNBD_CLF_CD_OPT.equals(mnbdClfCd) ) {
                // 상품 등록이고 옵션이 없을 경우
                if( "N".equals(productVO.getUpdateYn()) && "N".equals(productVO.getBaseVO().getItemInfo() ) ) {
                    mnbdClfCd = ProductConstDef.MNBD_CLF_CD_MART_SELLER;
                    pdPrdModel.setMnbdClfCd(mnbdClfCd);
                }
                // 상품 수정일 경우
                else if( "Y".equals(productVO.getUpdateYn()) ) {
                    // 무옵션 상품 재고정보 조회
                    CatalogRecmVO recmVO = new CatalogRecmVO();
                    recmVO.setPrdNo(productVO.getPrdNo());
                    recmVO.setSearchType("MODEL_CHANGE");
                    recmVO = catalogMapper.getProductStockCheckInfo(recmVO);

                    // 옵션이 없는 상품일 경우
                    if( recmVO != null && recmVO.getStockNo()  > 0 ) {
                        mnbdClfCd = ProductConstDef.MNBD_CLF_CD_MART_SELLER;
                        pdPrdModel.setMnbdClfCd(mnbdClfCd);

                        // 카탈로그 매칭 되어 있을 경우 해제 처리
                        if( recmVO.getCtlgNo() > 0 ) {
                            recmVO.setPrdNo(productVO.getPrdNo());
                            recmVO.setUpdateNo(productVO.getUpdateNo());

                            PdStock pdStock = new PdStock();
                            pdStock.setPrdNo(productVO.getPrdNo());
                            pdStock.setUpdateNo(productVO.getUpdateNo());
                            pdStock.setStockNo(recmVO.getStockNo());

                            // PD_STOCK 매칭 해제
                            optionMapper.updateCutCatalogMatch(pdStock);

                            // PD_STOCK_HIST 등록
                            optionMapper.insertServiceStckQtyHist(recmVO.getStockNo());
                        }
                    }
                }
            }

            pdPrdModel.setCreateNo(productVO.getCreateNo());
            pdPrdModel.setPrdNo(productVO.getPrdNo());
            pdPrdModel.setModelCdVal(prdModelVO.getModelCd());
            pdPrdModel.setModelNm(prdModelVO.getModelNm());
            pdPrdModel.setModelNo(prdModelVO.getModelNo());

            catalogMapper.mergePdPrdModel(pdPrdModel);
        }
    }

    /**
     * 등록하려는 판매자 상품코드로 기 매칭된 카탈로그가 존재 할 경우 자동 매칭 처리
     * @param productVO
     * @throws CatalogException
     */
    public void setMatchCatalog(ProductVO productVO) throws CatalogException {
        try {
            if( StringUtil.isNotEmpty(productVO.getBaseVO().getSellerPrdCd()) ){
                // 스위치 체크
                switchUtil.addKey(ProductConstDef.SWITCH_PRD_CTLG_AUTO_MATCH);
                switchUtil.setUseCache(true);
                switchUtil.checkKey();

                if( !switchUtil.isExist(ProductConstDef.SWITCH_PRD_CTLG_AUTO_MATCH) ) {
                    return;
                }

                PdPrd pdPrd = new PdPrd();

                BeanUtils.copyProperties(productVO, pdPrd);
                pdPrd.setSellerPrdCd(productVO.getBaseVO().getSellerPrdCd());
                catalogMapper.insertProductCatalogMatchInfo(pdPrd);
            }
        } catch(Exception e) {
            // Error Pass
            log.error("setMatchCatalog Error (" + productVO.getPrdNo() + ") : " + e.getMessage(), e);
        }
    }
}
