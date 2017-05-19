package com.example.mightybee.retaildatabase;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mightybee.retaildatabase.data.ItemContract;
import com.example.mightybee.retaildatabase.data.ItemDBHelper;

public class DetailView extends AppCompatActivity {

    public boolean stockStatus;
    // Edit field to enter the item name
    private EditText mProductNameEditText;
    // Edit field to enter the productID
    private EditText mProductIDEditText;
    // Edit field to enter the cost of the item
    private EditText mProductRetailCostEditText;
    // Edit field to enter the stock status
    private Spinner mProductStockSpinner;
    // Edit field to enter the sale status
    private CheckBox mProductOnSaleCheckbox;
    private int mStock = ItemContract.ItemLine.STOCK_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        // Find all relevant views that we will need to read input from
        mProductNameEditText = (EditText) findViewById(R.id.edit_product_name);
        mProductIDEditText = (EditText) findViewById(R.id.edit_product_ID);
        mProductRetailCostEditText = (EditText) findViewById(R.id.edit_product_cost);

        mProductStockSpinner = (Spinner) findViewById(R.id.spinner_stock);
        mProductOnSaleCheckbox = (CheckBox) findViewById(R.id.on_sale_checkbox);

        setupSpinner();
    }


    // Setup the dropdown spinner that allows the selection of items

    private void setupSpinner() {
        // Create an adapter for the spinners so that the options come from the string array

        final ArrayAdapter inStockSpinnerAdapter =
                ArrayAdapter.createFromResource(this, R.array.array_stock_options, android.R.layout.simple_spinner_item);

        // Specify the dropdown layout style
        inStockSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mProductStockSpinner.setAdapter(inStockSpinnerAdapter);

        // Set the int mSelected to the const values
        mProductStockSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.IN_STOCK))) {
                        mStock = ItemContract.ItemLine.IN_STOCK;
                        stockStatus = true;
                    } else if (selection.equals(getString(R.string.NOT_IN_STOCK))) {
                        mStock = ItemContract.ItemLine.NOT_IN_STOCK;
                        stockStatus = false;
                    } else {
                        mStock = ItemContract.ItemLine.STOCK_UNKNOWN;
                        stockStatus = false;
                    }
                }
            }

            // Because AdapterView is abstract, onNothing needs to be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mStock = ItemContract.ItemLine.STOCK_UNKNOWN;
            }
        });
    }


    // Get input from editor and save it in the db
    private void insertItem() {
        // Read from the input fields, elim leading or trailing white space
        String nameString = mProductNameEditText.getText().toString().trim();
        String productIDString = mProductIDEditText.getText().toString().trim();
        String productCostString = mProductRetailCostEditText.getText().toString().trim();
        boolean onSaleStatus = mProductOnSaleCheckbox.isChecked();
        boolean stockString = stockStatus;


        // Setup db helper
        ItemDBHelper mItemDBHelper = new ItemDBHelper(this);
        // Put the db in write mode
        SQLiteDatabase db = mItemDBHelper.getWritableDatabase();

        // Create content values obj where the column name is the keys and the item attrib
        // from the editor are values

        ContentValues values = new ContentValues();
        values.put(ItemContract.ItemLine.COLUMN_PRODUCT_NAME, nameString);
        values.put(ItemContract.ItemLine.COLUMN_PRODUCT_ID, productIDString);
        values.put(ItemContract.ItemLine.COLUMN_RETAIL_COST, productCostString);
        values.put(ItemContract.ItemLine.COLUMN_IN_STOCK, stockString);
        values.put(ItemContract.ItemLine.COLUMN_ON_SALE, onSaleStatus);

        // Insert a new row for this item, return the ID of that new row
        long newRowID = db.insert(ItemContract.ItemLine.TABLE_NAME, null, values);

        // Show a toast message saying success or not
        if (newRowID == -1) {
            Toast.makeText(this, "Error with saving item!", Toast.LENGTH_SHORT).show();
        } else {
            // otherwise it was a success and display the row id!
            Toast.makeText(this, "Item saved, row  ID: " + newRowID, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_catalog options from the res/menu_catalog/menu_editor.xml file.
        // This adds menu_catalog items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu_catalog option in the app bar overflow menu_catalog
        switch (item.getItemId()) {
            case R.id.action_save:
                // Save item to db
                insertItem();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the delete menu_catalog option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the UP arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity(catalog/main activity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}



















