package com.example.studentlistapp.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.studentlistapp.api.ApiService;
import com.example.studentlistapp.data.StudentDao;
import com.example.studentlistapp.model.Student;
import com.example.studentlistapp.model.StudentResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Repository quản lý việc lấy dữ liệu từ DAO hoặc API
public class StudentRepository {
    private ApiService apiService; // Dùng để gọi API
    private StudentDao studentDao; // DAO giả lập
    private MutableLiveData<List<Student>> studentList; // Lưu danh sách sinh viên

    // Constructor
    public StudentRepository() {
        // Khởi tạo Retrofit để gọi API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/") // URL cơ sở của API
                .addConverterFactory(GsonConverterFactory.create()) // Chuyển JSON thành object
                .build();
        apiService = retrofit.create(ApiService.class);

        // Khởi tạo DAO giả lập
        studentDao = new StudentDao();

        // Khởi tạo LiveData với danh sách rỗng
        studentList = new MutableLiveData<>(new ArrayList<>());
    }

    // Hàm lấy danh sách sinh viên (sử dụng DAO giả lập)
    public MutableLiveData<List<Student>> getStudents() {
        studentList.setValue(studentDao.getStudents()); // Lấy dữ liệu từ DAO
        return studentList;
    }

    // Hàm lấy danh sách sinh viên
//    public MutableLiveData<List<Student>> getStudents() {
//        // Gọi API để lấy dữ liệu
//        apiService.getStudents().enqueue(new Callback<StudentResponse>() {
//            @Override
//            public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
//                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
//                    Log.d("StudentRepository", "API success: " + response.body().getData().size() + " students");
//                    // Lấy danh sách sinh viên từ response
//                    studentList.setValue(response.body().getData());
//                } else {
//                    Log.d("StudentRepository", "API failed, using DAO");
//                    // Nếu API lỗi, dùng dữ liệu từ DAO giả lập
//                    studentList.setValue(studentDao.getStudents());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<StudentResponse> call, Throwable t) {
//                // Nếu gọi API thất bại, dùng dữ liệu từ DAO giả lập
//                studentList.setValue(studentDao.getStudents());
//            }
//        });
//        return studentList;
//    }
}