package pl.wojtach.listazakupow.list

import android.app.Activity
import pl.wojtach.listazakupow.shared.getAllShoppingListsFromSQLite
import pl.wojtach.listazakupow.shared.getInitialState
import pl.wojtach.listazakupow.shared.mutate
import pl.wojtach.listazakupow.shared.use

val shoppingListIdKey = "SHOPPING_LIST_KEY"

fun onAddNewShoppingList(view: ShoppingListsView, activity: Activity)
        = getInitialState { view.adapter.shoppingLists }
        .mutate { addNewShoppingList(it) }
        .use { saveShoppingLists(data = it, context = activity.applicationContext) }
        .use { drawListView(shoppingLists = it, view = view) }
        .use { startShoppingListDetailsActivity(it.first().timestamp, activity) }

fun onActivityCreate(activity: Activity, view: ShoppingListsView) =
        getInitialState { getAllShoppingListsFromSQLite(activity.applicationContext) }
        .use { drawListView(shoppingLists = it, view = view) }