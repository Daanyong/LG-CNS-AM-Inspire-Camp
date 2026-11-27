# [Day 24]  LG CNS AM Inspire Camp 7기 TIL
작성일: 2026.11.27 (금) | 과목: AI 서비스 개발을 위한 백엔드

## 오늘 학습한 주제

-  Dialect
- 데이터 접근 방식이 발전해온 흐름
- Spring Data Core와 Spring Data JPA
- 쿼리 메서드 (파인더 메서드)
- 테스트 케이스 작성
- Optional
- 트랜잭션
- 업데이트
- 바이브 코딩: Antigravity CLI
- MCP

---

## 1. Dialect

Dialect는 원래 사투리, 방언이라는 뜻인데 여기서는 DB 제품마다 조금씩 다른 SQL 문법을 가리키는 말이다.

- Oracle, MySQL, PostgreSQL 같은 DB는 각각 다른 제품이고 SQL 문법이 조금씩 다르다
- 원래 ANSI SQL이라는 표준이 먼저 있었던 게 아니라, DB 벤더들이 먼저 만들고 한참 뒤에 표준이 나와서 이런 차이가 생겼다고 한다
- JDBC는 우리가 SQL을 직접 쓰지만 JPA는 하이버네이트가 SQL을 대신 만들어준다
- 그래서 하이버네이트한테 지금 쓰는 DB가 뭔지 알려줘야 그 DB에 맞는 SQL을 만들 수 있고, 그게 Dialect 설정이다
- 우리는 `MariaDBDialect`를 쓰고 있다
- Hibernate API 문서에 가면 지원하는 DB별 Dialect 클래스 목록이 있다. DB2, Derby, H2, HSQL, MariaDB, Oracle, PostgreSQL, SQL Server, Sybase 같은 것들이 있었다
- H2는 Dialect를 따로 안 써도 스프링 부트가 알아서 설정해줘서 안 썼던 거라고 한다

---

## 2. 데이터 접근 방식이 발전해온 흐름

JDBC 예제랑 이것저것 비교해서 보여주셨다.

- 순수 JDBC: 드라이버 클래스 로딩, Connection 맺기, PreparedStatement 만들기, SQL 직접 작성, ResultSet에서 컬럼 값을 하나씩 꺼내서 변수에 넣기까지 전부 직접 해야 한다
- JdbcTemplate: 스프링이 위 과정을 많이 줄여줬지만 그래도 SQL은 직접 써야 한다. 과도기 느낌
- MyBatis: 예전에 JSP랑 같이 많이 썼는데 지금은 거의 안 쓴다고 한다. 수업에서는 안 다룬다
- 하이버네이트 직접 사용: SQL 대신 객체로 다루지만 세션 연결 같은 코드를 직접 써야 하고, 조건 조회는 HQL을 써야 한다
- Spring Data JPA: 인터페이스만 만들면 되고, 간단한 건 쿼리문이 하나도 필요 없다. 조인 같은 복잡한 건 `@Query`로 쓰게 되는데 그건 뒤에서 배운다

### 2.1 Spring Data JPA와 하이버네이트 직접 사용의 차이

- Spring Data JPA는 Repository를 인터페이스로만 만든다. 구현체는 스프링이 알아서 만들어서 주입해준다
- 하이버네이트를 직접 쓰면 `@Repository` 클래스를 만들고 `SessionFactory`를 주입받아서 `getCurrentSession()`으로 세션을 얻은 다음 `persist`, `createQuery` 같은 걸 직접 호출해야 한다
- 같은 기능(이메일로 조회, 활성 상태인 회원을 최신순으로 조회)을 만들어도 Data JPA는 메서드 이름 한 줄이고, 하이버네이트는 JPQL 문자열 쓰고 파라미터 바인딩하고 `getResultList()` 호출해야 해서 훨씬 길다
- 새로 개발하는 거면 Data JPA를 권장한다
- 기존에 JDBC나 MyBatis로 돌아가던 레거시 시스템을 점진적으로 마이그레이션하는 경우에는 하이버네이트 직접 사용을 고려한다고 한다
- 조건에 따라 쿼리가 동적으로 바뀌는 복잡한 경우는 QueryDSL 같은 별도 솔루션을 쓴다

