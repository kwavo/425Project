package com.cnit355.myles.a425project;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Leaderboard extends AppCompatActivity {

    private DatabaseReference mDatabase;
    ArrayAdapter<String> adapter;
    List<String> list;
    List<String> scoreList,userList;
    ListView lv;
    TextView header;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUserId;
    int numCorrect;
    int count;
    LeaderboardArrayAdapter scoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        setTitle("Top Leaders");

        header = findViewById(R.id.textView4);
        lv = findViewById(R.id.list);
        list = new ArrayList<>();
//        scoreList = new ArrayList<>();
//        userList = new ArrayList<>();
//
//        scoreAdapter = new LeaderboardArrayAdapter(this, userList, scoreList);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        count = adapter.getCount();
        //Log.i("scoreList size", String.valueOf(scoreList.size()));

        numCorrect  = getIntent().getIntExtra("numberCorrect",0);
        header.setText("You scored: " + String.valueOf(numCorrect));

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    Score score = d.getValue(Score.class);
                    list.add(score.getUser() +": " + String.valueOf(score.getScore()));
//                    scoreList.add(String.valueOf(score.getScore()));
//                    userList.add(String.valueOf(score.getUser()));
//                    scoreAdapter.notifyDataSetChanged();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mUserId = mFirebaseUser.getEmail();

        String username = mUserId.substring(0,mUserId.indexOf("@"));
        Score score = new Score(numCorrect, username);
        Log.i("leaderboard count", String.valueOf(adapter.getCount()));

        mDatabase.child("Events").push().setValue(score);

    }
}
