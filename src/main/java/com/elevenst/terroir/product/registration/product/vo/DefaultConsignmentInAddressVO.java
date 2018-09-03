package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DefaultConsignmentInAddressVO implements Serializable {

	/**
	 * 기본 위탁 반품/교환지 주소지번호
	 * addr_seq
	 */
	private int cnsgInAddrSeq;

	/**
	 * 기본 위탁 반품/교환지 명
	 * addr_nm
	 */
	private String cnsgInAddrNm;

	/**
	 * 기본 위탁 반품/교환지 국내, 해외주소구분(01:국내, 02:해외)
	 */
	private String cnsgInLocationCd;

	/**
	 * 회원번호
	 */
	private long cnsgInMemNo;


	public DefaultConsignmentInAddressVO() {
	}

	public DefaultConsignmentInAddressVO(int cnsgInAddrSeq, String cnsgInAddrNm, String cnsgInLocationCd, long cnsgInMemNo) {
		this.cnsgInAddrSeq = cnsgInAddrSeq;
		this.cnsgInAddrNm = cnsgInAddrNm;
		this.cnsgInLocationCd = cnsgInLocationCd;
		this.cnsgInMemNo = cnsgInMemNo;
	}


}
