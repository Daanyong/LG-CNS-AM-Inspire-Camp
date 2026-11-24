# [Day 21] LG CNS AM Inspire Camp 7기 TIL
작성일: 2026.11.24 (화) | 과목: AI 서비스 개발을 위한 백엔드

## 오늘 학습한 주제

- 스프링 부트란
- IntelliJ 개발환경 설정
- 프로젝트 구조
- pom.xml 살펴보기
- 의존성 타고 들어가기
- 애플리케이션 클래스와 실행
- WebApplicationType 바꿔보기
- jar 파일 만들어서 실행하기
- 환경변수 읽기와 우선순위
- 람다식과 함수형 인터페이스

---

## 1. 스프링 부트란

- 단독으로 실행되는 스프링 애플리케이션을 운영에 바로 배포할 수 있는 수준으로 쉽게 만들게 해주는 프레임워크
- 스프링 플랫폼과 서드파티 라이브러리에 대해 미리 정해둔 기본 방식을 제공한다. 이걸 opinionated view, 즉 편견이 있는 관점이라고 부른다. 자주 쓰는 라이브러리 설정을 미리 해놨으니 개발자는 최소한만 설정하면 된다.

### 특징

- 단독 실행 가능한 애플리케이션
- Tomcat, Jetty, Undertow 같은 웹 컨테이너가 내장되어 있다. 그래서 웹 애플리케이션이어도 war가 아니라 jar로 묶어서 배포할 수 있다.
- 스타터 의존성: 여러 라이브러리를 묶어서 제공해서 빌드 설정이 단순해진다.
- 서드파티 라이브러리용 자동 설정 클래스를 엄청 많이 제공한다. MongoDB, Redis 등을 쓸 때 설정 클래스를 이미 만들어놨다.
- Actuator: 메트릭, 헬스 체크 같은 모니터링 기능
- XML 설정 파일을 안 써도 된다.

---

## 2. IntelliJ 개발환경 설정

### JDK

- File, Project Structure에서 SDK를 JDK 17로 지정하고 Language level은 `SDK default` 로 한다.
- JDK가 설치된 경로는 Platform Settings, SDKs에서 확인할 수 있다.
- IntelliJ도 자바로 만든 프로그램이고 스프링 부트도 JDK 기반이라 JDK 위치를 알려줘야 한다.

### 플러그인 (Settings, Plugins, Marketplace)

- Lombok: 예전에는 IntelliJ에 번들로 들어있었는데 지금은 따로 설치해야 한다. getter, setter, toString, 생성자 같은 반복 코드를 어노테이션만 선언하면 만들어주는 라이브러리다. 소스 코드 생성을 해주기 때문에 별도 플러그인이 필요하다.
- Atom Material Icons: 파일 종류에 따라 아이콘을 다르게 보여준다 (git 관련 파일이면 git 아이콘 등). VS Code에도 비슷한 게 있다.
- Rainbow Brackets Lite: 여는 괄호와 닫는 괄호를 같은 색으로 맞춰서 보기 쉽게 해준다. 그냥 Rainbow Brackets는 유료이고 Lite가 무료 오픈소스라 이걸 쓴다.
- Spring Boot Assistant: properties 파일에서 `server.port` 같은 환경변수 키를 자동완성해준다. 원래 Spring Boot 플러그인이 있는데 얼티밋 에디션 전용이라, 우리는 커뮤니티 에디션이라 Assistant를 쓴다.
- 플러그인을 다 깐 다음에 IntelliJ를 한 번에 재시작해야 확실히 적용된다.

### 어노테이션 프로세싱

- Build, Execution, Deployment, Compiler, Annotation Processors에서 `Enable annotation processing` 체크. 안 하면 롬복이 소스를 생성하지 못한다.

### 프로젝트마다 다시 해야 하는 설정

Annotation processing, Build project automatically, Advanced Settings 쪽 체크는 한번 해도 새 프로젝트를 만들면 다시 해줘야 한다. 과제로 새 프로젝트를 만들 때 이 설정을 다시 해야 한다. (JDK 위치나 폰트는 한 번 하면 남는다.)

