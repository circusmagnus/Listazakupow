package pl.wojtach.listazakupow.list

import android.app.Activity
import android.content.Context
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import pl.wojtach.listazakupow.list.ShoppingListsMainView.STATE.ARCHIVED_LISTS
import pl.wojtach.listazakupow.list.ShoppingListsMainView.STATE.CURRENT_LISTS
import pl.wojtach.listazakupow.shared.getActiveShoppingListsFromSQLite
import pl.wojtach.listazakupow.shared.getArchivedShoppingListsFromSQLite
import pl.wojtach.listazakupow.shared.setArchivedStatusOfShoppingListInSqlDb

//TODO: Operate on Database in background thread

fun onAddNewShoppingList(view: ShoppingListsView, activity: Activity) =
        with(createNewShoppingList(System.currentTimeMillis())) {
            (listOf(this) + view.adapter.shoppingLists)
                    .let { async { saveShoppingLists(data = it, context = activity.applicationContext) } }

            startShoppingListDetailsActivity(this.id, activity)
        }

fun onActivityCreate(mainView: ShoppingListsMainView) = drawMainView(mainView)

fun onActivityStart(mainView: ShoppingListsMainView) = launch(UI) { setupListView(mainView) }

private suspend fun setupListView(mainView: ShoppingListsMainView): ShoppingListsAdapter {
    return when (mainView.state) {
        CURRENT_LISTS -> async { getActiveShoppingListsFromSQLite(mainView.appContext) }
        ARCHIVED_LISTS -> async { getArchivedShoppingListsFromSQLite(mainView.appContext) }
    }.let { drawListView(it.await(), mainView.shoppingLists) }
}

fun onShoppingListClicked(listId: Long, context: Context) = startShoppingListDetailsActivity(listId, context)

fun onShoppingListArchived(listId: Long, listView: ShoppingListsView): Job = launch(UI) {
    async {
        setArchivedStatusOfShoppingListInSqlDb(listView.context.applicationContext, listId)(true)
        getActiveShoppingListsFromSQLite(listView.context.applicationContext)
    }.let { drawListView(shoppingLists = it.await(), view = listView) }
}

fun onShowArchivedListsClicked(mainView: ShoppingListsMainView) =
        mainView.switchToArchivedLists()
                .let { launch(UI) { setupListView(it) } }

fun onShowActiveListsClicked(mainView: ShoppingListsMainView) = mainView.switchToCurrentLists()
        .let { launch(UI) { setupListView(it) } }