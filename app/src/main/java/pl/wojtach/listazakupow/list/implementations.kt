package pl.wojtach.listazakupow.list

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.shopping_list_small_card.view.*
import pl.wojtach.listazakupow.R
import pl.wojtach.listazakupow.details.ShoppingDetailsActivity
import pl.wojtach.listazakupow.details.shoppingListIdKey
import pl.wojtach.listazakupow.list.ShoppingListsMainView.STATE.ARCHIVED_LISTS
import pl.wojtach.listazakupow.list.ShoppingListsMainView.STATE.CURRENT_LISTS
import pl.wojtach.listazakupow.shared.saveShoppingListToSqlDb
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Lukasz on 10.12.2017.
 */

fun drawMainView(mainView: ShoppingListsMainView) =
        when (mainView.state) {
            CURRENT_LISTS -> mainView.title.text = mainView.appContext.getString(R.string.active_lists)
            ARCHIVED_LISTS -> mainView.title.text = mainView.appContext.getString(R.string.archived_lists)
        }

fun ShoppingListsMainView.switchToCurrentLists() = apply { state = CURRENT_LISTS }

fun ShoppingListsMainView.switchToArchivedLists() = apply { state = ARCHIVED_LISTS }

fun drawListView(shoppingLists: List<ShoppingList>, view: ShoppingListsView) =
        view.adapter.apply {
            this.shoppingLists = shoppingLists
            this.archivers =
                    shoppingLists
                    .map { { shoppingList: ShoppingList -> onShoppingListArchived(shoppingList.id, view) } }
            notifyDataSetChanged()
        }

@SuppressLint("SimpleDateFormat")
fun drawSmallShoppingListView(shoppingList: ShoppingList, view: ShoppingListSmallView, archivize: ShoppingListArchiver) =
        view.apply {
            name.text = shoppingList.name
            date.text = SimpleDateFormat("dd-MM-yyyy").format(Date(shoppingList.timestamp))

            when (shoppingList.isArchived) {
                true -> archivize_button.visibility = View.GONE
                false -> {
                    archivize_button.visibility = View.VISIBLE
                    archivize_button.setOnClickListener { archivize(shoppingList) }
                }
            }
        }

fun addNewShoppingList(oldData: List<ShoppingList>, timestamp: Long) = ShoppingList(
        name = "",
        timestamp = timestamp,
        isArchived = false
).let { listOf(it) + oldData }

fun saveShoppingLists(context: Context, data: List<ShoppingList>) =
        data.map { it.copy(id = saveShoppingListToSqlDb(context, it)) }

fun startShoppingListDetailsActivity(listId: Long, context: Context) =
        Intent(context, ShoppingDetailsActivity::class.java)
                .apply { putExtra(shoppingListIdKey, listId) }
                .let { context.startActivity(it) }

fun archivizeShoppingList(shoppingList: ShoppingList) =
        shoppingList.copy(isArchived = true)
