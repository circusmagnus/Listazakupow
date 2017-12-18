package pl.wojtach.listazakupow.details

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import pl.wojtach.listazakupow.R

/**
 * Created by Lukasz on 05.12.2017.
 */

class ShoppingItemsAdapter(var getters: List<GetShoppingItem>, var removers: List<RemoveShoppingItem>
) : RecyclerView.Adapter<ShoppingItemHolder>() {

    override fun onBindViewHolder(holder: ShoppingItemHolder, position: Int) {
        holder.onBind(getters[position], removers[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemHolder =
            when (viewType) {
                0 -> ShoppingItemHolder.OrdinaryHolder(
                        getShopppingItemView(parent)
                )
                1 -> ShoppingItemHolder.LastShoppingItemHolder(
                        getShopppingItemView(parent)
                )
                else -> throw IllegalStateException("Invalid view type")
            }

    private fun getShopppingItemView(parent: ViewGroup) =
            LayoutInflater.from(parent.context).inflate(
                    R.layout.shopping_item, parent, false) as ViewGroup

    override fun getItemCount(): Int = getters.size

    override fun getItemViewType(position: Int): Int =
            when (position) {
                getters.lastIndex -> 1
                else -> 0
            }

    override fun onViewRecycled(holder: ShoppingItemHolder?) {
        holder?.onUnbind()
        super.onViewRecycled(holder)
    }
}

