package pl.wojtach.listazakupow.details

import pl.wojtach.listazakupow.list.ShoppingList
import java.text.SimpleDateFormat
import java.util.*

fun drawDetailsView(shoppingList: ShoppingList, shoppingItems: List<ShoppingItem>, view: ShoppingDetailsView) =
        view.apply {
            shoppingListName.text = shoppingList.name
            shoppingListDate.text =  SimpleDateFormat("dd-MM-yyyy").format(Date(shoppingList.timestamp))
            shoppingListItems.adapter.items = shoppingItems
        }