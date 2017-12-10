package pl.wojtach.listazakupow.list

import android.content.ContentValues
import android.content.Context
import pl.wojtach.listazakupow.database.DbContract
import pl.wojtach.listazakupow.database.DbHelper

/**
 * Created by Lukasz on 09.12.2017.
 */
fun writeShoppingList(context: Context, shoppingList: ShoppingList) = DbHelper(context)
        .writableDatabase
        .insert(DbContract.ShoppingListsTable.name, null, getContentValues(shoppingList))

private fun getContentValues(shoppingList: ShoppingList): ContentValues =
        ContentValues().apply {
            put(DbContract.ShoppingListsTable.Columns.name, shoppingList.name)
            put(DbContract.ShoppingListsTable.Columns.timestamp, shoppingList.timestamp)
        }