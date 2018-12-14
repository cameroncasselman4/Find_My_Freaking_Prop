package com.example.administrator.find_my_freaking_prop;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //create MyDatabaseHelper Object
    MyDatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new MyDatabaseHelper(this);
        configureAddItemButton();
        configureFullInventoryButton();
        configureAddPersonButton();
        configureViewPeopleButton();
        configureInStockButton();
        configureOutStockButton();
    }

    public void configureAddPersonButton() {
        Button addPerson = (Button) findViewById(R.id.addPerson);
        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddPerson.class));
            }
        });
    }
    public void configureAddItemButton()
    {
        Button addItem = (Button)findViewById(R.id.addItem);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddItem.class));
            }
        });
    }

    //view all inventory
    public void configureFullInventoryButton() {
        Button fullInventoryButton = (Button)findViewById(R.id.viewList);
        fullInventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fullInventoryIntent = new Intent(MainActivity.this, ViewInventory.class);
                fullInventoryIntent.putExtra("fromButton","viewInventory");
                startActivity(fullInventoryIntent);
            }
        });
    }
    //view inStock inventory
    public void configureInStockButton() {
        Button inStockButton = (Button)findViewById(R.id.viewInStock);
        inStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inStockIntent = new Intent(MainActivity.this, ViewInventory.class);
                inStockIntent.putExtra("fromButton","inStock");
                startActivity(inStockIntent);
            }
        });
    }

    public void configureOutStockButton() {
        Button outOfStockButton = (Button)findViewById(R.id.viewOutOfStock);
        outOfStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent outOfStockIntent = new Intent(MainActivity.this, ViewInventory.class);
                outOfStockIntent.putExtra("fromButton","outOfStock");
                startActivity(outOfStockIntent);
            }
        });
    }



    public void configureViewPeopleButton()
    {
        Button viewPeopleButton = (Button)findViewById(R.id.viewPeopleButton);
        viewPeopleButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, view_people.class));
        }
    });
}
}
