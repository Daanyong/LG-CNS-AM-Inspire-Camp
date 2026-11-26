# [Day 23]  LG CNS AM Inspire Camp 7기 TIL
작성일: 2026.11.26 (목) | 과목: AI 서비스 개발을 위한 백엔드

## 오늘 학습한 주제

- Gradle과 YAML 조합 프로젝트
- DataSource와 DB 메타 정보 확인
- H2 콘솔 접속과 404 에러
- JPA 엔티티 만들기
- ID 생성 전략
- MariaDB 연결
- JDBC 드라이버와 JDBC에서 JPA까지의 흐름
- DDL, DML, DCL, TCL
- ddl-auto 옵션 비교 실험
- SQL 실습: employees, departments 테이블
- 바이브 코딩 환경 준비: Antigravity

---

## 1. Gradle과 YAML 조합 프로젝트

지금까지는 Maven에 properties 파일 조합으로 했는데, 오늘은 같은 예제를 Gradle과 YAML 조합으로 한 번 더 열어봤다. 소스는 거의 똑같고 빌드 도구와 설정 파일 포맷만 다르다. 처음 시작할 때는 Maven + properties 조합을 많이 쓰고, Gradle은 안드로이드 앱(코틀린)에서 많이 쓴다고 한다.

### pom.xml과 build.gradle 비교

- `pom.xml` 역할을 `build.gradle`이 하고, XML 대신 JSON 비슷한 느낌의 문법이다
- `settings.gradle`에 루트 프로젝트 이름이 들어 있다
- 플러그인 정보, 자바 소스 호환성(JDK 버전)이 적혀 있다
- Maven에서는 저장소를 따로 지정하지 않았는데 Gradle에서는 `repositories`에 `mavenCentral()`로 소스를 내려받는 곳을 지정한다
- 일반 의존성은 `implementation`으로 주고, 롬복은 `compileOnly`와 `annotationProcessor`로 준다
- 개발할 때만 필요한 의존성은 `developmentOnly`로 준다. devtools가 이런 경우인데, package.json의 devDependencies나 타입스크립트와 비슷한 개념이다. 타입스크립트도 컴파일해서 JS로 바꿔서 실행하니까 운영에는 필요 없는 의존성이고, 나중에 jar로 묶을 때 개발 의존성은 포함되지 않는다
- 로그백을 제외하는 것도 Maven은 `exclusion`을 썼는데 Gradle은 `configurations.all`에서 `exclude group`으로 한다. 문법만 다르고 개념은 같다
- 같은 설정이 Maven보다 훨씬 짧고 속도도 Gradle이 더 빠르다고 한다
- 다만 Maven은 의존성이 계층 구조로 어떻게 숨어 있는지 Ctrl 눌러서 따라가 볼 수 있어서 처음 배울 때는 Maven이 낫고, Gradle은 나중에 한 번 써보는 걸 추천하셨다
- 래퍼 파일도 `mvnw` 대신 `gradlew`를 쓴다

### application.yml 문법

properties 대신 `application.yml`을 쓴다.

- `key: value` 형태이고 콜론 뒤에 반드시 공백 한 칸이 있어야 한다
- 계층은 들여쓰기로 표현하고 공백 두 칸씩 쓴다. 탭 문자는 문법 오류가 날 수 있어서 쓰지 말라고 한다. 들여쓰기가 안 맞으면 오류가 난다
- 주석은 `#`
- 리스트는 하이픈 뒤에 공백 한 칸을 두고 값을 쓴다. JSON 배열처럼 `[]`로 쓸 수도 있지만 하이픈 방식을 더 권장한다
- 문자열은 큰따옴표나 작은따옴표로 감쌀 수 있다
- `---` 구분자를 쓰면 한 파일 안에서 문서를 나눌 수 있다. 물리적으로는 파일 하나인데 별도 파일처럼 인식돼서, 기존에 `application.properties`, `application-test.properties`, `application-prod.properties`로 나눠 쓰던 걸 한 파일에 쓸 수 있다
- 프로파일별 구역은 `spring.config.activate.on-profile`로 지정한다
- 이 YAML 형식은 뒤에서 배울 도커 컴포즈, 깃허브 액션, 쿠버네티스에서도 그대로 쓰니까 미리 익혀두면 좋다


### Gradle 빌드와 jar 실행

