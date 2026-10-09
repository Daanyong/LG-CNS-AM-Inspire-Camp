# [LG CNS 7기] 4주차 Day4 TIL
React 라우터 동적 페이지 이동, useNavigate 상태 전달 및 Welcome 화면 구현

## 📌 오늘의 학습 키워드
- `react-router-dom 라우팅`
- `useNavigate 훅`
- `useLocation 훅`
- `라우터 상태 전달`
- `인라인 스타일 객체`

---

## 💡 공부한 내용 정리하기

### 1. `react-router-dom`을 활용한 경로 연결
- `App.js`에서 `BrowserRouter`, `Routes`, `Route`를 활용하여 URL 경로에 따라 렌더링할 컴포넌트를 지정함
- `/` 경로 연결: 기본 로그인 화면인 `Login` 컴포넌트 호출
- `/welcome` 경로 연결: 로그인 성공 후 이동할 환영 페이지인 `Welcome` 컴포넌트 호출

---

### 2. `useNavigate`와 `useLocation`을 통한 페이지 이동 및 데이터 전달
- **`useNavigate`**: 페이지를 이동시킬 때 사용하는 훅
  - `moveUrl('/')`을 통해 로그아웃 버튼 클릭 시 로그인 화면으로 이동
  - 로그인 성공 시 `navigate('/welcome', { state: { username: userId } })`와 같이 이동 경로에 사용자 정보를 넘겨줄 수 있음
- **`useLocation`**: 이전 페이지에서 전달받은 데이터(`state`)를 넘겨받는 훅
  - `location.state?.username || 'Guest'` 구문을 활용하여 전달받은 사용자 이름을 읽어오고, 데이터가 없을 경우 기본값으로 `'Guest'`를 출력하도록 처리함

---

### 3. 자바스크립트 객체를 활용한 인라인 스타일링
- 별도의 CSS 파일 대신 `Welcome.jsx` 파일 내부에 `styles` 자바스크립트 객체를 선언하고, `style={styles.container}` 형태로 컴포넌트에 직접 스타일 적용

---

## 💻 React 실습 코드 분석

### 1) `Welcome.jsx` (`src/component/page/Welcome.jsx`)
```jsx
import { useNavigate, useLocation } from 'react-router-dom';

const Welcome = () => {

    const moveUrl = useNavigate();
    const location = useLocation();
    
    // location.state로 전달받은 username을 추출하고, 없을 경우 'Guest' 사용
    const username = location.state?.username || 'Guest';

    // 로그아웃 버튼 클릭 시 메인 로그인 페이지('/')로 이동
    const handleLogout = () => {
        moveUrl('/');
    }

    return(
        <div style = {styles.container}>
            <h1>환영합니다, {username}님 !! </h1>
            <p>로그인이 성공적으로 완료되었습니다.</p>
            <button onClick={handleLogout} style={styles.btn}>로그아웃</button>
        </div>
    );
}

// 인라인 스타일 적용을 위한 자바스크립트 객체 정의
const styles = {
    container: {
        marginTop: "100px",
        textAlign: "center",
        fontFamily: "Noto Sans KR, sans-serif",
    },
    btn: {
        marginTop: "20px",
        padding: "10px 20px",
        background: "#0984e3",
        color: "#fff",
        border: "none",
        borderRadius: "8px",
        cursor: "pointer",
    },
}

export default Welcome;
```

### 2) `App.js` (`src/App.js`)
```javascript
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './component/page/Login';
import Welcome from './component/page/Welcome';

function App() {
    return (
        <Router>
            <Routes>
                {/* 메인 경로 및 환영 페이지 경로 매핑 */}
                <Route element="{<Login" path="/"/>} />
                <Route element="{<Welcome" path="/welcome"/>} />
            </Routes>
        </Router>
    );
}

export default App;
```

---

## 🖥️ 실행 결과

- `http://localhost:3000/welcome` 접속 시 "환영합니다, {로그인 시 사용한 Id}님 !!" 문구와 성공 메시지, 그리고 [로그아웃] 버튼이 화면에 정상 렌더링됨
- 이전 로그인 페이지에서 전달한 아이디 정보가 `Welcome` 페이지의 `{username}` 자리에 동적으로 잘 반영된 것을 확인

실제 구현 화면

<img width="636" height="597" alt="스크린샷 2026-10-08 122308" src="https://github.com/user-attachments/assets/f9b0ad59-2c89-4cd4-8ed1-0b5a22eb3a71" />

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
`App.js`에서 라우터 경로를 세팅하고 `useNavigate`와 `useLocation`을 조합하여 로그인 화면에서 환영 화면으로 넘어가는 동적 페이지 전환 및 데이터 전달을 완성했다. 리액트가 싱글 페이지 애플리케이션으로 작동하면서도 주소창의 URL 경로에 따라 필요한 화면을 바꿔 보여주고, 이전 페이지에서 입력한 정보를 다음 페이지에서 넘겨받아 화면에 띄우는 메커니즘을 학습했다.

그리고 무엇보다 사전학습을 시작한 날부터 지금까지 단 하루도 거르지 않고 '1일 1강의 & 1 TIL 작성'을 달성하여 큰 성취감을 느낀다. 당장 내일부터는 지정된 강의 일정이 없으나, 아직 부족하다고 느껴지는 자바 기본기를 자습으로 보완하며 매일 TIL을 계속 이어갈 계획이다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: `useLocation`으로 받는 `state` 값이 없는 상태로 바로 `/welcome` URL에 접근했을 때 `'Guest'`로 안전하게 예외 처리되는지 테스트하기
- **내일의 학습 계획**: 본 교육 수강 전까지 Java 공부

---

#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
