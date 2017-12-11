package pl.wojtach.listazakupow.shared

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
import pl.wojtach.listazakupow.database.DbContract
import pl.wojtach.listazakupow.database.DbHelper
import pl.wojtach.listazakupow.details.ShoppingItem
import pl.wojtach.listazakupow.list.ShoppingList

/**
 * Created by Lukasz on 09.12.2017.
 */
fun saveShoppingListToSqlDb(context: Context, shoppingList: ShoppingList): Long =
        DbHelper(context).writableDatabase.insertWithOnConflict(
                    DbContract.ShoppingListsTable.name,
                    null,
                    getContentValuesForList(shoppingList),
                    CONFLICT_REPLACE
            )

fun saveShoppingItemToSqlDb(context: Context, shoppingItem: ShoppingItem): Long =
        DbHelper(context).writableDatabase.insertWithOnConflict(
                    DbContract.ShoppingItemsTable.name,
                    null,
                    getContentValuesForItem(shoppingItem),
                    CONFLICT_REPLACE
            )

fun SQLiteDatabase.saveInTransaction(operation: (SQLiteDatabase) -> Long): Long {
    var insertedItemId = -1L
    with(this) {
        beginTransaction()
        insertedItemId = operation(this)
        setTransactionSuccessful()
        endTransaction()
    }
    return insertedItemId
}

private fun getContentValuesForList(shoppingList: ShoppingList): ContentValues =
        ContentValues().apply {
            put(DbContract.ShoppingListsTable.Columns.name, shoppingList.name)
            put(DbContract.ShoppingListsTable.Columns.timestamp, shoppingList.timestamp)
            put(DbContract.ShoppingListsTable.Columns.isArchived, shoppingList.isArchived)
        }

private fun getContentValuesForItem(shoppingItem: ShoppingItem): ContentValues =
        ContentValues().apply {
            put(DbContract.ShoppingItemsTable.Columns.shoppingListId, shoppingItem.shoppingListId)
            put(DbContract.ShoppingItemsTable.Columns.shoppingItem, shoppingItem.item)
        }