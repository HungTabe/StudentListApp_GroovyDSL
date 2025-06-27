package com.example.studentlistapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.studentlistapp.model.Student;
import com.example.studentlistapp.repository.StudentRepository;
import java.util.ArrayList;
import java.util.List;

public class StudentViewModel extends ViewModel {
    private StudentRepository repository;
    private MutableLiveData<List<Student>> studentList;
    private MutableLiveData<Boolean> isLoading;

    public StudentViewModel() {
        repository = new StudentRepository();
        studentList = new MutableLiveData<>(new ArrayList<>());
        isLoading = new MutableLiveData<>(true); // Bật loading ngay từ đầu
        loadStudents();
    }

    public LiveData<List<Student>> getStudentList() {
        return studentList;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private void loadStudents() {
        // Gọi API hoặc DAO từ Repository
        repository.getStudents().observeForever(students -> {
            // Giả lập thời gian tải 2 giây
            new android.os.Handler().postDelayed(() -> {
                studentList.setValue(students != null ? students : new ArrayList<>());
                isLoading.setValue(false); // Tắt loading sau khi có dữ liệu
            }, 2000);
        });
    }
}