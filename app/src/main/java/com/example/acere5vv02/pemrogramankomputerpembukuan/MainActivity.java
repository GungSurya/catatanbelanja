package com.example.acere5vv02.pemrogramankomputerpembukuan;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static int USERID = 0;
    public static final String EXTRA_USERID = "USERID";
    Toolbar toolbar;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connect database
        db = new DatabaseHelper(getApplicationContext());

        //int res = db.getUserCount();

        //Toast.makeText(getApplicationContext(), db.getUserCount(), Toast.LENGTH_SHORT).show();
        /* if(res.getCount() <= 0){
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.putExtra(EXTRA_USERID, USERID);
            startActivity(intent);
        }*/
        //init toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(false); // disable the default title element here (for centered title)
    }

}
