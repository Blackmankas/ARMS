package com.example.mightybee.retaildatabase.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 *  The purpose of a contract file is to allow the simple changing of table names, columns, and
 *  predetermined values. This file is for the modification of the item table.
 */

public final class ItemContract {

    private ItemContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.example.mightybee.retaildatabase";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ITEM_INVENTORY = "iteminventory";

    public static class ItemLine implements BaseColumns {

        //Content URI to access data in book inventory provider
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,
                PATH_ITEM_INVENTORY);

        //MIME type for multiple items
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + "/" + PATH_ITEM_INVENTORY;

        //MIME type for single items
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + "/" + PATH_ITEM_INVENTORY;

        // Product name
        // Type: String
        public static final String TABLE_NAME = "product_listing";

        // Product ID
        // Type: Int
        public static final String COLUMN_PRODUCT_ID = "product_id";

        // Product description
        // Type: String
        public static final String COLUMN_PRODUCT_NAME = "name";

        // Product sale status
        // Type: bool
        public static final String COLUMN_ON_SALE = "on_sale";

        // Product price
        // Type: int
        public static final String COLUMN_RETAIL_COST = "retail_price";

        // Product stock status
        // Type: bool
        public static final String COLUMN_IN_STOCK = "in_stock";


        // Set possible values for in stock
        public static final int IN_STOCK = 1;
        public static final int NOT_IN_STOCK = 0;
        public static final int STOCK_UNKNOWN = 2;
    }
}
