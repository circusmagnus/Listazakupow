package pl.wojtach.listazakupow.details

import android.content.Context
import android.util.Log
import pl.wojtach.listazakupow.list.ShoppingList
import pl.wojtach.listazakupow.shared.*

//TODO: Operate on Database in background thread

//TODO: change this one to convention of use cases as function vals
fun onFragmentViewCreated(view: ShoppingDetailsView, appContext: Context, shoppingListId: Long) =
        initProcedureWith { createShoppingDetailsState(appContext, shoppingListId) }
                .use { it.draw(view) }

val onShoppingListItemAdded: (ShoppingDetailsView, Long) -> Unit =
        { view: ShoppingDetailsView, shoppingListId: Long ->
            createNewShoppingItem(shoppingListId)
                    .let { saveShoppingItemToSqlDb(view.appContext, it) }
                    .let { createShoppingDetailsState(view.appContext, shoppingListId) }
                    .draw(view)
                    .also { scrollToNewItem(view) }
        }

val onShoppingItemEdited =
        { item: ShoppingItem, appContext: Context ->
            saveShoppingItemToSqlDb(appContext, item)
            Log.d("onItemEdited", "id: ${item.id} content: ${item.item}")
        }

val onShoppingItemDeleted = { item: ShoppingItem, view: ShoppingDetailsView ->
    deleteShoppingItemFromSqlDb(view.appContext, item)
            .let { createShoppingDetailsState(view.appContext, item.shoppingListId) }
            .draw(view)
}

val onShoppingListNameEdited = { shoppingList: ShoppingList, appContext: Context ->
    saveShoppingListToSqlDb(appContext, shoppingList)
}
