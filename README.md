# User Service

## Mục lục
- [Giới thiệu](#giới-thiệu)
- [Yêu cầu hệ thống](#yêu-cầu-hệ-thống)
- [Cài đặt](#cài-đặt)
- [Chạy ứng dụng](#chạy-ứng-dụng)
- [Cấu hình](#cấu-hình)
- [API](#api)
- [Test](#test)
- [Đóng góp](#đóng-góp)
- [Liên hệ](#liên-hệ)

## Giới thiệu
User Service là một microservice trong hệ thống e-commerce, chịu trách nhiệm quản lý thông tin người dùng (CRUD, phân trang, xác thực).

## Yêu cầu hệ thống
- Java 17+
- Gradle 7+
- MySQL/PostgreSQL
- Docker (nếu chạy bằng container)

## Cài đặt
```bash
git clone https://github.com/your-org/ecommerce.git
cd user-service
./gradlew build
```

## Chạy ứng dụng
```bash
./gradlew bootRun
```
Hoặc chạy bằng Docker:
```bash
docker build -t user-service .
docker run -p 8081:8081 user-service
```

## Cấu hình
- Sửa file `src/main/resources/application.yml` để cấu hình database, port, v.v.
- Các biến môi trường quan trọng:
    - `DB_HOST`
    - `DB_USER`
    - `DB_PASSWORD`

## API
- `GET /api/users` - Lấy danh sách người dùng (có phân trang)
- `POST /api/users` - Tạo mới người dùng
- `PUT /api/users/{id}` - Cập nhật thông tin người dùng
- `DELETE /api/users/{id}` - Xóa người dùng

Xem chi tiết tại [Swagger UI](http://localhost:8081/swagger-ui.html)

## Test
```bash
./gradlew test
```

## Đóng góp
- Fork repo, tạo branch mới, commit code, tạo pull request.
- Tuân thủ style code chuẩn Java.

## Liên hệ
- Email: your.email@example.com
- Zalo: 0123456789