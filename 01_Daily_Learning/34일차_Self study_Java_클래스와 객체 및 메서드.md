# [LG CNS 7기] 5주차 Day6 TIL
자바 클래스와 객체 및 메서드 개념 정리

## 📌 오늘의 학습 키워드
- `객체지향 프로그래밍과 클래스`
- `객체와 인스턴스`
- `객체 변수의 독립성`
- `메서드의 4가지 유형`
- `return의 역할과 변수의 효력 범위`

---

## 💡 공부한 내용 정리하기

### 1. 클래스와 객체
- **개념 관계**: 클래스는 객체를 만들어내기 위한 틀이며, 이 틀을 통해 만들어진 실체를 객체 또는 인스턴스라고 부름
- **독립성**: 동일한 클래스로부터 생성된 객체들이라도 각 객체의 내부 변수는 서로 공유되지 않고 독립적인 상태를 유지함

---

### 2. 메서드의 구조와 4가지 유형
- **입력값과 리턴값이 모두 있는 유형**: 매개변수를 전달받아 연산 후 결과를 호출한 곳으로 돌려줌
- **입력값만 있는 유형**: 매개변수를 받아서 내부 로직이나 출력을 수행하며 리턴값은 없음
- **리턴값만 있는 유형**: 전달받는 매개변수 없이 정해진 값을 리턴함
- **입력값과 리턴값이 모두 없는 유형**: 매개변수와 리턴값 없이 지정된 동작만 수행함

---

### 3. return문과 변수의 효력 범위
- **의미 탈출**: 리턴 타입이 없는 메서드에서 `return`을 사용하면 값을 반환하는 것이 아니라 메서드를 그 즉시 종료하고 빠져나가는 역할을 함
- **변수의 범위**: 메서드 내부에서 선언되거나 매개변수로 전달된 변수는 해당 메서드 내부에서만 효력이 미치며, 외부 변수값을 변경하려면 리턴받은 값을 다시 대입하거나 객체 자체를 전달해야 함

---

## 💻 자바 실습 코드 분석

<details>
<summary><b>🔍 실습 코드 펼치기 / 접기</b></summary>

```java
// region part 05
static void part05_1() {
    // 객체 지향 프로그래밍이란?
    Calculator cal1 = new Calculator(); // 계산기1 객체 생성
    Calculator cal2 = new Calculator(); // 계산기2 객체 생성

    System.out.println(cal1.add(3)); // 3 출력
    System.out.println(cal1.add(4)); // 7 출력

    System.out.println(cal2.add(3)); // 3 출력
    System.out.println(cal2.add(7)); // 10 출력
}

static void part05_2() {
    // 클래스
    // 클래스: 과자 모양을 찍어 내는 과자 틀
    // 객체: 과자 틀에 의해 만들어진 과자들

    Animal cat = new Animal(); // cat이라는 객체는 Animal의 인스턴스
    cat.setName("body"); // 메서드 호출
    System.out.println(cat.name); // null 출력 -> class에서 this.name = name; 추가, 선언 후: body 출력

    // 객체 변수는 공유되지 않는다
    Animal dog = new Animal();
    dog.setName("happy");

    System.out.println(cat.name); // body 출력
    System.out.println(dog.name); // happy 출력

}

// 아래는 part05_3을 위한 함수들

// 입력값과 리턴값이 모두 있는 메서드
int sum(int a, int b) { // a, b는 매개 변수
    return a + b;
}

// 입력값이 없는 메서드
String say() { //
    return "Hi";
}

// 리턴값이 없는 메서드
void sum_2(int a, int b) {
    System.out.println(a + "과 " + b + "의 합은 " + (a+b) + "입니다.");
}

// 입력값과 리턴값이 모두 없는 메서드
void say_2() {
    System.out.println("Hi");
}

void sayNick(String nick) { // 리턴값이 없는 void에서만 사용 가능
    if ("바보".equals(nick)) {
        return; // 메서드를 호출한 곳에 리턴값을 돌려주는 역할이 아니라 메서드를 즉시 빠져나가는 역할을 한다.
        // "바보"라는 값이 들어오면 문자열을 출력하지 않고 메서드를 즉시 빠져나간다
    }
    System.out.println("나의 별명은 "+nick+" 입니다.");
}

void varTest(int a) {
    a++;
}

int varTest_2(int a) {
    a++;
    return a;
}

int a_var_3; // 객체 변수

void varTest_3(Sample sample) {
    sample.a_var_3++;
}

static void part05_3() {
    // 메서드 더 살펴보기
    int a = 3;
    int b = 4;

    Sample sample = new Sample();

    int c = sample.sum(3, 4); // 3, 4는 인수

    System.out.println(c); // 7 출력

    String s = sample.say();
    System.out.println(s); // Hi 출력

    sample.sum_2(3, 4); //  3과 4의 합은 7입니다 출력

    sample.say_2();

    // return의 또 다른 쓰임
    sample.sayNick("야호");
    sample.sayNick("바보"); // 출력되지 않는다.

    // 메서드 내에서 선언된 변수의 효력 범위
    int a_var1 = 1;
    sample.varTest(a_var1);
    System.out.println(a_var1); // 1 출력

    int a_var_2 = 1;
    a_var_2 = sample.varTest_2(a_var_2);
    System.out.println(a_var_2); // 2 출력

    sample.a_var_3 = 1;
    sample.varTest_3(sample);
    System.out.println(sample.a_var_3); // 2 출력
}
```

</details>

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
자바의 가장 핵심 개념인 객체지향 프로그래밍과 클래스, 메서드의 기본 동작 구조를 학습했다. 객체를 생성할 때 각 인스턴스의 변수가 서로 독립적으로 관리된다는 점과 다양한 메서드 형태에 따른 데이터 전달 방식을 주의해야 할 것 같다. 특히 메서드 내부 변수의 범위와 리턴문의 구체적인 역할을 코드로 직접 검증하는게 재밌었다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 매개변수로 전달받은 숫자가 음수일 때 `return`으로 즉시 빠져나가는 예외 처리 메서드 직접 만들어보기
- **내일의 학습 계획**: 05장 객체지향 프로그래밍 05-4 ~ 05-7 (값에 의한 호출, 객체에 의한 호출, 객체 변수, 메서드, 매개 변수, 인수 등) 학습

---

#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
