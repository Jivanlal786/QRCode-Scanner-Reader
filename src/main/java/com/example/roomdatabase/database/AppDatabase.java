package com.example.roomdatabase.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.roomdatabase.dao.UserDao;
import com.example.roomdatabase.entity.User;

@Database(entities = {User.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

        public abstract UserDao userDAO();
}
