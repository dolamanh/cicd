# CI/CD Demo App cho Android

Đây là một dự án mẫu minh họa cách thiết lập quy trình CI/CD đầy đủ cho ứng dụng Android với GitHub Actions.

## Cấu trúc dự án

Dự án được tổ chức với cấu trúc tiêu chuẩn của Android Studio và sử dụng các tính năng sau:

- **Jetpack Compose**: Xây dựng giao diện người dùng hiện đại
- **Kotlin**: Ngôn ngữ lập trình chính
- **Gradle Kotlin DSL**: Cấu hình build
- **Product Flavors**: Hỗ trợ nhiều môi trường (dev, qa, prod)
- **GitHub Actions**: Tự động hóa quy trình CI/CD

## Quy trình CI/CD

Dự án này sử dụng GitHub Actions để tự động hóa các quy trình sau:

### Kiểm thử & Phân tích

- Lint kiểm tra để phân tích mã nguồn
- Unit tests trên JVM
- Instrumentation tests trên thiết bị hoặc máy ảo Android

### Build & Đóng gói

- Debug build cho phát triển nội bộ
- Release build có ký số
- AAB bundle để phân phối trên Google Play

### Triển khai

- Triển khai các bản debug lên Firebase App Distribution cho tester nội bộ
- Triển khai các bản release lên Google Play (alpha/beta)

## Cài đặt môi trường phát triển

1. Clone repository này
2. Mở dự án với Android Studio
3. Đồng bộ Gradle để tải các phụ thuộc
4. Tạo file `keystore.properties` với thông tin ký ứng dụng của bạn (hoặc sử dụng file mẫu cho môi trường phát triển)

## Biến môi trường cho CI/CD

Để thiết lập GitHub Actions, cần định nghĩa các biến môi trường sau trong repository secrets:

- `KEYSTORE_BASE64`: Keystore được mã hóa Base64
- `KEYSTORE_KEY_ALIAS`: Alias của khóa trong keystore
- `KEYSTORE_KEY_PASSWORD`: Mật khẩu của khóa
- `KEYSTORE_STORE_PASSWORD`: Mật khẩu của keystore
- `FIREBASE_APP_ID`: ID ứng dụng Firebase
- `FIREBASE_SERVICE_CREDENTIALS`: Thông tin xác thực cho Firebase

## Hướng dẫn build thủ công

### Debug build

```bash
./gradlew assembleDebug
```

### Release build

```bash
./gradlew assembleRelease
```

### Build cho từng môi trường

```bash
# Môi trường phát triển
./gradlew assembleDevDebug

# Môi trường QA
./gradlew assembleQaDebug

# Môi trường production
./gradlew assembleProdRelease
```

## Bảo trì & Gỡ lỗi CI/CD

### Các vấn đề thường gặp & giải pháp

1. **Lỗi ký ứng dụng trong CI**
   - Kiểm tra các biến môi trường đã được thiết lập đúng chưa
   - Đảm bảo keystore được mã hóa đúng định dạng

2. **Lỗi instrumentation test**
   - Kiểm tra logs từ Firebase Test Lab
   - Đảm bảo các thiết bị test tương thích với ứng dụng của bạn

3. **Thời gian build quá lâu**
   - Tối ưu hóa cache trong GitHub Actions
   - Sử dụng các tùy chọn tăng tốc Gradle

## Đóng góp

Đóng góp luôn được chào đón! Vui lòng tạo issue hoặc pull request để cải thiện dự án.
