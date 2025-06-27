package com.example.studentlistapp.api;

import com.example.studentlistapp.model.StudentResponse;
import retrofit2.Call;
import retrofit2.http.GET;

// Interface định nghĩa các API endpoint
public interface ApiService {
    // Gọi API GET để lấy danh sách sinh viên
    @GET("users?page=1")
    Call<StudentResponse> getStudents();
}