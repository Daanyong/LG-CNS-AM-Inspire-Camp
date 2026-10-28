# [LG CNS 7기] 5주차 Day4 TIL
자바 조건문 if문 및 논리 연산자 실습

## 📌 오늘의 학습 키워드
- `if문과 else문의 기본 구조`
- `비교 연산자`
- `논리 연산자`
- `리스트와 contains 활용 조건문`
- `else if문을 활용한 다중 조건 처리`

---

## 💡 공부한 내용 정리하기

### 1. 조건문 if문과 비교 연산자
- **기본 구조**: 조건식이 참일 경우 실행되는 코드 블록과 거짓일 경우 실행되는 코드 블록을 분기함
- **비교 연산자**: 크기 비교와 값이 같은지 또는 다른지를 판별하여 참과 거짓의 결과를 반환함

---

### 2. 논리 연산자와 리스트 조건문
- **논리 연산자**:
  - `&&`: 두 조건이 모두 참이어야 참
  - `||`: 두 조건 중 하나만 참이어도 참
  - `!`: 조건의 결과를 반대로 뒤집음
- **리스트 연산**: `List` 자료형의 `contains` 메서드를 조건식에 활용해 특정 요소가 들어있는지에 따라 화면 출력을 다르게 제어할 수 있음

---

### 3. 중첩 조건문 개선과 else if문
- **else if문**: 조건이 여러 개일 때 중첩된 구문을 사용하면 코드가 복잡해지므로, `else if`를 활용해 가독성 높게 여러 조건을 순차적으로 체크할 수 있음

---

## 💻 자바 실습 코드 분석

<details>
<summary><b>🔍 실습 코드 펼치기 / 접기</b></summary>

```java
static void part04_1(){
    // 04_1 if 문

    // if 문과 else 문의 기본 구조
    // if 문은 주어진 조건이 참일 때 실행되는 코드 블록
    // else 문은 if 문의 조건이 거짓일 때 실행되는 코드 블록

    // 비교 연산자
    int x = 3;
    int y = 2;

    System.out.println(x > y); // true 출력
    System.out.println(x < y); // false 출력
    System.out.println(x == y); // false 출력
    System.out.println(x != y); // true 출력

    int money = 2000;
    if (money >= 3000) {
        System.out.println("택시를 타고 가라");
    } else {
        System.out.println("걸어가라");
    }
    // 걸어가라 출력

    // and, or, not 연산자
    // x && y : x와 y 모두 참이어야 참이다 (and)
    // x || y : x와 y 둘 중 적어도 하나가 참이면 참이다 (or)
    // !x : x가 거짓이면 참이다 (not)

    boolean hasCard = true;
    if (money >= 3000 || hasCard) { // x||y: x와 y 둘 중 적어도 하나가 참이면 참이다.
        System.out.println("택시를 타고 가라");
    } else {
        System.out.println("걸어가라");
    }
    // 택시를 타고 가라 출력

    // contains: List 자료형에 해당 아이템이 있는지 조사
    ArrayList<String> pocket = new ArrayList<String>();
    pocket.add("paper");
    pocket.add("cellphone");
    pocket.add("money");

    if (pocket.contains("money")) {
        System.out.println("택시를 타고 가라");
    } else {
            System.out.println("걸어가라");
    } // 택시를 타고 가라 출력

    // else if
    boolean hasCard_ei = true;
    ArrayList<String> pocket_ei = new ArrayList<String>();
    pocket_ei.add("paper");
    pocket_ei.add("cellphone");
    
    
    // 아래 코드를 else if 문으로 수정해보자
    /*
    if (pocket_ei.contains("money")) {
        System.out.println("택시를 타고 가라");
    } else {
        if (hasCard) {
            System.out.println("택시를 타고 가라");
        } else {
            System.out.println("걸어가라");
        }
    }
    */

    if (pocket_ei.contains("money")) {
        System.out.println("택시를 타고 가라");
    } else if (hasCard) {
            System.out.println("택시를 타고 가라");
        } else {
            System.out.println("걸어가라");
        }

}
```

</details>

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
자바 제어문의 `if`문 구조와 논리 연산자, 그리고 `else if` 분기 처리를 학습했다. 중첩된 `if` 구문을 `else if`로 개선해 보면서 상황에 따른 프로그램 흐름 제어 방법과 코드 가독성을 다듬는 것을 연습했다. 지금까지 사용했던 언어와 비슷한듯 또 달라서, 헷갈리지 않게 주의해야겠다는 생각이 들었다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 오늘 다룬 `if`문과 논리 연산자를 활용해 점수에 따라 A, B, C 학점을 부여하는 간단한 조건문 예제 작성해 보기
- **내일의 학습 계획**: 04장 제어문 다지기 (switch문, while문, for문)

---
#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
