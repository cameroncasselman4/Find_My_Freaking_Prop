package com.example.administrator.find_my_freaking_prop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddItem extends AppCompatActivity {

    MyDatabaseHelper db;
    EditText itemName, itemDescription, itemLocation;
    Button addData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        db = new MyDatabaseHelper(this);

        itemName = (EditText) findViewById(R.id.itemName);
        itemDescription = (EditText) findViewById(R.id.itemDescription);
        itemLocation = (EditText) findViewById(R.id.itemLocation);
        addData = (Button) findViewById(R.id.addItem);

        submitItemsIntoDatabase();

    }

    public void submitItemsIntoDatabase(){
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = db.insertData(itemName.getText().toString(),itemDescription.getText().toString(),itemLocation.getText().toString());
                if(isInserted)
                    Toast.makeText(AddItem.this,"Inventory Updated",Toast.LENGTH_LONG).show();

                else
                    Toast.makeText(AddItem.this,"Failed to Update",Toast.LENGTH_LONG).show();
            }
        });
    }

}