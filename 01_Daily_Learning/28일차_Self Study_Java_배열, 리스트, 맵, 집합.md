# [LG CNS 7기] 4주차 Day7 TIL
배열, 리스트, 맵, 집합 자료형 활용 실습

## 📌 오늘의 학습 키워드
- `배열 다루기`
- `ArrayList와 제네릭스`
- `String.join과 정렬`
- `HashMap의 활용`
- `HashSet과 집합 연산`

---

## 💡 공부한 내용 정리하기

### 1. 배열과 리스트
- **배열**: 같은 자료형의 데이터들을 순서대로 모아놓은 구조로, 대괄호와 인덱스를 이용해 값에 접근하며 길이를 구할 때는 `length`를 사용함
- **ArrayList**: 크기가 정해져 있지 않고 동적으로 변하는 자료형
  - `add`: 요소 추가 및 특정 위치 삽입
  - `get` / `size` / `contains`: 특정 위치 값 추출, 전체 개수 확인, 특정 항목 포함 여부 확인
  - `remove`: 값이나 위치를 지정하여 해당 항목 삭제
- **제네릭스**: 자료형을 안전하게 사용할 수 있도록 명시해 주는 기능으로, 불필요한 형 변환 과정을 줄여줌
- **문자열 연결 및 정렬**: 반복문을 돌리지 않고도 `String.join`을 사용해 여러 요소를 구분자로 손쉽게 연결할 수 있으며, `sort`로 오름차순 정렬이 가능함

---

### 2. 맵 자료형
- **키와 값의 쌍**: 이름과 값의 대응 관계를 쉽게 표현해 주는 자료형
- **주요 기능**:
  - `put` / `get`: 데이터 추가 및 키에 해당하는 값 조회
  - `containsKey` / `remove`: 키 존재 여부 확인 및 항목 삭제
  - `size` / `keySet`: 저장된 항목 개수 확인 및 모든 키 목록 모아서 출력

---

### 3. 집합 자료형
- **중복 불가 및 순서 없음**: 중복된 값을 허용하지 않고 순서가 유지되지 않는 특징이 있음
- **집합 연산**:
  - `retainAll`: 공통된 요소만 남기는 교집합
  - `addAll`: 모든 요소를 하나로 합치는 합집합
  - `removeAll`: 특정 요소들을 제외하는 차집합
- **요소 제어**: `add`로 값을 하나씩 추가하거나 `addAll`로 여러 개를 동시에 추가할 수 있고, `remove`로 특정 값을 삭제할 수 있음

---

## 💻 자바 실습 코드 분석

<details>
<summary><b>🔍 실습 코드 펼치기 / 접기</b></summary>

