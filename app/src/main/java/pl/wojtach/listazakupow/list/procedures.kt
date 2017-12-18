package pl.wojtach.listazakupow.list

import android.app.Activity
import android.content.Context
import android.util.Log
import pl.wojtach.listazakupow.shared.*

fun onAddNewShoppingList(view: ShoppingListsView, activity: Activity)
        = initProcedureWith { view.adapter.shoppingLists }
        .compose { addNewShoppingList(it, System.currentTimeMillis()) }
        .compose { saveShoppingLists(data = it, context = activity.applicationContext) }
        .use {
            drawListView(shoppingLists = it, view = view)
            Log.d("onAddNewShoppingList", "${it.first().timestamp}")
            startShoppingListDetailsActivity(it.first().id, activity)
        }

fun onActivityStart(activity: Activity, view: ShoppingListsView) =
        initProcedureWith { getActiveShoppingListsFromSQLite(activity.applicationContext) }
                .use { drawListView(shoppingLists = it, view = view) }

val onShoppingListClicked = { listId: Long, context: Context -> startShoppingListDetailsActivity(listId, context) }

val onShoppingListArchived: (Long, ShoppingListsView) -> Unit = {
    listId: Long, listView: ShoppingListsView ->
    getShoppingListByIdFromSQLIte(listView.context.applicationContext, listId)
            ?.let { archivizeShoppingList(it) }
            ?.let { saveShoppingListToSqlDb(listView.context.applicationContext, it) }
            ?.let { getActiveShoppingListsFromSQLite(listView.context.applicationContext) }
            ?.let { lists: List<ShoppingList> -> drawListView(lists, listView) }
}