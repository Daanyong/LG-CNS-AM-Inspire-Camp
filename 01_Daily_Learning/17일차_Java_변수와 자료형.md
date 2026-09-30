# [LG CNS 7기] 3주차 Day3 TIL
Java 변수(Variable) 개념, 데이터 타입 및 선언 구문 실습

## 📌 오늘의 학습 키워드
- `Java Variable (데이터를 담는 그릇)`
- `Primitive Type vs Reference Type`
- `Basic Data Types (byte, short, int, long, float, double, char, boolean, String)`
- `Variable Declaration Syntax ([접근지정자] [변수타입] [변수명] = value;)`
- `Scope of Variables (전역변수 vs 지역변수)`

---

## 💡 공부한 내용 본인의 언어로 정리하기

### 1. 변수의 개념 및 데이터 타입 분류
변수란 **데이터를 담을 수 있는 메모리 상의 그릇**을 의미한다.

- **기본 타입 (Primitive Type)**: 실제 값을 메모리에 직접 저장
  - **정수형**: `byte`, `short`, `int`, `long`
  - **실수형**: `float`, `double`
  - **문자형**: `char` (단일 문자, 작은따옴표 `' '` 사용)
  - **논리형**: `boolean` (`true` / `false`)
- **참조 타입 (Reference Type)**: 값이 위치한 주소값을 저장
  - 기본 타입을 제외한 모든 타입이 해당됨
  - **문자열형**: `String` (클래스 형태의 참조 타입, 큰따옴표 `" "` 사용)

---

### 2. 변수 선언 문법 및 위치
- **구문 형식**: `[접근지정자] [변수타입] [변수명] = value;`
  - `=`은 오른쪽의 값을 왼쪽 변수에 대입(할당)하는 할당 연산자임
- **선언 위치에 따른 분류**:
  - **전역변수 (Member/Field Variable)**: 클래스 영역에 선언되어 객체 전체에서 접근 가능한 변수
  - **지역변수 (Local Variable)**: 메서드나 블록(`{ }`) 내부에서 선언되어 해당 범위 내에서만 소멸 전까지 존재하는 변수

---

## 💻 Java 변수 선언 실습 코드 (`VarialbeApp.java`) 분석

```java
public class VarialbeApp {

    // [접근지정자] [변수타입] [변수명] = 할당값;
    
    // String (참조 타입 / 문자열)
    public String name = "dykang"; 
    
    // int (기본 타입 / 정수형)
    public int age = 24; 
    
    // double (기본 타입 / 실수형)
    public double height = 160.5; 
    
    // boolean (기본 타입 / 논리형)
    public boolean isMarraige = false; 
    
    // char (기본 타입 / 단일 문자형)
    public char gender = 'w'; 

}
```

실제 실습 코드

<img width="320" height="153" alt="스크린샷 2026-09-30 121242" src="https://github.com/user-attachments/assets/9cb12944-f03d-4f59-a995-596d78548d10" />

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
가장 기본 단위인 변수의 개념과 다양한 데이터 타입을 정리했다. 정수, 실수, 문자, 논리값을 저장하는 기본 타입과 문자열을 다루는 참조 타입의 차이를 이해하고, 접근 지정자와 변수를 선언 및 초기화하는 문법 구조를 `VarialbeApp.java` 실습을 통해 직접 작성하였다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 오늘 작성한 `VarialbeApp` 클래스 내에 `main` 메서드를 추가하고, `System.out.println`을 활용해 각 변수에 담긴 값들을 콘솔에 출력하는 실습 진행해보기
- **내일의 학습 계획**: Java 4강 수강

---

#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
