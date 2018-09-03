package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DefaultOutAddressVO implements Serializable {

	/**
	 * 출고지 주소지번호
	 * addr_seq
	 */
	private int outAddrSeq;

	/**
	 * 출고지 명
	 * addr_nm
	 */
	private String outAddrNm;

	/**
	 * 출고지 국내, 해외주소구분(01:국내, 02:해외)
	 */
	private String outLocationCd;

	/**
	 * 회원번호
	 */
	private long outMemNo;

	/**
	 *  출고지 통합주소번호
	 *  itg_addr_seq
	 */
	private int itgAddrSeq;

	public DefaultOutAddressVO() {
	}

	public DefaultOutAddressVO(int outAddrSeq, String outAddrNm, String outLocationCd, long outMemNo, int itgAddrSeq) {
		this.outAddrSeq = outAddrSeq;
		this.outAddrNm = outAddrNm;
		this.outLocationCd = outLocationCd;
		this.outMemNo = outMemNo;
		this.itgAddrSeq = itgAddrSeq;
	}


}
