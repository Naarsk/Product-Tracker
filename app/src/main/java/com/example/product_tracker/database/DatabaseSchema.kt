package com.example.product_tracker.database

class DatabaseSchema {

    object ProductTable {
        const val TABLE_NAME = "product_table"
        const val PRIMARY_KEY = "key"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PRICE = "price"
        const val COLUMN_QUANTITY = "quantity"
        const val COLUMN_IMAGE_URL = "image_url"
        const val COLUMN_TYPE = "type"
        const val COLUMN_COLOR = "color"
        const val COLUMN_CREATED_AT = "created_at"
        const val COLUMN_UPDATED_AT = "updated_at"
    }

    object SaleTable{
        const val TABLE_NAME = "sales_table"
        const val PRIMARY_KEY = "key"
        const val PRODUCT_KEY = "product_key"
        const val COLUMN_QUANTITY_SOLD = "quantity_sold"
        const val COLUMN_DATE = "sale_date"
        const val COLUMN_PRICE_SOLD = "price_sold"
        const val COLUMN_DISCOUNT = "discount_amount"
        const val COLUMN_CREATED_AT = "created_at"
        const val COLUMN_UPDATED_AT = "updated_at"
    }

    object RefurbishingTable{
        const val TABLE_NAME = "sales_table"
        const val PRIMARY_KEY = "key"
        const val PRODUCT_KEY = "product_key"
        const val COLUMN_QUANTITY_ADDED = "quantity_added"
        const val COLUMN_DATE = "updated_at"
    }
}