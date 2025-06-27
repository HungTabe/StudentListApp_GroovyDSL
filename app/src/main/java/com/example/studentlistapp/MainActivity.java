package com.example.studentlistapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.studentlistapp.adapter.StudentAdapter;
import com.example.studentlistapp.databinding.ActivityMainBinding;
import com.example.studentlistapp.viewmodel.StudentViewModel;

// MainActivity hiển thị danh sách sinh viên
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding; // ViewBinding để truy cập view
    private StudentViewModel viewModel; // ViewModel cung cấp dữ liệu
    private StudentAdapter adapter; // Adapter cho RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Khởi tạo ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo RecyclerView
        adapter = new StudentAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        // Khởi tạo ViewModel
        viewModel = new ViewModelProvider(this).get(StudentViewModel.class);

        // Quan sát danh sách sinh viên
        viewModel.getStudentList().observe(this, students -> {
            adapter.setStudents(students); // Cập nhật danh sách trong RecyclerView
        });

        // Quan sát trạng thái loading
        viewModel.getIsLoading().observe(this, isLoading -> {
            // Hiển thị hoặc ẩn ProgressBar
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
    }
}