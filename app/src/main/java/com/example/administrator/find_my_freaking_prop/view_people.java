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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class view_people extends AppCompatActivity {

    MyDatabaseHelper db = new MyDatabaseHelper(this);;
    private static final String TAG = "ViewInventory1";
    ListView listView;
    Button allPeople,notRenting,renting;
    TextView buttonPressedLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_people);
        nav();
        buttons();
        populateListView("viewInventorybtn");
    }
    @Override
    public void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_view_people);
        nav();
        buttons();
        populateListView("viewInventorybtn");
    }

    public void nav() {
        configureAddItemButton();
        configureFullInventoryButton();
        configureAddPersonButton();
        configureViewPeopleButton();
    }

    public void configureAddPersonButton() {
        Button addPerson = (Button) findViewById(R.id.addPerson);

        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view_people.this, AddPerson.class));
            }
        });
    }
    public void configureAddItemButton()
    {
        Button addItem = (Button)findViewById(R.id.addItem);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view_people.this, AddItem.class));
            }
        });
    }

    //view all inventory
    public void configureFullInventoryButton() {
        Button fullInventoryButton = (Button)findViewById(R.id.viewList);
        fullInventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fullInventoryIntent = new Intent(view_people.this, ViewInventory.class);
                fullInventoryIntent.putExtra("fromButton","viewInventory");
                startActivity(fullInventoryIntent);
            }
        });
    }

    public void configureViewPeopleButton() {
        Button viewPeopleButton = (Button) findViewById(R.id.viewPeopleButton);
        viewPeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view_people.this, view_people.class));
            }
        });
    }

    private void buttons(){

        //set on click listeners for buttons
        buttonPressedLogo = (TextView) findViewById(R.id.textView2); //allPeople,notRenting,renting;
        allPeople = (Button) findViewById(R.id.allPeople);
        notRenting = (Button) findViewById(R.id.notRenting);
        renting = (Button) findViewById(R.id.rentedUsers);

        allPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonPressed = "viewInventorybtn";
                buttonPressedLogo.setText("All Users");
                populateListView(buttonPressed);
            }
        });

        notRenting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonPressed = "inStockbtn";
                buttonPressedLogo.setText("Users not Renting");
                populateListView(buttonPressed);
            }
        });

        renting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonPressed = "outOfStockbtn";
                buttonPressedLogo.setText("Users Renting");
                populateListView(buttonPressed);
            }
        });

    }


    private void populateListView(String buttonPressed) {
        listView = (ListView) findViewById(R.id.inventoryList);

        Cursor data = db.getPeople();

        if(buttonPressed == "viewInventorybtn") {
            data = db.getPeople();  //query to get all of the data in the items table
        }
        else if(buttonPressed == "inStockbtn") {
            data = db.getRentFalse(); //query to get all data that in stock
        }
        else if(buttonPressed == "outOfStockbtn") {
            data = db.getRentFalse(); //query to get all data out of stock
        }

        //** 1st col = itemID, 2nd col = personID, 3rd = itemName, 4th col = itemLocation 5th col = itemDescription, 6th col = itemInStock
        //ArrayList<ArrayList<String>> itemData = new ArrayList<ArrayList<String>>(); //This is used to store information about the attributes in the table ***leaving out due date for now.
        ArrayList<String>getData = new ArrayList<String>();
        while(data.moveToNext())
        { //loops until there's no more rows
            Log.d(TAG, "reading itemName from database: " + data.getString(1));
            getData.add(data.getString(1));
        }

        Log.d(TAG, "onItemClick: This ID is " + getData);

        final ListAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,getData); //add listData arraylist to list view
        listView.setAdapter(adapter);

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString(); //get current name when list item is clicked
                Cursor data = db.getItemData(name);//get the itemID associated with the name
                String personID = "";
                String itemLocation = "";
                String itemDescription = "";
                String itemInStock = "";
                int itemID = -1;
                if(data.moveToNext()){ //if moving to the next row finds the ID
                    itemID = data.getInt(0);
                    personID = data.getString(1);
                    itemLocation = data.getString(3);
                    itemDescription = data.getString(4);
                    itemInStock = data.getString(5);
                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: This ID is " + itemID);
                    Intent editScreenIntent = new Intent(view_people.this, EditDataActivity.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name", name);
                    editScreenIntent.putExtra("personID", personID);
                    editScreenIntent.putExtra("location", itemLocation);
                    editScreenIntent.putExtra("description", itemDescription);
                    editScreenIntent.putExtra("itemInStock", itemInStock);
                    startActivity(editScreenIntent);
                }
                else{
                    toastMessage("No id associated with that name");
                }
            }
        });*/
    }


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}
