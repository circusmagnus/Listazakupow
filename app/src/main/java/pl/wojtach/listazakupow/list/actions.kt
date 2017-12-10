package pl.wojtach.listazakupow.list

import android.content.Context
import kotlinx.android.synthetic.main.shopping_list_item_view.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Lukasz on 10.12.2017.
 */

fun drawListView(shoppingLists: List<ShoppingList>, view: ShoppingListsView) =
        view.adapter.apply {
            this.shoppingLists = shoppingLists
            notifyDataSetChanged()
        }


fun drawSmallShoppingListView(shoppingList: ShoppingList, view: ShoppingListSmallView) =
        view.apply {
            name.setText(shoppingList.name)
            date.setText(SimpleDateFormat("dd-MM-yyyy").format(Date(shoppingList.timestamp)))
        }

fun addNewShoppingList(oldData: List<ShoppingList>) = ShoppingList(
        name = "Lista zakupów",
        timestamp = System.currentTimeMillis()
).let { listOf(it) + oldData }

fun saveShoppingLists(context: Context, data: List<ShoppingList>) =
        data.forEach { writeShoppingList(context, it) }