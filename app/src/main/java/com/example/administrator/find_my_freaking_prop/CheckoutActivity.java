package com.example.administrator.find_my_freaking_prop;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    MyDatabaseHelper db;
    private static final String TAG = "CheckoutActivity1";
    ListView listView;
    //this is passed from the previous activity
    private int selectedItemID,selectedPersonID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateListView();
    }
    @Override
    public void onRestart() {
        super.onRestart();
        populateListView();
    }


    public void populateListView() {
        setContentView(R.layout.activity_view_people);
        Intent receivedIntent = getIntent();
        selectedItemID = receivedIntent.getIntExtra("id",-1);
        listView = (ListView) findViewById(R.id.peopleList);
        db = new MyDatabaseHelper(this);
        Cursor data = db.getPeople();

        //populate with a list of names for the listview adapter
        ArrayList<String> personNames = new ArrayList<String>(); //For storing the names of people

        while (data.moveToNext()) {
            personNames.add(data.getString(1));
        }

        //Log.d(TAG, "onItemClick: This ID is " + getRowData);
        final ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, personNames); //add listData arraylist to list view
        listView.setAdapter(adapter); //set adapter to listview
        //set on click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Cursor peopleData = db.getPeople(name);
                //define variables to be instantiated below during the database query
                int personID = -1;
                String personName = "";
                String personPhone = "";
                String personEmail = "";

                while(peopleData.moveToNext()) {
                    personID = peopleData.getInt(0);
                    personName = peopleData.getString(1);
                    personPhone = peopleData.getString(2);
                    personEmail = peopleData.getString(3);
                }
                //set personID in Table_Items to the person id clicked
                db.setPersonIDinItem(personID,selectedItemID);
                //change in stock to false
                db.updateItemInStockFalse(selectedItemID);
                //set toast notifying the user that person is now renting the item
                toastMessage("Item checked out to " + personName);
                //look into what happens when holding the button down?
                finish();

            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}