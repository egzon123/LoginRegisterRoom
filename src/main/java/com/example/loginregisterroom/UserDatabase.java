package com.example.loginregisterroom;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;

@Database(entities = {User.class},version = 1,exportSchema = false)
public abstract class UserDatabase  extends RoomDatabase {

    private static UserDatabase instance;
    public abstract UserDao userDao();

    public static synchronized UserDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),UserDatabase.class,"user_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao noteDao;

        private PopulateDbAsyncTask(UserDatabase db){
            noteDao = db.userDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new User("Egzon","Berisha", 'M',"1996/06/25","egzon.berisha@gmail.com","egzon123"));
            noteDao.insert(new User("Bleron","Muliqi", 'M',"1986/03/20","bleron.muliqi@gmail.com","bleron123"));
            noteDao.insert(new User("Filan","Fisteku", 'M',"2000/07/06","filan.fisteky@gmail.com","filan123"));
            return null;
        }
    }
}
