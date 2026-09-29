# [LG CNS 7기] 3주차 Day2 TIL
Java 객체지향 프로그래밍(OOP) 개요 및 첫 Hello World 출력 실습

## 📌 오늘의 학습 키워드
- `Java & OOP (Object Oriented Programming)`
- `Class vs Instance`
- `Variable (Attribute/Property) & Method`
- `JDK, JRE, JVM & Compiler (javac/java)`
- `Eclipse Temurin JDK 17 LTS`
- `Java Naming Convention (Identifier)`

---

## 💡 공부한 내용 본인의 언어로 정리하기

### 1. 객체지향 프로그래밍 (OOP) 기본 개념
- **Class vs Instance**: 클래스는 객체를 만들어내기 위한 설계도(틀)이며, 인스턴스는 해당 설계도를 바탕으로 메모리에 실제 생성된 객체임
- **변수 (Variable / Attribute / Property)**: 객체의 상태 및 속성 데이터를 저장하는 공간
- **메서드 (Method)**: 객체가 수행할 동작 및 기능을 정의한 코드 블록

---

### 2. Java 실행 환경과 컴파일 과정
- **JDK (Java Development Kit)**: 개발을 위한 도구 모음 (`javac` 컴파일러 등 포함)
- **JRE (Java Runtime Environment)**: 자바 프로그램을 실행하기 위한 환경
- **JVM (Java Virtual Machine)**: 자바 바이트코드를 해당 OS에 맞게 해석하고 실행하는 가상 머신
- **컴파일 및 실행 (`javac` / `java`)**:
  - 작성한 `.java` 소스 코드는 `javac` 컴파일러에 의해 `.class` 바이트코드로 변환된 후, JVM을 통해 실행됨
- **개발 환경 세팅**: Eclipse Temurin JDK 17 LTS 버전을 설치하고 프로젝트 환경 구성 완료

---

### 3. 식별자(Identifier) 명명 규칙 (Naming Convention)
- 자바는 **대소문자를 엄격하게 구분**함
- **클래스명**: PascalCase (첫 글자 대문자, 예: `Teacher`, `GreetingApp`)
- **변수명**: camelCase (첫 글자 소문자, 예: `anySum`)
- **메서드명**: camelCase (첫 글자 소문자, 동사형, 예: `playing`, `playGround`)

---

## 💻 Java 첫 실습 코드 (`GreetingApp.java`) 분석

```java
// 클래스 선언: 파일명(GreetingApp.java)과 동일해야 함
public class GreetingApp {

    // 자바 프로그램의 시작점(Entry Point)인 main 메서드 선언
    public static void main(String args[]) {
        
        // 콘솔 화면에 문자열을 출력하고 줄바꿈을 수행하는 표준 출력 문장
        System.out.println("Hello World");
    }
}
```

실제 실습 코드

<img width="356" height="139" alt="스크린샷 2026-09-29 092054" src="https://github.com/user-attachments/assets/4f4dd527-d91c-4950-9faf-f6b4a8d65344" />

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
본격적으로 백엔드 핵심 언어인 Java 학습을 시작했다. JDK 17 설치부터 시작하여 `javac` 컴파일러를 확인했다. 클래스와 인스턴스의 차이, 대소문자 구분의 엄격함, 그리고 식별자 표기법을 준수하며 작성한 첫 `GreetingApp` 실습을 통해 Java 언어의 기초를 학습했다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 오늘 작성한 `GreetingApp.java` 코드를 터미널(CLI)에서 `javac`로 직접 컴파일하여 `.class` 파일 생성을 확인하고 `java` 명령어로 실행해보기
- **내일의 학습 계획**: Java 3강 - 조건문과 반복문 수강

---

#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
