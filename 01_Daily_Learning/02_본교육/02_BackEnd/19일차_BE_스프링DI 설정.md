# [Day 19] LG CNS AM Inspire Camp 7기 TIL
작성일: 2026.11.20 (금) | 과목: AI 서비스 개발을 위한 백엔드

## 오늘 학습한 주제
- 스프링 테스트에서 컨테이너 생성과 설정 파일 위치 지정하기
- `@Autowired`, `@Resource`, `@Qualifier`로 빈 주입받기
- 컬렉션(List, Set, Map) 주입
- 전략 1(XML만 사용)의 문제점
- 전략 2 = 어노테이션 + XML의 `component-scan`
- 전략 2 실습: setter 방식, 생성자 방식
- properties 파일로 자주 바뀌는 값 분리하기
- 전략 3 = XML 없이 자바 설정 클래스 (`@Configuration`, `@Bean`, `@ComponentScan`)

## 스프링에서 의존 객체를 설정하고 주입하는 전략 3가지

| 전략 | 설정 방식 | 주입 방식 |
|---|---|---|
| 전략 1 | 전부 XML | setter 주입, 생성자 주입 |
| 전략 2 | 클래스에 어노테이션 + XML에는 component-scan만 | 필드(setter 역할) 주입, 생성자 주입 |
| 전략 3 | XML 없이 자바 설정 클래스 | 어노테이션 방식 + `@Bean` 직접 생성 |

---

## 1. 스프링 테스트에서 컨테이너 만들기

기존 JUnit 테스트에서는 컨테이너를 직접 만들고 `getBean`으로 빈을 꺼내 쓰는 과정을 매번 반복했다. 이 반복을 줄이려고 JUnit 기반으로 스프링이 만든 게 스프링 테스트다.

### 쓰는 방법

<details>
<summary>코드 보기</summary>

```java
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:spring-beans-annot.xml")
public class AnnotatedHelloBeanTest {
    ...
}
```

</details>

- `@ExtendWith(SpringExtension.class)`: JUnit을 확장해서 컨테이너 객체를 대신 생성해준다. 컨테이너 생성을 우리가 안 하게 되는 부분이다.
- `@ContextConfiguration`: XML 설정 파일이 어디 있는지 알려주는 어노테이션이다. `locations` 속성에 `classpath:...` 형태로 쓴다. 기존에 컨테이너 만들 때 XML 경로 줬던 것과 똑같은 역할이다.
- 전략 3까지 가면 XML을 안 쓰니까 이 자리에 `classes` 속성으로 자바 설정 클래스를 지정하게 된다고 한다.
- 컨테이너는 XML을 읽고 파싱해서 객체를 만들고 메모리(컨텍스트)에 적재해둔다.

### assertEquals와 static import

`Assertions`는 JUnit이 제공하는 유틸리티 클래스고 안에 검증용 메서드가 전부 static이다. 값 비교, null 체크 같은 걸 콘솔에 찍어서 눈으로 보는 대신 이런 메서드로 판별한다.

- `import static org.junit.jupiter.api.Assertions.assertEquals;` 처럼 static을 붙여서 import하면 `Assertions.assertEquals`가 아니라 `assertEquals`만 써도 된다. `Assertions.*`로 전부 가져올 수도 있다.
- 테스트가 성공하면 초록색, 실패하면 빨간색으로 나온다. 실행은 `Ctrl + F11`.
- 테스트 메서드 이름은 아무거나 써도 된다.
- 특정 테스트 메서드를 잠깐 꺼두고 싶으면 주석 처리 말고 `@Disabled`를 붙인다.

---

## 2. @Autowired

### 개념

DI에서 인젝션은 주사기로 주사액을 몸에 넣는 그림으로 많이 표현한다. 우리가 의존하는 객체를 변수에 넣어달라는 뜻이다. `@Autowired`는 그 이름처럼 자동으로 찾아서 묶어준다.

