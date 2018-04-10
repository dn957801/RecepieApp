package com.example.dinesh.recepieapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllRecepieList extends AppCompatActivity {

    // view variables
    Button create_recepie;
    ListView listView;

    // Database variable
    RecepieDB db;

    // adapter to list down all receipes
    private ArrayAdapter<String> itemsAdapter;
    private ArrayList<String> myRecepieList = new ArrayList<String>();
    private int countOfRecepies = 0;
    String TAG="AllRecepieList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_recepie_list);

        // initialize database variable
        db = new RecepieDB(this);

        //set the itemsAdapter to point to correct list
        listView = (ListView) findViewById(R.id.recepie_list);
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(itemsAdapter);
        listView.setLongClickable(true);

        // Invoke function to get all recepies
        getAllRecepies();

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int count = 0, incr_count = 0;
                        for (count = 0; count < countOfRecepies; count++) {
                            if (position == count) {
                                // this is the recepie to be displayed
                                Cursor result = db.getRecepiesFromDatabase();
                                // increment cursor to reach exact row
                                while (incr_count != count) {
                                    result.moveToNext();
                                    incr_count++;
                                }
                                //itemsAdapter.clear();
                                // now reached exact position, invoke activity
                                Log.i(TAG, "DIN First count = " + count + "incr_count = " + incr_count);
                                Intent intent = new Intent(AllRecepieList.this, displayRecepie.class);
                                intent.putExtra("RECEPIE_ID", (count));
                                startActivity(intent);

                            }
                        }

                    }
                }
        );

        create_recepie = (Button) findViewById(R.id.create_new_recepie);
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

        int counter = 0;
        String recepie_name;
        // Retrieve recepies from database
        Cursor result = db.getRecepiesFromDatabase();
        countOfRecepies = result.getCount();
        if (countOfRecepies == 0) {
            // No recepies available. Show nothing
            Toast.makeText(AllRecepieList.this, "No recepies available yet", Toast.LENGTH_LONG).show();

        } else {
            Log.i(TAG, "DIN inside else, count of recepies = " + countOfRecepies);
            while (counter < countOfRecepies) {
                Log.i(TAG, "DIN while loop. counter = " + counter);
                /* if (counter >= countOfRecepies) {
                    Log.i(TAG, "DIN while loop break");
                    break;
                }*/
                //counter++;
                result.moveToNext();
                recepie_name = result.getString(1);
                Log.i(TAG, "DIN recepie_name = " + recepie_name);
                myRecepieList.add(recepie_name);
                itemsAdapter.add(recepie_name);
                itemsAdapter.notifyDataSetChanged();
                counter++;
            }
        }
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}
