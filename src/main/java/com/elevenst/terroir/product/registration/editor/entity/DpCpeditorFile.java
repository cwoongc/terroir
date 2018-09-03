package com.elevenst.terroir.product.registration.editor.entity;

import lombok.Data;

/**
 * DP_CPEDITOR_FILE
 *
 * html에디터 파일 정보 테이블
 */
@Data
public class DpCpeditorFile {

	/**
	* 파일번호
	*/
	private long fid;

	/**
	* 파일유형
	*/
	private String fileType;

	/**
	* null
	*/
	private String fileNm;

	/**
	* null
	*/
	private String fileSize;

	/**
	* 파일물리경로
	*/
	private String filePath;

	/**
	* 파일주소
	*/
	private String fileUrl;

	/**
	* 파일사용여부
	*/
	private String useYn;

	/**
	* null
	*/
	private String createDt;

	/**
	* null
	*/
	private long createNo;

	/**
	* null
	*/
	private String delDt;



}
