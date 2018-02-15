package pl.wojtach.listazakupow.list

import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.shopping_list_small_card.view.*

class ShoppingListHolder(val view: ShoppingListSmallView): RecyclerView.ViewHolder(view) {

    fun onBind(shoppingList: ShoppingList, archiver: ShoppingListArchiver) {
        drawSmallShoppingListView(shoppingList, view, archiver)
        view.setOnClickListener { onShoppingListClicked()(shoppingList.id, view.context) }
    }

    fun onUnbind(){
        view.archivize_button.setOnClickListener(null)
    }
}