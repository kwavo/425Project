package com.cnit355.myles.a425project;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log; 
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class QuizResults extends AppCompatActivity {

    TextView result;
    Button btnTryAgain, btnShareOnline;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;
    Score score;
     int numCorrect;
    int count,finalcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);
        setTitle("Quiz Results");

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        result = findViewById(R.id.textView3);
        btnTryAgain = findViewById(R.id.btnTryAgain);
        btnShareOnline = findViewById(R.id.btnShareOnline);
        numCorrect  = getIntent().getIntExtra("numberCorrect",0);

        result.setText("You got " + numCorrect + " questions correct!");


        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start game over
                Intent tryAgain = new Intent(QuizResults.this, WelcomeActivity.class);
                startActivity(tryAgain);
            }
        });

        btnShareOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else {
            ValueEventListener newEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (int i = 0; i < dataSnapshot.child("Events").getChildrenCount(); i++) {
                        if(dataSnapshot.child("Events").child(String.valueOf(i)).getValue() != null){
                            count = i;
                        }
                        Log.i("Count", String.valueOf(count));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            mDatabase.addValueEventListener(newEventListener);
            mUserId = mFirebaseUser.getEmail();
            String username = mUserId.substring(0,mUserId.indexOf("@"));
            Log.i("User ", mFirebaseUser.getEmail());
            score = new Score(numCorrect, username);
            count++;
            mDatabase.child("Events").child(String.valueOf(count)).setValue(score);
            Intent intent = new Intent(this, Leaderboard.class);
            intent.putExtra("QuizScore", numCorrect);
            LocalBroadcastManager.getInstance(QuizResults.this).sendBroadcast(intent);
        }
    }
}
