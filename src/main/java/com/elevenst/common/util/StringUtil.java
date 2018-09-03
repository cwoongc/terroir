package com.elevenst.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    private static final String maskFormat = "******************************";

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }

    /**
     * 문자배열 공백검사
     * @param arr
     * @return
     */
    public static boolean isEmpty(String[] arr) {
        return (arr == null || arr.length < 1);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    public static boolean isNotEmpty(String[] str) { return !isEmpty(str); }
    public static boolean isNotEmpty(List lst) { return !isEmpty(lst); }

    public static String nvl(Object obj) {
        return nvl(obj, "");
    }

    public static String nvl(Object obj, String ifNull) {
        return obj != null ? obj.toString() : ifNull;
    }

    public static String nvl(String str) {
        return nvl(str, "");
    }

    public static String nvl(String str, String ifNull) {
        return str == null || str.equals("") ? ifNull : str;
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public static boolean existsCharactersForArr(String[] str, String pattern) {
        int fasleCnt= 0;
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

        int strCnt = 0;
        if(str != null && str.length > 0) {
            strCnt = str.length;
            for(int i=0; i<strCnt; i++) {
                if(str[i] != null) {
                    Matcher m = p.matcher(str[i]);
                    if(m.find()) fasleCnt++;
                }
            }
        }
        if(fasleCnt > 0) return true;
        else return false;
    }

    public static String getEscapeSqlInjectionString(String inputStr){

        String[] specialCharacter = {"'", "\"", "=", "<", ">", "\\(", "\\)", "--", "#", ";"};
        String[] normalCharacter = {"sp_", "xp_", "select", "drop", "union","cmdshell" };
        if(null == inputStr){
            return null;
        }
        String retStr = inputStr;
        StringBuilder sb = new StringBuilder();

        sb.append("[EscapeSqlInjection] ");

        for(int i = 0 ; i < specialCharacter.length; i ++ ){
            String extStr = retStr.replaceAll(specialCharacter[i], "");
//    		retStr = retStr.replaceAll(specialCharacter[i], "");
            if(!retStr.equals(extStr)){
                sb.append("\n[금지문자 : ").append(specialCharacter[i]).append("]").append("(origin)").append(retStr).append(", (clean)").append(extStr);
            }
            retStr = extStr;
        }
        for(int j = 0 ; j < normalCharacter.length ; j ++ ){
            String regx = "(?i)" + normalCharacter[j];
            if("sp_".equals(normalCharacter[j]) ) {

                if(retStr.toUpperCase().indexOf("DISP_") > -1){
                    continue;
                }else{
                    String extStr = retStr.replaceAll(regx, "");
                    if(!retStr.equals(extStr)){
                        sb.append("\n[금지문자 : ").append(normalCharacter[j]).append("]").append("(origin)").append(retStr).append(", (clean)").append(extStr);
                    }
                    retStr = extStr;
                }
            }else{
                String extStr = retStr.replaceAll(regx, "");
                if(!retStr.equals(extStr)){
                    sb.append("\n[금지문자 : ").append(normalCharacter[j]).append("]").append("(origin)").append(retStr).append(", (clean)").append(extStr);
                }
                retStr = extStr;
            }
        }

        if(sb.length() > 25){
            Thread thisThread = Thread.currentThread();
            StackTraceElement[] ste = thisThread.getStackTrace();
            sb.append("\n");
            for(int i = 0 ; i < ste.length ; i ++){
                sb.append(ste[i]).append("\n");
            }
        }

        return retStr;
    }

    public static String[] split(String str) {
        return split(str, (String)null, -1);
    }

    public static String[] split(String text, String separator) {
        return split(text, separator, -1);
    }

    public static String[] split(String str, String separator, int max) {
        StringTokenizer tok = null;
        if (separator == null) {
            tok = new StringTokenizer(str);
        } else {
            tok = new StringTokenizer(str, separator);
        }

        int listSize = tok.countTokens();
        if (max > 0 && listSize > max) {
            listSize = max;
        }

        String[] list = new String[listSize];
        int i = 0;

        for(int lastTokenEnd = 0; tok.hasMoreTokens(); ++i) {
            int lastTokenBegin;
            if (max > 0 && i == listSize - 1) {
                String endToken = tok.nextToken();
                lastTokenBegin = str.indexOf(endToken, lastTokenEnd);
                list[i] = str.substring(lastTokenBegin);
                break;
            }

            list[i] = tok.nextToken();
            lastTokenBegin = str.indexOf(list[i], lastTokenEnd);
            lastTokenEnd = lastTokenBegin + list[i].length();
        }

        return list;
    }

    public static HashMap<String, String> split(String paramVal, String del1, String del2) {
        HashMap<String, String> result = new HashMap();
        if (paramVal != null && !"".equals(paramVal)) {
            StringTokenizer tokenizer = new StringTokenizer(paramVal, del1);

            while(tokenizer.hasMoreTokens()) {
                String str = tokenizer.nextToken();
                int keyIndex = str.indexOf(del2);
                if (keyIndex != -1) {
                    String key = str.substring(0, keyIndex);
                    if (key != null) {
                        key = key.trim();
                        if (!result.containsKey(key)) {
                            String strVal = str.substring(keyIndex + 1, str.length());
                            strVal = strVal.replaceAll("%20", " ");
                            strVal = strVal.replaceAll("%3D", "=");
                            strVal = strVal.replaceAll("%25", "%");
                            strVal = strVal.replaceAll("%26", "&");
                            result.put(key, strVal);
                        }
                    }
                }
            }

            return result;
        } else {
            return result;
        }
    }

    /**
     * 특정문자열에서 비교대상문자열(sub)이 매치되는 횟수를 리턴한다. 문자열이 null이거나 empty("")인 경우 0을 리턴한다.
     *
     * <pre>
     *
     *
     *    String2.countMatches(null, *)       = 0
     *    String2.countMatches(&quot;&quot;, *)         = 0
     *    String2.countMatches(&quot;abba&quot;, null)  = 0
     *    String2.countMatches(&quot;abba&quot;, &quot;&quot;)    = 0
     *    String2.countMatches(&quot;abba&quot;, &quot;a&quot;)   = 2
     *    String2.countMatches(&quot;abba&quot;, &quot;ab&quot;)  = 1
     *    String2.countMatches(&quot;abba&quot;, &quot;xxx&quot;) = 0
     *
     *
     * </pre>
     *
     * @param str
     * @param sub
     * @return int
     */
    public static int countMatches(String str, String sub) {
        if (str == null || str.length() == 0 || sub == null || sub.length() == 0) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    /**
     * 특정 문자열의 가장 왼쪽에서 부터의 n개의 문자열을 리턴한다. 문자열이 null인 경우 null을 리턴한다. length가 부적절한 수이거나, 문자열이 empty("")인 경우 ""을 리턴한다.
     *
     * <pre>
     *    String2.left(null, *)    = null
     *    String2.left(*, -ve)     = &quot;&quot;
     *    String2.left(&quot;&quot;, *)      = &quot;&quot;
     *    String2.left(&quot;abc&quot;, 0)   = &quot;&quot;
     *    String2.left(&quot;abc&quot;, 2)   = &quot;ab&quot;
     *    String2.left(&quot;abc&quot;, 4)   = &quot;abc&quot;
     * </pre>
     *
     * @param str
     * @param len
     * @return String
     */
    public static String left(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return "";
        }
        if (str.length() <= len) {
            return str;
        } else {
            return str.substring(0, len);
        }
    }

    /**
     * 3자리마다 콤마 찍어주는 함수
     *
     * @author bluepoet
     * @param num
     * @return String
     */
    public static String getCommaString(String num) {
        return getCommaStringForThree(num);
    }

    public static String getCommaStringForThree(String value) {
        boolean isFloat = false;
        boolean isNumber = true;
        if (isNumber(value)) {
            try {
                Long.parseLong(value);
                isFloat = false;
            } catch (NumberFormatException var6) {
                try {
                    Double.parseDouble(value);
                    isFloat = true;
                } catch (NumberFormatException var5) {
                    isFloat = false;
                    isNumber = false;
                }
            }

            if (isNumber) {
                return isFloat ? formatNumber(Double.parseDouble(value), "###,###,###,###,##0.###") : formatNumber(Long.parseLong(value), "###,###,###,###,##0");
            } else {
                return value;
            }
        } else {
            return value;
        }
    }

    public static String formatNumber(double value, String format) {
        if (Double.isNaN(value)) {
            return "0";
        } else {
            DecimalFormat formatValue = new DecimalFormat(format);
            return formatValue.format(value);
        }
    }

    public static boolean isNumber(String str) {
        if (str != null && str.length() != 0) {
            char[] chars = str.toCharArray();
            int sz = chars.length;
            boolean hasExp = false;
            boolean hasDecPoint = false;
            boolean allowSigns = false;
            boolean foundDigit = false;
            int start = chars[0] == '-' ? 1 : 0;
            int i;
            if (sz > start + 1 && chars[start] == '0' && chars[start + 1] == 'x') {
                i = start + 2;
                if (i == sz) {
                    return false;
                } else {
                    while(i < chars.length) {
                        if ((chars[i] < '0' || chars[i] > '9') && (chars[i] < 'a' || chars[i] > 'f') && (chars[i] < 'A' || chars[i] > 'F')) {
                            return false;
                        }

                        ++i;
                    }

                    return true;
                }
            } else {
                --sz;

                for(i = start; i < sz || i < sz + 1 && allowSigns && !foundDigit; ++i) {
                    if (chars[i] >= '0' && chars[i] <= '9') {
                        foundDigit = true;
                        allowSigns = false;
                    } else if (chars[i] == '.') {
                        if (hasDecPoint || hasExp) {
                            return false;
                        }

                        hasDecPoint = true;
                    } else if (chars[i] != 'e' && chars[i] != 'E') {
                        if (chars[i] != '+' && chars[i] != '-') {
                            return false;
                        }

                        if (!allowSigns) {
                            return false;
                        }

                        allowSigns = false;
                        foundDigit = false;
                    } else {
                        if (hasExp) {
                            return false;
                        }

                        if (!foundDigit) {
                            return false;
                        }

                        hasExp = true;
                        allowSigns = true;
                    }
                }

                if (i < chars.length) {
                    if (chars[i] >= '0' && chars[i] <= '9') {
                        return true;
                    }

                    if (chars[i] == 'e' || chars[i] == 'E') {
                        return false;
                    }

                    if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
                        return foundDigit;
                    }

                    if (chars[i] == 'l' || chars[i] == 'L') {
                        return foundDigit && !hasExp;
                    }
                }

                return !allowSigns && foundDigit;
            }
        } else {
            return false;
        }
    }

    /**
     * 숫자 스트링에서 콤마(,) 부호(+)를 제거한다.
     * @param s
     * @return
     */
    public static String numberFilter(String s) {
        String str = s;
        if (str != null) {
            str = str.replace(",", "");
            str = str.replace("+", "");
        }
        return str;
    }

    /**
     * int 숫자변환
     * @param str
     * @return
     */
    public static int parseInt(String str) {
        try {
            str = str.replaceAll("[^0-9]", "");
            str = numberFilter(str);
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            //System.out.println(e.toString());
            return 0;
        }
    }

    /**
     * long 숫자변환
     * @param str
     * @return
     */
    public static long parseLong(String str) {
        try {
            str = numberFilter(str);
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            //System.out.println(e.toString());
            return 0L;
        }
    }

    /**
     * long Type으로 변환
     */
    public static long getLong(String sField) {
        long sData=0;
        try {
            sData=Long.parseLong(sField.replaceAll(",", ""));
        } catch (Exception e){}
        return sData;
    }

    /**
     * double Type으로 변환
     */
    public static double getDouble(String sField) {
        double sData=0.0;
        try {
            sData=Double.parseDouble(sField);
        } catch (Exception e){}
        return sData;
    }

    /**
     *
     * 지정한 정수의 개수 만큼 빈칸(" ")을 스트링을 구한다. (스페이스제외)
     * 절단된 String의 바이트 수가 자를 바이트 개수를 넘지 않도록 한다.
     *
     * @param str 원본 String
     * @param length 자를 바이트 개수
     * @return String 절단된 String
     */
    public static String cutInStringByBytesNoneSpace(String str, int length) {
        byte[] bytes = str.getBytes();
        int len = bytes.length;
        int counter = 0;
        if (length >= len) {
            return str;
        }
        for (int i = length - 1; i >= 0; i--) {
            if (((int) bytes[i] & 0x80) != 0)
                counter++;
        }
        return new String(bytes, 0, length - (counter % 2));
    }

    /**
     * 객체 필드들 print
     * */
    public static <T> String printFieldsToString(T param) {

        StringBuilder result = new StringBuilder();
        String newLine = "\n";

        result.append(param.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = param.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                field.setAccessible(true);
                result.append( field.get(param) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();

    }

    public enum MaskingTypeEnum {
        ID, EMAIL, NM, TEL, SELNM, IP, ACCOUNT_NUM, CARD_NUM, IDPLUSNM
    }

    public static String replaceGroup(String regex, String source, Map<Integer, String> keyMapper) {
        Matcher m = Pattern.compile(regex).matcher(source);
        StringBuilder builder = new StringBuilder(source);
        if (!m.find()) {
            return source; // pattern not met, may also throw an exception here
        }

        if (keyMapper != null && !keyMapper.isEmpty()) {
            for(Map.Entry<Integer, String> entry: keyMapper.entrySet()){
                builder.replace(m.start(entry.getKey()), m.end(entry.getKey()), entry.getValue());
            }
        }
        return builder.toString();
    }

    public static String getConvertMemId(String memId, int unMskLength){
        String cvntMemId = "";
        String mskStr = "************************************************************";

        if (StringUtil.isEmpty(memId)) return cvntMemId;
        if (memId.length() <= unMskLength) return memId;

        cvntMemId = memId.substring(0, unMskLength) + mskStr.substring(0, memId.length() - unMskLength);

        return cvntMemId;
    }

    /**
     * 일반적인 회원ID 마스킹 (이메일 회원 포함)
     * @param memId
     * @return
     */
    public static String getMemIdMaskCommon(String memId) {
        int unMskLength = 3;

        if (StringUtil.isEmpty(memId)) return "";

        String[] arrMemId = memId.split("@", 2);
        int idLength = arrMemId[0].length();

        if (idLength <= unMskLength) {
            unMskLength = idLength - 1;
        }

        String returnId = getConvertMemId(arrMemId[0], idLength - unMskLength);

        if ( arrMemId.length > 1 ) {
            returnId = returnId + "@" + arrMemId[1];
        }

        return returnId;
    }

    /**
     * 11번가 마스킹 정책
     *
     * -. 아이디 (ID) : moss*** (뒷자리 3자리 마스킹)
     * -. 이메일 아이디 (ID) : moss***@nate.com (아이디 부분 뒷자리 3자리 마스킹, 3글자 이하일 경우 2글자 마스킹)
     * -. 이름 (NM) : 김*원 (가운데 이름 마스킹)
     * -. 집전화 (TEL) : 070-***-3433 (가운데 전화번호 마스킹)
     * -. 휴대폰 (TEL) : 010-****-1234 (가운데 전화번호 마스킹)
     * -. 이메일 (EMAIL) : ddw***@nate.com (Id의 뒤 3자리 마스킹, Id가 3글자 이하일 경우 2글자 마스킹)
     * -. 판매자명 (SELNM) : 3자까지는 이름과 동일 (김*, 김동*), 4자 이상일 경우 뒷자리 기준 2번, 3번째 마스킹 (검**획, 검색**팀)
     * -. IP (IP) : 123.456.***.543 3번째 IP 부분만 마스킹 처리함
     * -. 카드번호 (CARD_NUM) : 1234-5***-****-**43 '-'가 있는 구조여야함.
     * -. 계좌번호 (ACCOUNT_NUM) : 1234567896*******1 '-'가 없는 구조여야함.
     * -. 그 외 : 전체 마스킹
     *
     * @author RahXephon
     * @param data
     * @param type
     * @return
     */
    public static String getMaskingData(String data, String type) {
        String masking = "";

        if ( StringUtils.isEmpty(data) ) {
            return masking;
        }
        if ( MaskingTypeEnum.ID.name().equals(type) || MaskingTypeEnum.EMAIL.name().equals(type) ) {
            masking = getMemIdMaskCommon(data);
        } else if ( MaskingTypeEnum.NM.name().equals(type) ) {
            if ( data.length() < 3 ) {
                masking = StringUtils.overlay(data, StringUtils.substring(maskFormat, 0, data.length()-1), 1, data.length());
            } else {
                masking = StringUtils.overlay(data, StringUtils.substring(maskFormat, 0, data.length()-2), 1, data.length()-1);
            }
        } else if ( MaskingTypeEnum.TEL.name().equals(type) ) {
            if ( StringUtils.indexOf(data, "-") < 0 ) {
                masking = StringUtils.overlay(data
                    , StringUtils.substring(maskFormat, 0, 4)
                    , data.length()-4
                    , data.length()
                );
            } else {
                masking = StringUtils.overlay(data
                    , StringUtils.substring(maskFormat, 0, StringUtils.lastIndexOf(data, "-") -1 - StringUtils.indexOf(data, "-"))
                    , StringUtils.indexOf(data, "-")+1
                    , StringUtils.lastIndexOf(data, "-")
                );
            }
        } else if ( MaskingTypeEnum.SELNM.name().equals(type) ) {
            if ( data.length() < 3 ) {
                masking = StringUtils.overlay(data, StringUtils.substring(maskFormat, 0, data.length()-1), 1, data.length());
            } else if ( data.length() == 3 ) {
                masking = StringUtils.overlay(data, StringUtils.substring(maskFormat, 0, data.length()-2), 1, data.length()-1);
            } else {
                masking = StringUtils.overlay(data, "**", data.length()-3, data.length()-1);
            }
        }else if ( MaskingTypeEnum.IP.name().equals(type) ) {

            String regex="([0-9]{0,3})\\.([0-9]{0,3})\\.([0-9]{0,3})\\.([0-9]{0,3})";

            try {
                Map<Integer, String> keyMapper=new HashMap<Integer, String>();
                keyMapper.put(3, "***");
                masking = replaceGroup(regex,data,keyMapper);
            } catch (Exception e) {
                Logger.getLogger(StringUtil.class).error(e.getMessage(), e);
            }

        }else if ( MaskingTypeEnum.ACCOUNT_NUM.name().equals(type) ) {

            String regex="[0-9]+([0-9]{7})[0-9]";

            try {
                Map<Integer, String> keyMapper=new HashMap<Integer, String>();
                keyMapper.put(1, "*******");
                masking = replaceGroup(regex,data,keyMapper);
            } catch (Exception e) {
                Logger.getLogger(StringUtil.class).error(e.getMessage(),e);
            }

        }else if ( MaskingTypeEnum.CARD_NUM.name().equals(type) ) {

            String regex="([0-9]{0,4})\\-[0-9]([0-9]{0,3})\\-([0-9]{0,4})\\-([0-9]{0,2})([0-9]{0,2})";

            try {
                Map<Integer, String> keyMapper=new HashMap<Integer, String>();
                keyMapper.put(2, "***");
                keyMapper.put(3, "****");
                keyMapper.put(4, "**");
                masking = replaceGroup(regex,data,keyMapper);
            } catch (Exception e) {
                Logger.getLogger(StringUtil.class).error(e.getMessage(),e);
            }

        } else if (MaskingTypeEnum.IDPLUSNM.name().equals(type)) {
            if (data.length() < 4) {
                masking = StringUtils.overlay(data, StringUtils.substring(maskFormat, 0, data.length() - 1), 1, data.length());
            } else {
                masking = StringUtils.overlay(data, "**", 1, 3);
                masking = StringUtils.overlay(masking, "**", masking.length() - 3, masking.length() - 1);
            }
        } else {
            masking = StringUtils.substring(maskFormat, 0, data.length());
        }

        return masking;
    }

    /**
     * hongkildong(홍길동) 과 같이 등록된 데이터를 마스킹 처리
     * @param dataMap
     * @param key
     */
    public static void setDataMasking(Map<String, Object> dataMap, String key) {
        if(dataMap.containsKey(key)) {
            String value = StringUtil.nvl(dataMap.get(key));

            if(StringUtil.isEmpty(value)) {
                return;
            }

            String result = null;

            if(value.indexOf("(") > -1 && value.indexOf(")") > -1) {
                result = getMaskingData(value.substring(0, value.indexOf("(")), MaskingTypeEnum.ID.name())
                    + "(" + getMaskingData(value.substring(value.indexOf("(") + 1, value.indexOf(")")), MaskingTypeEnum.NM.name()) + ")";
            } else {
                result = getMaskingData(value, MaskingTypeEnum.ID.name());
            }

            dataMap.put(key, result);
        }
    }

    public static String getMaskingDataExceptSBO(String data, String type) {
        if("SBO".equals(System.getProperty("bo.type", "BO"))) {
            return data;
        }

        return getMaskingData(data, type);
    }

    /** SR-50552 [보안] SOFFICE 상품정보 Q&A XSS 조치요청
     * 기존 로직을 사용하려 하였으나 오류가 존재하여 새로운 메소드를 추가함
     * xss방지용의 문자열의 <>{}&#를 변경처리함.
     * @param in
     * @return String
     */
    public static String toXssHtml2(String in) {

        StringBuffer rv = new StringBuffer();

        if (in == null)
        {
            return null;
        }

        for (int i = 0; i < in.length(); i++)
        {
            char chr = in.charAt(i);
            switch (chr)
            {
                case '#':
                    rv.append("&#35;");
                    break;
                case '&':
                    rv.append("&#38;");
                    break;
                case '(':
                    rv.append("&#40;");
                    break;
                case ')':
                    rv.append("&#41;");
                    break;
                case '<':
                    rv.append("&lt;");
                    break;
                case '>':
                    rv.append("&gt;");
                    break;
                default:
                    rv.append(chr);
            }
        }
        return rv.toString();
    }

    /**
     * Json 포맷으로 전달해야 할 문자열 변환
     */
    public static String convertJSONFormat(String src) {
        if (StringUtil.isEmpty(src))	return src;

        Pattern JSONFormat =	Pattern.compile("^\\[");
        String result = null;
        Matcher m;

        m = JSONFormat.matcher(src);
        result =	m.replaceAll("@#@[");

        return result;
    }

    public static String setFullDateString(String src, boolean isStartDt){
        if (StringUtil.isEmpty(src))	return src;
        String result = null;
        if(isStartDt){

            switch (src.length()){
                case 8:
                    result = src+"000000";
                    break;
                case 10:
                    result = src+"0000";
                    break;
                case 12:
                    result = src+"00";
                    break;
                default:
                    result = StringUtil.left(src,8)+"000000";
                    break;
            }

        }else{

            switch (src.length()){
                case 8:
                    result = src+"235959";
                    break;
                case 10:
                    result = src+"5959";
                    break;
                case 12:
                    result = src+"59";
                    break;
                default:
                    result = StringUtil.left(src,8)+"235959";
                    break;
            }
        }

        return result;
    }

    public static boolean existsCharacters(String str, String pattern) {
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static boolean isContains(String[] arr, String str){
        boolean isExit = false;

        try{
            for(String s : arr){
                if(s.equals(str)){
                    isExit = true;
                    break;
                }
            }
        }catch (Exception e){
            isExit = false;
        }
        return isExit;
    }
}
