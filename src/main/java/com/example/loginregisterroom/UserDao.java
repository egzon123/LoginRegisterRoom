package com.example.loginregisterroom;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User note);

    @Update
    void update(User note);

    @Delete
    void delete(User note);

    @Query("DELETE FROM user_table")
    void deleteAllUsers();

    @Query("SELECT * FROM user_table")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT COUNT(*) FROM USER_TABLE where email = :email")
    int getUserByEmail(String email);
}
