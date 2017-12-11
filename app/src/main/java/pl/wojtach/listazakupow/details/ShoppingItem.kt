package pl.wojtach.listazakupow.details

data class ShoppingItem(
        val id: Long = -1,
        val shoppingListId: Long,
        val item: String
)