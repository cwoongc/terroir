package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DefaultGlobalOutAddressVO implements Serializable {

	/**
	 * 기본 해외 출고지 주소지번호
	 * addr_seq
	 */
	private int globalOutAddrSeq;

	/**
	 * 기본 해외 출고지 명
	 * addr_nm
	 */
	private String globalOutAddrNm;

	/**
	 * 기본 해외 출고지 국내, 해외주소구분(01:국내, 02:해외)
	 */
	private String globalOutLocationCd;

	/**
	 * 회원번호
	 */
	private long globalOutMemNo;


	private String kglFreePickupYN;



	public DefaultGlobalOutAddressVO() {
	}

	public DefaultGlobalOutAddressVO(int globalOutAddrSeq, String globalOutAddrNm, String globalOutLocationCd, long globalOutMemNo, String kglFreePickupYN) {
		this.globalOutAddrSeq = globalOutAddrSeq;
		this.globalOutAddrNm = globalOutAddrNm;
		this.globalOutLocationCd = globalOutLocationCd;
		this.globalOutMemNo = globalOutMemNo;
		this.kglFreePickupYN = kglFreePickupYN;
	}


}
