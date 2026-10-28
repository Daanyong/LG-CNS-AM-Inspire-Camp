# [LG CNS 7기] 4주차 Day6 TIL
자바 주요 자료형(숫자, 불, 문자, 문자열)과 StringBuffer 성능 비교 및 내장 메서드 실습

## 📌 오늘의 학습 키워드
- `숫자형 자료형 및 연산자`
- `불 자료형 및 조건식`
- `문자(char)와 아스키/유니코드`
- `문자열(String) 리터럴 vs new 객체 생성`
- `문자열 주요 내장 메서드`
- `StringBuffer의 메모리 이점 및 활용`

---

## 💡 공부한 내용 정리하기

### 1. 숫자, 불, 문자 자료형의 특징
- **숫자형**: 정수형(`int`, `long`)과 실수형(`float`, `double`)으로 구분되며, 접미사(`L`, `F`)로 형태를 명시함. 증감 연산자(`i++`, `++i`)의 위치에 따라 값의 대입 및 출력 시점이 달라짐
- **불(boolean)**: `true` 또는 `false` 값만 가지며, 조건문과 비교 연산(`>`, `==`)의 결과로 자주 활용됨
- **문자(char)**: 작은따옴표(`' '`)로 감싸며, 아스키코드 정수값(예: `97`)이나 유니코드(예: `\u0061`) 형태로도 표현 가능함

---

### 2. String 클래스의 특징 및 주요 메서드
- **리터럴 표기법 vs new 키워드**:
  - 리터럴 방식(`String a = "hello"`)은 가비지 컬렉션 및 메모리 재사용에 이점이 있음
  - `new` 키워드로 생성 시 항상 서로 다른 객체가 생성되므로, 문자열 값 자체 비교에는 `==` 연산자가 아닌 `equals()` 메서드를 사용해야 함
- **주요 내장 메서드**:
  - `equals()`: 문자열 값 동등성 비교
  - `indexOf()` / `contains()` / `charAt()`: 위치 검색, 포함 여부 확인, 특정 인덱스 문자 추출
  - `replaceAll()` / `substring()`: 문자열 치환 및 부분 추출
  - `split()`: 구분자를 기준으로 문자열 배열 생성
  - `String.format()` / `System.out.printf()`: 문자열 포매팅(%d, %s, %f 등) 및 출력

---

### 3. StringBuffer 자료형의 필요성
- **String 결합 연산의 단점**: `String`은 불변 객체이므로 `+` 연산자로 문자열을 이어 붙일 때마다 새로운 객체가 메모리에 계속 생성됨
- **StringBuffer의 장점**: 가변 객체이므로 `append()`나 `insert()` 메서드를 사용해 기존 메모리 공간에서 문자열을 효율적으로 수정함
- **결론**: 문자열 추가/수정이 빈번하게 일어나는 백엔드 로직에서는 `StringBuffer`나 `StringBuilder`를 사용하는 것이 메모리 효율성 면에서 뛰어남

---

## 💻 자바 실습 코드 분석 (`Sample.java`)

<details>
<summary><b>🔍 Sample.java 실습 코드 펼치기 / 접기</b></summary>

