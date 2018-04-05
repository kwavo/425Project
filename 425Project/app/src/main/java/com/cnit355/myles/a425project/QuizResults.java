package com.cnit355.myles.a425project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizResults extends AppCompatActivity {

    TextView result;
    Button btnTryAgain, btnShareOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);
        setTitle("Quiz Results");

        result = findViewById(R.id.textView3);
        btnTryAgain = findViewById(R.id.btnTryAgain);
        btnShareOnline = findViewById(R.id.btnShareOnline);
        int numCorrect  = getIntent().getIntExtra("numberCorrect",0);

        result.setText("You got " + numCorrect + " questions correct!");

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start game over
                Intent tryAgain = new Intent(QuizResults.this, MainActivity.class);
                startActivity(tryAgain);
            }
        });

        btnShareOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
