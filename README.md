# Spring Security OAuth 2.0
#### Spring Security와 OAuth 2.0을 이용하여 인증과 간단한 CRUD api를 구현합니다.

## 기술 스택
* Spring Boot
* Spring Security
* OAuth 2.0
* h2
* redis

## 설명
### 인증
- Spring Security와 OAuth 2.0 그리고 jwt를 이용하여 구현하였습니다.
- Authorization Code Grant 방식을 이용하였습니다.
- Google 이나 Kakao 계정을 통한 로그인 이후에는 jwt를 이용한 인증 방식을 사용하였습니다.

### CRUD
- 간단한 내용을 생성, 조회, 수정, 삭제할 수 있도록 구현하였습니다.

## API
* auth    
  Google Login, [GET] /oauth2/authorization/google  
   * REQUEST   
   * RESPONSE
  
  Kakao Login, [GET] /oauth2/authorization/kakao
   * REQUEST
   * RESPONSE

  refresh access token, [GET] /api/auth/token/refresh
   * REQUEST
   * RESPONSE

  logout, [GET]  /api/auth/sign-out
   * REQUEST
   * RESPONSE
  
* user
* memo
* ERROR 



