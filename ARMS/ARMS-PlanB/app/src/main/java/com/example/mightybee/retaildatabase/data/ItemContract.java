package com.example.mightybee.retaildatabase.data;

import android.provider.BaseColumns;

/**
 *  The purpose of a contract file is to allow the simple changing of table names, columns, and
 *  predetermined values. This file is for the modification of the item table.
 */

public final class ItemContract {

    private ItemContract() {
    }

    public static class ItemLine implements BaseColumns {
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


    }
}
