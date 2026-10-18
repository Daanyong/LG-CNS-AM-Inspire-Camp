# [LG CNS 7기] 5주차 Day7 TIL
자바 상속, 오버라이딩/오버로딩, 생성자 및 인터페이스 정리

## 📌 오늘의 학습 키워드
- `객체 전달과 값의 변경`
- `클래스 상속`
- `메서드 오버라이딩과 오버로딩`
- `생성자와 생성자 오버로딩`
- `인터페이스와 디폴트 메서드`

---

## 💡 공부한 내용 정리하기

### 1. 객체 전달을 통한 속성값 변경
- **객체에 의한 호출**: 메서드에 단순 기본 자료형 값을 넘기면 외부 값에 영향을 주지 않지만, 객체 자체를 전달하면 메서드 내부에서 해당 객체의 속성을 변경했을 때 수행 후에도 변경된 값이 유지됨

---

### 2. 상속과 메서드 재정의
- **상속**: 부모 클래스의 변수와 메서드를 그대로 물려받아 자식 클래스에서 기능을 확장함
- **메서드 오버라이딩**: 부모 클래스에 정의된 메서드를 자식 클래스에서 동일한 이름과 형태로 재정의하여 커스텀 동작을 수행함
- **메서드 오버로딩**: 동일한 클래스 내에서 이름은 같지만 매개변수의 개수나 타입을 다르게 하여 여러 개 선언하는 기법

---

### 3. 생성자와 생성자 오버로딩
- **생성자**: 클래스명과 이름이 같고 리턴 타입이 없는 메서드로, 객체가 생성되는 시점에 필요한 초기 설정을 담당함
- **생성자 오버로딩**: 매개변수를 다르게 하여 여러 형태의 생성자를 정의하고, 객체 생성 시점에 다양한 방식으로 초기값을 지정할 수 있음

---

### 4. 인터페이스의 역할과 기능
- **규격 정의**: 클래스들이 따라야 하는 공통 규칙이나 메서드 이름을 정의함
- **다형성 구현**: ZooKeeper 클래스가 개별 동물 클래스를 몰라도 인터페이스 하나만으로 다양한 동물 객체들을 통합하여 처리함
- **디폴트 및 스태틱 메서드**: 인터페이스 내부에서 공통 로직을 기본적으로 제공하는 디폴트 메서드와 객체 생성 없이 바로 호출 가능한 스태틱 메서드를 활용할 수 있음

---

## 💻 자바 실습 코드 분석

<details>
<summary><b>🔍 실습 코드 펼치기 / 접기</b></summary>

