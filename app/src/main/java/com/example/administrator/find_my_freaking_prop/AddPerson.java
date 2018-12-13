package com.example.administrator.find_my_freaking_prop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPerson extends AppCompatActivity {

    MyDatabaseHelper db;
    EditText firstName, lastName, phoneNumber, email;
    Button addData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        db = new MyDatabaseHelper(this);

        firstName = (EditText) findViewById(R.id.firstNameID);
        phoneNumber = (EditText) findViewById(R.id.phoneID);
        email = (EditText) findViewById(R.id.emailID);
        addData = (Button) findViewById(R.id.addPerson);

        submitPeopleIntoDatabase();

    }

    public void submitPeopleIntoDatabase(){
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = db.insertpData(firstName.getText().toString(), phoneNumber.getText().toString(), email.getText().toString());
                if(isInserted) {

                        Toast.makeText(AddPerson.this,"Inventory Updated",Toast.LENGTH_LONG).show();
                        firstName.setText("");
                        phoneNumber.setText("");
                        email.setText("");
                        addData.setText("");
                }

                else
                    Toast.makeText(AddPerson.this,"Failed to Update",Toast.LENGTH_LONG).show();
            }
        });
    }

}