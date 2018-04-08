package com.example.dinesh.recepieapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateRecepie extends AppCompatActivity {
    RecepieDB myRecepieDB;
    EditText text_desc, text_ingr, text_inst, text_url;
    Button save, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recepie);

        // initialise datbase class variable
        myRecepieDB = new RecepieDB(this);

        // set view variables
        text_desc = (EditText) findViewById(R.id.descr_text);
        text_ingr = (EditText) findViewById(R.id.ingr_text);
        text_inst = (EditText) findViewById(R.id.inst_text);
        text_url = (EditText) findViewById(R.id.u_t);
        save = (Button) findViewById(R.id.save_recepie);
        cancel = (Button) findViewById(R.id.cancel_recepie);

        save.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myRecepieDB.insertData(text_desc.getText().toString(),
                                text_ingr.getText().toString(),
                                text_inst.getText().toString(),
                                text_inst.getText().toString());

                        if(isInserted == true) {
                            Toast.makeText(CreateRecepie.this, "Recepie saved",Toast.LENGTH_SHORT).show();
                            text_desc.setText(" ");
                            text_ingr.setText(" ");
                            text_inst.setText(" ");
                            text_url.setText(" ");
                        }
                        else {
                            Toast.makeText(CreateRecepie.this, "Database insertion failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }


    public void addRecepieToDB() {
        // invoke function to insert data to database
        //myRecepieDB.insertData()
    }
}
