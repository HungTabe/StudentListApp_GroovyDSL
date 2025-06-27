package com.example.studentlistapp.model;

import java.util.List;

// Class để xử lý response từ API
public class StudentResponse {
    private List<Student> data; // Danh sách sinh viên từ trường "data" trong JSON

    // Getter
    public List<Student> getData() {
        return data;
    }
}