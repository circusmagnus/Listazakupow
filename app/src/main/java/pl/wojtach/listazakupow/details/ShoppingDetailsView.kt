package pl.wojtach.listazakupow.details

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.widget.ListAdapter
import android.widget.TextView

/**
 * Created by Lukasz on 04.12.2017.
 */
interface ShoppingDetailsView {
    val appContext: Context
    val shoppingListName: TextView
    val shoppingListDate: TextView
    val shoppingListItems: ShoppingDetailsRecycler
}