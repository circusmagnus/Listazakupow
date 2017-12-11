package pl.wojtach.listazakupow.details

import android.content.Context
import pl.wojtach.listazakupow.list.ShoppingList
import pl.wojtach.listazakupow.shared.getShoppingItemsForId
import pl.wojtach.listazakupow.shared.getShoppingListByIdFromSQLIte
import java.text.SimpleDateFormat
import java.util.*

fun drawDetailsView(shoppingList: ShoppingList, shoppingItems: List<ShoppingItem>, view: ShoppingDetailsView) =
        view.apply {
            shoppingListName.text = shoppingList.name
            shoppingListDate.text =  SimpleDateFormat("dd-MM-yyyy").format(Date(shoppingList.timestamp))
            shoppingListItems.adapter.items = shoppingItems
        }

fun createShoppingDetailsState(appContext: Context, shoppingListId: Long): ShoppingDetailsState =
        getShoppingListByIdFromSQLIte(appContext, shoppingListId)
                .let {
                    when {
                        it == null -> NonExistingShoppingDetailsState()
                        it.isArchived -> ArchivedShoppingDetailsState(
                                it,
                                getShoppingItemsForId(shoppingListId, appContext)
                        )
                        it.isArchived.not() -> EditableShoppingDetailsState(
                                it,
                                getShoppingItemsForId(shoppingListId, appContext)
                        )
                        else -> throw IllegalArgumentException("unexpected shopping list state")
                    }
                }

fun addNewShoppingItem(oldState: ShoppingDetailsState): EditableShoppingDetailsState =
        (oldState.shoppingItems + ShoppingItem(shoppingListId = oldState.shoppingList.id, item = "co?"))
                .let { EditableShoppingDetailsState(oldState.shoppingList, it) }

fun getShoppingListFromUI(view: ShoppingDetailsView) = ShoppingList(
                        id = view.selectedShoppingListId,
                        name = view.shoppingListName.text.toString(),
                        timestamp = getTimeStampFromUI(view),
                        isArchived = false)


private fun getTimeStampFromUI(view: ShoppingDetailsView) =
        view.shoppingListDate.text.toString().let { SimpleDateFormat("dd-MM-yyyy").parse(it).time }