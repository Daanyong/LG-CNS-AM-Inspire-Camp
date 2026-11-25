# [Day 22] LG CNS AM Inspire Camp 7기 TIL
작성일: 2026.11.25 (수) | 과목: AI 서비스 개발을 위한 백엔드

## 오늘 학습한 주제

- 함수형 인터페이스, 람다, 메서드 레퍼런스
- 환경 변수 읽기와 우선순위 실습
- `@ConfigurationProperties`
- 프로파일
- 빌더 패턴
- 로깅
- DB 연동 개념: JPA, 하이버네이트, 스프링 데이터 JPA

---

## 1. 함수형 인터페이스, 람다, 메서드 레퍼런스

- 함수형 인터페이스는 추상 메서드가 딱 하나인 인터페이스고 자바 8부터 나왔다. 그 추상 메서드를 람다식이나 메서드 레퍼런스로 구현할 수 있다.
- `Iterable.forEach()`의 인자는 `Consumer`인데, `Consumer`에는 `accept`라는 추상 메서드 하나만 있다. 그래서 함수형 인터페이스다.
- 람다로 쓸 때는 메서드 이름은 안 써도 되고 인자와 리턴 타입만 맞추면 된다. `Consumer`는 인자를 받고 반환값이 없다.
- `forEach`가 호출되면서 `accept`가 바로 호출되는 것처럼, 내가 호출하지 않았는데 어떤 시점에 호출되는 메서드를 콜백이라고 한다.

### 4단계로 줄여나가기

- 1단계, 이름 있는 클래스: `Consumer`를 `implements`한 `PrinterConsumer` 클래스를 따로 만들고 `accept`를 오버라이딩한다. 가독성은 좋고 여러 곳에서 재사용할 수 있지만 일회성으로 쓸 때는 코드가 길어지고 클래스 파일도 늘어난다.
- 2단계, 익명 이너 클래스: 자기 이름 없이 부모(인터페이스) 이름을 빌려서 선언과 동시에 객체를 생성한다. `forEach`가 끝나면 메모리에서 사라진다.
- 3단계, 람다식: 인터페이스명과 메서드명을 다 생략하고 인자와 실행문만 남긴다.
- 4단계, 메서드 레퍼런스: 람다 본문이 이미 있는 메서드 하나만 호출하면 인자 전달도 생략하고 `System.out::println`처럼 `::`로 쓴다.
- 추상 메서드가 두 개 이상이면 람다로 못 쓰고 이너 클래스 안에서 여러 개를 오버라이딩해야 한다.

<details>
<summary>코드 보기 (4단계 비교)</summary>

```java
List<String> names = List.of("a", "b", "c");

// 1단계: 이름 있는 클래스
public class PrinterConsumer implements Consumer<String> {
    @Override
    public void accept(String name) {
        System.out.println(name);
    }
}
names.forEach(new PrinterConsumer());

// 2단계: 익명 이너 클래스
names.forEach(new Consumer<String>() {
    @Override
    public void accept(String name) {
        System.out.println("inner class : " + name);
    }
});

// 3단계: 람다식
names.forEach(name -> System.out.println(name));

// 4단계: 메서드 레퍼런스
names.forEach(System.out::println);
```

</details>

---

## 2. 환경 변수 읽기와 우선순위 실습

### 환경 변수 정의 방식

- `${random.int(1,100)}`처럼 쓰면 1부터 100 사이 랜덤 정수가 들어간다.
- 이미 선언한 환경 변수를 다른 환경 변수에서 `${myboot.name}` 같은 치환자로 불러올 수도 있다.
- 한글은 변환하다가 깨지는 경우가 있어서 properties에는 영문으로 쓰기로 했다.

### 읽어오는 방법 두 가지

- `@Value("${myboot.name}")`: 이름이 키 그대로 매칭되기 때문에 공백이 하나라도 들어가거나 없는 키를 쓰면 바로 에러가 난다..
- `Environment` 인터페이스를 `@Autowired`로 주입받아서 `getProperty("myboot.name")`으로 읽는다. 여기서는 `${}`를 안 써도 된다.

<details>
<summary>코드 보기 (properties, MyRunner)</summary>

```properties
myboot.name=spring
myboot.age=${random.int[1,100]}
myboot.fullName=${myboot.name} boot
```

