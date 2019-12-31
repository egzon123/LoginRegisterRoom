package com.example.loginregisterroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.TextView;


import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class WelcomeActivity extends AppCompatActivity {
    //    SharedPreferences pref;
    TextView name,surname,emailIn,gender,birthdate;
    UserViewModel userViewModel;
    User logedUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.getSupportActionBar().setTitle("Welcome");
        initView();
        String userEmail = getIntent().getStringExtra("userEmail");
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        retriveUserByEmail(userEmail);

    }



    private void initView(){
        name = findViewById(R.id.etWname);
        surname = findViewById(R.id.etWsurname);
        emailIn = findViewById(R.id.etWemail);
        gender = findViewById(R.id.etWgender);
        birthdate = findViewById(R.id.etWbirthdate);
    }

  private void retriveUserByEmail(String email){
        List<User> userList = null;

        userViewModel.getAllUsers().observe(this,users -> {
            setLogetUser(users,email);
        });

  }

  private void setLogetUser(List<User> users,String email){
            for (User user: users){
                if (user.getEmail().equals(email)) {
                    logedUser = user;
                    name.setText("Name : "+logedUser.getName());
                    surname.setText("Surname : "+logedUser.getSurname());
                    emailIn.setText("Email : "+logedUser.getEmail());
                    gender.setText("Gender : "+logedUser.getGender());
                    birthdate.setText("Birthdate : "+logedUser.getBirthday());

                }
            }

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

