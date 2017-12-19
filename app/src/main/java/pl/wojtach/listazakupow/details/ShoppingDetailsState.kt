package pl.wojtach.listazakupow.details

import android.content.Context
import pl.wojtach.listazakupow.list.ShoppingList

typealias GetShoppingItem = (Context) -> ShoppingItem?

interface ShoppingDetailsState{
    val shoppingList: ShoppingList
    val shoppingItemGetters: List<GetShoppingItem>

    fun draw (view: ShoppingDetailsView)
}


