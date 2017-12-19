package pl.wojtach.listazakupow.details.archived

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.archived_shopping_item.view.*
import pl.wojtach.listazakupow.details.GetShoppingItem

class ArchivedShoppingItemHolder(val view: ViewGroup) : RecyclerView.ViewHolder(view) {

    private fun appContext(): Context = view.context.applicationContext

    fun onBind(getter: GetShoppingItem) {
        getter(appContext())
                .let { view.shopping_item_archived.text = it?.item }
    }
}