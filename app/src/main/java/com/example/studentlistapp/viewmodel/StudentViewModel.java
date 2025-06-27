package com.example.studentlistapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.studentlistapp.data.StudentDao;
import com.example.studentlistapp.model.Student;
import com.example.studentlistapp.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

// ViewModel quản lý dữ liệu và trạng thái loading
public class StudentViewModel extends ViewModel {
    private StudentRepository repository; // Repository cung cấp dữ liệu
    private MutableLiveData<List<Student>> studentList; // Danh sách sinh viên
    private MutableLiveData<Boolean> isLoading; // Trạng thái loading
    private StudentDao studentDao; // DAO giả lập

    // Constructor
    public StudentViewModel() {
        repository = new StudentRepository();
        studentList = new MutableLiveData<>(new ArrayList<>()); // Khởi tạo danh sách rỗng
        isLoading = new MutableLiveData<>(false);
        loadStudents(); // Tải dữ liệu khi khởi tạo
    }

    // Lấy danh sách sinh viên để Activity quan sát
    public LiveData<List<Student>> getStudentList() {
        return studentList;
    }

    // Lấy trạng thái loading để hiển thị ProgressBar
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    // Hàm tải danh sách sinh viên
    private void loadStudents() {
        isLoading.setValue(true); // Bật trạng thái loading
        // Lấy dữ liệu từ Repository
        List<Student> students = repository.getStudents().getValue();
        if (students != null) {
            studentList.setValue(students);
        } else {
            studentList.setValue(new ArrayList<>()); // Gán danh sách rỗng nếu null
        }
        // Giả lập thời gian tải dữ liệu (2 giây)
        new android.os.Handler().postDelayed(() -> {
            isLoading.setValue(false); // Tắt trạng thái loading
        }, 2000);
    }
}