# Health Data Backend

## 📌 프로젝트 개요
이 프로젝트는 **사용자의 건강 데이터를 수집 및 저장**하는 백엔드 시스템입니다. 데이터를 **Samsung Health 또는 Apple Health**에서 App-to-App 방식으로 수집하여 서버에 저장하고, 이를 기반으로 사용자의 활동 기록을 조회할 수 있도록 합니다.

---

## 📊 ERD 설계

### **1. HealthRecord (건강 기록 테이블)**
사용자의 건강 데이터를 하나의 레코드 단위로 저장하는 테이블입니다.

| 필드명 | 타입 | 설명 |
|--------|----------|--------------------------------|
| `id` | `BIGINT` | 기본 키 (자동 증가) |
| `record_key` | `VARCHAR(255)` | 레코드 고유 키 (중복 불가) |
| `created_at` | `DATETIME` | 레코드 생성 시간 |


### **2. HealthEntry (건강 데이터 상세 기록)**
각 HealthRecord에 여러 개의 건강 데이터가 연결됩니다.

| 필드명 | 타입 | 설명 |
|--------|----------|-------------------------------|
| `id` | `BIGINT` | 기본 키 (자동 증가) |
| `health_record_id` | `BIGINT` | `HealthRecord`의 FK |
| `period_from` | `DATETIME` | 데이터 기록 시작 시간 |
| `period_to` | `DATETIME` | 데이터 기록 종료 시간 |
| `distance_unit` | `VARCHAR(10)` | 거리 단위 (예: km) |
| `distance_value` | `DOUBLE` | 이동 거리 값 |
| `calories_unit` | `VARCHAR(10)` | 칼로리 단위 (예: kcal) |
| `calories_value` | `DOUBLE` | 소모 칼로리 값 |
| `steps` | `INT` | 걸음 수 |


---

## 🔌 API 명세

### **1. 건강 기록 저장 API**
- **URL:** `POST /health/record`
- **설명:** JSON 데이터를 받아 `HealthRecord` 및 `HealthEntry` 테이블에 저장합니다.
- **요청 예시:**
  ```json
  {
    "recordkey": "7836887b-b12a-440f-af0f-851546504b13",
    "data": {
        "entries": [
            {
                "period": { "from": "2024-11-15 00:00:00", "to": "2024-11-15 00:10:00" },
                "distance": { "unit": "km", "value": 0.04223 },
                "calories": { "unit": "kcal", "value": 2.03 },
                "steps": 54
            }
        ]
    }
  }
  ```

### **2. 특정 기록 조회 API**
- **URL:** `GET /health/record/{id}`
- **설명:** `id`에 해당하는 건강 기록을 조회합니다.

### **3. 일별 건강 데이터 조회 API**
- **URL:** `GET /health/record/daily/{recordKey}?date=YYYY-MM-DD`
- **설명:** 특정 날짜의 건강 데이터를 조회합니다.

### **4. 월별 건강 데이터 조회 API**
- **URL:** `GET /health/record/monthly/{recordKey}?year=YYYY&month=MM`
- **설명:** 특정 월의 건강 데이터를 조회합니다.


---

## 🛠 기술 스택
- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA (MySQL 8.x)**
- **Redis (캐싱용)**
- **Kafka (비동기 처리)**
- **Docker & Docker Compose**


---

## 🚀 실행 방법
### **1. Docker 컨테이너 실행 (MySQL, Redis, Kafka)**
```sh
docker-compose up -d
```

### **2. 애플리케이션 실행**
```sh
./mvnw spring-boot:run
```

### **3. API 테스트**
Postman 또는 `curl`을 이용해 API를 테스트할 수 있습니다.
```sh
curl -X GET "http://localhost:8080/health/record/1"
```

---

# 데이터 조회 결과 제출 (Daily/Monthly 레코드키 기준)

## 📌 Daily Summary (일별 요약)
| Date       | RecordKey                              | Steps  | Calories | Distance (km) |
|------------|---------------------------------------|--------|----------|--------------|
| 2024-11-14 | 7b012e6e-ba2b-49c7-bc2e-473b7b58e72e | 2231   | 0.00     | 1.79         |
| 2024-11-14 | e27ba7ef-8bb2-424c-af1d-877e826b7487 | 2077   | 0.00     | 1.66         |
| 2024-11-15 | 3b87c9a4-f983-4168-8f27-85436447bb57 | 9589   | 345.36   | 7.24         |
| 2024-11-15 | 7836887b-b12a-440f-af0f-851546504b13 | 7243   | 289.21   | 5.42         |


---

## 📌 Monthly Summary (월별 요약)
| Month    | RecordKey                              | Steps   | Calories  | Distance (km) |
|----------|---------------------------------------|---------|----------|--------------|
| 2024-11  | 3b87c9a4-f983-4168-8f27-85436447bb57 | 130945  | 4671.77  | 100.72       |
| 2024-11  | 7836887b-b12a-440f-af0f-851546504b13 | 124783  | 5002.50  | 94.34        |
| 2024-12  | 3b87c9a4-f983-4168-8f27-85436447bb57 | 130551  | 4560.13  | 101.10       |
| 2024-12  | 7836887b-b12a-440f-af0f-851546504b13 | 115592  | 4635.84  | 87.38        |


