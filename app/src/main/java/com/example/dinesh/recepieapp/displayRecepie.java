package com.example.dinesh.recepieapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class displayRecepie extends AppCompatActivity {

    RecepieDB db;
    int recepie_id=0, recepie_id_fromDB = 0;
    String TAG="displayRecepie";
    TextView etRecepieX, etDTD, etIngredTD, etInstrTD, etURL;
    Button etEdit, etDelete, etRate;
    String desc_text, ingred_text,instr_text, url_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recepie);

        etRecepieX = (TextView) findViewById(R.id.recepieX);
        etDTD = (TextView) findViewById(R.id.description_text_display);
        etIngredTD = (TextView )findViewById(R.id.ingred_text_display);
        etInstrTD = (TextView) findViewById(R.id.instruction_text_display);
        etURL = (TextView) findViewById(R.id.url_text_display);

        etEdit = (Button)findViewById(R.id.SAVE);
        etDelete = (Button)findViewById(R.id.delete);
        etRate = (Button)findViewById(R.id.rate);

        db = new RecepieDB(this);
        Intent oldIntent = getIntent();
        recepie_id = oldIntent.getIntExtra("RECEPIE_ID" , 0);
        Log.i(TAG,"Recepie ID retrieved = " + recepie_id);
        displayRecepie(recepie_id);


        etEdit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(displayRecepie.this, EditRecepie.class);
                        intent.putExtra("RECEPIE_ID", recepie_id);
                        intent.putExtra("RECEPIE_ID_FROM_DB", recepie_id_fromDB);
                        intent.putExtra("DESCRIPTION_TEXT", desc_text );
                        intent.putExtra("INGRED_TEXT", ingred_text);
                        intent.putExtra("INSTRUCTION_TEXT", instr_text);
                        intent.putExtra("URL_TEXT", url_text);
                        startActivity(intent);
                    }
                }
        );

        etDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Alert the user to make sure user actually wants to delete the note book.
                        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(displayRecepie.this);
                        alert.setTitle("Alert!!");
                        alert.setMessage("Delete Recepie ?");
                        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                Log.i(TAG, "VICHITRA recepie to be deleted = " + recepie_id_fromDB);
                                int no_of_rows_deleted = db.deleteData(String.valueOf(recepie_id_fromDB));
                                if(no_of_rows_deleted == 0) {
                                    Toast.makeText(displayRecepie.this, "Delete operation failed", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(displayRecepie.this, "Delete successful", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), AllRecepieList.class);
                                    startActivity(intent);
                                }

                            }
                        });
                        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Just dismiss the dialogue, don't do anything.
                                dialog.dismiss();
                            }
                        });
                        alert.show();
                    }
                }
        );


    }

    public void displayRecepie( int recepie_id ) {
        int local_counter = 0;
        Cursor result = db.getRecepiesFromDatabase();
        if(result.getCount() == 0) {
            // No recepies available
            displayTheRecepie("Error", "No recepies yet");
            // show empty
            return;
        }

        while(local_counter <= recepie_id) {
            local_counter++;
            result.moveToNext();
        }

        // Set the recepie details
        recepie_id_fromDB = Integer.parseInt(result.getString(0));
        etRecepieX.setText(result.getString(0));
        Log.i(TAG, "VICHITRA recepie_id_fromDB = " + recepie_id_fromDB);
        Log.i(TAG, "VICHITRA recepie_id received in function = " + recepie_id);

        desc_text = result.getString(1);
        etDTD.setText(result.getString(1));

        ingred_text = result.getString(2);
        etIngredTD.setText(result.getString(2));

        instr_text = result.getString(3);
        etInstrTD.setText(result.getString(3));

        url_text = result.getString(4);
        etURL.setText(result.getString(4));


    }

    public void displayTheRecepie(String title, String recepieDetail) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(recepieDetail);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), AllRecepieList.class);
        startActivity(intent);
    }
}