```java
public static void part03_6() {
    // 03 - 6 배열

    // 배열값에 접근하기
    String[] weeks = {"월", "화", "수", "목", "금", "토", "일"};
    System.out.println(weeks[3]); // 목 출력

    // 배열의 길이 구하기
    for (int i = 0; i < weeks.length; i++) {
        System.out.println(weeks[i]); // weeks 배열을 순서대로 출력 (월~일)
    }

}

public static void part03_7() {
    // 03-7 리스트

    // ArrayList

    // add: 요소를 추가하는 기능
    ArrayList pitches = new ArrayList();
    pitches.add("138"); // 요소 추가
    pitches.add("129");
    pitches.add("142");
    pitches.add(0, "133"); // 원하는 인덱스 위치에 요소 추가

    // get: 특정 인덱스의 값을 추출할 수 있다
    System.out.println(pitches.get(1)); // 138 출력

    // size: ArrayList의 요소의 개수 리턴
    System.out.println(pitches.size()); // 4 출력

    // contains: 리스트 안에 해당 항목이 있는지 판별하여 그 결과를 boolean으로 리턴
    System.out.println(pitches.contains("142")); // true 출력

    // remove(객체): 리스트에서 객체에 해당하는 항목을 삭제한 뒤, 그 결과로 true 혹은 false 리턴
    System.out.println(pitches.remove("129")); // true 출력

    // remove(인덱스): 인덱스에 해당하는 항목을 삭제한 뒤, 그 항목을 리턴
    System.out.println(pitches.remove(0)); // 133 출력

    // 제네릭스: 자료형을 안전하게 사용할 수 있도록 만들어 주는 기능
    // 아래는 제네릭스를 사용하지 않은 예
    /*
    ArrayList pitches = new ArrayList();
    pitches.add("138"); // 요소 추가
    pitches.add("129");

    String one = (String) pitches.get(0); // Object 자료형에서 String 자료형으로 형 변환하는 과정
    String two = (String) pitches.get(1);

    // 제네릭스 적용
    // ArrayList<String> pitches = new ArrayList<>();
    pitches.add("138"); // 요소 추가
    pitches.add("129");

    String one = pitches.get(0); // 형 변환이 필요 없다.
    String two = pitches.get(0);
    */

    // 다양한 방법으로 ArrayList 만들기
    ArrayList<String> AL1 = new ArrayList<>();
    AL1.add("138");
    AL1.add("129");
    AL1.add("142");
    System.out.println(AL1); // [138, 129, 142] 출력

    String[] data = {"138", "129", "142"}; // 이미 배열이 있다
    ArrayList<String> AL2 = new ArrayList<>(Arrays.asList(data));
    System.out.println(AL2); // [138, 129, 142] 출력

    ArrayList<String> AL3 = new ArrayList<>(Arrays.asList("138", "129", "142"));
    System.out.println(AL3); // [138, 129, 142] 출력

    // String.join
    ArrayList<String> AL4 = new ArrayList<>(Arrays.asList("138", "129", "142"));
    String result = "";
    for (int i = 0; i < AL4.size(); i++) {
        result += AL4.get(i);
        result += ",";
    }
    result = result.substring(0, result.length() - 1);
    System.out.println(result); // 138,129,142 출력

    // String.join 적용
    ArrayList<String> AL5 = new ArrayList<>(Arrays.asList("138", "129", "142"));
    String result2 = String.join(",", AL5);
    System.out.println(result2); // 138,129,142 출력

    // String.join 문자열 배열에 적용
    String[] AL6 = new String[]{"138", "129", "142"};
    String result3 = String.join(",", AL6);
    System.out.println(result3); // 138,129,142 출력

    // 리스트 정렬하기
    ArrayList<String> AL7 = new ArrayList<>(Arrays.asList("138", "129", "142"));
    AL7.sort(Comparator.naturalOrder()); // 오름차순으로 정렬 (내림차순: Comparator.reverseOrder()
    System.out.println(AL7); // [129, 138, 142] 출력
}

public static void part03_8() {
    // 03-8 맵: 대응 관계를 쉽게 표현할 수 있게 해주는 자료형

    // HashMap
    // put: key와 value 추가
    HashMap<String, String> map = new HashMap<>();
    map.put("people", "사람");
    map.put("baseball", "야구");

    // get: key에 해당하는 value를 얻을 때 사용
    System.out.println(map.get("people")); // 사람 출력

    // containsKey: 맵에 해당 key가 있는지를 참 또는 거짓으로 리턴
    System.out.println(map.containsKey("people")); // true 출력

-     // remove: 맵의 항목을 삭제하는 메서드
해당 key의 항목을 삭제한 후 value 값을 리턴
    System.out.println(map.remove("people")); // 사람 출력

    // size: 맵 요소의 개수를 리턴
    System.out.println(map.size()); // 1 출력

    // keySet: 맵의 모든 key를 ㅁ와서 리턴
    map.put("people", "사람");
    System.out.println(map.keySet()); // [baseball, people] 출력
}

public static void part03_9() {
    // 집합

    // HashSet
    HashSet<String> set = new HashSet<>(Arrays.asList("H", "e", "l", "l", "o"));
    System.out.println(set); // [e, H, l, o] 출력

    // 교집합, 합집합, 차집합 구하기
    HashSet<Integer> s1 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6));
    HashSet<Integer> s2 = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8, 9));

    // 교집합 구하기
    HashSet<Integer> intersection = new HashSet<>(s1); // s1으로 intersection 생성
    intersection.retainAll(s2); // 교집합 수행
    System.out.println(intersection); // [4, 5, 6] 출력

    // 합집합 구하기
    HashSet<Integer> union = new HashSet<>(s1); // s1으로 intersection 생성
    union.addAll(s2); // 교집합 수행
    System.out.println(union); // [1, 2, 3, 4, 5, 6, 7, 8, 9] 출력

    // 차집합 구하기
    HashSet<Integer> substract = new HashSet<>(s1); // s1으로 intersection 생성
    substract.removeAll(s2); // 교집합 수행
    System.out.println(substract); // [1, 2, 3] 출력

-     // 집합 자료형과 관련된 메서드add, addAll, remove

    // add: 집합 자료형에 값을 추가할 때 사용
    HashSet<String> s3 = new HashSet<>();
    s3.add("Jump");
    s3.add("To");
    s3.add("Java");
    System.out.println(s3); //[Java, To, Jump] 출력

    // addAll: 값을 한꺼번에 여러 개 추가
    HashSet<String> s4 = new HashSet<>();
    s4.add("Jump");
    s4.addAll(Arrays.asList("To", "Java"));
    System.out.println(s4); // [Java, To, Jump] 출력

    // remove: 특정 값을 제거할 때 사용
    HashSet<String> s5 = new HashSet<>(Arrays.asList("Jump", "To", "Java"));
    s5.remove("To");
    System.out.println(s5); // [Java, Jump] 출력
}
```

</details>

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
자바의 자료구조인 리스트, 맵, 집합을 실습했다. 각 자료형이 가진 특징과 상황에 맞는 메서드 활용법을 학습했다. 실무 및 백엔드 개발 시 다량의 데이터를 효율적으로 정렬하고 추출할 경우 활용할 수 있을 것 같다는 생각을 했다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 오늘 다룬 맵 자료형을 사용해 학생 이름과 점수를 저장하고 특정 학생의 점수를 검색해 출력하는 예제 타이핑해 보기
- **내일의 학습 계획**: 03장 자바의 기초, 자료형 03-10 ~ 03-11 (상수 집합, 자료형 변환, final 등), 되새김 문제 학습

---
#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
