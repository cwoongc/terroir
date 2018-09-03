package com.elevenst.terroir.product.registration.template.service;

import com.elevenst.terroir.product.registration.delivery.vo.ProductSellerBasiDeliveryCostVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.template.entity.*;
import com.elevenst.terroir.product.registration.template.exception.TemplateServiceExcetpion;
import com.elevenst.terroir.product.registration.template.mapper.TemplateMapper;
import com.elevenst.terroir.product.registration.template.message.TemplateServiceExcetpionMessage;
import com.elevenst.terroir.product.registration.template.validate.TemplateValidate;
import com.elevenst.terroir.product.registration.template.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TemplateServiceImpl {

    @Autowired
    TemplateValidate templateValidate;

    @Autowired
    TemplateMapper templateMapper;

    public static final String SEL_PRD_INFO_TMPL_INSERT_ERROR = "판매정보템플릿 입력 오류 발생 : ";

    // 템플릿 저장
    public void setProductTemplate(TemplateVO templateVO, AddTemplateVO addPrdTempleteVO) throws TemplateServiceExcetpion{
		/*
         * 상품정보 템플릿 저장 옵션
         */
        /*
         * 1.판매정보 템플릿 입력
         */
        if (templateVO != null) {
            ProductInformationTemplateVO productInformationTemplateVO = templateVO.getProductInformationTemplateVO();
            InventoryVO inventoryVO = templateVO.getInventoryVO();
            this.insertTemplate(productInformationTemplateVO, inventoryVO);
        }
        /*
         * 3.추가구성정보 템플릿 입력
         */
        if (addPrdTempleteVO != null) {
            insertAddProductTemplate(addPrdTempleteVO);
        }
    }

    public void insertAddProductTemplate(AddTemplateVO addPrdTempleteVO) throws TemplateServiceExcetpion{

        try {

            //상품정보 템플릿 저장
            ProductInformationTemplateVO dataVO = addPrdTempleteVO.getProductInformationTemplateVO();
            long seq = insertProductInformationTemplate(dataVO);
            dataVO.setPrdInfoTmpltNo(seq);

            //번호셋팅 & 추가구성품목 저장
            List listAddProd = addPrdTempleteVO.getListAddProd();
            for(int i = 0 ; i < listAddProd.size() ; i++) {
                InventoryAddProductCompositionVO inventoryAddProductCompositionVO = (InventoryAddProductCompositionVO)listAddProd.get(i);
                inventoryAddProductCompositionVO.getPdPrdLstAddPrdCompVO().setPrdCompObjNo(seq);
            } //insert 영향은 없지만 set
            insertInventoryAddProductCompositionList(listAddProd);

        } catch(Exception ex) {
            throw new TemplateServiceExcetpion(TemplateServiceExcetpionMessage.TEMPLATE_INSERT_ERROR, ex);
        }
    }

    public void insertInventoryAddProductCompositionList(List listAddProd) throws TemplateServiceExcetpion{
        try
        {
            for(int i = 0 ; i < listAddProd.size() ; i++) {
                PdPrdLstAddPrdComp pdPrdLstAddPrdComp = new PdPrdLstAddPrdComp();
                InventoryAddProductCompositionVO inventoryAddProductCompositionVO = (InventoryAddProductCompositionVO)listAddProd.get(i);
                PdPrdLstAddPrdCompVO pdPrdLstAddPrdCompVO = inventoryAddProductCompositionVO.getPdPrdLstAddPrdCompVO();
                BeanUtils.copyProperties(pdPrdLstAddPrdCompVO,pdPrdLstAddPrdComp);
                templateMapper.insertInventoryAddProductComposition(pdPrdLstAddPrdComp);
            }
        }catch(Exception ex) {
            throw new TemplateServiceExcetpion(TemplateServiceExcetpionMessage.ADD_PRD_INSERT_ERROR,ex);
        }
    }

    public void insertTemplate(ProductInformationTemplateVO paramVO, InventoryVO inventoryVO) throws TemplateServiceExcetpion {

        long prdInfoTmpltNo;
        try {
            templateValidate.checkTemplateParameter(paramVO);

            //상품템플릿 테이블 저장
            prdInfoTmpltNo = this.insertProductInformationTemplate(paramVO);
            paramVO.getSellInformationVO().setPrdInfoTmpltNo(prdInfoTmpltNo);

            //판매정보 템플릿 저장
            insertSellInformation(paramVO);

            // 수량별 차등 기준값 등록
            insertOrdBasicDeliCost(inventoryVO.getInventoryOrdBasiDeliCostVOList(),prdInfoTmpltNo);

            // 안내정보 등록
            insertProductDesc(inventoryVO.getInventoryProductDescVOList(),prdInfoTmpltNo);

            // 출고지 교환 반품지 주소
            insertInventoryProductAddress(inventoryVO.getInventoryProductAddressVOList(), prdInfoTmpltNo);

        }catch(Exception ex) {
            throw new TemplateServiceExcetpion(TemplateServiceExcetpionMessage.TEMPLATE_INSERT_ERROR, ex);
        }
    }

    public void insertInventoryProductAddress(List inventoryProductAddressVOList, long prdInfoTmpltNo) throws TemplateServiceExcetpion {
        try {
            for(int i = 0 ; i < inventoryProductAddressVOList.size() ; i++) {
                PdPrdLstTgowPlcExchRtngd pdPrdLstTgowPlcExchRtngd = new PdPrdLstTgowPlcExchRtngd();
                PdPrdLstTgowPlcExchRtngdVO bo = (PdPrdLstTgowPlcExchRtngdVO)inventoryProductAddressVOList.get(i);
                bo.setPrdAddrObjNo(prdInfoTmpltNo);
                BeanUtils.copyProperties(bo,pdPrdLstTgowPlcExchRtngd);
                templateMapper.insertInventoryProductAddress(pdPrdLstTgowPlcExchRtngd);
            }
        }
        catch(Exception ex) {
            throw new TemplateServiceExcetpion(TemplateServiceExcetpionMessage.GOODS_SINGLE_INSERT_ERROR, ex);
        }
    }

    public void insertProductDesc(List inventoryProductDescVOList, long prdInfoTmpltNo) throws TemplateServiceExcetpion {
        try {
            // 물품대장설명 저장
            for(int i = 0 ;  i < inventoryProductDescVOList.size() ; i++)
            {
                PdPrdLstDesc pdPrdLstDesc = new PdPrdLstDesc();
                // 수량기준 배송비 셋팅 1:N 관계 저장
                PdPrdLstDescVO descBo = (PdPrdLstDescVO)inventoryProductDescVOList.get(i);
                if(prdInfoTmpltNo != 0L) {descBo.setPrdDescObjNo(prdInfoTmpltNo);}
                BeanUtils.copyProperties(descBo,pdPrdLstDesc);
                templateMapper.insertProductDesc(pdPrdLstDesc);
            }
        }
        catch (Exception ex) {
            log.debug(SEL_PRD_INFO_TMPL_INSERT_ERROR + ex.getMessage());
            throw new TemplateServiceExcetpion(TemplateServiceExcetpionMessage.PRD_INFO_TMPL_INSERT_ERROR, ex);
        }
    }

    public void insertOrdBasicDeliCost(List inventoryOrdBasiDeliCostVOList, long prdInfoTmpltNo) throws TemplateServiceExcetpion {
        try {
            // 수량기준 배송비 셋팅 1:N 관계 저장
            for(int i = 0 ; i < inventoryOrdBasiDeliCostVOList.size() ; i++)
            {
                PdPrdLstOrdBasiDlvCst pdPrdLstOrdBasiDlvCst = new PdPrdLstOrdBasiDlvCst();
                PdPrdLstOrdBasiDlvCstVO deliBo = (PdPrdLstOrdBasiDlvCstVO)inventoryOrdBasiDeliCostVOList.get(i);
                if(prdInfoTmpltNo != 0) {deliBo.setPrdInfoTmpltNo(prdInfoTmpltNo);}
                BeanUtils.copyProperties(deliBo,pdPrdLstOrdBasiDlvCst);
                templateMapper.insertOrdBasicDeliCost(pdPrdLstOrdBasiDlvCst);
            }
        }
        catch (Exception ex) {
            log.debug(SEL_PRD_INFO_TMPL_INSERT_ERROR + ex.getMessage());
            throw new TemplateServiceExcetpion(TemplateServiceExcetpionMessage.PRD_INFO_TMPL_INSERT_ERROR, ex);
        }
    }

    public void insertSellInformation(ProductInformationTemplateVO paramVO) throws TemplateServiceExcetpion {
        try {
            //상품정보번호를 세팅한다.
            SellInformationVO sellInformationVO = paramVO.getSellInformationVO();
            PdSelInfo pdSelInfo = new PdSelInfo();

            if (sellInformationVO != null) {

                BeanUtils.copyProperties(sellInformationVO,pdSelInfo);
                templateMapper.insertSellInformation(pdSelInfo);
            }
        }
        catch (Exception ex) {
            log.info(SEL_PRD_INFO_TMPL_INSERT_ERROR + ex.getMessage());
            throw new TemplateServiceExcetpion(TemplateServiceExcetpionMessage.PRD_INFO_TMPL_INSERT_ERROR, ex);
        }
    }

    public long insertProductInformationTemplate(ProductInformationTemplateVO paramVO) throws TemplateServiceExcetpion{
        long seq;
        PdPrdInfoTmplt pdPrdInfoTmplt = new PdPrdInfoTmplt();
        try {
            seq = templateMapper.getProductInformationTemplateSeq();
            if(paramVO == null) {
                log.debug("ProductInformationTemplateVO paramVO is null");
            }else {
                paramVO.getPrdInfoTmplVO().setPrdInfoTmpltNo(seq);
                BeanUtils.copyProperties(paramVO.getPrdInfoTmplVO(),pdPrdInfoTmplt);
                templateMapper.insertProductInformationTemplate(pdPrdInfoTmplt);
            }

        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("[" + this.getClass().getName() + ".insertProductInformationTemplate] MSG : "+ ex.getMessage(), ex);
            }
            throw new TemplateServiceExcetpion(TemplateServiceExcetpionMessage.PRD_INFO_TMPL_INSERT_ERROR, ex);
        }
        return seq;
    }

    public void insertTemplateInfoProcess(ProductVO productVO) throws TemplateServiceExcetpion{
        this.setProductTemplate(productVO.getTemplateVO(), null);
    }

    public void setTemplateValidateInfoProcess(ProductVO preProductVO, ProductVO productVO) throws TemplateServiceExcetpion{
        this.setSelStatCdTmplt(preProductVO, productVO);
    }


    public void setSelStatCdTmplt(ProductVO preProductVO, ProductVO productVO) {
        if(productVO.isUpdate()){
            if("Y".equals(preProductVO.getBaseVO().getTemplateYn())) {

                productVO.getBaseVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_BAN_SALE);
                productVO.getDpGnrlDispVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_BAN_SALE);
            }
        }
    }

    public List getProductInformationTemplateAllList(PrdInfoTmplVO paramVO) {
        List list = new ArrayList();
        list = templateMapper.getProductInformationTemplateAllList(paramVO);

        return list;
    }

    public ProductInformationTemplateVO getProductInformationTemplate(ProductInformationTemplateVO templateVO) {

        ProductInformationTemplateVO resultVO = new ProductInformationTemplateVO();
        PrdInfoTmplVO prdInfoTmplVO = new PrdInfoTmplVO();
        SellInformationVO sellInformationVO = new SellInformationVO();
        InventoryVO inventoryVO = new InventoryVO();

        prdInfoTmplVO = templateMapper.getProductInformationTemplate(templateVO);

        if(null == prdInfoTmplVO) {
            return resultVO;
        }

        if(ProductConstDef.PRD_INFO_TMPLT_CLF_CD_DLV.equals(prdInfoTmplVO.getPrdInfoTmpltClfCd())) {

            sellInformationVO = this.getSellInformationTemplateDetail(templateVO);

            InventoryOrderBasicDeliverCostVO inventoryOrderBasicDeliverCostVO = new InventoryOrderBasicDeliverCostVO();
            InventoryProductDescVO inventoryProductDescVO = new InventoryProductDescVO();
            InventoryProductAddressVO inventoryProductAddressVO = new InventoryProductAddressVO();

            inventoryOrderBasicDeliverCostVO.setPrdInfoTmpltNo(templateVO.getPrdInfoTmpltNo());
            inventoryOrderBasicDeliverCostVO.setDlvCstInstObjClfCd("02");
            inventoryVO.setInventoryOrdBasiDeliCostVOList(this.getOrderBasiDeliverCostVOList(inventoryOrderBasicDeliverCostVO));

            inventoryProductDescVO.setPrdDescObjNo(templateVO.getPrdInfoTmpltNo());
            inventoryProductDescVO.setPrdDescObjClfCd("02");
            inventoryVO.setInventoryProductDescVOList(this.getProductDescVOList(inventoryProductDescVO));

            inventoryProductAddressVO.setPrdAddrObjClfCd("02");
            inventoryProductAddressVO.setPrdAddrObjNo(templateVO.getPrdInfoTmpltNo());
            inventoryVO.setInventoryProductAddressVOList(this.getInventoryProductAddressList(inventoryProductAddressVO));

            resultVO.setSellInformationVO(sellInformationVO);
            resultVO.setInventoryVO(inventoryVO);
        }
        return resultVO;
    }

    private List getInventoryProductAddressList(InventoryProductAddressVO inventoryProductAddressVO) {
        List dataList = new ArrayList();
        InventoryProductAddressVO inOutAddressVO01 = null;
        InventoryProductAddressVO inOutAddressVO02 = null;
        InventoryProductAddressVO inOutAddressVO03 = null;
        InventoryProductAddressVO inOutAddressVO04 = null;
        InventoryProductAddressVO inOutAddressVO05 = null;
        String mbAddrLocation = "";
        InventoryProductAddressVO paramVO = new InventoryProductAddressVO();
        try
        {
            paramVO.setPrdAddrObjNo(inventoryProductAddressVO.getPrdAddrObjNo());
            paramVO.setPrdAddrObjClfCd(inventoryProductAddressVO.getPrdAddrObjClfCd());
            paramVO.setPrdAddrClfCd("01");//출고지
            //입력된 출고지 주소가 국내주소인지 해외주소인지 조회
            mbAddrLocation = templateMapper.getAddrLocation(paramVO);
            if(mbAddrLocation.equals("01")){ //국내
                inOutAddressVO01 = templateMapper.getInventoryProductAddress01List(paramVO);
            } else if(mbAddrLocation.equals("02")){ //해외
                inOutAddressVO01 = templateMapper.getInventoryProductAddress02List(paramVO);
            }

            paramVO.setPrdAddrClfCd("02");//교환/반품지
            //입력된 출고지 주소가 국내주소인지 해외주소인지 조회
            mbAddrLocation = templateMapper.getAddrLocation(paramVO);
            if(mbAddrLocation.equals("01")){ //국내
                inOutAddressVO02 = templateMapper.getInventoryProductAddress01List(paramVO);
            } else if(mbAddrLocation.equals("02")){ //해외
                inOutAddressVO02 = templateMapper.getInventoryProductAddress02List(paramVO);
            }

            paramVO.setPrdAddrClfCd("03");//방문수령지
            //입력된 출고지 주소가 국내주소인지 해외주소인지 조회
            mbAddrLocation = templateMapper.getAddrLocation(paramVO);
            if(mbAddrLocation != null) {
                if(mbAddrLocation.equals("01")){ //국내
                    inOutAddressVO03 = templateMapper.getInventoryProductAddress01List(paramVO);
                } else if(mbAddrLocation.equals("02")){ //해외
                    inOutAddressVO03 = templateMapper.getInventoryProductAddress02List(paramVO);
                }
            }
            paramVO.setPrdAddrClfCd("04");//판매자 해외출고지
            //입력된 출고지 주소가 국내주소인지 해외주소인지 조회
            mbAddrLocation = templateMapper.getAddrLocation(paramVO);
            if(mbAddrLocation != null) {
                if(mbAddrLocation.equals("01")){ //국내
                    inOutAddressVO04 = templateMapper.getInventoryProductAddress01List(paramVO);
                } else if(mbAddrLocation.equals("02")){ //해외
                    inOutAddressVO04 = templateMapper.getInventoryProductAddress02List(paramVO);
                }
            }

            paramVO.setPrdAddrClfCd("05");//판매자 교환/반품지
            //입력된 출고지 주소가 국내주소인지 해외주소인지 조회
            mbAddrLocation = templateMapper.getAddrLocation(paramVO);
            if(mbAddrLocation != null) {
                if(mbAddrLocation.equals("01")){ //국내
                    inOutAddressVO05 = templateMapper.getInventoryProductAddress01List(paramVO);
                } else if(mbAddrLocation.equals("02")){ //해외
                    inOutAddressVO05 = templateMapper.getInventoryProductAddress02List(paramVO);
                }
            }

            dataList.add(inOutAddressVO01);
            dataList.add(inOutAddressVO02);
            if(inOutAddressVO03 != null) {
                dataList.add(inOutAddressVO03);
            }
            if(inOutAddressVO04 != null)
                dataList.add(inOutAddressVO04);
            if(inOutAddressVO05 != null)
                dataList.add(inOutAddressVO05);
        }catch(Exception ex)
        {
            throw new TemplateServiceExcetpion(ex);
        }
        return dataList;
}

    private List getProductDescVOList(InventoryProductDescVO inventoryProductDescVO) {
        List result = new ArrayList();
        result = templateMapper.getProductDescVOList(inventoryProductDescVO);
        return result;
    }

    private List getOrderBasiDeliverCostVOList(InventoryOrderBasicDeliverCostVO inventoryOrderBasicDeliverCostVO) {
        List result = new ArrayList();
        result = templateMapper.getOrderBasiDeliverCostVOList(inventoryOrderBasicDeliverCostVO);
        return result;
    }

    private SellInformationVO getSellInformationTemplateDetail(ProductInformationTemplateVO templateVO) {
        SellInformationVO resultVO = new SellInformationVO();
        resultVO = templateMapper.getSellInformationTemplateDetail(templateVO);
        return resultVO;
    }

    public String getAddrBasiDlvCstTxt(ProductSellerBasiDeliveryCostVO addrBasiDlvCstVO) {
        String result = templateMapper.getAddrBasiDlvCstTxt(addrBasiDlvCstVO);
        return result;
    }
}