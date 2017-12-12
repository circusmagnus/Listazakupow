package pl.wojtach.listazakupow.shared

import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.util.Log
import pl.wojtach.listazakupow.database.DbContract
import pl.wojtach.listazakupow.database.DbContract.ShoppingListsTable.Columns.isArchived
import pl.wojtach.listazakupow.database.DbContract.ShoppingListsTable.Columns.name
import pl.wojtach.listazakupow.database.DbContract.ShoppingListsTable.Columns.timestamp
import pl.wojtach.listazakupow.database.DbHelper
import pl.wojtach.listazakupow.details.ShoppingItem
import pl.wojtach.listazakupow.list.ShoppingList

private val shoppingListsProjection = arrayOf(
        BaseColumns._ID,
        name,
        timestamp,
        isArchived
)

fun getAllShoppingListsFromSQLite(context: Context): List<ShoppingList> = DbHelper(context).readableDatabase
        .query(DbContract.ShoppingListsTable.name,
                shoppingListsProjection,
                null,
                null,
                null,
                null,
                "$timestamp DESC"
        )
        .let { mapToShoppingLists(it) }

fun getShoppingListByIdFromSQLIte(context: Context, id: Long): ShoppingList? = DbHelper(context).readableDatabase
        .query(
                DbContract.ShoppingListsTable.name,
                shoppingListsProjection,
                "${BaseColumns._ID} =?",
                arrayOf(id.toString()),
                null,
                null,
                null
        ).also { Log.d("getListById", "$id") }
        .let { mapToShoppingLists(it) }
        .firstOrNull()

private fun mapToShoppingLists(cursor: Cursor): List<ShoppingList> {
    tailrec fun addToList(list: MutableList<ShoppingList>): MutableList<ShoppingList> =
            if (!cursor.moveToNext()) list
            else {
                list.add(ShoppingList(
                        id = cursor.getLong(getIndex(BaseColumns._ID, cursor)),
                        name = cursor.getString(getIndex(name, cursor)),
                        timestamp = cursor.getLong(getIndex(timestamp, cursor)),
                        isArchived = cursor.getInt(getIndex(isArchived, cursor)) == 1
                ))
                addToList(list)
            }
    return addToList(mutableListOf()).also { cursor.close() }
}

private fun getIndex(columnName: String, cursor: Cursor) = cursor.getColumnIndexOrThrow(columnName)

private val shoppingItemsProjection = arrayOf(
        BaseColumns._ID,
        DbContract.ShoppingItemsTable.Columns.shoppingItem,
        DbContract.ShoppingItemsTable.Columns.shoppingListId
)

private val shoppingItemsIdsProjection = arrayOf(BaseColumns._ID)

fun getShoppingItemsForId(id: Long, appContext: Context) = DbHelper(appContext).readableDatabase
        .query(
                DbContract.ShoppingItemsTable.name,
                shoppingItemsProjection,
                "${DbContract.ShoppingItemsTable.Columns.shoppingListId} =?",
                arrayOf(id.toString()),
                null,
                null,
                null
        ).let { mapToShoppingItems(it) }

fun getShoppingItemsIds(shoppingListId: Long, appContext: Context) = DbHelper(appContext).readableDatabase
        .query(DbContract.ShoppingItemsTable.name,
                shoppingItemsIdsProjection,
                "${DbContract.ShoppingItemsTable.Columns.shoppingListId} =?",
                arrayOf(shoppingListId.toString()),
                null,
                null,
                null
        ).let { mapToShoppingItemsIds(it) }

private fun mapToShoppingItems(cursor: Cursor): List<ShoppingItem> {
    tailrec fun addToList(list: MutableList<ShoppingItem>): MutableList<ShoppingItem> =
            if (!cursor.moveToNext()) list
            else {
                list.add(ShoppingItem(
                        id = cursor.getLong(getIndex(BaseColumns._ID, cursor)),
                        shoppingListId = cursor.getLong(getIndex(DbContract.ShoppingItemsTable.Columns.shoppingListId, cursor)),
                        item = cursor.getString(getIndex(DbContract.ShoppingItemsTable.Columns.shoppingItem, cursor))
                ))
                addToList(list)
            }
    return addToList(mutableListOf()).also { cursor.close() }
}

private fun mapToShoppingItemsIds(cursor: Cursor): List<Long> {
    tailrec fun addToList(list: MutableList<Long>): MutableList<Long> =
            if (!cursor.moveToNext()) list
            else {
                list.add(cursor.getLong(getIndex(BaseColumns._ID, cursor)))
                addToList(list)
            }
    return addToList(mutableListOf()).also { cursor.close() }