- 환경변수(JAVA_HOME, Path)를 세팅한 터미널에서 `gradlew build`를 한다.
- 테스트 설정을 H2(메모리 DB)로 다시 돌리니 성공했다. 주석 처리해둔 url, driver-class-name, username 줄을 풀어줬다
- 결과물 위치는 Maven은 `target` 밑이었고 Gradle은 `build/libs` 밑에 jar가 생긴다
- jar 파일 이름은 `settings.gradle`의 루트 프로젝트 이름과 버전을 조합해서 만들어진다
- 실행은 똑같이 `java -jar 파일명.jar`이고 경로만 다르다. 포트를 8080으로 줘서 띄워봤다

<details>
<summary>빌드와 실행 명령</summary>

```bash
# 윈도우 커맨드 창
gradlew build

# 실행
java -jar build/libs/프로젝트이름-버전.jar
```

</details>

---

## 2. DataSource와 DB 메타 정보 확인

어제 만든 DbRunner에 코드를 일부 가져와서 지금 접속 중인 DB가 뭔지 로그로 찍어봤다. 앞으로 DB를 바꿔가며 쓸 거라서 확인용으로 만든 것이다.

- `DataSource`는 자바 표준(javax.sql)에서 제공하는 인터페이스이고, 스프링 부트가 기본으로 채택한 구현체가 `HikariDataSource`(Hikari 커넥션 풀)다
- 설명을 읽어보니 DataSource는 물리적인 데이터 소스와의 커넥션을 만들어주는 팩토리 역할이라고 한다. 팩토리는 특정 객체를 생성해주는 클래스를 부르는 말이라서 GoF 팩토리 메서드 패턴이나 스프링의 BeanFactory에도 팩토리가 붙는다
- `getConnection()`으로 `Connection`을 얻는다. Connection은 특정 DB와의 연결(세션)을 나타내고 commit, close 같은 트랜잭션 관련 기능도 있다. 트랜잭션은 뒤에서 나오니까 개념만 잡아두기
- `conn.getMetaData()`를 하면 `DatabaseMetaData`가 반환된다. 메타데이터는 데이터에 대한 데이터인데 홍길동전, 빠삐용 같은 작품들에 대해 "소설"이라고 하는 정보가 메타데이터라고 이해하면 된다
- DatabaseMetaData로 DB 제품 이름(오라클, MySQL 등), 버전, 드라이버 이름과 버전, 접속 URL, 사용자 이름, 트랜잭션 격리 수준, 테이블 관련 지원 여부 같은 엄청 많은 정보를 알 수 있다
- 실행해보니 H2일 때는 드라이버 이름이 H2 JDBC Driver, URL은 H2 메모리 DB 주소가 나왔다
- 사실 JDBC 연결 자체도 Hibernate가 다 해주기 때문에 우리가 직접 쓸 일은 없고 확인용이다

<details>
<summary>DbRunner에 넣은 코드</summary>

```java
try (Connection conn = dataSource.getConnection()) {
    DatabaseMetaData meta = conn.getMetaData();
    log.info("DB 제품명: {}", meta.getDatabaseProductName());
    log.info("DB URL: {}", meta.getURL());
    log.info("DB 사용자: {}", meta.getUserName());
    log.info("드라이버: {}", meta.getDriverName());
}
```

</details>

---

## 3. H2 콘솔 접속과 404 에러

- 브라우저에서 `localhost:8080`으로 들어가면 404 Whitelabel Error Page가 뜬다. 스프링 부트는 기본적으로 `static` 폴더 밑의 `index.html`을 보여주려고 하는데 파일이 없어서 나는 오류다
- `resources/static`에 `index.html`을 만들고(title, body에 hello 정도) 재시작하면 정상으로 뜬다.
- H2 콘솔은 웹 브라우저에서 접속한다. 이때 JDBC URL은 로그에 찍힌 현재 접속 URL(채팅으로 공유받은 것)을 써야 하고, 사용자 이름은 `sa`, 비밀번호는 없다
- 이전에 띄운 jar 프로세스가 같은 포트를 쓰고 있지 않은지도 확인해야 한다

---

## 4. JPA 엔티티 만들기

JPA를 시작할 때 가장 먼저 만드는 게 엔티티 클래스다. 엔티티 클래스를 만들고 DB에 접속하면 그 클래스를 기준으로 테이블이 만들어진다. 원래는 테이블(스키마)을 먼저 만드는 게 일반적인데 JPA는 반대 방향으로도 할 수 있다.

