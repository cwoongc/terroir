package com.elevenst.common.process;

/**
 * 등록 정보 컨버터
 *
 * 데이타 등록시에 데이터 중간 가공을 맡는 역할을 하는 클래스의 스펙을 본인터페이스에 정의한다.
 * 본 인터페이스를 확장하는 클래스는 void convertRegInfo(T vo1, T vo2) 구현하여
 * 입력된 vo를 데이타 가공 완료된 vo로 반환해야 한다.
 *
 * void convertRegInfo(T vo1, T vo2)는 주로 front로 부터의 입력값 확인, backend로 넘길 데이터 가공/설정 및 validate 클래스에 검증을 위임하는 로직을 담당한다.
 * 데이타 일관성을 위해 트랜잭션이 관리되는 클래스가 구현하는 것이 좋다. (service)
 *
 * @param <T> 데이타를 가공할 값객체 클래스
 */
public interface RegistrationInfoConverter<T> {
    void convertRegInfo(T vo1, T vo2);
}
