# [LG CNS 7기] 3주차 Day1 TIL
GitHub 원격 저장소(Remote Repository) 연동 및 Push/Pull 동기화 실습

## 📌 오늘의 학습 키워드
- `git remote -v`
- `git push (origin main)`
- `git pull` vs `git pull --no-rebase`
- `Remote-Local Commit Synchronization`

---

## 💡 공부한 내용 본인의 언어로 정리하기

### 1. 원격 저장소 연결 확인 및 로컬 커밋 전송 (`push`)
- **`git remote -v`**: 현재 로컬 저장소에 연결된 원격 저장소(GitHub)의 URL 주소(`fetch`, `push`)를 확인함
- **`git push`**: 로컬 저장소에서 새로 커밋한 이력을 원격 저장소(`origin/main`)로 업로드하여 동기화함

---

### 2. 원격 저장소 변경 사항 가져오기 및 병합 (`pull`)
- **`git pull`**: 원격 저장소의 최신 커밋 이력을 가져와 현재 로컬 브랜치에 즉시 병합(Merge)함
  - local과 remote가 일직선상에 있을 때는 **Fast-forward** 방식으로 깔끔하게 반영됨
- **`git pull --no-rebase`**:
  - 로컬과 원격 저장소에 각각 서로 다른 커밋이 존재하여 이력이 갈라졌을 때, Rebase 방식이 아닌 **Merge 커밋을 생성하며 안전하게 합치는 옵션**
  - 원격의 변경 사항을 가져와 로컬 커밋과 자동 병합(Auto-merging)을 수행하고 병합 커밋을 남김

---

### 3. Remote-Local 협업 워크플로우 정리
1. 로컬에서 작업 후 `git add .` 및 `git commit` 진행
2. 원격 저장소에 변경 사항이 있을 수 있으므로 먼저 `git pull` (필요 시 `--no-rebase` 옵션 활용)로 원격 내역을 동기화
3. 병합이 완료된 최종 이력을 `git push`로 원격 저장소에 반영
4. GitHub 상의 Commits 탭에서 로컬 작업 및 병합 커밋 이력이 정상적으로 반영되었는지 검증

---

## 💻 Git 명령어 실습 및 터미널 라인별 분석

```bash
# 1. 원격 저장소 연결 상태 확인
$ git remote -v
# -> origin [https://github.com/Daanyong/Git_prac.git](https://github.com/Daanyong/Git_prac.git) (fetch / push)

# 2. 로컬 변경 사항 커밋 후 원격 저장소로 push
$ git add .
$ git commit -m "add evie to leopards"
$ git push
# -> main -> main (로컬 커밋이 원격으로 성공적으로 전송됨)

# 3. 원격 저장소의 최신 변경 사항 pull (Fast-forward 병합 진행)
$ git pull
# -> Updating 1618a5b..6a85bfe / Fast-forward (leopards.yaml 반영 완료)

# 4. 로컬에서 추가 작업 후 커밋
$ git add .
$ git commit -m "edit leopards manager"

# 5. 원격과 로컬 이력이 분기된 상태에서 pull --no-rebase로 자동 병합 수행
$ git pull --no-rebase
# -> Auto-merging leopards.yaml
# -> Merge made by the 'ort' strategy (병합 커밋 자동 생성)

# 6. 병합된 최종 커밋 이력을 원격 저장소로 push
$ git push
# -> 576dadd..97f9831 main -> main (GitHub 웹 상에 최종 반영 완료)
```

실제 실습 로그

<img width="545" height="605" alt="스크린샷 2026-09-28 185503" src="https://github.com/user-attachments/assets/d3b3faf2-05b3-4ec7-8770-cbfc720944ec" />

<img width="527" height="323" alt="스크린샷 2026-09-28 185511" src="https://github.com/user-attachments/assets/a100b167-19c0-421a-91d7-304333c000b3" />

깃허브 연동 화면 (커밋 메시지 확인)

<img width="1261" height="586" alt="스크린샷 2026-09-28 185856" src="https://github.com/user-attachments/assets/a6f7c763-b509-46b4-b7c7-7f91cd64180a" />

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
로컬 저장소와 GitHub 원격 저장소를 연결하여 실제 코드 내역을 동기화하는 과정을 학습했다. 특히 로컬과 원격의 커밋 이력이 서로 다르게 갈라졌을 때 `git pull --no-rebase` 명령어로 안전하게 병합을 수행하고, 생성된 Merge 커밋까지 포함하여 원격으로 `git push`하는 흐름을 이해하였다. GitHub 웹 페이지의 Commits 탭에서 내가 전송한 커밋 이력들이 순서대로 쌓이는 것을 확인하였다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: GitHub 웹페이지에서 직접 파일을 수정/커밋해 보고, 로컬 터미널에서 `git fetch`와 `git pull`을 실행하여 원격 변경 사항을 수동/자동으로 동기화하는 차이점 비교해보기
- **내일의 학습 계획**: 탄탄한 백엔드 기초, Java 1강 - 개발 환경 세팅 수강

---

#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
