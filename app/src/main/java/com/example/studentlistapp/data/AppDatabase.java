package com.example.studentlistapp.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.studentlistapp.model.Student;

@Database(entities = {Student.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StudentDao studentDao();
}