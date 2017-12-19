package pl.wojtach.listazakupow.list

import android.app.Activity
import android.content.Context
import android.util.Log
import pl.wojtach.listazakupow.list.ShoppingListsMainView.STATE.ARCHIVED_LISTS
import pl.wojtach.listazakupow.list.ShoppingListsMainView.STATE.CURRENT_LISTS
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

val onActivityCreate = { mainView: ShoppingListsMainView -> drawMainView(mainView) }

val onActivityStart = { mainView: ShoppingListsMainView ->
    when (mainView.state) {
        CURRENT_LISTS -> getActiveShoppingListsFromSQLite(mainView.appContext)
        ARCHIVED_LISTS -> getArchivedShoppingListsFromSQLite(mainView.appContext)
    }.let { drawListView(it, mainView.shoppingLists) }
}

val onShoppingListClicked = { listId: Long, context: Context -> startShoppingListDetailsActivity(listId, context) }

val onShoppingListArchived: (Long, ShoppingListsView) -> Unit = {
    listId: Long, listView: ShoppingListsView ->
    getShoppingListByIdFromSQLIte(listView.context.applicationContext, listId)
            ?.let { archivizeShoppingList(it) }
            ?.let { saveShoppingListToSqlDb(listView.context.applicationContext, it) }
            ?.let { getActiveShoppingListsFromSQLite(listView.context.applicationContext) }
            ?.let { lists: List<ShoppingList> -> drawListView(lists, listView) }
}

val onShowArchivedListsClicked = { mainView: ShoppingListsMainView ->
    getArchivedShoppingListsFromSQLite(mainView.appContext)
            .let { drawListView(it, mainView.shoppingLists) }
            .let { mainView.switchToArchivedLists() }
            .let { drawMainView(it) }
}

val onShowActiveListsClicked = { mainView: ShoppingListsMainView ->
    getActiveShoppingListsFromSQLite(mainView.appContext)
            .let { drawListView(it, mainView.shoppingLists) }
            .let { mainView.switchToCurrentLists() }
            .let { drawMainView(it) }
}