package com.example.studentlistapp.viewmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.studentlistapp.model.Student;
import com.example.studentlistapp.repository.StudentRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentViewModel extends ViewModel {
    private StudentRepository repository;
    private MutableLiveData<List<Student>> studentList;
    private MutableLiveData<Boolean> isLoading;

    public StudentViewModel(Context context) {
        repository = new StudentRepository(context);
        studentList = new MutableLiveData<>(new ArrayList<>());
        isLoading = new MutableLiveData<>(true);
        loadStudents();
    }

    public LiveData<List<Student>> getStudentList() {
        return studentList;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private void loadStudents() {
        repository.getStudents().observeForever(students -> {
            new android.os.Handler().postDelayed(() -> {
                studentList.setValue(students != null ? students : new ArrayList<>());
                isLoading.setValue(false);
            }, 2000);
        });
    }

    public void addStudent(String firstName, String lastName, String email) {
        int newId = studentList.getValue().size() + 1;
        String avatar = "https://reqres.in/img/faces/" + newId + "-image.jpg";
        Student student = new Student(newId, email, firstName, lastName, avatar);
        repository.addStudent(student);
    }

    public void deleteStudent(Student student) {
        repository.deleteStudent(student);
    }

    public void updateStudent(Student student, String newEmail) {
        student.setEmail(newEmail);
        repository.updateStudent(student);
    }

    public void sortStudents() {
        List<Student> currentList = new ArrayList<>(studentList.getValue());
        Collections.sort(currentList, (s1, s2) -> {
            String name1 = s1.getFirst_name() + " " + s1.getLast_name();
            String name2 = s2.getFirst_name() + " " + s2.getLast_name();
            return name1.compareTo(name2);
        });
        studentList.setValue(currentList);
    }
}