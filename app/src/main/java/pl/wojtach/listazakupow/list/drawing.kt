package pl.wojtach.listazakupow.list

import kotlinx.android.synthetic.main.shopping_list_item_view.view.*
import pl.wojtach.listazakupow.shared.ShoppingList
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Lukasz on 10.12.2017.
 */

fun setupShoppingListsAdapter(shoppingLists: List<ShoppingList>, view: ShoppingListsView) =
        view.adapter.apply {
            this.shoppingLists = shoppingLists
            notifyDataSetChanged()
        }


fun drawSmallShoppingListView(shoppingList: ShoppingList, view: ShoppingListSmallView) =
        view.apply {
            name.setText(shoppingList.name)
            date.setText(SimpleDateFormat("dd-MM-yyyy").format(Date(shoppingList.timestamp)))
        }