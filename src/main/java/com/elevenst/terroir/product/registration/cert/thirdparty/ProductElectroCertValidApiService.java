package com.elevenst.terroir.product.registration.cert.thirdparty;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 1003811 on 2017. 10. 13..
 */
@Slf4j
@Component
public class ProductElectroCertValidApiService extends ProductCertValidApiService {

    private final String GET_AUTH_STATUS_REQUEST_URL = "http://emsip.go.kr/openapi/service/AuthenticationInfoService/getAuthStatus.do?mtlCefNo=";
    private final String GET_AUTH_INFO_REQUEST_URL = "http://emsip.go.kr/openapi/service/AuthenticationInfoService/getAuthInfo.do?mtlCefNo=";
    private final int TIMEOUT = 10000;

    @Override
    public boolean doRequestApi(String certType, String certKey) {
        boolean result = false;

        //create HTTP Request obj.
        GetMethod get = new GetMethod(GET_AUTH_STATUS_REQUEST_URL + certKey);
        HttpClient httpClient = new HttpClient();

        //set param option.
        httpClient.getParams().setParameter("http.protocol.expect-continue", false);
        httpClient.getParams().setParameter("http.connection.timeout", TIMEOUT);
        httpClient.getParams().setParameter("http.socket.timeout", TIMEOUT);
        try {
            //do request api to emsip
            httpClient.executeMethod(get);
            if (get.getStatusCode() == HttpStatus.SC_OK) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(get.getResponseBodyAsStream(), "UTF-8"));
                SAXBuilder builder = new SAXBuilder();
                Document document = (Document) builder.build(reader);
                Element root = document.getRootElement();
                Element authYn = root.getChild("authYn");
                if (StringUtils.equals("Y", authYn.getText())) {
                    result = true; //해당케이스 아닌경우 무조건 return false.
                }

            }

        } catch (IOException e) {
            log.warn("emsip.go.kr API 호출 중 오류.",e);
            e.printStackTrace();
        } catch (JDOMException e) {
            log.warn("emaip.go.kr API 결과값 파싱 중 오류.",e);
            e.printStackTrace();
        } finally {
            get.releaseConnection();
        }

        return result;
    }

    @Override
    public Map<String, Object> getCertInfo(String certType, String certKey) {

        Map<String, Object> rstMap = new HashMap<>();

        GetMethod get = new GetMethod("http://emsip.go.kr/openapi/service/AuthenticationInfoService/getAuthInfo.do?mtlCefNo=" + certKey);
        HttpClient httpclient = new HttpClient();
        BufferedReader reader = null;

        try {

            httpclient.getParams().setParameter("http.protocol.expect-continue", false);
            httpclient.getParams().setParameter("http.connection.timeout", TIMEOUT);
            httpclient.getParams().setParameter("http.socket.timeout", TIMEOUT);

            httpclient.executeMethod(get);
            if (get.getStatusCode() == HttpStatus.SC_OK) {

                String encode = "UTF-8";
                reader = new BufferedReader(new InputStreamReader(get.getResponseBodyAsStream(), encode));
                SAXBuilder builder = new SAXBuilder();
                Document document = (Document) builder.build(reader);

                Element root = document.getRootElement();
                if (root.getChildren().size() > 0)
                    for (Object ele : root.getChildren()) {
                        Element e = (Element) ele;
                        rstMap.put(e.getName(), e.getText());
                    }
            }
        } catch (Exception e) {
            log.warn("getCertInfo() 인증정보 확인 오류", e);
        } finally {
            IOUtils.closeQuietly(reader);
            get.releaseConnection();
        }

        return rstMap;
    }


}
