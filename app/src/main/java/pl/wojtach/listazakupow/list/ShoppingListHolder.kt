package pl.wojtach.listazakupow.list

import android.support.v7.widget.RecyclerView
import pl.wojtach.listazakupow.shared.ShoppingList

class ShoppingListHolder(val view: ShoppingListSmallView): RecyclerView.ViewHolder(view) {

    fun setData(shoppingList: ShoppingList) {
        drawSmallShoppingListView(shoppingList, view)
    }
}