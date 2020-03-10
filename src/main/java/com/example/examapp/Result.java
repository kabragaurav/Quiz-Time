package com.example.examapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Result extends AppCompatActivity {
    static String email;
    // disabling the back button of android phone
    @Override
    public void onBackPressed() { }

    TextView t, t_info;
    LinearLayout lr;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        t = findViewById(R.id.textView);
        t_info = findViewById(R.id.info);
        b = findViewById(R.id.back);
        lr = findViewById(R.id.lastroot);

        Intent i2 = getIntent();
        String[] user = {i2.getStringExtra("name"), i2.getStringExtra("email"),i2.getStringExtra("score"),  i2.getStringExtra("correct"), i2.getStringExtra("incorrect")};
        String name = user[0];
        String email = user[1];
        String score = user[2];
        String correct = user[3];
        String incorrect = user[4];

        Result.email = email;

        String ss = "<b>" + "Final Results : " + "</b>";
        t.setText(Html.fromHtml(ss));
        t.append("\n\n");

        if(Integer.parseInt(score) < 0)
            score = "0";


        t.append("Dear " + name + ", you obtained " + score + "/100.\n");
        double per = Double.parseDouble(score);

        if(per<=33) {
//            lr.setBackground(Color.RED);
            lr.setBackgroundResource(R.color.fail);
        }
        else if(per>33 && per<=70)
                lr.setBackgroundResource(R.color.avg);
        else
            lr.setBackgroundResource(R.color.fabulous);

        t.append("\nCorrect attempts : " + correct);
        t.append("\nIncorrect attempts : " + incorrect);
        t.append("\n\n           ---  Percentage : " + String.format(String.valueOf(per), "%.2f") + "%  ---");
        if(email!=null) {
            t.append("\n\nThe results have been sent to your provided email address, "+email+".");

        }

    }

    public void back(View obj){
        Intent i = new Intent(Result.this, MainActivity.class);
        startActivity(i);
    }
    public void sendmail(View v) {
        if (Result.email != null) {
//        System.out.println("Starting");
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
//                    System.out.println("trying");
                        GMailSender sender = new GMailSender("###@gmail.com",
                                "pass");
                        sender.sendMail("ExamApp Result", "Result was shown on app. Reply in order to give us your valuable feedback! Thank You.",
                                "###@gmail.com", Result.email);
                    } catch (Exception e) {
//                    System.out.println("caught");
                        Log.e("SendMail", e.getMessage(), e);
                    }
                }

            }).start();
//        System.out.println("end");
        }
        else{
            Toast.makeText(Result.this, "No email was provided",Toast.LENGTH_SHORT).show();
        }
    }
}
