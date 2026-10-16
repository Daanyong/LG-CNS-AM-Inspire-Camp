# [LG CNS 7기] 5주차 Day5 TIL
자바 제어문 종합 및 4장 되새김문제 실습

## 📌 오늘의 학습 키워드
- `switch문 조건 분기`
- `while문과 루프 제어`
- `for문과 이중 루프`
- `for-each문 순회`
- `제어문 활용 연습문제 풀이`

---

## 💡 공부한 내용 정리하기

### 1. 조건문과 반복문의 기본 제어
- **switch문**: 입력된 값에 따라 해당하는 구역으로 바로 이동해 코드를 실행하는 방식
- **while문**: 조건이 참인 동안 코드를 계속 반복하며, `break`로 루프를 즉시 탈출하거나 `continue`로 다음 반복 단계로 건너뛸 수 있음
- **for문과 이중 루프**: 반복 횟수가 정해져 있을 때 유용하며, 구구단처럼 루프 안에 루프를 집어넣는 구조를 만들어 다양한 반복 로직을 처리함
- **for-each문**: 배열이나 리스트의 모든 요소를 별도의 인덱스 계산 없이 하나씩 순제어하며 깔끔하게 꺼내 쓸 수 있음

---

### 2. 연습문제를 통한 실전 응용
- **다중 조건 판단**: 여러 조건문이 얽혀 있을 때 상위 조건의 성립 여부에 따라 아래 조건문들의 실행 여부가 결정되는 흐름을 파악함
- **누적합 및 별 찍기**: 반복문 안에서 조건식을 활용해 특정 배수만 누적하여 더하거나, 루프 변수의 크기에 맞춰 특수문자를 출력하는 로직을 다룸
- **배열 평균 계산**: `for-each`문으로 배열 내부 전체 값을 모두 더한 뒤, 배열의 길이로 나누어 평균값을 계산함

---

## 💻 자바 실습 코드 분석

<details>
<summary><b>🔍 실습 코드 펼치기 / 접기</b></summary>

