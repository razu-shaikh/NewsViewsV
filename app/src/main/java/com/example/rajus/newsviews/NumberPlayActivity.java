package com.example.rajus.newsviews;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
public class NumberPlayActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private EditText editText;
    String link_url;
    String getEditText;

    String url = "http://numbersapi.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_play);

        button =(Button)findViewById(R.id.button);
        textView = (TextView)findViewById(R.id.textview);
        editText =(EditText)findViewById(R.id.edit_text);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                //Log.e("Error"+Thread.currentThread().getStackTrace()[2],paramThrowable.getLocalizedMessage());
                Toast toast= Toast.makeText(NumberPlayActivity.this,"Please fill the search box correctly",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditText=editText.getText().toString();
                int textSize = getEditText.length();
                editText.setText("");
                if (getEditText.isEmpty() || textSize < 2){
                    Toast toast= Toast.makeText(NumberPlayActivity.this,"Please fill the search box correctly",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();

                }else{

                    link_url= url.concat(getEditText);

                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {

                            OkHttpClient client = new OkHttpClient();

                            Request request = new Request.Builder()
                                    .url(link_url)
                                    .build();

                            Response response = null;

                            try {
                                response = client.newCall(request).execute();
                                return response.body().string();

                            }catch (IOException e){
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {

                            textView.setText(o.toString());

                        }
                    }.execute();
                }

            }
        });

    }

}
