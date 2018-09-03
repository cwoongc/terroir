package com.elevenst.terroir.product.registration.image.vo;

import com.elevenst.terroir.product.registration.image.code.ImageKind;
import com.elevenst.terroir.product.registration.image.entity.PdPrdImage;
import com.elevenst.terroir.product.registration.image.entity.PdPrdImageChgHist;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

@Slf4j
@Data
public class ProductImageVO {

    private PdPrdImage pdPrdImage;

    private long prdNo;
    private long reRegPrdNo;        // 재등록대상 상품번호
    private int uploadType;         // 업로드 타입 INSERT(0):신규등록, COPY(1):복사, UPDATE(2):수정

    private List<ImageFile> imgFiles;

	private String createCd;
    private long eventNo;
    private String prdTypCd;
    private boolean isDeal;
    private boolean isGifItm;
    private String imgScoreEftvYn;
    private boolean isImageHistoryInsert;
    private long selMnbdNo;


    @Data
    public static class ImageFile {
        private File file;
        private ImageKind imageKind;
        private String tempPath;
        //private boolean isDelete;

        public ImageFile setFile(File file){
            this.file = file;
            return this;
        }

        public ImageFile setImageKind(ImageKind imageKind){
            this.imageKind = imageKind;
            return this;
        }

        public ImageFile setTempPath(String tempPath){
            this.tempPath = tempPath;
            return this;
        }

        /*
        public ImageFile setDelete(boolean isDelete){
            this.isDelete = isDelete;
            return this;
        }
        */

    }


    /*private long prdNo;           //상품번호번호
    private String urlPath;       //이미지URL경로
    private String basicExtNm;    //목록300이미지 확장명
    private String add1ExtNm;     //추가이미지1 확장명
    private String add2ExtNm;     //추가이미지2 확장명
    private String add3ExtNm;     //추가이미지3 확장명
    private String l170ExtNm;     //목록170이미지 확장명
    private String l80ExtNm;      //목록80이미지 확장명
    private String pngExtNm;      //PNG이미지 확장명
    private String orgExtNm;      //기본이미지의 원본이미지 확장명
    private String cardVwImgUrl;  //쇼킹딜 카드 이미지
    private long cutCnt;          //GIF 스틸컷 수
    private String createDt;      //등록일시
    private String updateDt;      //수정일시
    private String pngImgUrl;     //PNG파일로 변환하기 위해서 PD_PRD_IMG 테이블에 INSERT하기 위한 URL
    private String styleExtNm;	  //스타일스케치이미지 확장명
    private String wirelssDtlExtNm;  //모바일 상세설명 파일명
    private long verNo = 0;		  // 이미지 버전

    private long reRegPrdNo;        //재등록대상 상품번호

    private String basicZoomInImgYN; //기본확대이미지 등록여부
    private String add1ZoomInImgYN; //추가1확대이미지 등록여부
    private String add2ZoomInImgYN; //추가2확대이미지 등록여부
    private String add3ZoomInImgYN; //추가3확대이미지 등록여부

    private String dtlBasicExtNm;	//	상품기본이미지

    private String moblBasicExtNm;	//	모바일전용이미지
    private String moblAdd1ExtNm;	//	모바일전용추가1이미지
    private String moblAdd2ExtNm;	//	상품기본추가2이미지
    private String moblAdd3ExtNm;	//	상품기본추가3이미지
    private String moblAdd4ExtNm;	//	상품기본추가4이미지
    private String ptnrExusImgUrl;  //  제휴사전용이미지URL
    private String imgIsptCateYn;	//	카테고리포함여부
    private String minorSelCnYn;	//	미성년자여부

    private String moblBasicOrgImageUrl;	//	모바일전용이미지_TEMP경로
    private String moblAdd1OrgImageUrl;		//	모바일전용추가1이미지_TEMP경로
    private String moblAdd2OrgImageUrl;		//	상품기본추가2이미지_TEMP경로
    private String moblAdd3OrgImageUrl;		//	상품기본추가3이미지_TEMP경로
    private String moblAdd4OrgImageUrl;		//	상품기본추가4이미지_TEMP경로
    private String ptnrExusImageUrl;		//	제휴사전문관 이미지_TEMP경로

    private String moblBasicFileName;		//	모바일전용이미지명
    private String moblAdd1FileName;		//	모바일전용추가1이미지명
    private String moblAdd2FileName;		//	상품기본추가2이미지명
    private String moblAdd3FileName;		//	상품기본추가3이미지명
    private String moblAdd4FileName;		//	상품기본추가4이미지명
    private String ptnrExusFileName;		//	제휴사전문관 이미지명
    private String isptImageUrl;			//	검수이미지 업로드경로
    private String wirelssPrdNm;			//	모바일 갤러리 상품명
    private String bsnDealClf;				//	거래유형코드

    private String dgstExtNm;               //  옵션이미지

    private String imgScoreEftvYn;   // 이미지점수유효여부
    private double baseImgScore;
    private double dtlBaseImgScore;
    private long baseImgWdthVal;
    private long baseImgVrtclVal;
    private long dtlBaseImgWdthVal;
    private long dtlBaseImgVrtclVal;*/

    private String baseImgChgYn = "N";      //  목록이미지 변경여부(BASIC_EXT_NM)
    private String dtlBaseImgChgYn = "N";   //  상품상세대표이미지 변경여부(DTL_BASIC_EXT_NM)
    private String add1ImgChgYn = "N";       //  추가1이미지 변경여부(ADD1_EXT_NM)
    private String add2ImgChgYn = "N";       //  추가2이미지 변경여부(ADD2_EXT_NM)
    private String add3ImgChgYn = "N";       //  추가3이미지 변경여부(ADD3_EXT_NM)

    /*public ProductImageVO(){
        baseImgChgYn = "N";
        dtlBaseImgChgYn = "N";
        add1ImgChgYn = "N";
        add2ImgChgYn = "N";
        add3ImgChgYn = "N";
    }*/

    public PdPrdImageChgHist getPdPrdImageChgHist(){
        PdPrdImageChgHist pdPrdImageChgHist = new PdPrdImageChgHist();
        pdPrdImageChgHist.setPrdNo(pdPrdImage.getPrdNo());
        pdPrdImageChgHist.setBaseImgChgYn(baseImgChgYn);
        pdPrdImageChgHist.setDtlBaseImgChgYn(dtlBaseImgChgYn);
        pdPrdImageChgHist.setAdd1ImgChgYn(add1ImgChgYn);
        pdPrdImageChgHist.setAdd2ImgChgYn(add2ImgChgYn);
        pdPrdImageChgHist.setAdd3ImgChgYn(add3ImgChgYn);
        return pdPrdImageChgHist;
    }

//    public String getFilePath() {
//        if(imageKind == ImageKind.B) {
//            return pdPrdImage.getBasicExtNm();
//        } else if(imageKind == ImageKind.A1) {
//            return pdPrdImage.getAdd1ExtNm();
//        } else if(imageKind == ImageKind.A2) {
//            return pdPrdImage.getAdd2ExtNm();
//        } else if(imageKind == ImageKind.A3) {
//            return pdPrdImage.getAdd3ExtNm();
//        } else if(imageKind == ImageKind.L80) {
//            return pdPrdImage.getL80ExtNm();
//        } else if(imageKind == ImageKind.WD) {
//            return pdPrdImage.getMoblBasicExtNm();
//        }
//        return "";
//    }
}