package com.example.rajus.newsviews;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class CheckActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings=getSharedPreferences("prefs",0);
        final boolean firstRun=settings.getBoolean("firstRun",true);
        if(firstRun)
        {
            SharedPreferences.Editor editor=settings.edit();
            editor.putBoolean("firstRun",false);
            editor.commit();
            Intent i=new Intent(CheckActivity.this,SplashActivity.class);
            startActivity(i);
            finish();
        }
        else
          {
            Intent a=new Intent(CheckActivity.this,MainActivity.class);
            startActivity(a);
            finish();
          }
    }
}

