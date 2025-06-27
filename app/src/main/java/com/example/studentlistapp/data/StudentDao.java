package com.example.studentlistapp.data;

import com.example.studentlistapp.model.Student;

import java.util.ArrayList;
import java.util.List;

// Class DAO giả lập để trả về danh sách sinh viên
public class StudentDao {
    // Hàm trả về danh sách sinh viên mẫu
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        // Dữ liệu giả lập (sẽ được thay bằng API trong Repository)
        students.add(new Student(1, "george.bluth@reqres.in", "George", "Bluth", "https://reqres.in/img/faces/1-image.jpg"));
        students.add(new Student(2, "janet.weaver@reqres.in", "Janet", "Weaver", "https://reqres.in/img/faces/2-image.jpg"));
        return students;
    }
}