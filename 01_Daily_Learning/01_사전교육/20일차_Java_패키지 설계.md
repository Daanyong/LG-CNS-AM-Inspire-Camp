# [LG CNS 7기] 3주차 Day6 TIL
Java 계층형 패키지 구조 및 package/import 키워드 실습

## 📌 오늘의 학습 키워드
- `Layered Architecture (계층형 패키지 구조)`
- `package Keyword (파일 위치 지정)`
- `import Keyword (다른 패키지 클래스 불러오기)`
- `Controller - Service - Repository 의존 관계`
- `Domain Model (DTO, Entity)`

---

## 💡 공부한 내용 정리하기

### 1. 패키지와 import 키워드의 역할
- **`package`**: 파일 최상단에 작성하며, 해당 자바 클래스가 어느 폴더(패키지)에 위치해 있는지 주소를 명시함
- **`import`**: 다른 패키지에 있는 클래스를 가져와 사용할 때 선언함
  - 같은 패키지 내 클래스는 자유롭게 접근할 수 있지만, **서로 다른 패키지 간 접근 시에는 반드시 import문이 선언**되어 있어야 함

---

### 2. 계층형 패키지 구조를 쓰는 이유
처음에는 한 폴더에 파일을 다 넣어두는 게 편해 보였지만, 프로젝트가 커질수록 유지보수와 역할 분담을 위해 기능별로 폴더를 나누어 관리해야 함

- **`ctrl` (Controller)**: 클라이언트의 요청을 가장 먼저 받고 응답을 돌려주는 표현 계층
- **`service` (Service)**: 핵심 비즈니스 로직 및 계산을 처리하는 계층
- **`repository` (Repository)**: 데이터베이스(DB)에 직접 접근하여 데이터를 조작하는 계층
- **`domain`**:
  - **`dto` (Data Transfer Object)**: 계층 간 데이터 전달용 객체 (`TestRequestDTO`, `TestResponseDTO`)
  - **`entity`**: DB 테이블과 직접 매핑되는 객체 (`TestEntity`)

---

## 💻 Java 실습 코드 분석

### 1) TestController.java (`test.ctrl` 패키지)
```java
package test.ctrl; // 현재 파일의 위치 명시

// 다른 패키지(test.service)의 TestService 클래스를 사용하기 위해 import
import test.service.TestService;

public class TestController {
    // Controller 계층에서 비즈니스 로직을 부르기 위해 Service 객체를 new로 생성하여 참조
    public TestService service = new TestService();
}
```

### 2) TestService.java (`test.service` 패키지)
```java
package test.service; // 현재 파일의 위치 명시

// 다른 패키지(test.repository)의 TestRepository 클래스를 사용하기 위해 import
import test.repository.TestRepository;

public class TestService {
    // Service 계층에서 DB 접근을 위해 Repository 객체를 new로 생성하여 참조
    public TestRepository repository = new TestRepository();
}
```

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
처음에는 `ctrl`, `domain`, `dto`, `entity`, `repository`, `service` 등 패키지가 너무 다양하게 나뉘어서 구조를 파악하는 데 시간이 조금 걸렸다. 하지만 직접 코드를 작성해보며 `import`를 활용해 다른 패키지의 클래스를 가져오고, `Controller ➡️ Service ➡️ Repository` 순서로 객체를 연결해 보는 과정을 통해 백엔드 아키텍처의 기본 흐름을 직관적으로 이해할 수 있었다. 빨간 줄 에러가 발생할 때 `import` 선언이 누락되었는지 체크하는 습관이 중요할 것 같다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 오늘 실습한 계층형 패키지 구조를 보지 않고 처음부터 새로 구축해 보고, import문 없이 호출할 때 발생하는 컴파일 에러 직접 확인해 보기
- **내일의 학습 계획**: React 1강 수강

--- 

#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