- 의존하고 싶은 클래스 타입으로 변수를 선언하고 그 위에 `@Autowired`를 붙이면 끝이다.
- 이게 `getBean("hello", Hello.class)`를 대신하는 거다. `getBean`은 컨테이너한테 빈을 달라고 요청하는 메서드였는데 그걸 안 쓰게 된다.
- 변수 위에 붙이는 순간 컨테이너가 알아서 찾아서 주입해준다.

### 전략 1에서 쓴 클래스

전략 1 실습용 클래스의 핵심만 보면 이렇다. `Hello`는 기본 생성자, 인자가 있는 생성자, setter를 다 가지고 있고, 각 메서드가 호출될 때 로그를 찍게 해서 어떤 방식으로 주입되는지 눈으로 확인할 수 있다. `Printer`는 `print(String message)` 하나만 있는 인터페이스고, 구현체가 `ConsolePrinter`와 `StringPrinter` 두 개다.

<details>
<summary>Hello 핵심</summary>

```java
public class Hello {
    String name;
    Printer printer;
    List<String> names;

    public Hello() {
        System.out.println(this.getClass().getName() + " 기본생성자 호출됨!");
    }

    public Hello(String name, Printer printer) {
        System.out.println(this.getClass().getName() + " 오버로딩 생성자 호출됨!");
        this.name = name;
        this.printer = printer;
    }

    public void setName(String name) {
        System.out.println("setName() 메서드 호출됨 " + name);
        this.name = name;
    }

    public void setPrinter(Printer printer) {
        System.out.println("setPrinter() 메서드 호출됨 " + printer.getClass().getName());
        this.printer = printer;
    }

    public String sayHello() {
        return "Hello " + name;
    }

    public void print() {
        this.printer.print(sayHello());
    }
}
```

</details>

<details>
<summary>Printer 구현체 핵심 (각각 별도 파일)</summary>

```java
public class ConsolePrinter implements Printer {
    public void print(String message) {
        System.out.println(message);          // 바로 콘솔에 출력
    }
}

public class StringPrinter implements Printer {
    private StringBuffer buffer = new StringBuffer();

    public void print(String message) {
        this.buffer.append(message);          // 버퍼에 쌓아둠
    }

    public String toString() {
        return this.buffer.toString();        // 쌓인 내용 확인용
    }
}
```

</details>

### 대원칙은 타입 기준

`@Autowired`는 타입으로 찾는다. 그런데 같은 타입이 여러 개일 때가 문제다.

실습 상황은 이랬다. XML에 `Hello` 타입 빈이 두 개 있다.

- id가 `hello`인 빈 (setter 주입으로 만든 것)
- id가 `helloConst`인 빈 (생성자 주입으로 만든 것)

<details>
<summary>코드 보기</summary>

```java
@Autowired
Hello hello;
```

</details>

이 경우 변수명이 `hello`라서, 같은 타입이 여러 개여도 id가 변수명과 같은 `hello` 빈이 우선 선택된다. 정답은 setter로 만든 쪽이었다. (처음에 채팅으로 1번 2번 찍었는데 근거는 id와 변수명 일치였다.)

변수명을 `helloOne`으로 바꾸고 돌려보면 에러가 난다. 타입은 맞지만 `helloOne`이라는 id의 빈이 없어서 `UnsatisfiedDependencyException`, `NoSuchBeanDefinitionException` 같은 게 터진다. 메시지에 qualifying bean이 없다고 나온다.

### 변수명 안 맞추고 특정 빈 받고 싶을 때: @Qualifier

<details>
<summary>코드 보기</summary>

```java
@Autowired
@Qualifier("helloConst")
Hello helloOne;
```

</details>

- `@Qualifier`는 단독으로 쓰지 않고 `@Autowired`와 세트로 쓴다.
- 후보가 여러 개일 때 특정 빈 하나를 지목하는 한정자다.
- 변수명이 id와 달라도 `@Qualifier`에 id를 적으면 그 빈이 들어온다.
- 검증은 `assertEquals`로 `hello.sayHello()` 결과가 기대한 문자열과 같은지 봤다.

