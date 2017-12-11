package pl.wojtach.listazakupow.details

import android.content.Context
import pl.wojtach.listazakupow.shared.getInitialState
import pl.wojtach.listazakupow.shared.mutate
import pl.wojtach.listazakupow.shared.use

fun onFragmentViewCreated(view: ShoppingDetailsView, appContext: Context, shoppingListId: Long) =
        getInitialState { createShoppingDetailsState(appContext, shoppingListId) }
                .use { it.draw(view) }

fun onShoppingListItemAdded(view: ShoppingDetailsView, appContext: Context, shoppingListId: Long) =
        getInitialState { createShoppingDetailsState(appContext, shoppingListId) }
                .mutate { addNewShoppingItem(it as EditableShoppingDetailsState) }
                .use { it.draw(view) }

fun addNewShoppingItem(oldState: EditableShoppingDetailsState): EditableShoppingDetailsState =
        (oldState.shoppingItems + ShoppingItem(shoppingListId = oldState.shoppingList.id, item = "co?"))
                .let { EditableShoppingDetailsState(oldState.shoppingList, it) }
