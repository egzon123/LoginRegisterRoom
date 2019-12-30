package com.example.loginregisterroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;



public class WelcomeActivity extends AppCompatActivity {
    //    SharedPreferences pref;
    TextView name,surname,email,gender,birthdate;
    UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.getSupportActionBar().setTitle("Welcome");
        initView();
        String userEmail = getIntent().getStringExtra("userEmail");
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        User logedUser = retriveUserByEmail(userEmail);

        name.setText("Name : "+logedUser.getName());
        surname.setText("Surname : "+logedUser.getSurname());
        email.setText("Email : "+logedUser.getEmail());
        gender.setText("Gender : "+logedUser.getGender());
        birthdate.setText("Birthdate : "+logedUser.getBirthday());




    }

    private void initView(){
        name = findViewById(R.id.etWname);
        surname = findViewById(R.id.etWsurname);
        email = findViewById(R.id.etWemail);
        gender = findViewById(R.id.etWgender);
        birthdate = findViewById(R.id.etWbirthdate);
    }

  private User retriveUserByEmail(String email){
      for(User currentUser :userViewModel.getAllUsers().getValue()){
          if(currentUser.getEmail().equals(email)){
              return currentUser;
          }
      }
      return null;
  }



//    public User retriveUserByEmail(String email){
//        Gson g = new Gson();
//        User user = null;
//
//        pref = getApplicationContext().getSharedPreferences("userData", MODE_PRIVATE);
//        Map<String, ?> prefsMap = pref.getAll();
//        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
//            String userDetails = entry.getValue().toString();
//            if(userDetails.contains(email)){
//                user = g.fromJson(userDetails,User.class);
//            }else{
//                continue;
//            }
//        }
//        return user;
//    }

}

