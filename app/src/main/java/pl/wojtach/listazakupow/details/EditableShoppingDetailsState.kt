package pl.wojtach.listazakupow.details

import android.view.inputmethod.EditorInfo
import pl.wojtach.listazakupow.list.ShoppingList
import java.text.SimpleDateFormat
import java.util.*

interface ShoppingDetailsState{
    fun draw (view: ShoppingDetailsView)
}

class NonExistingShoppingDetailsState : ShoppingDetailsState {
    override fun draw(view: ShoppingDetailsView) {

    }
}

class ArchivedShoppingDetailsState(
        shoppingList: ShoppingList,
        val shoppingItems: List<ShoppingItem>) : ShoppingDetailsState {

    val shoppingList = shoppingList.takeIf { it.isArchived } ?: throw IllegalArgumentException("This list is not archived")

    override fun draw(view: ShoppingDetailsView) =
            view.apply {
                shoppingListName.text = shoppingList.name
                shoppingListDate.text = SimpleDateFormat("dd-MM-yyyy").format(Date(shoppingList.timestamp))
                shoppingListItems.adapter.items = shoppingItems

                shoppingListName.inputType = EditorInfo.TYPE_NULL
                shoppingListDate.inputType = EditorInfo.TYPE_NULL
            }.let { Unit }
}


class EditableShoppingDetailsState(
        shoppingList: ShoppingList,
        val shoppingItems: List<ShoppingItem>
) : ShoppingDetailsState {

    val shoppingList = shoppingList.takeUnless { it.isArchived } ?: throw IllegalArgumentException("This list is archived")

    override fun draw(view: ShoppingDetailsView) =
        view.apply {
            shoppingListName.text = shoppingList.name
            shoppingListDate.text = SimpleDateFormat("dd-MM-yyyy").format(Date(shoppingList.timestamp))
            shoppingListItems.adapter.items = shoppingItems
            addNewShoppingItemButton.setOnClickListener {
                onShoppingListItemAdded(
                        view = this,
                        appContext = this.appContext,
                        shoppingListId = shoppingList.id) }

        }.let { Unit }

}