```java
@Component
public class MyRunner implements ApplicationRunner {

    @Value("${myboot.name}")
    private String name;

    @Value("${myboot.age}")
    private int age;

    @Autowired
    private Environment environment;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("@Value : " + name + ", " + age);
        System.out.println("Environment : " + environment.getProperty("myboot.name"));
    }
}
```

</details>

### 우선순위 확인

- properties에는 `myboot.name=spring`으로 영문을 줬는데, 에디터의 Program arguments에 `--myboot.name=스프링`을 넣고 실행하니 한글이 출력됐다. 인자가 properties를 덮어쓴 것이다.
- 인자 이름을 `myboot.name`이 아닌 다른 이름으로 주면 덮어쓰지 않고 영문 그대로 나온다. 이름이 같아야 덮어쓴다.

### jar 파일로 실행해보기

- 에디터 안에서 Program arguments로 주는 것과, jar 실행할 때 뒤에 인자를 붙이는 건 똑같은 커맨드 라인 인자다.
- `mvnw package`로 jar를 만들고 `java -jar`로 실행한다. 
- `server.port`는 스프링이 미리 정해둔 환경 변수라서 이것도 인자로 줄 수 있다.
- jar 이름은 기본적으로 artifactId와 version을 대시로 연결해서 만들어진다. 이름을 고정하고 싶으면 pom.xml의 `build` 안에 `<finalName>`을 넣는다.
- 나중에 도커로 배포할 때 이런 식으로 커맨드 라인 인자로 필요한 환경 변수를 세팅하게 된다.

<details>
<summary>코드 보기 (finalName, 실행 명령어)</summary>

```xml
<build>
    <finalName>myboot4</finalName>
    <plugins>
        ...
    </plugins>
</build>
```

```bash
./mvnw clean
./mvnw package
java -jar target/myboot4.jar --myboot.name=myspring --server.port=8082
```

</details>

### Spring Cloud Config (개념만)

- properties가 jar 안에 들어있으면 값 하나 바꿀 때도 다시 빌드하고 배포해야 해서 불편하다.
- 자주 바뀌는 설정 파일만 깃(보안이 필요하면 비공개 저장소)에 따로 빼놓고, 변경이 생기면 클라우드 컨피그 서버가 각 서비스에 알려줘서 다시 적용하게 하는 방식이다.
- 우선순위 표에서도 jar 밖에 있는 설정이 더 높은 순위에 있다.

---

## 3. `@ConfigurationProperties`

### 왜 쓰는지

- `@Value`나 `getProperty`는 키를 문자열로 주기 때문에 오타나 공백 실수가 날 수 있다. 이런 걸 타입 세이프하지 않다고 한다.
- 같은 prefix로 시작하는 환경 변수들을 하나의 자바 객체에 담아두면, 객체에서 `.` 찍고 getter를 부르면 되니까 메서드 이름이 틀릴 일이 없다.
- 하이버네이트용 `HibernateProperties`도 `spring.jpa.hibernate...` 같은 환경 변수를 객체에 담아둔 대표적인 예시다.

### 규칙

- 클래스를 스프링 빈으로 등록하고(`@Component`) `@ConfigurationProperties(prefix = "myboot")`를 붙인다.
- 필드명은 prefix 뒤에 오는 이름과 맞춘다. 대소문자도 그대로 살려서 쓴다.
- 롬복 `@Getter`, `@Setter`를 쓴다. setter는 스프링 컨테이너가 값을 채워 넣을 때 호출하고, getter는 우리가 값을 꺼내 쓸 때 쓴다.
- `spring-boot-configuration-processor` 의존성이 필요하다. pom.xml에 추가하고 메이븐 싱크를 눌러야 한다.
- 사용하는 쪽에서는 `@Autowired`로 주입받는다.

<details>
<summary>코드 보기 (의존성, 클래스, 사용)</summary>

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

```java
@Component
@ConfigurationProperties(prefix = "myboot")
@Getter
@Setter
public class MyBootProperties {
    private String name;
    private int age;
    private String fullName;
}
```

```java
@Autowired
private MyBootProperties properties;

// run 메서드 안에서
System.out.println("properties : " + properties.getName() + ", " + properties.getAge());
```

</details>

---

## 4. 프로파일

### 개념

