# [LG CNS 7기] 2주차 Day4 TIL
Git Reset/Revert 실습 및 Branch 개념과 핵심 명령어 정리

## 📌 오늘의 학습 키워드
- `git reset --hard`
- `git revert` & `git revert --no-commit`
- `Git Branch Concept`
- `git switch` / `git switch -c`
- `git branch -d` / `git branch -D`

---

## 💡 공부한 내용 본인의 언어로 정리하기

### 1. Git 과거 되돌리기 실습 (Reset vs Revert)
- **`git reset --hard <commit>`**:
  - 지정한 커밋 시점으로 완전히 되돌아가며, 그 이후의 작업 내역과 변경 사항을 모두 삭제함
- **`git revert <commit>`**:
  - 특정 커밋에서 수행된 작업을 반대로 취소하는 **새로운 커밋**을 생성하여 이력을 보존함
- **`git revert --no-commit <commit>`**:
  - 커밋을 바로 생성하지 않고, revert된 변경 사항만 스테이징 영역에 올려둔 상태로 만듦 (`REVERTING` 상태 진입)
  - 이 상태에서 `git reset --hard`를 수행하면 진행 중이던 revert 작업을 취소하고 이전 커밋으로 복구 가능

---

### 2. Git Branch 개념 및 핵심 명령어
- **Branch란?**: 독립적으로 작업을 진행할 수 있는 **분기된 다른 차원(공간)**
- **주요 명령어**:
  - `git branch`: 현재 생성된 브랜치 목록 조회
  - `git branch 브랜치명`: 새로운 브랜치 생성
  - `git switch 브랜치명`: 해당 브랜치로 이동
  - `git switch -c 브랜치명`: 브랜치 생성과 동시에 해당 브랜치로 이동
  - `git branch -m 기존브랜치명 새브랜치명`: 브랜치 이름 변경
  - `git branch -d 브랜치명`: 작업이 완료된 브랜치 삭제 (병합되지 않은 변경 사항이 있으면 삭제 불가)
  - `git branch -D 브랜치명`: 병합 여부와 상관없이 브랜치 강제 삭제

---

## 💻 Git 명령어 실습 및 터미널 라인별 분석

```bash
# 1. 이전 커밋 이력(git log) 조회 후, 첫 커밋(e89fecf)으로 강제 리셋
$ git reset --hard e89fecf2dfa2ee79d1015f2151ee4c713cff1baa
# -> HEAD가 첫 커밋 시점으로 이동하며 이후 이력이 사라짐

# 2. 다시 최근 커밋(78bda3b) 해시를 입력하여 복구
$ git reset --hard 78bda3b
# -> HEAD가 다시 최근 커밋 시점으로 되돌아옴

# 3. 특정 커밋(76375c9: "add george to tigers")의 변경 사항을 취소하는 Revert 커밋 생성
$ git revert 76375c9c082befb466afd4b34dc54c638ab84e9f
# -> [master 3e2fae3] Revert "add george to tigers" 커밋 자동 생성 완료

# 4. 파일 삭제 실습 (leopards.yaml 제거)
$ git rm leopards.yaml

# 5. 커밋을 즉시 생성하지 않고 Revert 진행 (--no-commit)
$ git revert --no-commit 78bda3b8a0958f830bfa1ce3dabbe758fa8b4992
# -> 터미널에 (master|REVERTING) 표시되며 Revert 대기 상태 진입

# 6. 진행 중이던 Revert 상태를 취소하고 이전 상태로 복구
$ git reset --hard
# -> HEAD가 Revert 이전인 3e2fae3 커밋으로 복원됨
```

실제 실습 화면

VSCode의 Git Bash

<img width="561" height="631" alt="스크린샷 2026-09-24 184458" src="https://github.com/user-attachments/assets/2acf3c98-9e52-4f1f-9b61-04706c08efab" />

<img width="599" height="199" alt="스크린샷 2026-09-24 184507" src="https://github.com/user-attachments/assets/0f495f42-c9c4-44b1-86ff-95b35377a50d" />

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
`git reset --hard`와 `git revert`를 직접 실행하며 로컬 저장소의 커밋 위치(`HEAD`)가 이동하거나 취소 커밋이 쌓이는 흐름을 확인했다. 특히 `--no-commit` 옵션을 통해 Revert 작업 도중 `(master|REVERTING)` 상태가 되는 것과 이를 `git reset --hard`로 취소하는 과정을 다뤄보며 Git의 안전장치 메커니즘을 이해할 수 있었다. 또한 메인 줄기와 격리된 작업 공간인 브랜치(Branch)의 기본 조작법을 익혀, 안전한 기능 개발과 협업을 위한 기반을 다졌다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: `git switch -c` 명령어로 기능 개발용 브랜치를 직접 만들어 파일 수정 후 커밋해 보고, `master` 브랜치와 비교 분석하기
- **내일의 학습 계획**: Git 5강 - Merge와 충돌 해결
