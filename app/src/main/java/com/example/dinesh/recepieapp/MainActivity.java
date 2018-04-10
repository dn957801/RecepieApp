package com.example.dinesh.recepieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Database variable declaration
    RecepieDB myDb;

    // Variables related to XML files
    Button my_recepies, search_a_recepie, edit, delete, rate, create;
    TextView my_description, my_ingredients, my_instructions, my_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new RecepieDB(this);
        my_recepies = (Button) findViewById(R.id.my_recepies);
        search_a_recepie = (Button) findViewById(R.id.search_recepie);
        /** edit = (Button) findViewById(R.id.edit);
        delete = (Button) findViewById(R.id.delete);
        rate = (Button) findViewById(R.id.rate);
        create = (Button) findViewById(R.id.create);
        my_description = (TextView) findViewById(R.id.); */

        // onClickListener to list all existing recepies
        my_recepies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load the AllRecepieList activity to display all existing recepies
                Intent intent = new Intent(MainActivity.this, AllRecepieList.class);
                startActivity(intent);
            }
        });

        search_a_recepie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load the activity to perform search
            }
        });



    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