### 어노테이션 정리

- `@Entity`: 이 클래스는 DB 테이블과 매핑되는 클래스라는 뜻이다. JPA에서 제공하는 어노테이션이고, 스프링 부트 3 / 스프링 프레임워크 6부터는 패키지가 `javax.persistence`에서 `jakarta.persistence`로 바뀌었다. J2EE가 자카르타 EE로 이름이 바뀌었기 때문이다
- `@Id`: 이 변수가 PK(기본키)라는 뜻이다. 테이블의 각 행을 구분하는 유일한 값이 있어야 한다. 예전에는 주민번호를 PK로 쓰기도 했는데 지금은 순차적인 값을 PK로 쓰고, 주민번호 같은 건 중복되면 안 되는 unique 컬럼으로 따로 둔다
- 엔티티에 `@Id`를 안 달면 에러가 난다. PK를 지정 안 했다는 오류라는 걸 로그를 보고 맞출 수 있어야 한다
- `@GeneratedValue`: PK 값을 자동으로 생성해준다
- `@Table(name = "customers")`: 테이블 이름을 지정한다. 안 쓰면 클래스 이름이 테이블 이름이 된다
- `@Column`: 컬럼 상세 설정. 안 써도 필드는 전부 컬럼이 된다. 속성으로 `name`, `unique`, `nullable`, `insertable`, `updatable`, `length`, `precision`, `comment` 등이 있다. 기본값은 unique가 false, nullable이 true
- 자바의 camelCase는 DB에서 snake_case로 바뀐다. `customerId`는 `customer_id` 컬럼이 되고, 이름을 바꾸고 싶으면 `@Column(name = "...")`로 지정하면 된다
- 타입은 long 같은 기본형보다 `Long` 같은 래퍼 클래스를 쓰는 걸 권장한다
- 자바 타입과 DB 타입 매핑도 확인했다. `Long`은 `bigint`, `String`은 `varchar`(가변 문자열), `LocalDateTime`은 `datetime(6)`

### 만든 Customer 엔티티

- id: PK, 자동 생성
- customerId: 고객 번호(주민번호 같은 개념), `unique = true`, `nullable = false`
- customerName: 고객 이름, `nullable = false`
- createdAt: 등록 일시

게터, 세터도 만들어줬다. 이 상태로 실행하면 H2에 `customers` 테이블이 바로 생긴다. H2 콘솔에서 새로고침하면 테이블이 보이고 인덱스 탭에서 PK 제약조건과 customer_id unique 제약조건이 걸려 있는 것도 확인했다.

### 날짜 필드

- 자바 8에서 추가된 `java.time` 패키지를 쓴다. `LocalDate`는 연월일, `LocalDateTime`은 연월일시분초이고 `now()`로 현재 시간을 가져온다
- 필드 이름은 보통 `createdAt` 형태로 많이 짓는다
- `@Column(nullable = false, updatable = false)`로 등록 시점에 한 번 기록하고 이후에는 수정이 안 되게 했다
- 거기에 Hibernate에서 제공하는 `@CreationTimestamp`를 같이 붙여야 값이 들어간다. JPA가 아니라 Hibernate 어노테이션이다

<details>
<summary>Customer 엔티티 코드</summary>

```java
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String customerName;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // 게터, 세터
}
```

</details>

---

## 5. ID 생성 전략

`@GeneratedValue(strategy = GenerationType.xxx)`로 PK 자동 생성 방식을 고른다. DB 벤더마다 PK에 순차값을 채우는 방식이 달라서 전략도 여러 가지다.

- AUTO: 아무것도 설정 안 하면 이거다. persistence provider, 즉 Hibernate가 현재 DB에 맞는 전략을 알아서 고른다. H2와 MariaDB 둘 다 시퀀스 방식으로 만들었다
- IDENTITY: DB의 자동 증가 컬럼 기능을 쓴다. MySQL, MariaDB는 컬럼 옆에 `auto_increment`를 붙이는 방식이다. insert 쿼리를 실행한 직후에 식별자가 확보된다
- SEQUENCE: 테이블과 별개로 시퀀스라는 객체를 만들어두고 `nextval` 같은 함수로 값을 가져온다. 오라클, PostgreSQL 같은 DB에서 쓴다
- TABLE: 키 생성만 전담하는 별도 테이블을 만들어두고 거기서 순차값을 가져온다
- UUID: 128비트 범용 고유 식별자를 생성한다. 하이버네이트 6대부터 지원된다고 한다. 중복이 거의 안 되게 만든 값이라 PK나 트랜잭션 추적, 파일 이름 등에 쓴다

