package com.example.mightybee.retaildatabase;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mightybee.retaildatabase.data.ItemContract.ItemLine;
import com.example.mightybee.retaildatabase.data.ItemDBHelper;

/**
 * Displays list of pets that were entered and stored in the app.
 */

public class ListView extends AppCompatActivity {

    private static final String TAG = "ListView";

    /**
     * Database helper that will provide us access to the database
     */
    private ItemDBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListView.this, DetailView.class);
                startActivity(intent);
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new ItemDBHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        displayDatabaseInfo();
        readAllItems();
    }

    private void readAllItems() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + ItemLine.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.e(TAG, "readAllItems: " + DatabaseUtils.dumpCursorToString(cursor));

        if (cursor.moveToFirst()) {
            do {
                int idColumnIndex = cursor.getColumnIndex(ItemLine._ID);
                int nameColumnIndex = cursor.getColumnIndex(ItemLine.COLUMN_PRODUCT_NAME);
                int stockColumnIndex = cursor.getColumnIndex(ItemLine.COLUMN_IN_STOCK);
                int saleColumnIndex = cursor.getColumnIndex(ItemLine.COLUMN_ON_SALE);

                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentSale = cursor.getString(saleColumnIndex);
                int currentStock = cursor.getInt(stockColumnIndex);

                Log.e(TAG, "readAllItems: " +
                        currentID + " " +
                        currentName + " " +
                        currentSale + " " +
                        currentStock);

            } while (cursor.moveToNext());
        }
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ItemLine.COLUMN_PRODUCT_ID,
                ItemLine.COLUMN_PRODUCT_NAME,
                ItemLine.COLUMN_ON_SALE,
                ItemLine.COLUMN_RETAIL_COST,
                ItemLine.COLUMN_IN_STOCK};

        // Perform a query on the item table
        Cursor cursor = db.query(
                ItemLine.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        Log.e(TAG, "displayDatabaseInfo: " + DatabaseUtils.dumpCursorToString(cursor));

        TextView displayView = (TextView) findViewById(R.id.text_view_item);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The item table contains <number of rows in Cursor> items.
            // _id - name - sale - cost - stock
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The item table contains " + cursor.getCount() + " items.\n\n");
            displayView.append(ItemLine._ID + " - " +
                    ItemLine.COLUMN_PRODUCT_ID + " - " +
                    ItemLine.COLUMN_PRODUCT_NAME + " - " +
                    ItemLine.COLUMN_ON_SALE + " - " +
                    ItemLine.COLUMN_RETAIL_COST + "-" +
                    ItemLine.COLUMN_IN_STOCK + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(ItemLine._ID);
            int nameColumnIndex = cursor.getColumnIndex(ItemLine.COLUMN_PRODUCT_NAME);
            int saleColumnIndex = cursor.getColumnIndex(ItemLine.COLUMN_ON_SALE);
            int stockColumnIndex = cursor.getColumnIndex(ItemLine.COLUMN_IN_STOCK);

            Log.e("ListView", "displayDatabaseInfo: " +
                    idColumnIndex + " " +
                    nameColumnIndex + " " +
                    saleColumnIndex + " " +
                    stockColumnIndex);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentSale = cursor.getString(saleColumnIndex);
                int currentStock = cursor.getInt(stockColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentSale + " - " +
                        currentStock + " - " +
                        currentID));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */
    private void insertItem() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(ItemLine.COLUMN_PRODUCT_ID, 1);
        values.put(ItemLine.COLUMN_PRODUCT_NAME, "Toto");
        values.put(ItemLine.COLUMN_IN_STOCK, "True");
        values.put(ItemLine.COLUMN_ON_SALE, "True");
        values.put(ItemLine.COLUMN_RETAIL_COST, "34.99");

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(ItemLine.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_catalog options from the res/menu_catalog/menu_catalog.xml file.
        // This adds menu_catalog items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu_catalog option in the app bar overflow menu_catalog
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu_catalog option
            case R.id.action_insert_dummy_data:
                insertItem();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu_catalog option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}