```java
static void part04_2(){
    // switch / case 문
    int month = 8;
    String monthString = "";
    switch (month) {
        case 1: monthString = "January";
            break;
        case 2: monthString = "February";
            break;
        case 3: monthString = "March";
            break;
        case 4: monthString = "April";
            break;
        case 5: monthString = "May";
            break;
        case 6: monthString = "June";
            break;
        case 7: monthString = "July";
            break;
        case 8: monthString = "August";
            break;
        case 9: monthString = "September";
            break;
        case 10: monthString = "October";
            break;
        case 11: monthString = "November";
            break;
        case 12: monthString = "December";
            break;
        default: monthString = "Invalid month";
            break;
    }
    System.out.println(monthString);
}

static void part04_3() {
    // while문

    int treeHit = 0;
    while (treeHit < 10) {
        treeHit++;
        System.out.println("나무를 " + treeHit + "번 찍었습니다.");
        if (treeHit == 10) {
            System.out.println("나무 넘어갑니다~");
        }
    }

    // 무한 루프
    /*
    while (true) {
        System.out.println("Ctrl-c를 눌러야 while문을 빠져나갈 수 있습니다.");
    }
    */

-     // while 문 빠져나가기break

    int coffee = 10;
    int money = 300;

    while (money > 0) {
        System.out.println("돈을 받았으니 커피를 줍니다.");
        coffee--;
        System.out.println("남은 커피의 양은 " + coffee + "입니다.");
        if (coffee == 0) {
            System.out.println("커피가 다 떨어졌습니다. 판매를 중지합니다.");
            break;
        }
    }

-     // while문으로 돌아가기continue

    int a = 0;
    while (a < 10) {
        a++;
        if (a % 2 == 0) {
            continue;
        }
        System.out.println(a);
    }
}

static void part04_4() {
    // for문

    String[] numbers = {"one", "two", "three"};
    for(int i = 0; i < numbers.length; i++) {
        System.out.println(numbers[i]);
    }

    int[] marks = {90, 25, 67, 45, 80};
    for(int i = 0; i < marks.length; i++) {
        if (marks[i] >= 60) {
            System.out.println((i+1)+"번 학생은 합격입니다.");
        } else {
            System.out.println((i+1)+"번 학생은 불합격입니다.");
        }
    }

-     // for문으로 돌아가기continue

    for(int i = 0; i < marks.length; i++) {
        if (marks[i] < 60) {
            continue;
        }
        System.out.println((i+1)+"번 학생 합격입니다.");
    }

    // 이중 for문
    for(int i = 2; i < 10; i++) {
        for(int j = 1; j < 10; j++) {
            System.out.print(i*j+" ");
        }
        System.out.println("");
    }
}

static void part04_5() {
    // for each문

    String[] numbers = {"one", "two", "three"};
    for(int i = 0; i < numbers.length; i++) {
        System.out.println(numbers[i]);
    }
    // 위 코드를 for each문으로 수정
    for(String number: numbers) {
        System.out.println(number);
    }

    ArrayList<String> numbers_AL = new ArrayList<>(Arrays.asList("one", "two", "three"));
    for (String number: numbers_AL) {
        System.out.println(number);
    }

}

static void part04_review() {

    // Q1 - 조건문의 참과 거짓 판단하기 (다음 코드 출력 결과 예상하기)
    String a = "write once, run anywhere";
    if (a.contains("wife")) { // false -> 다음으로 넘어감
        System.out.println("wife");
    } else if (a.contains("once") && !a.contains("run")) { // 1번 조건 참, 2번 조건 거짓 -> &&(and) 조건 불충족 -> 다음으로 넘어감
        System.out.println("once");
    } else if (!a.contains("everywhere")) { // 조건 충족 -> everywhere 출력 -> 이후 종료
        System.out.println("everywhere");
    } else if (a.contains("anywhere")) { // 앞서 충족된 조건이 있으므로 참/거짓 여부에 상관없이 실행되지 않음
        System.out.println("anywhere");
    } else { // 앞서 충족된 조건이 있으므로 참/거짓 여부에 상관없이 실행되지 않음
        System.out.println("none");
    }

    // Q2 - 3의 배수의 합 구하기
    int result = 0;
    int i = 0;
    while(i <= 1000) {
        if(i % 3 == 0) { // 3의 배수 == 3으로 나누어 떨어지는 수
            result += i;
        }
        i += 1;
    }
    System.out.println(result); // 166833 출력


    // Q3 - 별 표시하기
    int i_3 = 0;
    while (true) {
        i_3 += 1;
        if (i_3 > 5) {
            break;
        }
        for (int j_3 = 0; j_3 < i_3; j_3++) {
            System.out.print('*');
        }
        System.out.println("");
    }

    // Q4 - for문을 사용하여 1부터 100까지 출력하기
    for(int i_4 = 0; i_4 < 101; i_4++) { System.out.println(i_4); }

    // Q5 - 평균 점수 구하기 2
    // 10명의 학생의 평균 점수를 for each문을 사용하여 구하여라
    int[] marks = {70, 60, 55, 75, 95, 90, 80, 80, 85, 100};
    int total = 0;
    for (int mark: marks) { total += mark; }
    float average = (float) total / marks.length;
    System.out.println(average); // 79.0 출력
}
```

</details>

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
자바의 제어문 전체를 다루고 되새김문제를 풀이했다. 기본 조건문부터 반복문과 루프 제어, 그리고 컬렉션 순회에 최적화된 구문까지 한 번에 복습하였다. 다양하게 활용되는 제어 구조를 자바 코드로 작성해 보면서 자바 프로그래밍에 조금 더 익숙해졌다. 원래 익숙하던 제어문도 자바 문법으로 다시 익히려니 헷갈리는 부분이 많아서 주의해야 할 것 같다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 오늘 다룬 `for-each` 구문을 활용하여 짝수 숫자의 합만 계산해 내는 코드 구현해 보기
- **내일의 학습 계획**: 05장 객체 지향 프로그래밍 05-1 ~ 05-3 (객체 지향 프로그래밍, 클래스, 객체, 객체 변수, 메서드, 매개 변수, 인수 등) 학습

---
#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
