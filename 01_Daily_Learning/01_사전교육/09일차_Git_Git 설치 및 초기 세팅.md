# [LG CNS 7기] 2주차 Day2 TIL
Git 기초 명령어 실습 및 GitHub 개념 정리

## 📌 오늘의 학습 키워드
- `Git (Local Repository)`
- `Basic Git Commands (init, status, add, commit, log)`
- `GitHub (Cloud Repository)`
- `Fork, Pull Request, Issue, Actions`

---

## 💡 공부한 내용 본인의 언어로 정리하기

### 1. Git (Local): 코드 버전 관리
- **`git init`**: 새로운 Git 저장소(Repository)를 초기화하고 생성함
- **`git add`**: 작업 디렉터리의 변경된 파일을 커밋 전 대기 공간인 스테이징 영역(Staging Area)에 추가함
- **`git commit`**: 스테이징 영역에 올라온 변경 사항을 하나의 버전(히스토리)으로 기록함
- **`git log`**: 지금까지 기록된 커밋 이력을 확인함
- **`git branch & git merge`**: 독립적인 작업 공간(브랜치)을 생성하고, 병합(Merge)함
- **`git checkout`**: 특정 브랜치나 이전 커밋 버전으로 이동함

---

### 2. GitHub (Cloud): 코드 공유 및 협업 플랫폼
- **Repository**: 클라우드 상의 프로젝트 저장소
- **Fork**: 다른 사람의 저장소를 내 GitHub 계정으로 복사해오는 기능
- **Pull Request (PR)**: 내 저장소에서 작업한 변경 사항을 원본 저장소에 반영해달라고 병합 요청하는 기능
- **Issue**: 버그 제보, 기능 개선, 작업 논의 등을 진행하는 프로젝트 게시판
- **Action**: 빌드, 테스트, 배포 과정을 자동화하는 CI/CD 도구

---

## 💻 Git 명령어 실습 및 터미널 라인별 분석

```bash
# 1. 현재 디렉터리를 Git 저장소로 초기화
$ git init
# -> Initialized empty Git repository ... (.git 숨김 폴더 생성)

# 2. 현재 작업 공간의 상태 및 변경 사항 확인
$ git status
# -> On branch master / Untracked files: lions.yaml, tigers.yaml (추적되지 않는 파일 존재)

# 3. 모든 변경된 파일(lions.yaml, tigers.yaml)을 스테이징 영역에 올림
$ git add .

# 4. 스테이징 영역 상태 재확인
$ git status
# -> Changes to be committed: new file: lions.yaml, tigers.yaml (커밋 준비 완료)

# 5. 커밋 이력 확인 시도 (아직 커밋을 안 했으므로 에러 발생)
$ git log
# -> fatal: your current branch 'master' does not have any commits yet

# 6. 스테이징된 파일들을 메시지와 함께 첫 커밋으로 기록
$ git commit -m "first commit"
# -> [master (root-commit) e89fecf] 2 files changed, 16 insertions(+) 생성 완료

# 7. 성공적으로 생성된 커밋 이력 및 작성자/날짜 정보 확인
$ git log
# -> commit e89fecf2dfa2ee79d1015f2151ee4c713cff1baa (HEAD -> master)
# -> Author: dykang <kangdayoung030102@gmail.com>
# -> Date: Tue Sep 22 14:50:28 2026 +0900
# -> first commit
```
실제 실습 화면

<img width="686" height="407" alt="스크린샷 2026-09-22 150055" src="https://github.com/user-attachments/assets/d670fa27-812d-4e69-bccc-61672eec7d72" />
<img width="545" height="243" alt="스크린샷 2026-09-22 150113" src="https://github.com/user-attachments/assets/37ed35e8-aa5a-4cef-9d64-c638971325ff" />

---

## 🎯 오늘의 회고

### 1. 몰입도 및 소회
`git init`부터 `git add`, `git commit`으로 이어지는 Git의 기본 워크플로우를 실제 CLI 터미널 환경에서 직접 수행해 보았다. 파일이 생성된 후 `Untracked` 상태에서 `git add`를 통해 `Staging` 영역으로 올라가고, 최종적으로 `git commit`을 거쳐 버전 관리 이력(`git log`)으로 남는 일련의 라이프사이클을 직관적으로 이해할 수 있었다.

### 2. 내일을 위한 다짐 & 계획
- **개념 체화**: 오늘 생성한 로컬 저장소(`Git_prac`)를 GitHub 원격 저장소(Remote Repository)에 생성하고 `git remote add` 및 `git push`로 업로드해보기
- **내일의 학습 계획**: Git 3강 - Commit 이해하기 수강

---

#LGCNS #LGCNS7기 #LGCNS7기TIL #내일배움카드 #K-DT
