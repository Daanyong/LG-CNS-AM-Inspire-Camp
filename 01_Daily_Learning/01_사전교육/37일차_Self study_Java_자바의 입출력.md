# [LG CNS 7기] 6주차 Day2 TIL
자바 콘솔/파일 입출력과 6장 연습문제 실습

## 📌 오늘의 학습 키워드
- `콘솔 입출력 스트림`
- `파일 생성 및 내용 추가`
- `파일 읽기와 라인 단위 처리`
- `Scanner와 버퍼 관리`
- `Trouble Shooting: 콘솔 입력 시 엔터 개행 문자 버퍼 잔여 문제 해결`

---

## 💡 공부한 내용 정리하기

### 1. 콘솔 입출력 스트림
- **기본 스트림**: `InputStream`을 통해 1바이트 또는 바이트 배열 단위로 콘솔 입력값을 읽어오며, `InputStreamReader`를 거쳐 문자 단위로, `BufferedReader`를 통해 라인 단위로 읽어올 수 있음
- **Scanner 활용**: `Scanner`를 사용하면 한 단어, 한 라인, 혹은 정수 형태의 데이터 추출을 손쉽게 수행할 수 있음

---

### 2. 파일 입출력과 파일 제어
- **파일 쓰기**: `FileOutputStream`, `FileWriter`, `PrintWriter`를 활용해 파일에 내용을 기록함. `FileWriter` 생성 시 두 번째 인자로 참을 전달하면 기존 내용을 유지하며 이어 쓰는 추가 모드로 동작함
- **파일 읽기**: `FileInputStream`으로 바이트 단위로 읽거나, `FileReader`와 `BufferedReader`를 조합하여 라인 단위로 파일 내용을 읽어옴

---

### 3. 연습문제를 통한 실전 응용
- **영문 대문자 변환**: 특정 종료 문자가 들어올 때까지 콘솔 입력을 계속 받아 대문자로 변환해 출력함
- **자원 해제**: 파일에 데이터를 기록한 후 `close`를 호출하여 스트림을 닫아주어야 데이터가 정상적으로 저장됨을 확인함
- **파일 내용 치환**: 파일의 전체 내용을 읽어온 뒤 특정 단어를 다른 단어로 바꾸어 다시 파일에 덮어쓰는 로직을 구현함

---

## 🚨 Trouble Shooting: 콘솔 입력 시 엔터 개행 문자 버퍼 잔여 문제

### 1. 문제 상황
- `InputStream` 또는 `Scanner`의 숫자/토큰 입력 메서드(`nextInt` 등)를 사용한 직후, 다음 문장이나 개행을 읽어들이는 코드가 실행될 때 사용자가 누른 '엔터' 키의 아스키코드 값(개행 문자)이 버퍼에 그대로 남아있는 현상이 발생함
- 이로 인해 다음 입력 단계가 정상적으로 대기하지 않고, 빈 값이 자동으로 입력 처리되어 프로그램이 의도치 않게 다음 로직으로 넘어가는 애를 먹음

### 2. 원인 분석
- 콘솔에서 값을 입력하고 엔터를 누르면 입력값 뒤에 개행 문자가 함께 버퍼로 들어감
- 일부 입력 메서드는 실제 데이터 값만 읽어가고 엔터 개행 문자를 버퍼에 남겨두기 때문에, 이어지는 읽기 메서드가 해당 개행 문자를 새로운 입력으로 인식함

### 3. 해결 방법
- 데이터를 읽어들인 직후 `in.read()` 또는 `sc.nextLine()`을 한 번 실행해 주는 로직을 추가함
- 버퍼에 남아있던 엔터 개행 문자를 사전에 읽어 비워줌으로써, 다음 입력 단계에서 원하는 데이터를 차례대로 정상 받아올 수 있게 처리함

---

## 💻 자바 실습 코드 분석

<details>
<summary><b>🔍 실습 코드 펼치기 / 접기</b></summary>

