package com.example.studentlistapp.api;

import com.example.studentlistapp.model.StudentResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("users?page=1")
    Call<StudentResponse> getStudents();
}