package pl.wojtach.listazakupow.list

import android.app.Activity
import android.content.Context
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import pl.wojtach.listazakupow.list.ShoppingListsMainView.STATE.ARCHIVED_LISTS
import pl.wojtach.listazakupow.list.ShoppingListsMainView.STATE.CURRENT_LISTS
import pl.wojtach.listazakupow.shared.*

//TODO: Operate on Database in background thread

//TODO: change this one to convention of use cases as function vals
fun onAddNewShoppingList(view: ShoppingListsView, activity: Activity)
        = initProcedureWith { view.adapter.shoppingLists }
        .compose { addNewShoppingList(it, System.currentTimeMillis()) }
        .compose { saveShoppingLists(data = it, context = activity.applicationContext) }
        .use {
            drawListView(shoppingLists = it, view = view)
            startShoppingListDetailsActivity(it.first().id, activity)
        }

fun onActivityCreate() = { mainView: ShoppingListsMainView -> drawMainView(mainView) }

fun onActivityStart() = { mainView: ShoppingListsMainView ->
    launch(UI) { setupListView(mainView) }
}

private suspend fun setupListView(mainView: ShoppingListsMainView): ShoppingListsAdapter {
    return when (mainView.state) {
        CURRENT_LISTS -> async { getActiveShoppingListsFromSQLite(mainView.appContext) }
        ARCHIVED_LISTS -> async { getArchivedShoppingListsFromSQLite(mainView.appContext) }
    }.let { drawListView(it.await(), mainView.shoppingLists) }
}

fun onShoppingListClicked() = { listId: Long, context: Context -> startShoppingListDetailsActivity(listId, context) }

fun onShoppingListArchived(): (Long, ShoppingListsView) -> Unit = {
    listId: Long, listView: ShoppingListsView ->
    getShoppingListByIdFromSQLIte(listView.context.applicationContext, listId)
            ?.let { archivizeShoppingList(it) }
            ?.let { saveShoppingListToSqlDb(listView.context.applicationContext, it) }
            ?.let { getActiveShoppingListsFromSQLite(listView.context.applicationContext) }
            ?.let { lists: List<ShoppingList> -> drawListView(lists, listView) }
}

fun onShowArchivedListsClicked() = { mainView: ShoppingListsMainView ->
    getArchivedShoppingListsFromSQLite(mainView.appContext)
            .let { drawListView(it, mainView.shoppingLists) }
            .let { mainView.switchToArchivedLists() }
            .let { drawMainView(it) }
}

fun onShowActiveListsClicked() = { mainView: ShoppingListsMainView ->
    getActiveShoppingListsFromSQLite(mainView.appContext)
            .let { drawListView(it, mainView.shoppingLists) }
            .let { mainView.switchToCurrentLists() }
            .let { drawMainView(it) }
}