---

## 3. 프로젝트 구조

- `src/main/java`: 자바 소스
- `src/main/resources`: `static` (html, js 같은 정적 파일), `templates` (Thymeleaf 쓰는 html. 확장자는 html이지만 static이 아니라 templates에 둬야 한다), `application.properties`
- `src/test/java`: 테스트 케이스
- `target`: 컴파일된 class 파일이 들어가는 폴더. 나중에 만들 jar도 여기에 생긴다.
- `.gitignore`: IntelliJ 관련 파일 같은 건 깃에 안 올리도록 제외
- `.gitattributes`: 줄바꿈(CRLF, LF) 관련 설정
- `mvnw`, `mvnw.cmd`: 명령어로 Maven 빌드할 때 쓰는 스크립트.

---

## 4. pom.xml 살펴보기

- `artifactId` 는 프로젝트 이름
- `parent` 가 `spring-boot-starter-parent` 이고 그 안에 스프링 부트 버전이 있다.
- `java.version` 17
- 부트 어드민 버전 property가 하나 있다. Actuator 메트릭을 시각화해주는 게 Spring Boot Admin이다.
- 프로젝트 자체의 `version` 은 부트 버전이 아니라 우리가 만든 애플리케이션 버전이다 (`0.0.1-SNAPSHOT`). 기능을 추가할 때마다 올리는 번호이고, 나중에 jar 파일 이름이 artifactId와 이 version을 합쳐서 만들어진다.
- 의존성 목록은 `spring-boot-starter-` 로 시작하는 게 많다: data-jpa, actuator, thymeleaf, validation, webmvc, 부트 어드민 클라이언트
- 그 외에 devtools, h2 데이터베이스, MariaDB JDBC 드라이버(나중에 MariaDB 연동할 때 필요), lombok
- 테스트 의존성은 `scope` 가 `test` 라서 테스트에서만 적용된다. 예전에는 스타터 테스트 하나로 퉁쳤는데 이번 버전은 actuator 테스트, data-jpa 테스트, thymeleaf 테스트 식으로 기능별로 쪼개져 있다.
- `dependencyManagement`, 메이븐 컴파일러 플러그인, 롬복 관련 설정 등이 있다.

### 부모 pom을 따라 올라가 보기

IntelliJ에서 `artifactId` 에 Ctrl을 누르고 클릭하면 해당 pom 파일이 열린다.

- 우리 pom의 parent, 즉 `spring-boot-starter-parent` 를 열면 자바 버전, 라이선스(VMware), 리소스 설정(`application.properties` 와 yml을 resources 밑에서 자동 인식하게 하는 설정), 플러그인 설정이 있다.
- 거기서 한 단계 더 올라가면 `spring-boot-dependencies` 가 나온다. 파일이 3000줄 정도 되고 의존성이 엄청 많다. 각각 버전도 다 정해져 있다.
- 오른쪽의 External Libraries를 펼쳐보면 실제로 다운로드된 라이브러리 목록이 나오는데, logback, jackson, h2, HikariCP 같은 걸 우리가 따로 설치 안 했는데도 들어 있다.

---

## 5. 의존성 타고 들어가기

### spring-boot-starter-webmvc

타고 들어가면 jackson, tomcat, servlet, spring-web 등이 들어 있다. 스프링 프레임워크였으면 Jackson 같은 걸 직접 추가했어야 하는데, 부트는 웹 의존성을 추가하니까 알아서 딸려온다. 공식 문서의 opinionated view가 이런 뜻이다.

Jackson
- 자바 객체와 JSON 문자열을 서로 변환해주는 라이브러리
- 자바 객체를 JSON으로 바꾸는 걸 직렬화, JSON을 자바 객체로 바꾸는 걸 역직렬화라고 한다.
- 서버가 클라이언트에 응답할 때 자바 객체가 JSON으로 변환되어 나가고, 요청의 request body로 들어온 JSON은 자바 객체로 변환된다. REST API 만들 때 필수 역할이다.
- 같은 일을 하는 Gson 같은 라이브러리도 있는데, 가장 대중적이라서 부트가 Jackson을 골라서 채택했다.

