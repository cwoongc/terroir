package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DefaultConsignmentOutAddressVO implements Serializable {

	/**
	 * 기본 위탁 출고지 주소지번호
	 * addr_seq
	 */
	private int cnsgOutAddrSeq;

	/**
	 * 기본 위탁 출고지 명
	 * addr_nm
	 */
	private String cnsgOutAddrNm;

	/**
	 * 기본 위탁 출고지 국내, 해외주소구분(01:국내, 02:해외)
	 */
	private String cnsgOutLocationCd;

	/**
	 * 회원번호
	 */
	private long cnsgOutMemNo;


	public DefaultConsignmentOutAddressVO() {
	}

	public DefaultConsignmentOutAddressVO(int cnsgOutAddrSeq, String cnsgOutAddrNm, String cnsgOutLocationCd, long cnsgOutMemNo) {
		this.cnsgOutAddrSeq = cnsgOutAddrSeq;
		this.cnsgOutAddrNm = cnsgOutAddrNm;
		this.cnsgOutLocationCd = cnsgOutLocationCd;
		this.cnsgOutMemNo = cnsgOutMemNo;
	}


}
