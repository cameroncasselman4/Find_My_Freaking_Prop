package com.example.administrator.find_my_freaking_prop;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewInventory extends AppCompatActivity {

    MyDatabaseHelper db;
    private static final String TAG = "ViewInventory1";
    ListView listView;
    //String items[] = new String [] {"Apple","Orange","Bananna","Grapes"};
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


    private void populateListView(){
        setContentView(R.layout.activity_view_inventory);
        listView = (ListView) findViewById(R.id.inventoryList);
        db = new MyDatabaseHelper(this);
        Cursor data = db.getAllData();  //query to get all of the data in the items table

        int numCols = data.getColumnCount() -1; //database meta data function that returns the number of rows -1
        //** 1st col = itemID, 2nd col = personID, 3rd = itemName, 4th col = itemLocation 5th col = itemDescription, 6th col = itemInStock
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
        Log.d(TAG, "onItemClick: This ID is " + itemData.get(2));

        final ListAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,itemData.get(2)); //add listData arraylist to list view
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    Intent editScreenIntent = new Intent(ViewInventory.this, EditDataActivity.class);
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
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}