Tomcat
- 자바 기반 웹 컨테이너 (서블릿 컨테이너라고도 한다). 서블릿 표준 스펙을 구현한 경량 웹 애플리케이션 서버로, HTTP 요청을 받아서 자바 웹 애플리케이션을 실행하고 응답을 돌려준다. 서블릿 라이프사이클도 관리한다.
- 웹 서버(nginx, 아파치)는 html, css, js 같은 정적 리소스를 처리하는 데 특화되어 있고, 웹 컨테이너도 정적 리소스를 처리할 수는 있지만 웹 서버가 훨씬 빠르다.
- 그래서 보통 앞단에 웹 서버를 두고 뒤에 웹 컨테이너를 두는 구조를 쓴다. 외부에는 웹 서버의 포트(80)만 열고, 컨테이너 포트(8080 같은)는 외부에서 접근 못 하게 막는다. 컨테이너 뒤에 DB가 있다.
- 비동기 기반 WebFlux를 쓰면 Tomcat이 아니라 Netty를 쓴다.

### spring-boot-starter-data-jpa

안에 `jdbc` 가 있고, 그 안에 `HikariCP` 가 들어 있다.

JDBC
- 자바에서 DB에 접속해서 SQL을 실행하고 결과를 처리하게 해주는 API (`java.sql` 패키지)
- Connection, Statement, PreparedStatement 같은 클래스로 직접 접근한다.
- DB를 만드는 회사를 벤더라고 부르고, 벤더별로 JDBC 인터페이스의 구현체(드라이버)를 제공한다. 우리는 MariaDB(MySQL의 오픈소스 버전)를 쓰니까 MariaDB 자바 클라이언트 의존성을 추가했다.
- 인터페이스 기반으로 코딩하기 때문에 DB 종류가 바뀌어도 애플리케이션 구조가 크게 안 바뀐다.
- 부트에서는 순수 JDBC 코드를 직접 쓰지 않고, 이걸 추상화한 Spring JDBC나 JPA를 쓴다.

JPA, Hibernate, Spring Data JPA의 관계
- JPA는 표준 스펙이다. (인터페이스로만 구성된 명세. 자카르타 EE 쪽 표준)
- Hibernate는 JPA 스펙을 구현한 구현체(라이브러리 이름)다.
- Hibernate를 직접 쓰면 복잡해서, 스프링 쪽에서 한 번 더 추상화한 게 Spring Data JPA다.
- JDBC는 SQL을 직접 작성하는 저수준 방식이고, JPA는 SQL을 직접 안 쓰고 프레임워크가 만들어준다. 자바 객체와 DB 테이블을 매핑하는 ORM 기술이다.
- 조회, 등록, 수정, 삭제를 줄여서 CRUD라고 하는데 JPA는 CRUD 반복 코드를 많이 줄여준다.
- 최근 트렌드는 Spring Data JPA 중심이다. 복잡한 동적 쿼리나 통계, 대용량 배치는 QueryDSL을 같이 쓰기도 한다. MyBatis는 좀 오래된 기술이다.
- 유지보수성이 좋고, 도메인 중심 설계와 잘 맞고, 영속성 관리(메모리의 자바 객체를 관리)를 일관되게 해주고, 특정 DB에 종속되지 않는다는 점이 장점이라고 한다.

### 커넥션 풀링과 HikariCP

