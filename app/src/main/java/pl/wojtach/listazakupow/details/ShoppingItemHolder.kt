package pl.wojtach.listazakupow.details

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextWatcher
import android.view.ViewGroup
import kotlinx.android.synthetic.main.shopping_item.view.*

sealed class ShoppingItemHolder(open val view: ViewGroup) : RecyclerView.ViewHolder(view) {

    private lateinit var textWatcher: TextWatcher

    private fun appContext(): Context = view.context.applicationContext

    open fun onBind(getItem: GetShoppingItem, removeShoppingItem: RemoveShoppingItem) {

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

    class OrdinaryHolder(override val view: ViewGroup) : ShoppingItemHolder(view)

    class LastShoppingItemHolder(override val view: ViewGroup) : ShoppingItemHolder(view) {

        override fun onBind(getItem: GetShoppingItem, removeShoppingItem: RemoveShoppingItem) {
            super.onBind(getItem, removeShoppingItem)
            view.shopping_item.requestFocus()
        }
    }
}