어떤 게 더 좋고 나쁜 건 아니고 DB 벤더에 맞게 쓰는 게 제일 맞다. 오라클이나 PostgreSQL은 SEQUENCE, MariaDB와 MySQL은 IDENTITY가 맞다.

---

## 6. MariaDB 연결

외부 DB라서 H2처럼 엔티티만 만든다고 테이블이 바로 생기진 않는다. 먼저 DB와 계정을 만들어야 한다.

### DB와 계정 만들기

- 터미널에서 root 계정으로 접속한다.
- `show databases;`로 기본 내장 DB 목록 확인, `use mysql;`로 mysql DB 사용
- `boot_db` 데이터베이스를 만든다
- `boot` 사용자 계정을 비밀번호와 함께 만든다
- 그 계정에 `boot_db`의 모든 권한을 준다 (`grant`)
- `flush privileges`로 권한을 DB에 즉시 반영한다
- `mysql.user` 시스템 테이블에서 계정이 생겼는지 조회했다. host가 `%`면 로컬, 원격 상관없이 모든 호스트에서 이 계정으로 접속 가능하다는 뜻이다
- `exit`로 나와서 boot 계정으로 다시 접속(`mysql -u boot -p`, 이것도 직접 타이핑)하고 `show grants`로 권한 확인
- 애플리케이션에서 접속하는 계정으로 root를 쓰면 안 된다. root는 DB 관리자 계정이고, 앱용 계정은 따로 만들어서 쓰는 게 맞다. 비밀번호는 실제로는 최대한 길게 해야 한다
- 운영에 배포할 때는 앱 계정에서 DROP 같은 위험한 권한을 빼는 게 안전하다. 지금은 `ddl-auto`가 테이블을 drop하고 다시 만드는 방식이라 개발 중에는 일단 두고, 안정화되고 운영 배포하기 전에 빼라고 하셨다

<details>
<summary>MariaDB 명령어 정리</summary>

```sql
show databases;
use mysql;

create database boot_db;
create user 'boot'@'%' identified by 'boot';
grant all privileges on boot_db.* to 'boot'@'%';
flush privileges;

select user, host from mysql.user;
exit
```

```bash
# boot 계정으로 재접속 (직접 타이핑)
mysql -u boot -p
```

</details>

### 앱에서 MariaDB로 연결하기

- `pom.xml`에는 이미 `mariadb-java-client` 의존성이 있다. 이건 JDBC 드라이버고, DB 엔진 자체는 첫날 설치한 MariaDB다. H2는 엔진과 드라이버가 의존성 하나에 같이 들어 있는 점이 다르다
- test 쪽에는 H2 설정을 그대로 두고 prod 프로파일에 MariaDB 설정을 넣었다. `spring.profiles.active`를 test에서 prod로 바꿔서 `application-prod.properties`가 적용되게 한다. 이때 Run 설정의 program arguments에 넣었던 포트 지정도 빼야 한다
- 접속 정보는 환경변수와 기본값을 같이 쓰는 방식으로 적는다. `${DB_HOST:localhost}`는 환경변수 `DB_HOST`가 있으면 그 값을 쓰고 없으면 콜론 오른쪽 기본값을 쓴다는 뜻이다. 운영에 배포하면 IP는 반드시 바뀌고(컨테이너로 띄우면 컨테이너 이름이 될 수도 있다) 포트도 바뀔 수 있어서 이렇게 해둔다
- 비밀번호처럼 깃에 올라가면 안 되는 값은 기본값을 지우고 `${DB_PASSWORD}`만 남긴다. 그리고 Edit Configuration에서 환경변수나 program arguments로 값을 넘기면 내부적으로는 그 값을 쓰면서 깃에는 노출이 안 된다. 어제 배운 대로 인자가 properties보다 우선순위가 높다
- 나중에 도커 컴포즈에서 `DB_HOST`, `DB_PORT` 같은 값을 environment로 넘기는 방식이 이 구조와 그대로 이어진다고 한다.

<details>
<summary>application-prod.properties 예시</summary>

