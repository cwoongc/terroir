package com.elevenst.terroir.product.registration.option;

import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.option.code.OptionConstDef;
import com.elevenst.terroir.product.registration.option.entity.PdOptDtlImage;
import com.elevenst.terroir.product.registration.option.exception.OptionException;
import com.elevenst.terroir.product.registration.option.message.OptionServiceExceptionMessage;
import com.elevenst.terroir.product.registration.option.validate.OptionValidate;
import com.elevenst.terroir.product.registration.option.vo.OptionMixVO;
import com.elevenst.terroir.product.registration.option.vo.OptionWriteVO;
import com.elevenst.terroir.product.registration.option.vo.PdOptItemVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class InputOption extends AbstractOption {

    @Autowired
    OptionValidate optionValidate;

    @Override
    public Map<String, Object> processProductOption() throws OptionException {
        return null;
    }

    //public Map<String, Object> processProductOption(HashMap<String, Object> optMap, ProductVO productVO) throws OptionException {
    @Deprecated
    public void processProductOption(ProductVO productVO) throws OptionException {
        long userNo = productVO.getSelMnbdNo();
        //String[] colOptName		= AbstractOption.getArray4Option(dataBox.getValues("colOptName"));	// 작성형 옵션명
        //String[] colOptStatCd	= AbstractOption.getArray4Option(dataBox.getValues("colOptStatCd"));	// 작성형 옵션사용여부
        //TODO: data가 어떻게 넘어오느냐에 따라 반드시 다시 확인해야 함
        String[] colOptName		= AbstractOption.getArray4Option(productVO.getOptionVO().getColOptName());	// 작성형 옵션명
        String[] colOptStatCd	= AbstractOption.getArray4Option(productVO.getOptionVO().getColOptStatCd());	// 작성형 옵션사용여부
        //Map<String, Object> optionMap = (optMap == null) ? new HashMap<String, Object>() : optMap;
        //Map<String, PdOptItemVO> optionMap = (productVO.getOptionVO().getOptItemMap() == null) ? new HashMap<String, PdOptItemVO>() : productVO.getOptionVO().getOptItemMap();
        Map<String, PdOptItemVO> optItemMap = new LinkedHashMap<String, PdOptItemVO>();

        /*
        if (optionMap.get("optItemMap") != null) {
            optItemMap = (LinkedHashMap<String, PdOptItemVO>) optionMap.get("optItemMap");
        }
        */
        if (productVO.getOptionVO().getOptItemMap() != null) {
            //optItemMap = (LinkedHashMap<String, PdOptItemVO>) optionMap.get("optItemMap");
            optItemMap = productVO.getOptionVO().getOptItemMap();
        }

        if (colOptName.length != colOptStatCd.length) throw new OptionException(OptionServiceExceptionMessage.NOT_EQUALS_WRITE_OPTION_CNT_ERROR);
        else if (colOptName.length > 10) throw new OptionException(OptionServiceExceptionMessage.OVER_WRITE_OPTION_CNT_TEN_ERROR);

        String optNm, optStatCd;
        String optCheckMsg;
        PdOptItemVO pdOptItemVO;

        for (int i=OptionConstDef.INT_ZERO; i<colOptName.length; i++) {

            optNm = colOptName[i].trim();
            optStatCd = (OptionConstDef.USE_Y.equals(colOptStatCd[i])) ? OptionConstDef.TYPE_01 : (OptionConstDef.USE_N.equals(colOptStatCd[i])) ? OptionConstDef.TYPE_02 : colOptStatCd[i];
            optCheckMsg = optionValidate.checkMsgOptNmVal(OptionConstDef.USE_W, optNm, userNo);
            if (StringUtil.isNotEmpty(optCheckMsg)) throw new OptionException((i+1) + "번째 작성형 옵션명을 확인 하십시오.\n" + optCheckMsg);

            // 옵션항목구분코드:옵션명 ex) 03:색상
            String optItemKey = OptionConstDef.OPT_CLF_CD_CUST  + OptionConstDef.OPTION_ITEM_VALUE_DELIMITER + optNm;
            pdOptItemVO = optItemMap.get(optItemKey);

            if (pdOptItemVO == null) {
                pdOptItemVO = setPdOptItemVO(optItemMap.size()+1, optNm, OptionConstDef.OPT_CLF_CD_CUST, optStatCd, userNo, OptionConstDef.NULL_STRING);
                optItemMap.put(optItemKey, pdOptItemVO);
            } else {
                throw new OptionException("중복된 작성형 옵션명[" + optNm + "]이 존재합니다.");
            }
        }
        //optionMap.put("optItemMap", optItemMap);
        productVO.getOptionVO().setOptItemMap(optItemMap);      // 옵션명 (아이템) 맵(옵션명, 옵션 아이템 BO)
        //return optionMap;
    }






    public void processProductOptionNew(ProductVO productVO) throws OptionException {
        long userNo = productVO.getSelMnbdNo();
        //TODO: data가 어떻게 넘어오느냐에 따라 반드시 다시 확인해야 함
        //String[] colOptName		= AbstractOption.getArray4Option(productVO.getOptionVO().getColOptName());	// 작성형 옵션명
        //String[] colOptStatCd	= AbstractOption.getArray4Option(productVO.getOptionVO().getColOptStatCd());	// 작성형 옵션사용여부
        //String[] colOptName		= AbstractOption.getArray4Option(productVO.getOptionVO().getColOptName());	// 작성형 옵션명
        //String[] colOptStatCd	= AbstractOption.getArray4Option(productVO.getOptionVO().getColOptStatCd());	// 작성형 옵션사용여부
        OptionWriteVO optionWriteVO = productVO.getOptionVO().getOptionWriteVO();
        Map<String, PdOptItemVO> optItemMap = new LinkedHashMap<String, PdOptItemVO>();

        if (productVO.getOptionVO().getOptItemMap() != null) {
            optItemMap = productVO.getOptionVO().getOptItemMap();
        }

        if (optionWriteVO.getColOptName().size() != optionWriteVO.getColOptStatCd().size()) throw new OptionException(OptionServiceExceptionMessage.NOT_EQUALS_WRITE_OPTION_CNT_ERROR);
        else if (optionWriteVO.getColOptName().size() > 10) throw new OptionException(OptionServiceExceptionMessage.OVER_WRITE_OPTION_CNT_TEN_ERROR);

        String optNm, optStatCd;
        String optCheckMsg;
        PdOptItemVO pdOptItemVO;

        for (int i=OptionConstDef.INT_ZERO; i<optionWriteVO.getColOptName().size(); i++) {

            //optNm = colOptName[i].trim();
            optNm = optionWriteVO.getColOptName().get(i).trim();
            optStatCd = (OptionConstDef.USE_Y.equals(optionWriteVO.getColOptStatCd().get(i))) ? OptionConstDef.TYPE_01 : (OptionConstDef.USE_N.equals(optionWriteVO.getColOptStatCd().get(i))) ? OptionConstDef.TYPE_02 : optionWriteVO.getColOptStatCd().get(i);
            optCheckMsg = optionValidate.checkMsgOptNmVal(OptionConstDef.USE_W, optNm, userNo);
            if (StringUtil.isNotEmpty(optCheckMsg)) throw new OptionException((i+1) + "번째 작성형 옵션명을 확인 하십시오.\n" + optCheckMsg);

            // 옵션항목구분코드:옵션명 ex) 03:색상
            String optItemKey = OptionConstDef.OPT_CLF_CD_CUST  + OptionConstDef.OPTION_ITEM_VALUE_DELIMITER + optNm;
            pdOptItemVO = optItemMap.get(optItemKey);

            if (pdOptItemVO == null) {
                pdOptItemVO = setPdOptItemVO(optItemMap.size()+1, optNm, OptionConstDef.OPT_CLF_CD_CUST, optStatCd, userNo, OptionConstDef.NULL_STRING);
                optItemMap.put(optItemKey, pdOptItemVO);
            } else {
                throw new OptionException("중복된 작성형 옵션명[" + optNm + "]이 존재합니다.");
            }
        }
        productVO.getOptionVO().setOptItemMap(optItemMap);      // 옵션명 (아이템) 맵(옵션명, 옵션 아이템 BO)
    }
}
