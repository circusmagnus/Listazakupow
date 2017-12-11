package pl.wojtach.listazakupow.details

import android.content.Context
import pl.wojtach.listazakupow.shared.getInitialState
import pl.wojtach.listazakupow.shared.getShoppingItemsForId
import pl.wojtach.listazakupow.shared.getShoppingListByIdFromSQLIte
import pl.wojtach.listazakupow.shared.use

fun onCreate(view: ShoppingDetailsView, appContext: Context, id: Long) =
        getInitialState { ShoppingDetailsState(
                getShoppingListByIdFromSQLIte(appContext, id)!!,
                getShoppingItemsForId(id, appContext)
        ) }
                .use { drawDetailsView(it, view) }