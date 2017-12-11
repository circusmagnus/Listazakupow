package pl.wojtach.listazakupow.list

import android.app.Activity
import android.util.Log
import pl.wojtach.listazakupow.shared.compose
import pl.wojtach.listazakupow.shared.getAllShoppingListsFromSQLite
import pl.wojtach.listazakupow.shared.initProcedureWith
import pl.wojtach.listazakupow.shared.use

fun onAddNewShoppingList(view: ShoppingListsView, activity: Activity)
        = initProcedureWith { view.adapter.shoppingLists }
        .compose { addNewShoppingList(it, System.currentTimeMillis()) }
        .compose { saveShoppingLists(data = it, context = activity.applicationContext) }
        .use {
            drawListView(shoppingLists = it, view = view)
            Log.d("onAddNewShoppingList", "${it.first().timestamp}")
            startShoppingListDetailsActivity(it.first().id, activity)
        }

fun onActivityCreate(activity: Activity, view: ShoppingListsView) =
        initProcedureWith { getAllShoppingListsFromSQLite(activity.applicationContext) }
                .use { drawListView(shoppingLists = it, view = view) }