- DB랑 연동하려면 커넥션을 맺어야 쿼리를 보낼 수 있다. 근데 요청할 때마다 커넥션을 만들고 쓰고 폐기하면, 네트워크 핸드셰이크 때문에 커넥션 만드는 시간이 SQL 실행보다 오래 걸린다. 커넥션은 무거운 자원이다.
- 그래서 수영장처럼 풀을 만들어 두고, 필요한 개수만큼 커넥션을 미리 만들어 놓는다. 요청이 오면 풀에서 하나 빌려주고, 다 쓰면 폐기하는 대신 반납해서 재사용한다. 이게 커넥션 풀링이다. 내부 구현은 ArrayList처럼 커넥션 객체를 여러 개 담아두는 식이다.
- 동시 접속이 몰릴 때 무제한으로 새 커넥션을 열면 DB가 고갈되는 장애가 생기는데, 풀 크기를 제어해서 DB를 보호할 수도 있다. 유효성 검사, 수명 관리도 해준다.
- 커넥션 풀링을 지원하는 라이브러리는 Apache Commons DBCP, Tomcat JDBC Pool, C3P0(제일 오래됨), 알리바바 Druid(통계, 모니터링 기능), 오라클 UCP 등 여러 개가 있다. 스프링 부트는 예전에 Tomcat JDBC 풀을 쓰다가 HikariCP로 바꿨다. 성능이 압도적이고, 라이브러리 크기가 작고, 설계 철학이 명확하고, 설정이 거의 필요 없어서다.
- 프레임워크에서는 HikariCP 의존성을 직접 추가하고 `HikariDataSource` 를 설정 클래스에서 `@Bean` 으로 등록해야 하는데, 부트는 data-jpa 의존성만 추가하면 이걸 다 알아서 해준다.

---

## 6. 애플리케이션 클래스와 실행

부트는 애플리케이션 서버가 계속 떠 있어야 요청을 받아서 처리할 수 있다. devtools는 소스나 properties, html을 수정하면 애플리케이션을 자동으로 재시작해준다.

### `@SpringBootApplication`

Ctrl+클릭으로 타고 들어가 보니 세 가지 어노테이션이 합쳐진 것이었다.

- `@SpringBootConfiguration`: 안으로 들어가면 `@Configuration` 이다. 즉 애플리케이션 클래스 자체가 설정 클래스다. 그래서 이 클래스 안에 `@Bean` 메서드를 만들어서 빈을 등록해도 된다.
- `@ComponentScan`: 컴포넌트 스캔을 이 클래스가 한다. 프레임워크에서는 스캔하는 설정 클래스를 따로 만들어야 했는데 부트에서는 이 클래스가 그 역할을 한다.
- `@EnableAutoConfiguration`: 부트가 서드파티 라이브러리용으로 만들어놓은 수많은 auto configuration 클래스(DataSourceAutoConfiguration, JacksonAutoConfiguration, 캐시, 카산드라, H2 콘솔, 몽고DB, 카프카 등)를 활성화한다. API 문서에서 Configuration으로 검색하면 정말 많이 나온다.

### 베이스 패키지 주의

애플리케이션 클래스가 컴포넌트 스캔을 하기 때문에, 이 클래스가 있는 패키지, 즉 프로젝트 만들 때 정한 패키지가 베이스 패키지가 된다. 베이스 패키지 바깥에 다른 패키지를 만들고 거기에 `@Component` 클래스를 만들어봤는데 빈으로 인식이 안 됐다. 그래서 소스는 반드시 베이스 패키지 밑에 만들어야 한다.

### 자동 설정 확인 실습

HikariCP가 자동으로 빈 등록이 됐는지 확인하는 실습을 했다.

- `HikariDataSource` 는 `javax.sql.DataSource` 인터페이스를 구현한 클래스다.
- 스프링 프레임워크 때 `Printer` 인터페이스를 `@Autowired` 로 주입받으면 구현체인 `StringPrinter` 가 들어왔던 것과 똑같이, `DataSource` 를 주입받아서 클래스 이름을 찍어보면 어떤 구현체가 빈으로 등록됐는지 알 수 있다.
- 결과로 `HikariDataSource` 가 나왔다. 우리가 설정한 적이 없는데 부트의 auto configuration이 등록해준 것이다.
- AI에게 물어보니 이걸 해주는 클래스가 `DataSourceAutoConfiguration` 이고, 그 안의 내부 클래스 중 Hikari 관련 설정이 있으며, `@EnableAutoConfiguration` 을 통해 로드된다고 했다.

