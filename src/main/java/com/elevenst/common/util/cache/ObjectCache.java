package com.elevenst.common.util.cache;

import com.elevenst.exception.TerroirException;

import java.util.Date;
import java.util.List;

public interface ObjectCache
{
    // 서비스 코드값 set
    public void setService(String svc);

    // 서비스 코드값 조회
    public String getService();

    // 캐시 조회 결과는 Object, String, byte[] 로 반환
    public Object getObject(String key) throws TerroirException;
    public String getString(String key);
    public byte[] getByte(String key);

    // 캐시 저장 method.
    // expire는 초 단위로 현재 시간 부터 유지될 TTL (Time To Live)
    // expire가 없는 Method는 기본 저장 기간 설정.
    public void setObject(String key, Object val) throws TerroirException;
    public void setObject(String key, Object val, int expire) throws TerroirException;
    public void setObject(String key, Object val, Date expire) throws TerroirException;

    public void setString(String key, String val);
    public void setString(String key, String val, int expire);
    public void setString(String key, String val, Date expire);

    public void setByte(String key, byte[] val);
    public void setByte(String key, byte[] val, int expire);
    public void setByte(String key, byte[] val, Date expire);

    // 캐시 삭제
    public void remove(String key);

    // Known Hotkey
    public Object getObjectKHK(String key) throws TerroirException;
    public String getStringKHK(String key);
    public byte[] getByteKHK(String key);

    // 캐시 저장 method.
    public void setObjectKHK(String key, Object val) throws TerroirException;
    public void setObjectKHK(String key, Object val, int expire) throws TerroirException;
    public void setObjectKHK(String key, Object val, Date expire) throws TerroirException;

    public void setStringKHK(String key, String val);
    public void setStringKHK(String key, String val, int expire);
    public void setStringKHK(String key, String val, Date expire);

    public void setByteKHK(String key, byte[] val);
    public void setByteKHK(String key, byte[] val, int expire);
    public void setByteKHK(String key, byte[] val, Date expire);

    // 캐시 삭제
    public void removeKHK(String key);

    // return -1 : the key is invalid
    public int getExpire(String key);
    public void setExpire(String key, int expire);
    public void setExpire(String key, Date expireAt);

    public void setDefaultExpire(int expire);
    public int getDefaultExpire();
}