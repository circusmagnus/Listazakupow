package pl.wojtach.listazakupow.list

/**
 * Created by Lukasz on 04.12.2017.
 */
data class ShoppingList(
        var id: Long = -1,
        val name: String,
        val timestamp: Long,
        val isArchived: Boolean
)

