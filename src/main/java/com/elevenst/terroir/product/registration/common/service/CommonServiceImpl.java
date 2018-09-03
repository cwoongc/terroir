package com.elevenst.terroir.product.registration.common.service;

import com.elevenst.common.code.CacheKeyConstDef;
import com.elevenst.common.util.StringUtil;
import com.elevenst.common.util.cache.ObjectCacheImpl;
import com.elevenst.common.vo.SyAppMngVO;
import com.elevenst.exception.TerroirException;
import com.elevenst.terroir.product.registration.common.mapper.CommonMapper;
import com.elevenst.terroir.product.registration.common.vo.HelpPopVO;
import com.elevenst.terroir.product.registration.common.vo.SyCoDetailVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CommonServiceImpl {

    @Autowired
    CommonMapper commonMapper;

    @Autowired
    ObjectCacheImpl objectCache;

    public String getRealTimeProperty(Map prop) {

        String PLAN_ID = (String)prop.get("PLAN_ID");
        if(StringUtil.isNotEmpty(PLAN_ID)){
            prop.put("PLAN_ID", StringUtil.getEscapeSqlInjectionString(PLAN_ID));
        }
        String PLAN_SQL = (String)prop.get("PLAN_SQL");
        if(StringUtil.isNotEmpty(PLAN_SQL)){
            prop.put("PLAN_SQL", StringUtil.getEscapeSqlInjectionString(PLAN_SQL));
        }
        return commonMapper.getRealTimeProperties(prop);
    }

    public String get1hourTimeProperty(Map prop) {

        String key = "get1hourTimeProperty_"+prop.get("KEY").toString();
        try {
            String value = (String)objectCache.getObject(key);
            if(value == null){
                value = commonMapper.get1hourTimeProperties(prop);
                objectCache.setObject(key, value);
                return value;
            }
        }catch (Exception e){
            log.error("ESO cache error == "+key);
        }

        String PLAN_ID = (String)prop.get("PLAN_ID");
        if(StringUtil.isNotEmpty(PLAN_ID)){
            prop.put("PLAN_ID", StringUtil.getEscapeSqlInjectionString(PLAN_ID));
        }
        String PLAN_SQL = (String)prop.get("PLAN_SQL");
        if(StringUtil.isNotEmpty(PLAN_SQL)){
            prop.put("PLAN_SQL", StringUtil.getEscapeSqlInjectionString(PLAN_SQL));
        }
        return commonMapper.get1hourTimeProperties(prop);
    }

    public List<Object> get1hourTimePropertyList(Map<String, Object> prop) throws ProductException {


        String key = "get1hourTimePropertyList_"+prop.get("KEY").toString();
        try {
            List<Object> value = (List<Object>)objectCache.getObject(key);
            if(value == null){
                value = commonMapper.get1hourTimePropertyList(prop);
                objectCache.setObject(key, value);
                return value;
            }
        }catch (Exception e){
            log.error("ESO cache error == "+key);
        }

        try {
            return commonMapper.get1hourTimePropertyList(prop);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ProductException("get1hourTimePropertyList Error", e);
        }
    }

    public HelpPopVO getHelpPopCont(String code){

        String key = "getHelpPopCont_"+code;
        try {
            HelpPopVO value = (HelpPopVO)objectCache.getObject(key);
            if(value == null){
                value = commonMapper.getHelpPopCont(code);
                objectCache.setObject(key, value);
                return value;
            }
        }catch (Exception e){
            log.error("ESO cache error == "+key);
        }

        return commonMapper.getHelpPopCont(code);
    }

    public List<HelpPopVO> getHelpPopContList(List<String> codeList){
        return commonMapper.getHelpPopContList(codeList);
    }

    /*
    public int updatePropertyUpdateDt(String key) throws ProductException {
        int rows;
        try {
            CommonDAO dao = (CommonDAO) DAOFactory.createDAO(CommonDAOImpl.class);
            dao.setSqlMap(SqlMapLoader.getInstance());
            rows = dao.updatePropertyUpdateDt(key);
        } catch (Exception e) {
            throw new ProductException(e);
        }
        return rows;
    }

    public String getPropertyUpdateDt(String key) throws ProductException {
        String result = null;
        try {
            CommonDAO dao = (CommonDAO) DAOFactory.createDAO(CommonDAOImpl.class);
            dao.setSqlMap(SqlMapLoader.getInstance());
            result = dao.getPropertyUpdateDt(key);
        } catch (Exception e) {
            throw new ProductException(e);
        }
        return result;
    }
    */
    /**
     * 직방 xsite / partner_cd쿠키 리스트 조회
     * @return
     * @throws ProductException
     */
    /*
    public List<String> getDirectVisitCookieList(String daoMapId) throws ProductException {
        List<String> result = null;
        try {
            CommonDAO dao = (CommonDAO) DAOFactory.createDAO(CommonDAOImpl.class);
            dao.setSqlMap(SqlMapLoader.getInstance());
            result = dao.getDirectVisitCookieList(daoMapId);
        } catch (Exception e) {
            throw new ProductException(e);
        }
        return result;
    }

    public List<HashMap> getMobileDirectVisitCookieList(String daoMapId) throws ProductException {
        List<HashMap> result = null;
        try {
            CommonDAO dao = (CommonDAO) DAOFactory.createDAO(CommonDAOImpl.class);
            dao.setSqlMap(SqlMapLoader.getInstance());
            result = dao.getMobileDirectVisitCookieList(daoMapId);
        } catch (Exception e) {
            throw new ProductException(e);
        }
        return result;
    }
    */


    public SyAppMngVO getValidAppList(SyAppMngVO syAppMngVO) throws TerroirException{
        if ( syAppMngVO == null ) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("syAppList", syAppMngVO.getSyAppList());
        map.put("useCache",false);
        syAppMngVO.setValidAppList(commonMapper.getValidAppListMap(map));

        return syAppMngVO;
    }

    public SyAppMngVO getValidApp(String appId) throws TerroirException{
        if(StringUtil.isEmpty(appId)) return null;

        SyAppMngVO syAppMngVO = new SyAppMngVO();
        syAppMngVO.setAppId(appId);

        return commonMapper.getValidAppId(syAppMngVO);
    }

    public boolean isUseValidApp(String appId) throws TerroirException{
        return this.isUseValidApp(appId, true);
    }

    public boolean isUseValidApp(String appId, boolean isCache) throws TerroirException{
        if(StringUtil.isEmpty(appId)) return false;
        SyAppMngVO syAppMngVO = null;
        if(isCache){
            syAppMngVO = (SyAppMngVO)objectCache.getObject(ProductConstDef.ESO_DB_SWITCH_PREFIX+appId);
        }
        if(syAppMngVO == null){
            syAppMngVO = this.getValidApp(appId);
            if(objectCache != null){
                objectCache.setObject(ProductConstDef.ESO_DB_SWITCH_PREFIX+appId, syAppMngVO);
            }
        }
        if(syAppMngVO != null && appId.equals(syAppMngVO.getAppId())){
            return true;
        }else{
            return false;
        }
    }

    public List<SyCoDetailVO> getCodeDetail(String grpCd) throws TerroirException{
        SyCoDetailVO syCoDetailVO = new SyCoDetailVO();
        syCoDetailVO.setGrpCd(grpCd);
        return this.getCodeDetail(syCoDetailVO);
    }

    public List<SyCoDetailVO> getCodeDetail(SyCoDetailVO syCoDetailVO) throws TerroirException{
        if ( syCoDetailVO == null ) {
            return null;
        }
        return commonMapper.getCodeDetail(syCoDetailVO);
    }

    public SyCoDetailVO getCodeDetailName(SyCoDetailVO syCoDetailVO) {
        if(syCoDetailVO != null)
            return commonMapper.getCodeDetailName(syCoDetailVO);
        else
            return null;
    }

    public boolean isCodeDetail(String grpCd, String dtlsCd) {
        if(StringUtil.isEmpty(grpCd)){
            return false;
        }else{
            SyCoDetailVO syCoDetailVO = getCodeDetail(grpCd, dtlsCd);
            if(syCoDetailVO != null){
                return true;
            }
        }
        return false;
    }

    public SyCoDetailVO getCodeDetail(String grpCd, String dtlsCd) throws TerroirException{
        SyCoDetailVO syCoDetailVO = new SyCoDetailVO();
        syCoDetailVO.setGrpCd(grpCd);
        List<SyCoDetailVO> syCoDetailVOList = this.getCodeDetail(syCoDetailVO);

        if (syCoDetailVOList == null || syCoDetailVOList.size() == 0) {
            return null;
        }
        SyCoDetailVO syCoDetail = null;
        for (SyCoDetailVO syCoDtl : syCoDetailVOList) {
            if (dtlsCd.equals(syCoDtl.getDtlsCd())){
                syCoDetail = syCoDtl;
                break;
            }
        }
        return syCoDetail;
    }

    public String replaceSpecialChar(String str){
        String inputLimitedSpecialCharacters = this.getPropertyKeyValue(CacheKeyConstDef.PD_INPUT_LIMITED_SPECIAL_CHARACTERS);
        if(inputLimitedSpecialCharacters == null || inputLimitedSpecialCharacters.equals("")) return str;

        if(str != null){
            for (int i = 0; i < str.length(); i++) {
                if(inputLimitedSpecialCharacters.indexOf(str.charAt(i)) != -1) {
                    str = str.replaceAll("["+inputLimitedSpecialCharacters+"]", " ");
                    str = str.replaceAll(" {2,}", " ");
                    str = str.trim();
                    break;
                }
            }
        }

        return str;
    }

    public String get1hourTimeProperty(String key) {
        return this.getPropertyKeyValue(key);
    }
    public String getPropertyKeyValue(String key) {

        if (StringUtil.isEmpty(key)) {
            return null;
        }

        try {
            Map prop = new HashMap();
            prop.put("KEY", key);
            return this.get1hourTimeProperty(prop);
        } catch (Exception e) {
            log.error("getPropertyKeyValue 조회 실패",e);
        }

        return null;
    }
}
