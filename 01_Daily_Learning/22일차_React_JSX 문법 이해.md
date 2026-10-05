# [LG CNS 7기] 4주차 Day1 TIL
React 개발 환경 구축(CRA), 프로젝트 디렉토리 구조 및 Virtual DOM 동작 원리

## 📌 오늘의 학습 키워드
- `Node.js & npm (Node Package Manager)`
- `Create React App (npx create-react-app)`
- `npm start (개발 서버 구동)`
- `React Project Directory Structure`
- `Virtual DOM & index.js / index.html Rendering Mechanism`

---

## 💡 공부한 내용 정리하기

### 1. React 개발 환경 설정 및 프로젝트 생성
- **Node.js & npm**: 자바스크립트 실행 환경인 Node.js와 패키지 관리 도구인 npm 버전을 터미널에서 확인 (`node -v`, `npm -v`)
- **`npx create-react-app 프로젝트명`**:
  - React 앱에 필요한 기본 보일러플레이트(폴더 구조, 패키지 설치, 빌드 세팅 등)를 자동으로 생성해 주는 공식 CLI 도구
  - 명령어를 실행하여 `react_pjt` 폴더 생성 완료
- **`npm start`**:
  - 생성된 프로젝트 디렉토리로 이동하여 개발용 로컬 서버를 기동하는 명령어 (`http://localhost:3000`)

---

### 2. React 프로젝트 디렉토리 구조 및 구동 원리
- **`public/index.html`**:
  - 브라우저에 표시되는 유일한 HTML 파일
  - 내부를 열어보면 태그가 거의 없는 상태이지만, React가 실행되면서 화면이 채워짐
- **`App.js`**:
  - 하나의 독립된 **컴포넌트** 역할
  - 컴포넌트 내부에서 출력되는 **엘리먼트** 가 HTML로 변환되어 브라우저에 렌더링되며, 재사용성이 높음
- **`index.js` (연결 고리)**:
  - `index.html`과 `App.js` 컴포넌트를 중간에서 이어주는 핵심 파일
  - React가 관리하는 **Virtual DOM** 에 실제 필요한 컴포넌트들을 등록하고, `index.html` 내의 `#root` 엘리먼트에 동적으로 그려줌

---

## 💻 터미널 명령 및 실습 진행 과정

```bash
# 1. Node.js 및 npm 버전 확인
$ node -v
# -> v21.7.2
$ npm -v
# -> 10.5.1

# 2. Create React App을 활용한 신규 리액트 프로젝트 생성
$ npx create-react-app react_pjt
# -> react_pjt 폴더 생성 및 필요한 패키지 자동 설치 완료

# 3. 생성된 프로젝트 폴더로 이동하여 개발 서버 실행
$ cd react_pjt
$ npm start
# -> Starting the development server...
# -> Compiled successfully!
# -> Local: http://localhost:3000 브라우저 자동 연결 완료
```

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
터미널에서 `npx create-react-app` 명령어로 직접 생성해 보고 개발 서버까지 띄워보았다. 터미널에 `Compiled successfully!` 메시지와 함께 브라우저 화면이 뜨는 것까지 확인했다. 다만 `index.html` 파일에는 별다른 내용이 없는데 어떻게 화면이 그려지는지 궁금했는데, `index.js`가 중간에서 Virtual DOM을 활용해 컴포넌트(`App.js`)를 등록해주는 매커니즘을 이해하게 되었다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 생성된 `react_pjt` 폴더 내의 `App.js` 코드를 열어보고, 내부 문구를 내 이름으로 수정하여 브라우저에 실시간으로 반영되는지 테스트해 보기
- **내일의 학습 계획**: React 3강 수강

---
#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
