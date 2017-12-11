package pl.wojtach.listazakupow.details

import android.content.Context
import pl.wojtach.listazakupow.shared.compose
import pl.wojtach.listazakupow.shared.initProcedureWith
import pl.wojtach.listazakupow.shared.saveShoppingItemToSqlDb
import pl.wojtach.listazakupow.shared.use

fun onFragmentViewCreated(view: ShoppingDetailsView, appContext: Context, shoppingListId: Long) =
        initProcedureWith { createShoppingDetailsState(appContext, shoppingListId) }
                .use { it.draw(view) }

fun onShoppingListItemAdded(view: ShoppingDetailsView, appContext: Context, shoppingListId: Long) =
        initProcedureWith { createShoppingDetailsState(appContext, shoppingListId) }
                .compose { addNewShoppingItem(it) }
                .use {
                    it.shoppingItems.forEach { saveShoppingItemToSqlDb(appContext, it) }
                    it.draw(view)
                }
