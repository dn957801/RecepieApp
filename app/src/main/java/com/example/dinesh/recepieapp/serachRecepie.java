package com.example.dinesh.recepieapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class serachRecepie extends AppCompatActivity {

    EditText editText;
    Button button;
    ListView list;
    RecepieDB db;
    String TAG ="searchRecepie";
    int count_of_recepies_from_result = 0;
    private ArrayAdapter<String> itemsAdapter;
    private ArrayList<String> myRecepieList = new ArrayList<String>();
    private ArrayList<String> myRecepieIdList = new ArrayList<String>();
    //CustomAdapter1 customAdapter = new CustomAdapter1();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach_recepie);

        editText = (EditText) findViewById(R.id.recepieSearch);
        button = (Button) findViewById(R.id.button_search_recepie);
        list = (ListView) findViewById(R.id.recepie_search_list);
        db = new RecepieDB(this);

        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myRecepieList);
        list.setAdapter(itemsAdapter);
        //list.setAdapter(customAdapter);

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemsAdapter.clear();
                        // Reference : https://github.com/google-developer-training/android-fundamentals/blob/master/WordListSqlSearchable/app/src/main/java/com/android/example/wordlistsqlsearchable/SearchActivity.java
                        Cursor cursor = db.search(editText.getText().toString());
                        if (cursor == null) {
                            // Nothing to display
                            android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(serachRecepie.this);
                            alert.setTitle("Alert!!");
                            alert.setMessage("No recipe found");
                            alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });
                            alert.show();
                            return;
                        }
                        else if (cursor != null && cursor.getCount() >0) {
                            if(cursor.moveToFirst()) {
                                do {
                                    String rec_id = cursor.getString(cursor.getColumnIndex("ID"));

                                    String rec_name = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));

                                    String rating = cursor.getString(cursor.getColumnIndex("RATING"));

                                    myRecepieList.add(rec_name);
                                    itemsAdapter.notifyDataSetChanged();
                                    myRecepieIdList.add(rec_id);

                                    count_of_recepies_from_result++;

                                } while (cursor.moveToNext());
                            }

                        }
                        else {
                            // Nothing to display
                            android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(serachRecepie.this);
                            alert.setTitle("Alert!!");
                            alert.setMessage("No recipe found");
                            alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });
                            alert.show();
                            return;
                        }
                    }
                }
        );

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int recepie_id = 0;
                recepie_id = Integer.parseInt(myRecepieIdList.get(position));

                Intent intent = new Intent(serachRecepie.this, displayRecepie.class);
                intent.putExtra("RECEPIE_ID", recepie_id);
                intent.putExtra("PREV_INTENT", "SEARCH");
                startActivity(intent);

            }
        });


    }

}