### 주입 대상 찾는 순서 정리

1. 타입이 일치하는 빈을 찾는다.
2. 후보가 여러 개면 `@Qualifier`나 `@Primary`를 확인한다.
3. 그래도 안 정해지면 변수명이 빈 id와 같은 걸 찾는다.
4. 그래도 없으면 예외가 터진다.

### 주입이 잘 됐는지 확인하는 방법

`name` 변수는 `sayHello()`로 확인했다. `printer` 변수는 `print()` 메서드가 `this.printer.print(sayHello())`를 부르니까 `print()`를 호출해서 확인한다. 이때 `printer`가 `ConsolePrinter`면 `print`가 호출되면서 바로 콘솔에 메시지가 찍힌다. 빈을 만나는 순간 객체가 생성되고(생성자 로그) 주입되는 흐름을 로그로 확인할 수 있었다.

### 선언만 해도 에러가 나는 이유

테스트 메서드가 비어 있거나 아예 `@Autowired` 변수를 한 번도 안 불러도, 변수를 선언하고 어노테이션을 붙이는 순간 컨테이너가 런타임에 id와 타입에 맞는 빈을 찾으러 간다. 그래서 잘못된 선언이면 호출 안 해도 에러가 난다. `@Disabled`로 테스트 메서드를 꺼도 필드 쪽 에러는 그대로 난다.

---

## 3. @Resource

`@Autowired`랑 역할은 같다. 내가 의존하는 빈을 찾아서 넣어준다. 차이는 만든 쪽과 찾는 기준이다.

<details>
<summary>코드 보기</summary>

```java
@Resource(name = "stringPrinter")
PrinterBean printerResource;
```

</details>

- 패키지가 `javax.annotation`이다. 자바 진영 표준이고 예전에는 J2EE, 지금은 Jakarta EE라고 부른다.
- 우리는 스프링 5 버전이라 `javax` 패키지를 쓰고, 스프링 부트에서는 Jakarta 기반이라 `jakarta.` 패키지로 바뀐다고 하셨다.
- `name` 속성에 빈 id를 적어서 id로 찾는다.

### Autowired와 Resource 비교

| 구분 | @Autowired | @Resource |
|---|---|---|
| 만든 쪽 | 스프링 | 자바 표준 |
| 찾는 기준 | 타입 우선 | 이름(id) 우선 |
| 같은 타입 여러 개일 때 | `@Qualifier`, `@Primary` | `name` 속성으로 지정 |
| 붙일 수 있는 위치 | 필드, setter, 생성자, 파라미터 | 필드, setter 메서드만 |
| 생성자 주입 | O | X |
| 컬렉션 주입 | O | 제한적 |
| 선택적 의존(required=false) | O | X |

기억할 건 `@Autowired`는 타입, `@Resource`는 아이디. 그리고 생성자 주입에는 `@Resource`를 못 쓴다.

---

## 4. 컬렉션 주입 (List, Set, Map)

`Hello` 클래스에 `List<String> names`와 `setNames`가 있었다. XML에서는 `<property>` 안에 하위 태그가 들어가야 해서 시작 태그와 끝 태그를 둘 다 써야 한다.

<details>
<summary>코드 보기</summary>

```xml
<property name="names">
    <list>
        <value>스프링 프레임워크</value>
        <value>스프링 부트</value>
        <value>스프링 클라우드</value>
        <value>스프링 부트</value>
    </list>
</property>
```

</details>

- 타입이 `List`면 `<list>`, `Set`이면 `<set>`, `Map`이면 `<map>` 안에 `<entry>`를 쓴다.
- 하위 엘리먼트 없이 `<property name="names"/>`만 쓰면 에러가 난다.
- 값이 문자열 같은 자바 기본 타입이면 `<value>`, 다른 빈이면 `<ref bean="...">`를 쓴다.

### List와 Set 차이

- List: 순서를 유지하고, 중복을 허용한다.
- Set: 순서를 보장하지 않고(넣은 순서대로 안 나옴), 중복을 허용하지 않는다.

