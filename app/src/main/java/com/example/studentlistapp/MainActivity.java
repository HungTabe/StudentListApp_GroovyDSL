package com.example.studentlistapp;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.studentlistapp.adapter.StudentAdapter;
import com.example.studentlistapp.databinding.ActivityMainBinding;
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

        viewModel = new ViewModelProvider(this).get(StudentViewModel.class);

        viewModel.getStudentList().observe(this, students -> {
            adapter.setStudents(students);
        });

        viewModel.getIsLoading().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
    }
}