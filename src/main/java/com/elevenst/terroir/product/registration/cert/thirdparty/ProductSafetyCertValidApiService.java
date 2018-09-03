package com.elevenst.terroir.product.registration.cert.thirdparty;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Created by 1003811 on 2017. 10. 13..
 */
@Slf4j
@Component
public class ProductSafetyCertValidApiService extends ProductCertValidApiService {

    private final String REQUEST_URL = "http://www.safetykorea.kr/openapi/api/cert/certificationDetail.json?certNum=";
    private final int TIMEOUT = 10000;

    @Override
    public boolean doRequestApi(String certType, String certKey) {
        boolean result = false;

        //create HTTP Request obj.
        GetMethod get = new GetMethod(REQUEST_URL + certKey);
        HttpClient httpClient = new HttpClient();
        get.setRequestHeader("AuthKey", "123dae05-6c30-4737-a163-863ae64a1d39");

        //set param option.
        httpClient.getParams().setParameter("http.protocol.expect-continue", false);
        httpClient.getParams().setParameter("http.connection.timeout", TIMEOUT);
        httpClient.getParams().setParameter("http.socket.timeout", TIMEOUT);
        try {
            //do request api to safetykorea.
            httpClient.executeMethod(get);
            if (get.getStatusCode() == HttpStatus.SC_OK) {
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(IOUtils.toString(get.getResponseBodyAsStream(), "UTF-8"));
                JsonObject json = element.getAsJsonObject();
                String resultCode = json.get("resultCode").getAsString();

                if(StringUtils.equals("2000" , resultCode )) {
                    JsonObject json2 = json.getAsJsonObject("resultData");
                    String certState = json2.get("certState").getAsString();
                    List<String> confirmCertList = Arrays.asList("적합", "변경", "개선명령", "청문실시");
                    if (confirmCertList.contains(certState)) {
                        result = true; //해당케이스 아닌경우 무조건 return false.
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            get.releaseConnection();
        }

        return result;
    }

    @Override
    public Map<String, Object> getCertInfo(String certType, String certKey) {
        return null;
    }
}
