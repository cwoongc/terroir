package com.elevenst.common.auth;

import skt.tmall.auth.AuthCode;

/**
 * Created by microflower on 2017. 8. 10..
 */
public class AuthChannelUtil {


    public static String getStartChar(String url) {

        String startChar = "";

        if (url.indexOf(AuthCode.WEB_BACK_CHANNEL) > -1) {

            //BACKOFFICE
            startChar = "&";


        } else if (url.indexOf(AuthCode.WEB_SELL_CHANNEL) > -1) {

            //SELLEROFFICE
            startChar = "&";


        } else {

            //WEB
            startChar = "&";

        }

        return startChar;

    }

    public static String getAuthType(String url) {

        String authType;

        if (url.indexOf(AuthCode.WEB_BACK_CHANNEL) > -1) {

            //BACKOFFICE
            authType = AuthCode.TMALL_AUTH_BACK;


        } else if (url.indexOf(AuthCode.WEB_SELL_CHANNEL) > -1) {

            //SELLEROFFICE
            authType = AuthCode.TMALL_AUTH;

        } else {

            //WEB
            authType = AuthCode.TMALL_AUTH;
        }

        return authType;
    }

    public static String getLoginUrl(String url) {

        String forwardLoginPage = "";

        if (url.indexOf(AuthCode.WEB_BACK_CHANNEL) > -1) {

            //BACKOFFICE
            forwardLoginPage = AuthCode.BACK_LOGIN_URL;

        } else if (url.indexOf(AuthCode.WEB_SELL_CHANNEL) > -1) {

            //SELLEROFFICE

            forwardLoginPage = AuthCode.SELLER_LOGIN_URL;

        } else {

            //WEB

            forwardLoginPage = AuthCode.FRONT_LOGIN_URL;

        }

        return forwardLoginPage;
    }


    public static String getChannel(String url) {

        String channel = "";

        if (url.indexOf(AuthCode.WEB_BACK_CHANNEL) > -1) {

            //BACKOFFICE
            channel = AuthCode.WEB_BACK_CHANNEL;

        } else if (url.indexOf(AuthCode.WEB_SELL_CHANNEL) > -1) {

            //SELLEROFFICE

            channel = AuthCode.WEB_SELL_CHANNEL;

        } else {

            //WEB

            channel = AuthCode.WEB_CHANNEL;

        }

        return channel;

    }
}
