package pl.wojtach.listazakupow.details

import pl.wojtach.listazakupow.shared.ShoppingList

fun initialState() = { ShoppingList(
        name = "Nowa lista",
        timestamp = System.currentTimeMillis(),
        shoppingItems = emptyList())}

fun draw(state: )