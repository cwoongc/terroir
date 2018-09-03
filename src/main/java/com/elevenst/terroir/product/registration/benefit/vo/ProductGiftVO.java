package com.elevenst.terroir.product.registration.benefit.vo;

import com.elevenst.common.util.StringUtil;
import lombok.Data;

import java.io.File;

@Data
public class ProductGiftVO {

	private long giftNo;
	private long prdNo;
	private String giftNm;
	private String aplBgnDt;
	private String aplEndDt;
	private String imgUrlPath;
	private String imgNm;
	private String giftInfo;
	private String createDt;
	private long createNo;
	private String updateDt;
	private long updateNo;
	private String useGiftYn = "N";

	private String imgUploadYn;
	private File imgFile;

	public boolean compare(ProductGiftVO productGiftVO) {
		if(
			this.prdNo == productGiftVO.getPrdNo()
				&& this.giftNm.equals(productGiftVO.getGiftNm())
				&& this.aplBgnDt.equals(productGiftVO.getAplBgnDt())
				&& this.aplEndDt.equals(productGiftVO.getAplEndDt())
				&& StringUtil.nvl(this.giftInfo).equals(StringUtil.nvl(productGiftVO.getGiftInfo()))
			) return true;

		return false;
	}
}