package com.elevenst.terroir.product.registration.ctgrattr.mapper;


import com.elevenst.terroir.product.registration.ctgrattr.entity.PdCtgrAttrValueEtc;
import com.elevenst.terroir.product.registration.ctgrattr.entity.PdPrdAttrComp;
import com.elevenst.terroir.product.registration.ctgrattr.entity.PdPrdAttrCompTxt;
import com.elevenst.terroir.product.registration.ctgrattr.entity.PdPrdAttrValueCd;
import com.elevenst.terroir.product.registration.ctgrattr.vo.CtgrAttrVO;
import com.elevenst.terroir.product.registration.ctgrattr.vo.CtgrAttrValueVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface CtgrAttrMapper {

    List <CtgrAttrVO> getServiceKeyAttributeListByDispCtgNoPrdSecond(HashMap hashMap);
    List <CtgrAttrValueVO> getAttributeValueByOneAttributeNo(HashMap hashMap);
    List <CtgrAttrValueVO> getAttributeValueByOnePrdAttrValueCd(HashMap hashMap);
    List <CtgrAttrVO> getServiceKeyAttributeListByDispCtgNoPrdSecondForTour(HashMap hashMap);
    List <CtgrAttrVO> getServiceKeyAttributeListByDispCtgNo(HashMap hashMap);


    void serviceDeletePrdAttrValueCompAll(HashMap hashMap);
    void serviceDeletePrdAttrValueComp(HashMap hashMap);
    void deletePrdAttrValue(HashMap hashMap);

    CtgrAttrValueVO getCommonAttributeInfoByNo(long prdAttrNo);

    CtgrAttrValueVO getCodeByAttributeAndValueNm3(HashMap hashMap);
    CtgrAttrValueVO getCodeByAttributeAndValueNm2(HashMap hashMap);

    String getAttributeAndValueCodeNewSeq();
    String getAttributeValueEtcNewSeq();

    void insertAttributeAndValueCode(PdPrdAttrValueCd pdPrdAttrValueCd);
    void insertAttributeValueEtc(PdCtgrAttrValueEtc pdCtgrAttrValueEtc);

    void insertAttributeValueCompTxt(PdPrdAttrCompTxt pdPrdAttrCompTxt);
    void insertAttributeValueComp(PdPrdAttrComp pdPrdAttrComp);

    void insertAttributeValueCompTxtHist (PdPrdAttrCompTxt pdPrdAttrCompTxt);
    void insertAttributeValueCompHist (PdPrdAttrComp pdPrdAttrComp);

    CtgrAttrVO getPrdBrandInfo(long prdNo);

    List<CtgrAttrValueVO> getAttributeValueByAttributeNoList(long prdAttrNo);




}
