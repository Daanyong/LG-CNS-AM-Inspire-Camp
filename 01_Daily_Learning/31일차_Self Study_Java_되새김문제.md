# [LG CNS 7기] 5주차 Day3 TIL
자바 3장 연습문제 풀이 종합 및 실습

## 📌 오늘의 학습 키워드
- `산술 연산과 삼항 연산자`
- `문자열 추출 및 치환`
- `리스트 역순 정렬 및 문자열 결합`
- `맵 데이터 삭제`
- `중복 제거`
- `열거형을 활용한 매직 넘버 개선`

---

## 💡 공부한 내용 정리하기

### 1. 기본 연산 및 문자열 다루기
- **평균 및 홀짝 판별**: 산술 연산으로 평균값을 구하고, 나머지 연산자와 삼항 연산자를 활용해 조건문을 간결하게 한 줄로 작성함
- **문자열 가공**: `substring`과 `charAt`을 사용해 원하는 위치의 문자열이나 문자를 추출하고, `replaceAll`을 활용해 특정 문자를 변경함

---

### 2. 리스트와 컬렉션 응용
- **정렬 및 결합**: `sort`와 `Comparator.reverseOrder()`를 사용해 리스트를 내림차순으로 정렬하고, `String.join`으로 리스트 요소를 공백으로 연결함
- **맵 항목 삭제**: `remove` 메서드로 특정 키의 항목을 삭제함 동시에 삭제된 값을 확인하는 방법을 다룸
- **중복 제거**: 중복을 허용하지 않는 `HashSet`의 특징을 활용해 리스트의 중복 요소를 제거함

---

### 3. 열거형을 통한 코드 개선
- **매직 넘버 제거**: 숫자로 임의 지정되어 의미를 알기 힘들던 형태를 관련 항목끼리 묶은 열거형으로 대체하여, 코드의 가독성을 높이고 잘못된 값이 들어오는 오류를 방지함

---

## 💻 자바 실습 코드 분석

<details>
<summary><b>🔍 실습 코드 펼치기 / 접기</b></summary>

```java
public static void part03_review () {
    // Q1 평균 점수 구하기 1
    int a = 80;
    int b = 75;
    int c = 55;
    System.out.println((a + b + c) / 3); // 70 출력

    // Q2 홀수/짝수 판별하기 1
    int n = 13;
    if ((n % 2) != 0) System.out.println("홀수");
    else System.out.println("짝수");

    System.out.println((n % 2 != 0) ? "홀수" : "짝수");

    // Q3 주민등록번호 나누기
    {
        String pin = "881120-1068234";
        String yyyyMMdd = pin.substring(0, 6);
        String num = pin.substring(7);
        System.out.println(yyyyMMdd);
        System.out.println(num);

    /* My code
    String[] result = pin.split("-");
    System.out.println(result[0]);
    System.out.println(result[1]);

    System.out.print(result[0] + "\n" + result[1]);

    System.out.println();

    for (int i = 0; i < result.length; i++) {
        System.out.println(result[i]);
    }
    */
    }

    // Q4 원하는 숫자 추출하기
    String pin_4 = "881120-1068234";
    System.out.println(pin_4.charAt(7));

    // Q5 문자열 바꾸기
    String a_5 = "a:b:c:d";
    String b_5 = a_5.replaceAll(":", "#");
    System.out.println(b_5);

    // Q6 리스트를 역순으로 정렬하기
    ArrayList<Integer> AL_6 = new ArrayList<>(Arrays.asList(1, 3, 5, 4, 2));
    AL_6.sort(Comparator.reverseOrder());
    System.out.println(AL_6);

    // Q7 리스트를 문자열로 만들기
    ArrayList<String> AL_7 = new ArrayList<>(Arrays.asList("Life", "is", "too", "short"));
    String result_7 = String.join(" ", AL_7);
    System.out.println(result_7);

    // Q8 맵에서 값 삭제하기
    HashMap<String, Integer> grade = new HashMap<>();
    grade.put("A", 90);
    grade.put("B", 80);
    grade.put("C", 70);
    int result_8 = grade.remove("B");
    System.out.println(result_8);
    System.out.println(grade);

    // Q9 중복 숫자 제거하기
    ArrayList<Integer> AL_9 = new ArrayList<>(Arrays.asList(1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 5));
    HashSet<Integer> temp = new HashSet<>(AL_9);
    ArrayList<Integer> result_9 = new ArrayList<>(temp);
    System.out.println(result_9); // [1, 2, 3, 4 ,5] 출력

    // Q10 매직 넘버 제거하기

    /* 아래 코드 수정

    public void printCoffeePrice(int type) {
    HashMap<Integer, Integer> priceMap = new HashMap<>();
    priceMap.put(1, 3000);
    priceMap.put(2, 4000);
    priceMap.put(3, 5000);
    int price = priceMap.get(type);
    System.out.println(String.format("가격은 %d원입니다.", price));
    }

    printCoffeePrice(1);
    printCoffeePrice(99);
    */

    enum CoffeeType {
        AMERICANO,
        ICE_AMERICANO,
        CAFE_LATTE
    }
    ;

    class CoffeeService {
        void printCoffeePrice(CoffeeType type) {
            HashMap<CoffeeType, Integer> priceMap = new HashMap<>();
            priceMap.put(CoffeeType.AMERICANO, 3000);
            priceMap.put(CoffeeType.ICE_AMERICANO, 4000);
            priceMap.put(CoffeeType.CAFE_LATTE, 5000);
            int price = priceMap.get(type);
            System.out.println(String.format("가격은 %d원입니다.", price));
        }
    }

    CoffeeService service = new CoffeeService();
    service.printCoffeePrice(CoffeeType.AMERICANO);
```

</details>

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
자바 3장에서 학습한 자료형과 연산자 개념들을 종합적인 연습문제를 통해 복습하였다. 단순 문법 암기를 넘어 문자열 자르기, 리스트 역순 정렬, 중복 제거, 열거형을 통한 코드 개선까지 직접 풀이해 보면서 핵심 개념들이 실제 코드에서 어떻게 연결되는지 이해할 수 있었다. 

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 오늘 다룬 중복 제거 방식처럼, 문자열 배열에서 중복을 없애고 다시 리스트로 변환하는 짧은 예제 직접 작성해 보기
- **내일의 학습 계획**: 04장 제어문(if문, switch문, while문, for문) 학습 진행하기

---
#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
