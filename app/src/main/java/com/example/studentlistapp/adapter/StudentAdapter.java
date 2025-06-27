package com.example.studentlistapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studentlistapp.R;
import com.example.studentlistapp.model.Student;

import java.util.ArrayList;
import java.util.List;

// Adapter để hiển thị danh sách sinh viên trong RecyclerView
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private List<Student> students = new ArrayList<>(); // Khởi tạo danh sách rỗng

    // Cập nhật danh sách sinh viên
    public void setStudents(List<Student> students) {
        if (students != null) {
            this.students = students;
        } else {
            this.students = new ArrayList<>(); // Đảm bảo không gán null
        }
        notifyDataSetChanged(); // Thông báo RecyclerView cập nhật giao diện
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo view cho mỗi item từ layout item_student.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        // Lấy sinh viên tại vị trí position
        Student student = students.get(position);
        // Hiển thị tên và họ
        holder.tvName.setText(student.getFirst_name() + " " + student.getLast_name());
        // Hiển thị email
        holder.tvEmail.setText(student.getEmail());
        // Tải ảnh đại diện bằng Glide
        Glide.with(holder.itemView.getContext())
                .load(student.getAvatar())
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return students != null ? students.size() : 0; // Kiểm tra null để tránh lỗi
    }

    // ViewHolder lưu các view của mỗi item
    static class StudentViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView tvName, tvEmail;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }
    }
}