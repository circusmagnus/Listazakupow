package pl.wojtach.listazakupow.list

import android.app.Activity
import android.content.Context
import android.content.Intent
import kotlinx.android.synthetic.main.shopping_list_small_card.view.*
import pl.wojtach.listazakupow.details.ShoppingDetailsActivity
import pl.wojtach.listazakupow.details.shoppingListIdKey
import pl.wojtach.listazakupow.shared.saveShoppingListToSQL
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

fun addNewShoppingList(oldData: List<ShoppingList>, timestamp: Long) = ShoppingList(
        name = "Lista zakupów",
        timestamp = timestamp,
        isArchived = false
).let { listOf(it) + oldData }

fun saveShoppingLists(context: Context, data: List<ShoppingList>) =
        data.forEach { saveShoppingListToSQL(context, it) }

fun startShoppingListDetailsActivity(listId: Long, activity: Activity) =
        Intent(activity, ShoppingDetailsActivity::class.java)
                .apply { putExtra(shoppingListIdKey, listId) }
                .let { activity.startActivity(it) }