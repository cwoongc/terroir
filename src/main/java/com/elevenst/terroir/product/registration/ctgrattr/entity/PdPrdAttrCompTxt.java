package com.elevenst.terroir.product.registration.ctgrattr.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrdAttrCompTxt implements Serializable  {
    private static final long serialVersionUID = -3108952370658084888L;

    private long objPrdNo;

    private String prdAttrCd;

    private String objClfCd;

    private String prdAttrValueNm;

    private long createNo;

    private long updateNo;
/*
OBJ_PRD_NO	NUMBER	Not Null	��� �����ͻ�ǰ/�Ϲݻ�ǰ��ȣ/��ǰ�����ǰ
PRD_ATTR_CD	VARCHAR2(20)	Not Null	��ǰ�Ӽ��׸��ڵ�. PD_PRD_ATTR_VALUE_CD.ATTR_VALUE_CLF_CD=01 �� ��� PD_PRD_ATTR_VALUE_CD.PRD_ATTR_VALUE_CD ��
OBJ_CLF_CD	VARCHAR2(5)	Not Null	�����󱸺��ڵ�. �����ͻ�ǰ=02, �Ϲݻ�ǰ=01, ��ǰ�����ǰ=03
PRD_ATTR_VALUE_NM	VARCHAR2(2000)	��ǰ�Ӽ�����
CREATE_DT	DATE	������
CREATE_NO	NUMBER	������
UPDATE_DT	DATE	������
UPDATE_NO	NUMBER	������
 */
}
