package com.example.examapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity {

    TextView user, email;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        user = findViewById(R.id.name);
        email = findViewById(R.id.email);
        b = findViewById(R.id.goon);
    }

    public void termpage(View obj){
            Intent i = new Intent(MainActivity.this, TermPage.class);
            if(!isEmpty(user.getText().toString())) {
                if(!isEmpty(email.getText().toString())){
                    boolean isValid = isEmailValid(email.getText().toString());
                    if(isValid){
                        String name = String.valueOf(user.getText());
                        i.putExtra("name", name);
                        i.putExtra("email",email.getText().toString());
                        startActivity(i);
                    }else{
                        Toast.makeText(MainActivity.this, "Invalid Email !", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    String name = String.valueOf(user.getText());
                    i.putExtra("name", name);
                    startActivity(i);
                }
            }
            else{
                Toast.makeText(MainActivity.this, "Requires Name !", Toast.LENGTH_SHORT).show();
            }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //back button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