---

## 3. Spring Data Core와 Spring Data JPA

Spring Data라는 큰 프로젝트 안에 여러 개가 있고 JPA는 그중 하나일 뿐이다. MongoDB, Redis, Cassandra 같은 것들도 각각 Spring Data 라이브러리가 따로 있다.

- Spring Data Core(Commons): 어떤 DB를 쓰든 공통으로 필요한 것들을 모아둔 API다. 페이징 처리, 오디팅, 도메인 같은 게 여기 있다
- Spring Data JPA: JPA에만 특화된 부분만 따로 모은 것
- Spring Data MongoDB: 몽고DB에 특화된 부분. 여기는 `MongoRepository`가 있다
- 그래서 Repository 계층 구조를 타고 올라가면 Data JPA 패키지에서 시작해서 Core 패키지의 인터페이스까지 이어진다
- 페이징 같은 건 DB가 뭐든 다 필요하니까 Core에 넣고, 나머지 특화된 부분은 각 라이브러리에 넣은 설계라고 이해했다

### 3.1 상속 구조 따라가 보기

Ctrl 누르고 클릭하면서 따라 올라가 봤다.

- `JpaRepository`
- `ListCrudRepository` (등록, 수정, 삭제 담당. 리턴 타입이 List)
- `ListPagingAndSortingRepository` (페이징, 정렬 담당)
- 그 위에 `CrudRepository`, `PagingAndSortingRepository`
- 맨 위에 `Repository`는 메서드 없는 마커 인터페이스다
- 예전에는 CRUD랑 페이징이 한 인터페이스였는데 지금은 분리됐다

### 3.2 CrudRepository에 있는 메서드들

CRUD는 Create, Read, Update, Delete의 약자다.

- `save`: 등록. 엔티티 하나를 받아서 insert 쿼리가 나간다
- `saveAll`: 여러 개를 한 번에 등록. `Iterable`을 받는다 (List, Set의 상위 인터페이스)
- `findById`: PK로 조회. 리턴이 `Optional`
- `findAll`: 전체 조회
- `findAllById`: 여러 id로 한 번에 조회
- `count`: 전체 건수. 데이터가 많을 때 잘못 쓰면 성능이 안 좋아서 잘 안 쓴다
- `existsById`: 해당 PK가 있는지 확인
- `delete` 계열: id로 삭제, 엔티티로 삭제, 전체 삭제 등 종류가 많다

몇 가지 짚은 것들

- `save`의 시그니처에 `<S extends T>`가 있는데, T가 우리 엔티티고 S는 T를 상속받은 자식 엔티티도 받아줄 수 있다는 뜻이다. 지금은 엔티티가 하나라 신경 안 써도 되지만 나중에 부모 엔티티 밑에 자식 엔티티를 두는 구조에서는 의미가 있다
- `findAll`이 `Iterable`을 리턴하는 건 `CrudRepository`고, 개발자들이 List를 더 많이 쓰니까 `ListCrudRepository`에서 List로 리턴하는 `findAll`을 따로 추가해준 것이다
- 페이징할 때는 `PagingAndSortingRepository`의 `findAll`에 `Pageable`을 넘기고, 결과는 `Page`로 받는다. 예를 들어 100건이면 10건씩 쪼개서 일부만 담아주는 객체가 `Page`다. 페이징은 나중에 따로 한다
- update 메서드가 없다. 이유는 조회를 먼저 해서 엔티티를 가져온 다음 setter를 호출하면 되기 때문이다 (아래 더티 체킹 부분에서 다시 나옴). 왜 없는지는 AI한테 물어봐도 좋다고 하셨다
- delete가 종류가 많지만 실무에서는 잘 안 쓴다고 한다. 회원이 탈퇴해도 데이터를 지우지 않고 다른 곳에 보관했다가 일정 기간 후에 폐기하는 식이라고 한다

---

## 4. 쿼리 메서드 (파인더 메서드)

