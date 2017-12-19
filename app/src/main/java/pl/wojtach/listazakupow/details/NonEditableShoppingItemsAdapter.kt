package pl.wojtach.listazakupow.details

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import pl.wojtach.listazakupow.R

class NonEditableShoppingItemsAdapter(var getters: List<GetShoppingItem>
) : RecyclerView.Adapter<ArchivedShoppingItemHolder>() {

    override fun onBindViewHolder(holder: ArchivedShoppingItemHolder?, position: Int) {
        holder?.onBind(getters[position])
    }

    override fun getItemCount(): Int = getters.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchivedShoppingItemHolder =
            getShopppingItemView(parent)
                    .let { ArchivedShoppingItemHolder(it) }

    private fun getShopppingItemView(parent: ViewGroup) =
            LayoutInflater.from(parent.context).inflate(
                    R.layout.archived_shopping_item, parent, false) as ViewGroup
}