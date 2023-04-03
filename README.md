우리주변게임친구 서버
---
`Spring boot 3.0.3`, `spring security`, `QueryDsl 5.0.0`, `firebase 9.1.1`, `mariadb`, `jjwt:0.9.1`

---
### Swagger path
/docs

---

### Firebase Setting
**resources**
```text
Firebase Admin SDK `.json`파일 추가
```

**application.yml**
```text
project:
  properties:
    firebase-create-scoped: "https://www.googleapis.com/auth/firebase.messaging"
```

---

### Jwt
**application.yml**
```text
jwt:
  password: {jwt secret key}
```
---
### Image file path
```text
file:
    path: {local file path}
```
---
### SMS Login
Naver SMS API 사용 <br>
application-sms.properties 파일 추가
```text
sms:
  serviceId: {serviceId}
  accessKey: {accessKey}
  secretKey: {secretKey}
  sms.caller = {phone number}
```