- PK는 모든 엔티티에 반드시 있으니까 `findById`를 API에서 제공할 수 있다
- 나머지 필드는 개발자가 업무 필요에 따라 마음대로 정한 이름이라서 API 만든 사람들이 알 수가 없다
- 그래서 PK 말고 다른 컬럼으로 조회하고 싶으면 Repository 인터페이스에 메서드를 우리가 직접 선언해야 한다. 구현은 안 해도 되고 이름만 규칙에 맞게 쓰면 쿼리를 만들어준다

### 4.1 SQL로 확인

- `where id = 1`은 PK 조회 쿼리고 `findById`가 이 쿼리를 만들어준다
- `where customer_id = 'a001'`처럼 다른 컬럼으로 조회하는 건 API가 못 만들어주니까 우리가 만든다
- `customer_id`에는 unique 제약을 걸어놨으니까 결과가 최대 한 건이라는 게 보장된다. 그래서 리턴 타입을 `Optional<Customer>`로 한다
- `customer_name like '%t%'` 같은 이름 조회는 중복이 가능해서 여러 건이 나올 수 있다. 그래서 리턴 타입이 `List<Customer>`다
- PK로 조회했을 때 해당 id가 없으면 DB에서 아무것도 반환 안 하니까 자바에서 null이 된다. 매번 null 체크를 해야 하는 불편함을 줄이려고 `Optional`로 감싸는 것이다

### 4.2 like 패턴 정리

- `t%`는 t로 시작하는 것
- `%t`는 t로 끝나는 것
- `%t%`는 t가 포함된 것

### 4.3 메서드 이름 규칙

- `findBy`로 시작해야 하고 `By`의 B는 반드시 대문자다. 소문자로 쓰면 에러가 난다
- 그 뒤에 오는 필드명은 엔티티 필드명을 그대로 쓰되 첫 글자는 무조건 대문자로 쓴다. 필드가 `customerId`면 `findByCustomerId`. 이걸 `findBycustomerId`로 쓰거나 필드명 중간 대문자를 소문자로 바꾸면 "그런 property가 없다"는 에러가 난다

공식 문서(쿼리 메서드 설명 부분)에서 본 키워드들

- `findBy` 말고도 `readBy`, `getBy`, `queryBy`, `searchBy`, `streamBy` 도 된다
- `existsBy`: 존재 여부
- `countBy`: 건수
- `deleteBy`, `removeBy`: 삭제
- `findDistinctBy`: 중복 제거 (SQL의 distinct. 예를 들어 사원 이름 107건 중에 중복 빼면 줄어드는 그것)
- `And`, `Or`: 조건 연결
- `Between`: 구간 조회 (날짜 범위 같은 것)
- `Like`, `StartingWith`(StartsWith), `EndingWith`(EndsWith), `Containing`(Contains): like 검색
- `OrderBy필드명Desc` / `Asc`: 정렬
- `IsNull`, `Not` 등 논리 키워드도 있음
- `findByFirstName`, `findByFirstNameIs`, `findByFirstNameEquals`는 다 같은 뜻

### 4.4 JPQL과 네이티브 쿼리

- 쿼리 메서드 이름을 보고 만들어지는 건 SQL이 아니라 JPQL이다. 테이블이 아니라 엔티티를 대상으로 하는 쿼리 언어다
- 하이버네이트가 JPQL을 다시 네이티브 쿼리(진짜 DB용 SQL)로 바꿔서 날려준다. 거기까지는 알아서 해주니까 JPQL이 만들어진다는 정도만 이해하면 된다
- JPQL은 from절에 테이블명이 아니라 엔티티 이름을 쓰고 조건에는 필드명을 쓴다
- 메서드 이름으로 해결이 안 되는 경우(나중에 조인, 아우터 조인 등)는 메서드 위에 `@Query`를 붙이고 JPQL을 직접 쓴다. 이건 뒤에서 단계별로 한다
- `@Query(nativeQuery = true)`로 네이티브 SQL을 직접 쓸 수도 있다. 하이버네이트가 만든 쿼리가 이상하거나 마땅치 않을 때 가끔 쓴다
- 엔티티 위에 `@NamedQuery`로 쿼리를 쓰거나 XML에 쓰는 방법도 있지만 많이 안 쓴다

### 4.5 오늘 만든 쿼리 메서드

