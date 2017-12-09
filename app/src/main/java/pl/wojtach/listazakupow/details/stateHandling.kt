package pl.wojtach.listazakupow.details

import android.content.Context
import pl.wojtach.listazakupow.shared.ShoppingList
import pl.wojtach.listazakupow.shared.getAllShoppingListsFromDb

fun initialState() = { ShoppingList(
        name = "Nowa lista",
        timestamp = System.currentTimeMillis(),
        shoppingItems = emptyList())}

fun getAllShoppingLists(): (Context) -> List<ShoppingList> =
        {context -> getAllShoppingListsFromDb(context)
                .mapToState

fun draw(state: )