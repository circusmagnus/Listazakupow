package pl.wojtach.listazakupow.details

import android.content.Context
import pl.wojtach.listazakupow.shared.*

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

fun onFragmentDestroyed(view: ShoppingDetailsView, appContext: Context) =
        initProcedureWith { getShoppingListFromUI(view) }
                .use { saveShoppingListToSqlDb(appContext, it) }
