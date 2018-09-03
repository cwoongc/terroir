package com.elevenst.terroir.product.registration.ctgrattr.service;


import com.elevenst.common.properties.PropMgr;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.ctgrattr.entity.PdCtgrAttrValueEtc;
import com.elevenst.terroir.product.registration.ctgrattr.entity.PdPrdAttrComp;
import com.elevenst.terroir.product.registration.ctgrattr.entity.PdPrdAttrCompTxt;
import com.elevenst.terroir.product.registration.ctgrattr.entity.PdPrdAttrValueCd;
import com.elevenst.terroir.product.registration.ctgrattr.mapper.CtgrAttrMapper;
import com.elevenst.terroir.product.registration.ctgrattr.validate.CtgrAttrValidate;
import com.elevenst.terroir.product.registration.ctgrattr.vo.CtgrAttrCompVO;
import com.elevenst.terroir.product.registration.ctgrattr.vo.CtgrAttrVO;
import com.elevenst.terroir.product.registration.ctgrattr.vo.CtgrAttrValueVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CtgrAttrServiceImpl {

    @Autowired
    private CtgrAttrMapper ctgrAttrMapper;

    @Autowired
    private CtgrAttrValidate ctgrAttrValidate;

    @Autowired
    private PropMgr progMgr;



    /**
     * 상품등록 시 키속성값등록
     * valueObjClfCd를 파라미터로 추가하여 물품대장이나 상품을 선택할 수 있게함.
     */

    public List<CtgrAttrVO> getServiceKeyAttributeListByDispCtgNoPrdSecond(HashMap param) {

        return ctgrAttrMapper.getServiceKeyAttributeListByDispCtgNoPrdSecond(param);
    }

    public List<CtgrAttrValueVO> getAttributeValueByOneAttributeNo(HashMap param){
        return ctgrAttrMapper.getAttributeValueByOneAttributeNo(param);
    }

    public void setCtgrAttrInfoProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException {
        ctgrAttrValidate.checkCtgrAttrCompVO(productVO);
    }

    public void mergeCtgrAttrInfoProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException {
        this.serviceInsertSellerKeyAttributeValue(productVO);
    }


    private void serviceInsertSellerKeyAttributeValue(ProductVO productVO) throws ProductException {

        long objPrdNo = productVO.getPrdNo();
        long dispCtgrNo = productVO.getDispCtgrNo();
        List<CtgrAttrCompVO> ctgrAttrCompVOs = productVO.getCtgrAttrCompVOList();
        String valueObjClfCd = "01";
        boolean overrideAttr = true;
        boolean isUpdate = productVO.isUpdate();

        long hgrnkAttrValNo = 0;
        long infoTypeCtgrNo = 0;	// 상품정보제공 유형 카테고리 번호

        Set<String> beforePrdAttrCdSet = new HashSet<String>();
        Map<String, Object> compareMap = new HashMap<String, Object>();


        if (ctgrAttrCompVOs != null && ctgrAttrCompVOs.size() >0)
        {

            infoTypeCtgrNo = productVO.getBaseVO().getInfoTypeCtgrNo();	// 상품정보제공 유형 카테고리 번호

            // 현재 상품속성/속성값구성 조회

            if(isUpdate) {
                updatePreProcess(objPrdNo, dispCtgrNo, ctgrAttrCompVOs, valueObjClfCd, overrideAttr, isUpdate, infoTypeCtgrNo, beforePrdAttrCdSet, compareMap);
                releaseLifeTourAttrValue(productVO, infoTypeCtgrNo);
            }

            // 상품별 속성/속성값 등록
            for (int i=0;i<ctgrAttrCompVOs.size();i++)
            {

                CtgrAttrCompVO ctgrAttrCompVo = ctgrAttrCompVOs.get(i);
                ctgrAttrCompVo.setObjPrdNo(objPrdNo);
                ctgrAttrCompVo.setHgrnkAttrValNo(hgrnkAttrValNo);
                ctgrAttrCompVo.setDispCtgrNo(dispCtgrNo);
                ctgrAttrCompVo.setInfoTypeCtgrNo(infoTypeCtgrNo);
                ctgrAttrCompVo.setValueObjClfCd(valueObjClfCd);
                ctgrAttrCompVo.setCreateNo(productVO.getSelMnbdNo());
                //PdPrdAttrComp pdPrdAttrComp = convertAttrCompVOtoEntity(ctgrAttrCompVOs.get(i) ,objPrdNo, hgrnkAttrValNo, dispCtgrNo);

                if(!overrideAttr && beforePrdAttrCdSet.contains(ctgrAttrCompVo.getPrdAttrCd())) {
                    //기존 속성이 있을 경우 스킵.
                    continue;
                }

                //paramBO.setObjPrdNo(objPrdNo);
                //paramBO.setHgrnkAttrValNo(hgrnkAttrValNo);
                //paramBO.setDispCtgrNo(dispCtgrNo);
                if(StringUtil.nvl(progMgr.get1hourTimeProperty("ADD_ATTR_LIFEPLUS_CATEGORY"),"").indexOf("|" + dispCtgrNo + "|") > -1 && StringUtil.isEmpty(ctgrAttrCompVo.getPrdAttrValueNm())) {
                    continue;
                } else {
                    insertSellerAttributeValue( ctgrAttrCompVo , productVO );
                    //pdPrdAttrComp.setQCValifiedStat(true);
                }
                hgrnkAttrValNo = ctgrAttrCompVo.getPrdAttrValueNo();

                // 기존 상품 속성/속성값 데이터가 제거된 비교 해쉬맵 데이터를 히스토리에 등록
                /* 이력 등록 insertSellerAttributeValue로 이동
                String key = ctgrAttrCompVo.getPrdAttrCd() + ":" + ctgrAttrCompVo.getPrdAttrValueNm();
                if (compareMap.containsKey(key)) {
                    // attrType이 (02): 상품제공고시 속성이고 직접입력 일 경우에는 상품별 직접입력 속성/속성값 히스토리 관리(PD_PRD_ATTR_COMP_TXT_HIST) 테이블에 등록 처리)
                    if ("02".equals(ctgrAttrCompVo.getAttrType()) && StringUtil.isEmpty(ctgrAttrCompVo.getPrdAttrValueCd())) {
                        managerBO.insertAttributeValueCompTxtHist(paramBO);
                    }
                    // attrType에 상관없이 속성/속성값 코드 존재 시 상품별 속성/속성값 히스토리 관리 테이블에 데이터 등록
                    else if( !StringUtil.isEmpty(paramBO.getPrdAttrCd()) && !StringUtil.isEmpty(paramBO.getPrdAttrValueCd())) {
                        managerBO.insertAttributeValueCompHist(paramBO);
                    }
                }
                */
            }
        }

    }



    private void releaseLifeTourAttrValue(ProductVO productVO, long infoTypeCtgrNo) throws ProductException {
        long objPrdNo = productVO.getPrdNo();
        long dispCtgrNo = productVO.getDispCtgrNo();

        //현재 상품의 등록된 값이 여행속성일 경우에 해당 속성값의 정보를 삭제 후 등록 처리
//        List<CategoryVO> displayCategoryList = categoryMapper.getDisplayCategoryParentList(dispCtgrNo);

        //ProductRegistrationService prdRegService = (ProductRegistrationService) ServiceFactory.createService(ProductRegistrationServiceImpl.class);
        List<CtgrAttrVO> ctgrAttrTourLifeList = null;

        long dispCtgr2No = productVO.getCategoryVO().getMctgrNo();
//        if (displayCategoryList != null && displayCategoryList.size() >= 2) {
//            CategoryVO displayCategoryBO = displayCategoryList.get(displayCategoryList.size() - 2);
//            dispCtgr2No = displayCategoryBO.getDispCtgrNo();
//
//        }

        if (StringUtil.nvl(progMgr.get1hourTimeProperty("ADD_ATTR_TOUR_CATEGORY"), "").indexOf("|" + dispCtgr2No + "|") > -1) {
            //여행카테고리에 속해있는 속성정보를 가져온다.
            ctgrAttrTourLifeList = getCtgrAttributeListForTour(objPrdNo, dispCtgr2No, "N", infoTypeCtgrNo, null, "N", ProductConstDef.CATE_ATTR_SVC_TYP_CD);
        } else if (StringUtil.nvl(progMgr.get1hourTimeProperty("ADD_ATTR_LIFEPLUS_CATEGORY"), "").indexOf("|" + dispCtgrNo + "|") > -1) {
            //생활플러스에 속해있는 속성정보를 가져온다.
            ctgrAttrTourLifeList = getCtgrAttributeListForTour(objPrdNo, dispCtgrNo, "N", infoTypeCtgrNo, null, "N", ProductConstDef.CATE_LC_ATTR_SVC_TYP_CD);
        }

        if(ctgrAttrTourLifeList != null) {
            for (CtgrAttrVO ctgrAttrVO : ctgrAttrTourLifeList) {
                //카테고리의 속성값에 대한 내용을 삭제한다.
                //AttributeValueCompVO paramBO = (AttributeValueCompBO) BOFactory.createBO(AttributeValueCompBO.class);
                HashMap hashMap = new HashMap();
                hashMap.put("objPrdNo",objPrdNo);
                hashMap.put("prdAttrCd",ctgrAttrVO.getPrdAttrValueCd());

                ctgrAttrMapper.deletePrdAttrValue(hashMap);
            }
        }
    }


    private void updatePreProcess
            (long objPrdNo, long dispCtgrNo, List<CtgrAttrCompVO> attributeCompBOList, String valueObjClfCd, boolean overrideAttr, boolean isUpdate,
             long infoTypeCtgrNo, Set<String> beforePrdAttrCdSet, Map<String, Object> compareMap ) throws ProductException {
        HashMap abo = new HashMap();
        abo.put("dispCtgrNo" , dispCtgrNo);
        abo.put("objPrdNo", objPrdNo);
        abo.put("cmAttrYn", "Y");
        abo.put("inforTypeCtgrNo" , infoTypeCtgrNo);
        abo.put("valueObjClfCd", valueObjClfCd);
        /*
        abo.setDispCtgrNo(dispCtgrNo);
        abo.setObjPrdNo(objPrdNo);
        abo.setCmAttrYn("Y");
        abo.setInfoTypeCtgrNo(infoTypeCtgrNo);
        abo.getAttributeValueBO().setValueObjClfCd(valueObjClfCd);
        */
        List<CtgrAttrVO> list = ctgrAttrMapper.getServiceKeyAttributeListByDispCtgNoPrdSecond(abo);


        // 등록 상품 속성/속성값 데이터 비교 해쉬맵에 등록
        for (CtgrAttrCompVO attributeValueCompBO : attributeCompBOList) {
            compareMap.put(attributeValueCompBO.getPrdAttrCd() + ":" + attributeValueCompBO.getPrdAttrValueNm(), attributeValueCompBO);
        }

        // 기존 상품 속성/속성값 데이터를 비교 해쉬맵에서 제거 및 기존 상품에 등록된 속성셋.

        for (CtgrAttrVO ctgrAttributeBO : list) {
            compareMap.remove(ctgrAttributeBO.getPrdAttrValueCd() + ":" + ctgrAttributeBO.getPrdAttrValueCompValueNm());

            if (StringUtils.isNotEmpty(ctgrAttributeBO.getPrdAttrValueCompValueNm())) {
                beforePrdAttrCdSet.add(ctgrAttributeBO.getPrdAttrValueCd());
            }
        }

        // 상품속성/속성값구성 삭제 : 키속성인것만 지움
        HashMap hashMap = new HashMap();
        hashMap.put("objPrdNo", objPrdNo);
        hashMap.put("dispCtgrNo", dispCtgrNo);
        hashMap.put("keyAttrYn", "Y");
        hashMap.put("valueObjClfCd", valueObjClfCd);
        hashMap.put("infoTypeCtgrNo", infoTypeCtgrNo);
        /*
        attributeValueCompBO.setObjPrdNo(objPrdNo);
        attributeValueCompBO.setDispCtgrNo(dispCtgrNo);
        attributeValueCompBO.setKeyAttrYn("Y");
        attributeValueCompBO.setValueObjClfCd(valueObjClfCd);
        attributeValueCompBO.setInfoTypeCtgrNo(infoTypeCtgrNo);
        */

        List<String> newPrdAttrCdList = getNewPrdAttrCdList(beforePrdAttrCdSet, attributeCompBOList);

        if(!overrideAttr && !newPrdAttrCdList.isEmpty()) {
            hashMap.put("newPrdAttrCdList", newPrdAttrCdList);
            //attributeValueCompBO.setNewPrdAttrCdList(newPrdAttrCdList);
        }

        if(overrideAttr ||  !newPrdAttrCdList.isEmpty()){
            // 상품정보고시 일괄등록일 경우 카테고리 번호 0
            if (dispCtgrNo == 0 ) {
                ctgrAttrMapper.serviceDeletePrdAttrValueCompAll(hashMap);
            } else {
                ctgrAttrMapper.serviceDeletePrdAttrValueComp(hashMap);
            }
        }


    }

    /**
     * 새롭게 추가될 속성코드 목록 반환.
     *
     * @param beforeAttrCdSet   기존 상품에 등록된 속성셋.
     * @param afterAttrCdList   상품에 등록할 속성목록.
     * @return 새로운 속성코드 목록.
     */
    private List<String> getNewPrdAttrCdList(Set<String> beforeAttrCdSet, List<CtgrAttrCompVO> afterAttrCdList) {
        List<String> results = new ArrayList<String>();
        for (CtgrAttrCompVO attributeValueCompBO : afterAttrCdList) {
            String prdAttrCd = attributeValueCompBO.getPrdAttrCd();
            if(!beforeAttrCdSet.contains(prdAttrCd)) {
                results.add(prdAttrCd);
            }
        }

        return results;
    }



    /**
     * 카테고리 필수 속성 리스트 조회
     */
    public List<CtgrAttrVO> getCtgrAttributeList
    (long prdNo, long dispCtgrNo, String modifyCtgrYn, long infoTypeCtgrNo, String mode )  throws ProductException {

        List<CtgrAttrVO> attrList = null;
        try {

            /**
             * 1. 카테고리별 필수속성정보가져오기  AttributeService호출
             * 예) 제조사/브랜드/모델명
             * 		카테고리 전시카테고리정보 가져오기 (성인인증여부 , 서비스쿠폰상품 여부, 제조일자 유효일자 필수 여부) 가져오기
             */
            if ("Y".equals(modifyCtgrYn)) {
                prdNo = 0;
            }
            attrList = this.getSelectedKeyAttributeValueListByDispCtgNoPrdSecond(prdNo, "01", dispCtgrNo, infoTypeCtgrNo, mode, null) ;

        } catch (Exception e) {
            throw new ProductException("카테고리 속성 정보 조회", e);
        }

        return attrList;

    }

    public CtgrAttrVO getPrdBrandInfo(long prdNo){
        ArrayList<String> oonlyIncludeAttrCd = new ArrayList<String>();
        return ctgrAttrMapper.getPrdBrandInfo(prdNo);
        //oonlyIncludeAttrCd.add("11821");

        //return this.getSelectedKeyAttributeValueListByDispCtgNoPrdSecond(prdNo, "01",dispCtgrNo,0,null,oonlyIncludeAttrCd);
    }


    public List getSelectedKeyAttributeValueListByDispCtgNoPrdSecond
            (long objPrdNo, String valueObjClfCd, long dispCtgrNo, long infoTypeCtgrNo,
             String mode, List<String> onlyIncludeAttrCd ) throws ProductException {

        HashMap param = new HashMap();
        param.put("objPrdNo",objPrdNo);
        param.put("valueObjClfCd",valueObjClfCd);
        param.put("dispCtgrNo", dispCtgrNo);
        param.put("infoTypeCtgrNo", infoTypeCtgrNo);
        param.put("onlyIncludeAttrCd", onlyIncludeAttrCd);
        param.put("mode", mode);


        List<CtgrAttrVO> list = ctgrAttrMapper.getServiceKeyAttributeListByDispCtgNoPrdSecond(param);
        List<CtgrAttrVO> tempList = new ArrayList<CtgrAttrVO>();

        //중복속성 제거 처리
        if(list.size() > 0){
            for(int i=0; i<list.size(); i++){
                skipDuplicationAttr(tempList, list.get(i));
            }
        }
        /* 모바일에선 속성값 내리지 않는다 */
        /*
        // API로 조회 시에는 속성값 조회 안하도록 처리.
        if( !"API".equals(mode) ) {
            for (CtgrAttrVO tempCtgrAttrVO : tempList) {
                // 최상위 속성이나 상위속성이 없는 속성의 속성값 조회
                if (tempCtgrAttrVO.getHgrnkAttrNo() == 0) {
                    param.put("prdAttrNo", tempCtgrAttrVO.getPrdAttrNo() );
                    //paramBO.setPrdAttrNo(tempCtgrAttrBO.getPrdAttrNo());

                    List<CtgrAttrValueVO> attributeValueList = ctgrAttrMapper.getAttributeValueByOneAttributeNo(param);
                    // 상품수정시 속성이 복수선택 방식일때 값이 복수개일 수 있음으로 데이터를 별도로 가져와서 처리한다.
                    // ** 주의 : 아래 로직이 비효율적이나 필수속성이 복수선택방식이 아닌 경우가 많고 있다고 하더라도
                    //          그 값이 많지 않을 것임으로 아래와 같이 별도로 값을 가져와서 selected 설정한다.

                    if (objPrdNo > 0 && tempCtgrAttrVO.getAttrEntWyCd().equals("05")) {
                        param.put("prdAttrCd",tempCtgrAttrVO.getPrdAttrValueCd());//paramBO.setPrdAttrCd(tempCtgrAttrBO.getPrdAttrValueCd());
                        List<CtgrAttrValueVO> selectedAttrValueList = ctgrAttrMapper.getAttributeValueByOnePrdAttrValueCd(param);
                        for (int j=0; attributeValueList != null && j < attributeValueList.size(); j++) {
                            for (int n=0; selectedAttrValueList != null && n < selectedAttrValueList.size(); n++)
                                if ( attributeValueList.get(j).getPrdAttrValueNm().equals(selectedAttrValueList.get(n).getPrdAttrValueNm()) ) {
                                    attributeValueList.get(j).setSelectYn("Y");
                                    break;
                                }
                        }

                    }
                    tempCtgrAttrVO.setCtgrAttrValueVOList(attributeValueList);
                }

            }
        }
        */

        return tempList;
    }

    public List<CtgrAttrVO> getCtgrAttributeListForTour (long prdNo, long dispCtgrNo, String modifyCtgrYn, long infoTypeCtgrNo, String mode, String sellerPrdKeyAttrYn, String svcTypCd)  throws ProductException {

        List<CtgrAttrVO> attrList = null;
        try{

            /**
             * 1. 카테고리별 필수속성정보가져오기  AttributeService호출
             * 예) 제조사/브랜드/모델명
             * 		카테고리 전시카테고리정보 가져오기 (성인인증여부 , 서비스쿠폰상품 여부, 제조일자 유효일자 필수 여부) 가져오기
             */
            if ("Y".equals(modifyCtgrYn)) {
                prdNo = 0;
            }

            attrList = this.getSelectedKeyAttributeValueListByDispCtgNoPrdSecondForTour( prdNo,  dispCtgrNo,  modifyCtgrYn,  infoTypeCtgrNo,  mode,  sellerPrdKeyAttrYn,  svcTypCd);

        } catch (Exception e) {
            throw new ProductException("카테고리 속성 정보 조회", e);
        }

        return attrList;
    }

    public List getSelectedKeyAttributeValueListByDispCtgNoPrdSecondForTour(long prdNo, long dispCtgrNo, String modifyCtgrYn, long infoTypeCtgrNo, String mode, String sellerPrdKeyAttrYn, String svcTypCd) throws ProductException {

        HashMap param = new HashMap();
        param.put("dispCtgrNo", dispCtgrNo);//ctgrAttrVO.setDispCtgrNo(dispCtgrNo);
        param.put("objPrdNo", prdNo);
        param.put("infoTypeCtgrNo",infoTypeCtgrNo);//ctgrAttrVO.setInfoTypeCtgrNo(infoTypeCtgrNo);
        param.put("valueObjClfCd","01");//ctgrAttrVO.getAttributeValueBO().setValueObjClfCd("01");
        param.put("mode",mode);//ctgrAttrVO.setMode(mode);
        param.put("sellerPrdKeyAttrYn",sellerPrdKeyAttrYn);//ctgrAttrVO.setSellerPrdKeyAttrYn(sellerPrdKeyAttrYn);
        param.put("svcTypCd",svcTypCd);//ctgrAttrVO.setSvcTypCd(svcTypCd);
        param.put("cmAttrYn","Y"); //setCmAttrYn("Y");

        List<CtgrAttrVO> list = ctgrAttrMapper.getServiceKeyAttributeListByDispCtgNoPrdSecondForTour(param);

        List<CtgrAttrVO> tempList = new ArrayList<CtgrAttrVO>();

        // 중복 속성 제거
        if(list !=null && list.size() > 0){
            for(int i=0; i<list.size(); i++){
                skipDuplicationAttr(tempList, list.get(i));
            }
        }

        // API로 조회 시에는 속성값 조회 안하도록 처리.
        if( !"API".equals(param.get("mode")) ) {

            //param.put("objPrdNo", )//paramBO.setObjPrdNo(ctgrAttributeBO.getObjPrdNo());
            //paramBO.setValueObjClfCd(ctgrAttributeBO.getAttributeValueBO().getValueObjClfCd());
            for (CtgrAttrVO tempCtgrAttrVO : tempList) {

                // 최상위 속성이나 상위속성이 없는 속성의 속성값 조회
                //if (tempCtgrAttrBO.getHgrnkAttrNo() == 0) {
                param.put("prdAttrNo",tempCtgrAttrVO.getPrdAttrNo()); // paramBO.setPrdAttrNo(tempCtgrAttrBO.getPrdAttrNo());
                param.put("tourYn", "N"); //paramBO.setTourYN("Y");

                List<CtgrAttrValueVO> attributeValueList = ctgrAttrMapper.getAttributeValueByOneAttributeNo(param);
                // 상품수정시 속성이 복수선택 방식일때 값이 복수개일 수 있음으로 데이터를 별도로 가져와서 처리한다.
                // ** 주의 : 아래 로직이 비효율적이나 필수속성이 복수선택방식이 아닌 경우가 많고 있다고 하더라도
                //          그 값이 많지 않을 것임으로 아래와 같이 별도로 값을 가져와서 selected 설정한다.
                if (prdNo > 0 && tempCtgrAttrVO.getAttrEntWyCd().equals("05")) {
                    param.put("prdAttrCd",tempCtgrAttrVO.getPrdAttrValueCd());//.setPrdAttrCd(tempCtgrAttrVO.getPrdAttrValueCd());
                    List<CtgrAttrValueVO> selectedAttrValueList = ctgrAttrMapper.getAttributeValueByOnePrdAttrValueCd(param);
                    for (int j=0; attributeValueList != null && j < attributeValueList.size(); j++) {
                        for (int n=0; selectedAttrValueList != null && n < selectedAttrValueList.size(); n++)
                            if ( attributeValueList.get(j).getPrdAttrValueNm().equals(selectedAttrValueList.get(n).getPrdAttrValueNm()) ) {
                                attributeValueList.get(j).setSelectYn("Y");
                                break;
                            }
                    }

                }
                tempCtgrAttrVO.setCtgrAttrValueVOList(attributeValueList);
                //}

            }
        }

        return tempList;
    }


    /**
     * 중복 속성 제거 method
     * @param _list
     * @param _ctgrAttributeBO
     */
    private void skipDuplicationAttr(List<CtgrAttrVO> _list, CtgrAttrVO _ctgrAttributeBO) {

        boolean flag = false;

        for (CtgrAttrVO ctgrAttributeBO : _list) {
            if (ctgrAttributeBO.getPrdAttrValueCd().equals(_ctgrAttributeBO.getPrdAttrValueCd())) {
                flag = true;
                break;
            }
        }

        if (!flag) _list.add(_ctgrAttributeBO);

    }


    /**
     *   셀러가 속성에 속성값을 입력시
     * - 입력형+ 선택형
     * - 서비스 : 상품등록 (속성값등록)
     * - 속성속성값의 동의어가 값이 입력값으로 오면 자동으로 권장값(표준값)으로 변환해서 저장
     * - 상품번호,속성번호,속성값명(속성번호),valueObjClfCd=01,CreateNo,전시카테고리번호,표준카테고리번호
     * - 포문으로 호출됨
     * @throws ProductException
     */
    public void insertSellerAttributeValue(CtgrAttrCompVO paramVo , ProductVO productVO) throws ProductException {

        try {

            long objPrdNo 			= paramVo.getObjPrdNo();						// 상품 번호
            String attrCd 			= paramVo.getPrdAttrCd();						// 속성 코드
            //String attrValueCd 		= paramVo.getPrdAttrValueCd();					// 속성값 코드
            String prdAttrValueCd 	= StringUtil.nvl(paramVo.getPrdAttrValueCd());	// 속성값 코드
            String attrEntWyCd 		= StringUtil.nvl(paramVo.getPrdAttrEntyCd());	// 속성값 입력방식 코드
            long prdAttrNo 		    = paramVo.getPrdAttrNo();					    // 속성 번호	(PK)
            //long prdAttrValueNo 	= paramVo.getPrdAttrValueNo();					// 속성값 번호 (PK) 이게 꼭 필요한가?

            String valueObjClfCd 	= paramVo.getValueObjClfCd();					// 적용대상 구분 코드 (일반상품:01)
            String prdAttrNm 		= StringUtil.nvl(paramVo.getPrdAttrNm());		// 속성 명
            String prdAttrValueNm	= StringUtil.nvl(paramVo.getPrdAttrValueNm());	// 속성값 명
            long dispCtgrNo 		= paramVo.getDispCtgrNo();						// 전시 카테고리 번호
            long createNo 		    = paramVo.getCreateNo();					    // 등록자 번호
            String attrType			= paramVo.getAttrType();						// 속성 타입 (null, 01): 기존속성, (02): 상품제공고시 속성
            long infoTypeCtgrNo	    = paramVo.getInfoTypeCtgrNo();					// 상품정보제공 유형 카테고리 번호



            if(prdAttrValueNm==null || prdAttrValueNm.length()==0){
                throw new ProductException("속성값이 등록되지않았습니다.");
            }
            else if(prdAttrValueNm.indexOf("<")>=0 || prdAttrValueNm.indexOf(">")>=0){
                throw new ProductException("속성값에는 '<' 또는 '>' 문자를 포함할 수 없습니다.");
            }



            // 속성번호가 존재한경우
            if (prdAttrNo>0) {
                // 속성이 선택형인 경우를 찾기 위해 필요함 - 선택형인경우 다른 속성을 선택할수 없음 (해당 속성 데이터 조회)
                CtgrAttrValueVO ctgrAttr= ctgrAttrMapper.getCommonAttributeInfoByNo(prdAttrNo);
                if(ctgrAttr != null){
                    attrEntWyCd = StringUtil.nvl(ctgrAttr.getAttrEntWyCd(),"");
                }


                PdPrdAttrValueCd cdent =  new PdPrdAttrValueCd();

                cdent.setAttrValueClfCd("02"); 					// 속성값 구분코드
                cdent.setPrdAttrValueNm(prdAttrValueNm.trim());	// 속성값 명

                HashMap attrPrarm = new HashMap();
                attrPrarm.put("attrValueClfCd","02");
                attrPrarm.put("prdAttrValueNm",prdAttrValueNm.trim());
                attrPrarm.put("dispCtgrNo",dispCtgrNo);
                attrPrarm.put("infoTypeCtgrNo",infoTypeCtgrNo);
                attrPrarm.put("prdAttrCd", attrCd);

                // (중요) pd_prd_attr_comp 에 속성값이 들어가면서 동의어값이 올때는 원 속성값을 가져오도록 수정함.
                // 속성값명을 통해 동의어 와 사전테이블에서 확인하여 사전 테이블 속성값 코드, 속성값 명 조회
                if(prdAttrValueCd.isEmpty()) {
                    CtgrAttrValueVO resultVo = ctgrAttrMapper.getCodeByAttributeAndValueNm3(attrPrarm);

                    // 사전 테이블에 존재하는 동의어일 경우 사전 테이블의 속성값 코드, 속성값 명으로 대체 변경
                    if (resultVo != null) {
                        prdAttrValueCd = resultVo.getPrdAttrValueCd();                // 속성값 코드
                        cdent.setPrdAttrValueNm(resultVo.getPrdAttrValueNm());        // 속성값 명
                        paramVo.setPrdAttrValueNm(resultVo.getPrdAttrValueNm());      // 속성값 명
                    }

                }else{
                    cdent.setPrdAttrValueNm(prdAttrValueNm);
                }

                if(attrCd.equals("11821")) {
                    productVO.getBaseVO().setBrandCd(prdAttrValueCd);
                }

                // attrType이 (02): 상품제공고시 속성이 아니고 사전 테이블에 등록 속성값 미 존재 시 해당 속성값을 사전 테이블에 등록
                if(!"02".equals(attrType) && (prdAttrValueCd == null || prdAttrValueCd.length() < 1)){
                    // 1.속성값 코드 등록
                    String codeNewSeq = ctgrAttrMapper.getAttributeAndValueCodeNewSeq();//("AttributeAndValueCodeGetNewSeq");
                    cdent.setPrdAttrValueCd(codeNewSeq);
                    cdent.setCreateNo(createNo);
                    cdent.setPrdAttrAttrRegSrcCd("03"); 	// 속성/속성값 등록 주체 구분코드 (셀러 등록)
                    cdent.setUseYn("Y");
                    cdent.setUpdateNo(createNo);
                    cdent.setCmCdYn("N"); 					// 기준속성, 속성값 여부 (임시 속성값)
                    ctgrAttrMapper.insertAttributeAndValueCode(cdent);
                    prdAttrValueCd = codeNewSeq;		// 새로운 sequence 속성값 코드 설정
                }

                // attrType이 (02): 상품제공고시 속성이 아니고 기존 속성값에 없는 경우 - 즉, 직접입력(기타)를 통해 들어오는 경우
                // (중요) pd_ctgr_attr_value_etc 테이블에 직접입력된 속성값을 insert하고 pd_prd_attr_comp의 속성값은 기타로 처리한다.
                CtgrAttrValueVO resultConnVo = ctgrAttrMapper.getCodeByAttributeAndValueNm2(attrPrarm); // cd에서 조회




                if(!"02".equals(attrType) && (resultConnVo == null || resultConnVo.getPrdAttrValueCd() ==null || "".equals(resultConnVo.getPrdAttrValueCd()))){

                    // 선택형인경우 다른 속성값이 들어갈 수없음
                    if("01".equals(attrEntWyCd) || "05".equals(attrEntWyCd)){ // 선택형 또는 다중선택형인경우는 resultConnVo 에서 조회된 값이 여야함
                        if( log.isWarnEnabled() ) {
                            log.warn("Product Attr Error : " + ",prdAttrNo=" + prdAttrNo + ",objPrdNo=" + objPrdNo + ",attrType=" + attrType);
                        }
                        throw new ProductException("속성이 선택형인 경우 다른 속성값을 입력할 수 없습니다.");
                    }

                    PdCtgrAttrValueEtc etcEnt = new PdCtgrAttrValueEtc();
                    //2.속성값 기타 코드 등록
                    String etcNewSeq = ctgrAttrMapper.getAttributeValueEtcNewSeq(); //pd_ctgr_attr_value_etc의 키 생성
                    etcEnt.setAttrValueEtcNo(Long.parseLong(etcNewSeq));
                    etcEnt.setAttrValueEtcNm(cdent.getPrdAttrValueNm()); // 새로 등록될 속성값명
                    etcEnt.setAttrCd(attrCd);
                    etcEnt.setDispCtgrNo(dispCtgrNo);
                    etcEnt.setAttrValueCd(prdAttrValueCd);
                    etcEnt.setCreateNo(createNo);
                    etcEnt.setUpdateNo(createNo);
                    //managerBO.insertAttributeAndValueCode(cdbo);
                    ctgrAttrMapper.insertAttributeValueEtc(etcEnt);
                }

            }

            if( objPrdNo < 1){
                throw new ProductException("오류 : 상품등록 번호오류  ");
            }

            paramVo.setObjPrdNo(objPrdNo);
            paramVo.setPrdAttrValueCd(prdAttrValueCd);
            paramVo.setPrdAttrNo(prdAttrNo);
            //paramVo.setPrdAttrValueNo(prdAttrValueNo);
            paramVo.setUpdateNo(createNo);


            // attrType이 (02): 상품제공고시 속성이고 직접입력 일 경우에는 상품별 직접입력 속성/속성값 관리(PD_PRD_ATTR_COMP_TXT) 테이블에 등록 처리)
            if ("02".equals(attrType) && StringUtil.isEmpty(paramVo.getPrdAttrValueCd())) {
                PdPrdAttrCompTxt pdPrdAttrCompTxt = new PdPrdAttrCompTxt();
                pdPrdAttrCompTxt.setObjPrdNo(objPrdNo);
                pdPrdAttrCompTxt.setPrdAttrCd(paramVo.getPrdAttrCd());
                pdPrdAttrCompTxt.setObjClfCd(paramVo.getValueObjClfCd());
                pdPrdAttrCompTxt.setPrdAttrValueNm(paramVo.getPrdAttrValueNm());
                pdPrdAttrCompTxt.setCreateNo(createNo);
                pdPrdAttrCompTxt.setUpdateNo(createNo);
                ctgrAttrMapper.insertAttributeValueCompTxt(pdPrdAttrCompTxt);
                ctgrAttrMapper.insertAttributeValueCompTxtHist(pdPrdAttrCompTxt);
                //managerBO.insertAttributeValueCompTxt(paramBo);
            }
            // attrType에 상관없이 속성/속성값 코드 존재 시 상품별 속성/속성값 관리 테이블에 데이터 등록
            else if( !StringUtil.isEmpty(paramVo.getPrdAttrCd()) && !StringUtil.isEmpty(paramVo.getPrdAttrValueCd())) {

                PdPrdAttrComp pdPrdAttrComp = new PdPrdAttrComp();
                pdPrdAttrComp.setObjPrdNo(objPrdNo);
                pdPrdAttrComp.setPrdAttrCd(paramVo.getPrdAttrCd());
                pdPrdAttrComp.setPrdAttrValueCd(prdAttrValueCd);
                pdPrdAttrComp.setObjClfCd(paramVo.getValueObjClfCd());
                pdPrdAttrComp.setPrdAttrValueNm(paramVo.getPrdAttrValueNm());
                pdPrdAttrComp.setPrdImgNo(0);
                pdPrdAttrComp.setCreateNo(createNo);
                pdPrdAttrComp.setUpdateNo(createNo);



                ctgrAttrMapper.insertAttributeValueComp(pdPrdAttrComp);
                ctgrAttrMapper.insertAttributeValueCompHist(pdPrdAttrComp);
            }

            // sqlMap.commitTransaction();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ProductException("상품 속성값 체크시 오류");
        }

    }

    public List getServiceKeyAttributeListByDispCtgNo(long dispCtgrNo, boolean isAttrList, long infoTypeCtgrNo) throws ProductException {
        HashMap param = new HashMap();
        param.put("dispCtgrNo",dispCtgrNo);
        param.put("infoTypeCtgrNo",infoTypeCtgrNo);
        List<CtgrAttrVO> list =ctgrAttrMapper.getServiceKeyAttributeListByDispCtgNo(param);

        List<CtgrAttrVO> tempList = new ArrayList<CtgrAttrVO>();

        if(list!=null && list.size()>0){

            for(int i=0; i<list.size(); i++){

                // 중복 속성 제거
                skipDuplicationAttr(tempList, list.get(i));

            }
        }

        if(isAttrList)
        {
            for (CtgrAttrVO ctgrAttributeVO : tempList) {
                long prdAttrNo = ctgrAttributeVO.getPrdAttrNo();
                List<CtgrAttrValueVO> attributeValueList =ctgrAttrMapper.getAttributeValueByAttributeNoList(prdAttrNo);
                ctgrAttributeVO.setCtgrAttrValueVOList(attributeValueList);
            }
        }
        return tempList;
    }

}
