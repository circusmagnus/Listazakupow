package pl.wojtach.listazakupow.list

import android.content.Context

interface ShoppingListsMainView {
    val appContext: Context
    val shoppingLists: ShoppingListsView
}