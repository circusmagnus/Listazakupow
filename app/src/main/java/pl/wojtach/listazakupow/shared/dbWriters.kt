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

//TODO: error handling

fun saveShoppingListToSqlDb(context: Context, shoppingList: ShoppingList): Long =
        writableDatabase(context).insertWithOnConflict(
                    DbContract.ShoppingListsTable.name,
                    null,
                    getContentValuesForList(shoppingList),
                    CONFLICT_REPLACE
            )

private fun writableDatabase(context: Context) =
        DatabaseHolder.getInstance(context).writableDatabase

fun setArchivedStatusOfShoppingListInSqlDb(context: Context, id: Long) =
        { archived: Boolean ->
            writableDatabase(context).update(
                    DbContract.ShoppingListsTable.name,
                    ContentValues().apply { put(DbContract.ShoppingListsTable.Columns.isArchived, archived) },
                    "${BaseColumns._ID}=?",
                    arrayOf(id.toString())
            )
        }

fun saveShoppingItemToSqlDb(context: Context, shoppingItem: ShoppingItem): Long =
        writableDatabase(context).insertWithOnConflict(
                    DbContract.ShoppingItemsTable.name,
                    null,
                    getContentValuesForItem(shoppingItem),
                    CONFLICT_REPLACE
            )

fun deleteShoppingItemFromSqlDb(context: Context, shoppingItem: ShoppingItem) =
        deleteShoppingItemFromSqlDb(context, shoppingItem.id)

fun deleteShoppingItemFromSqlDb(context: Context, id: Long) =
        writableDatabase(context).delete(
                DbContract.ShoppingItemsTable.name,
                "${BaseColumns._ID} =?",
                arrayOf(id.toString())
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