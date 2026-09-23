# [LG CNS 7기] 2주차 Day3 TIL
Git 커밋 이력 관리 실습 및 과거로 되돌아가는 방법 (Reset vs Revert)

## 📌 오늘의 학습 키워드
- `Git Commit History & Management`
- `git commit -am (Add & Commit Option)`
- `git log (Commit Hash)`
- `git reset vs git revert`
- `GUI Tool (SourceTree) Tracking`

---

## 💡 공부한 내용 본인의 언어로 정리하기

### 1. Git에서 과거로 돌아가는 두 가지 방법
프로젝트 진행 중 이전 커밋 상태로 되돌아가야 할 때, `Reset`과 `Revert` 방식의 차이를 정확히 알고 상황에 맞게 선택해야 함.

- **`git reset`**:
  - 특정 커밋 시점으로 완전히 돌아간 뒤, **그 이후에 쌓인 커밋 내역을 지워버리는 방식**
  - **주의사항**: 로컬 개인 작업 공간에서는 유용하지만, 이미 원격 저장소(GitHub)에 올라가 공유된 커밋을 지우면 다른 팀원들과 충돌이 발생할 수 있음
- **`git revert`**:
  - 되돌리고 싶은 시점의 커밋이 변경한 내용을 **거꾸로 취소하는 새로운 커밋을 생성하는 방식**
  - 이전 히스토리를 삭제하지 않고 **보존**하므로, 협업 공간이나 원격 저장소에 공유된 코드일 때 안전하게 사용함
- **GUI (SourceTree)**:
  - 터미널 CLI 환경 외에 SourceTree 같은 GUI 도구를 통해 복잡하게 얽힌 커밋 그래프와 변경 사항을 직관적으로 확인하고 추적할 수 있음

---

## 💻 Git 커밋 실습 및 터미널 라인별 분석

```bash
# 1. 파일 수정 및 삭제, 신규 생성 상태 확인
$ git status
# -> lions.yaml 삭제, tigers.yaml 수정, leopards.yaml 생성(Untracked) 확인

# 2. 모든 변경 사항을 스테이징 영역으로 추가
$ git add .

# 3. 변경 사항을 확인하고 메시지와 함께 커밋
$ git commit -m "delete lions and modified tigers new leopards"
# -> [master c7b28df] 3 files changed (lions 삭제, tigers 수정, leopards 생성 기록)

# 4. 특정 신규 파일(cheetahs.yaml)만 추가하여 커밋
$ git add cheetahs.yaml
$ git commit -m "add team cheetahs"
# -> [master 473c3ee] add team cheetahs

# 5. 이미 추적(Tracked) 중인 파일의 수정 사항을 add와 commit 동시에 진행
$ git commit -am "add george to tigers"
# -> [master 76375c9] add george to tigers (-am 옵션으로 add 과정 생략)

# 6. 신규 파일(panthers.yaml) 추가 후 커밋
$ git add panthers.yaml
$ git commit -m "delete cheetahs replace leopards and panthers"
# -> [master 78bda3b] delete cheetahs replace leopards and panthers

# 7. 지금까지 쌓인 전체 커밋 이력(히스토리) 조회
$ git log
# -> 최신 커밋(78bda3b)부터 첫 커밋(e89fecf)까지 커밋 해시값, 작성자, 작성일, 커밋 메시지 순으로 출력
```

실제 실습 화면

VSCode의 Git Bash

<img width="540" height="407" alt="스크린샷 2026-09-23 173906" src="https://github.com/user-attachments/assets/5bbbe5cf-2c43-447d-93e1-f03292811561" />

<img width="539" height="257" alt="스크린샷 2026-09-23 173853" src="https://github.com/user-attachments/assets/94a2e37b-599c-42fc-967c-0db71891160c" />

<img width="561" height="590" alt="스크린샷 2026-09-23 173836" src="https://github.com/user-attachments/assets/7efe2832-d97c-47cb-ad3f-d5e0040af2b9" />

Source Tree로 확인하는 장면

<img width="1279" height="755" alt="스크린샷 2026-09-23 174338" src="https://github.com/user-attachments/assets/351f4724-4315-425a-ac35-04d8c98246f2" />

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
여러 파일의 삭제, 수정, 추가 과정을 차례대로 커밋해 보며 `git commit -am` 같은 유용한 단축 옵션을 익힐 수 있었다. `git log`를 통해 프로젝트가 쌓여온 궤적을 확인하면서, 과거 시점으로 안전하게 돌아가는 `reset`과 `revert`의 개념적 차이를 학습했다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 오늘 실습한 저장소에서 `git reset`과 `git revert` 명령어를 직접 테스트해 보고 SourceTree GUI 화면에서 커밋 그래프가 어떻게 변화하는지 눈으로 비교 검증하기
- **내일의 학습 계획**: Git 4강 - Branch의 개념과 활용 수강
