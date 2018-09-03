package com.elevenst.common.util;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.elevenst.terroir.product.registration.product.code.ProductConstDef;


public class SellerProductDetailUtil {

    private static final long serialVersionUID = -4208293784677002746L;

    private static Log log = LogFactory.getLog(SellerProductDetailUtil.class);



    /**
     * 상품 상세 내용이 옵션 매칭 되는 태그로 구성 여부 조회
     * @param html
     * @return String
     */
    public static String getPrdDetailOptionMatching(String html, String prdDtlTypCd) {
        try {
            String optType = "";
            if(prdDtlTypCd != null && !"".equals(prdDtlTypCd)) {
                optType = prdDtlTypCd;
            } else {
                if( StringUtil.isEmpty(html) ) {
                    return null;
                }

                if( html.indexOf("optImg") == -1 ) {
                    return null;
                }

                int srcAttrNullCnt = 0;

                // 상품 상세 HTML 파싱
                org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(html);

                //1단 // 2단 영역 구분하여 확인
                //int representImageCnt = 0;
                int[] returnArrVal = new int[2];
                org.jsoup.select.Elements dan2Eles = doc.select("div.smt_optbody div.optImg_dan2 div.optImg");
                if( dan2Eles != null && dan2Eles.size() > 0 ) {
                    optType = ProductConstDef.PRD_DTL_TYP_CD_OPT_DAN2;
                    returnArrVal = taggingRepresentImgCnt(dan2Eles, srcAttrNullCnt);
                } else {
                    optType = ProductConstDef.PRD_DTL_TYP_CD_OPT;
                    // <div class="optImg"> 영역 추출
                    org.jsoup.select.Elements eles = doc.select("div.smt_optbody div.optImg");
                    returnArrVal = taggingRepresentImgCnt(eles, srcAttrNullCnt);
                }

                if(returnArrVal[0] == 0) return null;
                //representImageCnt = returnArrVal[0];

                // <div class="imageDetailView"> 영역 추출 (상세이미지 영역은 요청에 의해 체크하지 않음)
                //org.jsoup.select.Elements imageDetailViewEles = doc.select("div.smt_optdetail");
                //if( imageDetailViewEles == null || imageDetailViewEles.size() == 0 ) {
                //return null;
                //}

                //org.jsoup.select.Elements imageDetailViewImgEles = null;
                //org.jsoup.nodes.Element imageDetailViewItem = null;
                //int imageDetailViewListSize = imageDetailViewEles.size();
                //String imageDetailViewOptNm = null;
                //int detailImageCnt = 0;

                if(returnArrVal[1] > 0) {
                    return null;
                }

    			/*
    			for(int i = 0 ; i < imageDetailViewListSize ; i++) {
    				imageDetailViewItem = imageDetailViewEles.get(i);

    				// 상세이미지 개수
    				imageDetailViewImgEles = imageDetailViewItem.select("img");
    				imageDetailViewOptNm = imageDetailViewItem.attr("id");
    				if( StringUtil.isNotEmpty(imageDetailViewOptNm) && (imageDetailViewImgEles != null && imageDetailViewImgEles.size() > 0) ) {
    					detailImageCnt++;
    				}

    				if( StringUtil.isEmpty(imageDetailViewOptNm) || (imageDetailViewImgEles != null && imageDetailViewImgEles.size() > 0 && imageDetailViewImgEles.attr("src").length() <= 0) ) {
    					srcAttrNullCnt++;
    					break;
    				}
    			}
    			*/

                //if(srcAttrNullCnt > 0) {
                //return null;
                //}

                //if(representImageCnt == detailImageCnt) {
                //return optType;
                //}
            }


            return optType;

            //return null;
        } catch(Exception e) {
            if( log.isErrorEnabled() ) {
                log.error("상품 상세 내용이 옵션 매칭 되는 태그로 구성 여부 조회 오류 : " + e.getMessage(), e);
            }
            return null;
        }
    }


    public static int[] taggingRepresentImgCnt(org.jsoup.select.Elements eles, int srcAttrNullCnt) {
        int representImageCnt = 0;
        int[] returnArrVal = new int[2];
        returnArrVal[0] = 0;
        returnArrVal[1] = 0;
        if( eles == null || eles.size() == 0 ) {
            //return 0;
            return returnArrVal;
        }

        org.jsoup.select.Elements imgEles = null;
        org.jsoup.nodes.Element item = null;
        int listSize = eles.size();
        String optNm = null;

        for(int i = 0 ; i < listSize ; i++) {
            item = eles.get(i);

            // div 태그 안에 img 태그가 1개 이상 존재하고 ID 속성에 값 및 src값이 존재할 경우.
            imgEles = item.select("img");
            optNm = item.attr("id");
            if( StringUtil.isNotEmpty(optNm) && (imgEles != null && imgEles.size() > 0) ) {
                representImageCnt++;
            }
            if( StringUtil.isEmpty(optNm) || (imgEles != null && imgEles.size() > 0 && imgEles.attr("src").length() <= 0) ) {
                returnArrVal[1] = 1;
                break;
            }
        }
        returnArrVal[0] = representImageCnt;
        return returnArrVal;
    }

}
