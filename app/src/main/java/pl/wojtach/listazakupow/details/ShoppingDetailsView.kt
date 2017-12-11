package pl.wojtach.listazakupow.details

import android.content.Context
import android.widget.ImageButton
import android.widget.TextView

/**
 * Created by Lukasz on 04.12.2017.
 */
interface ShoppingDetailsView {
    val appContext: Context
    val shoppingListName: TextView
    val shoppingListDate: TextView
    val shoppingListItems: ShoppingDetailsRecycler
    val addNewShoppingItemButton: ImageButton
    val selectedShoppingListId: Long
}