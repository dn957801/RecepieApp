package com.example.dinesh.recepieapp;

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
                            editText.append(" NO RESULT " + "\n");
                        }
                        else if (cursor != null && cursor.getCount() >0) {
                            if(cursor.moveToFirst()) {
                                do {
                                    String rec_id = cursor.getString(cursor.getColumnIndex("ID"));
                                    Log.i(TAG, "WOW rec received is = " + rec_id);
                                    String rec_name = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
                                    Log.i(TAG, "WOW rec_name received is = " + rec_name);
                                    String rating = cursor.getString(cursor.getColumnIndex("RATING"));
                                    Log.i(TAG, "WOW rating received is = " + rating);
                                    myRecepieList.add(rec_name);
                                    //myRecepieList.add(rating);
                                    itemsAdapter.notifyDataSetChanged();
                                    myRecepieIdList.add(rec_id);

                                    count_of_recepies_from_result++;

                                } while (cursor.moveToNext());
                            }

                        }
                        else {
                            editText.append(" NO RESULT " + "\n");
                        }
                    }
                }
        );

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int recepie_id = 0;
                recepie_id = Integer.parseInt(myRecepieIdList.get(position));
                Log.i(TAG, "HEY received recepie ID = " + recepie_id);
                Log.i(TAG, "parallel recepie is : " + myRecepieList.get(position) + "  culprit = " + position);

                Intent intent = new Intent(serachRecepie.this, displayRecepie.class);
                intent.putExtra("RECEPIE_ID", recepie_id);
                intent.putExtra("PREV_INTENT", "SEARCH");
                startActivity(intent);

            }
        });


    }

    /**
    class CustomAdapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return count_of_recepies_from_result;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            int counter = 0;
            convertView = getLayoutInflater().inflate(R.layout.custom_search_result, null);
            TextView res1 = (TextView) convertView.findViewById(R.id.result_text_view);
            RatingBar rb = (RatingBar) convertView.findViewById(R.id.result_rating_bar);

            Log.i(TAG, "NEW position = " + position + "text = " + myRecepieList.get(position));

            while(counter <count_of_recepies_from_result) {

                Log.i(TAG, "NEW position = " + position + "text = " + myRecepieList.get(position));

                res1.setText(myRecepieList.get(position));
                rb.setRating(Float.parseFloat(myRecepieList.get(position)));
            }

            return convertView;
        }
    }
    */

}
