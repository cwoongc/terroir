package com.elevenst.terroir.product.registration.ctgrattr.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdCtgrAttrValueEtc implements Serializable {

    private static final long serialVersionUID = -3168377547782824802L;

    private long attrValueEtcNo;

    private String attrValueEtcNm;

    private long dispCtgrNo;

    private String attrCd;

    private long sellerNo;

    private String sellerId;

    private String eMailSendYn;

    private String attrValueCd;

    private String statCd;

    private long createNo;

    private long updateNo;

/*
ATTR_VALUE_ETC_NO	NUMBER	Not Null	��Ÿ�Ӽ�����ȣ
ATTR_VALUE_ETC_NM	VARCHAR2(200)	Not Null	��Ÿ�Ӽ�����
DISP_CTGR_NO	NUMBER	Not Null	����ī�װ���ȣ
ATTR_CD	VARCHAR2(20)	Not Null	�Ӽ��ڵ�
SELLER_NO	NUMBER	������ȣ
SELLER_ID	VARCHAR2(60)	�������̵�
E_MAIL_SEND_YN	VARCHAR2(1)	'N'	�̸������ۿ���
ATTR_VALUE_CD	VARCHAR2(20)	�Ӽ����� ��ϵȰ�� �ش� �Ӽ����ڵ�(��Ÿ�Ӽ����� ��ϵ����������� NULL)
STAT_CD	VARCHAR2(5)	'01'	��Ÿ�Ӽ��������ڵ�
CREATE_DT	DATE	����Ͻ�
CREATE_NO	NUMBER	����ڹ�ȣ
UPDATE_DT	DATE	���������Ͻ�
UPDATE_NO	NUMBER	���������ڹ�ȣ
     */
}
