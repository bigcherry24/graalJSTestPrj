# GraalJS Web Demo (JDK 11)

textarea에 입력한 JavaScript 코드를 서버에서 GraalJS로 실행하는 Spring 기반 예제입니다.

## Tech Stack

- Java 11
- Spring Boot 2.7.18 (Spring Framework 5.3.x)
- GraalJS 22.3.5
- Thymeleaf

## 왜 Spring Boot 2.7.x 인가?

요청하신 JDK 11 기준에서는 Spring Boot 3.x를 사용할 수 없습니다.
Spring Boot 3.x는 Java 17 이상이 필요합니다.

그래서 이 샘플은 JDK 11에서 실제 실행 가능한 조합으로 구성했습니다.

## 실행

```bash
mvn spring-boot:run
```

브라우저에서 아래 주소로 접속합니다.

- http://localhost:8080

## 동작 방식

1. 화면 textarea에 JavaScript 코드 입력
2. 실행 버튼 클릭
3. 서버의 GraalJS 엔진이 코드를 평가 (`app` 객체만 바인딩)
4. 결과를 화면에 렌더링

## 노출 API 정책

- JS에서 사용할 수 있는 Java 브리지 객체는 `app` 1개만 노출
- 허용 메서드: `app.sayHello(name)`, `app.calc(a, b)`
- `Java.type(...)` 같은 Host class 접근은 차단

## 예시 스크립트

```javascript
const hi = app.sayHello("GraalJS");
const sum = app.calc(10, 32);
hi + " result=" + sum;
```

## 주의

이 코드는 학습용 예제입니다.
운영 환경에서는 임의 코드 실행에 대한 샌드박싱, 리소스 제한, 인증/권한 제어를 반드시 추가해야 합니다.
