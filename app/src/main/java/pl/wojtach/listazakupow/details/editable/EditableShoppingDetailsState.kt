package pl.wojtach.listazakupow.details.editable

import pl.wojtach.listazakupow.details.*
import pl.wojtach.listazakupow.list.ShoppingList
import java.text.SimpleDateFormat
import java.util.*

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
            getters.map { { item: ShoppingItem -> onShoppingItemDeleted()(item, shoppingDetailsView) } }

    private fun ShoppingDetailsView.setupAddButton() {
        addNewShoppingItemButton.setOnClickListener { onShoppingListItemAdded()(this, shoppingList.id) }
    }

    private fun ShoppingDetailsView.drawListDate() {
        shoppingListDate.text = SimpleDateFormat("dd-MM-yyyy").format(Date(shoppingList.timestamp))
    }

    private fun ShoppingDetailsView.drawListName() {
        shoppingListName.text = shoppingList.name
        shoppingListName.addTextChangedListener(
                SimpleTextWatcher {
                    onShoppingListNameEdited()(
                            shoppingList.copy(name = shoppingListName.text.toString()),
                            appContext
                    )
                }
        )
    }
}