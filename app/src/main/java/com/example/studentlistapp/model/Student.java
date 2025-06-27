package com.example.studentlistapp.model;

// Class Student đại diện cho một sinh viên
public class Student {
    private int id; // ID của sinh viên
    private String email; // Email
    private String first_name; // Tên
    private String last_name; // Họ
    private String avatar; // URL ảnh đại diện

    // Constructor
    public Student(int id, String email, String first_name, String last_name, String avatar) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
    }

    // Getter để lấy dữ liệu
    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getFirst_name() { return first_name; }
    public String getLast_name() { return last_name; }
    public String getAvatar() { return avatar; }
}