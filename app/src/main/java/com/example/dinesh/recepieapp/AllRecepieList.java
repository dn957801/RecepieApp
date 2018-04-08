package com.example.dinesh.recepieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class AllRecepieList extends AppCompatActivity {

    // view variables
    Button create_recepie;

    // adapter to list down all receipes
    private ArrayAdapter<String> itemsAdapter;
    private ArrayList<String> myRecepieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_recepie_list);

        /**Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        create_recepie = (Button)findViewById(R.id.create_a_recepie);
        create_recepie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load the activity to insert data to database
                Intent intent = new Intent(AllRecepieList.this, CreateRecepie.class);
                startActivity(intent);
            }
        });



    }

    public void getAllRecepies() {
        //SQLiteDatabase db = this.getWritableDatabase();
    }

}
