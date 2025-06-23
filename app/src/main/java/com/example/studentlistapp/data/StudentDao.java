package com.example.studentlistapp.data;

import com.example.studentlistapp.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    // Giả lập dữ liệu từ API
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        // Dữ liệu mẫu, sẽ thay bằng API thực tế
        students.add(new Student(1, "john@example.com", "John", "Doe", "https://reqres.in/img/faces/1-image.jpg"));
        students.add(new Student(2, "jane@example.com", "Jane", "Smith", "https://reqres.in/img/faces/2-image.jpg"));
        return students;
    }
}