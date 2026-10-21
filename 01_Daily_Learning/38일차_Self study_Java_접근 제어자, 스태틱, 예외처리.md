# [LG CNS 7기] 6주차 Day3 TIL
자바 접근 제어자, 스태틱, 싱글톤 패턴 및 예외 처리 기초

## 📌 오늘의 학습 키워드
- `패키지와 클래스 위치`
- `4가지 접근 제어자`
- `스태틱 변수 및 메서드`
- `싱글톤 디자인 패턴`
- `try~catch~finally 예외 처리`
- `Trouble Shooting: 싱글톤 객체 획득 시 new 키워드 미사용 문제`

---

## 💡 공부한 내용 정리하기

### 1. 패키지와 접근 제어자
- **패키지**: 비슷한 역할의 클래스들을 디렉터리 구조로 묶어 관리하는 단위
- **접근 제어자**: 변수나 메서드의 사용 범위를 제한하여 데이터를 보호함
  - `private`: 작성된 해당 클래스 내부에서만 접근 가능
  - `default`: 제어자를 붙이지 않았을 때 적용되며 동일한 패키지 내에서만 접근 가능
  - `protected`: 동일 패키지 내의 클래스 또는 상속받은 자식 클래스에서 접근 가능
  - `public`: 제한 없이 모든 클래스에서 접근 가능

---

### 2. 스태틱과 싱글톤 패턴
- **스태틱**: 객체 생성 시마다 매번 메모리를 할당하지 않고, 클래스 차원에서 단 하나만 생성하여 메모리를 공유함
- **싱글톤 패턴**: 단 하나의 객체만 생성되도록 보장하는 설계 방식으로, 전체 프로그램에서 공통된 상태나 기능을 다룰 때 활용함

---

### 3. 예외 처리
- **try~catch~finally**: 0으로 나누기처럼 프로그램 실행 중 에러가 발생할 가능성이 있는 코드 구문을 안전하게 제어함
- **finally 구문**: 예외 발생 여부와 관계없이 무조건 마지막에 실행되어 자원 해제 등의 작업을 처리함

---

## 🚨 Trouble Shooting: 싱글톤 객체 획득 시 new 키워드 미사용

### 1. 문제 상황
- 싱글톤 패턴이 적용된 클래스의 객체를 가져올 때 일반적인 객체 생성 방식처럼 `new` 키워드를 사용하려고 하여 오류를 겪음

### 2. 원인 분석
- 싱글톤 패턴은 클래스 외부에서 `new` 키워드로 인스턴스를 무분별하게 새로 생성하지 못하도록 생성자의 접근 제어자를 `private`으로 막아둠

### 3. 해결 방법
- `new` 키워드를 사용하지 않고, 클래스 내부에 미리 생성해둔 단 하나의 인스턴스를 반환하는 스태틱 메서드인 `getInstance()`를 직접 호출하여 동일한 객체 주소를 받아오도록 수정함

---

## 💻 자바 실습 코드 분석

<details>
<summary><b>🔍 실습 코드 펼치기 / 접기</b></summary>

```java
// region part 07
static void part07_1() {
    // 패키지: 비슷한 성격의 클래스들을 모아 놓은 자바의 디렉터리
    HouseKim kim = new HouseKim(); // 만약 상단에 package house를 선언한다면, HouseKim 사용을 위해서 import가 필요없다
    HousePark park = new HousePark();
}

static void part07_2() {
    // 접근 제어자: 변수나 메서드의 사용 권한을 설정할 수 있다
    // public < default < protected < public 순으로 보다 많은 접근을 허용한다
    // private: private이 붙은 변수나 메서드는 해당 클래스 안에서만 접근이 가능하다.
    // default: 접근 제어자를 별도로 설정하지 않는다면 변수나 메서드는 default 접근 제어자가 자동으로 설정되어 동일한 패키지 안에서만 접근이 가능하다
    // protected: protected가 붙은 변수나 메서드는 동일 패키지의 클래스 또는 해당 클래스를 상속받은 클래스에서만 접근이 가능하다
    // public: public 접근 제어자가 붙은 변수나 메서드는 어떤 클래스에서도 접근이 가능하다
}

static void part07_3() {
    // 스태틱: 클래스에서 공유되는 변수나 메서드를 정의할 때 사용된다
    HouseLee lee1 = new HouseLee(); // 객체마다 객체 변수 lastname을 저장하기 위한 메모리가 별도로 할당된다
    HouseLee lee2 = new HouseLee(); // static 키워드로 수정: 자바는 메모리 할당을 한 번만 하게 되어 메모리를 적게 사용할 수 있다

    Counter_07 c1 = new Counter_07(); // 값을 공유할 수 있게 된다
    Counter_07 c2 = new Counter_07(); // 1, 2 출력

    System.out.println(Counter_07.getCount()); // 2 출력

    System.out.println(Util.getCurrentDate("yyyyMMdd")); // 오늘 날짜 출력

    // 싱글톤 패턴: 단 하나의 객체만을 생성하게 강제하는 디자인 패턴
    // 클래스를 통해 생성할 수 있는 객체가 한 개만 되도록 만드는 것
    Singleton singleton1 = Singleton.getInstance(); // <- troubleshooting: new를 붙이지 않아도 되는 것을 몰랐음
    Singleton singleton2 = Singleton.getInstance();
    System.out.println(singleton1 == singleton2); // true 출력
}

static void part07_4() {
    // 예외 처리
    // 존재하지 않는 파일을 열려고 시도, 0으로 다른 숫자를 나누려고 시도, 배열에서 아무것도 없는 곳을 가리키려는 시도 등의 예외 처리

    // try~catch문: try문 안의 수행할 문장을 수행하는 도중에 예외가 발생하면 예외에 해당하는 catch문이 수행된다
    int c;
    Sample sample07 = new Sample();
    try {
        c = 4 / 0;
    } catch (ArithmeticException e) {
        c = -1;// 예외가 발생하여 이 문장이 수행된다
    } finally {
        sample07.shouldBeRun(); // 예외에 상관없이 무조건 수행된다
    }
}
```

</details>

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
7장의 접근 제어자, 스태틱 키워드, 싱글톤 패턴 및 예외 처리를 학습했다. 객체 생성과 데이터 보호를 위한 제어자 개념을 학습하고, 메모리 효율성을 위한 스태틱 변수와 싱글톤 디자인 패턴의 구조를 실습했다. 싱글톤 패턴 사용 시 생성자 접근을 제한하고 스태틱 메서드로 객체를 받아오는 과정을 확인하며 자바 객체 관리 메커니즘을 학습했다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 싱글톤 패턴을 활용해 앱 전체에서 설정값을 관리하는 인스턴스 예제 직접 구현해 보기
- **내일의 학습 계획**: 07장 07-5 ~ 07-6 (스레드, 함수형 프로그래밍, 람다, 스트림 등), 되새김문제 학습

---
#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
