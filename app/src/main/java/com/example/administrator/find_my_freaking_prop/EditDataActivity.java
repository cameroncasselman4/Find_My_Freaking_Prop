package com.example.administrator.find_my_freaking_prop;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditDataActivity extends AppCompatActivity {

    private static final String TAG = "EditDataActivity1";
    private Button btnSave,btnDelete;
    private EditText editable_item;
    MyDatabaseHelper db;
    private String selectedItemName;
    private int selectedItemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_data);
        btnSave = (Button) findViewById(R.id.btnDelete);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        editable_item = (EditText) findViewById(R.id.editable_item);
        db = new MyDatabaseHelper(this);

        //get the intent extra from the ViewInventory
        Intent receivedIntent = getIntent();

        //get the itemID that was passed from the previous intent
        selectedItemID = receivedIntent.getIntExtra("id",-1);

        //get the itemName that was passed from the previous intent
        selectedItemName = receivedIntent.getStringExtra("name");

        //set editItem line to selected name
        editable_item.setText(selectedItemName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = editable_item.getText().toString();
                Log.d(TAG, "onItemClick: This name is " + item);
                if(!item.equals("")){
                    db.updateItem(item,selectedItemID,selectedItemName);
                }else{
                    toastMessage("You must enter a name");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onItemClick: This name is " + "helloworld");
                AlertDialog.Builder a_builder = new AlertDialog.Builder(EditDataActivity.this);
                a_builder.setMessage("Are you sure you want to delete " + selectedItemName + " from inventory?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.deleteItem(selectedItemID,selectedItemName);
                                toastMessage("Removed from inventory");
                                finish();
                                //startActivity(new Intent(EditDataActivity.this, ViewInventory.class));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("Confirm");
                alert.show();
            }


        });
    }


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
