# BÀI 4: TỐI ƯU HÓA CẤU TRÚC GIAO DIỆN (DRY)

## 1. Bảng so sánh 2 cách tiếp cận cấu trúc giao diện

| Tiêu chí | (A) Copy-paste Header/Footer (Thủ công) | (B) Sử dụng LayoutDialect (`layout:decorate`) |
| :--- | :--- | :--- |
| **Tính lặp đoạn mã (DRY)** | Vi phạm nguyên tắc DRY. Header, Footer, Navbar bị lặp lại ở mọi trang file HTML. | Tuân thủ nguyên tắc DRY (Don't Repeat Yourself). Các thành phần dùng chung cấu hình ở một nơi duy nhất. |
| **Bảo trì và Cập nhật** | Rất khó bảo trì. Một thay đổi nhỏ ở Header (ví dụ như đổi logo) thì phải sửa ở tất cả các file HTML có tồn tại Header đó. | Rất dễ bảo trì. Thay đổi ở layout gốc (`main-layout.html`) sẽ tự động áp dụng cho mọi trang con kế thừa nó. |
| **Độ phức tạp ban đầu** | Đơn giản, trực quan, không cần cài đặt thêm thư viện hoặc cấu hình. | Phức tạp hơn ở giai đoạn đầu, yêu cầu cấu hình thêm thư viện thứ 3 (`ultraq layout-dialect`) và cấu hình Bean. |
| **Hiệu suất phát triển dự án lớn**| Kém. Càng nhiều trang mã nguồn càng thành mớ hỗn độn. | Rất tốt. Cấu trúc module hoá sạch sẽ, tập trung vào nội dung trang (Fragment). |

---

## 2. Phân tích kỹ thuật: Tại sao phải đăng ký Bean LayoutDialect?

Khi sử dụng Layout Dialect, nếu trong Java (SpringTemplateEngine) mà bạn **không đăng ký** LayoutDialect bean, thì Thymeleaf sẽ không nhận diện được các thuôc tính `layout:decorate` hay `layout:fragment`.

**Lý do cốt lõi:**
1. **Kiến trúc mở rộng (Pluggable Architecture) của Thymeleaf:** Thymeleaf mặc định chỉ đóng gói kèm `StandardDialect` (`th:text`, `th:each`, `th:if`,...). Các thẻ và tính năng không thuộc chuẩn thì template engine sẽ tự động bỏ qua.
2. **LayoutDialect là thư viện của bên thứ 3:** Tính năng layout không nằm trong lõi của Thymeleaf mà thuộc về project độc lập (`nz.net.ultraq.thymeleaf`). Do vậy, Spring không tự động biết đến sự tồn tại của nó trừ khi ta nạp thủ công.
3. **Quá trình Parsing Layout:** Để Thymeleaf biết cách bóc tách thẻ `layout:fragment` và đắp nội dung từ trang con lên thẻ `layout:decorate` của trang cha, `LayoutDialect` cung cấp các Processor đặc thù can thiệp sâu vào pha xử lý của TemplateEngine. Việc inject `LayoutDialect` bean vào `SpringTemplateEngine.addDialect()` chính là việc chúng ta nói với công cụ render: *"Hãy tải bộ xử lý layout này để biết cách cắt ghép/merge các thẻ HTML có tiền tố layout: lại với nhau!"*