```java
class Dog extends Animal { // Animal 클래스를 상속한다
    void sleep() {
        System.out.println(this.name + " zzz");
    }
}

class HouseDog extends Dog {
    void sleep() {
        System.out.println(this.name + " zzz in house");
    }

    void sleep(int hour) {
        System.out.println(this.name + " zzz in house for " + hour + " hours");
    }
}

class HouseDog_5_6 extends Dog {
    void sleep() {
        System.out.println(this.name + " zzz in house");
    }

    void sleep(int hour) {
        System.out.println(this.name + " zzz in house for " + hour + " hours");
    }

    HouseDog_5_6(String name) {
        this.setName(name);
    }
    HouseDog_5_6(int type) {
        if (type == 1) {
            this.setName("yorkshire");
        } else if (type == 2) {
            this.setName("bulldog");
        }
    }

}

interface Predator{
    String getFood();

    default void printFood() { // 디폴트 메서드
        System.out.println("my food is %s\n", getFood());
    }
    // 스태틱 메서드
    int LEG_COUNT = 4;

    static int speed {
        return LEG_COUNT*30;
    }
}

class Tiger extends Animal implements Predator{
    public String getFood() {
        return "apple";
    }
}

class Lion extends Animal implements Predator{
    public String getFood() {
        return "banana";
    }
}

class ZooKeeper {
    void feed(Predator predator) { // 호랑이가 오면 사과를 던져 준다
        System.out.println("feed " + predator.getFood());
    }
}
static void part05_4(){
    // 값에 의한 호출과 객체에 의한 호출

    // 아래 코드를 수정해보자.
    /*
    class Updater {
        void update(int count) {
            count++;
        }
    }

    class Counter {
        int count = 0;
    }

    Counter myCounter = new Counter();
    System.out.println("before update: " + myCounter.count); // before update: 0 출력
    Updater myUpdater = new Updater();
    myUpdater.update(myCounter.count);
    System.out.println("after update" + myCounter.count); // after update: 0 출력
    */

    Counter myCounter = new Counter();
    System.out.println("before update: " + myCounter.count); // before update: 0 출력
    Updater myUpdater = new Updater();
    myUpdater.update(myCounter);
    System.out.println("after update: " + myCounter.count); // after update: 1 출력

    /*
    이전에는 int count와 같이 값을 전달받았다면
    위 코드는 Counter counter와 같이 객체를 전달받도록 변경함
    -> 메서드의 입력으로 객체를 전달하면 메서드가 입력받은 객체를 그대로 사용하기 때문에
    메서드가 객체의 속상값을 변경하면 메서드 수행 후에도 객체의 변경된 속성값이 유지된다.
    */

}

static void part05_5() {
    // 생성자
    Dog dog = new Dog();
    dog.setName("poppy");
    System.out.println(dog.name); // poppy 출력

    // 자식 클래스의 기능 확장하기
    dog.sleep(); // poppy zzz 출력

    // 메서드 오버라이딩: 부모 클래스의 메서드를 자식 클래스가 동일한 형태로 또다시 구현하는 행위
    HouseDog houseDog = new HouseDog();
    houseDog.setName("happy");
    houseDog.sleep(); // happy zzz 출력
    // HouseDog class 수정(클래스 내 sleep 함수 작성) 후
    // happy zzz in house 출력

    // 메서드 오버로딩: 한 클래스 내에서 동일한 이름의 메서드를 또 생성할 수 있다. 다만 메서드의 입력 항목이 다를 경우에만 가능
    houseDog.sleep(3); // happy zzz in house for 3 hours 출력

    // 다중 상속: 자바는 다중 상속을 지원하지 않는다
}

static void part05_6() {
    // 생성자: 메서드명이 클래스명과 동일하고 리턴 자료형을 정의하지 않는 메서드
    HouseDog dog = new HouseDog();
    System.out.println(dog.name); //  null 출력: name 변수에 아무런 값도 설정하지 않았기 때문

    HouseDog_5_6 dog_5_6 = new HouseDog_5_6("happy");
    System.out.println(dog_5_6.name); // happy 출력

    // 디폴트 생성자: 생성자의 입력 항목이 없고 생성자 내부에 아무 내용이 없는 생성자

    // 생성자 오버로딩: 입력 항목이 다른 생성자를 여러 개 만들 수 있다
    HouseDog_5_6 yorkshire = new HouseDog_5_6(1);
    System.out.println(yorkshire.name); // yorkshire 출력
}

static void part05_7() {
    // 인터페이스
    ZooKeeper zooKeeper = new ZooKeeper();
    Tiger tiger = new Tiger();
    Lion lion = new Lion();
    zooKeeper.feed(tiger); // feed apple 출력
    zooKeeper.feed(lion); // feed banana 출력
}
```

</details>

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
자바 객체지향의 핵심 내용이라 생각하는 상속, 생성자, 그리고 인터페이스 개념을 학습했다. 클래스를 상속받아 기존 기능을 오버라이딩과 오버로딩으로 확장하는 방법을 공부하고, 객체 생성 시점에 안전하게 초기화를 수행하는 생성자 활용법을 실습했다. 특히 인터페이스를 활용해 다양한 동물 객체들을 하나의 타입으로 통합 제어하는 코드를 작성하면서 이해도가 높아졌다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 추상 클래스와 인터페이스의 차이점을 파악하고 간단한 추상 클래스 구조 구현해 보기
- **내일의 학습 계획**: 05장 객체 지향 프로그래밍 05_8 ~ 05_9(다형성, 추상 클래스), 되새김 문제 학습

---
#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
