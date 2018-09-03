package com.elevenst.terroir.product.registration.fee.service;

import com.elevenst.common.process.RegistrationInfoConverter;
import com.elevenst.common.util.DateUtil;
import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import com.elevenst.terroir.product.registration.fee.entity.SeFeeAplRng;
import com.elevenst.terroir.product.registration.fee.entity.SeFeeItm;
import com.elevenst.terroir.product.registration.fee.exception.FeeServiceException;
import com.elevenst.terroir.product.registration.fee.mapper.FeeMapper;
import com.elevenst.terroir.product.registration.fee.validate.FeeValidate;
import com.elevenst.terroir.product.registration.fee.vo.BasketNointerestVO;
import com.elevenst.terroir.product.registration.fee.vo.SaleFeeVO;
import com.elevenst.terroir.product.registration.fee.vo.SeFeeItemVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.code.SelMthdCd;
import com.elevenst.terroir.product.registration.product.vo.ListingItemVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.service.SellerServiceImpl;
import com.elevenst.terroir.product.registration.seller.validate.SellerValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FeeServiceImpl implements RegistrationInfoConverter<ProductVO>{

    @Autowired
    FeeMapper feeMapper;

    @Autowired
    FeeValidate feeValidate;

    @Autowired
    SellerServiceImpl sellerService;

    public void insertSeFeeItmInfoProcess(ProductVO productVO) throws FeeServiceException{
        this.approveSaleFeeMediation(productVO);
    }

    public void updateSeFeeItmInfoProcess(ProductVO preProductVO, ProductVO productVO) throws FeeServiceException{
        this.updateSeFeeEndDt(productVO);
        this.updateSeFeeItmCtgr(preProductVO, productVO);
        this.approveSaleFeeMediation(productVO);
    }

    private void approveSaleFeeMediation(ProductVO productVO) throws FeeServiceException{
        try {
            if(feeValidate.isInsertSeFeeItmProduct(productVO)){
                List<SeFeeItm> seFeeItmList = this.setDefaultSeFeeItm(productVO);
                if(seFeeItmList != null){
                    for(SeFeeItm seFeeItm : seFeeItmList){
                        List<SeFeeItm> list = this.getHasSaleFeeMedPeriodOverlap(seFeeItm);

                        if(list != null && list.size() > 0){
                            this.applyFeeItemDateOverlap(list, seFeeItm);
                            this.insertSeFeeItm(seFeeItm);
                        }
                    }
                }
            }
        }catch (Exception e){
            throw new FeeServiceException("판매수수료 기간 중복처리 오류");
        }
    }

    private void applyFeeItemDateOverlap(List<SeFeeItm> hasOverlapList, SeFeeItm seFeeItm) throws FeeServiceException, Exception{

        if(hasOverlapList != null
            && hasOverlapList.size() > 0){
            for(int j=0 ; j<hasOverlapList.size() ; j++){
                SeFeeItm prdFeeItmBO = hasOverlapList.get(j);
                String perAplBgnDy = prdFeeItmBO.getAplBgnDy();
                String perAplEndDy = prdFeeItmBO.getAplEndDy();
                // 기존 일자의 시작일이 들어오는 시작일보다 크거나 같고 기존 일자 종료일이 들어오는 종료일보다 큰 경우
                if(Long.parseLong(seFeeItm.getAplBgnDy()) <= Long.parseLong(perAplBgnDy)
                    && Long.parseLong(seFeeItm.getAplEndDy()) < Long.parseLong(perAplEndDy))
                {
                    prdFeeItmBO.setAplBgnDy(DateUtil.addSeconds(seFeeItm.getAplEndDy(), 1, "yyyyMMddHHmmss"));
                    prdFeeItmBO.setUpdateDt(seFeeItm.getUpdateDt());
                    prdFeeItmBO.setUpdateNo(seFeeItm.getUpdateNo());
                    this.updateSeFeeItm(prdFeeItmBO);
                }
                // 기존 일자의 시작일이 들어오는 시작일보다 작고 기존 일자 종료일이 들어오는 종료일보다 작거나 같은경우
                else if(Long.parseLong(seFeeItm.getAplBgnDy()) > Long.parseLong(perAplBgnDy)
                    && Long.parseLong(seFeeItm.getAplEndDy()) >= Long.parseLong(perAplEndDy))
                {
                    prdFeeItmBO.setAplEndDy(DateUtil.addSeconds(seFeeItm.getAplBgnDy(),-1,"yyyyMMddHHmmss"));
                    prdFeeItmBO.setUpdateDt(seFeeItm.getUpdateDt());
                    prdFeeItmBO.setUpdateNo(seFeeItm.getUpdateNo());
                    this.updateSeFeeItm(prdFeeItmBO);
                }
                // 기존의 일자가 모두 오버랩되는경우 - 기존 데이터 삭제
                else if(Long.parseLong(seFeeItm.getAplBgnDy()) <= Long.parseLong(perAplBgnDy)
                    && Long.parseLong(seFeeItm.getAplEndDy()) >= Long.parseLong(perAplEndDy))
                {
                    prdFeeItmBO.setHistOccrDt(seFeeItm.getUpdateDt());
                    this.deleteSeFeeItm(prdFeeItmBO);
//                    dao.deleteSeFeeAplRng(prdFeeItmBO);
                }
                // 기존 일자의 시작일이 들어오는 시작일보다 크거나 같고 기존 일자 종료일이 들어오는 종료일보다 크거나 같은경우
                else if(Long.parseLong(seFeeItm.getAplBgnDy()) > Long.parseLong(perAplBgnDy)
                    && Long.parseLong(seFeeItm.getAplEndDy()) < Long.parseLong(perAplEndDy))
                {
                    String tmpAplEndDy = prdFeeItmBO.getAplEndDy();
                    prdFeeItmBO.setAplEndDy(DateUtil.addSeconds(seFeeItm.getAplBgnDy(),-1,"yyyyMMddHHmmss"));
                    prdFeeItmBO.setUpdateDt(seFeeItm.getUpdateDt());
                    prdFeeItmBO.setUpdateNo(seFeeItm.getUpdateNo());
                    this.updateSeFeeItm(prdFeeItmBO);

                    // 구간이 존재하는 경우는 구간도 같이 등록한다.
                    List<SeFeeAplRng> seFeeAplRngList = feeMapper.getSeFeeAplRngByFeeItmNo(prdFeeItmBO);

                    prdFeeItmBO.setFeeItmNo(feeMapper.getSeFeeItmSeq());
                    prdFeeItmBO.setAplBgnDy(DateUtil.addSeconds(seFeeItm.getAplEndDy(),1,"yyyyMMddHHmmss"));
                    prdFeeItmBO.setAplEndDy(tmpAplEndDy);
                    prdFeeItmBO.setCreateDt(seFeeItm.getUpdateDt());
                    prdFeeItmBO.setCreateNo(seFeeItm.getUpdateNo());
                    prdFeeItmBO.setRequestDt(prdFeeItmBO.getRequestDt());

                    if(seFeeAplRngList != null
                        && seFeeAplRngList.size() > 0)
                    {
                        SeFeeAplRng seFeeAplRng = null;
                        List<SeFeeAplRng> seFeeAplRngBOList = new ArrayList();

                        for(int r=0,size=seFeeAplRngList.size(); r < size ; r++){
                            seFeeAplRng = seFeeAplRngList.get(r);
                            seFeeAplRng.setFeeItmNo(prdFeeItmBO.getFeeItmNo());
                            seFeeAplRng.setCreateDt(seFeeItm.getUpdateDt());
                            seFeeAplRng.setCreateNo(seFeeItm.getUpdateNo());
                            seFeeAplRng.setUpdateDt(seFeeItm.getUpdateDt());
                            seFeeAplRng.setUpdateNo(seFeeItm.getUpdateNo());

                            seFeeAplRngBOList.add(seFeeAplRng);
                        }
                        prdFeeItmBO.setSeFeeAplRngBOList(seFeeAplRngBOList);
                    }
                    this.insertSeFeeItm(prdFeeItmBO);
                    this.updateSeFeeAplRng(prdFeeItmBO);
                }
            }
        }

    }

    private List<SeFeeItm> getHasSaleFeeMedPeriodOverlap(SeFeeItm seFeeItm) throws FeeServiceException{
        return feeMapper.getHasSaleFeeMedPeriodOverlap(seFeeItm);
    }

    private List<SeFeeItm> setDefaultSeFeeItm(ProductVO productVO) throws FeeServiceException{
        String[] feeItmNm = {"상품별 고정가 상품판매 조정수수료", "상품별 공동구매 상품판매 조정수수료", "상품별 경매 상품판매 조정수수료"};
        String[] selMthdCds = {"01", "02", "03"};

        List<SeFeeItm> seFeeItmList = new ArrayList<SeFeeItm>();

        for(int i=0;i<3;i++){

            SeFeeItm seFeeItm = new SeFeeItm();
            seFeeItm.setFeeItmNm(feeItmNm[i]);
            seFeeItm.setSelMthdCd(selMthdCds[i]);

            seFeeItm.setFeeItmNo(feeMapper.getSeFeeItmSeq());
            seFeeItm.setBasiSelFeeYn("N");
            seFeeItm.setCreateNo(productVO.getUpdateNo());
            seFeeItm.setFeeKdCd("03");
            seFeeItm.setFeeTypCd("03101");
            seFeeItm.setFrcEndYn("N");
            seFeeItm.setUpdateNo(productVO.getUpdateNo());
            seFeeItm.setRngUseYn("N");
            seFeeItm.setPrgrsAplYn("N");
            seFeeItm.setCreateDt(productVO.getHistAplBgnDt());
            seFeeItm.setUpdateDt(productVO.getHistAplBgnDt());
            seFeeItm.setRequestNo(productVO.getUpdateNo());
            seFeeItm.setRequestDt(productVO.getHistAplBgnDt());
    //        seFeeItm.setAprvStatCd("01");

            seFeeItm.setAddPrdFee(-1);
            seFeeItm.setAplEndDy(StringUtil.left(productVO.getHistAplBgnDt(), 8)+"000000");
            seFeeItm.setAplEndDy("99991231235959");
            seFeeItm.setDispCtgrNo(productVO.getCategoryVO().getMctgrNo());
            seFeeItm.setFee(1);
            seFeeItm.setFeeAplUnitCd("01");
            seFeeItm.setSelFeeAplObjNo(String.valueOf(productVO.getPrdNo()));
            seFeeItm.setSelMthdCd(productVO.getBaseVO().getSelMthdCd());
            seFeeItm.setCreateNo(productVO.getSelMnbdNo());
            seFeeItm.setUpdateNo(productVO.getUpdateNo());
            seFeeItm.setCreateDt(productVO.getHistAplBgnDt());
            seFeeItm.setUpdateDt(productVO.getHistAplBgnDt());
            seFeeItm.setSelFeeAplObjClfCd("02");

            seFeeItmList.add(seFeeItm);
        }

        return seFeeItmList;
    }

    //상품별 판매수수료율 날짜 update (1원폰)
    private void setSeFeeEndDt(SeFeeItm seFeeItm) throws FeeServiceException {
        try {
            feeMapper.setSeFeeEndDt(seFeeItm);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new FeeServiceException("판매수수료율 날짜 수정 오류 "+ e);
        }
    }

    private void setSeFeeEndDt(long prdNo, String updateDt, long updateNo) {
        SeFeeItm bo = new SeFeeItm();
        bo.setSelFeeAplObjNo(String.valueOf(prdNo));
        bo.setAplEndDy(updateDt);
        bo.setUpdateNo(updateNo);

        feeMapper.setSeFeeEndDt(bo);
    }

    public void updateSeFeeEndDt(ProductVO productVO) throws FeeServiceException{
        try {
            if("Y".equals(productVO.getMobile1WonYn())
                && 1 == productVO.getPriceVO().getSelPrc()
                && sellerService.isMobileSeller(productVO))
            {
                setSeFeeEndDt(this.setSeFeeItm(productVO));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new FeeServiceException("판매수수료율 날짜 수정 오류 "+ e);
        }
    }

    private SeFeeItm setSeFeeItm(ProductVO productVO) throws FeeServiceException{
        SeFeeItm seFeeItm = new SeFeeItm();

        seFeeItm.setSelFeeAplObjNo(String.valueOf(productVO.getPrdNo()));
        seFeeItm.setAplEndDy(productVO.getUpdateDt());
        seFeeItm.setUpdateNo(productVO.getUpdateNo());
        seFeeItm.setDispCtgrNo(productVO.getDispCtgrNo());

        return seFeeItm;
    }

    public void updateSeFeeItmCtgr(ProductVO preProductVO, ProductVO productVO) throws FeeServiceException{
        if(preProductVO.getDispCtgrNo() != productVO.getDispCtgrNo()){
            feeMapper.updateSeFeeItmCtgr(this.setSeFeeItm(productVO));
        }
    }

    private void updateSeFeeItm(SeFeeItm seFeeItm) throws FeeServiceException{
        feeMapper.updateSeFeeItm(seFeeItm);
        seFeeItm.setDmlTypCd("U");
        this.insertSeFeeItmHist(seFeeItm);
    }

    private void insertSeFeeItmHist(SeFeeItm seFeeItm) throws FeeServiceException{
        seFeeItm.setHistOccrDt(seFeeItm.getUpdateDt());
        feeMapper.insertSeFeeItmHist(seFeeItm);
    }

    private void deleteSeFeeItm(SeFeeItm seFeeItm) throws FeeServiceException{
        seFeeItm.setDmlTypCd("D");
        this.insertSeFeeItmHist(seFeeItm);
        feeMapper.deleteSeFeeAplRng(seFeeItm);
        feeMapper.deleteSeFeeItm(seFeeItm);
    }

    private void insertSeFeeItm(SeFeeItm seFeeItm) throws FeeServiceException{
        feeMapper.insertSeFeeItm(seFeeItm);
        if("03".equals(seFeeItm.getFeeKdCd())){
            //서비스 이용료 설정시만 history 남긴다
            seFeeItm.setDmlTypCd("I");
            this.insertSeFeeItmHist(seFeeItm);
        }
    }

    private void updateSeFeeAplRng(SeFeeItm seFeeItm) throws FeeServiceException{
        // 구간이 있는경우 실행함
        if("Y".equals(seFeeItm.getRngUseYn())){
            // 구간이 있는경우 삭제
            feeMapper.deleteSeFeeAplRng(seFeeItm);

            List<SeFeeAplRng> seFeeAplRngBOList = seFeeItm.getSeFeeAplRngBOList();
            if(seFeeAplRngBOList != null && seFeeAplRngBOList.size()>0){
                for(int i=0 ; i<seFeeAplRngBOList.size() ; i++){
                    feeMapper.insertSeFeeAplRng(seFeeAplRngBOList.get(i));
                }
            }else{
                throw new FeeServiceException("구간에 해당하는 정보가 존재하지 않습니다.");
            }
        }
    }

    /**
     * 수수료 조회
     */
    public String getSaleFeeRateAmount(long dispCtgr2No, long dispCtgr3No, String selMthdCd, long userNo, long prdNo, long selPrc) throws FeeServiceException {
        String feevalue = "";
        try {
            SeFeeItemVO seFeeItemVO = new SeFeeItemVO();
            seFeeItemVO.setDispCtgr2No(dispCtgr2No);
            seFeeItemVO.setDispCtgr3No(dispCtgr3No);
            seFeeItemVO.setSelMthdCd(selMthdCd);
            seFeeItemVO.setSelMnbdNo(userNo);
            seFeeItemVO.setPrdNo(prdNo);
            seFeeItemVO.setSelPrc(selPrc);
            feevalue = feeMapper.getProductSaleFee(seFeeItemVO);
        } catch (Exception e) {
            throw new FeeServiceException("수수료(정액/율) 조회", e);
        }

        return feevalue;
    }

    public SaleFeeVO getCategoryDefaultFee(ProductVO productVO) throws FeeServiceException {
        // 예약 판매일 경우 고정가 판매로 체크되도록 변경
        if(ProductConstDef.PRD_SEL_MTHD_CD_PLN.equals(productVO.getBaseVO().getSelMthdCd())) {
            productVO.getBaseVO().setSelMthdCd(ProductConstDef.PRD_SEL_MTHD_CD_FIXED);
        }

        return feeMapper.getCategoryDefaultFee(productVO);
    }

    /**
     * 기본 마진율 조회
     * @param memNo
     * @throws FeeServiceException
     */
    public long getBasicMarginRate(long memNo) throws FeeServiceException {
        long mrgnRt = 0;
        try {
            mrgnRt = feeMapper.getBasicMarginRate(memNo);
        } catch (FeeServiceException ex) {
            throw ex;
        }
        return mrgnRt;
    }

    /**
     * 카드사 무이자 수수료 목록 조회
     * @param   basketNointerestVO    검색조건을 저장
     * @return  BasketNointerestBO List
     * @throws  FeeServiceException
     */
    public List<BasketNointerestVO> getCrdtCrdenFeeList(BasketNointerestVO basketNointerestVO) throws FeeServiceException {
        List<BasketNointerestVO> basketNointerestList = null;
        try {
            int totalRecCnt = feeMapper.getTotalCrdtCrdenFeeCnt(basketNointerestVO);
            basketNointerestVO.setTotalCount(totalRecCnt);
            basketNointerestVO.setPageInfo();
            basketNointerestList = feeMapper.getCrdtCrdenFeeList(basketNointerestVO);
        } catch (FeeServiceException ex) {
            throw ex;
        }
        return basketNointerestList;
    }


    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) {
        this.convertListingItemVOList(productVO);
        feeValidate.checkItemCtgrStat(productVO);
    }

    private void convertListingItemVOList(ProductVO productVO) throws FeeServiceException{
        // 리스팅광고
        // 고정가 / 공동구매 / 중고상품 경우에만 설정 가능
        if(!GroupCodeUtil.isContainsDtlsCd(productVO.getBaseVO().getSelMthdCd(),
                                          SelMthdCd.FIXED,
                                          SelMthdCd.COBUY,
                                          SelMthdCd.USED)){
            throw new FeeServiceException("리스팅광고를 설정할 수 없습니다.");
        }
    }
}
