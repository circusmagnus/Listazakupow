package pl.wojtach.listazakupow.details

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.archived_shopping_item.view.*

class ArchivedShoppingItemHolder(val view: ViewGroup) : RecyclerView.ViewHolder(view) {

    private fun appContext(): Context = view.context.applicationContext

    fun onBind(getter: GetShoppingItem) {
        getter(appContext())
                .let { view.shopping_item_archived.text = it?.item }
    }
}