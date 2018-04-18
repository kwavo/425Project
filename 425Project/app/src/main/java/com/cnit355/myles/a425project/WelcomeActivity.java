package com.cnit355.myles.a425project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {

    Spinner spinner;
    ArrayList<String> categories = new ArrayList<>();
    int catConst = 9;
    int catSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setTitle("Welcome!");

        Button btnStart = findViewById(R.id.btnStart);
        spinner = findViewById(R.id.spinner);



        categories.add("General Knowledge");
        categories.add("Entertainment: Books");
        categories.add("Entertainment: Film");
        categories.add("Entertainment: Music");
        categories.add("Entertainment: Musicals & Theatres");
        categories.add("Entertainment: Television");
        categories.add("Entertainment: Video Games");
        categories.add("Entertainment: Board Games");
        categories.add("Science & Nature");
        categories.add("Science: Computers");
        categories.add("Science: Mathematics");
        categories.add("Mythology");
        categories.add("Sports");
        categories.add("Geography");
        categories.add("History");
        categories.add("Politics");
        categories.add("Art");
        categories.add("Celebrities");
        categories.add("Animals");
        categories.add("Vehicles");
        categories.add("Entertainment: Comics");
        categories.add("Science: Gadgets");
        categories.add("Entertainment: Japanese Anime & Manga");
        categories.add("Entertainment: Cartoon & Animations");

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                catSelected = adapterView.getSelectedItemPosition() + catConst;
                Log.d("category", "category" + catSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent beginGame = new Intent(WelcomeActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();

                bundle.putInt("selected Category", catSelected);
                beginGame.putExtras(bundle);
                startActivity(beginGame);
            }
        });
    }
}
