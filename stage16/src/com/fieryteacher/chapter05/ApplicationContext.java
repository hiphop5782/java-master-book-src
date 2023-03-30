package com.fieryteacher.chapter05;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class ApplicationContext {
	// singleton 인스턴스 저장소
	private Map<Class<?>, Object> container = Collections.synchronizedMap(new HashMap<>());

	public ApplicationContext() {
		try {
			scan();
			injection();
		}
		catch(Exception e) {
			System.err.println("패키지 스캔 오류");
			e.printStackTrace();
		}
	}
	
	// 초기 탐색
	public void scan() throws Exception {
		Set<Class<?>> classes = PackageScanner.scanAllPackage();
		
		//POJO 애노테이션이 붙은 클래스를 찾아 옵션에 따라 다음과 같이 처리
		//- singleton=true일 경우 인스턴스를 생성하여 singletonContainer에 등록
		//- singleton=false일 경우 클래스 정보만 prototypeContainer에 등록
		classes.stream().filter(cls->cls.getDeclaredAnnotation(Registration.class) != null)
						.forEach(cls->{
							try {
									container.put(cls, cls.getDeclaredConstructor().newInstance());
							}
							catch(Exception e) {
								//예외 처리 생략
							}
						});
	}
	
	//의존성 주입(DI) 처리
	//- singleton container를 조사하여 @Required 애노테이션이 붙은 필드를 찾는다.
	//- 해당 필드가 singleton container에 존재한다면 필드의 접근을 해제하고 주입 설정한다.
	public void injection() {
		container.forEach((cls, obj)->{//컨테이너에 등록된 모든 클래스를 반복
			Arrays.stream(cls.getDeclaredFields())//모든 필드를 추출하여 반복
				.forEach(field->{
					boolean hasRequired = Arrays.stream(field.getDeclaredAnnotations())//필드의 애노테이션을 추출하여 반복
														.anyMatch(annotation->(annotation instanceof Required));//Required 애노테이션이 있는지 확인
					if(hasRequired) {//Required가 존재한다면
						try {
							field.setAccessible(true);//필드 접근제한 해제
							field.set(obj, getInstance(field.getType()));//필드에 인스턴스 주입(DI, Dependency Injection)
						}
						catch(Exception e) {
							e.printStackTrace();
						}
					}
				});
		});
	}
	
	//인스턴스 반환 메소드
	public <T>T getInstance(Class<T> cls) {
		if(container.containsKey(cls))//일치하는 항목이 있다면
			return cls.cast(container.get(cls));//바로 반환
		
		for(Class<?> clz : container.keySet()) {
			if(cls.isAssignableFrom(clz)) {//cls가 clz의 상위클래스라면
				return cls.cast(container.get(clz));//반환
			}
		}
		
		throw new NoSuchElementException("등록된 인스턴스 중 일치하는 항목이 없습니다");//예외 발생
	}
	
}