- `findByCustomerId`: unique 컬럼이라 한 건 보장 → `Optional<Customer>`
- `findByCustomerNameContains` (또는 `Containing`): 이름에 특정 문자가 들어간 고객들 → `List<Customer>`

---

## 5. 테스트 케이스 작성

### 5.1 테스트 클래스 만들기

- 인터페이스 안에 커서를 두고 `Ctrl + Shift + T` 하면 Create New Test가 뜬다
- 테스트 라이브러리는 JUnit5를 골랐다. JUnit 말고도 Spock, TestNG 같은 게 있는데 JUnit이 제일 일반적이라고 한다
- 테스트 클래스는 `src/test` 쪽 같은 패키지에 생긴다
- 스프링 프레임워크 때는 `@ExtendWith(SpringExtension.class)` 같은 걸 붙였는데, 스프링 부트에서는 `@SpringBootTest` 하나만 붙이면 된다. 내부적으로는 똑같이 JUnit 확장 기능으로 컨테이너를 만들어주고 테스트 컨텍스트를 시작해준다
- `@Autowired`로 `CustomerRepository`를 주입받는다
- 엔티티에 getter, setter가 빠져 있어서 추가해야 했다

### 5.2 Given-When-Then

테스트를 세 단계로 나눠서 쓴다.

- given: 준비 단계. 객체를 만들고 값을 넣는다
- when: 실행 단계. 실제로 테스트할 메서드를 호출한다
- then: 검증 단계. 결과가 기대한 대로인지 확인한다

### 5.3 assertThat

- JUnit5의 `Assertions.assertEquals`는 actual과 expected 자리가 바뀌어도 아무 문제가 없어서 구분이 안 되는 사소한 결함이 있다
- 그래서 AssertJ의 `assertThat(실제값).isEqualTo(기대값)` 형태를 개발자들이 더 많이 쓴다. 이름이 같은 `Assertions` 클래스인데 만든 곳이 다르다
- `System.out.println`으로 값을 찍어서 확인하는 건 JUnit에서 권장하는 방식이 아니다
- static import는 `Alt + Enter`로 하면 된다
- `Ctrl + Alt + V`로 리턴 값을 변수로 바로 뽑는 단축키도 알려주셨다

<details>
<summary>등록 테스트 코드</summary>

```java
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testInsert() {
        // given
        Customer customer = new Customer();
        customer.setCustomerId("A001");
        customer.setCustomerName("스프링");

        // when
        Customer addedCustomer = customerRepository.save(customer);

        // then
        assertThat(addedCustomer).isNotNull();
        assertThat(addedCustomer.getId()).isEqualTo(1L);
        assertThat(addedCustomer.getCustomerName()).isEqualTo("스프링");
    }
}
```

</details>

### 5.4 create와 update 차이

테스트를 계속 돌려보면서 ddl-auto 값에 따라 결과가 어떻게 다른지 확인했다.

- create: 실행할 때마다 테이블을 drop하고 다시 만든다. 그래서 같은 id를 다시 넣어도 중복 에러가 안 난다. 이전 데이터가 다 사라지니까
- update: 데이터가 유지된다. 그래서 `customer_id`에 걸어둔 unique 때문에 같은 값을 또 insert하면 `DataIntegrityViolationException`이 난다. 이건 에러라기보다 데이터 정합성 위반이라서 당연한 결과다
- 테스트를 전체 클래스 단위로 실행하면 insert 테스트가 계속 다시 실행돼서 update 모드에서는 중복 에러가 난다. 그래서 테스트는 메서드별로 따로 실행하는 게 좋다
- 테스트 중에는 create로 두는 게 편하고, 데이터를 유지하면서 확인하고 싶을 때만 update로 바꾼다
- auto increment 번호가 중간에 건너뛰는 경우가 있었다. 롤백되거나 실패한 insert도 번호를 소모하기 때문이다

---

## 6. Optional

`findById`나 `findByCustomerId`가 `Optional`을 리턴하니까 사용법을 배웠다.

- 값이 있을 수도 있고 없을 수도 있는 객체를 감싸는 일종의 컨테이너(래퍼 클래스)다
- 없는 id로 조회하면 null이 나오는데, 그걸 매번 if로 null 체크하는 방어 로직 대신 Optional로 받는다
- 함수형 언어 쪽 접근에서 아이디어를 가져온 거라고 한다