```java
static void part06_1() throws IOException {
    // 콘솔 입출력
    InputStream in = System.in;
    int a;
    a = in.read(); // InputStream의 read 메서드는 1byte만 읽을 수 있음
    in.read(); // a 입력 후 누르는 엔터키의 아스키코드값이 버퍼에 저장되는 것을 방지 -> trouble shooting
    System.out.println(a); // a 입력 후 엔터 -> 97 출력 (아스키코드값)

    int b;
    int c;
    b = in.read();
    c = in.read();
    in.read(); // b, c 입력 후 누르는 엔터키의 아스키코드값이 버퍼에 저장되는 것을 방지
    System.out.println(b); // b, c 입력 후 엔터 -> 97, 98 (아스키코드값) 출력
    System.out.println(c);

    byte[] d = new byte[3];
    in.read(d);
    in.read(); // d 입력 후 누르는 엔터키의 아스키코드값이 버퍼에 저장되는 것을 방지
    System.out.println(d[0]); // 한 번에 abc 모두 읽고, 각 값의 아스키코드값 출력
    System.out.println(d[1]);
    System.out.println(d[2]);

    InputStreamReader reader = new InputStreamReader(in); // InputStreamReader는 객체를 생성할 때 생성자의 입력으로 InputStream 객체가 필요하다
    char[] e = new char[3];
    reader.read(e); // byte가 아닌 문자로 입력 스트림을 읽음
    reader.read(); // e 입력 후 버퍼에 남아있는 엔터 아스키코드를 버림
    System.out.println(e);

    BufferedReader br = new BufferedReader(reader); // BufferedReader는 객체를 생성할 때 생성자의 입력으로 InputStreamReader의 객체가 필요하다
    String f = br.readLine();
    System.out.println(f);

    Scanner sc = new Scanner(System.in); // Scanner 클래스는 생성자의 입력으로 System.in 객체가 필요하다 -> 콘솔 입력인 InputStream이 필요하다는 의미
    System.out.println(sc.next()); // next 메서드는 한 개의 토큰을 읽어 들인다 (nextLine: 라인을 읽어 들인다, nextInt: 정수를 읽어 들인다)
    sc.close();
}

static void part06_2() throws IOException {
    // 파일 입출력

    FileOutputStream output = new FileOutputStream("c:/Users/PC/Desktop/LG CNS AM Inspire Camp/LG CNS AM Inspire Camp/01_사전학습/04_self_java/out_fo.txt"); // 해당 경로에 txt 파일 생성
    // FileOutputStream 클래스는 객체를 생성할 때 생성자의 입력으로 파일명을 넘겨주어야 한다

    for(int i = 1; i < 11; i++) {
        String data = i+"번째 줄입니다.\r\n";
        output.write(data.getBytes()); // FileOutputStream에 값을 쓸 때는 type 단위로 써야 하므로 String을 byte 배열로 바꾸어 주는 getBytes 메서드 사용
        // 지정 경로의 txt파일에 해당 내용 입력 후 종료
    }
    output.close();

    FileWriter fw = new FileWriter("c:/Users/PC/Desktop/LG CNS AM Inspire Camp/LG CNS AM Inspire Camp/01_사전학습/04_self_java/out_fw.txt");
    for(int i = 1; i < 11; i++) {
        String data = i+"번째 줄입니다.\r\n";
        fw.write(data); // FileWriter를 이용하면 byte 배열 대신 문자열을 사용할 수 있다
        // 지정 경로의 txt파일에 해당 내용 입력 후 종료
    }
    fw.close();

    PrintWriter pw = new PrintWriter("c:/Users/PC/Desktop/LG CNS AM Inspire Camp/LG CNS AM Inspire Camp/01_사전학습/04_self_java/out_pw.txt");
    for(int i = 1; i < 11; i++) {
        String data = i+"번째 줄입니다.";
        pw.println(data); // FileWriter를 이용하면 byte 배열 대신 문자열을 사용할 수 있다
        // 지정 경로의 txt파일에 해당 내용 입력 후 종료
    }
    pw.close();

    // 파일에 내용 추가하기
    FileWriter fw2 = new FileWriter("c:/Users/PC/Desktop/LG CNS AM Inspire Camp/LG CNS AM Inspire Camp/01_사전학습/04_self_java/out_fw.txt", true); // 두 번째 파라미터값이 true이면 파일을 추가 모드로 연다
    for(int i = 11; i < 21; i++) {
        String data = i+"번째 줄입니다.\r\n";
        fw2.write(data); // 지정 경로의 txt파일에 해당 내용 입력 추가 후 종료
    }
    fw2.close();

    FileWriter pw2 = new FileWriter("c:/Users/PC/Desktop/LG CNS AM Inspire Camp/LG CNS AM Inspire Camp/01_사전학습/04_self_java/out_pw.txt", true); // 두 번째 파라미터값이 true이면 파일을 추가 모드로 연다
    for(int i = 11; i < 21; i++) {
        String data = i+"번째 줄입니다.\r\n";
        pw2.write(data); // 지정 경로의 txt파일에 해당 내용 입력 추가 후 종료
    }
    pw2.close();

    // 파일 읽기
    byte[] b = new byte[1024];
    FileInputStream input = new FileInputStream("c:/Users/PC/Desktop/LG CNS AM Inspire Camp/LG CNS AM Inspire Camp/01_사전학습/04_self_java/out_fo.txt");
    input.read(b);
    System.out.println(new String(b));
    input.close();

    BufferedReader br = new BufferedReader(new FileReader("c:/Users/PC/Desktop/LG CNS AM Inspire Camp/LG CNS AM Inspire Camp/01_사전학습/04_self_java/out_fo.txt"));
    // 한 줄 단위로 파일을 읽을 수 있다
    while(true) {
        String line = br.readLine();
        if (line == null) break; // 더 이상 읽을 라인이 없을 경우 while문을 빠져나간다
        System.out.println(line);
    }
    br.close();
}

static void part06_review() throws IOException {
    // 되새김문제
    // Q1 - 입력한 숫자의 합 구하기
    Scanner sc = new Scanner(System.in);
    System.out.print("첫 번째 숫자를 입력하세요: ");
    int first = sc.nextInt();
    System.out.print("두 번째 숫자를 입력하세요: ");
    int second = sc.nextInt();

    int result = first + second;
    System.out.printf("%d + %d = %d입니다.%n", first, second, result);

    sc.nextLine(); // nextInt후 버퍼에 남아있는 엔터 잔여물 비우기

    // Q2 - 입력한 영문을 대문자로 변경하기
    while (true) {
        Scanner sc_2 = new Scanner(System.in);
        System.out.print("영어 문장을 입력하세요: ");
        String line = sc.nextLine();
        if ("END".equals(line)) { break; } // 입력된 문장이 END면 종료
        System.out.println(line.toUpperCase());
    }

    // Q3 - 오류 수정하기
    FileWriter fw = new FileWriter("c:/Users/PC/Desktop/LG CNS AM Inspire Camp/LG CNS AM Inspire Camp/01_사전학습/04_self_java/out_fw_review.txt");
    fw.write("Write once, run anywhere");
    fw.close(); // 추가

    BufferedReader br = new BufferedReader(new FileReader("c:/Users/PC/Desktop/LG CNS AM Inspire Camp/LG CNS AM Inspire Camp/01_사전학습/04_self_java/out_fw_review.txt"));
    String line = br.readLine();
    System.out.println(line);
    br.close(); // 추가

    // Q4 - 사용자의 입력을 파일에 저장하기
    FileWriter fw_4 = new FileWriter("c:/Users/PC/Desktop/LG CNS AM Inspire Camp/LG CNS AM Inspire Camp/01_사전학습/04_self_java/out_fw_rev4.txt", true);
    System.out.print("문장을 입력하세요: ");
    Scanner sc_4 = new Scanner(System.in);
    String line_4 = sc_4.nextLine();
    fw_4.write(String.format("%s\n", line_4));
    fw_4.close();

    // Q5 - 파일 내용 바꾸어 저장하기
    // 'Life is too short \n you need python'이 저장되어 있는 txt파일에서 'python'을 'java'로 바꾸기
    // 파일 읽기

    ArrayList<String> data = new ArrayList<>();
    BufferedReader br_rev5 = new BufferedReader(new FileReader("c:/Users/PC/Desktop/LG CNS AM Inspire Camp/LG CNS AM Inspire Camp/01_사전학습/04_self_java/out_fw_rev5.txt"));
    while (true) {
        String line_rev5 = br_rev5.readLine();
        if (line_rev5 == null) { break; }
        data.add(line_rev5);
    }
    br.close();

    String text = String.join("\n", data);
    text = text.replaceAll("python", "java");

    FileWriter fw_rev5 = new FileWriter("c:/Users/PC/Desktop/LG CNS AM Inspire Camp/LG CNS AM Inspire Camp/01_사전학습/04_self_java/out_fw_rev5.txt");
    fw_rev5.write(text);
    fw_rev5.close();
}
```

</details>

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
자바 입출력 단원과 되새김문제를 실습했다. 콘솔을 통해 사용자의 입력을 받는 다양한 방식과 외부 파일에 데이터를 기록하고 읽어오는 방법을 학습했다. 특히 입력 과정에서 발생했던 문자 버퍼 잔여 문제를 원인 분석부터 해결까지 처리하는게 살짝 어려웠지만, 그 덕에 입출력 스트림의 내부 동작 방식을 제대로 이해할 수 있었다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 오늘 다룬 입출력을 활용해 사용자에게 이름과 나이를 입력받고, 파일에 기록한 뒤 다시 파일에서 읽어와 콘솔에 출력하는 프로그램 만들어보기
- **내일의 학습 계획**: 07장 07-1 ~ 07-4 (패키지, 접근 제어자, 스태틱, 예외 처리) 학습

---
#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
