package pl.wojtach.listazakupow.details

import pl.wojtach.listazakupow.list.ShoppingList

//TODO(Intended for signalling errors but not used yet)
class ErrorShoppingDetailsState : ShoppingDetailsState {

    override val shoppingList: ShoppingList
        get() = throw IllegalStateException()
    override val shoppingItemGetters: List<GetShoppingItem>
        get() = throw IllegalStateException()

    override fun draw(view: ShoppingDetailsView) {
        TODO("need to show error here")
    }
}