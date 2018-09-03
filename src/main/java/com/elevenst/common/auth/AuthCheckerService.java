package com.elevenst.common.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import skt.tmall.auth.Auth;
import skt.tmall.auth.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by microflower on 2017. 8. 10..
 */
@Component
public class AuthCheckerService {

    private Logger log = LoggerFactory.getLogger(AuthCheckerService.class);

    public static final long LOGIN_DURATION = 1000 * 60 * 60 * 6; // 6시간

    public boolean authCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String requestURL = request.getRequestURL().toString();

        String url = requestURL.toUpperCase();
        String authType = AuthChannelUtil.getAuthType(url);
        String channel = AuthChannelUtil.getChannel(url);
        String forwardLoginPage = AuthChannelUtil.getLoginUrl(url);
        String startChar = AuthChannelUtil.getStartChar(url);

        if (log.isDebugEnabled()) {
            log.debug(channel + " start");
            log.debug(url);
            log.debug(authType);
            log.debug(forwardLoginPage);
            log.debug(startChar);
        }

        Auth auth = AuthService.getAuth(request, response, authType);

        if (auth.isAuth()) {

            log.info("로그인 됨");
            request.setAttribute(authType, auth);

            return true;

        } else {

            log.info("로그인 되어있지 않다");

        }


        if (requestURL.startsWith("http://") && requestURL.indexOf("protocol=https") > 0) {
            requestURL = "https://" + requestURL.substring(7);
        }

        String returnURL = URLEncoder.encode(requestURL, "UTF-8");

        log.info("returnURL:" + returnURL);

        StringBuffer parameters =new StringBuffer();
        parameters.append("returnURL=" + returnURL);

        String nextURL = forwardLoginPage + startChar + parameters.toString();

        response.sendRedirect(nextURL);

        return false;


    }


}