### ApplicationRunner

main 메서드가 static이라 여기서는 주입받은 변수를 쓸 수가 없다. 그래서 애플리케이션이 시작되자마자 뭔가 실행하고 싶을 때는 러너를 쓴다.

- 베이스 패키지 밑에 `runner` 패키지를 만든다.
- `ApplicationRunner` 인터페이스를 구현하고 `run` 메서드를 오버라이딩한다. (`Ctrl + I` 로 구현할 메서드 목록을 띄울 수 있다.)
- 스프링 빈으로 등록해야 하니까 `@Component` 를 꼭 붙인다
- 애플리케이션이 기동되면 이 `run` 이 자동으로 호출된다.

<details>
<summary>DbRunner 코드</summary>

```java
package com.example.myspringboot4.runner;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DbRunner implements ApplicationRunner {

    @Autowired
    DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 어떤 구현 클래스가 빈으로 등록됐는지 확인
        System.out.println("DataSource 구현 클래스 = " + dataSource.getClass().getName());
    }
}
```

</details>

러너가 여러 개면 `@Order` 로 순서를 줄 수 있다. 숫자가 낮을수록 먼저 실행된다. 실습에서 `DbRunner` 를 `@Order(1)`, `MyRunner` 를 `@Order(2)` 로 줬더니 로그에도 그 순서대로 찍혔다.

---

## 7. WebApplicationType 바꿔보기

`SpringApplication.run(...)` 을 바로 부르는 대신, `SpringApplication` 객체를 직접 만들면 애플리케이션 설정을 바꿀 수 있다.

<details>
<summary>SpringApplication 직접 생성 코드</summary>

```java
@SpringBootApplication
public class MySpringBoot4ProjectApplication {

    public static void main(String[] args) {
        SpringApplication application =
                new SpringApplication(MySpringBoot4ProjectApplication.class);

        // NONE / SERVLET / REACTIVE
        application.setWebApplicationType(WebApplicationType.SERVLET);

        application.run(args);
    }
}
```

</details>

- `SERVLET`: 동기 방식 웹. 설정을 안 하면 기본값이다. Tomcat이 뜨고 프로세스가 계속 살아 있다.
- `REACTIVE`: 비동기 방식 웹 (WebFlux).
- `NONE`: 웹 앱이 아니다. 실행해보니 Tomcat이 안 뜨고, DB 관련 작업만 하고 프로세스가 바로 종료된다

---

## 8. jar 파일 만들어서 실행하기

### JAVA_HOME 설정

`mvnw` 가 `JAVA_HOME` 환경변수를 쓰기 때문에 이게 JDK 17을 가리켜야 한다. 먼저 `java -version` 으로 17이 나오는지 확인했고, 다른 버전(11, 23, 25 등)이 나오는 사람은 아래처럼 터미널에만 임시로 설정했다. 터미널을 닫으면 초기화되니까 새로 열 때마다 해줘야 한다. (컴퓨터 전체에 시스템 환경변수로 설정하는 방법도 있다.)

- 경로 뒤에 `bin` 을 붙여서 PATH 맨 앞에 넣어야 기존 PATH보다 먼저 17이 잡힌다.

<details>
<summary>JAVA_HOME 설정 명령어</summary>

```bash
# 윈도우 (Command Prompt)
set JAVA_HOME=C:\Program Files\Java\jdk-17.0.x
set PATH=%JAVA_HOME%\bin;%PATH%
java -version

# 맥 / 리눅스 / Git Bash
export JAVA_HOME=/내/jdk17/경로
export PATH=$JAVA_HOME/bin:$PATH
java -version
```

</details>

### Maven 라이프사이클과 package

우측 Maven 탭의 Lifecycle에 작업들이 있다.

