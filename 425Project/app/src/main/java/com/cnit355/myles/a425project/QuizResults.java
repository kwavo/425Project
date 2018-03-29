package com.cnit355.myles.a425project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class QuizResults extends AppCompatActivity {

    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        result = findViewById(R.id.textView3);
        int numCorrect  = getIntent().getIntExtra("numberCorrect",0);

        result.setText("You got " + numCorrect + " questions correct!");
    }
}
