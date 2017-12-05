package pl.wojtach.listazakupow.details

import pl.wojtach.listazakupow.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Lukasz on 04.12.2017.
 */
interface ShoppingDetailsDrawer {
    fun draw(view: ShoppingDetailsView)
}

class NonExistingShoppingDetailsDrawer(
        private val timestamp: () -> Long = { System.currentTimeMillis() }
): ShoppingDetailsDrawer {

    override fun draw(view: ShoppingDetailsView) {
        view.shoppingListName.text = view.appContext.getString(R.string.new_shopping_list_name)
        view.shoppingListDate.text = SimpleDateFormat("dd-MM-yyyy").format(Date(timestamp()))
    }
}