package com.example.studentlistapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.studentlistapp.api.ApiService;
import com.example.studentlistapp.model.Student;
import com.example.studentlistapp.model.StudentResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentRepository {
    private ApiService apiService;
    private MutableLiveData<List<Student>> studentList;

    public StudentRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        studentList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Student>> getStudents() {
        apiService.getStudents().enqueue(new Callback<StudentResponse>() {
            @Override
            public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    studentList.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<StudentResponse> call, Throwable t) {
                studentList.setValue(null);
            }
        });
        return studentList;
    }
}