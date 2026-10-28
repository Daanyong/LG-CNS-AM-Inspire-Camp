# [LG CNS 7기] 6주차 Day1 TIL
자바 다형성, 인터페이스 활용 및 5장 연습문제 실습

## 📌 오늘의 학습 키워드
- `다형성과 인터페이스`
- `상속을 활용한 기능 확장`
- `메서드 오버라이딩과 오버로딩`
- `객체 참조와 메모리 구조`
- `인터페이스를 활용한 다형성 설계`

---

## 💡 공부한 내용 정리하기

### 1. 다형성과 인터페이스 활용
- **다형성**: 하나의 객체가 여러 가지 타입을 가질 수 있는 성질로, 인터페이스를 구현한 클래스들은 해당 인터페이스 타입으로 묶어서 제어할 수 있음
- **유연한 설계**: 경비원 클래스가 각 동물의 개별 타입을 일일이 알지 않아도, 인터페이스 형태 하나만으로 다양한 동물의 짖는 동작을 통합하여 실행할 수 있음

---

### 2. 5장 연습문제를 통한 객체지향 개념 복습
- **상속과 메서드 추가**: 부모 클래스를 상속받아 빼기 기능을 추가한 계산기 클래스를 구현함
- **메서드 오버라이딩**: 부모의 더하기 동작을 재정의하여 값이 특정 한계를 넘지 못하도록 제한하는 로직을 적용함
- **메서드 오버로딩**: 동일한 평균 계산 메서드 이름을 유지하면서 정수 배열과 리스트 두 가지 형태의 매개변수를 모두 처리할 수 있도록 구현함
- **객체 참조**: 변수에 리스트를 대입할 때 객체가 복사되는 것이 아니라 동일한 주소를 가리키게 되어 한쪽의 변경이 다른 쪽에도 그대로 반영됨을 확인함
- **인터페이스 응용**: 광물 인터페이스를 정의하고 각 광물 클래스가 이를 구현하게 함으로써, 계산기 클래스가 개별 광물 타입에 의존하지 않고 다형성을 통해 총가치를 합산하도록 설계함

---

## 💻 자바 실습 코드 분석

<details>
<summary><b>🔍 주요 실습 코드 펼치기 / 접기</b></summary>

```java
interface Barkable {
    void bark();
}

class Tiger extends Predator implements Barkable {
    public String getFood() {
        return "apple";
    }
    public void bark() { System.out.println("어흥"); }
}

class Lion extends Predator implements Barkable {
    public String getFood() {
        return "banana";
    }
    public void bark() { System.out.println("으르렁"); }
}

class Bouncer {
    void barkAnimal(Barkable animal) {
        animal.bark();
    }
}

class Calculator_review {
    int value;

    void add(int val) { this.value += val; }

    int getValue() { return this.value; }
}

class UpgradeCalculator extends Calculator_review { void minus(int val) { this.value -= val; } }

class MaxLimitCalculator extends Calculator_review {
    void add(int val) {
        this.value += val;

        if (this.value > 100) { this.value = 100; }
    }
}

class Calculator_4 {
    int avg(int[] data) {
        int total = 0;
        for (int nums : data) { total += nums; }
        return total / data.length;
    }

    int avg(ArrayList<Integer> data) {
        int total = 0;
        for (int nums : data) { total += nums; }
        return total / data.size();
    }
}

interface Mineral {
    int getValue();
}

class Gold implements Mineral {
    public int getValue() { return 100; }
}

class Silver implements Mineral {
    public int getValue() { return 90; }
}

class Bronze implements Mineral {
    public int getValue() { return 80; }
}

class MineralCalculator {
    int value = 0;

    public void add(Mineral mineral) { this.value += mineral.getValue(); }

    public int getValue() { return this.value; }
}

static void part05_8() {
    // 다형성
    Tiger tiger = new Tiger();
    Lion lion = new Lion();

    Bouncer bouncer = new Bouncer();
    bouncer.barkAnimal(tiger); // 어흥 출력
    bouncer.barkAnimal(lion); // 으르렁 출력
}

static void part05_review() {
    // 되새김문제

    // Q1 클래스 상속하고 메서드 추가하기
    UpgradeCalculator cal2 = new UpgradeCalculator();
    cal2.add(10);
    cal2.minus(3);
    System.out.println(cal2.getValue());

    // Q2 클래스 상속하고 메서드 오버라이딩하기
    MaxLimitCalculator cal_2 = new MaxLimitCalculator();
    cal_2.add(50);
    cal_2.add(60);
    System.out.println(cal_2.getValue()); // 100 출력

    // Q4 메서드 오버로딩하기
    int[] data = {1, 3, 5, 7, 9};
    ArrayList<Integer> data_2 = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 9));
    Calculator_4 cal_4 = new Calculator_4();
    System.out.println(cal_4.avg(data));
    System.out.println(cal_4.avg(data_2));

    // Q7 인터페이스 사용하기
    MineralCalculator cal_7 = new MineralCalculator();
    cal_7.add(new Gold());
    cal_7.add(new Silver());
    cal_7.add(new Bronze());
    System.out.println(cal_7.getValue()); // 270 출력
}
```

</details>

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
5장 전체를 복습하고 되새김문제를 풀이했다. 인터페이스를 활용한 다형성의 개념을 코드로 확인하고, 상속과 메서드 오버라이딩 및 오버로딩 구조를 연결해 보았다. 광물 계산기 예제를 통해 객체지향적으로 코드를 설계했을 때 모듈 간의 결합도가 낮아지고 확장성이 높아진다는 점을 확인하였다. 관련 문제를 더 풀어보며 손에 익을 때까지 연습해봐야 할 것 같다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 인터페이스 다형성 예제 활용하여 학점 계산 인터페이스를 두고 다양한 계산 로직 클래스들을 연결하는 예제 만들어보기
- **내일의 학습 계획**: 06장 자바의 입출력(콘솔 입출력, 파일 입출력 등), 되새김문제 학습

---
#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
