package pl.wojtach.listazakupow.shared

import android.content.Context
import android.database.Cursor
import pl.wojtach.listazakupow.database.DbContract
import pl.wojtach.listazakupow.database.DbHelper

val shoppingListsProjection = arrayOf(
        DbContract.ShoppingListsTable.Columns.name,
        DbContract.ShoppingListsTable.Columns.timestamp
)

val shoppingItemsProjection = arrayOf(
        DbContract.ShoppingItemsTable.Columns.shoppingItem,
        DbContract.ShoppingItemsTable.Columns.shoppingListId
)

val select

fun getAllShoppingListsFromDb(context: Context): List<ShoppingList> =
//        DbHelper(context).readableDatabase.query(
//                DbContract.ShoppingListsTable.name,
//                shoppingListsProjection,
//                null,
//                null,
//                null,
//                null,
//                DbContract.ShoppingListsTable.Columns.timestamp
//        )

DbHelper(context).readableDatabase
        .rawQuery("SELECT * FROM ${DbContract.ShoppingListsTable}", null)
        .let { mapToEntities(it) }

fun mapToEntities(it: Cursor): List<ShoppingList> {
    while (it.moveToNext())
        it.getInt()
}
