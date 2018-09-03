package com.elevenst.common.properties;

import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.common.service.CommonServiceImpl;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PropMgr {

    @Autowired
    CommonServiceImpl commonServiceImpl;

    /**
     * DB 테이블 프로퍼티값 조회(1시간 주기 캐시)
     * @param key 키값
     * @return 조회된 value
     */
    public String get1hourTimeProperty(String key) {
        // 01.tMallDomainProject/src/skt/tmall/business/common/dao/config/commonCorpDAOMap.xml
        return getKeyValue(key, false);
    }

    /**
     * DB 테이블 프로퍼티값 조회(1시간 주기 캐시)
     * @param key 키값
     * @return 조회된 value list
     */
    public List<Object> get1hourTimePropertyList(String[] key) {
        // 01.tMallDomainProject/src/skt/tmall/business/common/dao/config/commonCorpDAOMap.xml
        return getKeyValueList(key, "commonCorpDAOMap.get1hourTimeProperty.List");
    }

    /**
     * 쿼리조회 공통 내부함수
     * 공통 쿼리 작성시 KEY / VALUE 명칭은 꼭 준수 해야함 !!
     * @param key 조회할 식별 Key
     * @return 조회된 value
     */
    protected String getKeyValue(String key, boolean isRealTime) {

        if (StringUtil.isEmpty(key)) {
            return null;
        }

        try {
            Map prop = new HashMap();
            prop.put("KEY", key);

            if(isRealTime) {
                return commonServiceImpl.getRealTimeProperty(prop);
            } else {
                return commonServiceImpl.get1hourTimeProperty(prop);
            }
        } catch (Exception e) {
            throw new ProductException("[PropMgr] getKeyValue 조회 실패", e);
        }
    }

    /**
     * 키 값들을 in으로 던져	key와 value의 list를 구해옴
     * @param key 키 배열
     * @return 키,value list
     */
    protected List<Object> getKeyValueList(String[] key, String mapName) {
        if (StringUtil.isEmpty(key)||key.length <= 0)
            return null;

        try {
            Map<String, Object> prop = new HashMap<String, Object>();
            prop.put("KEY", key);

            List<Object> list = commonServiceImpl.get1hourTimePropertyList(prop);

            return list;
        } catch (Exception e) {
            throw new ProductException("[PropMgr] "+ mapName +" 조회 실패", e);
        }
    }
}
