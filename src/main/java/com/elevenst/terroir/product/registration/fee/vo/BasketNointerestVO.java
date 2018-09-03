package com.elevenst.terroir.product.registration.fee.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BasketNointerestVO implements Serializable {
    private static final long serialVersionUID = 4455898029575365019L;

    private String intFreeSEQ;			//무이자할부 시퀀스
    private String crdenCD ;			//카드서번호
    private String crdenNM ;			//카드사이름
    private String crdenEngNM ;		//카드사영문이름
    private String crdenStlMtdCD;		//카드결제방법코드
    private String useYN;				//사용여부
    private String sellerUseYN;			//판매자사용여부
    private String intFreeStrtDT;		//무이자할부시작일
    private String intFreeEndDT;		//무이자할부종료일
    private String spcCnrtCLF;			//특약구분
    private String intFreeTypCD;		//무이자할부유형코드
    private String intFreeMonCD;		//무이자할부개월코드
    private String intFreeAplTermAMT;	//무이자할부적영대상조건금액
    private float intFreeDfrmRT;		//무이자할부부담비율
    private String intFreeCrdenDfrmRT;	//무이자할부카드사부담비율
    private String intFreeTmallDfrmRT;	//무이자할부tMall부담비율
    private String createDT;			//등록일시
    private String createNO;			//등록자번호
    private String updateDT;			//수정일시
    private String updateNO;			//수정자번호
    private String createNM;			//작성자이름
    private String updateNM;			//수정자이름
    private String createID;			//작성자ID
    private String updateID;			//수정자ID
    private String eftvStrtDT;
    private String eftvEndDT;
    private String crdenBaseFeeRT;
    private String[] freeCrdenDfrmRtData;
    private String updateYN;

    private String endDate;				//검색시작일
    private String bgnDate;				//검색마지막일
    private String nowDate;				//현재날짜
    private String searchState;			//상태
    private String searchCard;			//카드사
    private String searchType;			//부담주체
    private String searchDate;			//검색년월
    private String eventYN;             //검색여부

    private String flagSch;
    private String flagIng;
    private String flagEnd;

    private int pageNum;			//현제 페이지 번호
    private int startRowNum;		//페이지시작번호
    private int endRowNum;			//페이지끝번호
    private int nextPageNum;		//다음페이지번호
    private int prevPageNum;		//이전페이지번호
    private int totalPageNum;	    //마지막페이지번호
    private int numberPerPage=20;		//페이지 수
    private int totalCount;			//토탈카운트
    private String typ;             //무이자,부분무이자 여부
    private String coment;          //부분무이자시 나오는 설명
    private String directVisitYn;
    private String eventComment; //이벤트 쿠폰 혜택에 나오는 안내

    public void setPageInfo() {

        int pageNum = getPageNum();			//현제페이지번호
        int totalCnt = getTotalCount();		//총카운트
        int lowCnt = getNumberPerPage();	//페이지당 줄 수
        int totalPageNum = 1;				//총 페이지 수
        int nextPageNum = 1;				//다음페이지번호
        int prevPageNum = 1;				//이전페이지번호
        int startRowNum = 1;				//페이지 줄 시작번호
        int endRowNum = 1;					//페이지 줄 끝번호


        if(lowCnt==0){
            lowCnt=20;
        }
        if(totalCnt>lowCnt){ //총카운트가 로우수보다 많을 경우 전체 페이지 수 증가
            if(totalCnt%lowCnt>0){
                totalPageNum = (totalCnt/lowCnt)+1;
            }else{
                totalPageNum = (totalCnt/lowCnt);
            }
        }

        if(pageNum==0 || pageNum > totalPageNum ){	//페이지값이 없을경우 또는 페이지당 줄 수 변경으로 페이지값이 커졌을경우 1페이지로 설정
            pageNum=1;
        }



        if(totalPageNum>1 && totalPageNum>pageNum){	//다음페이지 설정.
            nextPageNum = pageNum+1;
        }else if(totalPageNum == pageNum){
            nextPageNum = pageNum;
        }

        if(totalPageNum > 1 && pageNum > 1){			//이전페이지 설정
            prevPageNum = pageNum-1;
        }

        if(totalCnt > lowCnt){
            startRowNum = ((pageNum-1) * lowCnt )+1;
        }

        if(totalPageNum == pageNum){
            endRowNum = totalCnt+1;
        }else if(totalPageNum>pageNum){
            endRowNum = ((pageNum)*lowCnt)+1;
        }

        setPageNum(pageNum);
        setTotalPageNum(totalPageNum);
        setNextPageNum(nextPageNum);
        setPrevPageNum(prevPageNum);
        setStartRowNum(startRowNum);
        setEndRowNum(endRowNum);
        setNumberPerPage(lowCnt);
    }
}
