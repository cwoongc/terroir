package com.elevenst.terroir.product.registration.common.vine.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SellerNickNameV1 implements Serializable{
    private static final long serialVersionUID = -7167075872588750367L;
    private long nicknameSequence;
    private long memberNo;
    private boolean hasBaseNickname;
    private String nickname;
    private String nicknameImageUrl;
}