- `clean`: target 폴더를 전부 지운다.
- `validate`: 컴파일 전에 프로젝트가 유효한지 검사
- `compile`: class 파일 생성
- `test`: 테스트 케이스 실행
- `package`: jar로 묶기

`package` 를 하면 앞의 단계(validate, compile, test)를 다 거쳐서 `target` 밑에 jar가 만들어진다. 윈도우에서는 `mvnw package`, 맥은 `./mvnw package`, 끝에 `BUILD SUCCESS` 가 나오면 된다.

### 실행

터미널에서 `java -jar target/` 까지 치고 Tab을 누르면 jar 파일명이 자동완성된다. 실행하면 `banner.txt` 의 애플리케이션 버전도 잘 나왔다. 에디터에서 돌리는 건 개발용이고, 나중에 배포할 때는 이렇게 jar로 묶어서 실행한다.

그리고 에디터에서 실행 중인 앱이 있으면 8080 포트가 겹쳐서 jar가 안 뜨기 때문에 먼저 종료해야 한다. jar 실행을 멈출 때는 `Ctrl + C`.

<details>
<summary>빌드와 실행 명령어</summary>

```bash
# 윈도우
mvnw clean
mvnw package
java -jar target/my-spring-boot4-project-0.0.1-SNAPSHOT.jar

# 맥
./mvnw clean
./mvnw package
java -jar target/my-spring-boot4-project-0.0.1-SNAPSHOT.jar
```

</details>

---

## 9. 환경변수 읽기와 우선순위

### 환경변수 읽기 (`@Value`)

`application.properties` 에 직접 만든 환경변수를 넣고 `@Value` 로 읽어왔다.

```properties
myboot.name=spring
```

`MyRunner` 에서 `@Value("${myboot.name}")` 으로 받아서 출력하면 `spring` 이 나온다.

<details>
<summary>MyRunner 코드</summary>

```java
@Component
@Order(2)
public class MyRunner implements ApplicationRunner {

    @Value("${myboot.name}")
    private String name;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("myboot.name = " + name);

        // 프로그램 인자로 server.port, myboot.name 이 넘어왔는지 확인
        System.out.println("server.port 있나? " + args.containsOption("server.port"));
        System.out.println("myboot.name 있나? " + args.containsOption("myboot.name"));
    }
}
```

</details>

### ApplicationArguments

main 메서드의 `String[] args` 로 넘어온 값이 `run(ApplicationArguments args)` 의 `ApplicationArguments` 객체에 담겨서 전달된다.

- `containsOption("키")`: 그 이름의 옵션이 있는지 true/false
- `getOptionNames()`: 옵션 이름 목록 (`Set<String>`)
- `getOptionValues("키")`: 해당 옵션의 값 목록 (값이 여러 개일 수 있다)

이런 걸 쓰는 이유는 API 키 같은 민감한 값 때문이다. 예를 들어 OpenAI API 키를 properties에 그대로 적고 깃에 올리면 노출되니까, properties에는 자리만 만들어두고 실제 값은 실행할 때 인자로 넣는다. JWT의 시크릿 값이나 만료 시간도 비슷하게 환경변수로 빼야 하는 값들이다. 개발 환경과 운영 환경에서 달라지는 값도 소스를 고치지 않고 실행할 때 인자로 바꾸는 게 효율적이다.

### 프로그램 인자와 VM 인자 세팅 (IntelliJ)

Run, Edit Configurations에서 설정한다.

- 프로그램 인자: `Program arguments` 칸에 `--server.port=8083 --myboot.name=스프링` 처럼 앞에 `--` 를 붙인다.
- VM 인자: 기본으로는 칸이 안 보여서 `Modify options` 에서 `Add VM options` 를 체크해야 나온다. 앞에 `-D` 를 붙인다. 예: `-Dserver.port=8087`

