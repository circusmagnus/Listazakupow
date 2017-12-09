package pl.wojtach.listazakupow.list

import android.content.Context
import kotlinx.android.synthetic.main.shopping_list_view.view.*
import pl.wojtach.listazakupow.shared.ShoppingList
import pl.wojtach.listazakupow.shared.getAllShoppingListsFromSQLite
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Lukasz on 09.12.2017.
 */

fun getAllShoppingLists(): (Context) -> List<ShoppingList> =
        {context -> getAllShoppingListsFromSQLite(context) }

fun draw(state: (Context) -> List<ShoppingList> ) = {
    context: Context -> state(context)
        .let {  }
}

fun drawShoppingLists(shoppingLists: List<ShoppingList>, view: ShoppingListsView) =
        view.adapter.apply {
            this.shoppingLists = shoppingLists.map { shoppingList ->
                { view: ShoppingListSmallView -> drawSmallShoppingListView(
                        shoppingList = shoppingList,
                        view = view
                ) }
            }
            notifyDataSetChanged()
        }


fun drawSmallShoppingListView(shoppingList: ShoppingList, view: ShoppingListSmallView) =
        view.apply {
            name.setText(shoppingList.name)
            date.setText(SimpleDateFormat("dd-MM-yyyy").format(Date(shoppingList.timestamp)))
        }.let { Unit }