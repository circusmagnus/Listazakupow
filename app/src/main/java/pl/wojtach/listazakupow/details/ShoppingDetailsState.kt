package pl.wojtach.listazakupow.details

import pl.wojtach.listazakupow.list.ShoppingList

class ShoppingDetailsState(
        val shoppingList: ShoppingList,
        val shoppingItems: List<ShoppingItem>
) {
}