```properties
spring.datasource.url=jdbc:mariadb://${DB_HOST:localhost}:${DB_PORT:3306}/boot_db
spring.datasource.username=boot
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

spring.jpa.hibernate.ddl-auto=create
```

</details>

### 테이블이 생기는 것 확인

- 이 상태에서 `show tables;`를 해도 처음에는 테이블이 없었다. 외부 DB는 설정이 필요하다. `ddl-auto` 설정과 SQL 로그 확인용 설정을 넣고 재시작하니까 로그에 drop table, drop sequence, create sequence, create table이 찍히면서 테이블이 만들어졌다
- 안 될 때 확인할 것: `profiles.active`가 prod인지, `ddl-auto`가 create로 되어 있는지, 환경변수 세팅을 했는지. 그래도 안 되면 설정 파일 반영이 안 된 것일 수 있어서 애플리케이션 클래스를 완전히 내렸다가 다시 올리는 게 깔끔하다
- 테이블은 `customers`와 시퀀스 테이블 하나(`customers_seq`)가 만들어졌다. `desc customers;`로 구조 확인
- AUTO 전략일 때는 `customers_seq`라는 시퀀스가 만들어졌고 start 1, increment 50으로 되어 있었다. id 컬럼에는 `auto_increment`가 없었다

### AUTO에서 IDENTITY로 바꿔보기

- 전략을 `GenerationType.IDENTITY`로 바꾸고 재시작하면 customers 테이블은 drop 후 다시 만들어지는데 시퀀스는 안 지워준다. 그래서 수동으로 `drop sequence customers_seq;`를 해줬다
- 바꾼 후에는 `desc customers`에서 id가 `bigint not null auto_increment`로 나온다. 컬럼 자체의 자동 증가 기능을 쓰는 거라 시퀀스가 필요 없다. MariaDB는 IDENTITY가 맞다
- AUTO일 때 생성된 쿼리를 보면 id에는 PK 제약만 있고 customer_id에 unique 제약(`add constraint ... unique`)이 따로 추가된다

### 직접 insert 해보기

- `insert into customers (customer_id, customer_name, created_at) values (...)` 형태로 직접 넣었다. 컬럼 전체가 아니라 일부만 넣을 때는 컬럼 이름을 적어줘야 한다
- id는 안 넣어도 알아서 1, 2로 자동 증가했다
- 원래 `createdAt`은 앱의 `@CreationTimestamp`가 채워주지만 지금은 SQL을 직접 날리는 거라 DB 쪽에는 적용이 안 돼서 직접 넣어줘야 한다. 날짜 함수는 DB마다 다른데 MariaDB는 `now()`를 쓰면 되고, 직접 `'2026-11-26 10:00:00'` 같은 문자열로 줘도 된다
- 결과에 나노초 단위 같은 자리까지 들어가는 것도 확인했다

<details>
<summary>insert 예시</summary>

```sql
insert into customers (customer_id, customer_name, created_at)
values ('C001', '홍길동', now());

insert into customers (customer_id, customer_name, created_at)
values ('C002', '김철수', now());

select * from customers;
```

</details>

---

## 7. JDBC 드라이버와 JDBC에서 JPA까지의 흐름

### JDBC 드라이버가 필요한 이유

- `java.sql`과 `javax.sql` 패키지를 합쳐서 JDBC API라고 부르는데, 들어가 보면 거의 다 인터페이스고 클래스는 몇 개 없다
- 퀴즈: 이 인터페이스를 구현하는 건 누구일까? 정답은 DB 회사(벤더)다. 오라클, MariaDB 같은 DB 회사가 자기 DB에 맞게 구현한 게 JDBC 드라이버다
- 로깅에서 SLF4J 인터페이스를 쓰고 구현체를 로그백에서 로그포제이로 바꿔도 사용법이 같았던 것과 같은 원리다. DB를 마리아에서 오라클로 바꿔도 애플리케이션 작성 절차는 똑같고 접속 URL, 드라이버 클래스 이름 같은 파라미터만 달라진다

### 순수 JDBC 예제 (오라클과 마리아 비교)

AI한테 요청해서 만든 오라클용, MariaDB용 예제를 비교해봤다. 직접 실습하진 않았고 흐름을 이해하는 용도..

