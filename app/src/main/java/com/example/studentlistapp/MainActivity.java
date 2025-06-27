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

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private StudentViewModel viewModel;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new StudentAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @Override
            public <T extends androidx.lifecycle.ViewModel> T create(Class<T> modelClass) {
                return (T) new StudentViewModel(getApplicationContext());
            }
        }).get(StudentViewModel.class);

        viewModel.getStudentList().observe(this, students -> {
            adapter.setStudents(students);
        });

        viewModel.getIsLoading().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        binding.btnSort.setOnClickListener(v -> viewModel.sortStudents());

        binding.btnAdd.setOnClickListener(v -> {
            String firstName = binding.etFirstName.getText().toString().trim();
            String lastName = binding.etLastName.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty()) {
                viewModel.addStudent(firstName, lastName, email);
                binding.etFirstName.setText("");
                binding.etLastName.setText("");
                binding.etEmail.setText("");
                Toast.makeText(this, "Đã thêm sinh viên", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnStudentActionListener(new StudentAdapter.OnStudentActionListener() {
            @Override
            public void onDelete(Student student) {
                viewModel.deleteStudent(student);
                Toast.makeText(MainActivity.this, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEdit(Student student) {
                String newEmail = binding.etEmail.getText().toString().trim();
                if (!newEmail.isEmpty()) {
                    viewModel.updateStudent(student, newEmail);
                    binding.etEmail.setText("");
                    Toast.makeText(MainActivity.this, "Đã sửa email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập email mới", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}