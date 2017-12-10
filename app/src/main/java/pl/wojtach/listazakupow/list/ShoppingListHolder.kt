package pl.wojtach.listazakupow.list

import android.support.v7.widget.RecyclerView

class ShoppingListHolder(val view: ShoppingListSmallView): RecyclerView.ViewHolder(view) {

    fun setData(shoppingList: ShoppingList) {
        drawSmallShoppingListView(shoppingList, view)
    }
}