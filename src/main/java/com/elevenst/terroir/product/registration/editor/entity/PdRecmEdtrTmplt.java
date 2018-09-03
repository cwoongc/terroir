package com.elevenst.terroir.product.registration.editor.entity;

import lombok.Data;

/**
 * PD_RECM_EDTR_TMPLT
 *
 * 추천에디터템플릿
 */
@Data
public class PdRecmEdtrTmplt {

	/**
	* 추천에디터템플릿번호
	*/
	private long recmEdtrTmpltNo;

	/**
	* 추천에디터템플릿명
	*/
	private String recmEdtrTmpltNm;

	/**
	* 추천에디터템플릿내용
	*/
	private String recmEdtrTmpltCont;

	/**
	* 섬네일이미지URL
	*/
	private String thumImgUrl;

	/**
	* 사용여부
	*/
	private String useYn;

	/**
	* 생성일시
	*/
	private String createDt;

	/**
	* 생성번호
	*/
	private long createNo;

	/**
	* 수정일시
	*/
	private String updateDt;

	/**
	* 수정번호
	*/
	private long updateNo;



	public PdRecmEdtrTmplt() {
	}

	public PdRecmEdtrTmplt(long recmEdtrTmpltNo, String recmEdtrTmpltNm, String recmEdtrTmpltCont, String thumImgUrl, String useYn, String createDt, long createNo, String updateDt, long updateNo) {
		this.recmEdtrTmpltNo = recmEdtrTmpltNo;
		this.recmEdtrTmpltNm = recmEdtrTmpltNm;
		this.recmEdtrTmpltCont = recmEdtrTmpltCont;
		this.thumImgUrl = thumImgUrl;
		this.useYn = useYn;
		this.createDt = createDt;
		this.createNo = createNo;
		this.updateDt = updateDt;
		this.updateNo = updateNo;
	}
}
