package com.elevenst.terroir.product.registration.common.mapper;

import com.elevenst.common.vo.SyAppMngVO;
import com.elevenst.terroir.product.registration.common.vo.HelpPopVO;
import com.elevenst.terroir.product.registration.common.vo.SyCoDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface CommonMapper {
    String get1hourTimeProperties(Map map);
    String getRealTimeProperties(Map map);
    List get1hourTimePropertyList(Map map);
    List<HashMap<String, Object>> getValidAppListMap(Map map);
    HashMap<String, Object> getValidAppMap(SyAppMngVO syAppMngVO);
    List<SyCoDetailVO> getCodeDetail(SyCoDetailVO syCoDetailVO);
    SyCoDetailVO getCodeDetailName(SyCoDetailVO syCoDetailVO);
    HelpPopVO getHelpPopCont(String code);
    List<HelpPopVO> getHelpPopContList(List<String> codeList);
    SyAppMngVO getValidAppId(SyAppMngVO syAppMngVO);
}