### 테스트

처음엔 `for`문으로 콘솔에 찍어서 중복된 값이 그대로 나오는 걸 확인했고, 나중에는 `assertEquals`로 바꿨다.

<details>
<summary>코드 보기</summary>

```java
assertEquals(4, hello.getNames().size());
assertEquals("스프링 프레임워크", hello.getNames().get(0));
```

</details>

---

## 5. 전략 1의 문제점 (XML만 쓸 때)

전략 1은 초창기 스프링 방식이고 모든 설정을 XML에 한다. 이 방식이 XML hell이라고 불리는 이유는 이렇다.

- XML을 조금이라도 잘못 쓰면 그 XML을 읽는 모든 테스트가 같이 터진다. 수업 중에 참조하는 빈 id를 일부러 틀리게 써봤는데, 그 빈이랑 상관없는 테스트까지 전부 에러가 났다.
- 여러 개발자가 같은 XML 파일을 공유해야 한다. SVN 같은 버전 관리에 올려서 서로 받아 쓰는데, 남이 만든 부분을 건드려서 실수가 생기면 그게 그대로 올라가서 다른 사람도 같이 막힌다.
- 빈 개수가 늘수록 파일이 커지고 관리가 어렵다. 빈 id가 다른 사람이랑 겹치면 안 돼서 계속 확인해야 한다.
- 빈으로 쓸 클래스에 setter나 생성자가 반드시 있어야 한다. 직접 주석 처리해서 지워봤더니 바로 에러가 났다.

장점도 있다. 설정이 전부 XML에 모여 있어서 그래프 뷰로 빈 사이의 의존 관계를 한눈에 볼 수 있다. 그래서 여러 명이 개발하는 단계에서는 불편하지만, 한두 명이 유지보수하는 운영 단계에서는 괜찮은 방식이라고 한다.

---

## 6. 전략 2: 어노테이션 + XML 일부

XML을 최대한 줄이고, 설정 정보를 각 클래스 안에 어노테이션으로 넣는다. XML에는 어노테이션 붙은 클래스가 어느 패키지에 있는지만 알려주면 된다.

### XML 태그를 어노테이션이 어떻게 대체하는지

| 전략 1 XML | 전략 2 어노테이션 |
|---|---|
| `<bean id="..." class="...">` | 클래스 위에 `@Component` |
| `<property name="name" value="...">` | 변수 위에 `@Value("...")` |
| `<property name="printer" ref="...">` | 변수 위에 `@Autowired` + `@Qualifier` (또는 `@Resource`) |

### @Component

- 클래스 위에 붙이면 스프링 빈이라는 표시다.
- id를 따로 안 주면 클래스 이름의 첫 글자를 소문자로 바꾼 게 id가 된다. `HelloBean`이면 `helloBean`. 뒤에 대문자는 그대로 유지한다.
- id를 주고 싶으면 `@Component("stringPrinter")`처럼 쓴다.
- XML의 `<bean>` 태그를 대체하는 건데 이름이 `@Bean`이 아니고 `@Component`인 이유는, `@Bean`이 전략 3에서 따로 쓰이는 어노테이션이라서다. 역할은 비슷한데 선언 위치가 다르다.

### 중요!!!! 선언만으로는 안 된다. component-scan이 필요하다

클래스에 `@Component`만 붙인다고 컨테이너가 알아서 찾지 않는다. 어노테이션이 붙은 클래스가 있는 패키지를 XML에 알려줘야 컨테이너가 그 패키지를 스캔해서 빈으로 등록한다.

<details>
<summary>코드 보기</summary>

```xml
<!-- Component Auto Scanning 을 위한 설정 -->
<context:component-scan base-package="myspring.di.annotation" />
```

</details>

### 네임스페이스

새 XML 설정 파일을 만들 때 Spring Bean Configuration File에서 `beans`와 `context` 스키마를 선택했다. XML 맨 위에 보면 `xmlns`로 네임스페이스가 선언돼 있다.

