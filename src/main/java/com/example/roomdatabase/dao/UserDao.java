package com.example.roomdatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomdatabase.entity.User;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertRecord(User user);

    @Query("Select * from user_table")
    List<User> readRecord();
}