- 다른 부분은 JDBC URL, 드라이버 클래스 이름, 유저/비밀번호, 쿼리 일부(오라클은 보통 1521 포트, 로컬 DB 이름 등)뿐이고 나머지 코드는 똑같다
- 흐름: 드라이버 로딩 → `getConnection` → `PreparedStatement` 생성 → 파라미터 바인딩 → 쿼리 실행 → `ResultSet`에서 컬럼 값을 꺼내서 자바 변수에 넣기. 마지막에 try-with-resources로 자원을 해제한다
- 쿼리 하나 실행하는데도 코드가 엄청 길다. 그래서 요즘은 이렇게 안 쓰고 생산성이 높은 JPA를 쓴다
- SQL 인젝션: 입력값에 이상한 SQL을 넣어서 의도하지 않은 쿼리가 실행되게 하는 해킹이다. SQL 문자열에 값을 그냥 이어붙이면 그대로 실행될 수 있어서, 쿼리에는 `?`를 쓰고 나중에 `setInt`, `setString` 같은 걸로 타입에 맞게 값을 세팅하는 방식(PreparedStatement)으로 어느 정도 막을 수 있다

### Spring JdbcTemplate

- 순수 JDBC의 반복 작업(Connection, PreparedStatement, ResultSet 생성과 해제)을 스프링이 한 번 추상화해서 줄여준 게 `JdbcTemplate`이다
- 클래스에 `@Repository`를 붙이고(DB 연동 역할이라는 의미가 있는 컴포넌트) `JdbcTemplate`을 생성자로 주입받아서 쓴다
- 쿼리만 쓰고 `jdbcTemplate.query(...)`, `jdbcTemplate.update(...)`를 호출하면 자원 생성과 해제는 알아서 해준다. 파라미터는 가변인자로 순서대로 넘기고 결과는 바로 리스트로 받을 수 있다
- 설정은 프로퍼티스에서 `spring.datasource.url`, 드라이버 클래스 이름 등을 넣는다
- 이것보다 더 추상화해서 쓰는 게 JPA다. 직접 해볼 필요는 없지만 히스토리를 이해하려고 보여주셨다

<details>
<summary>JdbcTemplate 사용 예시 (개념 확인용)</summary>

```java
@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> findNamesByDepartment(int departmentId) {
        String sql = "select first_name from employees where department_id = ?";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getString("first_name"),
                departmentId);
    }

    public int raiseSalary(int employeeId, int amount) {
        String sql = "update employees set salary = salary + ? where employee_id = ?";
        return jdbcTemplate.update(sql, amount, employeeId);
    }
}
```

</details>

---

## 8. DDL, DML, DCL, TCL

- DDL(데이터 정의어): 테이블이나 DB를 만들고 구조를 바꾸고 없애는 언어. `create table`, `alter table`, `drop table`, 데이터를 싹 지우는 `truncate` 등. 지금 Hibernate가 로그에 찍으면서 하고 있는 게 DDL이다 (JPA는 표준 스펙이고 실제 구현체인 Hibernate가 한다)
- DML(데이터 조작어): `select`, `insert`, `update`, `delete`처럼 데이터를 조회하거나 조작하는 언어. 주말에 공부할 부분이 여기
- DCL(데이터 제어어): `grant`로 권한을 주고 `revoke`로 회수한다. 아까 MariaDB에서 쓴 grant가 DCL이고, 운영에서 drop 권한을 빼는 것도 revoke
- TCL(트랜잭션 제어어): `commit`은 처리한 내용을 DB에 영구 반영하고 `rollback`은 insert나 update 한 걸 취소한다

---

## 9. ddl-auto 옵션 비교 실험

`spring.jpa.hibernate.ddl-auto`로 앱이 시작할 때 테이블을 어떻게 다룰지 정한다.

- create: 시작할 때 기존 테이블을 무조건 drop하고 새로 만든다
- create-drop: create와 같은데 애플리케이션이 종료될 때도 drop한다
- update: 테이블 구조와 데이터를 그대로 유지한다. 엔티티에 필드를 추가하면 컬럼은 자동으로 추가해준다. 하지만 필드를 지워도 컬럼은 안 지워진다
- validate: 검증만 한다. DB에 이미 있는 테이블 구조가 엔티티와 일치하는지만 보고 다르면 오류를 낸다. 운영 환경에서는 이걸 가장 많이 쓴다

create와 create-drop, update는 개발 환경용이다. 운영에서 drop부터 하면 고객 정보가 다 날아가서 난리가 난다.

### 직접 해본 실험

