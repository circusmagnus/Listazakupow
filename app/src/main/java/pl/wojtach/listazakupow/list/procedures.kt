package pl.wojtach.listazakupow.list

import android.app.Activity
import android.content.Context
import pl.wojtach.listazakupow.shared.getInitialState
import pl.wojtach.listazakupow.shared.mutate
import pl.wojtach.listazakupow.shared.use

val shoppingListIdKey = "SHOPPING_LIST_KEY"

fun onAddNewShoppingList(view: ShoppingListsView, appContext: Context)
        = getInitialState { view.adapter.shoppingLists }
        .mutate { addNewShoppingList(it) }
        .use { saveShoppingLists(data = it, context = appContext) }
        .use { drawListView(shoppingLists = it, view = view) }

fun onActivityCreate(activity: Activity, view: ShoppingListsView) =
        getInitialState { getAllShoppingListsFromSQLite(activity.applicationContext) }
        .use { drawListView(shoppingLists = it, view = view) }