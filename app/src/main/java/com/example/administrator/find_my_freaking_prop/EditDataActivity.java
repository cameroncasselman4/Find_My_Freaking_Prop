package com.example.administrator.find_my_freaking_prop;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditDataActivity extends AppCompatActivity {

    private static final String TAG = "EditDataActivity1";
    private Button btnSave,btnDelete,btnCheckout;
    private EditText getItemName,getItemLocation,getItemDescription,getItemInStock;
    private TextView getPersonID;
    MyDatabaseHelper db;
    private String selectedItemName,selectedPersonID,selectedItemLocation,selectedItemDescription,selectedItemInStock,getSelectedItemID;
    private int selectedItemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Still need a method that will populate the user renting the item -Currently working on Cam
        //Still need to change the checkout button function to do something else when an item is already checked out. It needs to check in an item instead.
        
        setContentView(R.layout.activity_edit_data);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnCheckout = (Button) findViewById(R.id.btnCheckout);
        getItemName = (EditText) findViewById(R.id.getItemName);
        getPersonID = (TextView) findViewById(R.id.getPersonID);
        getItemLocation = (EditText) findViewById(R.id.getItemLocation);
        getItemDescription = (EditText) findViewById(R.id.getItemDescription);
        //getItemInStock = (EditText) findViewById(R.id.getItemInStock); we don't have anything on the ui for this yet
        db = new MyDatabaseHelper(this);

        //get the intent extra from the ViewInventory
        Intent receivedIntent = getIntent();

        //get the itemID, name, personid, location, and description passed from the previous intent
        selectedItemID = receivedIntent.getIntExtra("id",-1);
        selectedItemName = receivedIntent.getStringExtra("name");
        selectedPersonID = receivedIntent.getStringExtra("personID");
        selectedItemLocation = receivedIntent.getStringExtra("location");
        selectedItemDescription = receivedIntent.getStringExtra("description");
        //call the get person method
        String selectedPersonName = getPersonName(String.valueOf(selectedItemID));

        //Log.d(TAG, "onItemClick: This name is " + selectedPersonName);

        //set editItem line to selected name
        getItemName.setText(selectedItemName);
        getItemLocation.setText(selectedItemLocation);
        getItemDescription.setText(selectedItemDescription);

        //this checks the personId associated with the item. If it's null then the text is set
        if(selectedPersonID == null)
            getPersonID.setText("Item is available for rent");
        else
            getPersonID.setText(selectedPersonID);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = getItemName.getText().toString();
                //Log.d(TAG, "onItemClick: This name is " + item);
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
                //Log.d(TAG, "onItemClick: This name is " + "helloworld");
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

        //send to checkout activity
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkoutScreenIntent = new Intent(EditDataActivity.this, CheckoutActivity.class);
                //startActivity(new Intent(EditDataActivity.this, CheckoutActivity.class));
                checkoutScreenIntent.putExtra("id",selectedItemID);
                //checkoutScreenIntent.putExtra("personID",selectedPersonID);
                startActivity(checkoutScreenIntent);
            }
        });
    }

    private String getPersonName(String itemID){
        //query the database for the person's name
        String personName = "Item available for rent";
        db = new MyDatabaseHelper(this);
        Cursor data = db.getRenter(itemID);
        Log.d("stuff1", "data.movetonext " + data.moveToNext());
        if(data.moveToNext()) {
            Log.d("stuff1", "data.movetonext " + data.getString(0));
            if(data.getString(0) != null)
                personName = data.getString(0);
        }

       return personName;
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