```java
import java.util.Locale;

public class Sample {
    public static void main(String[] args) {
        // part03_1();
        // part03_2();
        // part03_3();
        // part03_4();
        // part03_5();
    }

    public static void part03_1() {
        int a = 10;
        int b = 5;

        System.out.println(a+b); // 15
        System.out.println(a-b); // 5
        System.out.println(a*b); // 50
        System.out.println(a/b); // 2

        System.out.println(7 % 3); // 1
        System.out.println(3 % 7); // 3

        int i =0;
        int j = 10;

        i++;
        j--;

        System.out.println(i); // 1
        System.out.println(j); // 9

        i = 0;
        System.out.println(i++); // 0
        System.out.println(i); // 1

        i = 0;
        System.out.println(++i); // 1
        System.out.println(i); // 1
    }

    public static void part03_2() {
        int base = 180;
        int height = 185;
        boolean isTall = height > base;

        if (isTall) {
            System.out.println("키가 큽니다.");
        }

        int i = 3;
        boolean isOdd = i % 2 == 1;
        System.out.println(isOdd);
    }

    public static void part03_3() {
        char a1 = 'a'; // 문자로 표현
        char a2 = 97; // 아스키코드로 표현
        char a3 = '\u0061'; // 유니코드로 표현

        System.out.println(a1); // a 출력
        System.out.println(a2); // a 출력: 아스키코드 97번에 해당하는 a 출력
        System.out.println(a3); // a 출력: 16진수 유니코드 값 -> 유니코드 0061에 해당하는 a 출력

    }

    public static void part03_4() {
        String a = "Hello Java";

        // IndexOf: 문자열에서 특정 문자열이 시작되는 위치(인덱스값)를 리턴
        System.out.println(a.indexOf("Java")); // 6 출력

        // contains: 문자열에서 특정 문장ㄹ이 포함되어 있는지 여부를 리턴
        System.out.println(a.contains("Java")); // true 출력

        // charAt: 문자열에서 특정 위치의 문자를 리턴
        System.out.println(a.charAt(6)); // J 출력

        // replaceAll: 문자열에서 특정 문자열을 다른 문자열로 바꿀 때 사용
        System.out.println(a.replaceAll("Java", "World")); // Hello World 출력

        // substring: 문자열에서 특정 문자열을 뽑아낼 때 사용
        System.out.println(a.substring(0, 4)); // Hell 출력

        // toUpperCase: 문자열을 모두 대문자로 변경할 때 사용
        System.out.println(a.toUpperCase()); // "HELLO JAVA" 출력

        // split: 문자열을 특정한 구분자로 나누어 문자열 배열로 리턴
        String s = "a:b:c:d";
        String[] result = s.split(":"); // result는 {"a", "b", "c", "d"}

        // 숫자 바로 대입하기
        System.out.println(String.format("I eat %d apples", 3)); // I eat 3 apples 출력

        // 문자열 바로 대입하기
        System.out.println(String.format("I eat %s apples", "five")); // I eat five apples 출력

        // 숫자값을 나타내는 변수 대입하기
        int number = 3;
        System.out.println(String.format("I eat %d apples", number)); // I eat 3 apples 출력

        // 값을 2개 이상 넣기
        int n = 10;
        String day = "three";
        System.out.println(String.format("I ate %d apples. So I was sick for %s days", n, day));

        // 정렬과 공백 표현하기
        System.out.println(String.format("%10s", "hi")); // "        hi" 출력 (공백 8개)
        System.out.println(String.format("%-10sjane", "hi")); // "hi        jane" 출력 (공백 8개)

        // 소수점 표현하기
        System.out.println(String.format("%.4f", 3.42134234)); // 3.4213 출력

        // 응용
        System.out.println(String.format("%10.4f", 3.42134234)); // "    3.4213" 출력 (공백 4개)

        System.out.printf("I eat %d apples", 3); // "I eat 3 apples" 출력
        System.out.printf("I eat %d apples", 3);
    }

    public static void part03_5() {
        StringBuffer sb = new StringBuffer();
        sb.append("hello");
        sb.append(" ");
        sb.append("jump to java");
        String result = sb.toString();
        System.out.println(result); // hello jump to java 출력

        String r = "";
        r += "hello";
        r += " ";
        r += "jump to java";
        System.out.println(r); // hello jump to java 출력

        StringBuffer sb2 = new StringBuffer();
        sb2.append("jump to java");
        sb2.insert(0, "hello ");
        System.out.println(sb2.toString()); // hello jump to java 출력

        StringBuffer sb3 = new StringBuffer();
        sb3.append("Hello jump to java");
        System.out.println(sb3.substring(0, 4)); // Hell 출력
    }
}
```

</details>

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
단순히 문자열을 선언하고 사용하는 것을 넘어, 메모리 상에서 `String`과 `StringBuffer`가 어떻게 다르게 동작하는지 학습했다. `+` 연산자로 문자열을 더하면 백엔드 서버 메모리에 부담을 줄 수 있음을 알았다. `equals()`와 `==`의 차이 등을 다시 확실하게 정의하였다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 여러 자료형 및 연산들 섞어 적용해보기
- **내일의 학습 계획**: 03장 자바의 기초, 자료형 03-6 ~ 03-9 (배열, 리스트, 맵, 집합 등) 학습

---
#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