- `beans`는 접두어 없는 디폴트 네임스페이스라서 `<bean>`처럼 접두어 없이 쓴다.
- `context`는 따로 접두어를 붙여서 `<context:component-scan>`처럼 쓴다.
- 네임스페이스를 두는 이유는 자바에서 패키지가 다르면 클래스 이름이 같아도 되는 것과 같다. 기능별로 태그 이름이 겹치지 않게 공간을 나눈 거다.

### component-scan이 하는 일

클래스패스에서 어노테이션이 붙은 컴포넌트를 찾아서 스프링 빈으로 자동 등록한다. 기본적으로 `@Component`가 붙은 걸 찾는다.

### 스테레오타입 어노테이션

전부 `@Component`와 같은 건데 역할을 표시해주는 용도다.

- `@Repository`: DB 연동하는 클래스
- `@Service`: 비즈니스 로직, 트랜잭션 처리 하는 클래스
- `@Controller`: 웹 화면과 연동되는 가장 앞단, 요청을 받아서 뒷단으로 넘기는 클래스
- `@RestController`: REST API 만드는 클래스
- `@ControllerAdvice`: 컨트롤러들의 공통 예외 처리 같은 걸 맡는 클래스. 관점 지향 프로그래밍 이야기에서 코드 재사용 방법 중 하나로 나온다(함수, 상속, 어드바이스 순). 만들어두면 스프링이 알아서 불러준다.

스캔 대상도 전부 같은 컴포넌트라서 `@Component` 하나로 다 해도 되지만, 역할이 드러나면 묵시적인 약속이 돼서 읽기 편하다.

### 전략 2에서 @Autowired 쓸 때 주의

`PrinterBean` 인터페이스를 구현한 클래스가 `ConsolePrinterBean`, `StringPrinterBean` 두 개라서 타입으로만 찾으면 후보가 두 개가 된다. 그래서 `@Qualifier`로 지목해줘야 한다. 하나뿐이면 그냥 자동으로 찾아준다.

---

## 7. 전략 2 실습 정리

### 준비 작업

- 기존 `xml` 패키지를 통째로 복사해서 `annotation` 패키지를 새로 만들었다. 클래스 이름이 같으면 수업 중에 헷갈려서, 이름 뒤에 `Bean`을 붙여서 `Hello` -> `HelloBean`, `ConsolePrinter` -> `ConsolePrinterBean`, `StringPrinter` -> `StringPrinterBean`, `Printer` -> `PrinterBean`으로 바꿨다.
- 어노테이션 방식은 setter가 필요 없어서 `setName`, `setPrinter`를 지웠다. `setNames`는 그대로 뒀다.
- 설정 파일도 새로 `spring-beans-annot.xml`을 만들었다. `beans`와 `context` 네임스페이스만 선택해서 만들었고, 처음에는 위에서 본 `component-scan` 한 줄만 넣었다. XML이 이렇게 확 줄어든다. (properties 설정은 9번에서 추가했다.)

### 코드 개념

전략 2의 setter 주입 방식 클래스는 `HelloBean`이다. 변수마다 어노테이션을 붙여서 각각 주입받는다. 어노테이션이 XML의 어떤 태그를 대체하는지 주석으로 적어둔 게 핵심이다.

<details>
<summary>PrinterBean 구현체 핵심 (각각 별도 파일)</summary>

```java
@Component("consolePrinter")
public class ConsolePrinterBean implements PrinterBean {
    public void print(String message) {
        System.out.println(message);
    }
}

@Component("stringPrinter")
public class StringPrinterBean implements PrinterBean {
    private StringBuffer buffer = new StringBuffer();

    public void print(String message) {
        this.buffer.append(message);
    }

    public String toString() {
        return this.buffer.toString();
    }
}
```

</details>

<details>
<summary>HelloBean 핵심 (setter 방식)</summary>