실습 결과
- `-Dserver.port=8087` 를 VM 인자로 주면 포트가 실제로 8087로 바뀌어 뜬다. 즉 부트의 `server.port` 설정에는 VM 인자도 먹힌다.
- 하지만 `containsOption("server.port")` 로 확인하면 VM 인자일 때는 false가 나온다. `ApplicationArguments` 는 main 메서드로 넘어온 프로그램 인자만 담기 때문이다. 프로그램 인자로 바꾸니 true가 나왔다.
- properties에 `myboot.name=spring` 으로 되어 있는데 인자로 `--myboot.name=스프링` 을 주면 출력값이 한글 `스프링` 으로 바뀐다.

### 우선순위

숫자가 낮을수록 우선순위가 높다.

- 커맨드 라인 인자(프로그램 인자): 4순위
- jar 안의 `application.properties`: 15순위

그래서 properties에 어떻게 적어놨든 실행할 때 인자로 주면 그게 덮어쓴다. 처음에 8080이랑 `spring` 이 설정돼 있는데도 인자로 준 값이 이겼다. 문서에는 1번부터 17번까지 순서가 있고, 이게 굉장히 중요한 개념이라고 여러 번 하셨다.

---

## 10. 람다식과 함수형 인터페이스

`ApplicationArguments` 의 `getOptionNames()` 로 이름 목록을 뿌려볼 때 람다가 나와서 같이 정리했다. 자바 람다가 좀 어려워서 JS부터 설명해주셨다.

### JavaScript 화살표 함수

일반 함수와 람다(화살표) 함수 비교. 화살표 함수는 함수 키워드와 함수 이름, 중괄호, return을 다 생략할 수 있고, 인자와 바디를 화살표로만 연결한다. (인자가 한 개면 괄호도 생략 가능하고, 두 개 이상이면 괄호가 필요하다.)

<details>
<summary>JS 함수와 화살표 함수 비교</summary>

```javascript
// 일반 함수
function add(n1, n2) {
  return n1 + n2;
}
console.log(add(1, 2));   // 3

// 화살표 함수 (람다)
const add2 = (n1, n2) => n1 + n2;
console.log(add2(1, 2));  // 3
```

</details>

### 자바: Iterable.forEach와 Consumer

- `Set`, `List` 같은 컬렉션은 `Iterable` 의 하위 인터페이스라서 `forEach` 메서드를 쓸 수 있다.
- `forEach` 의 인자는 `Consumer` 인데, 이건 함수형 인터페이스다.
- 함수형 인터페이스는 추상 메서드가 딱 하나만 있는 인터페이스다. `Consumer` 는 메서드가 두 개 있어 보이지만 하나(`accept`)만 추상 메서드이고, 나머지는 `default` 라서 바디가 있다.
- 이 추상 메서드를 재정의하는 구문을 람다식이나 메서드 레퍼런스로 쓸 수 있다.
- 메서드 이름을 생략하니까 어떤 메서드를 오버라이딩하는지 알 수 있어야 해서, 추상 메서드가 반드시 한 개여야 한다.
- `accept(T t)` 는 인자가 하나 들어오고 반환값이 `void` 다. 그래서 람다도 인자 하나를 받아서 바디에서 처리만 하면 된다.
- 람다에서 인자 타입은 안 써도 된다 (에디터가 추론해서 보여주기만 한다).
- 자바는 화살표가 `->` 다.

### 메서드 레퍼런스

람다에서 받은 인자를 바디에서 가공 없이 그대로 넘기기만 하는 경우에는 인자까지 생략하고 `System.out::println` 처럼 쓸 수 있다. 이게 메서드 레퍼런스다. 인자가 있다는 걸 알아서 추론해준다. 나중에 스트림에서 이런 식으로 간결하게 쓰는 일이 많다고 한다.

<details>
<summary>forEach 세 가지 방식 비교</summary>

```java
Set<String> optionNames = args.getOptionNames();

// 1. 기존 for문
for (String name : optionNames) {
    System.out.println("name = " + name);
}

// 2. 람다식
optionNames.forEach(optionName -> System.out.println("이름: " + optionName));

// 3. 메서드 레퍼런스
optionNames.forEach(System.out::println);
```

</details>

---
#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
