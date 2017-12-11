package pl.wojtach.listazakupow.details

import android.content.Context
import pl.wojtach.listazakupow.shared.getInitialState
import pl.wojtach.listazakupow.shared.getShoppingItemsForId
import pl.wojtach.listazakupow.shared.getShoppingListByIdFromSQLIte
import pl.wojtach.listazakupow.shared.use

fun onCreate(view: ShoppingDetailsView, appContext: Context, id: Long) =
        getInitialState { ShoppingDetailsState(
                shoppingList = getShoppingListByIdFromSQLIte(appContext, id)!!,
                shoppingItems = getShoppingItemsForId(id, appContext)
        ) }
                .use { it.draw(view) }