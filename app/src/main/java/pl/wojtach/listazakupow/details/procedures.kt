package pl.wojtach.listazakupow.details

import android.content.Context
import android.util.Log
import pl.wojtach.listazakupow.shared.initProcedureWith
import pl.wojtach.listazakupow.shared.saveShoppingItemToSqlDb
import pl.wojtach.listazakupow.shared.saveShoppingListToSqlDb
import pl.wojtach.listazakupow.shared.use

fun onFragmentViewCreated(view: ShoppingDetailsView, appContext: Context, shoppingListId: Long) =
        initProcedureWith { createShoppingDetailsState(appContext, shoppingListId) }
                .use { it.draw(view) }

val onShoppingListItemAdded: (ShoppingDetailsView, Context, Long) -> Unit =
        { view: ShoppingDetailsView, appContext: Context, shoppingListId: Long ->
            createShoppingDetailsState(appContext, shoppingListId)
                    .let { addNewShoppingItem(it, appContext) }
                    .run { draw(view) }
        }

//        initProcedureWith { createShoppingDetailsState(appContext, shoppingListId) }
//                .compose { addNewShoppingItem(it, appContext) }
//                .use { it.draw(view) }

fun onFragmentDestroyed(view: ShoppingDetailsView, appContext: Context) =
        initProcedureWith { getShoppingListFromUI(view) }
                .use { saveShoppingListToSqlDb(appContext, it) }

val onShoppingItemEdited =
        { item: ShoppingItem, appContext: Context -> saveShoppingItemToSqlDb(appContext, item)
            Log.d("onItemEdited", "id: ${item.id} content: ${item.item}")
        }
