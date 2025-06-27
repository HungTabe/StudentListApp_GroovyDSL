package com.example.studentlistapp.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.studentlistapp.model.Student;
import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM students")
    List<Student> getAllStudents();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Student> students);

    @Insert
    void insertStudent(Student student);

    @Delete
    void delete(Student student);

    @Update
    void update(Student student);

    @Query("SELECT COUNT(*) FROM students")
    int getStudentCount();
}