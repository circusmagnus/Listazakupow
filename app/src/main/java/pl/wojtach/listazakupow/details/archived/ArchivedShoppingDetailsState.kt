package pl.wojtach.listazakupow.details.archived

import android.annotation.SuppressLint
import android.view.View
import android.view.inputmethod.EditorInfo
import pl.wojtach.listazakupow.details.GetShoppingItem
import pl.wojtach.listazakupow.details.ShoppingDetailsState
import pl.wojtach.listazakupow.details.ShoppingDetailsView
import pl.wojtach.listazakupow.list.ShoppingList
import java.text.SimpleDateFormat
import java.util.*

class ArchivedShoppingDetailsState(
        shoppingList: ShoppingList,
        override val shoppingItemGetters: List<GetShoppingItem>) : ShoppingDetailsState {

    override val shoppingList = shoppingList.takeIf { it.isArchived } ?: throw IllegalArgumentException("This list is not archived")

    @SuppressLint("SimpleDateFormat")
    override fun draw(view: ShoppingDetailsView) =
            view.apply {
                drawFields()
                setupItemsAdapter()
            }.let { Unit }

    private fun ShoppingDetailsView.setupItemsAdapter() {
        shoppingListItems.getNonEditableItemsAdapter()
                ?.apply {
                    getters = shoppingItemGetters
                    notifyDataSetChanged()
                } ?: NonEditableShoppingItemsAdapter(shoppingItemGetters)
                .let { shoppingListItems.adapter = it }
    }

    private fun ShoppingDetailsView.drawFields() {
        shoppingListName.text = shoppingList.name
        shoppingListDate.text = SimpleDateFormat("dd-MM-yyyy").format(Date(shoppingList.timestamp))
        shoppingListName.inputType = EditorInfo.TYPE_NULL
        shoppingListDate.inputType = EditorInfo.TYPE_NULL
        addNewShoppingItemButton.visibility = View.GONE
    }
}