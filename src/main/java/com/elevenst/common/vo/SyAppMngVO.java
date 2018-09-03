package com.elevenst.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Data
public class SyAppMngVO implements Serializable{

    private static final long serialVersionUID = -2973270156506019909L;
    private String appId;
    private String grpId;
    private String appNm;
    private String useYn;
    private Date bgnDt;
    private Date endDt;
    private boolean useCache = true;

    private List<String> syAppList = new ArrayList<String>();

    private List<HashMap<String, Object>> validAppList;

    public List<String> validAppListStr = new ArrayList<String>();

    public void clearKey(){
        syAppList.clear();
    }

    public void addKey(String key)
    {
        syAppList.add(key);
    }


    public void setUseCache(boolean bUseCache)
    {
        this.useCache = bUseCache;
    }

    public boolean isExist(String key) {
        if (validAppList == null)
            return false;

        boolean bExist = false;
        for(HashMap<String, Object> map : validAppList){
            map.containsKey(key);
        }
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
