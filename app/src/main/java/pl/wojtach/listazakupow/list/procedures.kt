package pl.wojtach.listazakupow.list

import android.content.Context

fun onAddNewShoppingList(view: ShoppingListsView, appContext: Context)
        = getShoppingLists { view.adapter.shoppingLists }
        .mutate { addNewShoppingList(it) }
        .act { saveShoppingLists(data = it, context = appContext) }
        .act { drawListView(shoppingLists = it, view = view) }