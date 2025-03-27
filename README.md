# TiTi
스케줄 기반 교통 정보 조회 서비스
> 기존에 교통 정보 확인을 위해 별도의 앱을 들어가야 했던 불편함을 해결하고, 스케줄의 교통 정보를 알림, 위젯 등으로 편리하게 조회할 수 있는 서비스 

### 프로젝트에 새롭게 적용한 기술
1. 기능단위로 분리된 멀티 모듈
2. CallAdapter을 사용한 보일러 플레이트 코드 최소화 및 예외 처리
4. AuthInterceptor를 이용한 Token 처리  
   https://velog.io/@daeyoung2/Android-Token-Flow
6. Compose Widget - glance
7. Compose navigation type safe
8. Android Unit Test  
   https://velog.io/@daeyoung2/Android-Test-Code%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BD%94%EB%93%9C

## Project Structure
![Image](https://github.com/user-attachments/assets/69943e33-e342-48fa-99fa-d87f39904d4a)

## Tech Stack
| Tech Stack      |                                                                 |
|----------------|-----------------------------------------------------------------|
| **Architecture**  | MVVM, Clean Architecture                                      |
| **Compose**      | Navigation, Glance                                           |
| **DI**          | Hilt                                                          |
| **Network**     | Retrofit2, OkHttp3                                            |
| **Asynchronous** | Coroutine, Flow                                              |
| **Jetpack**     | Room, DataStore, ViewModel                                    |
| **ETC**        | Kakao SDK, Firebase Cloud Messaging, Firebase Analytics        |
