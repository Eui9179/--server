# 우리주변게임친구 서버

## 🚀 프로젝트 요약

<p align="center" width="100%">
    <img width="1283" alt="Frame 322(1)" src="https://user-images.githubusercontent.com/83222282/229736196-9d1d9899-3893-44b3-ad51-b04cb3c04e50.png">
</p>


📋 학교를 등록하고 학교 친구들과 오늘 할 게임을 공유하는 애플리케이션입니다.<br>
서로 팔로우하고 팔로우를 하면 친구가 하는 게임과 닉네임을 쉽게 알 수 있고 오늘 무슨 게임을 할지<br>‘오늘의 게임’을 통해 쉽게 알 수 있게 도와줍니다.

<br>

✅ 자세한 내용
https://eui9179.notion.site/f8cd37a3c45149098bb926b57709231e

<br>

📅 작업기간: 2022.7 ~ 2022.11

👨🏻‍💻 투입인원: 2명

💻 주요업무

- 개발 전담
    - SpringBoot REST API 서버 개발
    - flutter 앱 개발

🔧 스킬 및 사용툴

 `Spring boot 3.0.3`, `QueryDsl 5.0.0`, `firebase 9.1.1`
 

## Getting Stated

### Firebase Setting

- resources

```text
Firebase Admin SDK `.json`파일 추가
```

- application.properties

```text
project.properties.firebase-create-scoped: "https://www.googleapis.com/auth/firebase.messaging"
```

---

### Jwt
- application.properties

```text
jwt:
  password: {jwt secret key}
```

---

### Image file path
```text
file:
    path: {local folder path}/profile
```

---

### SMS Login

Naver SMS API 사용 <br>

- application-sms.properties 파일 추가

```text
sms:
  serviceId: {serviceId}
  accessKey: {accessKey}
  secretKey: {secretKey}
  sms.caller = {phone number}
```

---
