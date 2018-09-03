package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DefaultInAddressVO implements Serializable {

	/**
	 * 반품/교환지 주소지번호
	 * addr_seq
	 */
	private int inAddrSeq;

	/**
	 * 반품/교환지 명
	 * addr_nm
	 */
	private String inAddrNm;

	/**
	 * 반품/교환지 국내, 해외주소구분(01:국내, 02:해외)
	 */
	private String inLocationCd;

	/**
	 * 회원번호
	 */
	private long inMemNo;


	public DefaultInAddressVO() {
	}

	public DefaultInAddressVO(int inAddrSeq, String inAddrNm, String inLocationCd, long inMemNo) {
		this.inAddrSeq = inAddrSeq;
		this.inAddrNm = inAddrNm;
		this.inLocationCd = inLocationCd;
		this.inMemNo = inMemNo;
	}


}
