package pl.wojtach.listazakupow.details

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import pl.wojtach.listazakupow.list.ShoppingList
import java.text.SimpleDateFormat
import java.util.*

typealias GetShoppingItem = (Context) -> ShoppingItem?
typealias RemoveShoppingItem = (Context, Long) -> Unit

interface ShoppingDetailsState{
    val shoppingList: ShoppingList
    val shoppingItemGetters: List<GetShoppingItem>

    fun draw (view: ShoppingDetailsView)
}

class NonExistingShoppingDetailsState : ShoppingDetailsState {
    override val shoppingList: ShoppingList
        get() = throw IllegalStateException()
    override val shoppingItemGetters: List<GetShoppingItem>
        get() = throw IllegalStateException()

    override fun draw(view: ShoppingDetailsView) {
    }
}


class ArchivedShoppingDetailsState(
        shoppingList: ShoppingList,
        override val shoppingItemGetters: List<GetShoppingItem>) : ShoppingDetailsState {

    override val shoppingList = shoppingList.takeIf { it.isArchived } ?: throw IllegalArgumentException("This list is not archived")

    override fun draw(view: ShoppingDetailsView) =
            view.apply {
                shoppingListName.text = shoppingList.name
                shoppingListDate.text = SimpleDateFormat("dd-MM-yyyy").format(Date(shoppingList.timestamp))
                shoppingListItems.adapter.getters = shoppingItemGetters
                shoppingListItems.adapter.removers = shoppingItemGetters
                        .map { createShoppingItemRemover(view, shoppingList.id) }

                shoppingListName.inputType = EditorInfo.TYPE_NULL
                shoppingListDate.inputType = EditorInfo.TYPE_NULL
                addNewShoppingItemButton.visibility = View.GONE
            }.let { Unit }
}


class EditableShoppingDetailsState(
        shoppingList: ShoppingList,
        override val shoppingItemGetters: List<GetShoppingItem>) : ShoppingDetailsState {

    override val shoppingList = shoppingList.takeUnless { it.isArchived } ?: throw IllegalArgumentException("This list is archived")

    override fun draw(view: ShoppingDetailsView) =
        view.apply {
            shoppingListName.text = shoppingList.name
            shoppingListName.addTextChangedListener(
                    SimpleTextWatcher {
                        onShoppingListNameEdited(shoppingList.copy(name = shoppingListName.text.toString()), view.appContext)
                    }
            )

            shoppingListDate.text = SimpleDateFormat("dd-MM-yyyy").format(Date(shoppingList.timestamp))
            shoppingListItems.adapter.getters = shoppingItemGetters
            shoppingListItems.adapter.removers = shoppingItemGetters
                    .map { createShoppingItemRemover(view, shoppingList.id) }

            shoppingListItems.adapter.notifyDataSetChanged()

            addNewShoppingItemButton.setOnClickListener {
                onShoppingListItemAdded(
                        this,
                        this.appContext,
                        shoppingList.id)
            }

        }.let { Unit }


}