1. validate로 바꾸고 재시작 → 테이블 구조와 엔티티가 같아서 에러 없이 올라왔고, 로그에 drop 쿼리도 없었다
2. 엔티티에 `customerAddress` 필드를 추가 → `SchemaManagementException`, `missing column customer_address`가 나고 `entityManagerFactory` 빈, `SessionFactory` 생성도 실패했다. 이런 로그가 보이면 DB 쪽 문제라는 걸 바로 알아야 한다
3. update로 바꾸면 에러 없이 컬럼이 추가된다
4. 필드를 다시 주석 처리해서 삭제해도 DB 컬럼은 그대로 남아 있다. 이럴 땐 수동으로 테이블을 drop해야 한다
5. `drop table customers`로 직접 지우고 update로 재시작 → 테이블이 없으면 create부터 해서 새로 만들어진다
6. 직접 insert로 데이터를 넣고 update 상태에서 재시작 → 데이터가 그대로 유지된다. 테이블이 이미 있어서 create table도 안 한다
7. create로 바꿔서 재시작 → drop부터 하고 데이터가 사라진다
8. validate 상태에서 테이블을 drop해두고 시작 → 테이블이 아예 없다는 오류. validate는 테이블이 미리 있어야 한다

개발 단계에서는 데이터를 유지하고 싶을 땐 update, 매번 깨끗하게 시작하면서 러너로 초기 데이터를 넣고 싶을 땐 create를 오가며 쓰면 된다.

### 스키마 버전 관리

운영에서는 validate를 쓰니까 테이블 스키마를 먼저 직접 만들어놔야 한다. 그런데 요구사항이 바뀌어서 컬럼을 추가하거나 주소 필드 크기를 늘려야 하는 일이 계속 생기니까 소스처럼 스키마도 버전 관리가 필요하다. 그때 쓰는 게 Flyway라는 외부 라이브러리인데 스프링 부트에 이미 연동이 되어 있어서 쓰기 쉽다고 한다. 나중에 시간이 되면 알려주신다고 했다.

---

## 10. SQL 실습: employees, departments 테이블

SQL 공부용으로 사원(employees)과 부서(departments) 테이블 스크립트를 받아서 실행했다. 이 스크립트는 SQL 첫 학습 때 많이 쓰는 예제 테이블이다.

### 테이블 구조

- departments: 부서 아이디(PK), 부서 이름, 부서 관리자 사번, 위치 아이디. 한글이 안 깨지게 `utf8mb4`로 만들었고 테이블에 comment도 달았다
- employees: 사번(PK), 이름, 성, 이메일, 전화번호, 입사일, 업무 코드, 급여, 커미션 비율, 관리자 사번, 부서 아이디
- 제약조건: 이메일은 unique, 급여는 0보다 커야 한다는 check 조건, 사번은 PK 이고 auto_increment로 만들어졌다

### PK와 FK 개념

- employees의 부서 아이디는 departments의 부서 아이디를 참조하는 FK(외래키)다. 부서 테이블에 없는 부서 번호가 사원에게 들어가면 안 되니까 이 제약을 건다
- 관리자 사번(manager_id)도 결국 사원이라서 자기 자신 테이블의 사번을 참조한다. 이게 셀프 조인에서 쓰인다
- 사장(King)은 관리자도 없고 부서 아이디도 null로 들어가 있다. 소속 부서가 없기 때문이다

### 실행 순서

- 터미널(커맨드 창)에서 boot 계정으로 `boot_db`에 접속해서 sql 파일을 실행한다. 이건 DB 안에서 하는 게 아니라 커맨드 창에서 `<` 로 파일을 넣는 방식이다
- 첫 번째 `create_table.sql`로 두 테이블을 만들고 `show tables`, `desc employees`, `desc departments`로 구조를 확인했다. 이때는 건수가 0이다
- 두 번째 `insert_data.sql`로 데이터를 넣었다. count를 해보면 departments 27건, employees 107건이 나오면 성공
- customers 테이블은 앱을 실행하면 다시 생기니까 신경 안 써도 된다

<details>
<summary>실행 명령</summary>

```bash
# 커맨드 창에서 (DB 접속 상태 아님)
mysql -u boot -p boot_db < create_table.sql
mysql -u boot -p boot_db < insert_data.sql
```

```sql
select count(*) from departments;
select count(*) from employees;
desc employees;
desc departments;
```

</details>

