package pl.wojtach.listazakupow.details

import android.content.Context
import pl.wojtach.listazakupow.details.archived.ArchivedShoppingDetailsState
import pl.wojtach.listazakupow.details.editable.EditableShoppingDetailsState
import pl.wojtach.listazakupow.shared.getShoppingItemById
import pl.wojtach.listazakupow.shared.getShoppingItemsIds
import pl.wojtach.listazakupow.shared.getShoppingListByIdFromSQLIte

fun createShoppingDetailsState(appContext: Context, shoppingListId: Long): ShoppingDetailsState =
        getShoppingListByIdFromSQLIte(appContext, shoppingListId)
                .let {
                    when {
                        it == null -> ErrorShoppingDetailsState()
                        it.isArchived -> ArchivedShoppingDetailsState(
                                it,
                                createShoppingItemGetters(shoppingListId, appContext)
                        )
                        it.isArchived.not() -> EditableShoppingDetailsState(
                                it,
                                createShoppingItemGetters(shoppingListId, appContext)
                        )
                        else -> throw IllegalArgumentException("unexpected shopping list state")
                    }
                }

private fun createShoppingItemGetters(shoppingListId: Long, appContext: Context) =
        getShoppingItemsIds(shoppingListId, appContext)
                .map { shoppingItemId -> createShoppingItemGetter(shoppingItemId) }

private fun createShoppingItemGetter(shoppingItemId: Long) =
        { context: Context -> getShoppingItemById(shoppingItemId, context) }

fun createNewShoppingItem(shoppingListId: Long) = ShoppingItem(shoppingListId = shoppingListId, item = "")

fun scrollToNewItem(view: ShoppingDetailsView) {
    view.shoppingListItems.scrollToPosition(getLastPosition(view))
}

private fun getLastPosition(view: ShoppingDetailsView) =
        view.shoppingListItems.adapter.itemCount - 1