메서드들

- `get()`: 안에 든 값을 꺼낸다. 값이 없는데 바로 꺼내면 `NoSuchElementException`이 난다
- `isPresent()`: 값이 있으면 true. 자바 8부터 있었다
- `isEmpty()`: 값이 없으면 true. 자바 11에서 추가됐다
- 그래서 `isPresent()`로 먼저 확인하고 `get()`을 하는 게 안전한데, 매번 이러면 불편하다
- `ifPresent(Consumer)`: 값이 있으면 그 값으로 주어진 동작을 실행한다
- `orElseGet(Supplier)`: 값이 있으면 그 값을 반환하고, 없으면 Supplier가 만들어주는 값을 반환한다
- `orElseThrow(Supplier)`: 값이 있으면 반환하고, 없으면 Supplier가 만들어주는 예외를 던진다

### 6.1 함수형 인터페이스 Consumer와 Supplier

- Consumer: 입력은 있고 출력은 없다 (소비만 하고 끝). 추상 메서드가 `accept(T t)`고 void다. `ifPresent`에 넣는 람다가 이거라서 Optional에 든 값이 바로 파라미터로 넘어온다
- Supplier: 입력은 없고 출력만 있다 (제공자). 추상 메서드가 `get()`이고 T를 반환해야 한다. 그래서 람다에서 파라미터 자리는 빈 괄호 `()`로 쓰고 화살표 뒤에 반환할 값을 쓴다
- 람다식은 메서드 이름은 신경 안 쓰고 입력과 출력의 모양만 맞추면 된다

### 6.2 orElseGet과 orElseThrow의 차이

- `orElseGet`: 없을 때 빈 객체 같은 대체 값을 만들어서 반환한다. 에러는 안 난다. 빈 `new Customer()`를 반환하면 객체는 있지만 customerId, name은 null이다
- `orElseThrow`: 없을 때 예외를 던진다. Supplier가 반환하는 타입 X가 `Throwable`의 자식이어야 한다
- 예외 상속 구조는 `Throwable` > `Exception` > `RuntimeException` 순이다. 지금은 일단 `RuntimeException`을 만들어서 던졌다
- 나중에 REST API 만들 때 이걸 활용한다고 한다. 해당 id가 없으면 예외를 던져서 클라이언트에게 404 같은 에러 코드와 "존재하지 않는다"는 메시지를 내려주는 식이다 (커스텀 예외 클래스는 그때 만든다)
- 있는 경우에는 어느 쪽이든 정상적으로 그 객체가 반환되니까 예외가 무조건 나는 게 아니다

### 6.3 테스트로 확인한 것

- 있는 고객번호로 조회 → `orElseGet` 결과의 customerId가 null이 아니다
- 없는 고객번호로 조회 → 빈 객체가 반환되니까 객체 자체는 null이 아니고, customerId가 null이다. 처음에 `isNotNull`로 객체를 검사해서 실패했는데, 안의 필드를 검사했어야 했다
- `orElseThrow`는 없는 경우에 예외가 터지니까 그 뒤 코드는 실행이 안 된다

---

## 7. 트랜잭션

### 7.1 개념

- 여러 개의 작업을 하나의 작업 단위로 묶어서, 모두 성공하거나 모두 실패하도록 보장하는 것
- 계좌이체가 예시다. 내 계좌에서 출금하는 작업과 상대 계좌에 입금하는 작업이 있는데 입금이 실패하면 출금도 원래대로 되돌려야 한다. 그래서 두 작업을 같은 트랜잭션으로 묶는다

### 7.2 ACID

- 원자성: 전부 성공하거나 전부 실패. 중간이 없다
- 일관성: 데이터가 앞뒤가 맞아야 한다. 출금이 만 원이면 입금도 만 원
- 격리성: 동시에 진행되는 작업들이 서로 간섭하지 않아야 한다. 이체 중인데 자동이체가 동시에 빠져나가는 상황, 재고가 하나뿐인 물건을 두 사람이 동시에 결제하는 상황이 예시다. 격리 수준이 높을수록 안전하지만 성능은 떨어진다 (가장 높은 게 SERIALIZABLE이고 순서대로 처리하는 거라 느려짐)
- 지속성: 트랜잭션이 끝나면 결과가 DB에 영구적으로 반영돼야 한다

