우리주변게임친구 서버
---
`Spring boot 3.0.3`, `spring security`, `QueryDsl 5.0.0`, `firebase 9.1.1`, `mariadb`, `jjwt:0.9.1`

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

### Jwt
**application.yml**
```text
jwt:
  password: {jwt secret key}
```

