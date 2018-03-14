package com.example.lasa.basico;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Lasa on 7-3-2018.
 */

public class PlashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent myIntent = new Intent(getBaseContext(), MyService.class);
        startService(myIntent);


        startActivity(new Intent(this, MainActivity.class));
        startActivity(new Intent(this, SongLayoutActivity.class));

        finish();
    }
}
