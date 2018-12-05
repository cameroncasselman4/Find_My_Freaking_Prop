package com.example.administrator.find_my_freaking_prop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    //Items table
    public static final String DATABASE_NAME = "inventory.db";
    public static final String TABLE_ITEMS = "item";
    public static final String ITEM_ID = "itemID";
    public static final String ITEM_NAME = "itemName";
    public static final String ITEM_LOCATION = "itemLocation";
    public static final String ITEM_DESCRIPTION = "itemDescription";
    public static final String ITEM_INSTOCK = "itemInStock";
    public static final String ITEM_DUEDATE = "itemDueDate";

    //Person table
    public static final String TABLE_PERSON = "person";
    public static final String PERSON_ID = "personID"; //foreign key also in Items table
    public static final String PERSON_FIRST_NAME = "fName";
    public static final String PERSON_LAST_NAME = "lName";
    public static final String PERSON_PHONE = "phone";
    public static final String PERSON_EMAIL = "email";


    public MyDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //Still need to add foreign key to other table after testin
        db.execSQL("create table " + TABLE_PERSON + " ( " + PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PERSON_FIRST_NAME + " TEXT, " + PERSON_LAST_NAME + " TEXT, " + PERSON_PHONE + " TEXT, " + PERSON_EMAIL + " TEXT)");
        db.execSQL("create table " + TABLE_ITEMS + " ( " + ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PERSON_ID + " INTEGER, " + ITEM_NAME + " TEXT, " + ITEM_LOCATION + " TEXT, " + ITEM_DESCRIPTION + " TEXT, " + ITEM_INSTOCK + " BOOLEAN, " + ITEM_DUEDATE + " DATE, FOREIGN KEY(" + PERSON_ID +") REFERENCES "+ TABLE_PERSON+"("+PERSON_ID+"))");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        onCreate(db);
    }

    //getData sql queries

    //setData update inserts and delete
    public boolean insertData(String name, String description, String location)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME,name);
        contentValues.put(ITEM_DESCRIPTION,description);
        contentValues.put(ITEM_LOCATION,location);
        long result = db.insert(TABLE_ITEMS,null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    //this is for use with populating the listview
    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ITEMS;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //returns entire row associated with the item name might cause a problem with duplicate names
    public Cursor getItemData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ITEMS + " WHERE " + ITEM_NAME + " = '" + name + "'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public boolean insertpData(String fname, String lname, String phone, String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSON_FIRST_NAME, fname);
        contentValues.put(PERSON_LAST_NAME, lname);
        contentValues.put(PERSON_PHONE, phone);
        contentValues.put(PERSON_EMAIL, email);
        long result = db.insert(TABLE_PERSON,null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    //updates items table from editDataActivity
    public void updateItem(String itemName, int id, String oldValue)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_ITEMS + " SET " + ITEM_NAME + " = '" + itemName +
                "' WHERE " + ITEM_ID + " = '" + id +
                "' AND " + ITEM_NAME + " = '" + oldValue + "'";
        db.execSQL(query);
    }

    //Deletes item from EditDataActivity
    public void deleteItem(int id, String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_ITEMS + " WHERE " + ITEM_ID + " = '" + id +
                "' AND " + ITEM_NAME + " = '" + name + "'";
        db.execSQL(query);
    }

}