### 7.3 commit과 auto commit

- 원래는 insert, update 같은 걸 실행해도 명시적으로 commit 해야 DB에 영구 반영된다
- 오라클은 auto commit이 기본이 아니라서 commit을 안 하면 반영이 안 된다
- 마리아DB와 MySQL은 auto commit이 기본이라서 insert 하자마자 DB에 반영된다. 그래서 오늘 테스트에서 트랜잭션 없이 save만 해도 바로 테이블에 들어갔던 것이다

### 7.4 스프링에서의 트랜잭션

- 순수 하이버네이트에서는 `beginTransaction`, `commit` 등으로 직접 트랜잭션 코드를 써야 한다 (수업 때 봤던 Getting Started 예제도 insert와 조회 모두 트랜잭션 안에서 했다)
- 스프링에서는 클래스나 메서드 위에 `@Transactional`만 붙이면 된다
- 메서드가 정상 종료되면 commit, 예외가 발생하면 자동으로 rollback 된다
- 클래스 위에 붙이면 전체 메서드에, 메서드 위에 붙이면 그 메서드에만 적용된다
- import는 스프링의 `org.springframework.transaction.annotation.Transactional`을 쓴다. jakarta 것도 있는데 스프링 것이 옵션이 더 많다
- 주문 저장과 재고 감소를 같은 트랜잭션으로 묶으면 주문이 실패했을 때 재고도 같이 롤백되는 식으로 쓴다
- 트랜잭션을 많이 걸수록 성능은 반비례로 떨어진다. 그래서 insert, update, delete 같은 변경 작업은 트랜잭션으로 묶고, 조회는 `@Transactional(readOnly = true)`로 주면 성능에 조금 도움이 된다

### 7.5 테스트 메서드에 @Transactional을 붙이면

- insert 쿼리가 나가긴 하는데 DB를 확인해보면 데이터가 없다
- 이유는 스프링 테스트에서는 테스트 메서드에 `@Transactional`을 붙이면 기본적으로 테스트가 끝난 뒤 자동으로 롤백하기 때문이다. 테스트가 DB에 찌꺼기를 남기면 안 된다는 원칙 때문이다
- 롤백된 insert도 auto increment 번호는 소모해서 다음 insert 때 번호가 뛰었다
- 결과를 DB에서 꼭 보고 싶으면 `@Rollback(false)`를 같이 붙인다. 기본값이 `@Rollback(true)`라서 롤백됐던 것이다
- 처음에 트랜잭션 없이 했을 때 바로 DB에 반영됐던 것도 이제 이해됐다. 롤백할 테스트 트랜잭션 자체가 없었으니까, save 호출 시점에 자체 트랜잭션이 commit된 것이다
- 이 설명이 맞는지 AI한테도 물어봤는데 같은 답이 나왔다

AI가 정리해준 것 중에 기억해둘 것

- 테스트에 `@Transactional`을 선언하고 자동 롤백을 유지하는 게 더 안전하고 일반적이다
- 반복 실행이나 순서에 상관없이 데이터가 누적되지 않아서 unique 충돌이 안 난다 (오늘 전체 실행하면서 중복 에러 난 것 같은 상황 방지)
- 지연 로딩(뒤에서 배움)된 연관 관계에 접근할 때도 테스트 트랜잭션이 열려 있으면 검증하기 쉽다
- 반대로 실제 commit 시점의 DB 제약조건을 검증해야 할 때는 `@Transactional`을 빼거나 `@Rollback(false)`를 쓴다

<details>
<summary>코드 보기</summary>

```java
@Test
@Transactional
@Rollback(false)   // 이거 없으면 테스트 끝나고 롤백돼서 DB에 안 남음
void testInsert() {

}
```

</details>

---

## 8. 업데이트

### 8.1 SQL 업데이트 문법 먼저 확인

`update 테이블명 set 컬럼명 = 값 where 조건` 형태다. 직접 이름만 바꾸는 update를 날려봤다.