```java
//@Component <=> <bean></bean>
@Component
public class HelloBean {
    //@Value <=> <property name="name" value="어노테이션" />
    @Value("${app.setter.name}")
    String name;

    //@Autowired <=> <property name="printer" ref="stringPrinter" />
    @Autowired
    @Qualifier("stringPrinter")
    PrinterBean printer;

    @Value("#{'${app.namelist}'.split(',')}") //Java,SpringFW,SpringBoot
    List<String> names;

    public String sayHello() {
        return "Hello " + name;
    }

    public void print() {
        this.printer.print(sayHello());
    }
}
```

</details>

- `@Component`: HelloBean은 id를 생략해서 `helloBean`이 된다.
- StringPrinterBean과 ConsolePrinterBean은 id를 `stringPrinter`, `consolePrinter`로 명시적으로 줬다. 다른 데서 `@Qualifier`로 참조해야 해서 id를 확실히 해둔 거다.
- `@Value`: setter 없이 변수에 바로 값이 들어간다. 전에 XML의 `<property name="name" value="...">`와 같은 효과다. (지금은 값을 properties에서 가져오게 해놨고 이건 9번에서 정리했다.)
- `@Autowired + @Qualifier`: 전에 `<property ref>` 쓰던 걸 대체한다. 후보가 `consolePrinter`, `stringPrinter` 둘이라서 `@Qualifier("stringPrinter")`로 하나를 지목했다.

### 테스트 케이스

<details>
<summary>AnnotatedHelloBeanTest 핵심</summary>

```java
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:spring-beans-annot.xml")
public class AnnotatedHelloBeanTest {
    @Autowired
    HelloBean hello;

    @Autowired
    PrinterBean stringPrinter;          // 변수명을 빈 id와 맞춤

    @Autowired
    @Qualifier("stringPrinter")
    PrinterBean printer;                // @Qualifier로 지목

    @Resource(name = "stringPrinter")
    PrinterBean printerResource;        // @Resource로 id 지목

    @Test
    void hello() {
        assertEquals("Hello 어노테이션", hello.sayHello());
        hello.print();

        assertEquals("Hello 어노테이션", stringPrinter.toString());
        assertEquals("Hello 어노테이션", printer.toString());
        assertEquals("Hello 어노테이션", printerResource.toString());
    }
}
```

</details>

- `hello()` 테스트에서 `hello.sayHello()`로 `name`이 잘 주입됐는지 확인했다.
- `hello.print()`를 부르면 `HelloBean`이 주입받은 `StringPrinterBean`의 버퍼에 메시지가 쌓인다. 그 다음에 프린터를 주입받은 변수 3개의 `toString()`이 전부 `Hello 어노테이션`으로 같게 나온다. 같은 빈(싱글톤)이 주입된 거라서 그렇다.
- 프린터는 `PrinterBean`처럼 상위 인터페이스 타입으로 받아도 된다. `@Qualifier`나 id로 `stringPrinter`를 지목했기 때문에 실제로는 `StringPrinterBean` 객체가 들어온다.
- 로그를 보면 클래스마다 기본 생성자가 호출되는 게 찍혔다. 객체 생성이 잘 되고 있다는 뜻이다.

### 같은 타입 후보가 둘일 때 에러가 나는 상황 직접 확인

테스트 클래스에서 `PrinterBean printer`를 `@Autowired`만 붙여서 받아보니까 에러가 났다. `NoUniqueBeanDefinitionException`이고, `PrinterBean` 타입 빈이 하나가 아니라 둘이라는 내용이다. 해결 방법은 여러 개라 다 해봤고, 위 테스트 코드의 변수 세 개가 각각 그 방법이다.

1. 변수명을 빈 id와 맞추기: `PrinterBean stringPrinter`로 바꾸면 된다.
2. `@Qualifier("stringPrinter")` 추가하기. 가장 직관적이라 권장하셨다.
3. `@Resource(name = "stringPrinter")` 쓰기.
4. `@Primary` 쓰기. (테스트에서는 안 써봤다)

### 이게 setter 주입인가 생성자 주입인가

