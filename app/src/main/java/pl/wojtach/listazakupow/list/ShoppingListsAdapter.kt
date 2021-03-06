package pl.wojtach.listazakupow.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import pl.wojtach.listazakupow.R

/**
 * Created by Lukasz on 03.12.2017.
 */

typealias ShoppingListArchiver = (ShoppingList) -> Unit

class ShoppingListsAdapter(var shoppingLists: List<ShoppingList>, var archivers: List<ShoppingListArchiver>)
    : RecyclerView.Adapter<ShoppingListHolder>() {

    override fun onBindViewHolder(holder: ShoppingListHolder, position: Int) {
        holder.onBind(shoppingLists[position], archivers[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListHolder =
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.shopping_list_small_card, parent, false)
                    .let { ShoppingListHolder(it as ShoppingListSmallView) }


    override fun getItemCount(): Int = shoppingLists.size
}

