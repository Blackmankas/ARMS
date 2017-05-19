
// The purpose of this file is to make the creation of the database extensible.
// All statements required to instantiate the tables are generalized and referential to the item
// contract itself. This allows for things like internationalization, re-usability and extensibility.


package com.example.mightybee.retaildatabase.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mightybee.retaildatabase.data.ItemContract.ItemLine;


// Database helper for ARMS. Manages db creation and version management.
public class ItemDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ItemDBHelper.class.getSimpleName();

    // Naming the db file
    private static final String DATABASE_NAME = "inventory.db";
    // DB Version. If you change the db schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    // Constructing a new instance of {@link ItemDBHelper}
    public ItemDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // This is called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a string that contains the SQL statement to create the inventory table
        String SQL_CREATE_INVENTORY_TABLE = "CREATE TABLE " + ItemLine.TABLE_NAME
                + " (" + ItemLine._ID + " TEXT PRIMARY KEY AUTOINCREMENT,"
                + ItemLine.COLUMN_PRODUCT_ID + " TEXT UNIQUE NOT NULL,"
                + ItemLine.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + ItemLine.COLUMN_ON_SALE + " BOOL DEFAULT 0, "
                + ItemLine.COLUMN_IN_STOCK + " BOOL DEFAULT 1, "
                + ItemLine.COLUMN_RETAIL_COST + "INTEGER NOT NULL);";

        // Execute the SQL statement100
        db.execSQL(SQL_CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, there is nothing to do here yet.
    }
}

