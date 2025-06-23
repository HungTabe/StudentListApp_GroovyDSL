package com.example.studentlistapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.studentlistapp.adapter.StudentAdapter;
import com.example.studentlistapp.databinding.ActivityMainBinding;
import com.example.studentlistapp.model.Student;
import com.example.studentlistapp.viewmodel.StudentViewModel;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private StudentViewModel viewModel;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo RecyclerView
        adapter = new StudentAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        // Khởi tạo ViewModel
        viewModel = new ViewModelProvider(this).get(StudentViewModel.class);

        // Quan sát dữ liệu sinh viên
        viewModel.getStudentList().observe(this, students -> {
            adapter.setStudents(students);
        });

        // Quan sát trạng thái loading
        viewModel.getIsLoading().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        // Xử lý nút Thêm
        binding.btnAdd.setOnClickListener(v -> {
            String firstName = binding.etFirstName.getText().toString().trim();
            String lastName = binding.etLastName.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();

            if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty()) {
                viewModel.addStudent(firstName, lastName, email);
                Toast.makeText(this, "Thêm sinh viên thành công!", Toast.LENGTH_SHORT).show();
                binding.etFirstName.setText("");
                binding.etLastName.setText("");
                binding.etEmail.setText("");
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện xóa
        adapter.setOnDeleteClickListener((student, position) -> {
            viewModel.deleteStudent(student, position);
            Snackbar.make(binding.getRoot(), "Đã xóa sinh viên", Snackbar.LENGTH_LONG)
                    .setAction("Hoàn tác", v -> viewModel.undoDelete())
                    .show();
        });

        // Xử lý nút Sắp xếp
        binding.btnSort.setOnClickListener(v -> {
            viewModel.sortStudents();
        });
    }
}