package pl.wojtach.listazakupow.shared

/**
 * Created by Lukasz on 04.12.2017.
 */
data class ShoppingList(
        val name: String,
        val timestamp: Long,
        val shoppingItems: List<String>
)