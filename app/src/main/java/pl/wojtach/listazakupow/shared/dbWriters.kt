package pl.wojtach.listazakupow.shared

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
import android.provider.BaseColumns
import pl.wojtach.listazakupow.database.DatabaseHolder
import pl.wojtach.listazakupow.database.DbContract
import pl.wojtach.listazakupow.details.ShoppingItem
import pl.wojtach.listazakupow.list.ShoppingList

/**
 * Created by Lukasz on 09.12.2017.
 */
fun saveShoppingListToSqlDb(context: Context, shoppingList: ShoppingList): Long =
        DatabaseHolder.getInstance(context).writableDatabase.insertWithOnConflict(
                    DbContract.ShoppingListsTable.name,
                    null,
                    getContentValuesForList(shoppingList),
                    CONFLICT_REPLACE
            )

fun saveShoppingItemToSqlDb(context: Context, shoppingItem: ShoppingItem): Long =
        DatabaseHolder.getInstance(context).writableDatabase.insertWithOnConflict(
                    DbContract.ShoppingItemsTable.name,
                    null,
                    getContentValuesForItem(shoppingItem),
                    CONFLICT_REPLACE
            )

fun deleteShoppingItemFromSqlDb(context: Context, shoppingItem: ShoppingItem) =
        DatabaseHolder.getInstance(context).writableDatabase.delete(
                DbContract.ShoppingItemsTable.name,
                BaseColumns._ID,
                arrayOf(shoppingItem.id.toString())
        )

fun android.database.sqlite.SQLiteDatabase.saveInTransaction(operation: (android.database.sqlite.SQLiteDatabase) -> Long): Long {
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
            shoppingList.id.takeUnless { it == -1L }.let { put(BaseColumns._ID, it) }
            put(DbContract.ShoppingListsTable.Columns.name, shoppingList.name)
            put(DbContract.ShoppingListsTable.Columns.timestamp, shoppingList.timestamp)
            put(DbContract.ShoppingListsTable.Columns.isArchived, shoppingList.isArchived)
        }

private fun getContentValuesForItem(shoppingItem: ShoppingItem): ContentValues =
        ContentValues().apply {
            shoppingItem.id.takeUnless { it == -1L }.let { put(BaseColumns._ID, it) }
            put(DbContract.ShoppingItemsTable.Columns.shoppingListId, shoppingItem.shoppingListId)
            put(DbContract.ShoppingItemsTable.Columns.shoppingItem, shoppingItem.item)
        }