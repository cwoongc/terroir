package com.elevenst.terroir.product.registration.pln_prd.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "PlanProductVO", description = "예약상품VO")
@Data
public class PlanProductVO implements Serializable {

    private static final long serialVersionUID = -7064685345886586070L;

    /**
     * 상품번호
     */
    @ApiModelProperty(value = "상품번호", required = true)
    private long prdNo;

    /**
     * 입고예정일자
     */
    @ApiModelProperty(value = "입고예정일자")
    private String wrhsPlnDy;

    /**
     * 입고일자
     */
    @ApiModelProperty(value = "입고일자")
    private String wrhsDy;

    /**
     * 등록일시
     */
    @ApiModelProperty(value = "등록일시")
    private String createDt;

    /**
     * 등록자번호
     */
    @ApiModelProperty(value = "등록자번호")
    private long createNo;

    /**
     * 수정일시
     */
    @ApiModelProperty(value = "수정일시")
    private String updateDt;

    /**
     * 수정자번호
     */
    @ApiModelProperty(value = "수정자번호")
    private long updateNo;

    /**
     * 이력 종료일시
     */
    @ApiModelProperty(value = "이력 종료일시")
    private String histAplEndDt;


}
