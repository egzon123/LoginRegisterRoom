package com.example.loginregisterroom;

import android.app.Application;
import android.os.AsyncTask;
import android.service.autofill.UserData;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application){
        UserDatabase database = UserDatabase.getInstance(application);
        userDao = database.userDao();
        allUsers = userDao.getAllUsers();
    }

    public void insert(User user) {
            insertUser(user);
    }

    public void delete(User user){
        deleteUser(user);
    }

    public void update(User user){
        updateUser(user);
    }
    public LiveData<List<User>> getAllUsers(){
        return allUsers;
    }
    public void deleteAllUsers(){
    deleteAll();
    }

    public void insertUser(final User user){
        new AsyncTask<User,Void,Void>(){
            @Override
            protected Void doInBackground(User... users) {
                userDao.insert(users[0]);
                return null;
            }
        }.execute(user);
    }

    public void updateUser(final User user){
        new AsyncTask<User,Void,Void>(){
            @Override
            protected Void doInBackground(User... users) {
                userDao.update(users[0]);
                return null;
            }
        }.execute(user);
    }

    public void deleteUser(final User user){
        new AsyncTask<User,Void,Void>(){
            @Override
            protected Void doInBackground(User... users) {
                userDao.delete(users[0]);
                return null;
            }
        }.execute(user);
    }

    public void deleteAll(){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                userDao.deleteAllUsers();
                return null;
            }
        }.execute();
    }

    public int getUsersByEmail(final String email){
        int count = 0;
        try {
           count =   new AsyncTask<String,Integer,Integer>(){
                  @Override
                  protected Integer doInBackground(String... emails) {
                     return userDao.getUserByEmail(emails[0]);

                  }
              }.execute(email).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return count;
    }
}
