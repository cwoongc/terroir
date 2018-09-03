package com.elevenst.common.util;

import com.elevenst.terroir.product.registration.product.code.CreateCdTypes;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.security.acl.Group;
import java.util.Collection;

@Component
public class VOUtil {

    public Object convertEntity(Object oriObj, Object toObj) {
        BeanUtils.copyProperties(oriObj, toObj);
        return toObj;
    }

    public boolean isEmptyVO(Object param) {
        if (param == null) {
            return true;
        }

        if (param instanceof Collection) {
            if(((Collection)param).isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public String getCalledByAPIType(String createCd) {
        final String CALLED_BY_API_STRING = "";
        final String CALLED_BY_NON_API_STRING = "ALL";

        return GroupCodeUtil.equalsDtlsCd(createCd, CreateCdTypes.SELLER_API) ?
            CALLED_BY_API_STRING : CALLED_BY_NON_API_STRING;

    }
}