### 8.2 JPA에서 업데이트하는 방법

update 메서드가 따로 없으니까 이렇게 한다.

- 먼저 `findByCustomerId`로 엔티티를 조회한다
- 엔티티의 setter로 이름을 바꾼다
- (트랜잭션 없이 하면) `save`를 한 번 더 호출해서 저장한다

### 8.3 트랜잭션 없이 save로 업데이트하면 생기는 문제

쿼리 로그를 보면서 이상한 점이 두 가지 있었다.

- select가 두 번 나간다. 처음 조회할 때 한 번, 그리고 `save`를 호출할 때 한 번 더. id가 이미 있는 엔티티를 `save`하면 새 엔티티가 아니라고 판단해서 내부적으로 엔티티 매니저의 `merge`를 타고, 이때 PK로 다시 조회하기 때문이다 (AI한테 물어봐서 확인함)
- 이름만 바꿨는데 update 쿼리에서 set절에 다른 컬럼들(customer_id까지)이 전부 들어간다. 값이 바뀌진 않지만 불필요하게 같이 update하는 것이다. 직접 쓴 SQL은 이름만 set했는데 이것과 비교하면 문제가 있는 거다

### 8.4 @DynamicUpdate

- 엔티티 클래스 위에 `@DynamicUpdate`(하이버네이트 어노테이션)를 붙이면 실제로 변경된 컬럼만 set절에 들어간다
- 적용하고 나니 `set customer_name = ?` 만 나가는 걸 확인했다
- 처음에 update 쿼리가 안 나와서 당황했는데, 바꾸려는 값이 이미 DB에 있는 값이랑 똑같아서 변경이 없다고 판단해서 안 보낸 거였다. 다른 값으로 바꾸니까 나갔다
- 그래도 select가 두 번 나가는 건 그대로다

### 8.5 더티 체킹

- 테스트 메서드에 `@Transactional`(+ `@Rollback(false)`)을 걸고, 엔티티를 조회한 다음 setter만 호출하고 `save`는 안 했는데도 update 쿼리가 나갔다
- select가 두 번 나가던 것도 한 번만 나갔다
- 트랜잭션 안에서 조회한 엔티티는 영속성 컨텍스트에서 엔티티 매니저가 관리하고, 값이 바뀌었는지를 자동으로 감지해서 update를 날려준다. 이걸 더티 체킹, 변경 감지라고 한다
- 그래서 트랜잭션 안에서 업데이트할 때는 조회 후 setter만 호출하면 되고, 이 방식이 권장하는 방식이다
- 영속성 컨텍스트, 엔티티 라이프사이클, merge 같은 내부 원리는 월요일에 다시 설명해주신다고 했다. 오늘은 결과만 확인했다

---

## 9. 바이브 코딩: Antigravity CLI

### 9.1 CLI 명령어

- `/planning`: 구현 전에 계획을 먼저 작성하고 사용자 승인을 받는 모드. 처음에는 계획을 검토하고 진행하는 게 맞다고 한다. 기본으로 이게 켜져 있다
- `/btw`: 작업 중에 중단 없이 귓속말처럼 가벼운 질문을 할 때 쓴다. 예를 들어 시간이 얼마나 걸릴지 물어볼 때
- `/model`: 모델과 effort 선택. 무료 계정이라 토큰을 금방 써서 Flash 계열 모델에 effort는 medium으로 했다. effort를 높이면 결과는 좋지만 토큰을 빨리 쓴다
- `!`: bash 모드. 터미널 명령어를 바로 입력할 수 있다 (한글이 좀 깨져 보이는 현상은 있음)
- `Ctrl + L`: 화면 초기화
- `/mcp`: 연결된 MCP 서버 목록 확인

### 9.2 AGENTS.md

프로젝트 규칙이나 선호하는 스타일을 마크다운으로 적어두면 AI가 시작할 때 이 파일을 읽어서 컨텍스트에 로드하고 기억한다. 매번 같은 말을 반복하지 않아도 된다.

