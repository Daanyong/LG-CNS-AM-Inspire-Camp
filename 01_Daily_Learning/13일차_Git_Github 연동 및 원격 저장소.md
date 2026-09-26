# [LG CNS 7기] 2주차 Day6 TIL
Git 브랜치 병합 방식 (Merge vs Rebase) 실습 및 적용

## 📌 오늘의 학습 키워드
- `Git Branch Merge (3-way merge)`
- `Git Branch Rebase`
- `Fast-Forward Merge`
- `Branch Cleanup (git branch -d)`

---

## 💡 공부한 내용 본인의 언어로 정리하기

### 1. 서로 다른 브랜치를 합치는 두 가지 방식 (Merge vs Rebase)
- **`git merge`**:
  - 두 브랜치의 줄기를 **하나의 새로운 커밋(Merge Commit)으로 이어붙여** 합치는 방식
  - 기존 각 브랜치의 커밋 히스토리가 그대로 보존된다는 장점이 있음
- **`git rebase`**:
  - 브랜치의 **기반(Base) 위치를 다른 브랜치의 최신 커밋 뒤로 변경하여 이어붙이는 방식**
  - 커밋 히스토리가 분기 없이 하나의 일자(선형) 그래프로 깔끔하게 정리됨

---

### 2. Merge와 Rebase의 실무 적용 흐름
1. **Merge 활용 (`master`에 `add-coach` 병합)**:
   - 메인 줄기인 `master`로 이동하여 `git merge add-coach` 실행
   - 자동 병합(ort strategy)을 통해 새로운 병합 커밋이 생성되고 안전하게 합쳐짐
2. **Rebase + Fast-forward 활용 (`new-teams` 병합)**:
   - `new-teams` 브랜치로 이동하여 `git rebase master` 수행 ➡️ `new-teams` 커밋들의 Base가 `master` 최신 시점 뒤로 이동함
   - 다시 `master`로 돌아와 `git merge new-teams` 수행 ➡️ 이미 선형으로 정리되었으므로 **Fast-forward** 방식으로 병합이 완료됨
3. **사용이 완료된 브랜치 삭제**:
   - 병합이 완료된 `add-coach`와 `new-teams` 브랜치는 `git branch -d` 명령어로 삭제하여 깔끔하게 정리함

---

## 💻 Git 명령어 실습 및 터미널 라인별 분석

```bash
# 1. master 브랜치로 이동 후 add-coach 브랜치를 Merge 방식으로 병합
$ git switch master
$ git merge add-coach
# -> Auto-merging (leopards, panthers, tigers 파일 자동 병합 완료 및 Merge 커밋 생성)

# 2. 병합이 완료된 add-coach 브랜치 삭제 및 남은 브랜치 확인
$ git branch -d add-coach
$ git branch
# -> master, new-teams 존재 확인

# 3. new-teams 브랜치로 이동하여 master의 최신 변경 사항 뒤로 Rebase 수행
$ git switch new-teams
$ git rebase master
# -> Successfully rebased and updated refs/heads/new-teams (Base 위치 재설정 완료)

# 4. master 브랜치로 돌아와 new-teams 병합 (Fast-forward 방식으로 빠르게 병합됨)
$ git switch master
$ git merge new-teams
# -> Fast-forward (jaguars.yaml, pumas.yaml 추가 완료)

# 5. 병합이 완료된 new-teams 브랜치 삭제
$ git branch -d new-teams
# -> Deleted branch new-teams
```

실제 실습 화면

<img width="554" height="634" alt="스크린샷 2026-09-26 161643" src="https://github.com/user-attachments/assets/e9636d85-bd66-4749-b8a7-0e93e5700286" />

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
구별하기 어려웠던 `merge`와 `rebase`의 차이점을 직접 터미널에서 수행해 보며 학습했다. `add-coach` 브랜치는 Merge 커밋을 남기며 두 줄기를 합쳤고, `new-teams` 브랜치는 먼저 `git rebase`로 Base를 맞춘 뒤 `master`에서 Fast-forward 병합을 일으켜 깔끔한 단일 선형 이력을 만들어 보았다. 상황에 따라 히스토리 보존이 필요할 때는 Merge를, 커밋 그래프 단정화가 필요할 때는 Rebase를 선택하는 기준을 세울 수 있었다. 다만 이번에도 영상제목과 영상 내용이 맞지 않아 아쉬운 점이 있다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 서로 다른 브랜치에서 동일한 파일의 같은 위치를 수정하여 충돌을 일으키고, 이를 직접 해결한 뒤 커밋하는 과정 테스트해 보기
- **내일의 학습 계획**: Git 7강 - Push&Pull 수강

---
#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
