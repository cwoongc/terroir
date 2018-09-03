package com.elevenst.terroir.product.registration.option;

import com.elevenst.common.properties.PropMgr;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.category.validate.CategoryValidate;
import com.elevenst.terroir.product.registration.customer.service.CustomerServiceImpl;
import com.elevenst.terroir.product.registration.deal.service.DealServiceImpl;
import com.elevenst.terroir.product.registration.deal.validate.DealValidate;
import com.elevenst.terroir.product.registration.deal.vo.ProductEvtVO;
import com.elevenst.terroir.product.registration.option.exception.OptionException;
import com.elevenst.terroir.product.registration.option.message.OptionServiceExceptionMessage;
import com.elevenst.terroir.product.registration.option.service.OptionServiceImpl;
import com.elevenst.terroir.product.registration.option.vo.PdOptItemVO;
import com.elevenst.terroir.product.registration.option.vo.PdOptValueVO;
import com.elevenst.terroir.product.registration.option.vo.ProductStockVO;
import com.elevenst.terroir.product.registration.price.service.PriceServiceImpl;
import com.elevenst.terroir.product.registration.price.vo.PriceVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.option.code.OptionConstDef;
import com.elevenst.terroir.product.registration.option.validate.OptionValidate;
import com.elevenst.terroir.product.registration.product.service.ProductServiceImpl;
import com.elevenst.terroir.product.registration.product.validate.ProductValidate;
import com.elevenst.terroir.product.registration.option.vo.OptionVO;
import com.elevenst.terroir.product.registration.product.vo.BaseVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.service.SellerServiceImpl;
import com.elevenst.terroir.product.registration.seller.validate.SellerValidate;
import com.elevenst.terroir.product.registration.seller.vo.SellerAuthVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public abstract class AbstractOption implements OptionConstDef {

    @Autowired
    ProductServiceImpl productServiceImpl;

    @Autowired
    CustomerServiceImpl customerServiceImpl;

    @Autowired
    PriceServiceImpl priceServiceImpl;

    @Autowired
    SellerServiceImpl sellerServiceImpl;

    @Autowired
    OptionServiceImpl optionServiceImpl;

    @Autowired
    DealServiceImpl dealServiceImpl;

    @Autowired
    CombinationOption optionCombination;

    @Autowired
    InputOption optionInput;

    @Autowired
    PropMgr propMgr;

    @Autowired
    ProductValidate productValidate;

    @Autowired
    OptionValidate optionValidate;

    @Autowired
    CategoryValidate categoryValidate;

    @Autowired
    DealValidate dealValidate;

    @Autowired
    SellerValidate sellerValidate;

    private Log log = LogFactory.getLog(AbstractOption.class);
    // 상품 옵션 처리 Method
    public abstract Map<String, Object> processProductOption() throws OptionException;

    protected String productVaildation() {
        String msg = "";

        return msg;
    }

    // 옵션정보 수집 용 Array 생성
    public static String[] getArray4Option(String[] _data) {
        String[] rtnData = _data;
        if (rtnData != null && rtnData.length == 1) {
            if (StringUtil.isNotEmpty(rtnData[0])) {
                rtnData = StringUtil.split(rtnData[0], OPTION_DELIMITER);
            } else {
                rtnData = new String[0];
            }
        } else if (rtnData == null) {
            rtnData = new String[0];
        }
        return rtnData;
    }

    // 옵션정보 수집 용 Array 생성
    public static String[] getArray4OptionAddNullValue(String[] _data) {
        String[] rtnData = new String[0];
        if(_data != null && _data.length == 1) {
            _data[0] = _data[0].replaceAll("undefined", "");
            rtnData = _data;

            if (StringUtil.isNotEmpty(rtnData[0])) {
                rtnData = rtnData[0].split(OPTION_DELIMITER, StringUtil.countMatches(rtnData[0], OPTION_DELIMITER)+1);
            } else {
                rtnData = new String[0];
            }
        }
        return rtnData;
    }

    /**
     * ProductOptItemBO 생성 method
     * @param itmNo, itmNm, optClfCd, statCd, userNo, prdExposeClfCd
     * @return ProductOptItemBO
     */
    public PdOptItemVO setPdOptItemVO(long itmNo, String itmNm, String optClfCd, String statCd, long userNo, String prdExposeClfCd) throws OptionException {
        PdOptItemVO bo = new PdOptItemVO();
        bo.setOptItemNo		(itmNo		);		// 옵션명(아이템) 등록 순번
        bo.setStockClmnPos	(itmNo		);		// 옵션명(아이템) pd_stock 테이블 컬럼 위치
        bo.setOptItemNm		(itmNm		);		// 옵션(아이템) 명
        bo.setOptClfCd		(optClfCd	);		// 옵션 타입
        bo.setStatCd		(statCd		);		// 사용 여부
        bo.setPrdExposeClfCd(prdExposeClfCd);	// 옵션노출방식
        bo.setCreateNo		(userNo		);		// 생성자
        bo.setUpdateNo		(userNo		);		// 수정자
        return bo;
    }

    /**
     * ProductOptValueBO 생성 method
     * @param pdOptItemVO, optValue, optStatCd, userNo
     * @return ProductOptValueBO
     */
    protected PdOptValueVO setPdOptValueVO(PdOptItemVO pdOptItemVO, String optValue, String optStatCd, long userNo) throws OptionException {
        PdOptValueVO bo = new PdOptValueVO();
        bo.setOptItemNo		(pdOptItemVO.getOptItemNo()						);	// 옵션명(아이템) 등록 순번
        bo.setOptValueNo	(pdOptItemVO.getPdOptValueVOList().size() + 1);	// 옵션값 등록 순번 (옵션 value BO 리스트 사이즈 + 1)
        bo.setRegRnk		(pdOptItemVO.getPdOptValueVOList().size() + 1);	// 옵션값 등록 순번 (옵션 value BO 리스트 사이즈 + 1)
        bo.setOptValueNm	(optValue										);	// 옵션값
        bo.setPrdStckStatCd	(optStatCd										);	// 옵션 재고상태
        bo.setCreateNo		(userNo											);	// 생성자
        bo.setUpdateNo		(userNo											);	// 수정자
        return bo;
    }

    /**
     * ProductStockBO 에 옵션 아이템 순번, 옵션값 순번, 옵션값을 설정
     * @param productStockVO	(재고BO)
     * @param obj	(parameter)
     * @param n		(setter method N countter)
     * @throws OptionException
     */
    public static void setPrdStckBOMethodInvoke(ProductStockVO productStockVO, Object[] obj, int n) throws OptionException {
        try
        {
            Method method = null;
            method = productStockVO.getClass().getMethod("setOptItemNo"+(n), new Class[]{long.class});
            method.invoke(productStockVO, obj[0]);

            method = productStockVO.getClass().getMethod("setOptValueNo"+(n), new Class[]{long.class});
            method.invoke(productStockVO, obj[1]);

            method = productStockVO.getClass().getMethod("setOptValueNm"+(n), new Class[]{String.class});
            method.invoke(productStockVO, obj[2]);
        } catch (SecurityException e) {
            throw new OptionException(e.getCause());
        } catch (NoSuchMethodException e) {
            throw new OptionException(e.getCause());
        } catch (IllegalArgumentException e) {
            throw new OptionException(e.getCause());
        } catch (IllegalAccessException e){
            throw new OptionException(e.getCause());
        } catch (InvocationTargetException e){
            throw new OptionException(e.getCause());
        }
    }





    /**
     * 옵션별 매입가 및 마진율 체크
     * @param productStockVO
     * @return
     * @throws OptionException
     */
    public String checkMsgPurchaseAndMargin(ProductStockVO productStockVO) throws OptionException {
        String msg = "";
        try{
            ProductVO productVO = new ProductVO();
            productVO.setBaseVO(new BaseVO());
            productVO.setPriceVO(new PriceVO());
            productVO.setOptionVO(new OptionVO());
            productVO.getOptionVO().setMrgnPolicyCd(productStockVO.getMrgnPolicyCd());
            productVO.getBaseVO().setBsnDealClf(productStockVO.getBsnDealClf());
            productVO.getPriceVO().setSelPrc(productStockVO.getAddedSelPrc());
            productVO.setSelMnbdNo(productStockVO.getSelMnbdNo());
            productVO.getOptionVO().setPuchPrc(productStockVO.getPuchPrc());
            productVO.getOptionVO().setMrgnRt(productStockVO.getMrgnRt());
            productVO.getOptionVO().setMrgnAmt(productStockVO.getMrgnAmt());
            productVO.getOptionVO().setAprvStatCd(productStockVO.getAprvStatCd());

            msg = productValidate.checkMsgPuchPrc(productVO);

            productStockVO.setPuchPrc(productVO.getOptionVO().getPuchPrc());
            productStockVO.setMrgnRt(productVO.getOptionVO().getMrgnRt());
            productStockVO.setMrgnAmt(productVO.getOptionVO().getMrgnAmt());
        }catch (Exception e) {
            throw new OptionException("옵션 매입가 및 마진율 체크 오류", e);
        }

        return msg;
    }

    /**
     * 옵션상세
     * <option ~ 의 형태로 반환
     * 2009.09.15 조재후 수정
     * 옵션테이블구조 변경으로 수정
     * @param sourceOptionList
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List getOptionDtlForOrder(List sourceOptionList) {
        List allOptionDtl = new ArrayList();
        List<OptionVO> selOptionDtl = new ArrayList<OptionVO>();
        List<OptionVO> insOptionDtl = new ArrayList<OptionVO>();

        OptionVO option = null;
        String optionNoString = "";
        String optionItemNoString = "";
        String optionValueNoString = "";
        String optionNmString = "";
        String optionDtlString = "";
        OptionVO selOption = new OptionVO();
        OptionVO insOption = new OptionVO();
        String optNoString = "";
        String optItemNoString = "";
        String optValueNoString = "";
        String optionClfString = "";

        for(int i=0;i<sourceOptionList.size();i++) {
            option = (OptionVO)sourceOptionList.get(i);
            optNoString = String.valueOf(option.getOptItemNo()+""+option.getOptValueNo());
            optItemNoString = String.valueOf(option.getOptItemNo());
            optValueNoString = String.valueOf(option.getOptValueNo());

            if (!"03".equals(option.getOptClfCd())) {
                if(optionNoString.equals("")) {
                    optionNoString = optNoString +"";
                    optionItemNoString = optItemNoString +"";
                    optionValueNoString = optValueNoString +"";
                    optionDtlString = option.getOptValueNm();
                    optionClfString = option.getOptClfCd();
                } else {
                    optionNoString = optionNoString + "∏" + optNoString +"";
                    optionItemNoString = optionItemNoString + "∏" + optItemNoString +"";
                    optionValueNoString = optionValueNoString + "∏" + optValueNoString +"";
                    optionDtlString = optionDtlString + "∏" + option.getOptValueNm();
                }

                if ( (i == (sourceOptionList.size()-1))
                    || (option.getOptItemNo() != ((OptionVO)sourceOptionList.get(i+1)).getOptItemNo()) )
                {	// 해당 옵션의 마지막이면 세팅
                    //if(option.getRn()==option.getCnt()){
                    optionNmString = option.getOptItemNm();
                    selOption.setOptNoArr(optionNoString);
                    selOption.setOptItemNoArr(optionItemNoString);
                    selOption.setOptValueNoArr(optionValueNoString);
                    selOption.setOptNm(optionNmString);
                    selOption.setDtlOptNm(optionDtlString);
                    selOptionDtl.add(selOption);
                    optionNoString = "";
                    optionItemNoString = "";
                    optionValueNoString = "";
                    optionNmString = "";
                    optionDtlString = "";
                    selOption = new OptionVO();
                }
            } else {
                insOption.setOptNo(StringUtil.parseLong(optNoString));
                insOption.setOptItemNo(StringUtil.parseLong(optItemNoString));
                insOption.setOptValueNo(StringUtil.parseLong(optValueNoString));
                insOption.setOptNm(option.getOptItemNm());
                insOptionDtl.add(insOption);
                insOption = new OptionVO();
            }
        }
        allOptionDtl.add(selOptionDtl);
        allOptionDtl.add(insOptionDtl);
        allOptionDtl.add(optionClfString); //3번째 인자는 조합형인지 독립형인지 구분하는 인자(01:조합형 / 02:독립형)
        return allOptionDtl;
    }
}