- 개발 환경은 보통 로컬(내 컴퓨터), 개발 서버(팀 공용), 스테이징(운영과 거의 같게 맞춘 환경), 운영(프로덕션)으로 나뉜다.
- 환경마다 DB 접속 URL, 계정, 외부 API 주소(결제 시스템 등), 로그 수준, 내부 구현이 달라질 수 있다. 이걸 주석 풀었다 막았다 하면서 관리하면 실수하기 쉬우니까 프로파일이라는 이름표를 붙여서 구분한다.
- 현재 활성 프로파일은 `spring.profiles.active` 환경 변수로 지정한다. 값(태그명)은 정해진 키워드가 아니라 팀에서 약속하는 건데, 보통 `local`, `dev`, `test`, `stage`, `prod` 같은 걸 쓴다.

### 방법 1: 설정 파일 분리

- `application-{프로파일명}.properties` 형태로 만들면 활성 프로파일에 맞는 파일이 적용된다. yml 포맷으로 해도 된다.
- 프로파일 파일은 우선순위가 13위라서 그냥 `application.properties`(15위)보다 높다.
- 실습에서 `application-test.properties`에는 랜덤 1~50, `application-prod.properties`에는 랜덤 51~100으로 다르게 줘서 확인했다. `spring.profiles.active=test`일 땐 1~50 사이 값이 나오고, prod로 바꾸면 51~100 사이 값이 나왔다.
- 나중에 DB 연동할 때 test는 H2, prod는 MariaDB 같은 식으로 나눌 수 있다.

### 방법 2: 빈 분리

- 같은 빈이지만 환경마다 내부 구현을 다르게 하고 싶을 때 `@Profile`을 쓴다.
- 프로파일 없이 같은 이름의 빈 메서드를 두 개 만들면 빈 아이디(메서드 이름)가 중복돼서 에러가 난다. `@Profile`을 붙이면 활성 프로파일에 해당하는 것만 등록돼서 에러가 안 난다.
- 사용하는 쪽에서는 그냥 `CustomerVo` 하나만 주입받으면 되고, 어떤 구현이 들어올지는 런타임에 프로파일로 결정된다.
- 부정도 가능하다. `@Profile("!prod")`라고 하면 운영이 아닐 때만 동작한다. 로컬이나 개발에서는 가짜(목) 구현을 쓰고 운영에서만 진짜 메일 발송이나 결제 연동을 하는 식으로 쓴다.
- 주의할 점: `prod`에만 있는 빈을 주입받으려고 하면 다른 프로파일일 때 주입 오류가 날 수 있다. `local`이나 `default`도 같이 만들어두는 게 안전하다.
- 교재에 `NotificationSender` 인터페이스 예시도 있었다. 개발 환경에서는 `DummyNotificationSender`, 운영에서는 `RealEmailNotificationSender`가 주입된다. 

<details>
<summary>코드 보기 (프로파일별 빈, 사용)</summary>

```java
@Profile("test")
@Configuration
public class TestConfig {
    @Bean
    public CustomerVo customerVo() {
        return CustomerVo.builder()
                .mode("테스트 모드")
                .rate(0.5)
                .build();
    }
}

@Profile("prod")
@Configuration
public class ProdConfig {
    @Bean
    public CustomerVo customerVo() {
        return CustomerVo.builder()
                .mode("운영 모드")
                .rate(0.8)
                .build();
    }
}
```

```java
// MyRunner
@Autowired
private CustomerVo customerVo;

// run 안에서
System.out.println(customerVo);
```

```properties
# application.properties
spring.profiles.active=test
```

</details>

### 실습하면서 확인한 것

- properties에 `spring.profiles.active=test`로 해두면 test 빈이 나온다.
- 에디터 실행 설정에서 활성 프로파일을 `prod`로 따로 주면 properties가 test여도 prod 쪽이 나온다. 설정 파일보다 우선순위가 높은 쪽 값이 이기기 때문이다.
- jar로 만들어서 실행할 때도 `java -jar target/myboot4.jar --spring.profiles.active=prod`로 주면 prod 설정이 적용됐다.
- 연습문제는 `MyEnvironment`라는 클래스를 만들어서 운영에서는 운영 환경 값을, 개발에서는 개발 환경 값을 세팅하는 것이다.

---

## 5. 빌더 패턴

