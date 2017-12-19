package pl.wojtach.listazakupow.list

import android.content.Context
import android.widget.TextView

interface ShoppingListsMainView {
    val appContext: Context
    val shoppingLists: ShoppingListsView
    val title: TextView

    var state: STATE

    enum class STATE {
        CURRENT_LISTS, ARCHIVED_LISTS
    }
}