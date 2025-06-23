package com.example.studentlistapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.studentlistapp.model.Student;
import com.example.studentlistapp.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StudentViewModel extends ViewModel {
    private StudentRepository repository;
    private MutableLiveData<List<Student>> studentList;
    private MutableLiveData<Boolean> isLoading;
    private Student lastDeletedStudent;
    private int lastDeletedPosition;

    public StudentViewModel() {
        repository = new StudentRepository();
        studentList = new MutableLiveData<>();
        isLoading = new MutableLiveData<>(false);
        loadStudents();
    }

    public LiveData<List<Student>> getStudentList() {
        return studentList;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private void loadStudents() {
        isLoading.setValue(true);
        new android.os.Handler().postDelayed(() -> {
            studentList.setValue(repository.getStudents().getValue());
            isLoading.setValue(false);
        }, 2000);
    }

    public void addStudent(String firstName, String lastName, String email) {
        List<Student> currentList = studentList.getValue() != null ? new ArrayList<>(studentList.getValue()) : new ArrayList<>();
        int newId = currentList.size() + 1;
        String avatar = "https://reqres.in/img/faces/" + newId + "-image.jpg";
        currentList.add(new Student(newId, email, firstName, lastName, avatar));
        studentList.setValue(currentList);
    }

    public void deleteStudent(Student student, int position) {
        List<Student> currentList = new ArrayList<>(studentList.getValue());
        lastDeletedStudent = student;
        lastDeletedPosition = position;
        currentList.remove(student);
        studentList.setValue(currentList);
    }

    public void undoDelete() {
        if (lastDeletedStudent != null) {
            List<Student> currentList = new ArrayList<>(studentList.getValue());
            currentList.add(lastDeletedPosition, lastDeletedStudent);
            studentList.setValue(currentList);
            lastDeletedStudent = null;
        }
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