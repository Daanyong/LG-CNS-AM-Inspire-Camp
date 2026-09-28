# [LG CNS 7기] 2주차 Day7 TIL
Git 병합 충돌(Conflict) 해결 및 Merge/Rebase 중단(--abort) 메커니즘

## 📌 오늘의 학습 키워드
- `Git Merge & Rebase Conflict`
- `Conflict Resolution Process`
- `git merge --abort`
- `git rebase --abort` / `git rebase --continue`

---

## 💡 공부한 내용 본인의 언어로 정리하기

### 1. 병합 충돌(Conflict) 발생 원인
- 서로 다른 두 브랜치에서 **동일한 파일의 같은 위치**를 수정하고 병합(Merge 또는 Rebase)을 시도할 때 발생함
- Git이 어느 쪽 코드를 최종 반영해야 할지 자동으로 판단할 수 없으므로, 개발자가 직접 코드를 수정하여 충돌을 해결해야 함

---

### 2. Merge 및 Rebase 중단 명령어 (`--abort`)
당장 충돌을 해결하기 어렵거나, 병합 시도를 취소하고 병합 시작 전 안전한 상태로 되돌리고 싶을 때 사용함

- **`git merge --abort`**:
  - Merge 작업 도중 충돌이 발생했을 때, 진행 중인 Merge를 취소하고 이전 상태로 원상복구함
- **`git rebase --abort`**:
  - Rebase 작업 도중 충돌이 발생했을 때, Rebase 시도를 중단하고 원래 브랜치 상태로 되돌림

---

### 3. Rebase 충돌 해결 워크플로우
1. 충돌 발생 시 터미널 상태가 `(branch|REBASE 1/2)` 등 병합 진행 중 상태로 변경됨
2. 충돌이 난 파일의 에디터를 열어 충돌 기호(`<<<<<<<`, `=======`, `>>>>>>>`)를 정리하고 원하는 코드로 수정
3. 수정 완료 후 `git add .`으로 스테이징 영역에 추가
4. 별도의 커밋 없이 **`git rebase --continue`** 를 실행하여 남은 Rebase 과정을 이어서 진행
5. 만약 Rebase를 그만두고 싶다면 **`git rebase --abort`** 로 중단

---

## 💻 Git 명령어 실습 및 터미널 라인별 분석

```bash
# 1. 충돌 테스트를 위한 브랜치 생성 및 각 브랜치별 동일 파일 수정/커밋
$ git branch conflict01
$ git branch conflict02
$ git commit -m "edit tigers, leopards, panthers" # master 커밋

$ git switch conflict01
$ git commit -m "edit tigers" # conflict01 커밋

$ git switch conflict02
$ git commit -m "edit leopards"
$ git commit -m "edit panthers" # conflict02 커밋

# 2. master 브랜치로 돌아와 conflict01 병합 시도 (tigers.yaml 충돌 발생)
$ git switch master
$ git merge conflict01
# -> CONFLICT (content): Merge conflict in tigers.yaml
# -> Automatic merge failed; fix conflicts and then commit the result.
# -> 터미널 상태가 (master|MERGING)으로 전환됨

# 3. tigers.yaml 충돌 수동 해결 후 저장, add 및 Merge 커밋 생성
$ git add .
$ git commit -m "incomnig to tigers"
# -> 충돌 해결 완료 및 (master) 상태 복귀

# 4. conflict02 브랜치로 이동하여 master 대상 Rebase 시도 (leopards.yaml 충돌 발생)
$ git switch conflict02
$ git rebase master
# -> CONFLICT (content): Merge conflict in leopards.yaml
# -> error: could not apply 102a3dd... edit leopards
# -> 터미널 상태가 (conflict02|REBASE 1/2) 상태로 전환됨

# 5. Rebase 진행 중 브랜치 전환 시도 (에러 발생 확인)
$ git switch conflict02
# -> fatal: cannot switch branch while rebasing

# 6. leopards.yaml 충돌 수동 해결 후 add 실행 (이후 git rebase --continue로 이어 진행)
$ git add .
```

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
실무에서 가장 두려운 상황 중 하나인 'Git 병합 충돌' 현상을 직접 만들어보고 해결하는 실습을 진행했다. Merge 시 충돌이 나면 `(master|MERGING)` 상태가 되고 충돌 수정 후 `git commit`을 거치며, Rebase 시 충돌이 나면 `(branch|REBASE)` 상태가 되어 해결 후 `git rebase --continue`로 풀어나가는 메커니즘을 학습했다. 또한 당장 해결이 곤란할 때 언제든 안전하게 돌아갈 수 있는 `git merge --abort`와 `git rebase --abort`라는 치트키 명령어를 학습했다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 일부러 충돌을 발생시킨 뒤 `git rebase --abort`를 실행하여 Rebase 시도 전 상태로 완벽히 복원되는지 터미널에서 확인해보기
- **내일의 학습 계획**: Git 8강 - 협업 시나리오 수강

---

#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
