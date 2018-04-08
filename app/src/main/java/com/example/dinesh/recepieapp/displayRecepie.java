package com.example.dinesh.recepieapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class displayRecepie extends AppCompatActivity {

    RecepieDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recepie);

        db = new RecepieDB(this);


    }

    public void displayRecepie() {
        Cursor result = db.getRecepiesFromDatabase();
        if(result.getCount() == 0) {
            // No recepies available
            displayTheRecepie("Error", "No recepies yet");
            // show empty
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(result.moveToNext()) {
            buffer.append("Id :"+ result.getString(0)+"\n");
            buffer.append("Description :"+ result.getString(1)+"\n");
            buffer.append("Ingredients :"+ result.getString(2)+"\n");
            buffer.append("Instructions :"+ result.getString(3)+"\n");
            buffer.append("URL :"+ result.getString(4)+"\n\n");

        }

        // now call a function to show all data
        displayTheRecepie("Data", buffer.toString());

    }

    public void displayTheRecepie(String title, String recepieDetail) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(recepieDetail);
        builder.show();
    }
}
