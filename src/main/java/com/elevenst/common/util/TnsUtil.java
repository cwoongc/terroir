package com.elevenst.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class TnsUtil {

    private static final String PC_ID = "PCID";
    /**
     *
     * PC-ID를 조회하는 메소드.
     * <P/>
     * 개인화 서비스에서 Cookie 셋팅한 PC-ID를 가져오기 위한 메소드.
     * PC-ID가 없을 대는 ""(Blank)를 넘김
     * @param  request
     * @return
     */
    public static String getPCID(HttpServletRequest request) {

        String rslt = "";
        try {

            Cookie[] cookie = request.getCookies();
            if(cookie != null) {
                for(int i = 0; i < cookie.length; i++) {
                    String tempNm = cookie[i].getName();
                    if(PC_ID.equals(tempNm)) {
                        rslt = cookie[i].getValue();
                    }
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return rslt;
    }
}