변수마다 따로따로 주입받는 지금 방식은 setter 주입에 해당한다. setter 메서드를 지웠지만 변수 하나하나에 각각 주입한다는 점에서 같은 개념이다. 반대로 생성자를 호출하면서 한꺼번에 주입받는 게 생성자 주입이다.

---

## 8. 전략 2에서 생성자 주입 (어노테이션 방식)

### 클래스 새로 만든 이유

`HelloBean`은 변수에 직접 주입하는 방식이라, 같은 변수를 생성자로도 초기화하면 코드가 섞여서 헷갈린다. 그래서 `HelloBeanCons` 클래스를 따로 만들었다. 기본 생성자는 없애고 인자가 있는 생성자만 둔다. 같은 패키지에 있으니까 XML은 수정할 게 없다.

### 코드 개념

<details>
<summary>HelloBeanCons 핵심 (생성자 방식)</summary>

```java
@Component
public class HelloBeanCons {
    String name;
    PrinterBean printer;
    List<String> names;

    @Autowired
    public HelloBeanCons(
            @Value("${app.constructor.name}") String name,
            @Qualifier("consolePrinter") PrinterBean printer) {
        this.name = name;
        this.printer = printer;
    }

    @Value("#{'${app.namelist}'.split(',')}")
    public void setNames(List<String> list) {
        this.names = list;
    }
}
```

</details>

- 생성자 위에 `@Autowired`를 붙인다.
- 값 주입은 파라미터 앞에 `@Value`를 붙인다.
- 어떤 빈을 넣을지 지정하는 `@Qualifier`도 파라미터 앞에 쓴다. 여기서는 `consolePrinter`를 지목했다.
- `names`는 생성자 인자가 아니라서 `setNames` 메서드 위에 `@Value`를 붙였다. 이렇게 setter 메서드 위에 붙여도 된다.
- `@Resource`는 생성자에 못 쓰니까 이 방식에서는 `@Autowired`를 써야 한다.

### 왜 @Value가 파라미터에 되는지: Target과 Retention

`@Value` 소스를 열어보면 `@Target`에 필드, 메서드, 파라미터가 있다. 그래서 변수 위에도, 파라미터 앞에도 붙일 수 있다. `@Autowired`는 생성자, 메서드, 파라미터, 필드가 다 가능하다. `@Resource`는 클래스, 필드, 메서드만 가능해서 생성자에는 안 된다.

`@Retention(RUNTIME)`도 같이 봤다.

- `@Override`는 retention이 SOURCE이고 컴파일 타임에 오버라이딩 규칙(메서드 선언부가 부모와 같은지)을 검사해주는 어노테이션이다.
- 스프링 쪽 어노테이션은 대부분 RUNTIME이라서 실행 중에 동작한다.

### 테스트

<details>
<summary>코드 보기</summary>

```java
@Autowired
HelloBeanCons helloCons;

@Test
void hello_constructor() {
    assertEquals("Hello 어노테이션생성자", helloCons.sayHello());
    helloCons.print();

    assertEquals(3, helloCons.getNames().size());
    assertEquals("SpringBoot", helloCons.getNames().get(2));
}
```

</details>

- `helloCons.sayHello()`가 `Hello 어노테이션생성자`인지 확인해서 생성자로 `name`이 잘 들어갔는지 봤다.
- 프린터는 `consolePrinter`를 지목했다. `ConsolePrinterBean`은 `print`가 호출되자마자 바로 콘솔에 찍기 때문에 `toString` 같은 검증용 메서드가 없다. 그래서 `helloCons.print()`를 호출했을 때 콘솔에 `Hello 어노테이션생성자`가 뜨면 주입이 잘 된 거다.
- `names`는 크기가 3이고, 인덱스 2번이 `SpringBoot`인지 확인했다.

### 정리

- 전략 2는 어노테이션과 XML(component-scan만)을 같이 쓰는 방식이다.
- 필드나 setter 위치에 붙이면 setter 주입에 해당한다. 변수 단위로 따로 주입받는다.
- 생성자 위에 `@Autowired`, 파라미터에 `@Value`, `@Qualifier`를 쓰면 생성자 주입이다. 한꺼번에 주입받는다.

