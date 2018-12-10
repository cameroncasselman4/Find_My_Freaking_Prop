package com.example.administrator.find_my_freaking_prop;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    MyDatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        configureSpinner();
        configureAddPersonButton();
    }

    public void configureSpinner() {
        db = new MyDatabaseHelper(this);
        Spinner spinner = (Spinner) findViewById(R.id.personSpinner);
        Cursor data = db.getPeople();  //query to get all data from the person table

        int numCols = data.getColumnCount(); //database meta data function that returns the number of rows -1
        ArrayList<ArrayList<String>> itemData = new ArrayList<ArrayList<String>>(); //This is used to store information about the attributes in the table ***leaving out due date for now.
        ArrayList<String>getRowData = new ArrayList<String>();
        for(int i = 0; i <= numCols; i++) //loop for each column
        {
            while(data.moveToNext())
            { //loops until there's no more rows
                getRowData.add(data.getString(i));
            }
            //add getRowData to 2d arrayList
            itemData.add(new ArrayList<String>(getRowData));
            //clear getRowData for next use
            getRowData.clear();
            //move cursor back to the top
            data.moveToFirst();
        }
        ArrayAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,itemData.get(1)); //add listData arraylist to list view
        spinner.setAdapter(adapter);

    }


    public void configureAddPersonButton() {
        Button addPerson = (Button) findViewById(R.id.addPerson);
        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckoutActivity.this, AddPerson.class));
            }
        });
    }
}
