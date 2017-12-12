package pl.wojtach.listazakupow.details

import android.content.Context
import pl.wojtach.listazakupow.list.ShoppingList
import pl.wojtach.listazakupow.shared.getShoppingItemById
import pl.wojtach.listazakupow.shared.getShoppingItemsIds
import pl.wojtach.listazakupow.shared.getShoppingListByIdFromSQLIte
import pl.wojtach.listazakupow.shared.saveShoppingItemToSqlDb
import java.text.SimpleDateFormat

//fun drawDetailsView(shoppingList: ShoppingList, shoppingItems: List<ShoppingItem>, view: ShoppingDetailsView) =
//        view.apply {
//            shoppingListName.text = shoppingList.name
//            shoppingListDate.text =  SimpleDateFormat("dd-MM-yyyy").format(Date(shoppingList.timestamp))
//            shoppingListItems.adapter.items = shoppingItems
//        }

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
        ShoppingItem(shoppingListId = oldState.shoppingList.id, item = "co?")
                .let { saveShoppingItemToSqlDb(appContext, it) }
                .let { id -> createShoppingItemGetter(id) }
                .let { oldState.shoppingItems + it }
                .let { EditableShoppingDetailsState(oldState.shoppingList, it) }

private fun createShoppingItemGetter(shoppingItemId: Long) =
        { context: Context -> getShoppingItemById(shoppingItemId, context) }


fun getShoppingListFromUI(view: ShoppingDetailsView) = ShoppingList(
                        id = view.selectedShoppingListId,
                        name = view.shoppingListName.text.toString(),
                        timestamp = getTimeStampFromUI(view),
                        isArchived = false)


private fun getTimeStampFromUI(view: ShoppingDetailsView) =
        view.shoppingListDate.text.toString().let { SimpleDateFormat("dd-MM-yyyy").parse(it).time }