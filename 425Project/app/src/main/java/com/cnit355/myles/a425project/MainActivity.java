package com.cnit355.myles.a425project;

import android.os.AsyncTask;
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
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView test,question;
    ListView list;
    String answers[] = new String[4];
    ArrayAdapter<String> adapter;
    int questionCounter = 0;
    String apiResponse;
    JSONArray questionList;
    int correctAnswerCounter = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button nextBtn = findViewById(R.id.nextBtn);
        test = findViewById(R.id.editText);
        question = findViewById(R.id.textView2);
        list = findViewById(R.id.listView);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        new Retrieve().execute();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if answer selected is correct
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedAnswer = adapter.getItem(i);
                        Log.i("selectedAnswer: ", selectedAnswer);
                        if(checkAnswer(questionList, questionCounter, selectedAnswer) == true){
                            correctAnswerCounter++;
                        }
                        Log.i("Correct: ", "Count " + correctAnswerCounter);
                    }
                });


                generateNewQuestion(questionList, questionCounter, apiResponse);
            }
        });
    }

    class Retrieve extends AsyncTask<Void, Void, String> {
        private Exception exception;

        protected void onPreExecute() {

        }

        protected String doInBackground(Void... urls) {
            try {
                URL url = new URL("https://opentdb.com/api.php?amount=10&type=multiple");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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
                finally{
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

//                JSONObject x = (JSONObject)jsonArray.get(questionCounter);
//                JSONArray ans = x.getJSONArray("incorrect_answers");
//                for(int j = 0; j < ans.length(); j++){
//                    answers[j] = ans.getString(j);
//                }
//                String name = x.getString("question");
//                String correctAnswer = x.getString("correct_answer");
//                answers[3] = correctAnswer;
//                adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, answers);
//                shuffleArray(answers);
//                list.setAdapter(adapter);
//                question.setText(name);
                question.setText("Press next to start.");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateNewQuestion(JSONArray a, int i, String r){
        questionCounter++;
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
