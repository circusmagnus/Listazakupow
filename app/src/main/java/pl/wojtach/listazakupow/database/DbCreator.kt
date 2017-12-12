package pl.wojtach.listazakupow.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DbCreator(context: Context)
    : SQLiteOpenHelper(context, DbContract.DATABASE_NAME, null, DbContract.DATABASE_VERSION) {

    companion object {
        val createShoppingListTableStatement = (
                "CREATE TABLE " + DbContract.ShoppingListsTable.name
                        + " ("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + DbContract.ShoppingListsTable.Columns.timestamp + " INTEGER NOT NULL,"
                        + DbContract.ShoppingListsTable.Columns.name + " TEXT,"
                        + DbContract.ShoppingListsTable.Columns.isArchived + " INTEGER DEFAULT 1 NOT NULL"
                        + ");"
                )

        val createShoppingItemsTableStatement = (
                "CREATE TABLE " + DbContract.ShoppingItemsTable.name
                        + " ("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + DbContract.ShoppingItemsTable.Columns.shoppingItem + " TEXT, "
                        + DbContract.ShoppingItemsTable.Columns.shoppingListId + " INTEGER NOT NULL, "
                        + "FOREIGN KEY(${DbContract.ShoppingItemsTable.Columns.shoppingListId}) REFERENCES ${DbContract.ShoppingListsTable.name}(${BaseColumns._ID})"
                        + ");"
                )
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createShoppingListTableStatement)
        db.execSQL(createShoppingItemsTableStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.ShoppingListsTable.name)
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.ShoppingItemsTable.name)
        db.execSQL(createShoppingListTableStatement)
        db.execSQL(createShoppingItemsTableStatement)
    }
}