package com.example.rajus.newsviews;
//This is Home Activity
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;

    ImageView imageView;
    TextView textView;
    RelativeLayout relative;
    public String content; //url
    private  AlertDialog.Builder alert;

    private FirebaseAuth mAuth;

    private  FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser()== null){
                    startActivity(new Intent(HomeActivity.this,GoogleLogInActivity.class));
                }
            }
        };

        mDrawer = (DrawerLayout)findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawer,R.string.open,R.string.close);

        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nave_view= (NavigationView) findViewById(R.id.nave_view);
        nave_view.setNavigationItemSelectedListener(this);

        imageView =(ImageView)findViewById(R.id.image);
        textView =(TextView)findViewById(R.id.description);
        relative = (RelativeLayout)findViewById(R.id.relative);

        String imageUrl = getIntent().getStringExtra("img");
        String description = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content"); //url

        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(imageView);

        textView.setText(description);

        //Toast.makeText(HomeActivity.this,"content"+content,Toast.LENGTH_LONG).show();

        relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert = new AlertDialog.Builder(HomeActivity.this);

                alert.setTitle("Title");
                alert.setMessage("If you want to read Detail..Please press ok");

                alert.setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Intent intent = new Intent(HomeActivity.this,WebViewActivity.class);
                        intent.putExtra("webontent",content);
                        startActivity(intent);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();

            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.setting){
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.exit){

            mAuth.signOut();

        }
        if (id == R.id.about){
            Intent intent = new Intent(HomeActivity.this,SettingActivity.class);
            startActivity(intent);
        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void numberPlayActivity(View view) {
        Intent intent = new Intent(HomeActivity.this,NumberPlayActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}
