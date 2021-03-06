package com.cnit355.myles.a425project;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView test,question, qCounter;
    ListView list;
    String answers[] = new String[4];
    ArrayAdapter<String> adapter;
    int questionCounter = 0;
    int qTracker = 1;
    String apiResponse;
    JSONArray questionList;
    int correctAnswerCounter = 0;
    String selectedAnswer = "";

    int catCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Quizop");

        Button nextBtn = findViewById(R.id.nextBtn);
        question = findViewById(R.id.textView2);
        list = findViewById(R.id.listView);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        qCounter = findViewById(R.id.QCounter);
        qCounter.setText("Question #1");

        new Retrieve().execute();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionCounter++;
                qTracker ++;
                generateNewQuestion(questionList, questionCounter, apiResponse);
                if(questionCounter > 9){
                    Intent mIntent = new Intent(MainActivity.this, Leaderboard.class);
                    mIntent.putExtra("numberCorrect", correctAnswerCounter);
                    startActivity(mIntent);
                }
                qCounter.setText("Question #" + qTracker);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // check if selected answer is correct
                selectedAnswer = adapter.getItem(i);
                Log.i("selectedAnswer: ", selectedAnswer);
                if(checkAnswer(questionList, questionCounter, selectedAnswer) == true){
                    correctAnswerCounter++;
                }
                Log.i("Correct: ", "Count " + correctAnswerCounter);
            }
        });
    }

    class Retrieve extends AsyncTask<Void, Void, String> {
        private Exception exception;

        protected void onPreExecute() {

        }

        protected String doInBackground(Void... urls) {
            Bundle bundle = getIntent().getExtras();
            int selectedCategory = bundle.getInt("selected Category");
            try {
                URL url = new URL("https://opentdb.com/api.php?amount=10&category=" + selectedCategory + "&type=multiple");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                Log.i("url", "url" + url);
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally {
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            Log.i("INFO", response);
            apiResponse = response;
            try {

                JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                JSONArray jsonArray = new JSONArray(object.getString("results"));
                questionList = jsonArray;
                generateNewQuestion(questionList, questionCounter, apiResponse);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateNewQuestion(JSONArray a, int i, String r){
        try {
            JSONObject x = (JSONObject)a.get(i);
            JSONArray ans = x.getJSONArray("incorrect_answers");
            for(int j = 0; j < ans.length(); j++){
                answers[j] = ans.getString(j);
            }
            String name = x.getString("question");
            String correctAnswer = x.getString("correct_answer");

            adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, answers);
            answers[3] = correctAnswer;
            shuffleArray(answers);
            list.setAdapter(adapter);
            question.setText(name);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void generateCategories() {


    }

    private boolean checkAnswer(JSONArray a, int j, String s){
        try {
            JSONObject o = (JSONObject)a.get(j);

            String correctAnswer = o.getString("correct_answer");
            Log.i("Correct answer: ", correctAnswer);
            if(correctAnswer.equals(s)){
                return true;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void shuffleArray(String[] a) {
        int n = a.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }

    private static void swap(String[] a, int i, int change) {
        String helper = a[i];
        a[i] = a[change];
        a[change] = helper;
    }
}
