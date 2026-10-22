# [LG CNS 7기] 6주차 Day4 TIL
자바 스레드, join, 람다식 및 스트림 API 정리

## 📌 오늘의 학습 키워드
- `스레드 생성과 병렬 처리`
- `Thread.join을 활용한 동기화`
- `람다식과 표준 함수형 인터페이스`
- `스트림 API를 활용한 데이터 가공`

---

## 💡 공부한 내용 정리하기

### 1. 스레드와 동기화 처리
- 스레드 생성: 하나의 프로세스 안에서 여러 작업을 동시에 실행할 수 있는 단위로, `Thread` 클래스를 상속받거나 `Runnable` 인터페이스를 구현해 사용할 수 있음
- 순서 보장 불가: 스레드를 실행시키면 각 스레드는 순서에 상관없이 병렬로 작동함
- `join` 메서드: 스레드의 작업이 모두 완료될 때까지 메인 흐름이 기다리도록 제어하여 순서를 동기화함

---

### 2. 람다식과 함수형 인터페이스
- 람다식: 메서드를 하나의 간결한 식 형태로 표현한 것으로, 익명 함수를 생성하여 코드 양을 크게 줄여줌
- 제약 조건: 람다식을 적용하려는 인터페이스는 추상 메서드가 단 1개만 존재하는 단일 메서드 인터페이스여야 함
- 표준 함수형 인터페이스: 자바에서 기본 제공하는 `BiFunction`이나 `BinaryOperator` 등을 활용하면 별도의 인터페이스 정의 없이 람다식을 바로 적용할 수 있음

---

### 3. 스트림 API를 활용한 데이터 가공
- 기존 방식과의 차이: 복잡한 조건문, 반복문, 중복 제거 및 정렬 로직을 스트림을 활용하면 가독성 높게 한 줄로 연결하여 처리할 수 있음
- 주요 파이프라인 단계:
  - `boxed()`: 기본형 스트림을 객체형 스트림으로 변환
  - `filter()`: 조건에 맞는 요소만 추출 (예: 짝수 판별)
  - `distinct()`: 중복 요소 제거
  - `sorted()`: 요소 정렬 (예: 역순 정렬)
  - `mapToInt()` 및 `toArray()`: 최종 연산을 거쳐 다시 배열 형태로 변환

---

## 💻 자바 실습 코드 분석

<details>
<summary><b>🔍 실습 코드 펼치기 / 접기</b></summary>

java
static void part07_5() {
    // 스레드: 한 프로세스 내에서 두 가지 또는 그 이상의 일을 동시에 할 수 있
    // Sample sample = new Sampl
    // sample.start(); // start()로 스레드를 실행한

for (int i = 0; i < 10; i++) { // 총 10개의 스레드를 생성하여 실행한다
    Thread t = new Sample
    t.star
}
System.out.println("main end."); // main 메서드 종료
// 0번 스레드부터 9번 스레드까지 순서에 상관없이 실행

// Join: 모든 스레드가 종료된 후에 main 메서드를 종료하고 싶은 경우 사용
ArrayList<Thread> threads = new ArrayList<>();
for (int i = 0; i < 10; i++) {
    // Thread t = new Sample
    Thread t = new Thread(new Sample(i)); // 인터페이스를 사용한 경우 다른 클래스 상속이 가능하므로 좀 더 유연한 프로그램으로 만들 수 
    t.star
    threads.add
}

for (int i = 0; i < threads.size(); i++) {
    Thread t = threads.get
    t
        t.join(); // t 스레드가 종료할 때까지 기
    } catch (Exception 
 
}
System.out.println("main end");

}

static void part07_6() {
    // 함수형 프로그

// 람다를 적용한 코드
// Calculator_07 mc = (int a, int b) -> a + b;
Calculator_07 mc = (a, b) -> a + b; // 람다 축약하기
int result = mc.sum(3, 4);
System.out.println(result); // 7 출력
// 인터페이스의 메서드가 1개 이상이면 람다 함수를 사용할 수 없다.

//람다 함수 인터페이스
BiFunction<Integer, Integer Integer,> mc_2 = (a, b) -> a + b;
int result_2 = mc_2.apply(3, 4); // sum이 아닌 apply 메서드를 사용
System.out.println(result_2); // 7 출력

BinaryOperator<Integer> mc_3 = (a, b) -> a + b;
int result_3 = mc_2.apply(3, 4); // sum이 아닌 apply 메서드를 사용
System.out.println(result_3); // 7 출력

// 스트림
int[] data = {5, 6, 4, 2, 3, 1, 1, 2, 2, 4, 8};

/* 아래 코드를 스트림으로 수정
// 짝수만 포함하는 ArrayList 생성
ArrayList<Integer> dataList = new ArrayList<>();
for (int i = 0; i < data.length; i++) {
    if(data[i] % 2 == 0) { dataList.add(data[i]
}

// Set을 사용하여 중복을 제거
HashSet<Integer> dataSet = new HashSet<>(dataList);
// Set을 다시 List로 변경
ArrayList<Integer> distinctList = new ArrayList<>(dataSet);
// 역순으로 정렬
distinctList.sort(Comparator.reverseOrder());

// Integer 리스트를 정수 배열로 변환
int[] result_4 = new int[distinctList.size()];
for (int i = 0; i < distinctList.size(); i++) {
    result_4[i] = distinctList.get
}
*/

int[] result_4 = Arrays.stream(data) // IntStream을 생성한다
        .boxed() // IntStream을 Stream<Integer>로 변
        .filter((a) -> a % 2 == 0) // 짝수만 뽑
        .distinct()// 중복을 제
        .sorted(Comparator.reverseOrder()) // 역순으로 정
        .mapToInt(Integer::intValue) // Stream<Integer>를 IntStream으로 변
        .toArray() // int[] 배열로 반
 
}



</details>

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
자바의 스레드 제어, 람다식, 스트림에 대해 학습하였다. 다중 스레드의 순서를 `join`을 이용해 제어하는 방법을 알게 되었고, 길고 복잡했던 반복문/조건문 필터링 코드가 스트림 활용으로 깔끔하게 정돈되는 과정을 직접 확인하였다. 드디어 내일부터는 본 교육 시작이다.

### 2. 내일을 위한 다짐 & 계획
- 개념 체화: 오늘 다룬 스트림 API를 활용해 홀수만 필터링한 뒤 오름차순으로 정렬해 새로운 배열로 만드는 예제 타이핑해 보기
- 내일의 학습 계획: 본 교육 시작 !!!!!
