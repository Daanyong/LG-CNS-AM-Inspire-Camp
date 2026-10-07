# [LG CNS 7기] 4주차 Day3 TIL
React 폼 유효성 검사, Axios 비동기 통신 및 CSS 스타일링 실습

## 📌 오늘의 학습 키워드
- `폼 유효성 검사`
- `Axios & async/await`
- `react-router-dom`
- `CSS 스타일링 및 Flexbox 레이아웃`

---

## 💡 공부한 내용 정리하기

### 1. CSS를 활용한 로그인 UI 레이아웃 및 스타일링
- **화면 중앙 정렬**: `body`와 `.login-container`에 `display: flex`, `justify-content: center`, `align-items: center`를 적용하여 로그인 폼을 화면 한가운데에 배치함
- **배경 그라데이션**: `background: linear-gradient(135deg, #74b9ff, #0984e3)`로 부드럽고 입체감 있는 배경색 연출
- **카드 및 버튼 디자인**: `.login-box`에 `border-radius: 16px`와 `box-shadow`를 주어 둥근 모서리의 흰색 카드를 만들고, 로그인 버튼 클릭 시 부드럽게 색상이 바뀌도록 `transition: 0.2s` 애니메이션 효과 부여

---

### 2. 폼 유효성 검사 및 예외 처리
- 아이디나 비밀번호가 입력되지 않은 상태로 제출할 경우, `setError("아이디와 비밀번호를 모두 입력해주세요.")`를 실행하고 바로 `return` 처리하여 불필요한 요청을 방지함
- 검사를 통과하면 `setError('')`로 에러 메시지를 초기화하고 성공 알림창(`alert`)을 띄움

---

### 3. Axios를 활용한 백엔드 API 연동 준비 및 라우팅
- **Axios 라이브러리**: 백엔드 서버와 데이터를 주고받기 위해 `import axios from "axios"` 추가
- **`async / await` 비동기 처리**: `handleSubmit` 함수를 `async`로 선언하고, `await axios.post(...)` 형태로 백엔드 API 요청 구문을 준비함
- **JWT 토큰 저장 원리**: 백엔드 응답으로 전달받은 인증 토큰(`response.data.token`)을 브라우저의 `localStorage.setItem('token', token)`에 저장하여 로그인 상태를 유지하는 흐름을 학습함
- **`react-router-dom`**: 싱글 페이지 애플리케이션의 페이지 이동을 위해 `BrowserRouter`, `Routes`, `Route` 구조 세팅

---

## 💻 React 실습 코드 분석

### 1) `Login.css` (`src/component/css/Login.css`)
```css
body {
    font-family: "Noto Sans KR", sans-serif;
    background: linear-gradient(135deg, #74b9ff, #0984e3);
    height: 100vh;
    margin: 0;
    display: flex;
    justify-content: center;
    align-items: center;
}

.login-box {
    background: #fff;
    padding: 40px;
    width: 350px;
    border-radius: 16px;
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
    text-align: center;
}

.login-btn {
    width: 100%;
    padding: 12px;
    background: #0984e3;
    color: white;
    font-weight: bold;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: 0.2s;
}
```

실제 구현 화면

<img width="1280" height="800" alt="스크린샷 2026-10-07 165915" src="https://github.com/user-attachments/assets/9017f130-211f-4491-ad6a-33346ef5787a" />

### 2) `Login.jsx` (`src/component/page/Login.jsx`)
```jsx
import React, { useState } from 'react';
import axios from "axios";
import '../css/Login.css';

const Login = () => {
    const [userId, setUserId] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log('btn click event');

        // 1. 유효성 검사: 예외 입력값 처리
        if (!userId || !password) {
            setError("아이디와 비밀번호를 모두 입력해주세요.");
            return;
        }

        /*
        // 2. 로그인 API 요청 (주석 처리된 백엔드 통신 구문)
        const response = await axios.post("http://localhost:8080/api/login", {
            username: userId,
            password: password,
        });

        // 3. 토큰을 로컬스토리지에 저장
        const token = response.data.token;
        localStorage.setItem('token', token);
        */

        alert('로그인 성공!');
        setError('');
    }

    return (
        <div className="login-container">
            <form className="login-box" onSubmit={handleSubmit}>
                <h2>로그인</h2>
                <div className="input-group">
                    <label>아이디</label>
                    <input
                        type="text"
                        placeholder="아이디를 입력하세요"
                        value={userId}
                        onChange={(e) => setUserId(e.target.value)}
                    />
                </div>
                <div className="input-group">
                    <label>비밀번호</label>
                    <input
                        type="password"
                        placeholder="비밀번호를 입력하세요"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                {error && <p className="error-message">{error}</p>}
                <button type="submit" className="login-btn">로그인</button>
            </form>
        </div>
    );
};

export default Login;
```

실제 구현 화면 (로그인 성공)

<img width="1277" height="796" alt="image" src="https://github.com/user-attachments/assets/95942dc3-c3fc-4b69-8a7f-42466369607e" />


실제 구현 화면 (로그인 실패)

<img width="893" height="758" alt="image" src="https://github.com/user-attachments/assets/5427ce9c-a126-418c-8fd5-7d611c59c72e" />


---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
기본 기능만 있던 로그인 화면에 중앙 정렬과 그라데이션, 그림자 효과 등 CSS 스타일을 정교하게 입혀보았다. 빈 값이 입력되었을 때 예외 문구를 띄워주는 유효성 검사 로직과 백엔드 연동을 위한 `axios`, `async/await` 비동기 통신, 그리고 로그인 토큰을 로컬스토리지에 저장하는 실제 웹 개발의 메커니즘을 학습했다. `react-router-dom` 라우팅 세팅까지 진행하였다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 오늘 세팅한 CSS 속성 중 `transition`이나 `box-shadow` 값을 수정해 보며 화면 변화 체감하기
- **내일의 학습 계획**: React 5강 수강

---
#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