- GoF 패턴 중 하나로, 복잡한 객체를 만드는 과정을 표현 방식과 분리하는 생성 패턴이다.
- 멤버 변수가 많은 클래스에서 값이 없는 건 `null`로 채워서 생성자를 호출하면, 파라미터 개수가 많아져서 생성자 폭발이 일어나고 어떤 값이 뭘 의미하는지 한눈에 안 들어온다. (`new User("kim", "a@a.com", true, false, null ...` 이런 식)
- 빌더 패턴은 필수 속성과 선택 속성을 나눈다.
  - 클래스 안에 `static inner class`로 `Builder`를 만든다.
  - 필수 속성은 `Builder` 생성자로 받는다.
  - 선택 속성은 필드명과 같은 이름의 메서드로 설정하고 자기 자신(`this`)을 리턴한다.
  - `build()` 메서드에서 바깥 클래스의 `private` 생성자를 호출해서 객체를 만든다.
- 이런 코드를 롬복 `@Builder`가 자동으로 만들어준다. `Customer.builder().mode("..").rate(0.5).build()` 이렇게 쓴다.
- 장점은 가독성이 좋아지고, 생성 시점에 값 검증을 할 수 있고, 한 번 만들어진 객체를 불변으로 유지할 수도 있다는 점이다.
- `HttpRequest` 예시에서도 url, method는 필수고 timeout, retry, authToken은 선택으로 나눠서 `build()` 안에서 timeout이 0보다 큰지 체크하고 있었다.

<details>
<summary>코드 보기 (직접 구현한 빌더, 롬복 사용)</summary>

```java
public class Computer {
    private final String cpu;   // 필수
    private final String gpu;   // 선택
    private final String ram;   // 선택

    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.gpu = builder.gpu;
        this.ram = builder.ram;
    }

    public static class Builder {
        private final String cpu;
        private String gpu;
        private String ram;

        public Builder(String cpu) {
            this.cpu = cpu;
        }

        public Builder gpu(String gpu) {
            this.gpu = gpu;
            return this;
        }

        public Builder ram(String ram) {
            this.ram = ram;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }
}

// 사용
Computer computer = new Computer.Builder("intel")
        .gpu("rtx")
        .ram("16g")
        .build();
```

```java
// 롬복 사용
@Getter
@ToString
@Builder
public class CustomerVo {
    private String mode;
    private double rate;
}
```

</details>

---

## 6. 로깅

### 왜 로깅 라이브러리를 쓰는지

- `System.out.println` 같은 걸로 찍으면 개발할 때만 필요한 로그와 운영에서 꼭 봐야 하는 로그가 구분이 안 된다. 개발하면서 찍은 걸 지우지 않고 배포하면 운영자가 중요한 로그를 찾기 힘들어진다. 막았다 풀었다 하다가 실수도 나온다.
- 핵심 이유는 로그 레벨이다. 로그 레벨은 낮은 쪽부터 `trace`, `debug`, `info`, `warn`, `error` 순서다.
- 현재 설정한 레벨보다 낮은 로그는 출력이 안 된다. 소스는 건드리지 않고 로그 레벨 설정만 바꾸면 개발에서는 debug까지, 운영에서는 info나 warn 이상만 나오게 할 수 있다.
- 스프링 부트의 기본 로그 레벨은 info다.
- 그 외 기능
  - 콘솔 말고 파일이나 DB로도 출력할 수 있다. 출력 방식은 어펜더, 파일을 일정 주기로 나누는 건 롤링 정책이 담당한다.
  - 운영 로그에는 스택 트레이스 같은 민감한 정보가 사용자한테 보이면 안 되고, 주민번호나 카드번호, 이메일 같은 개인 정보는 마스킹 처리를 해야 한다.

설정 레벨별로 어떤 로그가 찍히는지는 아래와 같다.

| 설정 레벨 | trace | debug | info | warn | error |
|---|---|---|---|---|---|
| debug | X | O | O | O | O |
| info | X | X | O | O | O |
| warn | X | X | X | O | O |
| error | X | X | X | X | O |

### 파사드 패턴과 로깅 구조

- 파사드는 건물의 정면이라는 뜻이다. 여러 서브 시스템 앞에 대문 역할을 하는 하나의 창구를 둬서, 클라이언트가 내부 구조를 몰라도 되게 하는 패턴이다. 게이트웨이와 비슷한 개념이다.
- 로깅에서는 SLF4J가 파사드(인터페이스 모음)고, Logback이나 Log4j2가 실제 구현체다. 스프링 부트는 기본으로 Logback을 쓴다. Log4j는 예전에 보안 이슈가 있었다.
- 코드에서는 SLF4J API만 호출하고, 런타임에 SLF4J가 구현체를 찾아서 연결해준다. 그래서 구현체를 바꿔도 로그를 쓰는 코드는 영향이 없다.

