package com.elevenst.terroir.product.registration.ctgrattr.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrdAttrValueCd  implements Serializable {

    private static final long serialVersionUID = 3990428799765919472L;
    /**
     * ��ǰ�Ӽ�/�Ӽ����ڵ� PRD_ATTR_VALUE_CD
     */
    private String prdAttrValueCd;

    /**
     * ��ǰ�Ӽ�/�Ӽ����� PRD_ATTR_VALUE_NM
     */
    private String prdAttrValueNm;

    /**
     * ��ǰ�Ӽ�/�Ӽ��������ڵ� ATTR_VALUE_CLF_CD
     */
    private String attrValueClfCd;

    /**
     * �Ӽ�/�Ӽ��� ��� ��ó �ڵ� [����(01), ���(02), �Ǹ���(03)] PRD_ATTR_ATTR_REG_SRC_CD
     */
    private String prdAttrAttrRegSrcCd;

    /**
     * ��뿩��
     */
    private String useYn;

    /**
     * ���ؼӼ�,�Ӽ��� ����
     */
    private String CmCdYn;

    /**
     * ���ؼӼ� ��ȯ�� ATTR_VALUE_CD_TRNVR
     */
    private String attrValueCdTrnvr;

    /**
     * ���ؼӼ� ��ȯ�� ATTR_VALUE_CD_TRNVR_DT
     */
    private String attrValueCdTrnvrDt;

    /**
     *  �̹��� URL IMG_URL
     */
    private String imgUrl;

    /**
     * ��ǥ ���� ��ǰ�Ӽ����� SYN_ATTR_VALUE_NM
     */
    private String synAttrValueNm;

    /**
     * �Ӽ��� �߰�����
     */
    private String prdAttrValueDesc;

    /**
     * ��ǰ�Ӽ� Ÿ�� (NULL, 01) ���ؼӼ�, (02) ��ǰ������� �����Է� �Ӽ�  ATTR_TYPE
     */
    private String AttrType;

    /**
     * �Ӽ� ���� ATTR_DESC
     */
    private String attrDesc;

    /**
     * ��ǥ�Ӽ����� REP_ATTR_VALUE_YN
     */
    private String repAttrValueYn;

    /**
     * ��ǰ�Ӽ�/�Ӽ��� ������ PRD_ATTR_VALUE_ENG_NM
     */
    private String prdAttrValueEngNm;

    private long createNo;

    private long updateNo;



}
