```markdown
# StudentListApp

## Giới thiệu
`StudentListApp` là một ứng dụng Android được phát triển bằng **Java** trên **Android Studio**, sử dụng kiến trúc **MVVM** (Model-View-ViewModel). Ứng dụng hiển thị danh sách sinh viên, cho phép thêm, xóa, sắp xếp và lưu trữ dữ liệu bằng **Room Database**. Dự án này được thiết kế để đáp ứng bài thi thực hành lập trình Android với các yêu cầu cụ thể.

### Các tính năng
1. **Hiển thị danh sách sinh viên**: Lấy dữ liệu từ API giả lập (https://reqres.in/api/users?page=1) và hiển thị trong RecyclerView.
2. **Thêm sinh viên**: Cho phép nhập thông tin sinh viên mới (tên, họ, email) và cập nhật danh sách.
3. **Xóa sinh viên**: Xóa sinh viên khỏi danh sách với tùy chọn "Hoàn tác" qua Snackbar.
4. **Sắp xếp danh sách**: Sắp xếp sinh viên theo thứ tự alphabet (first_name + last_name).
5. **Lưu trữ dữ liệu**: Sử dụng Room Database để lưu và quản lý danh sách sinh viên.

### Công nghệ sử dụng
- **Ngôn ngữ**: Java
- **IDE**: Android Studio
- **Kiến trúc**: MVVM
- **Thư viện**:
  - Retrofit: Gọi API
  - Room: Lưu trữ dữ liệu
  - LiveData & ViewModel: Quản lý dữ liệu và UI
  - Glide: Tải ảnh
  - RecyclerView: Hiển thị danh sách
- **Build tool**: Gradle (Groovy DSL)

## Cài đặt và chạy ứng dụng

### Yêu cầu
- **Android Studio**: Phiên bản mới nhất (đề xuất 2023.1.1 trở lên).
- **JDK**: Phiên bản 8 hoặc cao hơn.
- **Thiết bị**: Emulator hoặc thiết bị Android (API 21 trở lên).
- **Kết nối Internet**: Để gọi API.

### Hướng dẫn cài đặt
1. **Clone repository**:
   git clone https://github.com/<your-username>/StudentListApp.git

2. **Mở dự án**:
   - Mở Android Studio, chọn `Open an existing project`.
   - Chọn thư mục `StudentListApp` vừa clone.
3. **Sync dự án**:
   - Nhấn `Sync Project with Gradle Files` trong Android Studio để tải các thư viện.
4. **Cấu hình emulator**:
   - Tạo một emulator với API 21 (Android 5.0) hoặc cao hơn.
   - Hoặc kết nối thiết bị Android thật qua USB Debugging.
5. **Chạy ứng dụng**:
   - Nhấn nút `Run` (hình tam giác xanh) trong Android Studio.
   - Chọn emulator hoặc thiết bị để chạy.

### Lưu ý
- Đảm bảo cấp quyền Internet trong `AndroidManifest.xml`:
  <uses-permission android:name="android.permission.INTERNET" />

- Nếu gặp lỗi Gradle, kiểm tra file `build.gradle` và đảm bảo kết nối mạng ổn định.

## Cấu trúc dự án

StudentListApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/studentlistapp/
│   │   │   │   ├── adapter/        # Adapter cho RecyclerView
│   │   │   │   ├── api/            # Interface và cấu hình API (Retrofit)
│   │   │   │   ├── data/           # Room Database và DAO
│   │   │   │   ├── model/          # Model dữ liệu (Student)
│   │   │   │   ├── repository/     # Repository xử lý dữ liệu
│   │   │   │   ├── viewmodel/      # ViewModel quản lý logic
│   │   │   │   └── MainActivity.java  # Activity chính
│   │   │   ├── res/
│   │   │   │   ├── layout/         # File XML giao diện
│   │   │   │   └── values/         # Tài nguyên (chuỗi, màu sắc)
│   ├── build.gradle                # Cấu hình Gradle module
├── build.gradle                    # Cấu hình Gradle project
├── .gitignore                      # File bỏ qua khi đẩy Git
└── README.md                       # File mô tả dự án


## Hướng dẫn sử dụng
1. **Mở ứng dụng**:
   - Danh sách sinh viên được tải từ Room Database (hoặc API ban đầu).
   - ProgressBar hiển thị trạng thái loading trong 2 giây.
2. **Thêm sinh viên**:
   - Nhập `First Name`, `Last Name`, `Email` vào các ô tương ứng.
   - Nhấn nút `Thêm` để thêm sinh viên mới.
   - Toast thông báo "Thêm sinh viên thành công!".
3. **Xóa sinh viên**:
   - Nhấn nút `Xóa` trên mỗi dòng trong danh sách.
   - Snackbar xuất hiện với tùy chọn `Hoàn tác` để khôi phục.
4. **Sắp xếp danh sách**:
   - Nhấn nút `Sắp xếp` để sắp xếp sinh viên theo tên (alphabet).
5. **Lưu trữ**:
   - Dữ liệu sinh viên được lưu vào Room Database, giữ nguyên khi mở lại ứng dụng.

## Kết quả
Dưới đây là các màn hình chính của ứng dụng (chụp màn hình khi chạy):
- **Danh sách sinh viên**: Hiển thị danh sách với ảnh, tên, email.
- **Thêm sinh viên**: Giao diện nhập liệu và Toast xác nhận.
- **Xóa sinh viên**: Snackbar với tùy chọn "Hoàn tác".
- **Sắp xếp**: Danh sách được sắp xếp theo tên.
- **Room Database**: Dữ liệu lưu trữ và tải lại từ database.

*(Lưu ý: Chụp màn hình khi chạy ứng dụng trên emulator hoặc thiết bị thật và nộp cùng source code.)*

## Tác giả
- **Tên**: Tran Dinh Hung
- **MSSV**: QE170194
- **Liên hệ**: [trandinhhung717@gmail.com]

