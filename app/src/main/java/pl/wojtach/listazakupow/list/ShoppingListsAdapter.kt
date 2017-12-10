package pl.wojtach.listazakupow.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import pl.wojtach.listazakupow.R

/**
 * Created by Lukasz on 03.12.2017.
 */

class ShoppingListsAdapter(var shoppingLists: List<ShoppingList>)
    : RecyclerView.Adapter<ShoppingListHolder>() {

    override fun onBindViewHolder(holder: ShoppingListHolder, position: Int) {
        holder.setData(shoppingLists[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListHolder =
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.shopping_list_item_view, parent)
                    .let { it as ShoppingListSmallView }
                    .let { ShoppingListHolder(it) }


    override fun getItemCount(): Int = shoppingLists.size
}

