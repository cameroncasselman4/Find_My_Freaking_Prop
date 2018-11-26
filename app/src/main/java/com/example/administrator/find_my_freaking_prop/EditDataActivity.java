package com.example.administrator.find_my_freaking_prop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditDataActivity extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";
    private Button btnSave,btnDelete;
    private EditText editable_item;
    MyDatabaseHelper db;
    private String selectedName;
    private int selectedID;

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
        selectedID = receivedIntent.getIntExtra("id",-1);

        //get the itemName that was passed from the previous intent
        selectedName = receivedIntent.getStringExtra("name");

        //set editItem line to selected name
        editable_item.setText(selectedName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = editable_item.getText().toString();
                if(!item.equals("")){

                }else{
                    toastMessage("You must enter a name");
                }
            }
        });

    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