### 설정과 사용

- 베이스 패키지의 로그 레벨은 `logging.level.패키지명`으로 준다. 이번에는 `application-test.properties`에는 debug, `application-prod.properties`에는 info를 줬다. 대소문자는 상관없다.
- 파일로 남기고 싶으면 `logging.file.path`로 폴더를 지정한다. 콘솔에도 나오고 파일에도 같이 쌓인다. `logs` 폴더를 만들고 `.gitignore`에 `logs`를 추가해서 깃에는 안 올라가게 했다.
- 로거 생성은 `LoggerFactory.getLogger(클래스.class)`로 하거나, 롬복 `@Slf4j`를 클래스에 붙이면 `log`라는 변수를 바로 쓸 수 있다. 값은 `{}` 자리에 넣어서 출력한다.

<details>
<summary>코드 보기 (로그 설정, 로거 사용)</summary>

```properties
# application-test.properties
logging.level.com.example.myboot=debug

# application-prod.properties
logging.level.com.example.myboot=info

# 파일 로그 (application.properties 쪽에 공통으로 둬도 됨)
logging.file.path=logs
```

```
# .gitignore
logs
```

```java
@Slf4j
@Component
public class MyRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("구현 클래스 : {}", log.getClass().getName());
        log.info("name : {}, age : {}", properties.getName(), properties.getAge());
    }
}

// @Slf4j 없이 직접 만들 때
private static final Logger log = LoggerFactory.getLogger(MyRunner.class);
```

</details>

### 로그 레벨 실습 결과

- `test` 프로파일일 때는 debug 로그 4개, info 로그 4개가 전부 나왔다.
- `prod`로 바꾸면 info 4개만 나오고 debug는 안 나왔다.
- 처음에 debug가 안 나와서 이상했는데, 실행 설정에 `spring.profiles.active=prod`를 남겨놓은 상태였다. 그걸 지우니까 test 프로파일이 적용돼서 debug도 나왔다.

### 구현체 교체 (Logback에서 Log4j2로)

- pom.xml에서 `spring-boot-starter`의 `spring-boot-starter-logging`을 exclusion으로 제외하고, `spring-boot-starter-log4j2` 의존성을 추가한다. 변경 후에는 메이븐 싱크를 하고 재시작해야 한다.
- 이렇게 바꾸면 `log.getClass().getName()`으로 찍은 구현 클래스 이름이 Logback 쪽에서 Log4j 쪽으로 달라진다. 하지만 `log.info(...)`를 쓰는 코드는 한 줄도 안 바꿔도 그대로 동작한다. 파사드를 쓰는 장점이다.
- `logback-spring.xml`을 쓰면 Logback에 종속돼서 구현체를 바꿀 때 영향이 가기 때문에, 로그 설정을 properties에 한 것이다.

<details>
<summary>코드 보기 (pom.xml 교체)</summary>

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```

</details>

---

## 7. DB 연동 개념: JPA, 하이버네이트, 스프링 데이터 JPA

### ORM

- 객체와 관계형 DB 사이의 개념적인 불일치를 해결해주는 기술이다. 자바는 JPA라는 표준이 있고, 파이썬은 SQLAlchemy나 장고 ORM이 있다.

### JPA

- 영속성 API 표준 명세다. `jakarta.persistence` 패키지를 열어보면 대부분 인터페이스와 어노테이션(`@Entity`, `@Id`, `@Table`, `@OneToMany`, `@ManyToOne` 등)이고 구현 클래스는 거의 없다.
- 실제로 SQL을 만들거나 DB에 연결하는 동작은 직접 하지 않는다. 로깅 파사드처럼 JPA가 파사드 역할이다.

### 하이버네이트

- JPA 명세를 실제로 구현한 ORM 프레임워크고 사실상 표준이다. 다른 구현체로 EclipseLink, DataNucleus 등이 있지만 거의 안 쓴다.
- JDBC는 자바 소스 안에 SQL을 직접 쓰고, 마이바티스는 XML에 SQL을 작성해야 해서 둘 다 SQL을 개발자가 쓴다. 하이버네이트는 SQL을 대부분 자동으로 만들어준다.
- 엔티티 상태를 메모리에서 추적하는 영속성 컨텍스트를 두고 변경 감지를 한다.
- 방언(Dialect): DB 종류(오라클, PostgreSQL, MariaDB 등)마다 SQL 문법이 조금씩 달라서, 어떤 DB를 쓰는지 하이버네이트에 알려줘야 그 DB에 맞는 SQL을 만든다.
- 하이버네이트를 직접 쓰면 코어, 트랜잭션 API 같은 의존성을 일일이 가져와야 하고, 세션 팩토리를 만들고 세션을 열어서 `persist` 하는 코드를 직접 써야 한다. 조회도 복잡해지면 코드가 길어진다.

<details>
<summary>코드 보기 (하이버네이트 직접 사용 예시, 문서 기준)</summary>

```java
SessionFactory sessionFactory = new Configuration()
        .addAnnotatedClass(Event.class)
        .buildSessionFactory();

