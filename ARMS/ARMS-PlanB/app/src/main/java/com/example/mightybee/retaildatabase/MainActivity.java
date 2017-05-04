package com.example.mightybee.retaildatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void openListView(View view){
        Intent i = new  Intent (this, ListView.class);
        startActivity(i);
    }

    public void openDetailView(View view){
        Intent i = new Intent (this, DetailView.class);
        startActivity(i);
    }
}
