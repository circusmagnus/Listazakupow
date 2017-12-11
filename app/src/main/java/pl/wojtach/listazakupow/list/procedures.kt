package pl.wojtach.listazakupow.list

import android.app.Activity
import android.util.Log
import pl.wojtach.listazakupow.shared.getAllShoppingListsFromSQLite
import pl.wojtach.listazakupow.shared.getInitialState
import pl.wojtach.listazakupow.shared.mutate
import pl.wojtach.listazakupow.shared.use

fun onAddNewShoppingList(view: ShoppingListsView, activity: Activity)
        = getInitialState { view.adapter.shoppingLists }
        .mutate { addNewShoppingList(it, System.currentTimeMillis()) }
        .use { saveShoppingLists(data = it, context = activity.applicationContext) }
        .mutate { getAllShoppingListsFromSQLite(activity.applicationContext) }
        .use { drawListView(shoppingLists = it, view = view) }
        .use { Log.d("onAddNewShoppingList", "${it.first().timestamp}")}
        .use { startShoppingListDetailsActivity(it.first().id, activity) }

fun onActivityCreate(activity: Activity, view: ShoppingListsView) =
        getInitialState { getAllShoppingListsFromSQLite(activity.applicationContext) }
        .use { drawListView(shoppingLists = it, view = view) }