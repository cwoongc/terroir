package com.elevenst.terroir.product.registration.deal.validate;

import com.elevenst.common.properties.PropMgr;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.deal.exception.DealException;
import com.elevenst.terroir.product.registration.deal.service.DealServiceImpl;
import com.elevenst.terroir.product.registration.deal.vo.EventPricePegVO;
import com.elevenst.terroir.product.registration.deal.vo.EventRqstPrdInfoVO;
import com.elevenst.terroir.product.registration.deal.vo.ProductEvtVO;
import com.elevenst.terroir.product.registration.deal.vo.ShockDealStateVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DealValidate {

    @Autowired
    DealServiceImpl dealService;

    @Autowired
    PropMgr propMgr;

    public long getCalculatedDscAmt(EventPricePegVO eventPricePegVO){
        if (ProductConstDef.CUPN_DSC_MTHD_CD_WON.equals(eventPricePegVO.getCupnDscMthdCd())) {
            return StringUtil.getLong(eventPricePegVO.getDscAmt());
        }
        else if (ProductConstDef.CUPN_DSC_MTHD_CD_PERCNT.equals(eventPricePegVO.getCupnDscMthdCd())) {
            Double tmpDscAmtPercntDobule = Double.parseDouble(eventPricePegVO.getDscRt());
            long tmpDscAmtPercnt = tmpDscAmtPercntDobule.intValue();
            return (long)(Math.floor(eventPricePegVO.getSelPrc() * tmpDscAmtPercnt * 0.001) * 10);
        }

        return 0;
    }

    /**
     * 쇼킹딜 상품의 진행상태(APRV_STAT_CD)가 확정완료(07) 일 경우 상품의 일부 정보 변경 체크
     * @param productVO
     * @return true(변경가능), false(변경불가)
     * @throws Exception
     */
    public boolean checkConfirmDealByChanged(ProductVO productVO) throws Exception {
        String msg = checkMsgConfirmDealByChanged(productVO, dealService.getPrdPrcCupnDlvInfo(productVO));

        if (StringUtil.isNotEmpty(msg))  {
            throw new ProductException(msg);
        }

        return true;
    }

    public String checkMsgConfirmDealByChanged(ProductVO productVO, ProductVO comparePrdVO) {
        if(comparePrdVO == null || productVO == null){
            return "상품이 존재하지 않습니다.";
        }else{
            if(comparePrdVO.getDispCtgrNo() != productVO.getDispCtgrNo()) return "쇼킹딜 참여 신청 상품은 카테고리를 변경할 수 없습니다.";
        }
        return "";
    }

    /**
     * 쇼킹딜의 모든 정보 수정 시 가능상태
     * - 필수사항 productEvtBO.eventNo, productEvtBO.prdNo
     * - 선택사항 productEvtBO.selMnbdNo(API연동업체 구분), productEvtBO.officeGubun (SO,BO 구분)
     * @param productEvtBO
     * @return ShockDealState
     * @throws DealException
     */
    public ShockDealStateVO getShockDealState(ProductEvtVO productEvtBO) throws DealException {
        ShockDealStateVO shockDealState = new ShockDealStateVO();

        try{
            if(productEvtBO == null || productEvtBO.getEventNo() <= 0 || productEvtBO.getPrdNo() <= 0)
                throw new DealException("쇼킹딜 정보가 없습니다.");

            if(productEvtBO.getEventRqstPrdInfoVO() == null) productEvtBO.setEventRqstPrdInfoVO(new EventRqstPrdInfoVO());
            productEvtBO.getEventRqstPrdInfoVO().setEventNo(productEvtBO.getEventNo());
            productEvtBO.getEventRqstPrdInfoVO().setPrdNo(productEvtBO.getPrdNo());

            ProductEvtVO checkEvtBO = dealService.getEventPrdInfoWithAprv(productEvtBO.getEventRqstPrdInfoVO());
            if(checkEvtBO == null || ProductConstDef.APRV_STAT_CD.impossibleState(checkEvtBO.getEventPricePegVO().getAprvStatCd())){
                shockDealState.setUpdableSelPrc(true);
                shockDealState.setUpdableDscAmtPercnt(true);
                shockDealState.setUpdableDispCtgrNo(true);
                shockDealState.setUpdableDlvCst(true);
                shockDealState.setUpdablePrdNm(true);
                shockDealState.setUpdablePrdImg(true);
            }else{
                String aprvStatCd = checkEvtBO.getEventPricePegVO().getAprvStatCd();
                String prdAddStatCd = checkEvtBO.getEventPricePegVO().getPrdAddStatCd();

                boolean isPreAplCmpl = ProductConstDef.APRV_STAT_CD.preApprovalComplete(aprvStatCd);	// 승인완료 이전
                boolean isPreCfrmCmpl = ProductConstDef.APRV_STAT_CD.preConfirmComplete(aprvStatCd);	// 확정완료 이전
                boolean isLiveDeal = ProductConstDef.APRV_STAT_CD.COMP_CNFM.equals(aprvStatCd) && "02|03|".indexOf(prdAddStatCd+"|") > -1;	// 쇼킹딜 진행중
                boolean isClsDeal = (ProductConstDef.APRV_STAT_CD.COMP_CNFM.equals(aprvStatCd) && "04".equals(prdAddStatCd)) || !ProductConstDef.APRV_STAT_CD.preCloseDeal(aprvStatCd);	// 쇼킹딜 종료

                // 쇼킹딜 상품정보 보기 (승인완료 ~ 쇼킹딜 종료 이전)
                shockDealState.setViewableRqstPrdInfo(!isPreAplCmpl && !isClsDeal);


                if(ProductConstDef.BACKOFFICE_TYPE.equals(productEvtBO.getOfficeGubun())){
                    // 쇼킹딜 상품정보 수정 (승인완료 ~ 확정완료)
                    shockDealState.setUpdablePrdInfo((!isPreAplCmpl && isPreCfrmCmpl) || !isLiveDeal);
                } else {
                    // 쇼킹딜 상품정보 수정 (승인완료 ~ 확정완료)
                    shockDealState.setUpdablePrdInfo(!isPreAplCmpl && isPreCfrmCmpl);
                }

                // 쇼킹딜 종료 후 판매정보/판매상태 보기 (쇼킹딜 시작 ~ 쇼킹딜 종료 이전)
                shockDealState.setViewableOrgPrdInfo(isLiveDeal);

                // 쇼킹딜 종료 후 판매상태 보기 (승인완료 ~ 쇼킹딜 종료 이전)
                shockDealState.setViewableSelStatCd(!isPreAplCmpl && !isClsDeal);
                // 쇼킹딜 종료 후 판매상태 수정 (승인완료 ~ 쇼킹딜 종료 이전)
                shockDealState.setUpdableSelStatCd(!isPreAplCmpl && !isClsDeal);

                // 신청정보 (승인대기만)
                shockDealState.setUpdableAplInfo(ProductConstDef.APRV_STAT_CD.REQ_APRV.equals(aprvStatCd));
                // 상품정보 - 판매가 (쇼킹딜 시작이전, 종료이후)
                shockDealState.setUpdableSelPrc(!isLiveDeal);
                // 상품정보 - 기본즉시할인  (쇼킹딜 시작이전, 종료이후)
                shockDealState.setUpdableDscAmtPercnt(!isLiveDeal);
                // 상품정보 - 카테고리 (확정완료이전, 쇼킹딜 종료이후)
                shockDealState.setUpdableDispCtgrNo(isPreCfrmCmpl || isClsDeal);
                // 상품정보 - 배송비  (쇼킹딜 시작이전, 종료이후)
                shockDealState.setUpdableDlvCst(!isLiveDeal);
                // 상품정보 - 상품명(API 연동업체의 경우 쇼킹딜 시작이전, 종료이후)
                if(productEvtBO.getSelMnbdNo() > 0 && Arrays.asList(propMgr.get1hourTimeProperty(ProductConstDef.SHOCKINGDEAL_EXCP_OBJ).split(",")).contains(Long.toString(productEvtBO.getSelMnbdNo()))){
                    shockDealState.setUpdablePrdNm(!isLiveDeal);
                    shockDealState.setUpdablePrdImg(!isLiveDeal);
                }else{
                    shockDealState.setUpdablePrdNm(true);
                    shockDealState.setUpdablePrdImg(true);
                }

                // 예외 처리
                if(checkEvtBO.getEventRqstPrdInfoVO().getEventNo() <= 0){	// 쇼킹딜 상품정보 없을경우
                    shockDealState.setViewableRqstPrdInfo(false);
                    shockDealState.setUpdablePrdInfo(false);
                    shockDealState.setViewableOrgPrdInfo(false);
                    shockDealState.setUpdableSelStatCd(false);
                }

                if(checkEvtBO.getEventOrgPrdInfoVO().getEventNo() <= 0){	// 쇼킹딜 종료 후 상품정보 없을경우
                    shockDealState.setViewableOrgPrdInfo(false);
                }
            }
        }catch (Exception e){
            throw new DealException(e);
        }

        return shockDealState;
    }

    public void checkDealInfo(ProductVO preProductVO, ProductVO productVO) throws DealException{
        if(productVO.isUpdate()){
            ProductVO dealVO = dealService.getPrdPrcCupnDlvInfo(productVO);
            if(preProductVO.getBaseVO().getPdEventCnt() > 0){
                this.checkShockDealPrice(productVO, dealVO);
            }else if(preProductVO.getBaseVO().getEvtConfirmCnt() > 0){
                this.checkShockDealCtgr(productVO, dealVO);
            }
        }
    }

    private void checkShockDealCtgr(ProductVO productVO, ProductVO dealVO) throws DealException{
        if(dealVO == null || productVO == null){
            throw new DealException("상품이 존재하지 않습니다.");
        }else{
            if(dealVO.getDispCtgrNo() != productVO.getDispCtgrNo()){
                throw new DealException("쇼킹딜 참여 신청 상품은 카테고리를 변경할 수 없습니다.");
            }
        }
    }

    private void checkShockDealPrice(ProductVO productVO, ProductVO dealVO) throws DealException{
        if(dealVO != null){
            if(ProductConstDef.BACKOFFICE_TYPE.equals(productVO.getChannel())){
                if(productVO.getPriceVO().getSelPrc() > dealVO.getPriceVO().getSelPrc()) {
                    throw new DealException("쇼킹딜 판매기간 중에는 가격의 상향조정이 불가합니다.");
                }
            }else{
                if(productVO.getPriceVO().getSelPrc() != dealVO.getPriceVO().getSelPrc()) {
                    throw new DealException("쇼킹딜 참여 신청 상품은 판매가를 변경할 수 없습니다.");
                }

                if((!StringUtil.nvl(dealVO.getBaseVO().getDlvCstInstBasiCd(),"").equals(StringUtil.nvl(dealVO.getBaseVO().getDlvCstInstBasiCd(),""))) ||
                    (dealVO.getBaseVO().getDlvCst() != dealVO.getBaseVO().getDlvCst()) ||
                    (!StringUtil.nvl(dealVO.getBaseVO().getDlvCstPayTypCd(),"").equals(StringUtil.nvl(dealVO.getBaseVO().getDlvCstPayTypCd(),""))) ||
                    (!StringUtil.nvl(dealVO.getBaseVO().getBndlDlvCnYn(),"").equals(StringUtil.nvl(dealVO.getBaseVO().getBndlDlvCnYn(),""))) ||
                    (!StringUtil.nvl(dealVO.getBaseVO().getDlvCstInfoCd(),"").equals(StringUtil.nvl(dealVO.getBaseVO().getDlvCstInfoCd(),"")))){
                    throw new DealException("쇼킹딜 참여 신청 상품은 배송 정보를 변경할 수 없습니다.");
                }

                if(!StringUtil.nvl(dealVO.getPriceVO().getCupnDscMthdCd(),"").equals(StringUtil.nvl(productVO.getPriceVO().getCupnDscMthdCd(),""))){
                    throw new DealException("쇼킹딜 참여 신청 상품은 셀러쿠폰 정보를 변경할 수 없습니다.");
                }
            }


            if(!"".equals(StringUtil.nvl(dealVO.getPriceVO().getCupnDscMthdCd(),""))){

                if(ProductConstDef.BACKOFFICE_TYPE.equals(productVO.getChannel())){

                    long dbDscAmt = 0L;
                    long paramDscAmt = 0L;

                    if("01".equals(StringUtil.nvl(dealVO.getPriceVO().getCupnDscMthdCd(),""))) {
                        dbDscAmt = dealVO.getPriceVO().getDscAmt();
                    } else if("02".equals(StringUtil.nvl(dealVO.getPriceVO().getCupnDscMthdCd(),""))) {
                        dbDscAmt = (long)(dealVO.getPriceVO().getSelPrc() * dealVO.getPriceVO().getDscRt() * 0.001) * 10;
                    }

                    if("01".equals(StringUtil.nvl(productVO.getPriceVO().getCupnDscMthdCd(),""))) {
                        paramDscAmt = productVO.getPriceVO().getDscAmt();
                    } else if("02".equals(StringUtil.nvl(productVO.getPriceVO().getCupnDscMthdCd(),""))) {
                        paramDscAmt = (long)(productVO.getPriceVO().getSelPrc() * productVO.getPriceVO().getDscRt() * 0.001) * 10;
                    }

                    if(dbDscAmt > paramDscAmt) {

                        if(("01".equals(StringUtil.nvl(dealVO.getPriceVO().getCupnDscMthdCd(),""))) &&
                            ("01".equals(StringUtil.nvl(productVO.getPriceVO().getCupnDscMthdCd(),"")))  &&
                            (dealVO.getPriceVO().getDscAmt() == productVO.getPriceVO().getDscAmt())){
                            // 변경전,후 정액이 같음
                        } else if(("02".equals(StringUtil.nvl(dealVO.getPriceVO().getCupnDscMthdCd(),""))) &&
                            ("02".equals(StringUtil.nvl(productVO.getPriceVO().getCupnDscMthdCd(),"")))  &&
                            (dealVO.getPriceVO().getDscRt() == productVO.getPriceVO().getDscRt())) {
                            // 변경전,후 정율이 같음
                        } else {
                            throw new DealException("쇼킹딜 판매기간 중에는 할인금액의 하향조정이 불가합니다.");
                        }

                    }

                } else {
                    if("01".equals(StringUtil.nvl(dealVO.getPriceVO().getCupnDscMthdCd(),"")) && dealVO.getPriceVO().getDscAmt() != productVO.getPriceVO().getDscAmt()){ // 정액
                        throw new DealException("쇼킹딜 참여 신청 상품은 셀러쿠폰 할인금액 정보를 변경할 수 없습니다.");
                    }
                    else if("02".equals(StringUtil.nvl(dealVO.getPriceVO().getCupnDscMthdCd(),"")) && dealVO.getPriceVO().getDscRt() != productVO.getPriceVO().getDscRt()){ // 정율
                        throw new DealException("쇼킹딜 참여 신청 상품은 셀러쿠폰 할인율 정보를 변경할 수 없습니다.");
                    }

                    String inIssStartDy = "";
                    String inIssEndDy = "";
                    String curIssStartDy = "";
                    String curIssEndDy = "";

                    if(!"".equals(StringUtil.nvl(productVO.getPriceVO().getIssCnBgnDt(),"")) && StringUtil.nvl(productVO.getPriceVO().getIssCnBgnDt(),"").length() > 8){
                        inIssStartDy = StringUtil.nvl(productVO.getPriceVO().getIssCnBgnDt(),"").substring(0,8);
                    }
                    if(!"".equals(StringUtil.nvl(productVO.getPriceVO().getIssCnEndDt(),"")) && StringUtil.nvl(productVO.getPriceVO().getIssCnEndDt(),"").length() > 8){
                        inIssEndDy = StringUtil.nvl(productVO.getPriceVO().getIssCnEndDt(),"").substring(0,8);
                    }
                    if(!"".equals(StringUtil.nvl(dealVO.getPriceVO().getIssCnBgnDt(),"")) && StringUtil.nvl(dealVO.getPriceVO().getIssCnBgnDt(),"").length() > 8){
                        curIssStartDy = StringUtil.nvl(dealVO.getPriceVO().getIssCnBgnDt(),"").substring(0,8);
                    }
                    if(!"".equals(StringUtil.nvl(dealVO.getPriceVO().getIssCnEndDt(),"")) && StringUtil.nvl(dealVO.getPriceVO().getIssCnEndDt(),"").length() > 8){
                        curIssEndDy = StringUtil.nvl(dealVO.getPriceVO().getIssCnEndDt(),"").substring(0,8);
                    }

                    if((!inIssStartDy.equals(curIssStartDy)) ||
                        (!inIssEndDy.equals(StringUtil.nvl(curIssEndDy)))){
                        throw new DealException("쇼킹딜 참여 신청 상품은 셀러쿠폰 기간 정보를 변경할 수 없습니다.");
                    }
                }

            }
        }
    }
}
