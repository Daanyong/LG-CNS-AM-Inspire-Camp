# [LG CNS 7기] 5주차 DAY1 TIL
자바 상수 집합과 형 변환 실습

## 📌 오늘의 학습 키워드
- `상수 집합`
- `문자열과 숫자 간의 형 변환`
- `정수와 실수 간의 형 변환`

---

## 💡 공부한 내용 정리하기

### 1. 상수 집합
- **상수들의 모임**: 서로 관련이 있는 정해진 데이터들을 묶어서 관리할 때 사용함
- **장점**: 숫자나 일반 문자열 같은 임의의 값을 직접 사용하는 것보다 코드가 명확해지고, 허용되지 않는 잘못된 값이 들어오는 오류를 방지해 줌
- **활용**: `values()`를 사용하면 정의된 모든 항목을 순서대로 하나씩 불러와 출력할 수 있음

---

### 2. 형 변환
- **문자열을 숫자로 변환**:
  - `Integer.parseInt`를 활용해 숫자로 된 문자열을 정수형 데이터로 바꿀 수 있음
  - `Double.parseDouble`을 활용해 소수점이 포함된 문자열을 실수형 데이터로 바꿀 수 있음
- **숫자를 문자열로 변환**:
  - 숫자에 빈 문자열(`""`)을 더하거나, `String.valueOf` 또는 `Integer.toString`을 사용해 문자열로 변환함
- **정수와 실수 간의 변환**:
  - 정수를 실수로 바꿀 때는 별도의 변환 작업 없이 자동으로 소수점이 붙어 대입됨
  - 반대로 실수를 정수로 바꿀 때는 소수점 아래 자리가 버려지므로 강제로 형을 지정해 주는 변환 작업이 필요함

---

## 💻 자바 실습 코드 분석

<details>
<summary><b>🔍 실습 코드 펼치기 / 접기</b></summary>

```java
public static void part03_10() {
    // 03-10 상수 집합

    // enum: 서로 연관 있는 여러 개의 상수 집합을 정의할 때 사용
    enum CoffeeType {
        AMERICANO,
        ICE_AMERICANO,
        CAFE_LATTE
    };

    System.out.println(CoffeeType.AMERICANO); // AMERICANO 출력
    System.out.println(CoffeeType.ICE_AMERICANO); // ICE_AMERICANO 출력
    System.out.println(CoffeeType.CAFE_LATTE); // CAFE_LATTE 출력

    for(CoffeeType type: CoffeeType.values()) {
        System.out.println(type); // 순서대로 AMERICANO, ICE_AMERICANO, CAFE_LATTE 출력
    }

    // 매직 넘버를 사용할 때보다 코드가 명확하다
    // 잘못된 값을 사용해 생길 수 있는 오류를 막을 수 있다
}

public static void part03_11() {
    // 03-11 형 변환과 final

    // 형 변환
    String num = "123";
    int n = Integer.parseInt(num);
    System.out.println(n); // 123 출력

    int n2 = 123;
    String num2 = ""+n;
    System.out.println(num); // 123 출력

    int n3 = 123;
    String num3 = String.valueOf(n);
    String num4 = Integer.toString(n);
    System.out.println(num3); // 123 출력
    System.out.println(num4); // 123 출력

    String num5 = "123.456";
    double d = Double.parseDouble(num5);
    System.out.println(d);

    int num6 = 123;
    double num7 = num6; // 정수를 실수로 바꿀 때에는 캐스팅이 필요 없다.
    System.out.println(num7); // 123.0 출력

    double num8 = 123.456;
    int num9 = (int) num8; // 실수를 정수로 바꿀 때에는 반드시 정수형으로 캐스팅해 주어야 한다.
    System.out.println(num9); // 소수점이 생략된 123 출력


}
```

</details>

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
상수 집합과 다양한 형 변환 방식을 실습했다. 데이터를 주고받는 과정에서 형태를 안전하게 맞추는 것이 중요할 것 같다는 생각을 했고, 열거형을 활용해 코드의 오류를 줄이는 방법을 학습했다. 실무에서 자주 쓰이는 변환 구문들이라고 해서 더 꼼꼼하게 학습했던 것 같다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 오늘 다룬 형 변환을 활용해 사용자에게 문자열로 입력받은 두 숫자를 정수로 바꾸어 더한 값을 출력하는 등의 간단한 코드 작성해 보기
- **내일의 학습 계획**: 04장 학습

---
#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
