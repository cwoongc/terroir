package com.elevenst.terroir.product.registration.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DefaultAddressSetVO implements Serializable{

    @ApiModelProperty(value = "기본 출고지 주소")
    private DefaultOutAddressVO defaultOutAddress;

    @ApiModelProperty(value = "기본 교환/반품지 주소")
    private DefaultInAddressVO defaultInAddress;

    @ApiModelProperty(value = "기본 방문수령지 주소")
    private DefaultVisitAddressVO defaultVisitAddress;

    @ApiModelProperty(value = "기본 위탁 출고지 주소")
    private DefaultConsignmentOutAddressVO defaultConsignmentOutAddress;

    @ApiModelProperty(value = "기본 위탁 교환/반품지 주소")
    private DefaultConsignmentInAddressVO defaultConsignmentInAddress;

    @ApiModelProperty(value = "기본 해외 출고지 주소")
    private DefaultGlobalOutAddressVO defaultGlobalOutAddressVO;

    public DefaultAddressSetVO(DefaultOutAddressVO defaultOutAddress, DefaultInAddressVO defaultInAddress, DefaultVisitAddressVO defaultVisitAddress, DefaultConsignmentOutAddressVO defaultConsignmentOutAddress, DefaultConsignmentInAddressVO defaultConsignmentInAddress, DefaultGlobalOutAddressVO defaultGlobalOutAddress) {
        this.defaultOutAddress = defaultOutAddress;
        this.defaultInAddress = defaultInAddress;
        this.defaultVisitAddress = defaultVisitAddress;
        this.defaultConsignmentOutAddress = defaultConsignmentOutAddress;
        this.defaultConsignmentInAddress = defaultConsignmentInAddress;
        this.defaultGlobalOutAddressVO = defaultGlobalOutAddress;
    }

    public DefaultAddressSetVO() {
    }
}