- 전역 설정: 홈 디렉토리 밑 `.gemini` 폴더에 `AGENTS.md`를 두면 모든 프로젝트에 적용된다
- 프로젝트 설정: 프로젝트 루트 폴더에 `AGENTS.md`를 두면 그 프로젝트에만 적용된다
- IDE 말고 CLI만 쓰고 있어서 메뉴에서 만드는 게 아니라 파일을 해당 위치에 복사해서 넣기만 하면 인식된다. 재시작도 필요 없었다
- 잘 읽었는지는 "언어 및 소통 원칙 알고 있어?", "git 관련 금지 규칙 알고 있어?" 같은 질문으로 확인해봤고 제대로 대답했다
- 맥은 홈 디렉토리의 숨김 폴더를 찾아서 넣으면 된다고 한다

강사님이 준 AGENTS.md에 들어있던 규칙들 (정답이 있는 건 아니고 각자 필요한 걸 적으면 됨)

- 한국어로 답변, IT 전문 용어는 그대로 쓰기
- 코드 블록 규칙 같은 출력 형식
- 파괴적이거나 되돌릴 수 없는 작업은 반드시 먼저 물어보기
- 작업할 때 플랜 아티팩트를 먼저 제시하고 승인받은 뒤에 구현하고 검증하기
- 절대 금지: force push, `reset --hard` 등 히스토리를 날리는 git 명령 (한번 push 시키면 AI가 그 뒤로 알아서 push해버릴 수 있어서 못 박아둬야 한다)
- `.env` 수정 금지. 안 그러면 AI가 막 고쳐서 넣어둔 키가 사라질 수 있다
- ssh 키, aws 같은 자격증명 파일은 접근도 하지 말기
- `rm -rf` 같은 위험한 명령 금지

---

## 10. MCP

### 10.1 개념

- Model Context Protocol의 약자. LLM이 외부 도구랑 연동할 수 있게 해주는 표준 규약이다. 손발을 달아주는 느낌
- DB, GitHub, Slack, Notion 같은 외부 시스템마다 연결 방법이 다 다르면 불편하니까 Anthropic이 표준을 만들었다. 지금은 아이폰이든 안드로이드든 다 C타입으로 충전하는 것처럼, 연결 방식을 통일한 것이다

### 10.2 구성

- MCP 클라이언트: 요청하는 쪽. Claude Code, Antigravity 같은 코딩 에이전트가 이쪽이다. 사용자의 질문을 받아서 어떤 도구가 필요한지 판단하고 서버에 명령을 내린다
- MCP 서버: 실제 작업을 수행하는 쪽. 예를 들어 GitHub MCP 서버는 GitHub API랑 직접 통신하면서 레포지토리 읽기, 쓰기 같은 걸 한다
- MCP 서버는 내 컴퓨터에 따로 설치해줘야 하는 별도 프로그램이고 보통 npm으로 설치한다
- 설치한 다음 코딩 에이전트의 설정 파일(Antigravity나 Claude는 JSON, Codex는 toml)에 그 서버를 실행하는 방법을 등록해줘야 한다
- 이렇게 하면 "커밋 메시지를 요약해서 커밋하고 push 해줘" 같은 자연어 요청을 에이전트가 알아서 판단해서 MCP 서버를 호출한다

### 10.3 GitHub MCP 서버 연결 실습

- npm(Node.js)으로 GitHub MCP 서버 프로그램을 로컬에 설치했다
- GitHub 토큰(`ghp_`로 시작하는 것)을 `.env` 파일에 `GITHUB_API_KEY` 이름으로 넣었다. `.gitignore`에 `.env`를 넣어서 GitHub에 올라가지 않게 했다
- `mcp_config.json`을 열어서 GitHub MCP 서버 실행 설정을 넣었다. 이 설정에서 `GITHUB_PERSONAL_ACCESS_TOKEN`이라는 환경변수 이름은 GitHub MCP 서버가 미리 정해둔 이름이라 바꾸면 안 되고, 거기에 `.env`에 넣어둔 값(GITHUB_API_KEY)을 연결해준다. `.env` 쪽 이름은 우리가 임의로 정해도 된다
- 다른 MCP로는 Playwright, Context7이 있다고 한다. Context7은 코딩 에이전트가 최신 라이브러리 문서와 코드 샘플을 볼 수 있게 해줘서 항상 최신 기준으로 코드를 짜게 해준다

---

#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
