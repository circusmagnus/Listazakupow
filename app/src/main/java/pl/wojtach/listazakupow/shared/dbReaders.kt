package pl.wojtach.listazakupow.shared

import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import pl.wojtach.listazakupow.database.DbContract
import pl.wojtach.listazakupow.database.DbContract.ShoppingListsTable.Columns.isArchived
import pl.wojtach.listazakupow.database.DbContract.ShoppingListsTable.Columns.name
import pl.wojtach.listazakupow.database.DbContract.ShoppingListsTable.Columns.timestamp
import pl.wojtach.listazakupow.database.DbCreator
import pl.wojtach.listazakupow.details.ShoppingItem
import pl.wojtach.listazakupow.list.ShoppingList

private val shoppingListsProjection = arrayOf(
        BaseColumns._ID,
        name,
        timestamp,
        isArchived
)

private val shoppingItemsProjection = arrayOf(
        BaseColumns._ID,
        DbContract.ShoppingItemsTable.Columns.shoppingItem,
        DbContract.ShoppingItemsTable.Columns.shoppingListId
)

private val shoppingItemsIdsProjection = arrayOf(BaseColumns._ID)

fun getAllShoppingListsFromSQLite(context: Context): List<ShoppingList> = QueryPayload(
        DbContract.ShoppingListsTable.name,
        shoppingListsProjection,
        null,
        null,
        null,
        null,
        "$timestamp DESC"
).executeQuery(context)
        .let { mapToShoppingLists(it) }

fun getShoppingListByIdFromSQLIte(context: Context, id: Long): ShoppingList? = QueryPayload(
        DbContract.ShoppingListsTable.name,
        shoppingListsProjection,
        "${BaseColumns._ID} =?",
        arrayOf(id.toString()),
        null,
        null,
        null
).executeQuery(context)
        .let { mapToShoppingLists(it) }
        .firstOrNull()

fun getShoppingItemsForId(id: Long, appContext: Context) = QueryPayload(
        DbContract.ShoppingItemsTable.name,
        shoppingItemsProjection,
        "${DbContract.ShoppingItemsTable.Columns.shoppingListId} =?",
        arrayOf(id.toString()),
        null,
        null,
        null
).executeQuery(appContext)
        .let { mapToShoppingItems(it) }

fun getShoppingItemById(shoppingItemId: Long, appContext: Context) = QueryPayload(
        tableName = DbContract.ShoppingItemsTable.name,
        objectProjection = shoppingItemsProjection,
        where = "${BaseColumns._ID} =?",
        whereArgs = arrayOf(shoppingItemId.toString()),
        groupBy = null,
        having = null,
        orderBy = null
).executeQuery(appContext)
        .let { mapToShoppingItems(it) }
        .firstOrNull()

fun getShoppingItemsIds(shoppingListId: Long, appContext: Context) = QueryPayload(
        DbContract.ShoppingItemsTable.name,
        shoppingItemsIdsProjection,
        "${DbContract.ShoppingItemsTable.Columns.shoppingListId} =?",
        arrayOf(shoppingListId.toString()),
        null,
        null,
        null
).executeQuery(appContext)
        .let { mapToShoppingItemsIds(it) }

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
}

data class QueryPayload(
        val tableName: String,
        val objectProjection: Array<String>,
        val where: String?,
        val whereArgs: Array<String>?,
        val groupBy: String?,
        val having: String?,
        val orderBy: String?
)

private fun QueryPayload.executeQuery(appContext: Context): Cursor =
        DbCreator(appContext).readableDatabase.query(
                tableName,
                objectProjection,
                where,
                whereArgs,
                groupBy,
                having,
                orderBy
        )


