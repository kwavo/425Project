package com.cnit355.myles.a425lab9_m2;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Member;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnGetData;
    ListView listView;
    Spinner spinner;
    String urlAddress = "http://web.ics.purdue.edu/~minb/json_students";
    ArrayList<Member> members;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Lab 9 Mission 2");

        btnGetData = findViewById(R.id.btnGetData);
        listView = findViewById(R.id.listView);
        spinner = findViewById(R.id.spinner);

    }

    public class NetworkAsyncTask extends AsyncTask<Integer, String, Object> {
        Context context = null;
        String address;
        ProgressDialog dialog = null;
        ArrayList<Member> members;

        public NetworkAsyncTask(Context c, String a) {
            context = c;
            address = a;
            members = new ArrayList<Member>();
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setTitle("Please wait...");
            dialog.show();
        }

        @Override
        protected Object doInBackground(Integer... integers) {
            StringBuffer sb = new StringBuffer();
            InputStream is = null;
            InputStreamReader isr = null;
            BufferedReader br = null;

            try {
                URL url = new URL(address);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(10000);

                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    is = con.getInputStream();
                    isr = new InputStreamReader(is);
                    br = new BufferedReader(isr);

                    while (true) {
                        String strLine = br.readLine();
                        if (strLine == null)
                            break;

                        sb.append(strLine + "\n");
                    }
                    Log.i("sb: ", sb.toString());
                    parser(sb.toString());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (br != null)
                        br.close();
                    if (is != null)
                        is.close();
                    if (isr != null)
                        isr.close();
                } catch (Exception ex2) {
                    ex2.printStackTrace();
                }
            }
            return members;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            dialog.dismiss();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        private void parser (String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = new JSONArray(jsonObject.getString("Member Info"));
                members.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    String name = jsonObject1.getString("name");
                    Log.i("name", name);

                    ArrayList<String> hobbies = new ArrayList<String>();
                    JSONArray jsonArray2 = jsonObject1.getJSONArray("hobbies");

                    for (int j = 0; j < jsonArray2.length(); j++) {
                        String hobby = jsonArray2.getString(j);
                        Log.i("hobbies[" + j + "] : " , hobby);
                    }

                    JSONObject jsonObject2 = jsonObject1.getJSONObject("info");
                    int year = jsonObject2.getInt("year");
                    String number = jsonObject2.getString("number");
                    int id = jsonObject2.getInt("id");

                    Member member = new Member(name, year, hobbies, number, id);
                    members.add(member);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public class Member {
        String name;
        int year;
        ArrayList<String> hobbies;
        String number;
        int id;

        public Member(String n, int y, ArrayList<String> h, String num, int id) {
            name = n;
            year = y;
            hobbies = h;
            number = num;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public ArrayList<String> getHobbies() {
            return hobbies;
        }

        public void setHobbies(ArrayList<String> hobbies) {
            this.hobbies = hobbies;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

}
