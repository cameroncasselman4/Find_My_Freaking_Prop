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
    public static final String ITEM_NAME = "item";
    public static final String ITEM_LOCATION = "itemLocation";
    public static final String ITEM_DESCRIPTION = "itemDescription";
    public static final String ITEM_INSTOCK = "itemInStock";
    public static final String ITEM_DUEDATE = "itemDueDate";

    //Person table
    public static final String TABLE_PERSON = "person";
    public static final String PERSON_ID = "personID"; //foreign key also in Items table
    public static final String PERSON_NAME = "fName";
    public static final String PERSON_PHONE = "phone";
    public static final String PERSON_EMAIL = "email";


    public MyDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //Still need to add foreign key to other table after testin
        db.execSQL("create table " + TABLE_PERSON + " ( " + PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PERSON_NAME + " TEXT, " + PERSON_PHONE + " TEXT, " + PERSON_EMAIL + " TEXT)");
        db.execSQL("create table " + TABLE_ITEMS + " ( " + ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PERSON_ID + " INTEGER, " + ITEM_NAME + " TEXT, " + ITEM_LOCATION + " TEXT, " + ITEM_DESCRIPTION + " TEXT, " + ITEM_INSTOCK + " TEXT NOT NULL DEFAULT 'true', " + ITEM_DUEDATE + " DATE, FOREIGN KEY(" + PERSON_ID +") REFERENCES "+ TABLE_PERSON+"("+PERSON_ID+"))");
        ContentValues contentValues = new ContentValues();
        //insert data because list view doesn't work when only one item is in the database? can't figure out why that is.
        contentValues.put(ITEM_NAME,"Test");
        contentValues.put(ITEM_DESCRIPTION,"Test description");
        contentValues.put(ITEM_LOCATION,"Test location");
        db.insert(TABLE_ITEMS,null, contentValues);
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

    //update query to change the item to out of stock
    public void updateItemInStockFalse(int itemID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_ITEMS + " SET " + ITEM_INSTOCK + " = 'false' WHERE " + ITEM_ID + " = " + itemID;
        db.execSQL(query);
    }

    //update query to change item to in stock
    public void updateItemInStockTrue(int itemID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_ITEMS + " SET " + ITEM_INSTOCK + " = 'true' WHERE " + ITEM_ID + " = " + itemID;
        db.execSQL(query);
    }
    //update query to associate personID with itemID

    public void setPersonIDinItem(int personID, int itemID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_ITEMS + " SET " + PERSON_ID + " = " + personID + " WHERE " + ITEM_ID + " = " + itemID;
        db.execSQL(query);
    }

    public boolean insertpData(String fname, String phone, String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSON_NAME, fname);
        contentValues.put(PERSON_PHONE, phone);
        contentValues.put(PERSON_EMAIL, email);
        long result = db.insert(TABLE_PERSON,null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    //updates items table from editDataActivity
    public void updateItem(String itemName, String location, String description, int id, String oldValue)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_ITEMS + " SET " + ITEM_NAME + " = '" + itemName + "', " + ITEM_LOCATION + " = '" + location + "', " + ITEM_DESCRIPTION + " = '" + description +
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
    //methods for person table

    //query the person table returning resultset of people. This will be used for populating the listview for people
    public Cursor getPeople() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + PERSON_ID + ", " + PERSON_NAME + " FROM " + TABLE_PERSON;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getPeople(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PERSON + " WHERE " + PERSON_NAME + " = " + "'" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //get person associated with the rented item
    public Cursor getRenter(String itemID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + PERSON_NAME + " FROM " + TABLE_PERSON
                    + " JOIN " + TABLE_ITEMS + " USING(" + PERSON_ID + ") WHERE " + ITEM_ID + " = " + itemID;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

}