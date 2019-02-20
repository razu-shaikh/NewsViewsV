package com.example.rajus.newsviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DatePlayActivity extends AppCompatActivity {
    private Button search_button;
    private WebView webView;
    private EditText editText_date;
    String link_url_date;
    String getEditText_Date;

    String url_date = "http://numbersapi.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_play);

        search_button =(Button)findViewById(R.id.search_button);
        editText_date = (EditText)findViewById(R.id.edit_text_date);
         webView = (WebView)findViewById(R.id.web_view_date);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                //Log.e("Error"+Thread.currentThread().getStackTrace()[2],paramThrowable.getLocalizedMessage());
                Toast toast= Toast.makeText(DatePlayActivity.this,"Please fill the search box correctly",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();

            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getEditText_Date = editText_date.getText().toString();
                editText_date.setText("");

                link_url_date = url_date.concat(getEditText_Date);

                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(link_url_date);

                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }
}
