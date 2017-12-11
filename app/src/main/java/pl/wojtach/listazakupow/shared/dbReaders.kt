package pl.wojtach.listazakupow.shared

import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import pl.wojtach.listazakupow.database.DbContract
import pl.wojtach.listazakupow.database.DbContract.ShoppingListsTable.Columns.name
import pl.wojtach.listazakupow.database.DbContract.ShoppingListsTable.Columns.timestamp
import pl.wojtach.listazakupow.database.DbHelper
import pl.wojtach.listazakupow.details.ShoppingItem
import pl.wojtach.listazakupow.list.ShoppingList

private val shoppingListsProjection = arrayOf(
        BaseColumns._ID,
        name,
        timestamp
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
        .let { mapToEntities(it) }

fun getShoppingListByIdFromSQLIte(context: Context, id: Long): ShoppingList? = DbHelper(context).readableDatabase
        .query(
                DbContract.ShoppingListsTable.name,
                shoppingListsProjection,
                "$timestamp =?",
                arrayOf(id.toString()),
                null,
                null,
                null
        ).let { mapToShoppingLists(it) }
        .firstOrNull()

private fun mapToShoppingLists(cursor: Cursor): List<ShoppingList> {
    tailrec fun addToList(list: MutableList<ShoppingList>): MutableList<ShoppingList> =
            if (!cursor.moveToNext()) list
            else {
                list.add(ShoppingList(
                        name = cursor.getString(getIndex(name, cursor)),
                        timestamp = cursor.getLong(getIndex(timestamp, cursor))
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

private fun mapToShoppingItems(cursor: Cursor): List<ShoppingItem> {
    tailrec fun addToList(list: MutableList<ShoppingItem>): MutableList<ShoppingItem> =
            if (!cursor.moveToNext()) list
            else {
                list.add(ShoppingItem(
                        shoppingListId = cursor.getLong(getIndex(DbContract.ShoppingItemsTable.Columns.shoppingListId, cursor)),
                        item = cursor.getString(getIndex(DbContract.ShoppingItemsTable.Columns.shoppingItem, cursor))
                ))
                addToList(list)
            }
    return addToList(mutableListOf()).also { cursor.close() }
}


