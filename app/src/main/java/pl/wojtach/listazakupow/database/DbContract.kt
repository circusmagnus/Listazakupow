package pl.wojtach.listazakupow.database

import android.provider.BaseColumns

object DbContract {
    val DATABASE_VERSION = 2
    val DATABASE_NAME = "ListaZakupow.db"

    object ShoppingListsTable {
        val name = "ShoppingLists"

        object Columns : BaseColumns {
            val timestamp = "timestamp"
            val name = "name"
            val isArchived = "isArchived"
        }
    }

    object ShoppingItemsTable {
        val name = "ShoppingItems"

        object Columns : BaseColumns {
            val shoppingListId = "shoppingListId"
            val shoppingItem = "shoppingItem"
        }
    }

}