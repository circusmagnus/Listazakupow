package pl.wojtach.listazakupow.shared

import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import pl.wojtach.listazakupow.database.DbContract
import pl.wojtach.listazakupow.database.DbContract.ShoppingItemsTable.Columns.shoppingItem
import pl.wojtach.listazakupow.database.DbContract.ShoppingItemsTable.Columns.shoppingListId
import pl.wojtach.listazakupow.database.DbContract.ShoppingListsTable.Columns.name
import pl.wojtach.listazakupow.database.DbContract.ShoppingListsTable.Columns.timestamp
import pl.wojtach.listazakupow.database.DbHelper

private val shoppingListsProjection = arrayOf(
        BaseColumns._ID,
        name,
        timestamp
)

private val shoppingItemsProjection = arrayOf(
        BaseColumns._ID,
        shoppingItem,
        shoppingListId
)

private val selectAllShoppingLists = "SELECT * FROM ${DbContract.ShoppingListsTable.name} ORDER BY $timestamp"

fun getAllShoppingListsFromSQLite(context: Context): List<ShoppingList> = DbHelper(context).readableDatabase
        .rawQuery(selectAllShoppingLists, null)
        .let { mapToEntities(it) }

fun getShoppingListByIdFromSQLIte(context: Context, id: Long): ShoppingList? = DbHelper(context).readableDatabase
        .query(
                DbContract.ShoppingListsTable.name,
                shoppingListsProjection,
                timestamp,
                arrayOf(id.toString()),
                null,
                null,
                null
        ).let { mapToEntities(it) }
        .firstOrNull()

private fun mapToEntities(cursor: Cursor): List<ShoppingList> {
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

