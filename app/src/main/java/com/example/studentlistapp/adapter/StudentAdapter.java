package com.example.studentlistapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.studentlistapp.R;
import com.example.studentlistapp.model.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private List<Student> students = new ArrayList<>();
    private OnStudentActionListener actionListener;

    public interface OnStudentActionListener {
        void onDelete(Student student);
        void onEdit(Student student);
    }

    public void setOnStudentActionListener(OnStudentActionListener listener) {
        this.actionListener = listener;
    }

    public void setStudents(List<Student> students) {
        this.students = students != null ? students : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = students.get(position);
        holder.tvName.setText(student.getFirst_name() + " " + student.getLast_name());
        holder.tvEmail.setText(student.getEmail());
        Glide.with(holder.itemView.getContext()).load(student.getAvatar()).into(holder.avatar);

        holder.btnDelete.setOnClickListener(v -> {
            if (actionListener != null) actionListener.onDelete(student);
        });

        holder.btnEdit.setOnClickListener(v -> {
            if (actionListener != null) actionListener.onEdit(student);
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView tvName, tvEmail;
        Button btnDelete, btnEdit;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}