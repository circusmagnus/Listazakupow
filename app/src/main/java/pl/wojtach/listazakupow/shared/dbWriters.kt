package pl.wojtach.listazakupow.shared

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
import pl.wojtach.listazakupow.database.DbContract
import pl.wojtach.listazakupow.database.DbHelper
import pl.wojtach.listazakupow.list.ShoppingList

/**
 * Created by Lukasz on 09.12.2017.
 */
fun saveShoppingListToSQL(context: Context, shoppingList: ShoppingList): Long =
        DbHelper(context).writableDatabase.saveInTransaction { db ->
            db.insertWithOnConflict(
                    DbContract.ShoppingListsTable.name,
                    null,
                    getContentValues(shoppingList),
                    CONFLICT_REPLACE
            )
        }

private fun SQLiteDatabase.saveInTransaction(operation: (SQLiteDatabase) -> Long): Long {
    var insertedItemId = -1L
    with(this) {
        beginTransaction()
        insertedItemId = operation(this)
        setTransactionSuccessful()
        endTransaction()
    }
    return insertedItemId
}

private fun getContentValues(shoppingList: ShoppingList): ContentValues =
        ContentValues().apply {
            put(DbContract.ShoppingListsTable.Columns.name, shoppingList.name)
            put(DbContract.ShoppingListsTable.Columns.timestamp, shoppingList.timestamp)
            put(DbContract.ShoppingListsTable.Columns.isArchived, shoppingList.isArchived)
        }