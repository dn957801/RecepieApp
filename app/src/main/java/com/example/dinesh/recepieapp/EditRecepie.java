package com.example.dinesh.recepieapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditRecepie extends AppCompatActivity {

    // Previous intent variables to store details of recepie to be edited
    int recepie_id=0, recepie_fromDB=0;
    String desc_text, ingred_text, inst_text, url_text;
    EditText descr, ingred, inst, url;
    Button save;
    RecepieDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recepie);

        // Extract previous intent variables
        Intent oldIntent = getIntent();
        recepie_id = oldIntent.getIntExtra("RECEPIE_ID" , 0);
        recepie_fromDB = oldIntent.getIntExtra("RECEPIE_ID_FROM_DB" , 0);
        desc_text = oldIntent.getStringExtra("DESCRIPTION_TEXT");
        ingred_text = oldIntent.getStringExtra("INGRED_TEXT");
        inst_text = oldIntent.getStringExtra("INSTRUCTION_TEXT");
        url_text = oldIntent.getStringExtra("URL_TEXT");

        // Initialize view variables
        descr = (EditText) findViewById(R.id.description_text_update);
        ingred = (EditText) findViewById(R.id.ingred_text_update);
        inst = (EditText)findViewById(R.id.instruction_text_update);
        url = (EditText) findViewById(R.id.url_text_update);
        save = (Button)findViewById(R.id.SAVE);

        // Set the view with previous value which can be updated by user
        descr.setText(desc_text);
        ingred.setText(ingred_text);
        inst.setText(inst_text);
        url.setText(url_text);

        db = new RecepieDB(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First retrieve updated values
                desc_text = descr.getText().toString();
                ingred_text = ingred.getText().toString();
                inst_text = inst.getText().toString();
                url_text = url.getText().toString();

                if(desc_text.matches("")) {
                    // empty, so alert and stay there
                    // Alert the user to make sure user actually wants to delete the note book.
                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(EditRecepie.this);
                    alert.setTitle("Alert!!");
                    alert.setMessage("Provide a description");
                    alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });
                    alert.show();
                    return;
                }
                boolean isUpdated = db.updateData(String.valueOf(recepie_fromDB), desc_text, ingred_text, inst_text, url_text);
                if(isUpdated == true) {
                    Toast.makeText(EditRecepie.this, "Data Updated in DB", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(EditRecepie.this, displayRecepie.class);
                    intent.putExtra("RECEPIE_ID", recepie_id);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(EditRecepie.this, "Data Update Failed in DB", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), displayRecepie.class);
        intent.putExtra("RECEPIE_ID", recepie_id);
        startActivity(intent);
    }
}
