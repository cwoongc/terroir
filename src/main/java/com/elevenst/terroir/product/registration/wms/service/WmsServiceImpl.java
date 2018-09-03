package com.elevenst.terroir.product.registration.wms.service;

import com.elevenst.common.process.RegistrationInfoConverter;
import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.common.code.BsnDealClf;
import com.elevenst.terroir.product.registration.common.service.CommonServiceImpl;
import com.elevenst.terroir.product.registration.option.vo.BasisStockVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.wms.entity.PdPrdWmsInfo;
import com.elevenst.terroir.product.registration.wms.exception.WmsInfoServiceException;
import com.elevenst.terroir.product.registration.wms.mapper.WmsMapper;
import com.elevenst.terroir.product.registration.wms.vo.EmpVO;
import com.elevenst.terroir.product.registration.wms.vo.WmChrgVO;
import com.elevenst.terroir.product.registration.wms.vo.WmCustomsResultVO;
import com.elevenst.terroir.product.registration.wms.vo.WmCustomsVO;
import com.elevenst.terroir.product.registration.wms.vo.WmInsrCstVO;
import com.elevenst.terroir.product.registration.wms.vo.WmWghtDlvCstVO;
import com.elevenst.terroir.product.registration.wms.vo.WmsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class WmsServiceImpl implements RegistrationInfoConverter<ProductVO>{

    @Autowired
    WmsMapper wmsMapper;

    @Autowired
    CommonServiceImpl commonService;

    public void checkWmsDataInsertProcess(ProductVO productVO) {
        if (ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(productVO.getBaseVO().getBsnDealClf())
            || ProductConstDef.BSN_DEAL_CLF_SPECIFIC_PURCHASE.equals(productVO.getBaseVO().getBsnDealClf())
            || ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(productVO.getBaseVO().getDlvClf())){

            productVO.getWmsVO().setPrdNo(productVO.getPrdNo());
            productVO.getWmsVO().setCreateNo(productVO.getCreateNo());
            productVO.getWmsVO().setUpdateNo(productVO.getUpdateNo());

            this.insertBasisStockAttribute(productVO.getWmsVO());
        }
    }

    /**
     * 기준재고속성 최초 등록(최초 담당MD만 등록됨)
     * @param param
     * @return
     * @throws WmsInfoServiceException
     */
    public void insertBasisStockAttribute(WmsVO param) throws WmsInfoServiceException {

        boolean isDirectPurchaseOutDlv = ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(param.getBsnDealClf()) && ProductConstDef.DLV_CLF_OUT_DELIVERY.equals(param.getDlvClf());

        PdPrdWmsInfo basisStockVO = new PdPrdWmsInfo();
        basisStockVO.setPrdNo(param.getPrdNo());
        basisStockVO.setEmpNo(param.getEmpNo());
        if(ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(param.getDlvClf())
            || ProductConstDef.SetTypCd.BUNDLE.equals(param.getSetTypCd())
            || isDirectPurchaseOutDlv
            || ProductConstDef.BSN_DEAL_CLF_SPECIFIC_PURCHASE.equals(param.getBsnDealClf()))
        {
            basisStockVO.setRepPtnrMemNo(String.valueOf(param.getSelMnbdNo()));
        }else{
            basisStockVO.setRepPtnrMemNo("0");
        }
        basisStockVO.setCreateNo(param.getCreateNo());
        basisStockVO.setUpdateNo(param.getUpdateNo());

        try{
            wmsMapper.insertBasisStockAttribute(basisStockVO);
            wmsMapper.insertPdPrdWmsInfoHist(basisStockVO);
        } catch (Exception e) {
            throw new WmsInfoServiceException("wmsInfo 등록을 완료하지 못했습니다.");
        }
    }

    public void updateEmployee(PdPrdWmsInfo param) throws WmsInfoServiceException{
        this.updateBatchEmployee(Arrays.asList(param));
    }

    public void updateBatchEmployee(List<PdPrdWmsInfo> paramList) {
        if(StringUtil.isEmpty(paramList)) throw new WmsInfoServiceException("wmsInfo 수정될 정보가 없습니다.");

        int result = 0;
        for(PdPrdWmsInfo param : paramList){
            result += wmsMapper.updateEmployeeNo(param);
            wmsMapper.insertPdPrdWmsInfoHist(param);
        }

        if(result != paramList.size()) throw new WmsInfoServiceException("wmsInfo 수정을 완료하지 못했습니다.");
    }

    public void updateWmsEmployeeNo(ProductVO productVO) throws WmsInfoServiceException{
        if(GroupCodeUtil.isContainsDtlsCd(productVO.getBaseVO().getBsnDealClf(), BsnDealClf.DIRECT_PURCHASE, BsnDealClf.SPECIFIC_PURCHASE)
            || ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(productVO.getBaseVO().getDlvClf())){
            PdPrdWmsInfo pdPrdWmsInfo = new PdPrdWmsInfo();
            pdPrdWmsInfo.setPrdNo(productVO.getPrdNo());
            pdPrdWmsInfo.setEmpNo(productVO.getBaseVO().getEmployeeNo());
            pdPrdWmsInfo.setUpdateNo(productVO.getUpdateNo());

            this.updateEmployee(pdPrdWmsInfo);
        }
    }


    /**
     * MD찾기 리스트 조회
     * @param empVO
     * @return HashMap<String, Object>
     * @throws WmsInfoServiceException
     */
    public HashMap<String, Object> getMdList(EmpVO empVO) throws WmsInfoServiceException {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // MD찾기 리스트 조회
            List<EmpVO> list = wmsMapper.getMdList(empVO);

            resultMap.put("TOTAL_COUNT", 0);
            if(list != null && list.size() > 0){
                resultMap.put("TOTAL_COUNT", list.get(0).getTotalCount());
            }
            resultMap.put("DATA_LIST", list);
        } catch (Exception e) {
            throw new WmsInfoServiceException("MD찾기 리스트 조회오류");
        }
        return resultMap;
    }

    /**
     * 물류 배송비 조회
     * @param itgMemNo 통합배송셀러번호
     * @param weight 무게
     * @param applyDate 적용기준일
     * @return
     * @throws WmsInfoServiceException
     */
    public long getSellerWghtDlvCst(long itgMemNo, double weight, String applyDate) throws WmsInfoServiceException {
        try {
            WmWghtDlvCstVO dataBo = new WmWghtDlvCstVO();
            dataBo.setSelMnbdNo(itgMemNo);	//통합 셀러 번호
            dataBo.setBgnWght(weight);		//무게
            dataBo.setBgnDt(applyDate);		//적용기준일

            List<WmWghtDlvCstVO> list = wmsMapper.getSellerWmWghtDlvCst(dataBo);

            if(list.size() > 0)
                return list.get(0).getDlvCst();
            else
                throw new WmsInfoServiceException(itgMemNo + " 통합아이디에 무게별 배송비 정보가 존재하지 않습니다.");
        } catch(Exception ex) {
            throw new WmsInfoServiceException(ex);
        }
    }

    /**
     * 물류 보험료 조회
     * @param itgMemNo 통합배송셀러번호
     * @param prdPrc 총상품가
     * @param applyDate 적용기준일
     * @return
     * @throws WmsInfoServiceException
     */
    public long getSellerInsrCst(long itgMemNo, long prdPrc, String applyDate) throws WmsInfoServiceException {
        try {
            WmInsrCstVO dataBo = new WmInsrCstVO();
            dataBo.setSelMnbdNo(itgMemNo);	//통합 셀러 번호
            dataBo.setBgnPrdPrc(prdPrc);	//상품가격
            dataBo.setBgnDt(applyDate);		//적용기준일

            List<WmInsrCstVO> list = wmsMapper.getSellerWmInsrCst(dataBo);

            if(list.size() > 0)
                return list.get(0).getInsrCst();
            else
                throw new WmsInfoServiceException(itgMemNo + " 통합아이디에 금액별 배송비 정보가 존재하지 않습니다.");
        } catch(Exception ex) {
            throw new WmsInfoServiceException(ex);
        }
    }

    /**
     * 물류 관세 조회
     * @param wmCustomsResultVO
     * @return
     * @throws WmsInfoServiceException
     */
    public WmCustomsResultVO getWmCustoms(WmCustomsResultVO wmCustomsResultVO) throws WmsInfoServiceException {
        try {
            //출고지 없을시는 기본 미국(AM)
            String deliveryCd = StringUtil.nvl(wmCustomsResultVO.getDeliveryCd(), "AM");
            wmCustomsResultVO.setDeliveryCd(deliveryCd);
            return wmsMapper.getWmCustomsCondition(wmCustomsResultVO);
        } catch(Exception ex) {
            throw new WmsInfoServiceException(ex);
        }
    }

    /**
     * 물류 수수료 조회
     * @param itgMemNo 통합배송셀러번호
     * @param applyDate 적용기준일
     * @return
     * @throws WmsInfoServiceException
     */
    public WmChrgVO getSellerCharge(long itgMemNo, String applyDate) throws WmsInfoServiceException {
        try {
            WmChrgVO dataBo = new WmChrgVO();
            dataBo.setSelMnbdNo(itgMemNo);	//통합 셀러 번호
            dataBo.setBgnDt(applyDate);		//적용기준일
            return wmsMapper.getWmCharge(dataBo);
        } catch(Exception ex) {
            throw new WmsInfoServiceException(ex);
        }
    }

    public void insertWmsInfoProcess(ProductVO productVO) throws WmsInfoServiceException{
        this.checkWmsDataInsertProcess(productVO);
    }

    public void updateWmsInfoProcess(ProductVO productVO) throws WmsInfoServiceException{
        this.updateWmsEmployeeNo(productVO);
    }

    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) {

        if(productVO.getSellerVO().isSellableBundlePrdSeller()
            || ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(productVO.getBaseVO().getDlvClf())
            || ProductConstDef.BSN_DEAL_CLF_SPECIFIC_PURCHASE.equals(productVO.getBaseVO().getBsnDealClf())
            || (ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(productVO.getBaseVO().getBsnDealClf())
                && (ProductConstDef.DLV_CLF_OUT_DELIVERY.equals(productVO.getBaseVO().getDlvClf())
                || ProductConstDef.DLV_CLF_IN_DELIVERY.equals(productVO.getBaseVO().getDlvClf()))
                )
            )
        {
            if(productVO.getBaseVO().getEmployeeNo() <= 0) throw new WmsInfoServiceException("담당MD 정보를 확인해 주세요.");
        }
        this.checkHsCode(productVO);
    }



    public void checkHsCode(ProductVO productVO) throws WmsInfoServiceException{
        String grpCd = "WM001";
        String dtlsCd = productVO.getBaseVO().getHsCode();
        String basiCd = productVO.getBaseVO().getDlvCstInstBasiCd();

        if( StringUtil.isNotEmpty(dtlsCd) && dtlsCd.getBytes().length > 4 ) {
            throw new WmsInfoServiceException("H.S code는 4자리까지 입력 가능합니다.");
        }

        if(!ProductConstDef.DLV_CST_INST_BASI_CD_GLB_ITG_ADDR.equals(basiCd) && !ProductConstDef.DLV_CST_INST_BASI_CD_FR_ITG_ADDR.equals(basiCd))
            return ;
        if("".equals(StringUtil.nvl(dtlsCd))){
            throw new WmsInfoServiceException("H.S code를 필수로 입력해야 합니다.");
        }
        if(!commonService.isCodeDetail(grpCd, dtlsCd)){
            throw new WmsInfoServiceException("해당 H.S code는 존재하지 않는 코드 입니다.");
        }

        WmCustomsVO wmCustomsVO = new WmCustomsVO();
        wmCustomsVO.setHsCode(dtlsCd);
        if(ProductConstDef.DLV_CST_INST_BASI_CD_GLB_ITG_ADDR.equals(basiCd))
            wmCustomsVO.setDeliveryCd("AM");
        else if(ProductConstDef.DLV_CST_INST_BASI_CD_FR_ITG_ADDR.equals(basiCd))						//이태리
            wmCustomsVO.setDeliveryCd("EU");
        int nCount = wmsMapper.getHScodeDeliveryCd(wmCustomsVO);
        if(nCount<=0){
            throw new WmsInfoServiceException("해당 H.S code는 설정 불가능한 코드 입니다.");
        }
    }

    public BasisStockVO getBasisStockAttribute(long prdNo) throws WmsInfoServiceException {
        return wmsMapper.getBasisStockAttribute(prdNo);
    }
}
