package pl.wojtach.listazakupow.details

import android.annotation.SuppressLint
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
        TODO("need to show error here")
    }
}


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

class EditableShoppingDetailsState(
        shoppingList: ShoppingList,
        override val shoppingItemGetters: List<GetShoppingItem>) : ShoppingDetailsState {

    override val shoppingList = shoppingList.takeUnless { it.isArchived } ?: throw IllegalArgumentException("This list is archived")

    override fun draw(view: ShoppingDetailsView) =
        view.apply {
            drawListName()
            drawListDate()
            setupAddButton()
            setupItemsAdapter(view)
        }.let { Unit }

    private fun setupItemsAdapter(view: ShoppingDetailsView) {
        view.shoppingListItems.getEditableItemsAdapter()
                ?.apply {
                    getters = shoppingItemGetters
                    removers = createItemRemovers(shoppingItemGetters, view)
                    notifyDataSetChanged()
                } ?: EditableShoppingItemsAdapter(
                shoppingItemGetters,
                createItemRemovers(shoppingItemGetters, view)
        ).let { view.shoppingListItems.adapter = it }
    }

    private fun createItemRemovers(getters: List<GetShoppingItem>, shoppingDetailsView: ShoppingDetailsView) =
            getters.map { createShoppingItemRemover(shoppingDetailsView, shoppingList.id) }

    private fun ShoppingDetailsView.setupAddButton() {
        addNewShoppingItemButton.setOnClickListener {
            onShoppingListItemAdded(
                    this,
                    this.appContext,
                    shoppingList.id)
        }
    }

    private fun ShoppingDetailsView.drawListDate() {
        shoppingListDate.text = SimpleDateFormat("dd-MM-yyyy").format(Date(shoppingList.timestamp))
    }

    private fun ShoppingDetailsView.drawListName() {
        shoppingListName.text = shoppingList.name
        shoppingListName.addTextChangedListener(
                SimpleTextWatcher {
                    onShoppingListNameEdited(
                            shoppingList.copy(name = shoppingListName.text.toString()),
                            appContext
                    )
                }
        )
    }
}