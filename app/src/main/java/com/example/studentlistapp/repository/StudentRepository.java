package com.example.studentlistapp.repository;

import android.content.Context;
import android.os.AsyncTask;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import com.example.studentlistapp.api.ApiService;
import com.example.studentlistapp.data.AppDatabase;
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

    public StudentRepository(Context context) {
        // Khởi tạo Retrofit với header
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("x-api-key", "reqres-free-v1")
                            .build();
                    return chain.proceed(newRequest);
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(ApiService.class);

        // Khởi tạo Room Database
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "student-database")
                .build();
        studentDao = db.studentDao();

        studentList = new MutableLiveData<>(new ArrayList<>());
    }

    public MutableLiveData<List<Student>> getStudents() {
        // AsyncTask để kiểm tra và lấy dữ liệu từ Room
        new AsyncTask<Void, Void, List<Student>>() {
            @Override
            protected List<Student> doInBackground(Void... voids) {
                // Kiểm tra Room
                if (studentDao.getStudentCount() > 0) {
                    return studentDao.getAllStudents();
                }
                return null; // Room rỗng
            }

            @Override
            protected void onPostExecute(List<Student> localStudents) {
                if (localStudents != null && !localStudents.isEmpty()) {
                    studentList.setValue(localStudents);
                } else {
                    // Gọi API nếu Room rỗng
                    apiService.getStudents().enqueue(new Callback<StudentResponse>() {
                        @Override
                        public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                            if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                                List<Student> apiStudents = response.body().getData();
                                // Lưu vào Room bằng AsyncTask
                                new AsyncTask<Void, Void, Void>() {
                                    @Override
                                    protected Void doInBackground(Void... voids) {
                                        studentDao.insert(apiStudents);
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Void aVoid) {
                                        studentList.setValue(apiStudents);
                                    }
                                }.execute();
                            } else {
                                // Lấy từ DAO giả lập nếu API thất bại
                                List<Student> daoStudents = getDaoStudents();
                                new AsyncTask<Void, Void, Void>() {
                                    @Override
                                    protected Void doInBackground(Void... voids) {
                                        studentDao.insert(daoStudents);
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Void aVoid) {
                                        studentList.setValue(daoStudents);
                                    }
                                }.execute();
                            }
                        }

                        @Override
                        public void onFailure(Call<StudentResponse> call, Throwable t) {
                            // Lấy từ DAO giả lập nếu API lỗi
                            List<Student> daoStudents = getDaoStudents();
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    studentDao.insert(daoStudents);
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    studentList.setValue(daoStudents);
                                }
                            }.execute();
                        }
                    });
                }
            }
        }.execute();
        return studentList;
    }

    // DAO giả lập
    private List<Student> getDaoStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "george.bluth@reqres.in", "George", "Bluth", "https://reqres.in/img/faces/1-image.jpg"));
        students.add(new Student(2, "janet.weaver@reqres.in", "Janet", "Weaver", "https://reqres.in/img/faces/2-image.jpg"));
        return students;
    }

    public void addStudent(Student student) {
        new AsyncTask<Void, Void, List<Student>>() {
            @Override
            protected List<Student> doInBackground(Void... voids) {
                studentDao.insertStudent(student);
                return studentDao.getAllStudents();
            }

            @Override
            protected void onPostExecute(List<Student> updatedList) {
                studentList.setValue(updatedList);
            }
        }.execute();
    }

    public void deleteStudent(Student student) {
        new AsyncTask<Void, Void, List<Student>>() {
            @Override
            protected List<Student> doInBackground(Void... voids) {
                studentDao.delete(student);
                return studentDao.getAllStudents();
            }

            @Override
            protected void onPostExecute(List<Student> updatedList) {
                studentList.setValue(updatedList);
            }
        }.execute();
    }

    public void updateStudent(Student student) {
        new AsyncTask<Void, Void, List<Student>>() {
            @Override
            protected List<Student> doInBackground(Void... voids) {
                studentDao.update(student);
                return studentDao.getAllStudents();
            }

            @Override
            protected void onPostExecute(List<Student> updatedList) {
                studentList.setValue(updatedList);
            }
        }.execute();
    }
}