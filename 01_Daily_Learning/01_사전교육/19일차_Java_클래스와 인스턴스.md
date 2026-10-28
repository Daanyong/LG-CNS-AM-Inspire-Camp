# [LG CNS 7기] 3주차 Day5 TIL
Java 5강 수강 - 클래스 설계, 객체 생성(new) 및 인스턴스 멤버 접근 실습

## 📌 오늘의 학습 키워드
- `Class Design (Teacher.java)`
- `Object Instantiation (new ClassName())`
- `Reference Type & Heap Memory`
- `Instance Field Access (.)`
- `Method Invocation with Argument & Return Value`

---

## 💡 공부한 내용 본인의 언어로 정리하기

### 1. 클래스 정의와 멤버 (속성 + 메서드)
- 설계도 역할을 하는 `Teacher` 클래스 내에 필드와 메서드를 정의함
- **필드**: `name`, `age`, `address`, `isMarraige` 등의 전역 변수 선언
- **메서드**:
  - `void eating()`: 반환값이 없는 단순 출력 기능
  - `String teaching(String subject)`: 매개변수(`subject`)를 전달받아 가공된 문자열 결과값을 반환(`return`)하는 기능

---

### 2. 객체 생성(`new`)과 참조 변수
- **객체 생성 문법**: `클래스명 변수명 = new 클래스명();`
  - `Teacher tea = new Teacher();`
- **메모리 동작 구조**:
  - `new Teacher()`: 힙 메모리 영역에 실제 객체를 생성
  - `Teacher tea`: 스택 영역에 존재하며, 힙 메모리에 생성된 객체의 주소값을 저장하는 **참조 변수**
- **멤버 접근 연산자 (`.`)**:
  - 생성된 객체의 주소를 가지는 참조 변수 뒤에 `.` 연산자를 붙여 필드에 값을 할당하거나 메서드를 호출함

---

## 💻 Java 실습 코드 분석

### 1) `Teacher.java` (클래스 설계)
```java
public class Teacher {
    // 필드 (속성)
    public String name;
    public int age;
    public String address;
    public boolean isMarraige;

    // 반환값이 없는 메서드 (void)
    public void eating() {
        System.out.println("강의하기 위해서 밥을 먹는다.");
    }

    // 매개변수(subject)와 반환값(String)이 있는 메서드
    public String teaching(String subject) {
        return "강사님이 가르치는 과목은 " + subject + " 입니다.";
    }
}
```

### 2) `TeacherApp.java` (객체 생성 및 실행)
```java
public class TeacherApp {
    public static void main(String[] args) {
        
        // 1. Teacher 객체 생성 (힙 메모리에 인스턴스 생성 및 주소값 저장)
        Teacher tea = new Teacher();

        // 2. 참조 연산자(.)를 통한 필드 값 할당 및 출력
        tea.name = "dykang";
        System.out.println(tea.name); // 출력: dykang

        // 3. 반환값 없는 메서드 호출
        tea.eating(); // 출력: 강의하기 위해서 밥을 먹는다.

        // 4. 매개변수를 전달하여 반환값이 있는 메서드 호출 및 결과 출력
        String result = tea.teaching("자바");
        System.out.println(result); // 출력: 강사님이 가르치는 과목은 자바 입니다.
    }
}
```

---

## 🖥️ 터미널 실행 결과

```text
dykang
강의하기 위해서 밥을 먹는다.
강사님이 가르치는 과목은 자바 입니다.
```

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
클래스와 인스턴스의 개념을 `Teacher`와 `TeacherApp` 코드로 직접 구현하였다. `new` 키워드가 힙 메모리에 새로운 공간을 할당한다는 것과 참조 변수가 그 주소값을 가리키고 있다는 것을 배웠다. 본 교육 시작 전 개인적으로 자바 공부를 더 하고 가야겠다는 생각도 들었다.
### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: `Teacher` 객체를 `tea2`로 하나 더 생성(`new Teacher()`)해 보고, 두 객체가 서로 다른 힙 메모리 주소를 가지며 필드 값이 독립적으로 유지되는지 확인하기
- **내일의 학습 계획**: Java 6강 수강

---

#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
