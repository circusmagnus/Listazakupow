package pl.wojtach.listazakupow.details

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_shopping_list_details.*

import pl.wojtach.listazakupow.R

class ShoppingDetailsFrag : Fragment(), ShoppingDetailsView {

    override val appContext: Context
        get() = context.applicationContext

    override val shoppingListName: TextView
        get() = details_name

    override val shoppingListDate: TextView
        get() = details_date

    override val shoppingListItems: ShoppingDetailsRecycler
        get() = shopping_items_list

    override val addNewShoppingItemButton: Button
        get() = addNewShoppingItemButton

    private var selectedShoppingListId: Long = -1

    var state: ShoppingDetailsState = NonExistingShoppingDetailsState()

    override fun onCreateView(
            inflater: LayoutInflater?,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_shopping_list_details, container, false)
        return rootView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        selectedShoppingListId = (context as? ShoppingListIdProvider)
                ?.getSelectedShoppingListId()
                ?: throw ClassCastException("Attaching Activity should implement ShoppingListIdProvider")
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentViewCreated(
                appContext = appContext,
                view = this,
                shoppingListId = selectedShoppingListId)
    }


    interface ShoppingListIdProvider{
        fun getSelectedShoppingListId(): Long
    }
}
