package com.elevenst.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 *  그룹코드 유틸리티
 *      - 그룹코드와 관련된 GroupCodeInterface 를 통해,
 *        해당 GroupCodeInterface를 상속받은 모든 enum class들과 관련된 helper method는 여기서 제작합니다.
 *  usage)
 *      - List tmp = GroupCodeUtil.getEntireDtlsCdList(PD065_PrdCertTypeCd.class)
 */
public abstract class GroupCodeUtil {

    public static <E extends Enum<E> & GroupCodeInterface> boolean isContainsDtlsCd(String dtlsCd, Class<E> enumClass) {
        for (E elem : enumClass.getEnumConstants()) {
            if (StringUtils.equals(dtlsCd, elem.getDtlsCd())) {
                return true;
            }
        }
        return false;
    }

    public static <E extends Enum<E> & GroupCodeInterface> boolean isContainsDtlsCd(String dtlsCd, EnumSet<E> enumSet) {
        for (GroupCodeInterface elem : enumSet) {
            if (StringUtils.equals(elem.getDtlsCd(), dtlsCd)) {
                return true;
            }
        }
        return false;
    }

    public static <E extends Enum<E> & GroupCodeInterface> boolean isContainsDtlsCd(String dtlsCd, E... enumList) {
        for (GroupCodeInterface elem : enumList) {
            if (StringUtils.equals(elem.getDtlsCd(), dtlsCd)) {
                return true;
            }
        }
        return false;

    }

    public static <E extends Enum<E> & GroupCodeInterface> List<String> getEntireDtlsCdList(Class<E> enumClass) {

        List<String> result = new ArrayList<>();
        for (E elem : enumClass.getEnumConstants()) {
            result.add(elem.getDtlsCd());
        }

        return result;
    }

    /**
     * 그룹코드 enum값 리턴 메소드
     * 출력 예시는 다음과 같음
     * {
     * "grpCd" : "PD019"
     * ,"grpCdNm" : "상품상태코드"
     * ,"dtls" : [
     * {"dtlsCd":"01", "dtlsComNm":"새상품"}
     * ,{"dtlsCd":"02", "dtlsComNm":"중고상품"}
     * ]
     * }
     * */
    public static <E extends Enum<E> & GroupCodeInterface> JSONObject toJsonObj(Class<E> enumClass) {
        E[] enumList = enumClass.getEnumConstants();

        JSONObject resultJson = new JSONObject();
        resultJson.put("grpCd", enumList[0].getGrpCd());
        resultJson.put("grpCdNm", enumList[0].getGrpCdNm());

        JSONArray elements = new JSONArray();
        for(E elem : enumClass.getEnumConstants()) {
            JSONObject jsonElem = new JSONObject();
            jsonElem.put("dtlsCd", elem.getDtlsCd());
            jsonElem.put("dtlsComNm", elem.getDtlsComNm());
            elements.add(jsonElem);
        }

        resultJson.put("dtls", elements);

        return resultJson;
    }
    /**
     * toJsonObj 스트링타입 리턴을 위함.(일종의 오버로딩 비스무리한 메소드.)
     * */
    public static <E extends Enum<E> & GroupCodeInterface> String toJsonTypeString(Class<E> enumClass) {
        return toJsonObj(enumClass).toString();
    }


    public static boolean equalsDtlsCd(String dtlsCd, GroupCodeInterface enumElem) {
        return StringUtils.equals(enumElem.getDtlsCd(), dtlsCd);
    }
    public static boolean notEqualsDtlsCd(String dtlsCd, GroupCodeInterface enumElem) {
        return !equalsDtlsCd(dtlsCd, enumElem);
    }

    public static <E extends Enum<E> & GroupCodeInterface> E fromString(Class<E> enumClass, String dtlsCd) {
        for(E elem : enumClass.getEnumConstants()){
            if(StringUtils.equals(dtlsCd, elem.getDtlsCd())) {
                return elem;
            }
        }
        return null;
    }
}
