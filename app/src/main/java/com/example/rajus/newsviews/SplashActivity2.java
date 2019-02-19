package com.example.rajus.newsviews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(4000);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(SplashActivity2.this, SplashActivity3.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }

            }
        };
        thread.start();

    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();

    }
}
