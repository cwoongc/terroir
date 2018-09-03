package com.elevenst.terroir.product.registration.common.vine;

import com.elevenst.common.properties.PropMgr;
import com.elevenst.common.util.StringUtil;
import com.elevenst.common.util.cache.ObjectCacheImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class VineApiConfig {

    @Autowired
    PropMgr propMgr;

    @Autowired
    ObjectCacheImpl objectCache;

    public static final String VINE_API_ALPHA_SERVER_URL = "vineApiAlphaServerUrl";
    public static final String VINE_API_SERVER_URL = "vineApiServerUrl";
    public static final String VINE_API_CLIENT_CONFIG = "VINE_API_CLIENT_CONFIG";
    public static final String SWITCH_VINE_PRODUCT_API = "VINE_PRODUCT_API";
    public static final String SWITCH_VINE_DEPARTMENT_API = "VINE_DEPARTMENT_API";
    public static final String SWITCH_VINE_MAIN_API = "VINE_MAIN_API";
    public static final String SWITCH_VINE_TIMELINE_API = "VINE_TIMELINE_API";
    public static final String SWITCH_VINE_RECOMMEND_API = "vineRecommendApi";
    public static final String SWITCH_VINE_GLOBAL_API = "VINE_GLOBAL_API";
    public static final String SWITCH_VINE_MYHISTORY_PRD_API = "VINE_MYHIST_PRD_API";
    public static final String SWITCH_VINE_QNA_API = "VINE_QNA_API";
    public static final String SWITCH_VINE_REVIEW_API = "VINE_REVIEW_API";

    public Map<String, Integer> getVineApiClientConfig() {

        String key = "getVineApiClientConfig";
        try {
            Map<String, Integer> value = (Map<String, Integer>)objectCache.getObject(key);
            if(value != null){
                return value;
            }
        }catch (Exception e){
            log.error("ESO cache error == "+key);
        }

        String config = StringUtil.nvl(propMgr.get1hourTimeProperty(VINE_API_CLIENT_CONFIG), "100,2000,30,60");
        String[] values = config.split(",");
        Map<String, Integer> map = new HashMap();
        map.put("connectTimeout", Integer.parseInt(values[0]));
        map.put("readTimeout", Integer.parseInt(values[1]));
        map.put("maxIdleConnections", Integer.parseInt(values[2]));
        map.put("keepAliveDuration", Integer.parseInt(values[3]));

        objectCache.setObject(key, map, 86400);

        return map;
    }

    public String getVineApiServerUrl() {
        return StringUtil.nvl(propMgr.get1hourTimeProperty(VINE_API_SERVER_URL), "");
    }

    public String getVineApiAlphaServerUrl() {
        return StringUtil.nvl(propMgr.get1hourTimeProperty(VINE_API_ALPHA_SERVER_URL), "");
    }


    public boolean isVineApiSwitchUse(String api) {

        return true;
    }

    public String getVerifyServerHostName() {
        return StringUtil.nvl(propMgr.get1hourTimeProperty("verifyServerHostCheck"), "");
    }

    public boolean isVerify() {
        try {
            String[] hostNames = getVerifyServerHostName().split(",");
            String localHostName = InetAddress.getLocalHost().getHostName();

            for(String hostName : hostNames) {
                if (!"".equals(hostName) && localHostName.indexOf(hostName) > -1) {
                    return true;
                }
            }
        } catch (UnknownHostException e) {
            return false;
        } catch (Exception e1) {
            return false;
        }
        return false;
    }
}
