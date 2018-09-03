package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DefaultVisitAddressVO implements Serializable {

	/**
	 * 방문수령지 주소지번호
	 * addr_seq
	 */
	private int visitAddrSeq;

	/**
	 * 방문수령지 명
	 * addr_nm
	 */
	private String visitAddrNm;

	/**
	 * 방문수령지 국내, 해외주소구분(01:국내, 02:해외)
	 */
	private String visitLocationCd;


	public DefaultVisitAddressVO() {
	}

	public DefaultVisitAddressVO(int visitAddrSeq, String visitAddrNm, String visitLocationCd) {
		this.visitAddrSeq = visitAddrSeq;
		this.visitAddrNm = visitAddrNm;
		this.visitLocationCd = visitLocationCd;
	}


}
