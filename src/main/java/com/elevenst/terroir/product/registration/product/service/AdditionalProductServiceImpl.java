package com.elevenst.terroir.product.registration.product.service;

import com.elevenst.common.process.RegistrationInfoConverter;
import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.VOUtil;
import com.elevenst.terroir.product.registration.common.code.BsnDealClf;
import com.elevenst.terroir.product.registration.option.entity.PdStock;
import com.elevenst.terroir.product.registration.option.mapper.OptionMapper;
import com.elevenst.terroir.product.registration.product.code.CreateCdTypes;
import com.elevenst.terroir.product.registration.product.code.DlvCstInstBasiCd;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.code.PrdCompTypCd;
import com.elevenst.terroir.product.registration.product.code.PrdStckStatCd;
import com.elevenst.terroir.product.registration.product.code.SelMthdCd;
import com.elevenst.terroir.product.registration.product.code.SetTypCd;
import com.elevenst.terroir.product.registration.product.entity.DpPrdContSummary;
import com.elevenst.terroir.product.registration.product.entity.PdAddPrdComp;
import com.elevenst.terroir.product.registration.product.entity.PdAddPrdGrp;
import com.elevenst.terroir.product.registration.product.entity.PdPrd;
import com.elevenst.terroir.product.registration.product.entity.PdPrdAdditionHist;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.mapper.ProductMapper;
import com.elevenst.terroir.product.registration.product.validate.AdditionalProductValidate;
import com.elevenst.terroir.product.registration.product.vo.ProductAddCompositionVO;
import com.elevenst.terroir.product.registration.product.vo.ProductCoordiVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.mapper.SellerMapper;
import com.elevenst.terroir.product.registration.product.code.SelStatCd;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AdditionalProductServiceImpl implements RegistrationInfoConverter<ProductVO>{

    private final String SY_COL_PROP_ADDT_PRD = "143";
    private final long PRODUCT_WEIGHT_LIMIT = 30000L;


    @Autowired
    ProductMapper productMapper;

    @Autowired
    SellerMapper sellerMapper;

    @Autowired
    OptionMapper optionMapper;

    @Autowired
    VOUtil voUtil;

    @Autowired
    AdditionalProductValidate additionalProductValidate;

    /**
     * 추가구성상품 생성
     * */
    public void insertAdditionalProductProcess(ProductVO productVO) {

        if(StringUtils.equals(productVO.getOfferDispLmtYn(), "Y")) {
            return;
        }

        long templatePrdNo = productVO.getBaseVO().getTemplatePrdNo();
        String tmpCol = productVO.getOptionVO().getTmpColMap().get(SY_COL_PROP_ADDT_PRD);
        if(templatePrdNo > 0 && !StringUtils.equals(tmpCol, "Y") && isExistAdditionalProduct(templatePrdNo)) {
            insertAddProductInformationSimple(productVO);
        }
        else {
            insertAddProductInformation(productVO);
        }

    }

    public void updateAdditionalProductProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        this.updateAddCompositionProductSellStatus(productVO);
    }

    private boolean isExistAdditionalProduct(long mainPrdNo) {

        long result = productMapper.isExistAdditionalProduct(mainPrdNo);
        return result > 0 ? true : false;

    }

    /**
     * 추가구성상품 심플 insert
     * 대상 테이블 리스트
     *  - PD_ADD_PRD_COMP_MAP_GT (from PD_ADD_PRD_COMP data)
     *  - PD_ADD_PRD_GRP (from another PD_ADD_PRD_GRP rowdata)
     *  - PD_ADD_PRD_COMP (from PD_ADD_PRD_COMP, PD_ADD_PRD_COMP_MAP_GT)
     *  - PD_STOCK (from PD_STOCK, PD_ADD_PRD_COMP_MAP_GT )
     *  - PD_PRD (from PD_PRD, PD_ADD_PRD_COMP_MAP_GT)
     *  - PD_PRD_ADDITION_HIST (from PD_PRD, PD_ADD_PRD_COMP_MAP_GT)
     * */
    private void insertAddProductInformationSimple(ProductVO productVO) {

        // PD_ADD_PRD_COMP_MAP_GT (from PD_ADD_PRD_COMP data)
        productMapper.insertPdAddPrdCompMapGtFROMPdAddPrdComp(productVO);

        // PD_ADD_PRD_GRP (from another PD_ADD_PRD_GRP rowdata)
        PdAddPrdGrp pdAddPrdGrp = new PdAddPrdGrp();
        pdAddPrdGrp.setMainPrdNo(productVO.getPrdNo());
        pdAddPrdGrp.setCreateNo(productVO.getCreateNo());
        pdAddPrdGrp.setUpdateNo(productVO.getUpdateNo());
        pdAddPrdGrp.setMainPrdNo(productVO.getBaseVO().getTemplatePrdNo());
        productMapper.insertPdAddPrdGrpUseSelectFrom(pdAddPrdGrp);

        // PD_ADD_PRD_COMP (from PD_ADD_PRD_COMP, PD_ADD_PRD_COMP_MAP_GT)
        productMapper.insertPdAddPrdCompFROMJoinTables(productVO);

        // PD_STOCK (from PD_STOCK, PD_ADD_PRD_COMP_MAP_GT );
        productMapper.insertPdStockFROMJoinTables(productVO);

        // PD_PRD (from PD_PRD, PD_ADD_PRD_COMP_MAP_GT)
        productMapper.insertPdPrdFROMJoinTables(productVO);

        // PD_PRD_ADDITION_HIST (from PD_PRD, PD_ADD_PRD_COMP_MAP_GT)
        productMapper.insertPdPrdAddHistFROMJoinTabled(productVO);

    }

    private void insertAddProductInformation(ProductVO productVO) {

        //init
        long prdNo = productVO.getPrdNo();
        long dispPrrtRnk = productVO.getCategoryVO().getDispPrrtRnk();
        ProductVO oldProductVO = new ProductVO();
        if(prdNo <= 0) {
            return;
        }

        // 상품 추가구성 정보를 담아 VO에 세팅(AS-IS데이터 백업)
        List<ProductAddCompositionVO> compositionVOList = productMapper.getProductAddCompositionList(prdNo);
        oldProductVO.setProductAddCompositionVOList(compositionVOList);

        //추가구성그룹 삭제(재추가를 위해 AS-IS데이터 삭제)
        productMapper.deletePdAddPrdGrp(prdNo);
        //추가구성상품 USE_YN Delete로 세팅
        productMapper.updateSetDelStatePdAddPrdComp(prdNo);

        //추가구성그룹 다시 적재
        long addPrdGrpCount = 0;
        Map<String, Long> addPrdGrpMap = new HashMap<String, Long>();

        if(productVO.getProductAddCompositionVOList() != null){

            int productAddCmpSize = productVO.getProductAddCompositionVOList().size();
            for(int i = 0 ; i < productAddCmpSize; i++) {
                ProductAddCompositionVO prdCmpVO = productVO.getProductAddCompositionVOList().get(i);

                //추가구성그룹에 addPrdGrpNm이 존재하지 않으면 그룹으로 등록
                String addPrdGrpNm = prdCmpVO.getAddPrdGrpNm();
                if (addPrdGrpMap.get(addPrdGrpNm) == null) {
                    addPrdGrpMap.put(addPrdGrpNm, ++addPrdGrpCount);

                    //PD_ADD_PRD_GRP insert.
                    PdAddPrdGrp pdAddPrdGrpEntity = new PdAddPrdGrp();
                    pdAddPrdGrpEntity.setAddPrdGrpNm(addPrdGrpNm);
                    pdAddPrdGrpEntity.setAddPrdGrpNo(addPrdGrpCount);
                    pdAddPrdGrpEntity.setDispPrrtRnk(dispPrrtRnk);
                    pdAddPrdGrpEntity.setMainPrdNo(prdNo);
                    pdAddPrdGrpEntity.setCreateNo(prdCmpVO.getUpdateNo());
                    pdAddPrdGrpEntity.setUpdateNo(prdCmpVO.getUpdateNo());
                    productMapper.insertPdAddPrdGrp(pdAddPrdGrpEntity);

                }

                prdCmpVO.setAplBgnDy(productVO.getBaseVO().getSelBgnDy());
                prdCmpVO.setAplEndDy(productVO.getBaseVO().getSelEndDy());
                prdCmpVO.setMainPrdNo(prdNo);
                prdCmpVO.setMainPrdYn("N");
                prdCmpVO.setSelPrc(prdCmpVO.getAddCompPrc());
                prdCmpVO.setAddPrdGrpNo(addPrdGrpMap.get(addPrdGrpNm).longValue());

                //기존의 추가구성 상품인 경우
                if(prdCmpVO.getPrdCompNo() > 0) {
                    ProductAddCompositionVO oldCmpVO = oldProductVO.getProductAddCompositionVOList().get(i);
                    updateAddProduct(prdCmpVO, oldCmpVO, productVO);
                }
                else { //새로운 추가구성 상품인 경우
                    insertAddProduct(prdCmpVO, productVO);
                }
            }

            if (dispPrrtRnk > 0) {
                //소팅한 순서대로 추가구성상품 조회.
                List<ProductAddCompositionVO> productAddCompositionVOList = productMapper.getProductAddCompositionListSortByDispPrrtRnk(productVO);

                for(ProductAddCompositionVO elem : productAddCompositionVOList) {
                    elem.setAplBgnDy("");
                    elem.setAplEndDy("");
                    //소팅순서대로 update.
                    productMapper.updateProductComposition(elem);
                }

            }
        }
    }

    private void updateAddProduct(ProductAddCompositionVO newCompositionVO, ProductAddCompositionVO oldCompositionVO, ProductVO productVO){

        if(newCompositionVO.getAddPrdWght() >= PRODUCT_WEIGHT_LIMIT) {
            throw new ProductException("추가상품 수정시 무게 오류");
        }

        //추가구성상품 품절인 상태에서 다시 수량 추가시 재고수량 및 상태 UPDATE
        PdStock pdStock = new PdStock();
        pdStock.setStockNo(newCompositionVO.getCompPrdStckNo());
        pdStock.setStckQty(newCompositionVO.getStckQty());
        if(pdStock.getStckQty() > 0) {
            pdStock.setPrdStckStatCd(PrdStckStatCd.USING.getDtlsCd());
        }
        else {
            pdStock.setPrdStckStatCd(PrdStckStatCd.SOLD_OUT.getDtlsCd());
        }
        //상품재고 업데이트
        productMapper.updateProductStockQty(pdStock);

        //상품마스터 판매상태 업데이트 : 재고수량이 있고, 판매상태가 품절일 경우 판매상태를 판매중으로 UPDATE
        String updatePrdSelStatCd = newCompositionVO.getSelStatCd();
        if(newCompositionVO.getStckQty() > 0) {

            switch(GroupCodeUtil.fromString(SelStatCd.class, updatePrdSelStatCd)){
                case PRODUCT_SOLD_OUT:
                    updatePrdSelStatCd = SelStatCd.PRODUCT_SELLING.getDtlsCd();
                    break;
                case AUCTION_BID_SUCCESS_END:
                    updatePrdSelStatCd = SelStatCd.AUCTION_SELLING.getDtlsCd();
                    break;
                default:
                    break;
            }
        }
        //상품테이블에 추가구성상품 업데이트
        PdPrd updatePdPrd = new PdPrd();
        updatePdPrd.setPrdNo(newCompositionVO.getCompPrdNo());
        updatePdPrd.setSelStatCd(updatePrdSelStatCd);
        //updatePdPrd.setCstmAplPrc(newCompositionVO.getAddCstmAplPrc()); //AS-IS 실제 쿼리에서도 쓰이지 않고, PD_PRD에도 나타나 있지 않는 property

        updatePdPrd.setMstrPrdNo(productVO.getBaseVO().getMstrPrdNo());
        updatePdPrd.setSelMnbdNo(productVO.getSelMnbdNo());
        updatePdPrd.setSelMnbdNckNmSeq(productVO.getSellerVO().getSelMnbdNckNmSeq());
        updatePdPrd.setPrdNm(newCompositionVO.getCompPrdNm());
        updatePdPrd.setSelBgnDy(productVO.getBaseVO().getSelBgnDy());
        updatePdPrd.setSelEndDy(productVO.getBaseVO().getSelEndDy());
        updatePdPrd.setSelMnbdClfCd(productVO.getBaseVO().getSelMnbdClfCd());
        updatePdPrd.setSelMthdCd(productVO.getBaseVO().getSelMthdCd());
        updatePdPrd.setPrdCompTypCd(PrdCompTypCd.ADD_PRODUCT.getDtlsCd());
        updatePdPrd.setDlvCnAreaCd(productVO.getBaseVO().getDlvCnAreaCd());
        updatePdPrd.setDlvWyCd(productVO.getBaseVO().getDlvWyCd());
        updatePdPrd.setDlvCstPayTypCd(productVO.getBaseVO().getDlvCstPayTypCd());
        updatePdPrd.setBndlDlvCnYn(productVO.getBaseVO().getBndlDlvCnYn());
        updatePdPrd.setTodayDlvCnYn(productVO.getBaseVO().getTodayDlvCnYn());
        updatePdPrd.setAppmtDyDlvCnYn(productVO.getBaseVO().getAppmtDyDlvCnYn());
        updatePdPrd.setSndPlnTrm(productVO.getBaseVO().getSndPlnTrm());
        updatePdPrd.setRcptIsuCnYn(productVO.getBaseVO().getRcptIsuCnYn());
        updatePdPrd.setPrdTypCd(productVO.getBaseVO().getPrdTypCd());
        updatePdPrd.setMinorSelCnYn(productVO.getBaseVO().getMinorSelCnYn());
        updatePdPrd.setDlvCstInstBasiCd(DlvCstInstBasiCd.FREE.getDtlsCd());
        updatePdPrd.setDlvCst(0);
        updatePdPrd.setOrgnTypCd(productVO.getBaseVO().getOrgnTypCd());
        updatePdPrd.setUnDlvCnYn(productVO.getBaseVO().getUnDlvCnYn());
        updatePdPrd.setSelPrdClfCd(productVO.getBaseVO().getSelPrdClfCd());
        updatePdPrd.setCreateNo(productVO.getCreateNo());
        updatePdPrd.setUpdateNo(newCompositionVO.getUpdateNo());
        updatePdPrd.setBsnDealClf(productVO.getBaseVO().getBsnDealClf());
        updatePdPrd.setPrdWght(newCompositionVO.getAddPrdWght());
        updatePdPrd.setSellerPrdCd(newCompositionVO.getSellerAddPrdCd());
        updatePdPrd.setSuplDtyfrPrdClfCd(newCompositionVO.getCompPrdVatCd());

        //상품 업데이트 & 상품히스토리 적재
        productMapper.updateProduct(updatePdPrd);
        productMapper.insertPdPrdHist(updatePdPrd);

        if (oldCompositionVO.getCompPrdNo() == newCompositionVO.getCompPrdNo() &&
            oldCompositionVO.getAddPrdWght() != newCompositionVO.getAddPrdWght()) {

            PdPrdAdditionHist pdPrdAdditionHist = new PdPrdAdditionHist();
            pdPrdAdditionHist.setPrdNo(updatePdPrd.getPrdNo());
            pdPrdAdditionHist.setPrdWght(updatePdPrd.getPrdWght());
            pdPrdAdditionHist.setUpdateNo(updatePdPrd.getUpdateNo());
            productMapper.insertPdPrdAdditionHist(pdPrdAdditionHist);
        }


        //추가구성 업데이트
        PdAddPrdComp pdAddPrdComp = new PdAddPrdComp();
        pdAddPrdComp.setAddPrdGrpNo(newCompositionVO.getAddPrdGrpNo());
        pdAddPrdComp.setSelPrc(newCompositionVO.getSelPrc());
        pdAddPrdComp.setAddCompPrc(newCompositionVO.getAddCompPrc());
        pdAddPrdComp.setStckQty(newCompositionVO.getStckQty());
        pdAddPrdComp.setUpdateNo(newCompositionVO.getUpdateNo());
        pdAddPrdComp.setUseYn(newCompositionVO.getUseYn());
        pdAddPrdComp.setCstmAplPrc((long)newCompositionVO.getAddCstmAplPrc()*100L);
        pdAddPrdComp.setCompPrdNm(newCompositionVO.getCompPrdNm());
        pdAddPrdComp.setDispPrrtRnk(newCompositionVO.getDispPrrtRnk());
        pdAddPrdComp.setPrdCompNo(newCompositionVO.getPrdCompNo());
        productMapper.updateAddPrdComp(pdAddPrdComp);
    }


    private void insertAddProduct(ProductAddCompositionVO newCompositionVO, ProductVO productVO) {

        if(newCompositionVO.getCompPrdNo() > 0) {
            return;
        }
        if(newCompositionVO.getAddPrdWght() >= PRODUCT_WEIGHT_LIMIT) {
            throw new ProductException("추가상품 무게 오류");
        }

        //상품 등록
        long addPrdNo = productMapper.getProductNo();
        PdPrd pdPrd = new PdPrd();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        pdPrd.setPrdNo(addPrdNo);
        pdPrd.setSelStatCd(productVO.getBaseVO().getSelStatCd());
        //pdPrd.setHistAplEndDtStr(sdf.format(new Date()));
        //pdPrd.setAddPrdYn("Y");

        pdPrd.setMstrPrdNo(productVO.getBaseVO().getMstrPrdNo());
        pdPrd.setSelMnbdNo(productVO.getSelMnbdNo());
        pdPrd.setSelMnbdNckNmSeq(productVO.getSellerVO().getSelMnbdNckNmSeq());
        pdPrd.setPrdNm(newCompositionVO.getCompPrdNm());
        pdPrd.setSelBgnDy(productVO.getBaseVO().getSelBgnDy());
        pdPrd.setSelEndDy(productVO.getBaseVO().getSelEndDy());
        pdPrd.setSelMnbdClfCd(productVO.getBaseVO().getSelMnbdClfCd());
        pdPrd.setSelMthdCd(productVO.getBaseVO().getSelMthdCd());
        pdPrd.setPrdCompTypCd(PrdCompTypCd.ADD_PRODUCT.getDtlsCd());
        pdPrd.setDlvCnAreaCd(productVO.getBaseVO().getDlvCnAreaCd());
        pdPrd.setDlvWyCd(productVO.getBaseVO().getDlvWyCd());
        pdPrd.setDlvCstPayTypCd(productVO.getBaseVO().getDlvCstPayTypCd());
        pdPrd.setBndlDlvCnYn(productVO.getBaseVO().getBndlDlvCnYn());
        pdPrd.setTodayDlvCnYn(productVO.getBaseVO().getTodayDlvCnYn());
        pdPrd.setAppmtDyDlvCnYn(productVO.getBaseVO().getAppmtDyDlvCnYn());
        pdPrd.setSndPlnTrm(productVO.getBaseVO().getSndPlnTrm());
        pdPrd.setRcptIsuCnYn(productVO.getBaseVO().getRcptIsuCnYn());
        pdPrd.setPrdTypCd(productVO.getBaseVO().getPrdTypCd());
        pdPrd.setMinorSelCnYn(productVO.getBaseVO().getMinorSelCnYn());
        pdPrd.setDlvCstInstBasiCd(DlvCstInstBasiCd.FREE.getDtlsCd());
        pdPrd.setDlvCst(0);
        pdPrd.setOrgnTypCd(productVO.getBaseVO().getOrgnTypCd());
        pdPrd.setUnDlvCnYn(productVO.getBaseVO().getUnDlvCnYn());
        pdPrd.setSelPrdClfCd(productVO.getBaseVO().getSelPrdClfCd());
        pdPrd.setCreateNo(productVO.getCreateNo());
        pdPrd.setUpdateNo(newCompositionVO.getUpdateNo());
        pdPrd.setBsnDealClf(productVO.getBaseVO().getBsnDealClf());
        pdPrd.setPrdWght(newCompositionVO.getAddPrdWght());
        pdPrd.setSellerPrdCd(newCompositionVO.getSellerAddPrdCd());
        pdPrd.setSuplDtyfrPrdClfCd(newCompositionVO.getCompPrdVatCd());

        if (sellerMapper.isCupnExcldSeller(pdPrd.getSelMnbdNo()) > 0) {
            pdPrd.setCupnExCd("01");
            pdPrd.setLpCupnExYn("Y");
            pdPrd.setPartnerCupnExYn("Y");
        }
        productMapper.insertPdPrd(pdPrd);

        if(GroupCodeUtil.equalsDtlsCd(pdPrd.getBsnDealClf(), BsnDealClf.COMMISSION) &&
            GroupCodeUtil.equalsDtlsCd(pdPrd.getSetTypCd(), SetTypCd.BUNDLE)&&
            GroupCodeUtil.equalsDtlsCd(pdPrd.getSelStatCd(), SelStatCd.PRODUCT_SOLD_OUT)) {

            //상품정보 저장
            ProductVO resultVO = productMapper.getOnlySearchPdPrd(pdPrd.getPrdNo());
            long prdStckQty = 0;
            if(resultVO!=null && GroupCodeUtil.equalsDtlsCd(resultVO.getBaseVO().getSetTypCd(), SetTypCd.BUNDLE)) {
                prdStckQty = optionMapper.getBundleProductStckCnt(pdPrd.getPrdNo());
            }
            else {
                prdStckQty = optionMapper.getProductStckCnt(pdPrd.getPrdNo());
            }

            if(prdStckQty > 0) {
                PdPrd selStatCdChng = new PdPrd();
                selStatCdChng.setPrdNo(pdPrd.getPrdNo());
                selStatCdChng.setSelStatCd(SelStatCd.PRODUCT_SELLING.getDtlsCd());
                productMapper.updatePdPrdSelStatCd(selStatCdChng);
            }
        }

        /*
         * 1원폰 여부 확인 후 등록
         * 추가구성상품 등록시에는 호출할 필요 없는 로직이다.
         */
        //approveSaleFeeMediation(productVO);

        //상품 history 저장
        productMapper.insertPdPrdHist(pdPrd);


        //상품 실시간 집계정보 초기데이터 생성 & 추가상품은 Summary 정보에서 삭제.
        DpPrdContSummary dpPrdContSummary = new DpPrdContSummary();
        dpPrdContSummary.setPrdNo(pdPrd.getPrdNo());
        dpPrdContSummary.setAbrdBrandYn(pdPrd.getAbrdBrandYn());
        dpPrdContSummary.setCreateNo(pdPrd.getUpdateNo());
        dpPrdContSummary.setUpdateNo(pdPrd.getUpdateNo());
        productMapper.insertProductSummary(dpPrdContSummary);

        /*
         * 상품 등록시 소호상품 같은경우 코디상품 같이 등록(prdTypCd = 24)
         */
        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getPrdTypCd(), PrdTypCd.SOHO)) {
            List<ProductCoordiVO> productCoordiVOList = productVO.getProductCoordiVOList();
            productMapper.deleteProductCoordi(productVO.getPrdNo());
            for(ProductCoordiVO elem : productCoordiVOList) {
                elem.setPrdNo(productVO.getPrdNo());
                elem.setCreateNo(productVO.getUpdateNo());
                productMapper.insertProductCoordi(elem);
            }
        }

        PdPrdAdditionHist pdPrdAdditionHist = new PdPrdAdditionHist();
        pdPrdAdditionHist.setPrdNo(pdPrd.getPrdNo());
        pdPrdAdditionHist.setPrdWght(pdPrd.getPrdWght());
        pdPrdAdditionHist.setUpdateNo(pdPrd.getUpdateNo());
        productMapper.insertPdPrdAdditionHist(pdPrdAdditionHist);


        //재고등록
        PdStock insertPdStock = new PdStock();
        long stockNo = productMapper.getProductStockNo();
        insertPdStock.setStockNo(stockNo);
        insertPdStock.setPrdNo(addPrdNo);
        insertPdStock.setSelQty(0);
        insertPdStock.setPrdStckStatCd(PrdStckStatCd.USING.getDtlsCd());
        insertPdStock.setStckQty(newCompositionVO.getCompPrdQty());
        insertPdStock.setCreateNo(newCompositionVO.getUpdateNo());
        productMapper.insertPdStock(insertPdStock);

        //추가구성정보 입력
        newCompositionVO.setStckQty(newCompositionVO.getCompPrdQty()); //값 복사....
        newCompositionVO.setAplBgnDy(productVO.getBaseVO().getSelBgnDy());
        newCompositionVO.setAplEndDy(productVO.getBaseVO().getSelEndDy());
        newCompositionVO.setMainPrdYn("N");
        newCompositionVO.setSelPrc(newCompositionVO.getAddCompPrc());
        newCompositionVO.setUseYn(newCompositionVO.getUseYn());
        newCompositionVO.setCompPrdStckNo(stockNo);
        newCompositionVO.setCompPrdNo(addPrdNo);
        long productCompositionNo = productMapper.getProductCompositionNo();
        newCompositionVO.setPrdCompNo(productCompositionNo);

        newCompositionVO.setPrdCompTypCd(PrdCompTypCd.ADD_PRODUCT.getDtlsCd());
        productMapper.insertProductComposition(newCompositionVO);

    }

    /**
     *  상품수정 시 추가구성상품의 PD_PRD 판매상태 변경
     * */
    public void updateAddCompositionProductSellStatus(ProductVO productVO) {

        if(!StringUtils.equals("Y", productVO.getOfferDispLmtYn())) {
            long addCompositionProductCount = productMapper.getAddCompPrdCount(productVO.getPrdNo());
            if(addCompositionProductCount > 0) {
                productMapper.updatePdPrdAddCompositionProductSellStatus(productVO);
            }
        }
    }

    //여러명이 작업했음. ㅠㅠ
    @Deprecated
    public void insertAdditionalProductWhenCallByApi(ProductVO productVO) {

        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getCreateCd(), CreateCdTypes.SELLER_API) &&
            !voUtil.isEmptyVO(productVO.getProductAddCompositionVOList())) {

            additionalProductValidate.checkRentalProductAdditional(productVO.getBaseVO().getPrdTypCd());
            insertAdditionalProductProcess(productVO);
        }

    }

    @Deprecated
    public long getProductCompositionCount(ProductVO productVO) {
        return productMapper.getAddCompPrdCount(productVO.getPrdNo());
    }

    public long getProductCompositionCount(long prdNo) {
        return productMapper.getAddCompPrdCount(prdNo);
    }


    public void setAdditionInfoProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        additionalProductValidate.checkRentalProductAdditional(productVO);
    }

    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{

        this.convertAddPrdInfo(preProductVO, productVO);
    }

    private void convertAddPrdInfo(ProductVO preProductVO, ProductVO productVO) {
        if(GroupCodeUtil.isContainsDtlsCd(productVO.getBaseVO().getSelMthdCd(), SelMthdCd.FIXED, SelMthdCd.COBUY, SelMthdCd.USED)){
            if("Y".equals(productVO.getMobile1WonYn())){
                additionalProductValidate.checkAddProductVOList(productVO);
            }
        }
    }

}
