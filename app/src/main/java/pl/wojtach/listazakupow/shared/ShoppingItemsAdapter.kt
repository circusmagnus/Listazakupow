package pl.wojtach.listazakupow.shared

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.shopping_item.view.*
import pl.wojtach.listazakupow.R

/**
 * Created by Lukasz on 05.12.2017.
 */

interface ListAdapter<ITEM>{
    val items: MutableList<ITEM>
}

class ShoppingItemsAdapter(val items: List<String>
) : RecyclerView.Adapter<ShoppingItemHolder>() {

    override fun onBindViewHolder(holder: ShoppingItemHolder, position: Int) {
        holder.view.shopping_item.text = items[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemHolder =
            ShoppingItemHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.shopping_item, parent) as ViewGroup)

    override fun getItemCount(): Int = items.size
}

class ShoppingItemHolder(val view: ViewGroup): RecyclerView.ViewHolder(view)

