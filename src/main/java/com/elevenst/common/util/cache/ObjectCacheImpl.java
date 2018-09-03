package com.elevenst.common.util.cache;

import com.elevenst.common.util.StringUtil;
import com.elevenst.exception.TerroirException;
import com.elevenst.terroir.product.registration.common.service.CommonServiceImpl;
import com.elevenst.terroir.product.registration.common.vo.SyCoDetailVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skt.tmall.common.util.esocache.ESOCacheByte;
import skt.tmall.common.util.esocache.ESOCachePoolContainer;
import skt.tmall.common.util.esocache.ObjectUtil;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ObjectCacheImpl implements ObjectCache {

    @Autowired
    CommonServiceImpl commonService;

    private static final String commonGroupCodeService = "SY047";
    private static final String commonGroupCodeSlowLimit = "SY048";
    private static final String commonGroupCodeDataLengthLimit = "SY048";
    private static final String dbSwitchCode = "USE_ESOCACHE";
    private static final String terroirKeyPrefix = "terroir_";

    private boolean useESOCache = true;
    private String cacheServerType= StringUtil.nvl(System.getProperty("cacheServer.type"), "");
    private String service = null;
    private String cluster = null;
    private int defaultExpire = 60 * 60; // 60 Seconds * 60 (1시간)

    private ESOCacheByte esoByte = null;

    private int KHKKeyMultipleNumber = 12;
    private int KHKKeyMaxLength = Integer.toString(KHKKeyMultipleNumber).length();
    private String KHKKeySuffix = "_KHK_";

    private long esocacheSlowLimit = 300; // Milliseconds
    private long esocacheDataLengthLimit = 1000000 ; // 1MB

    protected String charset = System.getProperty("file.encoding", "UTF-8");

    @PostConstruct
    public void setObjectCacheInfo(){

            String clusterName = ProductConstDef.ESO_CLUSTER_NAME;
            String serviceName = ProductConstDef.ESO_SERVICE_NAME;

            this.isUseESOCache();

            setService(serviceName);

            try {
                setCluster(clusterName);
            }catch (IllegalArgumentException e){
                this.useESOCache = false;
                this.cluster = clusterName;
            }
            esocacheSlowLimit = getSlowLimit(esocacheSlowLimit);
            esocacheDataLengthLimit = getDataLengthLimit(esocacheDataLengthLimit);
    }

    private void isUseESOCache(){
        try {
            this.useESOCache = commonService.isUseValidApp(dbSwitchCode, false);
            log.debug("ESOCache: use " + this.useESOCache);
        } catch (Exception e) {
            this.useESOCache = false;
            log.error(e.getMessage(), e);
            this.useESOCache = false;
        }
    }


    public void setObjectCacheInfo(String clusterName, String serviceName) throws IllegalArgumentException {
        this.isUseESOCache();

        setService(serviceName);
        setCluster(clusterName);
        esocacheSlowLimit = getSlowLimit(esocacheSlowLimit);
        esocacheDataLengthLimit = getDataLengthLimit(esocacheDataLengthLimit);
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        if (useESOCache) {
            Set<String> clusters = ESOCachePoolContainer.getActiveClusters();
            if (clusters == null || !clusters.contains(cluster)) {
                throw new IllegalArgumentException("Cannot find cluster name [" + cluster + "] in active clusters [" + clusters + "]");
            }
        }
        this.cluster = cluster;
    }

    private Long getSlowLimit(long def) {

        if(StringUtil.isEmpty(cacheServerType)){
            Long slowLimit = 0L;
            SyCoDetailVO codeDetail = commonService.getCodeDetail(commonGroupCodeSlowLimit, "SLOWLIMIT");
            if (codeDetail == null)
                return def;
            try {
                slowLimit = Long.parseLong(codeDetail.getCdVal1());
                return slowLimit;
            } catch (NumberFormatException e) {
                log.warn(e.getMessage(), e);
                return def;
            }
        }else if(StringUtil.isNotEmpty(cacheServerType) && cacheServerType.equals("verify")){
            return 1000l;
        }else{
            return def;
        }
    }

    private Long getDataLengthLimit(long def) {
        Long dataLengthLimit = 0L;
        SyCoDetailVO codeDetail = commonService.getCodeDetail(commonGroupCodeDataLengthLimit, "DATALENLMT");
        if (codeDetail == null)
            return def;
        try {
            dataLengthLimit = Long.parseLong(codeDetail.getCdVal1());
            return dataLengthLimit;
        } catch (NumberFormatException e) {
            log.warn(e.getMessage(), e);
            return def;
        }
    }

    private void _setByte(String key, byte[] val, int expire) {
        if (useESOCache) {
            if (esoByte == null){
                esoByte = new ESOCacheByte(cluster, service);
            }

            key = terroirKeyPrefix + key;

            long startTime, elapsedTime;
            startTime = System.currentTimeMillis();
            esoByte.set(key, val, expire);

            // Check Slow Limit
            elapsedTime = System.currentTimeMillis() - startTime;

            if (elapsedTime > esocacheSlowLimit){
                log.error("ESOCache over slow limit [" + esoByte.getCacheKey(key) + "] [" + elapsedTime + "ms]");
            }

            if (val != null && val.length > esocacheDataLengthLimit) {
                log.error("[ESOODLL]ESOCache over data length limit  [" + esoByte.getCacheKey(key) + "] [" + val.length + "]");
            }

        } else {
            return;
        }
    }


    public static int getDateDiffSecond(Date later, Date earlier) {
        return (int) (later.getTime() - earlier.getTime()) / 1000;
    }

    private String getKeyKHK(String key) {
        //
        // CACHE_KEY => CACHE_KEY_KHK_00
        // CACHE_KEY_KHK_01
        // CACHE_KEY_KHK_02
        // ...
        // CACHE_KEY_KHK_23
        //
        return getKeyKHKByMultipleNumber(key, (int) (System.currentTimeMillis() % KHKKeyMultipleNumber));
    }

    private String getKeyKHKByMultipleNumber(String key, int n) {
        // key will be appended hotkey suffix and magic number(n)
        // so, key will like
        // key_KHK_00, key_KHK_01 ... key_KHK_12
        return key + String.format(KHKKeySuffix + "%0" + KHKKeyMaxLength + "d", n);
    }

    @Override
    public void setService(String svc) {
        if (useESOCache) {
            SyCoDetailVO codeDetail = commonService.getCodeDetail(commonGroupCodeService, svc);
            if (codeDetail == null) {
                log.warn("Cannot find service name [" + svc + "] in TmallCommonCode group [" + commonGroupCodeService + "]");
            }

            if (esoByte != null)
                esoByte.setService(svc);
        }

        this.service = svc;
    }

    @Override
    public String getService() {
        return null;
    }

    @Override
    public Object getObject(String key) throws TerroirException {

        Object res = null;

        if(useESOCache){

            byte[] valByte = getByte(key);

            if (valByte == null){
                return null;
            }

            try {
                res = this.byteToObj(valByte);
            } catch (InvalidClassException e) {

                try{
                    remove(key);
                }catch(Exception e1){
                    log.error(e1.getMessage() + ", key [" + key + "]", e1);
                }

                throw new TerroirException("Cannot deserialize data for key [" + key + "]", e);
            } catch (IOException e) {
                throw new TerroirException("Cannot deserialize data for key [" + key + "]", e);
            } catch (ClassNotFoundException e) {
                log.error(e.getMessage() + ", key [" + key + "]", e);
                return null;
            } catch (Exception e) {
                log.error(e.getMessage() + ", key [" + key + "]", e);
                return null;
            }

        }else{
            return null;
        }
        return res;
    }


    public <T> T byteToObj(byte[] b) throws IOException, ClassNotFoundException {
        T obj = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(b);
        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(bis);
            obj = (T) in.readObject();
        } finally {
            bis.close();
            if (in != null) {
                in.close();
            }

        }
        return obj;
    }



    @Override
    public String getString(String key) {

        if (useESOCache) {
            byte[] valByte = getByte(key);

            if (valByte == null)
                return null;

            try {
                return new String(valByte, 0, valByte.length, charset);
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage() + ", key [" + key + "]", e);
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public byte[] getByte(String key) {
//        return new byte[0];

        if (useESOCache) {
            if (esoByte == null)
                esoByte = new ESOCacheByte(cluster, service);

            byte[] res = null;
            long startTime, elapsedTime;

            key = terroirKeyPrefix + key;

            startTime = System.currentTimeMillis();
            res = esoByte.get(key);

            // Check Slow Limit
            elapsedTime = System.currentTimeMillis() - startTime;

            if (elapsedTime > esocacheSlowLimit){
                log.error("ESOCache over slow limit [" + esoByte.getCacheKey(key) + "] [" + elapsedTime + "ms]");
            }

            if (res != null && res.length > esocacheDataLengthLimit) {
                log.error("[ESOODLL]ESOCache over data length limit [" + esoByte.getCacheKey(key) + "]  [" + res.length + "]");
            }

            return res;
        } else {
            log.warn("Cannot get data byte[] by DBCache api.");
            return null;
        }
    }

    @Override
    public void setObject(String key, Object val) throws TerroirException {
        byte[] valByte = null;
        try {
            valByte = ObjectUtil.objToByte(val);
            setByte(key, valByte);
        } catch (IOException e1) {
            throw new TerroirException("Cannot serialize data for key [" + key + "]", e1);
        } catch (Exception e){
            log.error("ESO cache setObject Error, key [" + key + "]", e);
        }
    }

    @Override
    public void setObject(String key, Object val, int expire) throws TerroirException {

        byte[] valByte = null;
        try {
            valByte = ObjectUtil.objToByte(val);
            setByte(key, valByte, expire);
        } catch (IOException e1) {
            throw new TerroirException("Cannot serialize data for key [" + key + "]", e1);
        }
    }

    @Override
    public void setObject(String key, Object val, Date expire) throws TerroirException {
        byte[] valByte = null;
        try {
            valByte = ObjectUtil.objToByte(val);
            setByte(key, valByte, expire);
        } catch (IOException e1) {
            throw new TerroirException("Cannot serialize data for key [" + key + "]", e1);
        }
    }

    @Override
    public void setString(String key, String val) {
        try {
            setByte(key, val.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage() + ", key [" + key + "]", e);
        }
    }

    @Override
    public void setString(String key, String val, int expire) {
        try {
            setByte(key, val.getBytes(charset), expire);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage() + ", key [" + key + "]", e);
        }
    }

    @Override
    public void setString(String key, String val, Date expire) {
        try {
            setByte(key, val.getBytes(charset), expire);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage() + ", key [" + key + "]", e);
        }
    }

    @Override
    public void setByte(String key, byte[] val) {
        _setByte(key, val, defaultExpire);
    }

    @Override
    public void setByte(String key, byte[] val, int expire) {
        _setByte(key, val, expire);
    }

    @Override
    public void setByte(String key, byte[] val, Date expire) {
        _setByte(key, val, getDateDiffSecond(expire, new Date()));
    }

    @Override
    public void remove(String key) {
        if (useESOCache) {
            if (esoByte == null)
                esoByte = new ESOCacheByte(cluster, service);

            long startTime, elapsedTime;
            startTime = System.currentTimeMillis();
            esoByte.del(key);

            // Check Slow Limit
            elapsedTime = System.currentTimeMillis() - startTime;

            if (elapsedTime > esocacheSlowLimit) {
                log.error("ESOCache over slow limit [" + esoByte.getCacheKey(key) + "] [" + elapsedTime + "ms]");
            }

        } else {
            return;
        }
    }

    @Override
    public Object getObjectKHK(String key) throws TerroirException {
        byte[] valByte = getByteKHK(key);
        if (valByte == null)
            return null;

        Object res = null;
        try {
            res = ObjectUtil.byteToObj(valByte);
        } catch (IOException e) {
            throw new TerroirException("Cannot deserialize data for key [" + key + "]");
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage() + ", key [" + key + "]", e);
            return null;
        }

        return res;
    }

    @Override
    public String getStringKHK(String key) {
        byte[] valByte = getByteKHK(key);

        if (valByte == null)
            return null;

        try {
            return new String(valByte, 0, valByte.length, charset);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage() + ", key [" + key + "]", e);
            return null;
        }
    }

    @Override
    public byte[] getByteKHK(String key) {

        String khKey = getKeyKHK(key);

        // Get KHKKeyValue
        byte[] val = getByte(khKey);
        return val;
    }

    @Override
    public void setObjectKHK(String key, Object val) throws TerroirException {
        setObjectKHK(key, val, defaultExpire);
    }

    @Override
    public void setObjectKHK(String key, Object val, int expire) throws TerroirException {
        byte[] valByte = null;
        try {
            valByte = ObjectUtil.objToByte(val);
        } catch (IOException e1) {
            throw new TerroirException("Cannot serialize data for key [" + key + "]");
        }
        setByteKHK(key, valByte, expire);
    }

    @Override
    public void setObjectKHK(String key, Object val, Date expire) throws TerroirException {
        setObjectKHK(key, val, getDateDiffSecond(expire, new Date()));
    }

    @Override
    public void setStringKHK(String key, String val) {

    }

    @Override
    public void setStringKHK(String key, String val, int expire) {

    }

    @Override
    public void setStringKHK(String key, String val, Date expire) {

    }

    @Override
    public void setByteKHK(String key, byte[] val) {
        setByteKHK(key, val, defaultExpire);
    }

    @Override
    public void setByteKHK(String key, byte[] val, int expire) {
        setByte(key, val, expire);
        for (int i = 0; i < KHKKeyMultipleNumber; i++) {
            String nKey = getKeyKHKByMultipleNumber(key, i);
            setByte(nKey, val, expire);
        }
    }

    @Override
    public void setByteKHK(String key, byte[] val, Date expire) {
        setByteKHK(key, val, getDateDiffSecond(expire, new Date()));
    }

    @Override
    public void removeKHK(String key) {
        remove(key);
        for (int i = 0; i < KHKKeyMultipleNumber; i++) {
            String nKey = getKeyKHKByMultipleNumber(key, i);
            remove(nKey);
        }
    }

    @Override
    public int getExpire(String key) {
        if (useESOCache) {
            if (esoByte == null)
                esoByte = new ESOCacheByte(cluster, service);

            int res = 0;
            long startTime, elapsedTime;
            startTime = System.currentTimeMillis();
            res = esoByte.getExpire(key);

            // Check Slow Limit
            elapsedTime = System.currentTimeMillis() - startTime;

            if (elapsedTime > esocacheSlowLimit){
                log.error("ESOCache over slow limit [" + esoByte.getCacheKey(key) + "] [" + elapsedTime + "ms]");
            }

            return res;
        } else {
            log.warn("DBCache cannot support to get expire from DB.");
            return -1;
        }
    }

    @Override
    public void setExpire(String key, int expire) {
        if (useESOCache) {
            if (esoByte == null)
                esoByte = new ESOCacheByte(cluster, service);

            long startTime, elapsedTime;
            startTime = System.currentTimeMillis();

            esoByte.setExpire(key, expire);

            // Check Slow Limit
            elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime > esocacheSlowLimit){
                log.error("ESOCache over slow limit [" + esoByte.getCacheKey(key) + "] [" + elapsedTime + "ms]");
            }
        } else {
            log.warn("DBCache cannot support to change expire from DB.");
        }
    }

    @Override
    public void setExpire(String key, Date expireAt) {
        if (useESOCache) {
            if (esoByte == null)
                esoByte = new ESOCacheByte(cluster, service);

            esoByte.setExpireAt(key, expireAt);
        } else {
            log.warn("DBCache cannot support to change expire from DB.");
        }

    }

    @Override
    public void setDefaultExpire(int expire) {
        this.defaultExpire = expire;
    }

    @Override
    public int getDefaultExpire() {
        return defaultExpire;
    }
}
