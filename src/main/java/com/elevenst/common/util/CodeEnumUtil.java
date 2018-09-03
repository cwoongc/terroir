package com.elevenst.common.util;

import com.elevenst.exception.TerroirException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * 사용자 정의 Code Enum을 활용하기 위한 Util.
 *
 * @author  wcchoi
 */
@Slf4j
public class CodeEnumUtil {

    /**
     * 사용자 정의 Enum에 정의되는 Field 명들을 선한하는 Enum이다.
     */
    public enum FieldName {
        code, codeName, groupCode, groupCodeName, type, typeName
    }

    /**
     * 사용자 정의 Enum의 특정 Field 값을 매개변수로 넘겨, 필드값에 맵핑되는 Enum instance를 반환하는 메소드.
     * 필드값이 중복되게 선언되어있다면 ordinal 순서대로 첫번째로 같은 필드값을 가지는 Enum instance를 반환한다
     *
     * @param clazz 사용자정의 Enum의 class 객체
     * @param fieldName 사용자정의 Enum에 선언된 사용자정의 Field 명
     * @param fieldValue 해당 Field에 저장된 갑. 특정 Enum 엘리먼트와 맵핑된다
     * @return 필드값과 맵핑된 Enum Instance
     * @throws java.lang.NoSuchFieldException 기술한 Field가 해당 enum에 존재하지 않을시 발생
     * @throws java.lang.IllegalAccessException 기술한 Field에 접근권한이 없을시 발생
     */
    public static <T extends Enum> T getEnumOfFieldValue(Class<T> clazz, FieldName fieldName, String fieldValue)
    {


        T value = null;

        try {
            for (Object o : clazz.getEnumConstants()) {
                T t = (T) o;
                Field field = t.getClass().getDeclaredField(fieldName.toString());
                field.setAccessible(true);
                if (((String) field.get(t)).equals(fieldValue)) {
                    value = t;
                    break;
                }
            }
        } catch (NoSuchFieldException ex) {
            EnumUtilException threx = new EnumUtilException(String.format(EnumUtilExceptionMessage.FIELD_NOT_EXISTS,clazz.getSimpleName(),fieldName), ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;

        } catch (IllegalAccessException ex) {
            EnumUtilException threx = new EnumUtilException(String.format(EnumUtilExceptionMessage.ILLEGAL_FIELD_ACCESS,clazz.getSimpleName(),fieldName), ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;

        }
        return value;
    }


    /**
     * code, codeName 필드를 가진 사용자 정의 enum에서 code필드 값을 통해 codeName 필드값을 알아온다.
     *     *
     * @param clazz 사용자정의 Enum의 class 객체
     * @param code 코드
     * @return 코드명
     * @throws java.lang.NoSuchFieldException code, codeName 필드가 없는 enum에 사용했을 경우 발생
     * @throws java.lang.IllegalAccessException code, codeName 필드에 접근 권한이 없는 enum에 사용했을 경우 발생
     * @author wcchoi
    */
    public static <T extends Enum> String getCodeName(Class<T> clazz, String code) {
        String codeName = null;
        int step = 0;
        try {

            for (Object o : clazz.getEnumConstants()) {
                T t = (T) o;

                step = 1;

                Field codeField = t.getClass().getDeclaredField(FieldName.code.toString());
                codeField.setAccessible(true);

                if (((String) codeField.get(t)).equals(code)) {

                    step = 2;

                    Field codeNameField = t.getClass().getField(FieldName.codeName.toString());
                    codeNameField.setAccessible(true);
                    codeName = (String) codeNameField.get(t);
                    break;
                }
            }
        } catch (NoSuchFieldException ex) {
            EnumUtilException threx = new EnumUtilException(String.format(EnumUtilExceptionMessage.FIELD_NOT_EXISTS,clazz.getSimpleName(), step==1 ? FieldName.code.toString() : FieldName.codeName.toString()), ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;

        } catch (IllegalAccessException ex) {
            EnumUtilException threx = new EnumUtilException(String.format(EnumUtilExceptionMessage.ILLEGAL_FIELD_ACCESS,clazz.getSimpleName(), step==1 ? FieldName.code.toString() : FieldName.codeName.toString()), ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;

        }
        return codeName;
    }


    /**
     * type 필드를 가진 사용자 정의 enum에서 code필드 값이 본 메서드의 code 매개변수와 동일한 enum이 type필드 값을 매개변수 type 값과 동일한지 여부를 확인한다.
     *
     * @param clazz 사용자정의 Enum의 class 객체
     * @param code 코드
     * @param type 의심 타입
     * @return 코드의 의심 타입 여부
     * @throws java.lang.NoSuchFieldException code, type 필드가 없는 enum에 사용했을 경우 발생
     * @throws java.lang.IllegalAccessException code, type 필드에 접근 권한이 없는 enum에 사용했을 경우 발생
     * @author wcchoi
     */
    public static <T extends Enum> boolean isTypeOf(Class<T> clazz, String code, String type) {
        boolean isThatType = false;
        String rightType = null;
        int step = 0;
        try {

            for (Object o : clazz.getEnumConstants()) {
                T t = (T) o;

                step = 1;

                Field codeField = t.getClass().getDeclaredField(FieldName.code.toString());
                codeField.setAccessible(true);

                if (((String) codeField.get(t)).equals(code)) {

                    step = 2;

                    Field typeField = t.getClass().getField(FieldName.type.toString());
                    typeField.setAccessible(true);
                    rightType = (String) typeField.get(t);

                    if(type.equals(rightType))
                        isThatType = true;

                    break;
                }
            }
        } catch (NoSuchFieldException ex) {
            EnumUtilException threx = new EnumUtilException(String.format(EnumUtilExceptionMessage.FIELD_NOT_EXISTS,clazz.getSimpleName(), step==1 ? FieldName.code.toString() : FieldName.type.toString()), ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;

        } catch (IllegalAccessException ex) {
            EnumUtilException threx = new EnumUtilException(String.format(EnumUtilExceptionMessage.ILLEGAL_FIELD_ACCESS,clazz.getSimpleName(), step==1 ? FieldName.code.toString() : FieldName.type.toString()), ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;

        }
        return isThatType;

    }



    private static class EnumUtilException extends RuntimeException {
        private static final String MSG_PREFIX = "[" + TerroirException.class.getName().replace("Exception","") + "]";

        public EnumUtilException(String msg) { super(MSG_PREFIX + msg);}

        public EnumUtilException(Throwable cause) { super(MSG_PREFIX, cause); }


        public EnumUtilException(String message, Throwable exception) {
            super(MSG_PREFIX + message, exception);
        }


    }

    private class EnumUtilExceptionMessage {
        public static final String FIELD_NOT_EXISTS = "해당 Enum '%s'에는 '%s'라는 이름을 가진 Field가 없습니다.";
        public static final String ILLEGAL_FIELD_ACCESS = "해당 Enum '%s'의 Field '%s'에 접근할 권한이 없습니다.";
    }




}