---

## 11. 바이브 코딩 환경 준비: Antigravity

### Antigravity 소개

- 구글에서 만든 AI 코딩 에이전트 도구다. 올해 5월에 Gemini CLI 서비스를 종료하면서 제품군을 나눴다. Antigravity CLI는 커맨드라인에서 코딩 에이전트를 부르는 것이고, Antigravity IDE는 VS Code에 에이전트 매니저를 탑재한 방식이다
- 커서도 VS Code를 포크해서 에이전트를 넣은 방식과 비슷하고, IDE는 좀 무거워서 VS Code에서 CLI를 설치해서 쓰는 게 제일 가볍고 깔끔하다고 한다
- 비교 대상은 클로드 코드, 코덱스, 커서 같은 도구들이다. Antigravity는 무료 티어가 꽤 관대하고, 무료 한도를 다 쓰면 구글 계정을 바꿔서 쓰는 방법도 있다. 요금제도 클로드 코드보다 싼 편이다. 이 CLI는 Go로 만들어져서 생각보다 빠르다고 한다. 클로드 코드나 코덱스는 Rust 기반이다
- 컨텍스트 길이는 Antigravity가 1M 정도이고 클로드는 이전에 짧았다가 늘어났지만 상대적으로 짧은 편이다
- MCP를 지원한다. 설정 파일 포맷이 다른 도구들은 JSON인데 Antigravity만 특이하다고 하셨다
- 클로드 코드는 로컬에 Git Bash가 없으면 설치조차 안 될 정도로 깃과 밀접하게 연동된다. PR 리뷰, 이슈 연동 등을 할 수 있다
- 멀티 에이전트(서브 에이전트), 스킬, 웹 검색도 지원한다
- 클로드 코드와 코덱스는 npm이나 curl 명령으로 설치하고 Node.js가 꼭 필요한 건 아니다. 인증은 브라우저 인증을 쓴다. 클로드는 API 키도 되고 AWS 베드락, 버텍스 같은 클라우드를 통해 쓸 수도 있다

### SWE-bench Verified

- AI 모델과 코딩 에이전트가 실제 소프트웨어 엔지니어링 능력이 얼마나 되는지 평가하는 벤치마크 데이터셋이다
- GitHub 오픈소스 이슈를 해결하게 하고 기존 단위 테스트를 통과하는지로 측정한다
- 원래는 이슈 설명이 모호하거나 개발자도 못 푸는 문제가 섞여 있어서, 엔지니어를 투입해 문제를 엄선해서 다시 만든 게 Verified다
- 모델 성능이 빨리 좋아져서 평가 지표도 계속 발전해야 하는 상황이다

### 컨텍스트 파일 (AGENTS.md)

- 에이전트한테 "이건 해도 되고 이건 하지 마" 같은 걸 매번 말하면 힘들어서 미리 규칙을 적어두는 파일이 컨텍스트 파일이다
- Antigravity CLI나 코덱스 CLI는 `AGENTS.md`라는 마크다운 파일에 규칙과 선호(예: 타입스크립트를 선호한다 같은 것)를 적어두면 에이전트가 뜰 때마다 읽는다

### 단축키와 사용 팁

- Esc: 작업 중단
- 위 화살표: 이전에 입력한 프롬프트 불러오기
- Ctrl+L: 터미널 화면 지우기
- Tab: 자동 완성
- Alt+Enter: 줄바꿈 (브라우저에서 Shift+Enter 하는 것과 같은 용도)
- 느낌표: 에이전트 대화창에서 `!`를 먼저 치면 대화가 아니라 터미널 명령 모드가 된다. 에이전트를 끄지 않고 바로 `ls` 같은 걸 칠 수 있다. 단, `ls -al`이나 `pwd`는 Git Bash에서만 먹고 윈도우 커맨드에서는 안 된다

### 로그인

- 브라우저를 옆에 띄워놓고 `agy`를 실행한다. 
- 약관 동의 화면에서 화살표로 위치를 옮기고 엔터로 체크(x 표시)를 한다. x가 사라지면 동의하지 않는 상태이니까 다시 엔터로 체크하고, 화살표로 Done에 커서를 놓고 엔터
- 인증 방식은 Google OAuth(1번)를 고르고, 브라우저에서 구글 계정으로 로그인한 뒤 나오는 코드를 복사해서 터미널에 붙여넣고 엔터

---
#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
