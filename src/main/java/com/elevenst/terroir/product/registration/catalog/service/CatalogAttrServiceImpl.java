package com.elevenst.terroir.product.registration.catalog.service;

import com.elevenst.terroir.product.registration.catalog.exception.CatalogException;
import com.elevenst.terroir.product.registration.catalog.mapper.CatalogAttrMapper;
import com.elevenst.terroir.product.registration.catalog.vo.CatalogAttrVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Slf4j
@Service
public class CatalogAttrServiceImpl {

    @Autowired
    CatalogAttrMapper catalogAttrMapper;

    public List<CatalogAttrVO> getCtgrAttrInfo(long dispCtgrNo, long prdNo, long ctlgNo) throws CatalogException {
        List<CatalogAttrVO> ctgrAttrList = null;
        try {
            HashMap param1 = new HashMap();
            param1.put("dispCtgrNo", dispCtgrNo);
            param1.put("prdNo", prdNo);
            ctgrAttrList = catalogAttrMapper.getCtgrAttrList(param1);

            for (CatalogAttrVO ctlgAttr : ctgrAttrList) {
                if ("01".equals(ctlgAttr.getInputMthdCd())) { // 선택형만, PD162:01:선택형 (차후 다른 유형 추가, 제한 개수 변경 가능하여 뭐리에서 제한하지 않음 )
                    HashMap param = new HashMap();
                    param.put("prdNo", prdNo);
                    param.put("ctlgNo", ctlgNo);
                    param.put("ctlgAttrNo", ctlgAttr.getCtlgAttrNo());
                    param.put("ctlgAttrNM", ctlgAttr.getCtlgAttrNm());
                    param.put("dispCtgrNo" , dispCtgrNo);
                    ctlgAttr.setCatalogAttrValueVOList(catalogAttrMapper.getCtgrAttrValList(param));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new CatalogException("표준상품속성 조회 ", e);
        }
        return ctgrAttrList;

    }

    public List<HashMap> getProductCtlgAttrValList (long prdNo) {
        List<HashMap> productCtlgAttrValList= catalogAttrMapper.getProductCtlgAttrValList(prdNo);
        return productCtlgAttrValList;
    }

    public List<HashMap> getCtlgAttrValList (long ctlgNo){
        List<HashMap> ctgrAttrListValList  = catalogAttrMapper.getCtlgAttrValueList(ctlgNo);
        return ctgrAttrListValList;

    }



}
