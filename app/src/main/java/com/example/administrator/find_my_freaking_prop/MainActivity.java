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
        configureViewItemsButton();
        configureNextButton();
    }

    public void configureNextButton()
    {
        Button addItem = (Button)findViewById(R.id.addItem);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddItem.class));
            }
        });
    }

    public void configureViewItemsButton()
    {
        Button viewItems = (Button) findViewById(R.id.viewItem);
        viewItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Cursor res = myDb.getAllData();
               if(res.getCount() == 0){
                   showMessage("Error","Nothing found");
                   Toast.makeText(MainActivity.this,"No Items in inventory",Toast.LENGTH_LONG).show();
                   return;
               }
               StringBuffer buffer = new StringBuffer();
               while(res.moveToNext()){
                   buffer.append("Id: " + res.getString(0) + "\n");
                   buffer.append("PersonID: " + res.getString(1) + "\n");
                   buffer.append("Item Name: " + res.getString(2) + "\n");
               }

               //show data
                showMessage("Data", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
