package com.example.mightybee.retaildatabase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.example.mightybee.retaildatabase.data.ItemContract;

public class DetailView extends AppCompatActivity {

    // Edit field to enter the item name
    private EditText mProductNameEditText;
    // Edit field to enter the product description
    private EditText mProductDescriptionEditText;
    // Edit field to enter the stock status
    private Spinner mProductInStockEditText;
    // Edit field to enter the sale status
    private CheckBox mProductOnSaleEditText;
    // Edit field to enter the cost of the item
    private EditText mProductRetailCostEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read input from
        mProductNameEditText = (EditText) findViewById(R.id.edit_product_name);
        mProductDescriptionEditText = (EditText) findViewById(R.id.edit_description);
        mProductInStockEditText = (EditText) findViewById(R.id.edit_in_stock);
        
    }
}