sessionFactory.inTransaction(session -> {
    session.persist(new Event("Our very first event!", LocalDateTime.now()));
});
```

</details>

### 스프링 데이터 JPA

- 하이버네이트를 한 단계 더 추상화해서 쉽게 쓰게 해주는 스프링의 기술이다. 구조는 맨 아래 JDBC, 그 위에 하이버네이트(JPA 구현체), 그 위에 스프링 데이터 JPA다.
- 문서에서 계속 쉽다는 말이 반복된다.
- 핵심은 엔티티만 만들고 레포지토리를 인터페이스로 선언만 하면 된다는 것이다.
  - `JpaRepository`를 상속하면 등록, 조회, 수정, 삭제(CRUD) 메서드를 다 제공한다.
  - 구현 클래스는 스프링이 자동으로 만들어서 주입해준다.
  - 기본 키 말고 다른 컬럼으로 조회하고 싶으면 파인더 메서드(쿼리 메서드)를 인터페이스에 선언만 하면 된다. 메서드 이름 기반으로 쿼리가 생성된다.
  - 페이지네이션(게시판처럼 10개씩 나눠 보여주기)과 감사 로깅 같은 반복적인 코드도 지원한다.
- 동적 쿼리가 필요하면 Querydsl 같은 외부 라이브러리를 쓴다.
- 스프링 데이터 패밀리에는 JPA 말고도 몽고DB, 레디스, 카산드라, 네오포제이 등이 있다.
- 의존성은 `spring-boot-starter-data-jpa` 하나만 넣으면 하이버네이트 코어 등이 같이 따라온다. 로컬 테스트용으로는 H2를 쓰고, 운영에서는 MariaDB 같은 걸 쓸 예정이다.

<details>
<summary>코드 보기 (의존성)</summary>

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

</details>

### 엔티티 매핑 규칙

- 자바 클래스(엔티티)가 DB 테이블이 된다.
- 클래스로 만든 객체 하나는 테이블의 레코드(row) 한 줄이다.
- 클래스의 변수는 테이블의 컬럼에 매핑된다.
- 어노테이션
  - `@Entity`: 테이블과 연동되는 클래스
  - `@Table(name = "...")`: 테이블 이름 지정. 없으면 클래스 이름이 테이블 이름이다.
  - `@Id`: 기본키(각 row를 구분하는 고유한 키)
  - `@GeneratedValue`: 기본키 값을 순차적으로 자동 생성
  - `@Column(name = "...", nullable = false)`: 컬럼 이름이나 null 허용 여부 설정. 안 붙여도 변수명이 컬럼명이 된다.

<details>
<summary>코드 보기 (엔티티 예시)</summary>

```java
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime date;
}
```

</details>

### 하이버네이트 관련 설정

- `show_sql`: 하이버네이트가 만든 SQL을 로그에 출력한다.
- `format_sql`: 출력할 때 보기 좋게 포맷한다.
- `hbm2ddl.auto`를 `create`로 두면 엔티티 클래스 정보를 기반으로 테이블을 만들어주는데, 애플리케이션이 새로 시작될 때마다 기존 걸 drop하고 새로 만든다.

### H2 DB 설정

- H2는 따로 설정 안 해도 스프링이 자동으로 접속 정보를 만들어준다. 그런데 DB 이름이 실행할 때마다 랜덤 문자열로 바뀌는 게 불편해서, `application-test.properties`에 직접 지정해서 이름을 고정했다.
- 재시작해서 로그를 보니 접속 URL의 DB 이름이 `testdb`로 바뀐 걸 확인했다.

<details>
<summary>코드 보기 (application-test.properties)</summary>

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
```

</details>

---
#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
