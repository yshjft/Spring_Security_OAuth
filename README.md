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
  * Google Login, [GET] /oauth2/authorization/google
  * Kakao Login, [GET] /oauth2/authorization/kakao
    * RESPONSE   
      * RESPONSE HEADER
      ```
      Set-Cookie: refreshToken = ~~~~
      ```
      * RESPONSE BODY
      ```
      {
         "status": 200,
         "message": "login success",
         "result": {
            "accessToken": "~~~~"
         }
      }
      ```

  * refresh access token, [GET] /api/auth/token/refresh
    * REQUEST
      * REQUEST HEADER
      ```
      Authorization : Bearer ~~~
      Cookie : refreshToken = ~~~
      ```
    * RESPONSE
      * RESPONSE BODY
      ```
      {
         "status": 200,
         "message": "token refreshed",
         "result": {
            "accessToken": "~~~"
          }
      }
      ```

  * logout, [GET]  /api/auth/sign-out
    * RESPONSE
      * RESPONSE BODY
      ```
      {
         "status": 200,
         "message": "logout success"
      }
      ```

* user
  * 공통 REQUEST HEADER
  ```
  Authorization : Bearer ~~~
  Cookie : refreshToken = ~~~
  ```
  * 사용자 정보 조회, [GET] /api/users/info    
    * RESPONSE BODY
    ```
    {
       "status": 200,
       "message": "get user info success",
       "result": {
          "name": "~~~~",
          "email": "~~~~~",
          "profile_image": "~~~~~~"
       }
    }
    ```
  * 사용자 탈퇴, [DELETE] /api/users/info
    * RESPONSE BODY
    ```
    {
       "status": 200,
       "message": "delete user successfully. bye."
    }
    ```
  
* memo
  * 공통 REQUEST HEADER 
  ```
  Authorization : Bearer ~~~
  Cookie : refreshToken = ~~~
  ```
  * memo 작성, [POST] /api/memos
    * REQUEST BODY
    ```
    {
       "memo":"메모를 작성해 봅시다!!"
    }
    ```
    * RESPONSE BODY
    ```
    {
       "status": 201,
       "message": "write memo successfully",
       "result": {
           "id": 18
        }
    }
    ```
  * memo 전체 조회, [GET] /api/memos?page={page}&perPage={perPage}
    * RESPONSE BODY
    ```
    {
       "status": 200,
       "message": "get memos successfully",
       "result": {
           "metaData": {
               "page": 0,
               "totalPage": 1,
               "perPage": 5,
               "total": 1
           },
           "memos": [
               {
                   "id": 18,
                   "memo": "메모를 작성해 봅시다!!",
                   "createdAt": "2022-03-07T13:58:22.080074",
                   "updatedAt": "2022-03-07T13:58:22.080074"
               }
           ]
       }
    }
    ```
    
  * memo 개별 조회, [GET] /api/memos/{memoId}
    * RESPONSE BODY
    ```
    {
       "status": 200,
       "message": "get memo(id:18) successfully.",
       "result": {
           "id": 18,
           "memo": "메모를 작성해 봅시다!!",
           "createdAt": "2022-03-07T13:58:22.080074",
           "updatedAt": "2022-03-07T13:58:22.080074"
       }
    }
    ```
  * memo 수정, [PUT] /api/memos/{memoId}
    * RESPONSE BODY
    ```
    {
       "status": 200,
       "message": "update memo(id:18) successfully.",
       "result": {
           "id": 18
       }
    }
    ```
  * memo 삭제, [DELETE] /api/memos/{memoId}
    * RESPONSE BODY
    ```
    {
       "status": 200,
       "message": "delete memo(id:18) successfully.",
       "result": {
           "id": 18
       }
    }  
    ```


* ERROR
  * RESPONSE  
    * E40001   
      * 잘못된 입력이 있는 경우 발생하는 에러입니다.   
      * MethodArgumentNotValidException.class(equestBody), org.springframework.validation.BindException.class(ModelAttribute)
    * E40101, E40102, E40103, E40104, E40105
      * 인증과 관련해 발생하는 에러입니다.

       | ERROR CODE    |                         DESCRIPTION                         |
       |:-----------------------------------------------------------:|:------------:|
       | E40101  |   인증하지 않은 상태에서 접근하는 경우, UnAuthorizedAccessException.class   |
       | E40102 |      access token이 만료된 경우, ExpiredTokenException.class      |
       | E40103  | access token이 잘못된 경우(다시 로그인 해야함), WrongTokenException.class |  
       | E40104  |  refresh token이 잘못된 경우(다시 로그인 해야함), InvalidRefreshTokenException.class  |
  
    * E40301   
      * 권한이 없는 정보에 접근하는 경우 발생하는 엘러입니다.  
      * MemoAccessDeniedException.class

    * E40401, E40402

      | ERROR CODE      |                 DESCRIPTION                  |   
      |:--------------------------------------------:|:------------------:|   
      | E40401 | 사용자를 찾을 수 없는 경우, UserNotFoundException.class |   
      | E40402 |    메모를 찾을 수 없는 경우, MemoNotFoundException.class    |   
    
    * ERROR RESPONSE FORMAT
      ```
      {
        "status": "",
        "code": "",
        "message": "", 
        "errors": [
          {
            "field":"",
            "value":"",
            "reason":"",
          },
          ...
        ]
      }
      ```
      * status: 상태 코드
      * code: 에러 코드
      * message: 에러 메시지
      * errors: 에러가 발생한 입력값(없는 경우 비어있는 배열 반환)
        * field: 구체적인 위치
        * value: 입력값
        * reason: 발생한 이유



