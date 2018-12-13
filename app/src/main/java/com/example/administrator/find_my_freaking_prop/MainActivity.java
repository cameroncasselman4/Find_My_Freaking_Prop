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
        configureListButton();
        configureAddPersonButton();
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
    public void configureListButton()
    {
        Button addItem = (Button)findViewById(R.id.viewList);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewInventory.class));
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
