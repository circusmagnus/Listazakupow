package pl.wojtach.listazakupow.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import pl.wojtach.nazakupy.item.ShoppingList
import pl.wojtach.nazakupy.item.ShoppingListHolder

/**
 * Created by Lukasz on 03.12.2017.
 */
typealias ShoppingListDrawer = (view: ShoppingListSmallView) -> Unit

class ShoppingListsAdapter(var shoppingLists: List<ShoppingListDrawer>)
    : RecyclerView.Adapter<ShoppingListHolder>() {

    override fun onBindViewHolder(holder: ShoppingListHolder, position: Int) {
        holder.setData(shoppingLists[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListHolder =
            ShoppingListHolder(View(parent.context))

    override fun getItemCount(): Int = shoppingLists.size
}

