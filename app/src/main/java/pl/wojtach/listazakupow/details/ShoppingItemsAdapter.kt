package pl.wojtach.listazakupow.details

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Editable
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

class ShoppingItemsAdapter(var items: List<GetShoppingItem>
) : RecyclerView.Adapter<ShoppingItemHolder>() {

    override fun onBindViewHolder(holder: ShoppingItemHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemHolder =
            ShoppingItemHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.shopping_item, parent, false) as ViewGroup)

    override fun getItemCount(): Int = items.size

    override fun onViewRecycled(holder: ShoppingItemHolder?) {
        holder?.onUnbind()
        super.onViewRecycled(holder)
    }
}

class ShoppingItemHolder(val view: ViewGroup): RecyclerView.ViewHolder(view) {

    private lateinit var textWatcher: TextWatcher

    private fun appContext(): Context = view.context.applicationContext

    fun onBind(getItem: GetShoppingItem){
        textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                onShoppingItemEdited.invoke(
                        ShoppingItem(
                                id = getItem(appContext())?.id ?: -1L,
                                shoppingListId = getItem(appContext())?.shoppingListId ?: -1L,
                                item = view.shopping_item.text.toString()
                        ),
                        appContext()
                )
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }

        view.shopping_item.setText(getItem(appContext())?.item ?: "")
        view.shopping_item.addTextChangedListener(textWatcher)
    }

    fun onUnbind() {
        view.shopping_item.removeTextChangedListener(textWatcher)
    }
}

