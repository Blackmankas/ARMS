package com.example.mightybee.retaildatabase.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import static android.R.attr.id;

/**
 * {@link ContentProvider} for Items app.
 */
public class ItemProvider extends ContentProvider {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = ItemProvider.class.getSimpleName();

    /**
     * Initialize the provider and the database helper object.
     */

    //Database Helper Object
    private ItemDBHelper mDbHelper;

    private static final int ITEMS = 100;

    /**
     * URI matcher code for the content URI for a single Item in the Items table
     */
    private static final int ITEM_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // TODO: Add 2 content URIs to URI matcher
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_ITEM_INVENTORY, ITEMS);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_ITEM_INVENTORY + "/#", ITEM_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new ItemDBHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                // For the ItemS code, query the Items table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the Items table.
                // TODO: Perform database query on Items table
                cursor = database.query(ItemContract.ItemLine.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case ITEM_ID:
                // For the Item_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.Items/Items/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = ItemContract.ItemLine._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // This will perform a query on the Items table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(ItemContract.ItemLine.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        //Set notification URI on the Cursor
        //so we know what content URI the cursor was created for.
        //If the data at this URI changes, then we know we need to update the Cursor

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a Item into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertPet(Uri uri, ContentValues values) {

        String name = values.getAsString(ItemContract.ItemLine.COLUMN_PRODUCT_NAME);
        Integer cost = values.getAsInteger(ItemContract.ItemLine.COLUMN_RETAIL_COST);
        Integer in_stock = values.getAsInteger(ItemContract.ItemLine.COLUMN_IN_STOCK);
        Integer product_id = values.getAsInteger(ItemContract.ItemLine.COLUMN_PRODUCT_ID);
        
        //Check name is not null
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Item requires a name");
        }

        //Check if gender is valid or not null
        if (product_id == null ){
            throw new IllegalArgumentException("Item requires ID");
        }

        //Make sure weight is not null AND not less than 0
        if (cost == null) {
            throw new IllegalArgumentException("Item requires cost");
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(ItemContract.ItemLine.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        //Notify all listeners that the data has changed for the Item content URI
        //uri: content://com.example.androids.Items/Items
        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return updateItem(uri, contentValues, selection, selectionArgs);
            case ITEM_ID:
                // For the Item_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = ItemContract.ItemLine._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateItem(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update Items in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more Items).
     * Return the number of rows that were successfully updated.
     */
    private int updateItem(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link ItemLine#COLUMN_Item_NAME} key is present,
        // check that the name value is not null.
        if (values.containsKey(ItemContract.ItemLine.COLUMN_PRODUCT_NAME)) {
            String name = values.getAsString(ItemContract.ItemLine.COLUMN_PRODUCT_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Item requires a name");
            }
        }

        // TODO: Add more logic checking
        // If the {@link ItemLine# COLUMN_ITEM_ID} key is present,
        // check that the weight value is valid.
        if (values.containsKey(ItemContract.ItemLine.COLUMN_PRODUCT_ID)) {
            // Check that the weight is greater than or equal to 0
            Integer product_id = values.getAsInteger(ItemContract.ItemLine.COLUMN_PRODUCT_ID);
            if (product_id != null && product_id < 0) {
                throw new IllegalArgumentException("Item requires valid ID");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // No need to check the breed, any value is valid (including null).

        // TODO: Update the selected Items in the Items database table with the given ContentValues
        int rowsUpdated = database.update(ItemContract.ItemLine.TABLE_NAME, values, selection, selectionArgs);

        //If 1 or more rows were updated, then notify all listeners that the data at the given
        //URI has changed

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        //Track rows deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                // Delete all rows that match the selection and selection args
                //return database.delete(ItemContract.ItemLine.TABLE_NAME, selection, selectionArgs);
                rowsDeleted = database.delete(ItemContract.ItemLine.TABLE_NAME, selection, selectionArgs);
                break;
            case ITEM_ID:
                // Delete a single row given by the ID in the URI
                selection = ItemContract.ItemLine._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                //return database.delete(ItemContract.ItemLine.TABLE_NAME, selection, selectionArgs);
                rowsDeleted = database.delete(ItemContract.ItemLine.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        //If 1 or more rows were deleted, then notify all listeners that the data at the given
        //URI has changed

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return ItemContract.ItemLine.CONTENT_LIST_TYPE;
            case ITEM_ID:
                return ItemContract.ItemLine.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }


}