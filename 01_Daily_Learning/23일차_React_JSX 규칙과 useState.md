# [LG CNS 7기] 4주차 Day2 TIL
React JSX 문법, useState를 활용한 로그인 폼 컴포넌트 실습 및 이벤트 처리

## 📌 오늘의 학습 키워드
- `JSX`
- `Component Directory Structure`
- `useState Hook`
- `Form Event Handling`
- `index.js Root Rendering`

---

## 💡 공부한 내용 정리하기

### 1. JSX
자바스크립트 코드 안에서 HTML 태그 문법을 작성할 수 있게 해주는 확장 문법

- **`className` 사용**: 자바스크립트의 예약어인 `class`와 충돌을 방지하기 위해 `className` 속성 사용
- **자바스크립트 변수/함수 바인딩**: JSX 내부에서 `{ }` 중괄호를 사용해 자바스크립트 변수나 이벤트를 직접 꽂아 넣을 수 있음
- **조건부 렌더링**: `&&` 연산자를 활용해 조건에 따라 태그를 화면에 렌더링함

---

### 2. `useState`를 이용한 폼 상태 관리 및 이벤트 처리
- **상태(State) 선언**: `const [userId, setUserId] = useState("")`를 사용하여 사용자가 입력한 아이디와 비밀번호 데이터를 동적으로 관리
- **`onChange` 이벤트**: `<input>` 입력값이 변경될 때마다 `e.target.value`로 입력 데이터를 읽어와 `setUserId`, `setPassword`로 상태를 새로고침함
- **`onSubmit` 이벤트 & `e.preventDefault()`**:
  - `<form>` 제출 시 브라우저가 자동으로 페이지를 새로고침하는 기본 동작을 `e.preventDefault()`로 막음
  - 콘솔창에 `'btn click event'`를 출력하며 이벤트 핸들링 검증 완료

---

## 💻 Java/React 실습 코드 분석

### 1) `Login.jsx` (`src/component/page/Login.jsx`)
```jsx
import React, { useState } from 'react';
import '../css/Login.css';

const Login = () => {
    // 1. 입력 필드 및 에러 상태 관리
    const [userId, setUserId] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    // 2. 폼 제출 이벤트 핸들러
    const handleSubmit = (e) => {
        e.preventDefault(); // 폼 제출 시 페이지 새로고침 방지
        console.log('btn click event'); // 콘솔 이벤트 실행 확인
    }

    return (
        <div className="login-container">
            <form className="login-box" onSubmit={handleSubmit}>
                <h2>로그인</h2>

                {/* 아이디 입력 영역 */}
                <div className="input-group">
                    <label>아이디</label>
                    <input
                        type="text"
                        placeholder="아이디를 입력하세요"
                        value={userId}
                        onChange={(e) => setUserId(e.target.value)}
                    />
                </div>

                {/* 비밀번호 입력 영역 */}
                <div className="input-group">
                    <label>비밀번호</label>
                    <input
                        type="password"
                        placeholder="비밀번호를 입력하세요"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>

                {/* 에러 메시지 조건부 렌더링 */}
                {error && <p className="error-message">{error}</p>}

                <button type="submit" className="login-btn">로그인</button>
            </form>
        </div>
    );
};

export default Login;
```

### 2) `index.js` (`src/index.js`)
```javascript
import React from 'react';
import ReactDOM from 'react.dom/client';
import './index.css';
import Login from './component/page/Login'; // 작성한 Login 컴포넌트 불러오기

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        {/* 기존 App 컴포넌트 대신 Login 컴포넌트를 메인 루트에 렌더링 */}
        <Login/>
    </React.StrictMode>
);
```

---

## 🖥️ 실행 및 콘솔 출력 결과

- 브라우저 화면(`http://localhost:3000`): '로그인' 타이틀, 아이디/비밀번호 입력란 및 로그인 버튼이 배치된 UI 출력
- 아이디 폼에 아이디 및 비밀번호 입력 후 [로그인] 버튼 클릭 시, 브라우저 개발자 도구 Console 탭에 `btn click event` 로그가 정상 찍힘 확인

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
HTML처럼 보이지만 실제로는 자바스크립트인 JSX 문법을 다뤄보며 리액트의 표기법 규칙(`className`, `{ }` 사용)을 학습했다. 특히 기존 HTML 폼에서는 버튼을 누르면 페이지가 새로고침되어 데이터가 날아갔는데, `e.preventDefault()`를 통해 이를 막고 자바스크립트로 제어하는 흐름을 확인했다. `useState`를 사용해 `<input>`에 글자를 칠 때마다 상태 변수(`userId`, `password`)가 실시간으로 업데이트되는 원리를 이해하면서 리액트로 폼 데이터를 만져보았다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 오늘 만든 로그인 폼에서 아이디나 비밀번호가 빈 값일 때 `setError("아이디를 입력해주세요")`를 실행시켜 에러 메시지가 화면에 빨간 글씨로 뜨게 해보기
- **내일의 학습 계획**: React 4강 수강

---
#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
