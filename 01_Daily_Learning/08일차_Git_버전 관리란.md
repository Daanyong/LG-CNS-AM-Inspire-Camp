# [LG CNS 7기] 2주차 Day1
Git 버전 관리 시스템의 이해 및 최초 환경 설정

## 📌 오늘의 학습 키워드
- `Version Control System (VCS)`
- `Git Concept & Necessity`
- `CLI (Git Bash) vs GUI (SourceTree)`
- `Git Initial Configuration (git config)`

---

## 💡 공부한 내용 본인의 언어로 정리하기

### 1. 버전 관리(Version Control)와 Git의 이해
파일의 변경 사항을 시간에 따라 기록하여, 나중에 특정 시점의 버전을 다시 불러올 수 있는 시스템이다.

- **버전 관리의 이점**:
  - 각 파일 및 프로젝트 전체를 이전 상태로 되돌릴 수 있음
  - 시간에 따른 수정 내역을 비교 분석할 수 있음
  - 누가 문제를 일으켰는지, 언제 어떤 이슈가 발생했는지 추적 가능
  - 파일을 손실하거나 잘못 수정했을 때 쉽게 복구할 수 있음
- **Git이란?**:
  - 빠른 수행 속도에 중점을 둔 대표적인 **분산형 버전 관리 시스템 (Distributed Version Control System)**

---

### 2. Git의 필요성 (협업 및 개발 환경)
- 팀원 모두가 동일한 코드 기반에서 개발하도록 하여 불필요한 작업 시간 및 충돌을 최소화함
- 공통 프레임워크, 테스트 코드, 샘플 코드를 사전에 구성하여 프로젝트 시작 전 팀원 전체에게 동일한 테스트 환경 제공 가능

---

### 3. Git 인터페이스 종류
- **CLI (Command Line Interface)**: Terminal 환경 사용 (디렉터리 내에서 'Open Git Bash here'을 열어 사용)
- **GUI (Graphic User Interface)**: 그래픽 기반 도구 활용 (SourceTree 등)

---

### 4. Git 최초 환경 설정 (Initial Configuration)
Git 설치 후 커밋 작성자를 식별하기 위해 필수적으로 진행하는 사용자 설정 명령어이다.

- **사용자 이름 설정**:
  - `git config --global user.name "본인이름"`
  - `git config --global user.name` (설정 확인)

- **사용자 이메일 설정**:
  - `git config --global user.email "test@email.com"`
  - `git config --global user.email` (설정 확인)

- **기본 브랜치명 변경 (`main`으로 설정)**:
  - `git config --global init.defaultBranch main`

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
단순히 코드를 저장하는 용도로 생각했던 Git의 본질적인 목적(버전 추적, 복구, 협업 환경 통일)을 알게 되었다. 단순히 명령어를 외우기보다 왜 사용자 정보(`user.name`, `user.email`)와 기본 브랜치(`main`)를 글로벌 설정해 두어야 하는지 이해하였으며, CLI 환경인 Git Bash 사용에 점차 익숙해지고 있다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: Git Bash를 열어 오늘 설정한 `git config --list` 정보를 직접 확인하고, 기본 브랜치명이 `main`으로 잘 변경되었는지 체크하기
- **내일의 학습 계획**: Git 2강 - Git 설치 및 초기 세팅 수강

---

#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
