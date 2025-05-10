# mvc 미션

## 1단계
### 기능 설명
-  / 요청 시 메인 페이지(home.html)가 출력됩니다.

## 2단계
- /reservation: 예약 관리 페이지 (reservation.html) 응답
- /reservations: 예약 데이터 JSON 응답

## 3단계 - 예약 등록
- `POST /reservations`: 새로운 예약 정보를 등록합니다.
    - 요청 본문에 예약자의 이름, 날짜, 시간이 포함됩니다.
    - 성공 시 `201 Created` 응답 및 Location 헤더 반환

## 4단계 - 예외 처리
- 클라이언트 요청의 문제로 예외가 발생할 경우 `400 Bad Request`를 반환합니다.
- 처리되는 예외 예시:
  - 예약 입력값 누락 (name, date, time 중 하나라도 누락) 
  - 존재하지 않는 예약 ID로 삭제 요청
  - JSON 파싱 오류 (날짜/시간 형식이 올바르지 않음)

# 📌 API 명세서

---

##  GET /reservations

- 설명: 모든 예약 목록 조회
- 응답: `200 OK`
- 응답 본문 예시:

```json
[
  {
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
  },
  {
    "id": 2,
    "name": "죠르디",
    "date": "2023-08-05",
    "time": "17:00"
  }
  // ... 더 많은 예약
]

```
| 필드     | 타입                    | 설명     |
| ------ | --------------------- | ------ |
| `id`   | `number`              | 예약 식별자 |
| `name` | `string`              | 예약자 이름 |
| `date` | `string (YYYY-MM-DD)` | 예약 날짜  |
| `time` | `string (HH:mm)`      | 예약 시간  |

---
## POST /reservations
- 설명: 예약 추가
- 응답: `201 Created`
- 응답 본문 예시:
``` json
  {
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
  }
```
| 필드     | 타입                    | 필수 여부 | 설명     |
| ------ | --------------------- | ----- | ------ |
| `name` | `string`              | ✅     | 예약자 이름 |
| `date` | `string (YYYY-MM-DD)` | ✅     | 예약 날짜  |
| `time` | `string (HH:mm)`      | ✅     | 예약 시간  |
---
## DELETE /reservations/{id}
- 설명: 예약 삭제
- 응답: `204 No Content`
- 응답 본문 예시: 없음

---

## ❗ 예외 응답 명세

### 잘못된 요청 (필드 누락 등)

- 상태 코드: `400 Bad Request`
- 발생 조건:
  - 예약 요청에서 name, date, time 중 하나라도 누락되거나
  - 빈 문자열 또는 null 값이 들어온 경우

응답 예시:
```json
{
  "code": "BAD_REQUEST",
  "message": "이름은 필수입니다."
}
```
### 존재하지 않는 예약 ID
- 상태 코드: `400 Bad Request`
- 발생 조건: 존재하지 않는 ID를 가진 예약을 삭제할 때

응답 예시:
```json
{
"code": "NOT_FOUND_RESERVATION",
"message": "해당 예약이 존재하지 않습니다."
}
```

### JSON 파싱 오류
- 상태 코드: `400 Bad Request`
- 발생 조건: 요청 JSON이 잘못된 형식일 경우

응답 예시:
```json
{
  "code": "INVALID_JSON",
  "message": "형식이 옳바르지 않습니다"
}
```

### 존재하는 ID 중복 예약
- 상태 코드: `409 CONFLICT`
- 발생 조건: 중복된 ID의 Reservation을 예약할 경우

응답 예시:
```json
{
  "code": "RESERVATION_ALREADY_EXISTS",
  "message": "이미 ID가 존재하는 예약은 저장할 수 없습니다.
}
```

### 예상하지 못한 오류
- 상태 코드: `500 Internal Server Error`
- 발생 조건: 서버 내부에서 예상하지 못한 예외가 발생한 경우

응답 예시:
```json
{
  "code": "INTERNAL_SERVER_ERROR",
  "message": "예상치 못한 오류 발생"
}
```





