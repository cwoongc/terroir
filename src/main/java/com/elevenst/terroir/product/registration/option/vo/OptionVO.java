package com.elevenst.terroir.product.registration.option.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class OptionVO implements Serializable {
    private static final long serialVersionUID = 73303660472581949L;

    private long stockNo;
    private long prdNo;
    private long optItemNo1;
    private long optValueNo1;
    private long optValueNm1;
    private long optItemNo2;
    private long optValueNo2;
    private long optValueNm2;
    private long optItemNo3;
    private long optValueNo3;
    private long optValueNm3;
    private long optItemNo4;
    private long optValueNo4;
    private long optValueNm4;
    private long optItemNo5;
    private long optValueNo5;
    private long optValueNm5;
    private long optItemNo6;
    private long optValueNo6;
    private long optValueNm6;
    private long optItemNo7;
    private long optValueNo7;
    private long optValueNm7;
    private long addPrc;
    private long stckQty;
    private String prdStckStatCd;
    private String useYn;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
    private long selQty;
    private String barCode;
    private long realStckQty;
    private long resvStckQty;
    private long tempStckQty;
    private long dfctStckQty;
    private long regRnk;
    private long optWght;
    private long cstmOptAplPrc;
    private long puchPrc;
    private double mrgnRt;
    private long mrgnAmt;
    private long ctlgNo;
    private String repOptYn;
    private String addDesc;
    private String spplBnftCd;
    private Date matchDt;
    private long matchNo;
    private String setTypCd;
    private long rntlCst;

    private long optNo;
    private String optNm;
    private String dtlOptNm;
    private long optItemNo;
    private long optValueNo;
    private String optItemNm;
    private String optValueNm;
    private String optClfCd;

    private String optNoArr;								// 옵션번호 Array
    private String optItemNoArr;
    private String optValueNoArr;

    private String mrgnPolicyCd;
    private String bsnDealClf;
    private long addedSelPrc;
    private long selMnbdNo;
    private String aprvStatCd;
    private long oldStockNo;

    private String optStr;
    private String martOptionYn;

    private boolean chgOptClfCd;
    private int cnt;
    private int optColCnt;
    private HashMap<String, ArrayList<String>> optValueMap	= new HashMap<String, ArrayList<String>>();
    private Map<String, PdOptItemVO> optItemMap = new HashMap<String, PdOptItemVO>();
    private Map<String, String> optItemImageMap		= new HashMap<String, String>();
    private Map<String, String> optItemDetailImageMap		= new HashMap<String, String>();
    private HashMap<String, Integer> stckIndexMap			= new HashMap<String, Integer>();
    private List<ProductStockVO> productStockVOList = new ArrayList<ProductStockVO>();

    // TODO CYB 추가 데이터 받을 내용인듯 추가되어야 할듯 yskim에게 확인 받을껏
    private ProductOptCalcVO productOptCalcVO;

    // TODO : 아래는 내부로직에 대한 기존 멤버변수 임....
    private String[] colTitle;
    private String[] colCount;
    private String[] oriColCount;
    private String[] colOptPrice;
    private String[] colPuchPrc;
    private String[] colMrgnRt;
    private String[] colMrgnAmt;
    private String[] colAprvStatCd;
    private String[] prdStckStatCds;
    private String[] colBarCode;
    private String[] colOptWght;
    private String[] colCstmAplPrc;
    private String[] colSellerStockCd;

    private String[] colValue1;
    private String[] colValue2;
    private String[] colValue3;
    private String[] colValue4;
    private String[] colValue5;

    private String[] groupNumber;
    private String[] summaryImage;
    private String[] summaryImageStr;
    private String[] detailImage;
    private String[] detailImageStr;
    private String[] colSetTypCd;
    private String[] colCompInfoJson;
    private String[] colCtlgNo;
    private String[] colAddDesc;
    private String[] colSpplBnftCd;

    private long selPrc;
    private String prdExposeClfCd;
    private String ctgrOptPrcVal;
    private String ctgrOptPrcPercent;

    private String optSelectYn;
    private String optionSaveYn;
    private String optTypCd;

    private String useBgnDy;
    private String useEndDy;


    ///////////////////////// ProductVO -> OptionVO 이동
    private String prdSellerStockCd;

    private boolean qCVerifiedStat;
    private String sellerStockCd;

    //option
    private long prdStckQty;
    private long prdSelQty;
    private boolean modifiedPrdSelQty;

    private boolean isExistOpt = false;

    private HashMap<String, String> tmpColMap = new HashMap<String, String>();
    private List productOptItemVOList = new ArrayList();

    //TODO : 추후 옵션 데이터가 어떻게 넘어오느냐에 따라 수정필요함 (OptionAbstract, OptionInput, OptionCombination...)
    private String optInputYn;
    private String[] colOptName;
    private String[] colOptStatCd;


    //TODO: 옵션처리를 위한 신규생성
    private List<OptionSelectVO> optionSelectList;
    private OptionWriteVO optionWriteVO;
    private OptionMixVO optionMixVO;

    private int optAddPrcLimit = 0;
}
