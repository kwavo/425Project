package com.cnit355.myles.a425lab9_m1;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tvResponse;
    EditText editURL;
    Button btnConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Lab 9 Mission 1");

        tvResponse = findViewById(R.id.tvResponse);
        editURL = findViewById(R.id.editURL);
        btnConnect = findViewById(R.id.btnConnect);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkAsyncTask nat = new NetworkAsyncTask(MainActivity.this, editURL.getText().toString());
                nat.execute();
            }
        });

    }

    public class NetworkAsyncTask extends AsyncTask<Integer, String, Integer> {
        Context context;
        String address = editURL.getText().toString();
        ProgressDialog dialog;

        public NetworkAsyncTask(Context c, String a) {
            context = c;
            address = a;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Dialog");
            dialog.show();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
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

                    while(true) {
                        String strLine = br.readLine();
                        if(strLine == null)
                            break;

                        sb.append(strLine + "\n");
                    }
                    Log.i("SourceCode", sb.toString());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (br != null)
                        br.close();
                    if (isr != null)
                        isr.close();
                    if(is != null)
                        is.close();
                } catch (Exception ex2) {
                    ex2.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            dialog.dismiss();
            tvResponse.setText("Source code for " + editURL.getText().toString() + " acquired. See Android Monitor");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
