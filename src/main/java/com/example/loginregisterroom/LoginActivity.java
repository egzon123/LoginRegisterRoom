package com.example.loginregisterroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText userEmail,userPassword;
    Button btnSignIn,btnRegister;
    //    SharedPreferences pref;
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getSupportActionBar().setTitle("Sign In");
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        printAllData();
        initViews();

        btnSignIn.setOnClickListener((View v) -> {
            if(validate()){
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();

                User currentUser = authenticate(new User(null,null,'A',null,email,password));

                if(currentUser != null){
                    Toast.makeText(this, "Successfuly loged In!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,WelcomeActivity.class);
                    intent.putExtra("userEmail",currentUser.getEmail());
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "Failed to log in , please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener((View v) -> {
            Intent intent = new Intent(LoginActivity.this,com.example.loginregisterroom.UserRegisterActivity.class);
            startActivity(intent);
        });
    }

    private void printAllData() {
        for(User user :userViewModel.getAllUsers().getValue()){
            System.out.println(user);
        }
    }


//    public void printAllData(){
//         pref = getApplicationContext().getSharedPreferences("userData", MODE_PRIVATE);
//        Map<String, ?> prefsMap = pref.getAll();
//        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
//            Log.v("SharedPreferences", entry.getKey() + ":" +
//                    entry.getValue().toString());
//        }
//    }


//    private boolean validateUserCredentialsPrf(String email,String password){
//        pref = getApplicationContext().getSharedPreferences("userData", MODE_PRIVATE);
//        Map<String, ?   > prefsMap = pref.getAll();
//        User user = null;
//        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
//           String userDetails = entry.getValue().toString();
//            Gson g = new Gson();
//            user = g.fromJson(userDetails, User.class);
//          if(user.getEmail().equals(email) && user.getPassword().equals(password)){
//              return true;
//          }
//        }
//        return false;


    private void initViews(){
        userEmail = findViewById(R.id.username);
        userPassword = findViewById(R.id.password);
        btnSignIn = findViewById(R.id.login);
        btnRegister = findViewById(R.id.register);
    }

    private User authenticate(User user){
        for(User currentUser :userViewModel.getAllUsers().getValue()){
           if(currentUser.getEmail().equals(user.getEmail()) && currentUser.getPassword().equals(user.getPassword())){
               return currentUser;
           }
        }
        return null;
    }

    private boolean validate(){
        boolean valid = false;
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            valid = false;
            Toast.makeText(this, "Please enter a valid email!", Toast.LENGTH_SHORT).show();
        } else {
            valid = true;

        }
        if(password.isEmpty()){
            valid = false;
            Toast.makeText(this, "Please enter a valid password!", Toast.LENGTH_SHORT).show();
        }
        return valid;
    }

}
