package com.example.loginregisterroom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

//coment add
public class UserRegisterActivity extends AppCompatActivity {
    private UserViewModel userViewModel;
    EditText emri,mbiemri,email,password,confirm_password;
    Button btnRegister;
    TextView date;

    Button selectDate;
    DatePickerDialog datePickerDialog;
    int yearD;
    int monthD;
    int dayOfMonthD;

//    SharedPreferences pref;
//    SharedPreferences.Editor prefsEditor;


    Calendar calendar;

    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);
        this.getSupportActionBar().setTitle("Register");

        initView();

        selectDateFromDialog(selectDate);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (UserRegisterActivity.this.validate()) {
                    int selectedId = radioSexGroup.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    radioSexButton = (RadioButton) UserRegisterActivity.this.findViewById(selectedId);
                    if (!userViewModel.existByEmail(email.getText().toString())) {
                        String birthdateText = UserRegisterActivity.this.getDayOfMonthD() + "/" + UserRegisterActivity.this.getMonthD() + "/" + UserRegisterActivity.this.getYearD();
                        User user = new User();
                        user.setId(0);
                        user.setName(emri.getText().toString());
                        user.setSurname(mbiemri.getText().toString());
                        user.setEmail(email.getText().toString());
                        user.setPassword(password.getText().toString());
                        user.setGender(radioSexButton.getText().toString().charAt(0));

                        user.setBirthday(birthdateText);
                        userViewModel.insert(user);

                        Toast.makeText(UserRegisterActivity.this, "User created successfuly please Login", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserRegisterActivity.this, LoginActivity.class);
                        UserRegisterActivity.this.startActivity(intent);

                    } else {
                        Snackbar.make(btnRegister, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                    }

                }
            }
        });

    }

    public void selectDateFromDialog(Button selectDate){
        selectDate.setOnClickListener((View view)-> {
            calendar = Calendar.getInstance();
            yearD = calendar.get(Calendar.YEAR);
            monthD = calendar.get(Calendar.MONTH);
            dayOfMonthD = calendar.get(Calendar.DAY_OF_MONTH);

            datePickerDialog = new DatePickerDialog(UserRegisterActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            date.setText(day + "/" + (month + 1) + "/" + year);
                            setYearD(year);
                            setMonthD(month+1);
                            setDayOfMonthD(day);
                        }
                    }, yearD, monthD, monthD);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

    }

    private void initView(){
        emri=  findViewById(R.id.etEmri);
        mbiemri = findViewById(R.id.etMbiemri);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirmPassword);
        selectDate = findViewById(R.id.btnDate);
        date = findViewById(R.id.tvSelectedDate);
        btnRegister = findViewById(R.id.btnRegister);


        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
    }

    private boolean validate(){
        if(isUserNameOrSurnameEmpty(emri.getText().toString().trim(),mbiemri.getText().toString().trim())){
            Toast.makeText(UserRegisterActivity.this, "Name or Surname is Empty", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isValidEmail(email.getText().toString())){
            Toast.makeText(UserRegisterActivity.this,"Please enter valid email ",Toast.LENGTH_SHORT).show();
            return false;
        }else if (date.getText().toString().isEmpty()){
            Toast.makeText(UserRegisterActivity.this, "Please select a birthdate", Toast.LENGTH_SHORT).show();
            return false;

        }else if(!matchPws(password.getText().toString(),confirm_password.getText().toString())){
            Toast.makeText(UserRegisterActivity.this,"Passwords dont match",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }

    }

//    private void userRegisterSharedP(User user){
//         pref = getApplicationContext().getSharedPreferences("userData", MODE_PRIVATE);
//
//         prefsEditor = pref.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(user);
//        String uniqueID = UUID.randomUUID().toString();
//        prefsEditor.putString(uniqueID, json);
//        boolean isRegistredSuccessful = prefsEditor.commit();
//        if(isRegistredSuccessful){
//            Toast.makeText(this, "Registred Successfuly", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(UserRegisterActivity.this,LoginActivity.class);
//            startActivity(intent);
//
//        }else{
//            Toast.makeText(this, "Not Registerd", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    private boolean ifEmailExists(String email){
//        pref = getApplicationContext().getSharedPreferences("userData", MODE_PRIVATE);
//        Map<String, ?> prefsMap = pref.getAll();
//        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
//            String userDetails = entry.getValue().toString();
//            if(userDetails.contains(email)){
//                return true;
//            }
//        }
//        return false;
//    }

    private boolean isUserNameOrSurnameEmpty(String name,String password){
        return name.isEmpty() || password.isEmpty();
    }




    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean matchPws(String p1,String p2){
        if(p1.trim().isEmpty() && p2.trim().isEmpty()){
            return false;
        }else if(p1.equals(p2)){
            return true;
        }
        return false;
    }


    public void setYearD(int yearD) {
        this.yearD = yearD;
    }

    public void setMonthD(int monthD) {
        this.monthD = monthD;
    }

    public void setDayOfMonthD(int dayOfMonthD) {
        this.dayOfMonthD = dayOfMonthD;
    }

    public int getYearD() {
        return yearD;
    }

    public int getMonthD() {
        return monthD;
    }

    public int getDayOfMonthD() {
        return dayOfMonthD;
    }
}
