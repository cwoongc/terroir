package com.elevenst.terroir.product.registration.customer.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Deprecated
public class PrivateMemberVO implements Serializable {
    private static final long serialVersionUID = -8951154110148785725L;


    private long memNO;
    private String natvYN;
    private String memNM;
    private String rsdntNOfrgnrRegNO;
    private String rsdntNOfrgnrRegNOByEncrypted;
    private String brth;
    private String brthDD;
    private String sex;
    private String adltCrtfYN;
    private String marrYN;
    private String skGrpComMemYN;
    private Date createDT;
    private int createNO;
    private Date updateDT;
    private int updateNO;
    private boolean sktMember;
    private boolean skcMember;
    private boolean skNonRegular;
    private String frgnclf;
    //상품배송유형
    private String prdDeliTypCd;
    //수입형태
    private String imptTypCd;

    //미성년개인셀러회원승인여부
    private String minorPrvtSellerApprYN;
    //미성년개인셀러승인일자
    private Date minorPrvtSellerApprDT;
    //미성년개인셀러승인자
    private long minorPrvtSellerApprNO;

    private String empNM ;

    private String acctMemIdntyNo ;

    //[개인정보 유효기간제] widetns 이정주 :isSeparated 추가
    private boolean isSeparated = false;  //유효기간제 개인정보 분리보관 여부
    //[개인정보 유효기간제] widetns 이정주 : isNotDisplay 추가
    private boolean isNotDisplay = false;  //유효기간제 데이터 비노출 여부

    private String innerVipYN;
    private Date innerVipYNUpdDT;
    private String innerVipListYN;//내부VIP리스트 조회 여부
    private String countryTlphnCd;
}
