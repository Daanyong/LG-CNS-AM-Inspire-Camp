# [Day 4] LG CNS AM Inspire Camp 7기 TIL
작성일: 2026.10.30 (금) | 과목: AI 서비스 개발을 위한 프론트엔드

## 오늘 학습한 주제
- React와 Next.js의 데이터 흐름 및 렌더링 차이점 분석
- Tailwind CSS의 빌드 타임 정적 스캐닝 방식과 동적 클래스 올바른 사용법
- JS 호이스팅, 스크립트 로딩(Async / Defer / Module)의 원리
- Next.js 16의 동적 라우팅(`params`, `searchParams`)과 `<Suspense>` 스트리밍 및 프리렌더링 최적화

## 오늘 배운 것 – 나만의 언어로 설명
- CSR은 브라우저가 빈 `index.html`을 받아 JS를 다 실행한 후 화면을 그리는 반면, Next.js SSR은 서버에서 URL 요청(`params`, `searchParams`)을 받아 다 그려진 HTML을 내려주는 구조다.
- Next.js 15/16에서 `params`와 `searchParams`는 이제 Promise 형태다. `cacheComponents: true` 환경에서는 프리렌더링이 막히는 것을 방지하기 위해 `<Suspense>` 자식 컴포넌트 내부에서 `await` 해야 블로킹 없는 정적 껍데기 스트리밍이 가능하다.
- Tailwind CSS는 런타임에 코드를 실행하는 것이 아니라 빌드 시점에 소스 텍스트를 정적으로 스캔한다. 따라서 `bg-${color}-600`처럼 클래스명을 문자열 조립으로 작성하면 CSS 유틸리티가 생성되지 않으므로, 객체 매핑(e.g., `{ red: "bg-red-600" }[color]`)이나 CSS 변수/inline style 방식을 사용해야 한다.
- JS 호이스팅에서 `function` 선언문은 전체 스코프에서 이름이 미리 등록되지만, `const`/`let`은 초기화 전 실행 구간인 임시 사각지대(TDZ)에 걸려 `ReferenceError`가 발생한다. 
- `<script type="module">`은 기본적으로 `defer`처럼 동작하여 HTML 파싱을 막지 않고 문서 파싱 완료 후 실행된다.

## 기억에 남는 기타 내용들
- js나 html은 string이 근본 -> boolean도 `'true'`, `'false'`와 같이 표기하나 변수 개념이 아님
- min.js (minimum - 로직은 그대로이나 코드 용량을 압도적으로 줄여버림)

## AI와 함께한 부분
- 물어본 것: 강사님 github를 로컬에 git clone한 후 내가 코드를 따라 작성하며 실습하다가 다시 git pull 하며 파일 충돌 발생
- AI 답 중 검증한 것: 내가 작성했던 부분이 잘 살아있는지
- AI가 틀렸던 것: 없음

## 내일 계획 & 아직 모르는 것
- 내일 이어서 할 내용: 이번주 내용 전체 복습
- 아직 흐릿한 것: 

#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
