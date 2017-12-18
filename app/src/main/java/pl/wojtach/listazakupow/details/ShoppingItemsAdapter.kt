package pl.wojtach.listazakupow.details

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextWatcher
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

class ShoppingItemsAdapter(var getters: List<GetShoppingItem>, var removers: List<RemoveShoppingItem>
) : RecyclerView.Adapter<ShoppingItemHolder>() {

    override fun onBindViewHolder(holder: ShoppingItemHolder, position: Int) {
        holder.onBind(getters[position], removers[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemHolder =
            ShoppingItemHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.shopping_item, parent, false) as ViewGroup)

    override fun getItemCount(): Int = getters.size

    override fun onViewRecycled(holder: ShoppingItemHolder?) {
        holder?.onUnbind()
        super.onViewRecycled(holder)
    }
}

class ShoppingItemHolder(val view: ViewGroup): RecyclerView.ViewHolder(view) {

    private lateinit var textWatcher: TextWatcher

    private fun appContext(): Context = view.context.applicationContext

    fun onBind(getItem: GetShoppingItem, removeShoppingItem: RemoveShoppingItem){

        with(getItem(appContext())!!) {
            textWatcher = SimpleTextWatcher {
                onShoppingItemEdited.invoke(
                        ShoppingItem(
                                id = this.id,
                                shoppingListId = this.shoppingListId,
                                item = view.shopping_item.text.toString()
                        ),
                        appContext()
                )
            }
            view.shopping_item.setText(this.item)
            view.shopping_item.addTextChangedListener(textWatcher)
            view.delete_button.setOnClickListener { removeShoppingItem(appContext(), this.id) }
        }
    }

    fun onUnbind() {
        view.shopping_item.removeTextChangedListener(textWatcher)
        view.delete_button.setOnClickListener(null)
    }
}

