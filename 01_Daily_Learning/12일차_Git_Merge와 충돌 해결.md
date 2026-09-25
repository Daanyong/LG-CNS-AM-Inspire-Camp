# [LG CNS 7기] 2주차 Day5 TIL
Git Branch 조작 실습 및 브랜치별 독립 작업 이력 그래프 확인

## 📌 오늘의 학습 키워드
- `Git Branch Management (branch, switch)`
- `Branch Deletion Error (cannot delete checked out branch)`
- `Multi-Branch Parallel Work`
- `git log --all --decorate --oneline --graph`

---

## 💡 공부한 내용 본인의 언어로 정리하기

### 1. 브랜치 생성 및 작업 공간 이동
- **`git branch 브랜치명`**: 기존 브랜치에서 분기된 새로운 브랜치를 생성함
- **`git switch 브랜치명`**: 작업 공간(HEAD)을 지정한 브랜치로 이동시킴
- **`git branch`**: 로컬에 존재하는 브랜치 목록을 조회하며, 현재 위치한 브랜치는 앞에 `*` 표기로 확인 가능함

---

### 2. 브랜치 삭제 규칙과 주의점
- **삭제 명령어**: `git branch -d 브랜치명`
- **주의사항**: **현재 접속 중인(HEAD가 가리키는) 브랜치는 자기 자신을 스스로 삭제할 수 없음**
  - 예: `add-coach` 브랜치 위치에서 `git branch -d add-coach` 실행 시 `error: cannot delete branch ... used by worktree` 에러 발생
  - **해결방법**: `git switch master` 등 다른 브랜치로 이동한 후 삭제 명령어를 수행해야 함

---

### 3. 브랜치별 독립적 개발 및 이력 그래프 확인
- 서로 다른 브랜치(`master`, `add-coach`, `new-teams`)에서 각각 작업을 수행하고 커밋하면, 각 브랜치는 서로에게 영향을 주지 않고 독자적인 커밋 히스토리를 차곡차곡 쌓아감
- **`git log --all --decorate --oneline --graph`**:
  - `--all`: 현재 위치한 브랜치뿐만 아니라 모든 브랜치의 커밋 이력을 출력
  - `--oneline`: 커밋 해시와 메시지를 한 줄로 간결하게 표시
  - `--graph`: 브랜치가 어떻게 분기(Branching)되어 나갔는지 아스키 그래프로 시각화하여 확인 가능

---

## 💻 Git 명령어 실습 및 터미널 라인별 분석

```bash
# 1. 브랜치 생성 및 목록 확인
$ git branch add-coach
$ git branch
# -> add-coach 생성 확인 및 현재 위치(* master) 확인

# 2. 브랜치 이동 후 자기 자신 삭제 시도 (에러 발생 확인)
$ git switch add-coach
$ git branch -d add-coach
# -> error: cannot delete branch 'add-coach' used by worktree

# 3. master로 돌아가 정상적으로 브랜치 삭제 후 다시 필요한 브랜치들 생성
$ git switch master
$ git branch -d add-coach
$ git branch add-coach
$ git branch new-teams

# 4. master 브랜치에서 커밋 2개 추가
$ git add .
$ git commit -m "add olivia to leopards"
$ git add .
$ git commit -m "add freddie to panthers"

# 5. add-coach 브랜치로 이동하여 코치 관련 커밋 3개 추가
$ git switch add-coach
$ git add . && git commit -m "add coach grace to tigers"
$ git add . && git commit -m "add coach oscar to leopards"
$ git add . && git commit -m "add coach Teddy to panthers"

# 6. new-teams 브랜치로 이동하여 새 팀 관련 커밋 2개 추가
$ git switch new-teams
$ git add . && git commit -m "add team pumas"
$ git add . && git commit -m "add team jaguars"

# 7. 전체 브랜치의 분기 이력을 한눈에 그래프로 시각화 조회
$ git log --all --decorate --oneline --graph
# -> master, add-coach, new-teams가 각각 독립된 커밋 줄기로 갈라진 모습 확인
```

실제 실습 화면

<img width="780" height="634" alt="스크린샷 2026-09-25 101746" src="https://github.com/user-attachments/assets/c4b38930-61fd-49ba-8867-c37ec612f7f0" />

<img width="562" height="645" alt="스크린샷 2026-09-25 102219" src="https://github.com/user-attachments/assets/764b368f-9045-4727-a1ba-a5bd6c44fd2d" />

<img width="568" height="224" alt="스크린샷 2026-09-25 102241" src="https://github.com/user-attachments/assets/ce8d1313-988d-4ac4-ace1-3fa6dbc67d2a" />

SourceTree 실습 화면

<img width="952" height="536" alt="image" src="https://github.com/user-attachments/assets/6d04da40-5027-4cc5-b77f-9176f7533878" />

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
`master` 브랜치에서 시작하여 `add-coach`와 `new-teams` 브랜치로 나뉘어 각자 독립적으로 코드를 수정하고 커밋하는 실습을 진행했다. 특히 현재 위치한 브랜치는 바로 삭제할 수 없다는 에러를 확인하고 이를 `git switch`로 해결해 보면서 브랜치 조작 메커니즘을 익힐 수 있었다. 마지막에 `git log --all --graph` 옵션으로 3개의 브랜치가 분기되어 뻗어나간 이력을 시각화해보며, Git의 버전 관리 체계를 학습하였다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 오늘 갈라진 브랜치들(`add-coach`, `new-teams`)을 `master` 브랜치로 가져와 하나로 합치는 브랜치 병합(`git merge`) 실습 진행해보기
- **내일의 학습 계획**: Git 6강 - Github 연동 및 원격 저장소
