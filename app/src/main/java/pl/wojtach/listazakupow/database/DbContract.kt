package pl.wojtach.listazakupow.database

import android.provider.BaseColumns

object DbContract {
    val DATABASE_VERSION = 1
    val DATABASE_NAME = "ListaZakupow.db"

    object ShoppingListsTable : BaseColumns {
        val name = "ShoppingLists"
        val timestamp = "timestamp"
        val listName = "listName"
    }

}