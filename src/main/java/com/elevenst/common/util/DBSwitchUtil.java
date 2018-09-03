package com.elevenst.common.util;

import com.elevenst.common.vo.SyAppMngVO;
import com.elevenst.terroir.product.registration.common.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Component
public class DBSwitchUtil {
    private List<String> syAppList = null;
    private boolean bUseCache = true;
    private HashMap<String, Object> validAppMap = null;
    @Autowired CommonMapper commonMapper;

    public DBSwitchUtil()
    {
        syAppList = new ArrayList<String>();
    }

    public void setUseCache(boolean bUseCache){
        this.bUseCache = bUseCache;
    }

    public void addKey(String key){
        syAppList.removeAll(syAppList);
        syAppList.add(key);
    }

    public void checkKey() throws Exception {

        SyAppMngVO syAppMngVO = new SyAppMngVO();
        Collections.sort(syAppList);

        syAppMngVO.setValidAppListStr(syAppList);
        syAppMngVO.setUseCache(bUseCache);
        validAppMap = commonMapper.getValidAppMap(syAppMngVO);
    }

    public boolean isExist(String key) {
        if (validAppMap == null)
            return false;

        boolean bExist = validAppMap.containsValue(key);
        if (bExist && (key.indexOf("DB_CACHE") != -1))
        {
            Calendar calendar = Calendar.getInstance();
            int curHour = calendar.get(Calendar.HOUR_OF_DAY);

            if (curHour == 0)
                return false;
        }

        return bExist;
    }
}
