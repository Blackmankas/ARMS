package com.example.mightybee.retaildatabase.data;

import android.content.ClipData;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mightybee.retaildatabase.data.ItemContract.ItemLine;
/**
 * Created by alfar on 5/4/2017.
 */

// Database helper for ARMS. Manages db creation and version management.
public class ItemDBHelper extends SQLiteOpenHelper{

    public static final String LOG_TAG = ItemDBHelper.class.getSimpleName();

    // Naming the db file
    private static final String DATABASE_NAME = "inventory.db";
    // DB Version. If you change the db schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    // Constructing a new instance of {@link ItemDBHelper}
    public ItemDBHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    // This is called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db){
        // Create a string that contains the SQL statement to create the inventory table
        String SQL_CREATE_INVENTORY_TABLE = "CREATE TABLE " + ItemLine.TABLE_NAME
                + " (" + ItemLine._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ItemLine.COLUMN_PRODUCT_ID+ " INTEGER NOT NULL,"
                + 
    }
}

