package com.example.studentlistapp.repository;

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
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class StudentRepository {
    private ApiService apiService;
    private StudentDao studentDao;
    private MutableLiveData<List<Student>> studentList;

    public StudentRepository() {
        // Thêm header x-api-key vào OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("x-api-key", "reqres-free-v1")
                            .build();
                    return chain.proceed(newRequest);
                })
                .build();

        // Khởi tạo Retrofit với OkHttpClient
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(ApiService.class);

        studentDao = new StudentDao();
        studentList = new MutableLiveData<>(new ArrayList<>());
    }

    public MutableLiveData<List<Student>> getStudents() {
        // Gọi API
        apiService.getStudents().enqueue(new Callback<StudentResponse>() {
            @Override
            public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    studentList.setValue(response.body().getData());
                } else {
                    studentList.setValue(studentDao.getStudents());
                }
            }

            @Override
            public void onFailure(Call<StudentResponse> call, Throwable t) {
                studentList.setValue(studentDao.getStudents());
            }
        });
        return studentList;
    }
}