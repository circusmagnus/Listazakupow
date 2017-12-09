package pl.wojtach.listazakupow.shared

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import pl.wojtach.listazakupow.database.DbContract
import pl.wojtach.listazakupow.database.DbContract.ShoppingItemsTable.Columns.shoppingItem
import pl.wojtach.listazakupow.database.DbContract.ShoppingItemsTable.Columns.shoppingListId
import pl.wojtach.listazakupow.database.DbContract.ShoppingListsTable.Columns.name
import pl.wojtach.listazakupow.database.DbContract.ShoppingListsTable.Columns.timestamp
import pl.wojtach.listazakupow.database.DbHelper

val shoppingListsProjection = arrayOf(
        BaseColumns._ID,
        name,
        timestamp
)

val shoppingItemsProjection = arrayOf(
        BaseColumns._ID,
        shoppingItem,
        shoppingListId
)

val selectAllShoppingLists = "SELECT * FROM ${DbContract.ShoppingListsTable} ORDER BY $timestamp"

fun getAllShoppingListsFromDb(context: Context): List<ShoppingList> = DbHelper(context).readableDatabase
        .rawQuery(selectAllShoppingLists, null)
        .let { mapToEntities(it) }

fun mapToEntities(cursor: Cursor): List<ShoppingList> {

    tailrec fun addToList(list: MutableList<ShoppingList>) : MutableList<ShoppingList> =
        if(!cursor.moveToNext()) list
        else {
            list.add(ShoppingList(
                    name = cursor.getString(getIndex(name, cursor)),
                   timestamp = cursor.getLong(getIndex(timestamp, cursor)),
                    shoppingItems = emptyList())
            )
            addToList(list)
        }

    return addToList(mutableListOf()).also { cursor.close() }
}

fun getIndex(columnName: String, cursor: Cursor) = cursor.getColumnIndexOrThrow(columnName)

fun writeShoppingList(context: Context, shoppingList: ShoppingList) = DbHelper(context)
        .writableDatabase
        .insert(DbContract.ShoppingListsTable.name, null, getContentValues(shoppingList))

fun getContentValues(shoppingList: ShoppingList): ContentValues =
        ContentValues().apply {
            put(name, shoppingList.name)
            put(timestamp, shoppingList.timestamp)
        }
