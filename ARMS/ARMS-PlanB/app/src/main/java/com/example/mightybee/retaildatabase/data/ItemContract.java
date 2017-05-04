package com.example.mightybee.retaildatabase.data;

import android.provider.BaseColumns;

/**
 * Created by mightybee on 5/4/17.
 */

public final class ItemContract {

    private ItemContract(){}

    public static class ItemLine implements BaseColumns{
        // Product name
        // Type: String
        public static final String TABLE_NAME = "product_listing";

        // Product ID
        // Type: Int
        public static final String COLUMN_PRODUCT_ID = "product_id";

        // Product description
        // Type: String
        public static final String COLUMN_DESCRIPTION = "description";

        // Product sale status
        // Type: bool
        public static final String COLUMN_ON_SALE = "on_sale";

        // Product price
        // Type: int
        public static final String COLUMN_RETAIL_COST = "retail_price";

        // Product stock status
        // Type: bool
        public static final String COLUMN_IN_STOCK = "in_stock";

        // Possible values for stock status
        public static final int IN_STOCK = 1;
        public static final int NOT_IN_STOCK = 0;

        // Possible values for sale status
        public static final int ON_SALE = 1;
        public static final int NOT_ON_SALE = 0;


    }
}
