package com.example.dinesh.recepieapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
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
    private ArrayList<String> dynamicRecepieList = new ArrayList<String>();
    private ArrayList<Float> dynamicRatingList = new ArrayList<Float>();
    private int countOfRecepies = 0;
    String TAG="AllRecepieList";
    CustomAdapter customAdapter = new CustomAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_recepie_list);

        // initialize database variable
        db = new RecepieDB(this);

        //set the itemsAdapter to point to correct list
        listView = (ListView) findViewById(R.id.recepie_list);
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myRecepieList);
        listView.setAdapter(itemsAdapter);
        //listView.setLongClickable(true);

        listView.setAdapter(customAdapter);
        // Invoke function to get all recepies
        //getAllRecepies();

        /* listView.setOnItemClickListener(
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
        );*/

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
        float rating_for_recepie=0;
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
                dynamicRecepieList.add(recepie_name);
                //itemsAdapter.add(recepie_name);
                //itemsAdapter.notifyDataSetChanged();
                rating_for_recepie = Float.parseFloat(result.getString(5));
                dynamicRatingList.add(rating_for_recepie);
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

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            int counter = 0;
            Cursor result = db.getRecepiesFromDatabase();
            counter = result.getCount();
            return counter;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            int counter = 0;
            String recepie_name;
            float rating;

            convertView = getLayoutInflater().inflate(R.layout.customlayout, null);
            RatingBar rb = (RatingBar) convertView.findViewById(R.id.ratingBar);
            TextView title = (TextView) convertView.findViewById(R.id.title);

            // Retrieve recepies from database
            Cursor result = db.getRecepiesFromDatabase();
            countOfRecepies = result.getCount();

            getAllRecepies();

            if (countOfRecepies == 0) {
                // No recepies available. Show nothing
                Toast.makeText(AllRecepieList.this, "No recepies available yet", Toast.LENGTH_LONG).show();

            } else {
                while (counter < countOfRecepies) {
                    Log.i(TAG, "SHENOY title = "+ dynamicRecepieList.get(position) + "SHENOY rating = " + dynamicRatingList.get(position));
                    title.setText(dynamicRecepieList.get(position));
                    rb.setRating(dynamicRatingList.get(position));
                    counter++;
                }
            }

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                            Intent intent = new Intent(AllRecepieList.this, displayRecepie.class);
                            intent.putExtra("RECEPIE_ID", (count));
                            startActivity(intent);

                        }
                    }
                }
            });
            return convertView;
        }
    }

}