---

## 9. properties 파일로 자주 바뀌는 값 분리하기

### 왜 하냐

`@Value("어노테이션")`처럼 값을 소스에 박아두면 값이 바뀔 때마다 소스를 고쳐야 한다. 특히 환경에 따라 달라지는 값(DB 접속 정보 같은 것)은 더 문제다. 그래서 자주 바뀌는 값은 properties 파일로 빼고, 소스에는 키만 적는다.

### 실습: values.properties

resources 밑에 `values.properties` 파일을 만들고 키를 넣었다.

<details>
<summary>values.properties</summary>

```properties
app.setter.name=\uC5B4\uB178\uD14C\uC774\uC158
app.constructor.name=\uC5B4\uB178\uD14C\uC774\uC158\uC0DD\uC131\uC790
app.namelist=Java,SpringFW,SpringBoot
```

</details>

한글을 입력하면 이클립스가 알아서 유니코드로 바꿔준다. 위 파일의 값은 풀어보면 `app.setter.name`이 `어노테이션`, `app.constructor.name`이 `어노테이션생성자`다.

소스에서는 `${키}` 형태로 값을 가져온다. `HelloBean`의 `name`은 `${app.setter.name}`, `HelloBeanCons` 생성자의 `name` 파라미터는 `${app.constructor.name}`을 쓴다. (코드는 7번, 8번에 있다.)

### XML에서 properties 파일 알려주기

properties 파일만 만든다고 되는 게 아니라, XML에 이 파일이 어디 있는지 알려줘야 한다. `spring-beans-annot.xml`에 `property-placeholder` 설정을 추가했다.

<details>
<summary>spring-beans-annot.xml 핵심</summary>

```xml
<!-- values.properties 파일 정보를 설정 -->
<context:property-placeholder location="classpath:values.properties" />

<!-- Component Auto Scanning 을 위한 설정 -->
<context:component-scan base-package="myspring.di.annotation" />
```

</details>

- 이 태그가 있어야 `${...}` 치환자가 동작한다.
- 이 설정을 하면 컨테이너(애플리케이션 컨텍스트) 안에 `PropertySourcesPlaceholderConfigurer` 객체가 등록돼서 키-값을 메모리에 올려둔다.
- 스프링 부트에서는 이런 설정이 이미 내부에 돼 있어서 properties 파일만 채우면 된다고 한다.

### List 값을 properties에서 가져오기: SpEL

문자열 하나를 리스트로 바꿔서 `List<String>` 변수에 넣는 코드다. `HelloBean`에서는 필드에, `HelloBeanCons`에서는 `setNames` 메서드에 붙였다.

<details>
<summary>코드 보기</summary>

```java
@Value("#{'${app.namelist}'.split(',')}")
List<String> names;
```

</details>

하나씩 풀면 이렇게 된다.

1. `${app.namelist}`: properties에서 키 값을 문자열로 가져온다.
2. 앞뒤를 홑따옴표 `'...'`로 감싼다. SpEL 안에서 이걸 문자 리터럴로 취급하게 하려는 거다.
3. `.split(',')`: 쉼표 기준으로 잘라서 문자열 배열을 만든다. String의 `split` 메서드를 그대로 쓰는 거다.
4. 전체를 `#{ ... }`로 감싼다. 이 안은 스프링 표현 언어인 SpEL로 평가하겠다는 뜻이다. 런타임에 값을 계산하거나 조작할 수 있게 해준다.
5. 결과로 나온 배열을 `List<String>` 타입 변수에 맞게 스프링이 자동 변환해서 넣어준다.

### 테스트

8번의 `hello_constructor()`에서 확인했다. `app.namelist`에 값이 3개 들어 있으니 `size()`가 3이고, 인덱스 2번이 `SpringBoot`인지 `assertEquals`로 본다. 반복문으로 찍어보는 것보다 `assertEquals`로 크기와 특정 인덱스 값을 보는 게 낫다.

---
#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
