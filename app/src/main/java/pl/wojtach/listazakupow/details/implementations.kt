package pl.wojtach.listazakupow.details

import android.content.Context
import pl.wojtach.listazakupow.list.ShoppingList
import pl.wojtach.listazakupow.shared.*
import java.text.SimpleDateFormat

fun createShoppingDetailsState(appContext: Context, shoppingListId: Long): ShoppingDetailsState =
        getShoppingListByIdFromSQLIte(appContext, shoppingListId)
                .let {
                    when {
                        it == null -> NonExistingShoppingDetailsState()
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



fun addNewShoppingItem(oldState: ShoppingDetailsState, appContext: Context): EditableShoppingDetailsState =
        ShoppingItem(shoppingListId = oldState.shoppingList.id, item = "")
                .let { saveShoppingItemToSqlDb(appContext, it) }
                .let { id -> createShoppingItemGetter(id) }
                .let { newGetter -> oldState.shoppingItemGetters + newGetter }
                .let { EditableShoppingDetailsState(oldState.shoppingList, it) }

private fun createShoppingItemGetter(shoppingItemId: Long) =
        { context: Context -> getShoppingItemById(shoppingItemId, context) }

fun createShoppingItemRemover(shoppingDetailsView: ShoppingDetailsView, shoppingListId: Long) =
        { context: Context, shoppingItemId: Long ->
            deleteShoppingItemFromSqlDb(context, shoppingItemId)
                    .let { createShoppingDetailsState(
                            context,
                            shoppingListId) }
                    .apply { this.toString() }
                    .draw(shoppingDetailsView)
        }


fun getShoppingListFromUI(view: ShoppingDetailsView) = ShoppingList(
                        id = view.selectedShoppingListId,
                        name = view.shoppingListName.text.toString(),
                        timestamp = getTimeStampFromUI(view),
                        isArchived = false)


private fun getTimeStampFromUI(view: ShoppingDetailsView) =
        view.shoppingListDate.text.toString().let { SimpleDateFormat("dd-MM-yyyy").parse(it).time }

fun scrollToNewItem(view: ShoppingDetailsView) {
    view.shoppingListItems.scrollToPosition(getLastPosition(view))
}

private fun getLastPosition(view: ShoppingDetailsView) =
        view.shoppingListItems.adapter.itemCount - 1